# How to Approach System Design (Interview Framework)

Use this **same flow for every question**. Interviewers reward structure more than a perfect diagram.

---

## The 7-step flow (memorize this)

```text
1. Clarify requirements     (3–5 min)
2. Capacity estimate        (3–5 min)
3. High-level design        (8–12 min)
4. API + data model         (5–8 min)
5. Deep dive 2–3 parts      (15–20 min)
6. Bottlenecks + scale      (5 min)
7. Wrap-up summary          (1–2 min)
```

Say at the start:

> “I’ll clarify requirements, do a quick scale estimate, sketch a high-level design, then dive deeper where you care most.”

That one sentence buys you time and shows senior communication.

---

## Step 1 — Clarify requirements

### Functional (what the system does)

Ask:

- Who are the users? (mobile, web, internal admin)
- What are the **core actions**? (create, read, search, pay, message)
- Any **must-have** features for v1 vs nice-to-have?

Write two columns: **In scope** | **Out of scope**.

### Non-functional (how well it must work)

Pick **3–4** and get numbers if possible:

| NFR | Example questions |
|-----|-------------------|
| Scale | DAU? peak QPS? |
| Latency | p99 read vs write? |
| Availability | 99.9% OK? |
| Consistency | Strong or eventual? |
| Durability | Can we lose messages? |
| Security | Auth, PCI, PII? |

### Say this aloud (template)

> “Let me confirm scope. For v1 we need X and Y. We can defer Z. We’re targeting about N million DAU, reads dominate, and we can accept eventual consistency on feeds unless you want otherwise.”

### Red flags if you skip this

- You design Twitter when they asked for a URL shortener.
- You add Kafka everywhere with no async requirement.

---

## Step 2 — Capacity estimate (back-of-envelope)

Keep math **simple**. Round aggressively.

### Useful numbers (memorize)

| Fact | Round value |
|------|-------------|
| 1 day | ~86,400 sec → use **100K sec** |
| 1 million requests/day | ~12 QPS average |
| 1 billion requests/day | ~12K QPS average |
| 1 char | ~1 byte (ASCII) |
| 1 KB | 1000 bytes (interview math) |
| SSD | ms range; network cross-DC | tens–100+ ms |

### Formulas

```text
QPS (avg)     = total_requests_per_day / 100,000
QPS (peak)    = QPS_avg × 2 to 5   (pick 3 if unsure)

Storage/year  = records_per_day × size_per_record × 365
```

### Example script

> “10M DAU, each user does 20 reads and 2 writes per day. That’s 220M ops/day → ~2.2K avg QPS, call it **6–7K peak**. Each record is 500 bytes, 50M new records/day → ~25 GB/day before indexes.”

Show **work on paper**. Interviewers want thinking, not precision.

---

## Step 3 — High-level design (ASCII)

Start **boring and correct**:

```text
 [Client]
     |
     v
 [Load Balancer]
     |
     v
 [App servers] -----> [Cache] -----> [Database]
     |
     v
 [Message Queue] ----> [Workers]
     |
     v
 [Object storage / CDN]   (if media or static)
```

### Rules

- **One write path** and **one read path** labeled.
- Say **sync vs async** at edges (“payment write sync; receipt email async”).
- Add boxes **only when the requirement needs them**.

### Say this aloud

> “Clients hit a load balancer. Stateless app servers handle business logic. Hot reads go through cache. Primary data lives in the database. Slow or fan-out work goes through a queue and workers.”

---

## Step 4 — API + data model (sketch)

### API style

- REST is fine for interviews.
- Name resources: `POST /v1/links`, `GET /v1/feeds/{userId}`.
- Mention **idempotency** for payments and retries: header `Idempotency-Key`.

### Data model

- 3–6 tables/collections max on the whiteboard.
- Show **primary key** and **one index** you care about.
- Call out **shard key** if scale is huge (user_id, tenant_id).

### Say this aloud

> “I’ll keep APIs resource-oriented. For storage I’ll start with a relational core for money and identities, and add a document or wide-column store only if access patterns don’t fit SQL.”

---

## Step 5 — Deep dive (pick 2–3 components)

Interviewer usually steers. If not, pick:

1. **Hottest path** (read feed, send message, create payment)
2. **Hardest NFR** (consistency, real-time, fraud)
3. **Scale bottleneck** (DB, fan-out, single leader)

### Deep-dive checklist

- Request flow step by step
- Failure mode (what if cache down? DB slow?)
- Consistency choice (read-your-writes?)
- One algorithm or data structure if relevant (consistent hash, sliding window)

Do **not** deep-dive every technology logo on the diagram.

---

## Step 6 — Bottlenecks and scaling

Name **one bottleneck first**, then levers:

| Bottleneck | Common levers |
|------------|---------------|
| DB CPU/IO | Read replicas, sharding, denormalize |
| Hot keys | Split key, local cache, precompute |
| Fan-out | Pull vs push, hybrid, batch |
| Large payloads | CDN, pagination, compression |
| Spiky traffic | Queue, rate limit, auto-scale |

### Say this aloud

> “The first bottleneck is probably the primary DB on write QPS. I’d add read replicas for reads, shard by user_id when we cross single-node limits, and offload heavy work to async consumers.”

---

## Step 7 — Wrap-up (60–90 seconds)

Template:

1. One sentence problem
2. Architecture in one breath
3. Main trade-off you chose
4. What you’d build in phase 2

---

## Vocabulary cheat sheet (sprinkle in answers)

| Term | Simple meaning |
|------|----------------|
| Stateless service | Any server can handle any request; session in DB/Redis |
| Vertical scale | Bigger machine |
| Horizontal scale | More machines + partition work |
| Replication | Copies of data for read scale or failover |
| Sharding | Split data by key across DBs |
| Idempotent | Same request twice = same effect as once |
| At-least-once | Message may duplicate; consumer must dedupe |
| Exactly-once | Hard; usually “effectively once” with idempotency |
| Strong consistency | All readers see latest write (harder, slower) |
| Eventual consistency | Replicas converge later (faster, simpler) |
| p99 latency | 99% of requests faster than this |
| Thundering herd | Many clients hit origin when cache expires |

---

## Time boxing (45-min round)

```text
0–5   clarify
5–10  estimate
10–20 high-level diagram + APIs
20–38 deep dives (interviewer-led)
38–43 scale + failures
43–45 summary
```

If you talk too long on step 3, say: **“I’ll go deeper on X unless you prefer Y.”**

---

## Common mistakes (5.5 YOE)

1. **Jumping to microservices** — start monolith + clear modules; split when needed.
2. **No numbers** — always one QPS or storage line.
3. **Magic “we’ll use Kafka”** — name *why* (decouple, buffer, replay).
4. **Ignoring failures** — mention retry, timeout, dead letter queue.
5. **No trade-off** — every choice has a cost; say it.

---

## “Say this aloud” — open any design (45 sec)

> “I’ll treat this as a product with clear v1 scope. I want to confirm functional features and non-functional targets like scale, latency, and consistency. Then I’ll estimate traffic and storage with simple assumptions. I’ll draw a high-level path from client through load balancer to stateless services, cache, and database, plus async processing if we need it. We can sketch APIs and core tables, then deep-dive the riskiest parts—usually the hot read path, consistency, or sharding. I’ll close with bottlenecks and how I’d evolve the design in phase two.”

---

## Follow-up questions you should invite

- “Should I optimize for read latency or write consistency?”
- “Is multi-region required for v1?”
- “Do we need strong ordering for messages?”

Asking **one** good question beats drawing ten extra boxes.