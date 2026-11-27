# 02 — Creating Threads: Runnable, Callable, and Better Defaults

---

## Mental picture

```text
Ways to give "work" to a thread:

  Runnable.run()     → void, cannot throw checked easily to caller
  Callable.call()    → returns T, throws Exception
        │
        ▼
  Thread.start()  OR  ExecutorService.submit(callable)
```

**Rule of thumb:** Never use raw `Thread` in application code except demos — use **`ExecutorService`**.

---

## Why it matters

- Interviews test whether you know **lifecycle**: `NEW → RUNNABLE → BLOCKED/WAITING → TERMINATED`.  
- **`Callable` + `Future`** = "run later, get result or exception".  
- Spring `@Async`, HTTP clients, and batch jobs all sit on top of the same ideas.

---

## Small code snippet

```java
ExecutorService pool = Executors.newFixedThreadPool(2);

// Runnable — fire and forget
pool.execute(() -> System.out.println("task"));

// Callable — result
Future<Integer> future = pool.submit(() -> 40 + 2);
int answer = future.get(); // blocks until done

pool.shutdown();
```

`Thread` directly (know it, rarely use it):

```java
Thread t = new Thread(() -> { /* work */ });
t.start();
```

---

## Common mistakes

| Mistake | Fix |
|---|---|
| Calling `run()` instead of `start()` | `run()` runs on **current** thread — no new thread |
| No `shutdown()` on pools | JVM may hang; threads keep process alive |
| `Future.get()` with no timeout | Blocks forever if task stuck |
| Creating `Executors.newCachedThreadPool()` unbounded in prod | Can create thousands of threads under spike |

---

## Say this aloud

*"Runnable is void work; Callable returns a value and fits `submit`, which gives a Future. I start work with an ExecutorService instead of raw Thread so I get pooling, naming, and rejection policies. I always shut down the pool and use timeouts on `get` in production code."*

---

## Follow-ups

1. **Difference between `execute` and `submit`?** — `execute` void; `submit` returns `Future` and wraps Runnable in a Callable internally.  
2. **Checked exception from Callable?** — Surfaces as `ExecutionException` on `get()`.  
3. **Can you restart a Thread?** — No; once terminated, dead. Create new task on pool.  
4. **Daemon threads?** — JVM exits when only daemons left; use for background helpers, not business-critical shutdown logic.