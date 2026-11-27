# 09 — Producer–Consumer with BlockingQueue

---

## Mental picture

```text
  Producers ──put──► ┌──────────────────┐ ──take──► Consumers
                     │  BlockingQueue   │
                     │  [job][job][ . ] │
                     └──────────────────┘
                           │
                     blocks if full (put)
                     blocks if empty (take)
```

**Replaces hand-rolled `wait/notify`** for bounded buffers — fewer missed signals.

---

## Why it matters

- Order pipelines, log ingest, worker queues behind HTTP — classic **pattern** question.  
- Ties to **backpressure**: full queue slows producers instead of unbounded memory.

---

## Small code snippet

```java
BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

// Producer
queue.put("order-42"); // blocks when full

// Consumer
String job = queue.take(); // blocks when empty

// Non-blocking option
boolean offered = queue.offer("x", 100, TimeUnit.MILLISECONDS);
```

**Graceful stop:** poison pill sentinel value or `volatile shutdown` + `poll` with timeout.

---

## Common mistakes

| Mistake | Fix |
|---|---|
| Unbounded queue in memory | `ArrayBlockingQueue` with bounded capacity |
| `add()` throws when full | Use `put`/`offer` |
| Consumer swallows interrupt | Restore interrupt flag or exit loop |
| Multiple consumers on one queue without idempotency | Design for at-least-once |

---

## Say this aloud

*"BlockingQueue gives me a bounded buffer between producers and consumers. put blocks when full so I get natural backpressure; take blocks when empty so consumers don't spin. I pick ArrayBlockingQueue for a fixed bound or LinkedBlockingQueue when I still cap capacity explicitly. For shutdown I use poison pills or interrupt-aware loops with poll and timeout."*

---

## Follow-ups

1. **wait/notify vs BlockingQueue?** — BQ is safer, tested, handles spurious wakeup internally.  
2. **PriorityBlockingQueue?** — Ordered by priority, unbounded — watch memory.  
3. **DelayQueue?** — Scheduled work.  
4. **Disruptor?** — High-performance ring buffer — mention if low-latency context.

**Demo:** `../java/DemoProducerConsumer.java`