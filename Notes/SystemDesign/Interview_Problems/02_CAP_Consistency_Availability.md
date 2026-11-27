# CAP, Consistency, and Availability

Simple mental model for interviews. No PhD required.

---

## CAP theorem (one sentence)

In a **network partition**, you cannot have all three at once:

- **C** — Consistency (every read sees latest write)
- **A** — Availability (every request gets a response)
- **P** — Partition tolerance (network splits happen)

**Real world:** Partitions happen. So you choose **CP** or **AP** during the split.

```text
        Partition happens
               │
       ┌───────┴───────┐
       ▼               ▼
      CP              AP
  reject some      return maybe
  requests         stale data
  (stay consistent) (stay up)
```

---

## Consistency levels (practical)

| Level | Meaning | Example |
|-------|---------|---------|
| Strong | Read always latest write | Bank balance debit |
| Sequential | Order agreed cluster-wide | Zookeeper leader election |
| Causal | Related ops seen in order | “Reply after parent comment” |
| Read-your-writes | You see your own update | Edit profile then view |
| Eventual | Replicas catch up later | Like count, follower count |

**Interview default:** Money = strong (or very careful). Feeds/social = eventual often OK.

---

## Availability vs consistency trade-off

| Choose consistency | Choose availability |
|---------------------|---------------------|
| Payments, inventory lock | News feed, view counts |
| Leader election | DNS-ish lookup |
| Small critical config | Social likes |

---

## Patterns that help

**Quorum reads/writes (Dynamo style):** W + R > N → likely fresh read.

**Primary + replicas:** Writes to primary; reads from replica (lag).

**Distributed transactions / 2PC:** Strong but slow and fragile — avoid in huge scale interviews unless asked.

**Sagas:** Multi-step business flow with compensating steps (cancel shipment if payment fails).

**Idempotency keys:** Make retries safe under at-least-once delivery.

---

## “Say this aloud” (45 sec)

> “CAP says during a network split we can’t be fully consistent and fully available. In practice partitions happen, so we pick our trade-off. For payments I want strong consistency on the ledger—maybe reject or queue rather than show wrong money. For a news feed, eventual consistency is fine if we target seconds of lag and read-your-writes for the author. I’ll say which paths need strong guarantees and which can use replicas plus cache.”

---

## Follow-up Q&A

**Q: Is CAP outdated?**  
A: It’s a teaching frame. Real systems use tunable consistency per operation.

**Q: How do you get read-your-writes with replicas?**  
A: Route that user’s reads to primary briefly, or sticky session, or version check.

**Q: Kafka and consistency?**  
A: Ordering within a partition; consumers at-least-once unless you design idempotency.