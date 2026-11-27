# SQL vs NoSQL

**Default in interviews:** Start with **SQL** unless the access pattern clearly fights it.

---

## SQL (relational)

**Strengths:** ACID transactions, joins, constraints, ad-hoc queries.

**Weaknesses:** Harder to scale writes on one giant node; rigid schema migrations at huge scale.

**Use when:** Money, orders, users, anything needing **integrity**.

---

## NoSQL families

| Type | Example | Good for |
|------|---------|----------|
| Document | MongoDB | Flexible JSON docs, content |
| Wide-column | Cassandra | Huge write scale, time series |
| Key-value | Redis, DynamoDB | Simple K/V, session, metadata |
| Graph | Neo4j | Friends-of-friends, recommendations |

---

## Decision flow (ASCII)

```text
Need multi-row ACID transaction?
        │
       yes ──► SQL
        │
       no
        ▼
Fixed access pattern + huge scale?
        │
       yes ──► Wide-column / KV
        │
       no
        ▼
Flexible nested documents?
        │
       yes ──► Document
```

---

## Polyglot persistence (senior answer)

One system, multiple stores:

- **SQL** — users, wallet, orders
- **Redis** — cache, rate limit counters
- **Elasticsearch** — search
- **S3** — images

Sync via **app logic** or **change capture / queue** to search index.

---

## Sharding (when SQL is not enough)

**Shard key:** high cardinality (`user_id`).

**Problems:** cross-shard joins, rebalancing hotspots.

**Say:** “We design queries per shard; avoid cross-shard transactions in v1.”

---

## “Say this aloud” (50 sec)

> “I start with SQL for anything that needs transactions and clear relationships—users, wallets, orders. I add Redis for cache and rate limits. If we have a clear access pattern at very high write volume—like per-user time series—I’d consider Cassandra or a managed wide-column store. Search goes to an async index. The rule is: SQL for truth, specialized stores for access patterns SQL can’t serve cheaply.”

---

## Follow-up Q&A

**Q: SQL vs NewSQL (Spanner)?**  
A: Global strong consistency + scale; cost and ops complexity; fine to mention for global products.

**Q: ORM in design?**  
A: Mention schema and indexes; ORM is implementation detail.

**Q: Normalization vs denormalization?**  
A: Normalize for writes consistency; denormalize for read speed when proven hot path.