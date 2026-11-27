# Dynamic Programming — Common Patterns Reference

A reusable revision guide for Java interviews.

```text
DSA/DynamicProgramming/
├── notes/
└── *.java          ← package DSA.DynamicProgramming
```

---

## 1. What is DP?

**Recursion + overlapping subproblems + optimal substructure.**

Instead of solving the same subproblem many times, **memoize** or fill a **table** bottom-up.

Interview litmus test:

1. Can I define a **state** (e.g. `dp[i]`, `dp[i][j]`)?
2. Can I write a **recurrence** from smaller states?
3. Are subproblems **reused**?

---

## 2. How to recognise DP

| Signal | Example problems |
|---|---|
| Count ways | Climbing Stairs, Unique Paths |
| Min / max over choices | House Robber, Coin Change |
| Two sequences | LCS, Edit Distance |
| Subset / knapsack | 0/1 Knapsack, Partition Equal Subset Sum |
| String segmentation | Word Break |

If brute recursion is `O(2^n)` but states repeat → DP.

---

## 3. Pattern A — 1D DP on array index

```java
int[] dp = new int[n + 1];
dp[0] = base;
for (int i = 1; i <= n; i++) {
    dp[i] = best(dp[i - 1], dp[i - 2], ...);
}
```

**When:** Climbing Stairs, House Robber, Coin Change (on amount), Word Break.

**Space tip:** often roll `dp[i-1]`, `dp[i-2]` → `O(1)`.

---

## 4. Pattern B — 2D grid DP

```java
int[][] dp = new int[m][n];
dp[0][0] = 1;
for (int r = 0; r < m; r++) {
    for (int c = 0; c < n; c++) {
        dp[r][c] = dp[r-1][c] + dp[r][c-1];
    }
}
```

**When:** Unique Paths; tabulation for LCS / Edit Distance.

---

## 5. Pattern C — 0/1 Knapsack

Each item **once**:

```java
for (int item = 0; item < n; item++) {
    for (int w = W; w >= weight[item]; w--) {
        dp[w] = Math.max(dp[w], value[item] + dp[w - weight[item]]);
    }
}
```

**When:** ZeroOneKnapsack, Partition Equal Subset Sum (target = sum/2).

**Note:** loop capacity **downwards** when using 1D array for 0/1 items.

---

## 6. Pattern D — Unbounded knapsack / coin change

Each coin **unlimited**:

```java
for (int coin : coins) {
    for (int a = coin; a <= amount; a++) {
        dp[a] = Math.min(dp[a], 1 + dp[a - coin]);
    }
}
```

**When:** Coin Change (minimum coins).

---

## 7. Pattern E — Two strings (2D table)

`dp[i][j]` = answer for `s1[0..i-1]` and `s2[0..j-1]`.

Match: take diagonal `dp[i-1][j-1]`.  
Mismatch: combine neighbors (LCS max, Edit Distance min of ops).

**When:** LCS, Edit Distance.

---

## 8. Pattern F — LIS

- `O(n²)`: for each `i`, look at all `j < i` with smaller value.
- `O(n log n)`: **patience sorting** / `tails[]` with binary search.

---

## 9. Problem map

| # | Problem | Pattern |
|---:|---|---|
| 01 | Climbing Stairs | 1D Fibonacci-style |
| 02 | House Robber | 1D max with skip |
| 03 | Coin Change | Unbounded min |
| 04 | LIS | 1D or n log n |
| 05 | Unique Paths | 2D grid |
| 06 | 0/1 Knapsack | 2D / 1D backward |
| 07 | LCS | 2D strings |
| 08 | Word Break | 1D boolean |
| 09 | Partition Equal Subset Sum | 0/1 knapsack |
| 10 | Edit Distance | 2D strings |

Study order: **01 → 10**.

---

## 10. Common mistakes

1. Wrong base cases (`dp[0]`, empty string row).
2. Using forward knapsack loop for **0/1** on 1D array (duplicates items).
3. Confusing **subsequence** vs **subarray**.
4. Not initializing `∞` / `-∞` for min/max problems.
5. Returning `dp[n]` vs `dp[n-1]` off-by-one.

---

## 11. How to use this folder

1. Read this file.
2. For each problem: define state and recurrence on paper.
3. Run the `.java` demo.
4. Re-code optimal without notes.