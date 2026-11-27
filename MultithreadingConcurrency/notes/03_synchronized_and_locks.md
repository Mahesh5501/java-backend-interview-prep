# 03 — synchronized and Locks

---

## Mental picture

```text
Many threads ──► one counter (shared mutable state)

Without lock:
  read count=5, read count=5, both write 6  → LOST UPDATE

With synchronized / Lock:
  only ONE thread inside critical section at a time

    Thread A ████████░░░░░░░░
    Thread B ░░░░░░░░████████
             ↑ monitor / lock held
```

**`synchronized`** = intrinsic lock on object (monitor).  
**`ReentrantLock`** = explicit lock; tryLock, fairness, `Condition`.

---

## Why it matters

- Every billing counter, inventory decrement, session cache update needs **mutual exclusion** or **atomics**.  
- Senior interviews: **reentrancy**, **lock granularity**, **don't synchronize on wrong object**.

---

## Small code snippet

```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++; // read-modify-write — must be atomic as a unit
    }

    public synchronized int get() {
        return count;
    }
}
```

Explicit lock:

```java
private final ReentrantLock lock = new ReentrantLock();

public void increment() {
    lock.lock();
    try {
        count++;
    } finally {
        lock.unlock(); // ALWAYS in finally
    }
}
```

**Prefer `AtomicInteger`** when the only operation is a single variable:

```java
private final AtomicInteger count = new AtomicInteger();

public void increment() {
    count.incrementAndGet();
}
```

---

## Common mistakes

| Mistake | Why it hurts |
|---|---|
| Synchronizing on `Integer` or boxed values | Different objects → no mutual exclusion |
| Synchronizing only **part** of read-modify-write | Still racy |
| Huge `synchronized` methods | Contention; split or use finer locks |
| Forgetting `unlock()` | Use try/finally |
| Thinking `volatile` replaces sync for `i++` | volatile ≠ atomic compound ops |

---

## Say this aloud

*"synchronized gives mutual exclusion and establishes happens-before between unlock and the next lock on the same monitor. For simple counters I might use AtomicInteger; for multi-field invariants I hold a lock while all related fields are updated together. ReentrantLock helps when I need tryLock, timeouts, or multiple Conditions."*

---

## Follow-ups

1. **Is synchronized reentrant?** — Yes; same thread can re-enter.  
2. **`static synchronized`?** — Lock on Class object.  
3. **ReadWriteLock?** — Many concurrent readers, one writer — good for caches.  
4. **StampedLock?** — Optimistic reads when contention is low.

**Demo:** `../java/DemoSynchronizedCounter.java`