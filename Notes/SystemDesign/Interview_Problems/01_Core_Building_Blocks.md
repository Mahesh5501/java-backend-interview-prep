# Core Building Blocks (Interview Vocabulary)

Every system design is a **composition** of these blocks. Know what each does, when to add it, and one sentence to explain it aloud.

---

## Big picture

```text
                    ┌─────────────┐
  User ───────────► │     CDN     │ (static / video edge)
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │ Load        │
                    │ Balancer    │
                    └──────┬──────┘
                           │
              ┌────────────┼────────────┐
              ▼            ▼            ▼
         [App server] [App server] [App server]  (stateless)
              │            │            │
              └────────────┼────────────┘
                           │
         ┌─────────────────┼─────────────────┐
         ▼                 ▼                 ▼
    [Cache]          [Database]        [Message Queue]
   (Redis)           (SQL / NoSQL)         │
                                           ▼
                                      [Workers]
```

---

## 1. DNS

**What:** Phone book for the internet. Turns `api.myapp.com` into an IP address.

**When:** Always there; you rarely design DNS from scratch.

**Say aloud:** “DNS resolves the hostname. We can use geo DNS or anycast later for multi-region.”

---

## 2. Load Balancer (LB)

**What:** Distributes traffic across many app servers. Health-checks bad nodes.

**Types (simple):**

| Type | Idea |
|------|------|
| L4 | Routes by IP/port (fast, less smart) |
| L7 | Routes by URL, headers, cookies (API-friendly) |

**Algorithms:** round-robin, least connections, consistent hash (sticky sessions).

**When to add:** More than one app instance, or you need SSL termination at the edge.

**Say aloud:** “The load balancer spreads requests so no single JVM dies. App servers stay stateless so we can scale horizontally.”

**Failure:** If LB dies, you need a **pair** or cloud-managed LB (interviews: “managed LB with HA”).

```text
        Clients
           │
           ▼
    ┌──────────────┐
    │  Active LB   │
    └──────┬───────┘
           │
     ┌─────┴─────┐
     ▼           ▼
  App-1       App-2
```

---

## 3. App server (compute)

**What:** Your business logic (Java Spring, etc.).

**Rules for interviews:**

- **Stateless** — session in Redis/DB, not local RAM.
- **Thread pool** — bounded; don’t unbounded `new Thread()` per request.
- **Timeouts** on all outbound calls (DB, HTTP, queue).

**Say aloud:** “These are interchangeable nodes behind the LB. Any instance can serve any user if we externalize session and cache shared state.”

**Scale:** horizontal — add instances; auto-scale on CPU or QPS.

---

## 4. Cache

**What:** Fast store in front of slow store (usually **Redis** or in-memory local cache).

**Patterns:**

| Pattern | Use |
|---------|-----|
| Cache-aside | App reads cache; on miss, read DB and fill cache |
| Write-through | Write cache + DB together |
| TTL | Expire stale data |

**When:** Read-heavy, same keys hit often, slightly stale data OK.

**Say aloud:** “We cache hot keys in Redis with a TTL. On miss we read the DB and populate cache. We plan for cache stampede with jittered expiry or single-flight.”

**Watch out:** **Hot keys** (one celebrity user) — replicate key, local cache, or precompute.

See also: `04_Caching.md`.

---

## 5. Database (DB)

**What:** Durable source of truth.

**SQL (relational):** MySQL, Postgres — ACID, joins, clear schema.

**NoSQL:** Document (Mongo), wide-column (Cassandra), key-value (Dynamo style), graph — scale patterns differ.

**Common layout:**

```text
        Writes ──► [Primary]
                        │
            ┌───────────┼───────────┐
            ▼           ▼           ▼
        [Replica]  [Replica]   (async replication)
            ▲
        Reads (eventually consistent)
```

**When to shard:** Single primary cannot hold data or write QPS.

**Shard key:** `user_id`, `tenant_id` — high cardinality, even spread.

**Say aloud:** “Primary handles writes. Replicas serve read scale with replication lag we acknowledge. We shard when single-node limits hit.”

