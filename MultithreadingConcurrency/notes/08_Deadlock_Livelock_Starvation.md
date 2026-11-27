# 08 — Deadlock, Livelock, and Starvation

---

## Mental picture

**Deadlock** — circular wait, nobody moves:

```text
Thread 1: lock A ──waiting──► lock B
Thread 2: lock B ──waiting──► lock A
          ═══════════════════════════
                    DEADLOCK
```

**Livelock** — threads keep reacting, no progress (both step aside forever).  
**Starvation** — one thread never gets CPU/lock (unfair lock, low priority).

---

## Why it matters

- Production incidents: payment + inventory locks in wrong order, distributed deadlock (2 PC) — JVM version is the **interview template**.  
- Seniors must list **prevention** without hand-waving.

---

## Small code snippet (anti-pattern)

```java
synchronized (lockA) {
    synchronized (lockB) { /* work */ }
}
// another thread: lockB then lockA → deadlock risk
```

**Fix — lock ordering:**

```java
Object first = System.identityHashCode(a) < System.identityHashCode(b) ? a : b;
Object second = (first == a) ? b : a;
synchronized (first) {
    synchronized (second) { /* work */ }
}
```

Better: **one lock**, **tryLock with timeout**, **narrow critical sections**.

---

## How to avoid deadlock (interview checklist)

1. **Global lock order** — always acquire A before B.  
2. **tryLock(timeout)** — back off and retry/log (`ReentrantLock`).  
3. **Shrink critical sections** — less overlap time.  
4. **Avoid nested locks** — design lock-free or single-writer paths.  
5. **Detect in ops** — thread dumps (`jstack`), `ThreadMXBean.findDeadlockedThreads()`.

---

## Common mistakes

| Mistake | Reality |
|---|---|
| "synchronized can't deadlock" | It can, with two monitors |
| Only naming deadlock | Also define livelock & starvation |
| Holding lock while calling external service | Huge deadlock + latency surface |

---

## Say this aloud

*"Deadlock is circular waiting on locks. I prevent it by consistent lock ordering, timeouts with tryLock, and avoiding holding locks across IO. Livelock is active but useless work — like both threads yielding forever. Starvation is unfair scheduling or a biased lock — I might use fair locks or separate pools. In incidents I'd capture thread dumps and look for BLOCKED cycles."*

---

## Follow-ups

1. **Four Coffman conditions?** — Mutual exclusion, hold-and-wait, no preemption, circular wait — break one.  
2. **Database deadlock vs JVM?** — DB detects and rolls back; JVM needs tooling.  
3. **Lock-free structures?** — `ConcurrentLinkedQueue`, atomics — fewer locks, harder reasoning.

**Demo:** `../java/DemoDeadlock.java` (intentional) + comments on avoidance.