# 07 — CompletableFuture Basics

---

## Mental picture

```text
  supplyAsync ──► thenApply ──► thenCompose ──► join/get
       │              │              │
       │         (map value)    (flatMap — no nested Future)
       │
   runAsync (no result)

  allOf / anyOf combine multiple futures
```

**Async pipeline** without nesting `Future.get()` in every line.

---

## Why it matters

- Fan-out to multiple services, aggregate, timeout, fallback — standard in **high-LPA backend** interviews.  
- Shows you know **which thread runs the callback** (default `ForkJoinPool.commonPool()` unless you pass an `Executor`).

---

## Small code snippet

```java
ExecutorService bizPool = Executors.newFixedThreadPool(4);

CompletableFuture<String> user = CompletableFuture
        .supplyAsync(() -> fetchUser(), bizPool)
        .thenApply(u -> u.toUpperCase());

CompletableFuture<String> withFallback = CompletableFuture
        .supplyAsync(() -> riskyCall(), bizPool)
        .exceptionally(ex -> "DEFAULT");

CompletableFuture<Void> both = CompletableFuture.allOf(user, withFallback);

String result = user.orTimeout(2, TimeUnit.SECONDS).join();
```

**thenApply vs thenCompose:**

- `thenApply`: `T → U`  
- `thenCompose`: `T → CompletableFuture<U>` (avoid `CompletableFuture<CompletableFuture<...>>`)

---

## Common mistakes

| Mistake | Fix |
|---|---|
| Blocking `get()` inside reactive/netty thread | Offload to pool |
| Ignoring exceptions — pipeline returns null | `handle`, `exceptionally`, `whenComplete` |
| `join()` in prod without timeout | `get(timeout)` or `orTimeout` |
| All async on common pool for blocking IO | Dedicated executor |

---

## Say this aloud

*"CompletableFuture lets me compose async steps — supplyAsync for creation, thenApply for mapping, thenCompose when the next step is another async call. I pass a dedicated executor for blocking work, set timeouts, and handle failures with exceptionally or handle. For multiple calls I use allOf and combine results in thenApply."*

---

## Follow-ups

1. **Difference from Future?** — CompletableFuture is completion-stage composable; Future is get/cancel only.  
2. **Cancellation?** — `cancel(true)` may interrupt running thread if cooperative.  
3. **vs Project Reactor / RxJava?** — CF for single-value async chains; reactive for streams — know when CF is enough.  
4. **Structured concurrency?** — Java 21 `StructuredTaskScope` — scope lifetime bounds child tasks.

**Demo:** `../java/DemoCompletableFuture.java`