See: `03_SQL_vs_NoSQL.md`.

---

## 6. Message queue / stream

**What:** Buffer between producers and consumers (Kafka, RabbitMQ, SQS).

**Why:**

- **Decouple** — API returns fast; worker processes later.
- **Smooth spikes** — queue absorbs burst.
- **Retry** — failed consumer retries or DLQ.

**Delivery:**

- At-most-once — may lose
- At-least-once — may duplicate (common + idempotent consumers)
- Exactly-once — expensive; often idempotency keys instead

**Say aloud:** “For email, indexing, or webhooks I’d publish an event after the DB commit so we don’t lose work if the worker is down.”

See: `05_Message_Queues.md`.

```text
 API ──commit──► DB
  │
  └──publish──► [Queue] ──► Worker pool
```

---

## 7. CDN (Content Delivery Network)

**What:** Copies static files (images, JS, video) at edge locations near users.

**When:** Global users, large static assets, video chunks.

**Say aloud:** “User-generated media goes to object storage; CDN caches at the edge so reads don’t hit our origin every time.”

```text
 User in India ──► CDN edge (Mumbai) ──► cache hit → fast
                              │
                         miss ▼
                         Origin (S3 / API)
```

---

## 8. Object storage

**What:** Cheap blobs (S3). Not for transactional row updates.

**When:** Images, backups, log archives.

---

## 9. Search index (optional block)

**What:** Elasticsearch/OpenSearch for full-text search.

**When:** SQL `LIKE` is too slow; faceted search.

**Pattern:** **CDC or queue** from DB → search index (eventual consistency).

---

## 10. API Gateway (optional block)

**What:** Single entry for auth, rate limit, routing to microservices.

**When:** Many backend services; mobile clients need one URL.

**Say aloud:** “Gateway handles TLS, JWT validation, and rate limits before traffic hits internal services.”

---

## How blocks combine (typical read path)

```text
GET /item/123

Client → LB → App → Redis get("item:123")
                      │
                   miss?
                      ▼
                   DB SELECT → set Redis → response
```

## How blocks combine (typical async write path)

```text
POST /order

Client → LB → App → DB transaction (order row)
              │
              └── event "OrderCreated" → Queue → Email / Analytics workers
```

---

## Picking blocks — decision table

| Need | Add |
|------|-----|
| More traffic than one server | LB + more app instances |
| Slow repeated reads | Cache |
| Durable structured data | SQL (default) |
| Huge write scale, flexible schema | NoSQL + clear access pattern |
| Spike / background work | Queue + workers |
| Static or video worldwide | CDN + object storage |
| Full-text search | Search index async from DB |

---

## Java engineer angles (mention when relevant)

- **Connection pool** to DB (HikariCP) — size per instance × instance count < DB max connections.
- **Idempotency** for POST retries (payments, orders).
- **Transactional outbox** — insert row + outbox in same TX, separate publisher reads outbox (clean Kafka pattern).
- **Virtual threads** (Java 21+) — more concurrent I/O waits; still bound DB and pool limits.

---

## “Say this aloud” — explain building blocks (75 sec)

> “I think in layers. DNS and CDN handle edge delivery for static content. A load balancer fronts stateless app servers that run our Java services. For data, a relational database is our source of truth; Redis caches hot reads. When we need async work—notifications, indexing, or fan-out—we commit business state in the database first, then publish to a message queue so workers can retry safely. Each piece solves one problem: LB for availability, cache for latency, DB for durability, queue for decoupling. I only add search indexes or separate read models when the access pattern outgrows simple SQL plus cache.”

---

## Quick self-test

1. Why must app servers be stateless behind an LB?
2. Cache-aside: who loads data on a miss?
3. Why publish to a queue **after** DB commit?
4. When would you choose CDN vs only object storage?
5. What breaks if every app server opens 500 DB connections?

*Answers: (1) any instance serves any user (2) application (3) otherwise you emit events for failed writes (4) CDN for low-latency global read of same bytes (5) DB connection exhaustion — pool and cap replicas).*