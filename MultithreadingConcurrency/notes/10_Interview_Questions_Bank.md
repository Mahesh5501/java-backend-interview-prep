# 10 — Interview Questions Bank (Concurrency)

Practice **out loud**. Aim for 45–90 second answers unless they ask to go deeper.

---

## Tier 1 — Must nail

| # | Question | Anchor note |
|---|---|---|
| 1 | Thread vs process? When use threads? | 01 |
| 2 | Runnable vs Callable? | 02 |
| 3 | What is a race condition? Example? | 03 |
| 4 | `synchronized` vs `volatile`? | 03, 04 |
| 5 | What is happens-before? | 04, Roadmap JMM |
| 6 | Why is `i++` not atomic? | 03, 04 |
| 7 | ConcurrentHashMap vs HashMap + sync? | 05 |
| 8 | Why use thread pool? How size? | 06 |
| 9 | CompletableFuture vs Future? | 07 |
| 10 | Explain deadlock. How prevent? | 08 |

---

## Tier 2 — Senior signal

| # | Question | Say-this hook |
|---|---|---|
| 11 | Double-checked locking — why volatile? | Publishes fully constructed object |
| 12 | Fair vs unfair `ReentrantLock`? | Throughput vs starvation |
| 13 | `CallerRunsPolicy` — when? | Backpressure on caller |
| 14 | `parallelStream` pitfalls? | Common pool, blocking tasks, ordering |
| 15 | Virtual threads — what problem? | Cheap blocking IO concurrency |
| 16 | `ThreadLocal` — use and leak risk? | Pool threads + remove after use |
| 17 | `CountDownLatch` vs `CyclicBarrier` vs `Phaser`? | One-shot vs reusable vs phases |
| 18 | AtomicXXX vs lock? | Single var CAS vs multi-field invariant |
| 19 | How debug stuck prod threads? | `jstack`, thread dump, BLOCKED/WAITING |
| 20 | Design rate limiter (sketch)? | CHM + atomics or token bucket per key |

---

## Scenario questions (story format)

### S1 — Counter wrong in production

**Prompt:** API hit counter is lower than expected under load.

**Say this aloud:**  
*"I'd suspect lost updates on a non-atomic read-modify-write. I'd reproduce with a stress test, confirm with JFR or logging, then fix with AtomicLong or synchronized increment on the hot path. I'd verify metrics match expected QPS and watch lock contention afterward."*

### S2 — Service hangs on deploy

**Prompt:** App won't exit after shutdown hook.

**Say this aloud:**  
*"Non-daemon threads from an ExecutorService still running — I'd check for missing shutdown, blocked tasks, or consumers stuck in take(). I'd use graceful shutdown with timeout, then shutdownNow and log incomplete tasks."*

### S3 — Cache stampede

**Prompt:** Many threads load same key on cache miss.

**Say this aloud:**  
*"computeIfAbsent serializes per key but the loader is still expensive if many keys miss at once. I'd add per-key locking, single-flight pattern, or pre-warm. For TTL expiry I'd use staggered refresh or background updater."*

### S4 — Inter-service fan-out latency

**Prompt:** Call 5 downstream APIs, need p99 control.

**Say this aloud:**  
*"CompletableFuture allOf on a dedicated IO pool, per-call timeouts, exceptional fallbacks, and an overall deadline. I avoid blocking on the servlet thread if the stack is async-capable."*

---

## Rapid-fire follow-ups (flash cards)

```text
Q: Can volatile replace synchronized for a counter?
A: No — increment is compound; use AtomicInteger or sync.

Q: Does CHM lock the whole map?
A: No — bin-level; independent keys scale better.

Q: What does join() establish?
A: Happens-before: thread actions visible to caller after join.

Q: Poison pill pattern?
A: Sentinel on queue tells consumers to exit cleanly.

Q: Three states you see in thread dump?
A: RUNNABLE, BLOCKED (monitor), WAITING/TIMED_WAITING.
```

---

## Whiteboard drill (5 min each)

1. Draw producer–consumer with bounded queue.  
2. Draw deadlock cycle with two locks.  
3. Draw happens-before: volatile write → read.  
4. Draw thread pool: queue + workers + rejection.  

---

## Common mistakes in interviews

- Only API names, no **failure mode** (visibility vs atomicity).  
- Claiming *"we'll just synchronize everything"* without contention cost.  
- No mention of **timeouts** on async/blocking calls.  
- Forgetting **bounded queues** in pool design.  

---

## Self-score rubric

| Score | Meaning |
|---|---|
| 0–2 | Named concept only |
| 3–4 | Correct mechanism + one example |
| 5 | Mechanism + tradeoff + production story + follow-up ready |

Re-run demos in `../java/` and narrate **what could go wrong** if you removed each fix.