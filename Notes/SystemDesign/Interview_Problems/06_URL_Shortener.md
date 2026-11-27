# Design: URL Shortener (like bit.ly)

Classic **read-heavy** system. Good first full mock.

---

## 1. Clarify requirements

### Functional

| In scope | Out of scope (v1) |
|----------|-------------------|
| Shorten long URL → short link | Custom branded domains |
| Redirect short → long (HTTP 302/301) | Click analytics dashboard |
| Optional expiry / max uses | User accounts (optional v2) |

### Non-functional

- **Scale:** 100M new URLs/month, **10:1 read:write** redirect ratio
- **Latency:** redirect p99 < 100 ms
- **Availability:** 99.9%
- **Uniqueness:** two users must not get same short code for different URLs (or define collision handling)

---

## 2. Capacity estimate

Assumptions:

- 100M creates/month ≈ **3.3M/day** ≈ **40 creates/sec** avg → **~120/sec** peak
- 10× reads → **400/sec** avg → **~1.2K/sec** peak redirects

Storage (5 years):

- Row: short_code (7B) + long_url (500B avg) + metadata ≈ **600 bytes**
- 100M/month × 12 × 5 ≈ **6B rows** → ~3.6 TB raw (indexes extra)

**Say:** “Low write QPS, moderate read QPS. Storage is billions of rows — we need sharding eventually.”

---

## 3. High-level diagram

```text
 [Browser / App]
        │
        ▼
   [Load Balancer]
        │
        ▼
 [Redirect Service] ──────► [Redis cache]
        │                        │
        │ miss                   │
        ▼                        │
   [URL DB] ◄────────────────────┘
   (sharded)

 [Create API] ──► [ID generator] ──► [URL DB]
        │
        └──► cache write / invalidate
```

Separate **read path** (redirect) and **write path** (create) if traffic grows.

---

## 4. API sketch

```http
POST /v1/urls
Body: { "long_url": "https://...", "ttl_days": 30 }
Response: { "short_url": "https://sho.rt/abc12Xy" }

GET /abc12Xy
→ 302 Location: long_url
```

Admin (optional):

```http
DELETE /v1/urls/{code}   // invalidate
GET /v1/urls/{code}/stats
```

---

## 5. Data model sketch

**Table: `urls`**

| Column | Type | Notes |
|--------|------|-------|
| short_code | VARCHAR(7) PK | Base62 |
| long_url | TEXT | |
| created_at | TIMESTAMP | |
| expires_at | TIMESTAMP | nullable |
| owner_id | BIGINT | nullable |

**Index:** PK on `short_code` (redirect is lookup by code).

**Optional:** `long_url_hash` unique if dedupe same long URL to one short code.

---

## 6. Deep dive (pick 2–3)

### A. Generating short codes

**Option 1 — Base62 of auto-increment ID**

- Global ID from DB sequence or **Snowflake** ID
- Encode to Base62 (a–z, A–Z, 0–9)
- 7 chars → 62^7 ≈ 3.5 trillion codes

**Option 2 — Hash long URL**

- MD5/SHA truncate → collision risk → check DB and rehash

**Interview pick:** ID encoding — no collision loop on happy path.

### B. Redirect hot path

1. Parse `short_code`
2. Redis `GET url:{code}`
3. Miss → DB PK lookup → fill cache (TTL)
4. 302 to `long_url` (analytics: async queue click event)

### C. Sharding

Shard by **short_code hash** or range of ID.

Redirect only needs **one shard** — perfect key-based routing.

---

## 7. Bottlenecks & scaling

| Bottleneck | Scale lever |
|------------|-------------|
| Redis memory | Evict LRU; only cache hot 20% URLs (80/20) |
| DB read QPS | Read replicas + cache |
| DB size | Archive expired URLs; tier cold to object store |
| ID generator SPOF | Snowflake per app node or dedicated ID service |

---

## 8. “Say this aloud” (75 sec)

> “We shorten URLs with a create API that assigns a unique Base62 code from a distributed ID, stores the mapping in a sharded SQL or KV store, and caches hot codes in Redis. Redirects are read-heavy: lookup cache first, then database, return HTTP 302. We estimate low hundreds of creates per second at peak but over a thousand redirects, so we optimize the read path and cache popular links. Sharding by short code keeps redirects single-shard. Clicks can go to a queue for analytics without slowing redirect. Trade-off: 302 with short TTL cache vs 301 for permanent links—product choice.”

---

## 9. Common follow-up Q&A

**Q: 301 vs 302?**  
A: 301 caches at browser (good for permanent); 302 allows changing target and A/B.

**Q: Malicious URLs?**  
A: Scan queue, blocklist, rate limit creates per IP.

**Q: Same long URL twice?**  
A: Dedupe table on hash saves space; or allow duplicates for simplicity v1.

**Q: Custom alias `sho.rt/myname`?**  
A: Reserved words table; collision check on create.