# Problem 06 — 0/1 Knapsack

> **Pattern:** Classic 2D / 1D backward DP  
> **Goal:** Maximum value with weight limit; each item **once**.

---

## 1. Teaching goal

Understand the **2D table** first, then compress to 1D with **backward** inner loop.

---

## 2. Recurrence

Take or skip item `i` at capacity `w`:

`dp[i][w] = max(skip, val + dp[i-1][w-wt])`.

---

## 3. Java

`../ZeroOneKnapsack.java` — `knapsack2D`, `knapsack1D`.

---

## 4. Next

**Problem 07 — Longest Common Subsequence**