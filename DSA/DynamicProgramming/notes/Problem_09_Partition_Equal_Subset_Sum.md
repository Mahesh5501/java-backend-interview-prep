# Problem 09 — Partition Equal Subset Sum

> **Pattern:** 0/1 knapsack on subset sums  
> **Goal:** Split `nums` into two subsets with equal sum.

---

## 1. Example

```text
Input:  [1,5,11,5]
Output: true   (1+5+5 vs 11)
```

---

## 2. Reduction

If total odd → false. Else `dp[target]` with `target = sum/2`.

---

## 3. Java

`../PartitionEqualSubsetSum.java`.

---

## 4. Next

**Problem 10 — Edit Distance**