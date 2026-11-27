# Resilience Patterns

These topics belong together: **backend reliability / resilience** patterns used in APIs and microservices.

```text
ResiliencePatterns/
├── Caching/          → package Caching
├── RateLimiter/      → package RateLimiter
├── Retry/            → package Retry
├── CircuitBreaker/   → package CircuitBreaker
└── Idempotency/      → package Idempotency
```

## What each one is (simple)

| Folder | Idea in one line |
|---|---|
| **Caching** | Store recent/expensive results so you don’t recompute every time (e.g. LRU). |
| **RateLimiter** | Limit how many requests a client can make in a time window. |
| **Retry** | Try again after a temporary failure (with care — backoff). |
| **CircuitBreaker** | Stop calling a failing dependency for a while so the system can recover. |
| **Idempotency** | Same request repeated should not create duplicate side effects (e.g. double charge). |

## How they connect

```text
Client request
    → RateLimiter (protect capacity)
    → Idempotency (safe retries / duplicates)
    → Caching (faster reads)
    → Retry (transient errors)
    → CircuitBreaker (failing downstream)
```

## Study order

1. Caching  
2. Retry  
3. Idempotency  
4. RateLimiter  
5. CircuitBreaker  

## Run note (important)

Java packages were **not** changed (`Caching`, `RateLimiter`, etc.).

In Cursor/IDE, mark **`ResiliencePatterns`** as a **Source Root** so packages still resolve after the move.

Then open any class and Run `main` as before.
