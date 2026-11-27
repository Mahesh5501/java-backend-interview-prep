# Design: Rate Limiter

Protect APIs from abuse and **smooth traffic**. Often **Redis + sliding window**.

---

## 1. Clarify requirements

### Functional

| In scope | Out of scope |
|----------|--------------|
| Limit requests per user / IP / API key | Global DDoS (edge/WAF) |
| Return 429 when over limit | Dynamic per-tenant billing rules |
| Configurable rules (100 req/min) | UI for ops (v2) |

### Non-functional

- **Latency overhead:** < 5 ms per request
- **Accuracy:** “good enough” (allow small burst)
- **Distributed:** Many app servers share one limit
- **Availability:** Fail open vs closed? (ask: **fail open** for revenue APIs often; **closed** for auth)

---

## 2. Capacity estimate

- **1M DAU**, 50 API calls/day → 50M/day → **500 avg QPS**, **~1.5K peak**
- Each check: 1–2 Redis ops → Redis handles **100K+ ops/sec** on one cluster (roughly)

Storage:

- Counter per key per window bucket: millions of keys, TTL auto-expire → **GB scale**, not TB

---

## 3. High-level diagram

```text
 Client
    │
    ▼
 [API Gateway / LB]
    │
    ▼
 [Rate Limit middleware] ──► [Redis cluster]
    │         allow/deny
    ▼
 [App services]
```

Alternative: limit at **gateway** only vs **per service** — gateway is simpler v1.

```text
Request ──► extract key (user_id / IP / api_key)
         ──► Redis INCR or sliding window
         ──► if count > limit → 429 + Retry-After
         ──► else forward
```

---

## 4. API sketch

Internal (not public):

```http
POST /internal/ratelimit/check
Body: { "key": "user:123", "rule": "default", "cost": 1 }
Response: { "allowed": true, "remaining": 42, "reset_at": "..." }
```

Client sees normal API; on exceed:

```http
HTTP/1.1 429 Too Many Requests
Retry-After: 12
Body: { "error": "RATE_LIMIT_EXCEEDED" }
```

Rules config (v2):

```http
PUT /admin/rules/{name}
Body: { "limit": 100, "window_sec": 60, "scope": "user" }
```

---

## 5. Data model sketch

Redis keys (no SQL required v1):

```text
rl:{rule}:{key}:{window_bucket}  →  integer count
TTL = window length
```

Optional SQL for audit:

**Table: `rate_limit_rules`**

| rule_name | limit | window_sec | scope |

---

## 6. Deep dive

### A. Algorithms (simple)

| Algorithm | Idea | Pros/cons |
|-----------|------|-----------|
| Fixed window | Count per minute bucket | Burst at boundary |
| Sliding window log | Store timestamps | Accurate, more memory |
| **Sliding window counter** | Weighted prev+curr bucket | Good balance |
| Token bucket | Refill tokens at rate | Smooth bursts |

**Interview favorite:** sliding window counter or token bucket.

**Token bucket (speak):**

```text
tokens = min(capacity, tokens + (now - last) * rate)
if tokens >= cost: tokens -= cost; allow
else: deny
```

### B. Distributed Redis

- All nodes use same Redis → consistent count
- **Lua script** or `INCR` + `EXPIRE` atomic
- Redis Cluster hash tag `{user:123}` keep related keys on one slot

### C. Fail open vs fail closed

- Redis timeout → log + **allow** (availability) or **deny** (security)
- State your choice with reason

---

## 7. Bottlenecks & scaling

| Issue | Fix |
|-------|-----|
| Redis hotspot | Shard by key; local token bucket + async sync (advanced) |
| Global limit accuracy | Accept ~1% error or use centralized cell per rule |
| Multi-region | Per-region limits or CRDT-style counters (complex) |

---

## 8. “Say this aloud” (70 sec)

> “I put rate limiting at the API gateway or a shared filter before business logic. Each request gets a key—user ID, API key, or IP. I store counters in Redis with a sliding window or token bucket so limits are shared across all app instances. A typical check is one atomic increment with TTL under five milliseconds. When over limit we return 429 with Retry-After. I use Lua for atomicity. If Redis is down I’d fail open for product APIs but log heavily, or fail closed for login endpoints. Rules live in config and map to Redis key patterns.”

---

## 9. Follow-up Q&A

**Q: Different limits per endpoint?**  
A: Key `rl:endpoint:{name}:{user}` or weighted `cost` per route.

**Q: Bypass for internal traffic?**  
A: mTLS service identity + separate rule class.

**Q: Comparison to WAF?**  
A: WAF at edge for L7 attacks; rate limiter for app quotas.

**Q: Java implementation hint?**  
A: Servlet filter / Spring interceptor; Bucket4j with Redis proxy.