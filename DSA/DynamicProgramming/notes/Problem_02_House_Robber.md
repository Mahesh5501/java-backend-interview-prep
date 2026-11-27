# Problem 02 — House Robber

> **Pattern:** 1D DP with skip constraint  
> **Goal:** Maximum money without robbing two adjacent houses.

---

## 1. Example

```text
Input:  [2,7,9,3,1]
Output: 12   (2+9+1)
```

---

## 2. Recurrence

`dp[i] = max(dp[i-1], nums[i] + dp[i-2])`.

---

## 3. Java

`../HouseRobber.java` — `rob`, `robBrute`.

---

## 4. Next

**Problem 03 — Coin Change**