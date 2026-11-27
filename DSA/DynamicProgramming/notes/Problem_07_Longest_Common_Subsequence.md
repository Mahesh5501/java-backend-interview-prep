# Problem 07 — Longest Common Subsequence

> **Pattern:** 2D string DP  
> **Goal:** LCS length of two strings.

---

## 1. Example

```text
Input:  text1="abcde", text2="ace"
Output: 3   ("ace")
```

---

## 2. Recurrence

Match → `1 + dp[i-1][j-1]`; else `max(dp[i-1][j], dp[i][j-1])`.

---

## 3. Java

`../LongestCommonSubsequence.java`.

---

## 4. Next

**Problem 08 — Word Break**