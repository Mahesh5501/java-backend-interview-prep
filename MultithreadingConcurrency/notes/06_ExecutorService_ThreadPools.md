# 06 — ExecutorService and Thread Pools

---

## Mental picture

```text
                    ┌─────────────────┐
  tasks queue ────► │  Thread Pool    │ ────► Worker 1
      ▲             │  (fixed 4)      │ ────► Worker 2
      │             │                 │ ────► Worker 3
  submit()          └─────────────────┘ ────► Worker 4
  reject if full ──► (RejectedExecutionHandler)
```

**You submit work; the pool reuses threads** instead of paying `new Thread()` cost per task.

---

## Why it matters

- Spring `@Async`, Tomcat worker pool, `@Scheduled`, batch frameworks — all pools.  
- Senior round: **sizing**, **queue type**, **graceful shutdown**, **CallerRunsPolicy** backpressure.

---

## Small code snippet

```java
int cores = Runtime.getRuntime().availableProcessors();
ThreadPoolExecutor pool = new ThreadPoolExecutor(
        cores,
        cores * 2,
        60, TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(100),
        new ThreadPoolExecutor.CallerRunsPolicy()
);

try {
    pool.submit(() -> callExternalApi());
} finally {
    pool.shutdown();
    if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
        pool.shutdownNow();
    }
}
```

**Sizing rule of thumb (say it, then nuance):**

| Work type | Pool size hint |
|---|---|
| CPU-bound | ~`N` or `N+1` cores |
| IO-bound (HTTP, DB) | Higher — `N * (1 + wait/compute)` or measure |

---

## Common mistakes

| Mistake | Fix |
|---|---|
| `Executors.newFixedThreadPool` with unbounded `LinkedBlockingQueue` | Hidden queue growth → OOM |
| Never shutting down pool | Non-daemon threads keep JVM up |
| Same pool for CPU-heavy and blocking IO | Split pools — isolate failure domains |
| Ignoring `RejectedExecutionException` | Choose policy consciously |

---

## Say this aloud

*"ExecutorService decouples task submission from thread lifecycle. I prefer explicit ThreadPoolExecutor with a bounded queue and a named rejection policy so overload degrades predictably — CallerRuns slows the caller instead of dropping work. I shut down gracefully: shutdown, awaitTermination, then shutdownNow with a story for in-flight tasks."*

---

## Follow-ups

1. **ForkJoinPool?** — Work-stealing for divide-and-conquer (`parallelStream`, `CompletableFuture` default in some setups).  
2. **ScheduledThreadPoolExecutor?** — Fixed rate vs fixed delay.  
3. **Virtual threads (Java 21)?** — Huge concurrency for blocking IO; one per task can be OK with structured concurrency patterns.  
4. **How monitor?** — Queue size, active count, completed tasks — Micrometer/Actuator metrics.

**Demo:** `../java/DemoExecutorService.java`