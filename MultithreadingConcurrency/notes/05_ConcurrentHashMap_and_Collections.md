# 05 — ConcurrentHashMap and Concurrent Collections

---

## Mental picture

```text
HashMap + many threads = broken bucket chains / infinite loops (old JDK) / lost updates

ConcurrentHashMap:
  partition buckets (segments / nodes)
  CAS + synchronized on bin heads (modern CHM)

  Thread 1 ──► bucket 3 (lock/CAS)
  Thread 2 ──► bucket 7 (parallel)
  Thread 3 ──► bucket 3 (may wait on same bin)
```

**Concurrent collections** = iterate safely **weakly** (no `ConcurrentModificationException`) but iteration is a **snapshot** view, not a global lock.

---

## Why it matters

- Caches, rate limiters, session maps in multi-threaded servers.  
- Wrong choice: `Collections.synchronizedMap` wraps every operation — **one big lock**.  
- `ConcurrentHashMap` = higher concurrency for **independent keys**.

---

## Small code snippet

```java
ConcurrentHashMap<String, Integer> hits = new ConcurrentHashMap<>();

hits.put("api/users", 1);
hits.merge("api/users", 1, Integer::sum); // atomic per-key update

// computeIfAbsent — popular for caches (watch stampede)
hits.computeIfAbsent("expensiveKey", k -> loadFromDb(k));
```

Other types to name in interviews:

| Class | Use |
|---|---|
| `ConcurrentLinkedQueue` | Unbounded MPMC queue |
| `CopyOnWriteArrayList` | Rare writes, frequent iteration (listeners) |
| `BlockingQueue` implementations | Producer–consumer (see note 09) |

---

## Common mistakes

| Mistake | Fix |
|---|---|
| `if (!map.containsKey(k)) map.put(k, v)` | Race — use `putIfAbsent` / `computeIfAbsent` |
| Assuming `size()` is exact under concurrency | Approximate / stale; don't use for strict accounting without lock |
| Iterating and modifying plain `HashMap` | Use CHM or external lock |
| Using CHM for **compound** multi-key transactions | Need higher-level locking or transactional store |

---

## Say this aloud

*"ConcurrentHashMap stripes work across buckets so independent keys scale better than a synchronized HashMap. For per-key atomic updates I use merge, computeIfAbsent, or replace. I know iteration is weakly consistent — it reflects the map at some point during the walk, not a frozen global snapshot unless I externalize a copy."*

---

## Follow-ups

1. **CHM vs synchronizedMap?** — Throughput under contention; CHM finer granularity.  
2. **Null keys/values in CHM?** — Not allowed (unlike HashMap).  
3. **Cache stampede on `computeIfAbsent`?** — Long loader blocks others on that key; consider async refresh or locking wrapper.  
4. **Java 8+ stream on CHM?** — `forEach` with parallelism still needs business sense.