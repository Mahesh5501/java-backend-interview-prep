# Message Queues (Deep Dive Lite)

Queues turn **slow or unreliable work** into **fast API responses**.

---

## Queue vs log (Kafka-ish)

| | Queue (Rabbit, SQS) | Log (Kafka) |
|---|---------------------|-------------|
| Model | Message consumed → gone (often) | Retained log, replay |
| Ordering | Per queue | Per partition |
| Use | Job processing | Event streaming, analytics |

---

## Reliable pattern

```text
BEGIN TX
  INSERT business_row
  INSERT outbox_event
COMMIT

Publisher ──reads outbox──► Queue ──► Workers
```

**Never** publish before commit — you get events without data.

---

## Consumer design

- **Idempotent** processing (`event_id` dedupe table)
- **Retry** with backoff
- **DLQ** for poison messages
- **Visibility timeout** (SQS) — same idea as ack deadline

```text
Worker receives msg
  │
  ├── success ──► ack
  ├── transient fail ──► retry (n times)
  └── permanent fail ──► DLQ + alert
```

---

## When to use

| Use queue | Skip queue |
|-----------|------------|
| Email, SMS | Simple sync CRUD |
| Search index update | User must see result in same HTTP response |
| Webhooks to partners | Tiny traffic |
| Fan-out notifications | |

---

## “Say this aloud” (45 sec)

> “I commit business state in the database first, then publish an event—ideally with an outbox so publish and insert share one transaction. Workers consume at-least-once, so handlers are idempotent using a processed-event table. Failed messages retry with backoff; poison pills go to a dead letter queue for ops.”

---

## Follow-up Q&A

**Q: Ordering needed?**  
A: Single partition or single consumer per key; trade parallelism.

**Q: Backpressure?**  
A: Queue depth metrics; scale consumers; rate limit producers.

**Q: Exactly-once?**  
A: Hard end-to-end; idempotent consumer + transactional outbox is the practical answer.