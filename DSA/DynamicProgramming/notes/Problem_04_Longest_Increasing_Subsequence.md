# Problem 04 — Longest Increasing Subsequence

> **Pattern:** 1D DP or patience sorting  
> **Goal:** Length of longest strictly increasing **subsequence**.

---

## 1. Example

```text
Input:  [10,9,2,5,3,7,101,18]
Output: 4   (e.g. 2,3,7,18)
```

---

## 2. Approaches

- `O(n²)`: `dp[i]` best ending at `i`.
- `O(n log n)`: `tails[]` + binary search.

---

## 3. Java

`../LongestIncreasingSubsequence.java`.

---

## 4. Next

**Problem 05 — Unique Paths**