# Design: News Feed (Twitter / Instagram home timeline)

Core lesson: **fan-out on write vs fan-out on read** (push vs pull).

---

## 1. Clarify requirements

### Functional

| In scope | Out of scope |
|----------|--------------|
| User posts tweet/post | ML ranking (v2: simple time order) |
| Home timeline of followees | Explore/discover tab |
| Follow / unfollow | DMs |

### Non-functional

- **300M users**, **100M DAU**
- **500M posts/day** (writes)
- **Each user** loads feed 10×/day → **1B feed reads/day**
- **Latency:** feed load p99 < 300 ms
- **Consistency:** eventual OK; author sees own post immediately (read-your-writes)

---

## 2. Capacity estimate

- Writes: 500M/day → **5K/sec** avg → **~15K/sec** peak posts
- Reads: 1B/day → **10K/sec** avg → **~30K/sec** peak feed loads

Fan-out worst case:

- Celebrity 50M followers → **50M writes per post** if pure push — **bad**

Storage:

- Post ~1 KB → 500M/day → **500 GB/day** posts table

Timeline cache per user:

- 500 post IDs × 8 bytes × 100M active users — too big if fully materialized — need hybrid

---

## 3. High-level diagram

```text
 [Post API] ──► [Post DB]
        │
        └──► [Fan-out Service] ──► [Timeline cache / Redis or Cassandra]
                    │
                    └── skip if follower_count > threshold

 [Feed API] ──► [Timeline cache] ──► merge ──► [Post DB] for hydrate
        │
        └──► on miss / heavy user: [pull from followees] + cache
```

```text
         WRITE PATH (push for normal users)
User ──► Post ──► Fan-out worker ──► each follower's timeline list

         READ PATH
User ──► Feed API ──► get timeline IDs ──► batch get posts ──► response
```

---

## 4. API sketch

```http
POST /v1/posts
Body: { "text": "...", "media_ids": [] }
Response: { "post_id": "p_123" }

GET /v1/feed?cursor=...&limit=20
Response: { "posts": [ {...}, ... ], "next_cursor": "..." }

POST /v1/users/{id}/follow
DELETE /v1/users/{id}/follow
```

---

## 5. Data model sketch

**`users`** — id, handle, follower_count (denormalized)

**`posts`** — post_id, author_id, content, created_at  
Shard: `post_id` or `author_id`

**`follows`** — follower_id, followee_id, created_at  
Index: (follower_id), (followee_id)

**`timelines`** (fan-out-on-write)

| user_id | post_id | ranked_ts |

Stored in Redis LIST/ZSET or Cassandra wide rows.

---

## 6. Deep dive

### A. Fan-out strategy (critical)

| User type | Strategy |
|-----------|----------|
| Normal (< 10K followers) | **Push** post ID to each follower's timeline |
| Celebrity (> 10K) | **Pull** merge at read time from celebrity's recent posts |
| Hybrid | Push for most; at read, **merge** celebrity posts |

```text
Feed = timeline_cache(user) UNION pull_posts(celebrities_followed)
       sort by time
```

### B. Feed read path

1. Get 20 timeline IDs from cache (ZSET by time)
2. **MGET** post bodies from cache or DB
3. Return JSON; cursor = oldest id

### C. Hot post / thundering herd

- Cache post by `post_id`
- CDN for images only

---

## 7. Bottlenecks & scaling

| Bottleneck | Fix |
|------------|-----|
| Celebrity fan-out | Pull model + threshold |
| Timeline storage size | Trim to last 1000 IDs; archive |
| Fan-out worker lag | Queue + scale consumers; priority tiers |
| Feed merge CPU | Precompute celebrity lists; limit follow count |

---

## 8. “Say this aloud” (85 sec)

> “Posting writes a row to the post store, then a fan-out worker pushes the post ID into each follower’s timeline for normal accounts. For celebrities we skip push because one post would fan out to millions of writes—we merge their recent posts at read time instead. Loading the feed reads a precomputed timeline from Redis or a wide-column store, hydrates post bodies in batch, and paginates with a cursor. We target eventual consistency for followers but read-your-writes for the author by injecting their new post in the response. The main trade-off is storage and write amplification versus read latency—hybrid push-pull is the standard answer at scale.”

---

## 9. Follow-up Q&A

**Q: Ranked feed?**  
A: Offline feature store + scoring service; v1 chronological ZSET.

**Q: Delete post?**  
A: Tombstone in post table; async purge from timelines (hard at scale).

**Q: Consistency on follow?**  
A: New follow may miss last hour — acceptable or backfill recent posts.

**Q: Kafka role?**  
A: `PostCreated` event → fan-out workers + search index + analytics.