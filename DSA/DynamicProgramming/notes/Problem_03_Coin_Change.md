# Problem 03 — Coin Change

> **Pattern:** Unbounded knapsack / min DP  
> **Goal:** Fewest coins to make `amount`, or `-1`.

---

## 1. Example

```text
Input:  coins=[1,2,5], amount=11
Output: 3   (5+5+1)
```

---

## 2. Recurrence

`dp[a] = min(dp[a], 1 + dp[a-coin])` for all coins.

---

## 3. Java

`../CoinChange.java`.

---

## 4. Next

**Problem 04 — Longest Increasing Subsequence**