# Problem 08 — Word Break

> **Pattern:** 1D boolean DP  
> **Goal:** Can `s` be segmented into dictionary words?

---

## 1. Example

```text
Input:  s="leetcode", wordDict=[leet,code]
Output: true
```

---

## 2. Recurrence

`dp[i]` true if some `dp[j]` true and `s[j:i]` in dict.

---

## 3. Java

`../WordBreak.java` — brute vs DP.

---

## 4. Next

**Problem 09 — Partition Equal Subset Sum**