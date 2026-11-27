# 01 — Thread vs Process and Why Concurrency

---

## Mental picture

```text
PROCESS (JVM = one OS process usually)
┌─────────────────────────────────────────────┐
│  Heap: shared objects, static fields       │
│  ┌──────┐ ┌──────┐ ┌──────┐                 │
│  │Thread│ │Thread│ │Thread│  each has own   │
│  │ stack│ │ stack│ │ stack│  stack + PC     │
│  └──────┘ └──────┘ └──────┘                 │
└─────────────────────────────────────────────┘

OS schedules THREADS (lighter than new processes).
Switching threads = context switch (save registers, pick next runnable).
```

**One JVM** ≈ **one process**, **many threads** inside it sharing the heap.

---

## Why it matters

- **Throughput:** While one thread waits on IO (HTTP, DB, disk), others can use CPU.  
- **Responsiveness:** UI or gateway keeps accepting work while workers run.  
- **Cost:** Threads are cheaper than processes but **not free** — stacks (~1MB default), scheduling, contention on shared data.  
- **At 30+ LPA** they expect you to say *when not to parallelize* (small CPU work, already saturated pool, too much locking).

---

## Small code snippet

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> System.out.println("Worker on " + Thread.currentThread().getName()));
        t.start();
        t.join(); // main waits until worker finishes
    }
}
```

---

## Common mistakes

| Mistake | Reality |
|---|---|
| "Threads always make code faster" | Overhead + locks can make it slower |
| "Each request = new thread is fine" | Exhausts memory and OS limits; use pools |
| Confusing parallelism with concurrency | Concurrent = tasks overlap; parallel = same instant on multiple cores |
| Ignoring single-threaded models | Event loops (Netty) or virtual threads change the tradeoff |

---

## Say this aloud

*"A process owns memory and resources; threads inside a process share the heap but have private stacks. I use concurrency when work blocks on IO or when independent tasks can overlap, but I size thread usage carefully — in services I prefer thread pools or async APIs instead of unbounded `new Thread`. The hard part isn't starting threads; it's safe shared state and visibility."*

---

## Follow-ups

1. **What is a context switch?** — OS pauses one thread, restores another; microseconds, but adds up under load.  
2. **User thread vs platform thread?** — Platform thread = 1:1 with OS thread; virtual threads (Java 21+) are cheap, mount on carrier threads.  
3. **Is Java memory model per thread or per process?** — Per JVM process; threads share heap.  
4. **How does this relate to microservices?** — Each service is often a process; concurrency is **inside** the JVM (pools, async HTTP clients).