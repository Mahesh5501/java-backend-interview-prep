# Problem 08 — Subarray Sum Equals K

> **Pattern:** Prefix sum + HashMap of prefix frequencies  
> **Java file:** `../SubarraySumEqualsK.java`  
> **Goal:** Understand why sliding window fails with negatives, and how prefix maps fix it.

---

## 1. Problem statement

Given an integer array `nums` and an integer `k`, return the **number of contiguous subarrays** whose sum equals `k`.

### Example 1

```text
Input:  nums = [1, 1, 1], k = 2
Output: 2
```

Subarrays: `[1,1]` (indices 0–1) and `[1,1]` (indices 1–2).

### Example 2

```text
Input:  nums = [1, 2, 3], k = 3
Output: 2
```

`[1,2]` and `[3]`.

---

## 2. Mental picture

Prefix sum:

```text
prefix[i] = nums[0] + nums[1] + ... + nums[i]
```

Sum of subarray `left..right`:

```text
prefix[right] - prefix[left - 1]   (with prefix[-1] = 0)
```

We want that equal to `k`:

```text
prefix[right] - prefix[left - 1] == k
→ prefix[left - 1] == prefix[right] - k
```

So at each running `prefix`, ask: **how many earlier prefixes equal `prefix - k`?**

```text
nums:    1   1   1     k = 2
prefix:  1   2   3
At 2: 2-2=0 seen once → +1
At 3: 3-2=1 seen once → +1
Total 2
```

### Jargon

| Term | Meaning |
|---|---|
| **Prefix sum** | Running sum from the start |
| **prefixCount map** | How many times each prefix value has appeared |
| **Why put 0 → 1** | Empty prefix before start; whole-prefix subarrays that equal `k` |

---

## 3. How to start thinking

1. Brute: every `start..end` sum → `O(n²)`.
2. Can sliding window work? **Only if all numbers are non-negative.** Negatives break the “grow/shrink” rule.
3. General case (negatives allowed): prefix + HashMap.

---

## 4. Approach 1 — Brute force

### Idea

For every start, expand end and keep a running sum; count when sum == k.

### Java method

`subarraySumBrute`

### Complexity

`O(n²)` time, `O(1)` extra space.

---

## 5. Approach 2 — Sliding window (ONLY if all nums ≥ 0)

Works for “non-negative numbers only” variants.  
If `nums` can have **negatives**, window grow/shrink rules break — **do not use** for the general LeetCode problem.

### Java method

`subarraySumSlidingWindowNonNegative` — throws if it sees a negative, on purpose.

---

## 6. Approach 3 — Prefix sum + frequency map (optimal — what we use)

### Idea

```text
map: prefixValue → how many times seen
map.put(0, 1)   // imaginary empty prefix

prefix = 0, count = 0
for x in nums:
    prefix += x
    count += map.getOrDefault(prefix - k, 0)
    map.put(prefix, map.getOrDefault(prefix, 0) + 1)
```

### Why this approach?

- Handles negatives
- One pass `O(n)`
- Directly counts how many earlier starts form sum `k` ending here

### Why map stores counts (not just a Set)?

Multiple earlier prefixes can share the same value → multiple valid subarrays.

### Java method

`subarraySum`

### Dry run

`nums = [1, 1, 1]`, `k = 2`

| x | prefix | prefix−k | map before | add to count | map after |
|---|---|---|---|---|---|
| — | 0 | — | `{0:1}` | — | — |
| 1 | 1 | -1 | `{0:1}` | +0 | `{0:1, 1:1}` |
| 1 | 2 | 0 | `{0:1,1:1}` | +1 | `{0:1,1:1,2:1}` |
| 1 | 3 | 1 | `…2:1` | +1 | `{…,3:1}` |

Total count = 2.

### Complexity

| Time | Extra space |
|---|---|
| `O(n)` | `O(n)` |

---

## 7. What we use and why

| Approach | Java method | Works with negatives? | Time | Use |
|---|---|---|---|---|
| Brute | `subarraySumBrute` | yes | `O(n²)` | Baseline |
| Sliding window | `subarraySumSlidingWindowNonNegative` | **no** | `O(n)` | Only all ≥ 0 |
| **Prefix + map** | `subarraySum` | **yes** | `O(n)` | **Interview answer** |

---

## 8. Hardest part

### Forgetting `map.put(0, 1)`

Misses subarrays that start at index 0.

### Updating the map before checking

Usually **check first**, then put — same spirit as Two Sum. (If you put first, a `k == 0` case can miscount the current prefix against itself depending on logic — be careful.)

Our code: add to count using old map, **then** put current prefix.

### Confusing with “longest subarray”

This problem asks for **count**, not length — hence frequency map, not just first index.

---

## 9. Interview explanation — say this aloud

“Brute force tries every subarray in `O(n²)`. Sliding window is unsafe when negatives exist. I use prefix sums: a subarray sums to `k` when `prefix - earlierPrefix = k`. I store how many times each prefix appeared in a HashMap, initialize `0 → 1`, and at each step add `map[prefix - k]` to the answer, then record the current prefix. Time `O(n)`, space `O(n)`.”

---

## 10. Link to Java

| Approach | Method |
|---|---|
| Brute | `subarraySumBrute` |
| Sliding window (non-negative only) | `subarraySumSlidingWindowNonNegative` |
| Prefix + map | `subarraySum` |

Run `main` to compare. Do not call the window method on arrays with negatives.
