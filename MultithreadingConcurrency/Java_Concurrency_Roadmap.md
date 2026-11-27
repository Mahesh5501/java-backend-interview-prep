# Java Concurrency Roadmap (30+ LPA interviews)

Study path for a **~5.5 YOE Java developer** who can **explain** threading, visibility, pools, and async — not just name APIs.

```text
JavaConcurrency/
├── Java_Concurrency_Roadmap.md     ← you are here
├── notes/                          ← read 01 → 10 in order
└── java/                           ← package JavaConcurrency; run demos after each note
```

## What interviewers want at 30+ LPA

| Layer | They probe for |
|---|---|
| **Basics** | Thread vs process, when concurrency helps, cost of context switch |
| **Safety** | Race conditions, `synchronized`, `Lock`, when `volatile` is enough |
| **JMM (practical)** | Visibility, happens-before, why DCL is tricky |
| **Libraries** | `ConcurrentHashMap`, `ExecutorService`, `CompletableFuture`, `BlockingQueue` |
| **Production** | Pool sizing, rejection policy, timeouts, deadlock avoidance |
| **Story** | One real bug you fixed (stale read, pool exhaustion, deadlock) |

## Study order (2–3 weeks, ~30–45 min/day)

| Day block | Note | Demo to run |
|---|---|---|
| 1–2 | `01_Thread_vs_Process_and_Why_Concurrency.md` | — |
| 2–3 | `02_Creating_Threads_Runnable_Callable.md` | — |
| 3–4 | `03_synchronized_and_locks.md` | `DemoSynchronizedCounter.java` |
| 4–5 | `04_volatile_happens_before_visibility.md` | `DemoVolatileVisibility.java` |
| 5–6 | `05_ConcurrentHashMap_and_Collections.md` | — |
| 6–7 | `06_ExecutorService_ThreadPools.md` | `DemoExecutorService.java` |
| 7–8 | `07_CompletableFuture_basics.md` | `DemoCompletableFuture.java` |
| 8 | `08_Deadlock_Livelock_Starvation.md` | `DemoDeadlock.java` |
| 9 | `09_Producer_Consumer_BlockingQueue.md` | `DemoProducerConsumer.java` |
| 10+ | `10_Interview_Questions_Bank.md` | Re-run all demos, explain aloud |

## How to use each note

Every note follows the same rhythm:

1. **Mental picture** — ASCII diagram you can redraw on a whiteboard  
2. **Why it matters** — production link (microservices, caches, batch jobs)  
3. **Small snippet** — copy-paste sized  
4. **Common mistakes** — what juniors say wrong  
5. **Say this aloud** — 30–60 second interview answer  
6. **Follow-ups** — what they ask next  

## JVM Memory Model (practical interview level)

You do **not** need to quote the JLS. You need this story:

```text
Each thread has:
  - stack (local vars, call frames)
  - CPU registers / L1 cache (very fast, private to that core)

Shared heap:
  - objects live here; many threads can point at same object

Problem:
  Thread A writes x=1 in its cache; Thread B may still read x=0 from
  main memory or its own cache → VISIBILITY bug (not always a "race"
  on one increment — sometimes you never see the write at all).
```

**Happens-before** (one sentence): *If action A happens-before B, then B is guaranteed to see A’s effects.*

Common sources of happens-before:

| Mechanism | Practical meaning |
|---|---|
| `synchronized` unlock → later lock on same monitor | Full mutex + flush to main memory |
| `volatile` write → later read of same var | Visibility for that field; no compound atomicity |
| `Thread.start()` | Actions before `start` visible to new thread |
| `Thread.join()` | Actions in thread visible after `join` returns |
| `ConcurrentHashMap` / `java.util.concurrent` | Documented ordering for their APIs |

**Say this aloud:**  
*"The JMM defines which cross-thread reads are legal. Without synchronization, the JVM and CPU can reorder or cache writes. I use synchronized, locks, volatile, or concurrent utilities when another thread must see consistent state. volatile fixes visibility for a single field; it does not make i++ atomic."*

Deep dive: note `04_volatile_happens_before_visibility.md`.

## Compile and run demos

From `JavaConcurrency/java/`:

```bash
javac *.java
java JavaConcurrency.DemoSynchronizedCounter
java JavaConcurrency.DemoVolatileVisibility
java JavaConcurrency.DemoExecutorService
java JavaConcurrency.DemoProducerConsumer
java JavaConcurrency.DemoCompletableFuture
java JavaConcurrency.DemoDeadlock
```

## Self-check before interviews

- [ ] Draw process vs threads in one diagram  
- [ ] Explain why `new Thread()` per request is a bad default in services  
- [ ] Show broken counter vs fixed (`synchronized` or `AtomicInteger`)  
- [ ] When `volatile` is enough vs when you need a lock  
- [ ] Size a fixed pool: CPU-bound vs IO-bound rule of thumb  
- [ ] Chain two async calls with `CompletableFuture` and handle timeout  
- [ ] Name three ways to avoid deadlock  
- [ ] Explain producer–consumer with `BlockingQueue` without saying "wait/notify" first  

## Related prep in this repo

- **DSA** — `PreparationGuide/DSA/` (algorithm rounds)  
- Add **System Design** notes when you link concurrency to caching, queues, and backpressure  

Good luck — explain it like you're teaching a teammate on a video call.