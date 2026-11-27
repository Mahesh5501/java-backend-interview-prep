# Problem 05 — Unique Paths

> **Pattern:** 2D grid DP  
> **Goal:** Count paths from top-left to bottom-right (only right/down).

---

## 1. Example

```text
Input:  m=3, n=7
Output: 28
```

---

## 2. Recurrence

`dp[r][c] = dp[r-1][c] + dp[r][c-1]`.

---

## 3. Java

`../UniquePaths.java` — brute DFS vs 1D rolled DP.

---

## 4. Next

**Problem 06 — 0/1 Knapsack**