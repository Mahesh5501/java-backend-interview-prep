# Caching (Deep Dive Lite)

Caching is how you turn **slow DB reads** into **millisecond** responses.

---

## Cache-aside (most common)

```text
read(key):
  v = cache.get(key)
  if v: return v
  v = db.get(key)
  cache.set(key, v, TTL)
  return v

write(key):
  db.write(...)
  cache.delete(key)    // or update
```

**Why delete on write?** Avoid stale data if update bypasses cache logic.

---

## Problems and fixes

| Problem | Fix |
|---------|-----|
| Cache stampede | TTL jitter, single-flight lock, early recompute |
| Hot key | Local cache layer, key splitting, read replicas |
| Cold start | Warm cache on deploy, gradual rollout |
| Stale data | Shorter TTL, versioning, event invalidation |

---

## Where to cache

```text
Browser ──► CDN ──► API gateway ──► App local cache ──► Redis ──► DB
         (static)              (per JVM)      (shared)
```

**Local (Caffeine):** microsecond; inconsistent across nodes.  
**Redis:** shared; network hop ~1 ms LAN.

---

## What not to cache

- Highly personalized secrets (PII) without encryption
- Results that must be **strongly consistent** (fresh balance) — or cache very carefully with invalidation

---

## “Say this aloud” (40 sec)

> “I use cache-aside. The app checks Redis first; on miss it loads from the database and sets a TTL. On writes I update the database and invalidate the cache key so we don’t serve stale data. For viral keys I’d add a short local cache or split the key. I accept eventual staleness only where product allows it.”

---

## Follow-up Q&A

**Q: Write-through vs write-behind?**  
A: Write-through = simpler consistency. Write-behind = faster writes, risk of loss on crash.

**Q: Redis down?**  
A: Fallback to DB with circuit breaker; degrade latency not correctness.

**Q: Cache eviction?**  
A: LRU common; TTL for time-bound freshness.