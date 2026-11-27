# Problem 05 — Top K Frequent Elements

> **Pattern:** Frequency map + bucket **or** min-heap  
> **Java file:** `../TopKFrequentElements.java`  
> **Goal:** Know both interview solutions and when each wins.

---

## 1. Problem statement

Given `nums` and integer `k`, return the `k` most frequent elements (any order).

### Example

```text
Input:  nums = [1,1,1,2,2,3], k = 2
Output: [1, 2]   (or [2, 1])
```

`1` appears 3 times, `2` appears 2 times, `3` appears once → top 2 are `1` and `2`.

---

## 2. Mental picture

Step 1 always: **count frequencies**.

```text
1 → 3
2 → 2
3 → 1
```

Step 2: pick the `k` keys with largest counts — either:

- Put numbers into **buckets by frequency**, then read from high freq down
- Keep a **min-heap of size k** by frequency

---

## 3. How to start thinking

1. Must count first → HashMap value → frequency.
2. Naive: sort unique entries by frequency → `O(n log n)`.
3. Better with heap: `O(n log k)`.
4. Best average for this problem: bucket sort by frequency → `O(n)`.

---

## 4. Approach 1 — Frequency map + sort entries

### Idea

Count, put entries in a list, sort by frequency descending, take first `k`.

### Complexity

`O(n log n)` — fine, but not the sharpest answer.

### When

Quick coding if constraints are mild.

---

## 5. Approach 2 — Frequency map + min-heap of size k

### Idea

```text
for each unique number:
    push onto min-heap ordered by frequency
    if heap size > k → pop (remove least frequent)
```

Heap always holds the current top-k.

### Why min-heap (not max-heap)?

With size capped at `k`, popping the **smallest** frequency keeps the k largest.

### Java method

`topKFrequentHeap`

### Complexity

| Time | Extra space |
|---|---|
| `O(n log k)` | `O(n)` for map + `O(k)` heap |

### When to use

When `k` is small compared to `n`, or interviewer wants heap practice.

---

## 6. Approach 3 — Frequency map + buckets (optimal — often preferred)

### Idea

Frequency of any value is between `1` and `n`.

```text
bucket[f] = list of values that appear exactly f times

Then scan f from n down to 1, collect values until you have k of them.
```

```text
nums freq: 1→3, 2→2, 3→1

bucket[3] = [1]
bucket[2] = [2]
bucket[1] = [3]

Scan high→low: take 1, then 2 → done (k=2)
```

### Why this approach?

Linear time after counting — no heap log factors.

### Java method

`topKFrequentBucket`

### Complexity

| Time | Extra space |
|---|---|
| `O(n)` | `O(n)` |

---

## 7. What we use and why

| Approach | Java method | Time | Use when |
|---|---|---|---|
| Sort by freq | *(verbal)* | `O(n log n)` | Quick |
| Min-heap size k | `topKFrequentHeap` | `O(n log k)` | Heap follow-up / small k |
| **Buckets** | `topKFrequentBucket` | `O(n)` | **Best default here** |

Both heap and bucket are interview-valid. Know **both**. Lead with bucket if comfortable; offer heap as alternative.

---

## 8. Hardest part

### Mistake — Max-heap of all uniques then pop k

Works but `O(n log n)`. Size-`k` min-heap is smarter.

### Mistake — Bucket index off-by-one

`bucket` length should be `n + 1` because frequency can be `n`.

### Mistake — Assuming unique frequencies

Multiple numbers can share the same frequency — bucket holds a **list**.

---

## 9. Interview explanation — say this aloud

“I count frequencies with a HashMap. Then I either (1) put numbers into buckets indexed by frequency and read from high to low until I have k values — `O(n)`, or (2) keep a min-heap of size k by frequency — `O(n log k)`. I’ll use buckets for linear time.”

---

## 10. Link to Java

| Approach | Method |
|---|---|
| Bucket | `topKFrequentBucket` |
| Heap | `topKFrequentHeap` |
