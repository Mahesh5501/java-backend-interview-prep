# Problem 06 — 0/1 Knapsack

> **Pattern:** 2D → 1D DP with reverse weight loop  
> **Goal:** Max **value** with weight ≤ `capacity`, each item **once**.

---

## 1. Problem statement

`weights[i]`, `values[i]`, capacity `W`. Pick subset of items (each at most once) maximizing total value without exceeding `W`.

Classic teaching problem (not one fixed LeetCode number).

### Example

```text
weights = [1, 3, 4, 5]
values  = [1, 4, 5, 7]
capacity = 7
Output: 9   (items 2+3: weights 3+4, values 4+5)
```

---

## 2. Mental picture

Table: rows = items considered, cols = capacity `0..W`.

```text
         cap: 0  1  2  3  4  5  6  7
item 1        0  1  1  1  1  1  1  1
item 2        0  1  1  4  5  5  5  5
...
```

Cell choice at item `i`, cap `w`:

```text
skip: dp[i-1][w]
take: values[i] + dp[i-1][w - weights[i]]   (if wt <= w)
```

---

## 3. How to start thinking

1. **Greedy by value/weight ratio?** Can fail for 0/1 (not fractional).
2. **State?** `dp[i][w]` = best using first `i` items with cap `w`.
3. **0/1 constraint?** Each item appears in at most one branch — use **previous row** only when going 1D.
4. **1D trick?** Loop `w` **downwards** so each item used once per pass.

---

## 4. Approach 1 — Brute

Bitmask `2^n` subsets or recursion take/skip — `O(2^n)`.

---

## 5. Approach 2 — 2D DP (matches repo)

```java
public static int knapsack2D(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    for (int i = 1; i <= n; i++) {
        int wt = weights[i - 1];
        int val = values[i - 1];
        for (int w = 0; w <= capacity; w++) {
            dp[i][w] = dp[i - 1][w];
            if (wt <= w) {
                dp[i][w] = Math.max(dp[i][w], val + dp[i - 1][w - wt]);
            }
        }
    }
    return dp[n][capacity];
}
```

**Time:** `O(n * W)` **Space:** `O(n * W)`.

---

## 6. Approach 3 — Optimal 1D DP

```java
public static int knapsack1D(int[] weights, int[] values, int capacity) {
    int[] dp = new int[capacity + 1];
    for (int i = 0; i < weights.length; i++) {
        int wt = weights[i], val = values[i];
        for (int w = capacity; w >= wt; w--) {
            dp[w] = Math.max(dp[w], val + dp[w - wt]);
        }
    }
    return dp[capacity];
}
```

### Why reverse `w`

Forward would allow same item multiple times → **unbounded** knapsack bug.

### Complexity

| | 1D |
|---|---|
| **Time** | `O(n * W)` |
| **Space** | `O(W)` |

---

## 7. Hardest part

**Before:** Forward loop on `w` in 1D.

**After:** **Descending** `w` for 0/1; **ascending** for unbounded.

---

## 8. Dry run (1D) — cap 7, first two items only sketch

| item | `wt` | `val` | `dp` after (abbrev) |
|---|---:|---:|---|
| 0 | 1 | 1 | `[0,1,1,1,1,1,1,1]` |
| 1 | 3 | 4 | max at w=3,4,... |

Full answer with all items: **9**.

---

## 9. Edge cases

| Case | Note |
|---|---|
| `W=0` | return 0 |
| item heavier than W | skipped |
| duplicate wt/val | fine |

---

## 10. Common mistakes

| Mistake | Effect |
|---|---|
| Forward 1D loop | Double-use items |
| Fractional greedy | Not 0/1 optimal |

---

## 11. Interview script

> “2D: for each item, either skip or take with previous row. Space-optimize to 1D: iterate w from W down to wt. O(nW) pseudo-polynomial.”

---

## 12. Follow-ups

- **Unbounded knapsack:** forward `w`.
- **Partition / subset sum:** boolean DP — Problem 09.
- **Track items:** backtrack 2D table.

---

## 13. Quick revision

- **0/1 → downward `w`.**
- **`dp[w] = max(dp[w], val + dp[w-wt])`.**
- Not greedy ratio.

---

## 14. Java

`../ZeroOneKnapsack.java` — `knapsack2D`, `knapsack1D`.

---

## 15. Next

**Problem 07 — Longest Common Subsequence**

---

## Depth addendum — extended workbook

### Mental picture (second angle)

Sketch input on paper and mark **what changes each loop iteration**. If the pattern uses a stack, queue, heap, or map, write its contents as a column beside the array/tree.

### Brute force reminder

State the naive approach in one sentence, its time/space, and **one sentence why** the optimal pattern beats it.

### Hardest-part drill

| Before coding | After understanding |
|---|---|
| Unsure what moves | Name the invariant pointers/stack maintain |
| Edge case worry | Check empty, single, duplicates |

### Extended dry-run columns

| iter | key variables | container snapshot | output so far |
|---:|---|---|---|

Fill five rows from the matching Java main example in the sibling .java file.

### Interview say-aloud (30s)

> I recognize the pattern, use the right structure for the invariant, avoid brute time because each element is handled once.

### Follow-up Q&A

**Q: In-place?** Only if allowed.  
**Q: Unsorted input?** State preprocessing.

---

---

## Repository Java (full)

Align method names with `../ZeroOneKnapsack.java`.

```java
package DSA.DynamicProgramming;

/*
 * Problem 06 — 0/1 Knapsack (classic teaching)
 */
public class ZeroOneKnapsack {

    public static int knapsack2D(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            int wt = weights[i - 1];
            int val = values[i - 1];
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];
                if (wt <= w) {
                    dp[i][w] = Math.max(dp[i][w], val + dp[i - 1][w - wt]);
                }
            }
        }
        return dp[n][capacity];
    }

    public static int knapsack1D(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < weights.length; i++) {
            int wt = weights[i], val = values[i];
            for (int w = capacity; w >= wt; w--) {
                dp[w] = Math.max(dp[w], val + dp[w - wt]);
            }
        }
        return dp[capacity];
    }

    public static void main(String[] args) {
        int[] w = { 1, 3, 4, 5 };
        int[] v = { 1, 4, 5, 7 };
        int cap = 7;
        System.out.println("2D: " + knapsack2D(w, v, cap));
        System.out.println("1D: " + knapsack1D(w, v, cap));
    }
}
```
