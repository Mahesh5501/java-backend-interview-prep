# 04 — volatile, Happens-Before, and Visibility

---

## Mental picture

```text
Thread 1 (CPU core A)          Main memory           Thread 2 (CPU core B)
     │                              │                      │
     │  write flag=true             │                      │
     │  (may sit in cache) ────────►│                      │
     │                              │  read flag           │
     │                              │◄──── still false?    │
     │                              │      (stale!)        │

volatile write ──flush──► other threads' read sees latest value
(for THAT variable — ordering rules apply)
```

**Two different bugs:**

1. **Visibility** — B never sees A's write.  
2. **Atomicity** — `count++` is three steps; volatile does not fix that.

---

## Why it matters

- **Shutdown flags**, **publication of immutable config**, **double-checked locking** (with `volatile` on reference).  
- Without this, "it works on my laptop" fails under load on multi-core servers.

---

## JVM Memory Model (practical, not academic)

Interview-level bullets:

- Compiler and CPU may **reorder** instructions if spec allows.  
- Each thread may keep working copies of fields in **cache**.  
- The JMM defines **happens-before** edges; if A hb B, B sees A's writes.  
- **`synchronized`** — unlock hb next lock. **`volatile`** — write hb subsequent read of that volatile.

**Double-checked locking (correct pattern):**

```java
class Holder {
    private volatile Holder instance; // volatile required

    Holder getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new Holder();
                }
            }
        }
        return instance;
    }
}
```

Without `volatile`, another thread might see a partially constructed object.

---

## Small code snippet

```java
class Worker {
    private volatile boolean shutdown = false;

  public void run() {
        while (!shutdown) {
            doWork();
        }
    }

    public void stop() {
        shutdown = true; // writer — readers should see this
    }
}
```

For a **status flag**, volatile is often enough. For **counters**, use `AtomicLong` or locks.

---

## Common mistakes

| Mistake | Truth |
|---|---|
| "volatile makes everything thread-safe" | Only visibility + ordering for that var; not `i++` |
| Using volatile on Long/double without care | On 32-bit JVM historically torn reads; on modern 64-bit usually OK but atomics clearer |
| No happens-before between threads | Need sync, volatile, concurrent API, or thread start/join |
| `System.out.println` for "sync" | Not a memory barrier for your fields |

---

## Say this aloud

*"volatile guarantees that a write is visible to subsequent reads by other threads via happens-before. It does not make compound actions like increment atomic. When I have a single writer flag or publishing a fully constructed immutable object reference, volatile fits. When I update multiple fields or need read-modify-write, I use synchronization or atomics."*

---

## Follow-ups

1. **Difference vs synchronized?** — volatile: no mutual exclusion; synchronized: both visibility and exclusion.  
2. **final fields?** — Safe publication after constructor completes if `this` doesn't escape during construction.  
3. **`ConcurrentHashMap` visibility?** — Uses internal volatile/unsafe mechanics; use its API, don't wrap with extra sync blindly.  
4. **VarHandle / Unsafe?** — Advanced; mention you stick to j.u.c. in production.

**Demo:** `../java/DemoVolatileVisibility.java`