# Problem 05 — Unique Paths

> **Pattern:** 2D grid DP (only right/down moves)  
> **Goal:** Count paths from top-left to bottom-right in `m x n` grid.

---

## 1. Problem statement

Robot on `m x n` grid. Start `(0,0)`, end `(m-1,n-1)`. Only **right** or **down**. How many unique paths?

### Example

```text
Input:  m = 3, n = 7
Output: 28
```

### Constraints

- `1 <= m, n <= 100`

---

## 2. Mental picture

```text
  → → →
  ↓     Each path: (m-1) downs + (n-1) rights
  ↓     Order only matters — path counting
```

DP cell = ways to reach that cell:

```text
1  1  1  1
1  2  3  4
1  3  6  10
```

> **`ways(r,c) = ways(r-1,c) + ways(r,c-1)`** (with top row / left col = 1).

---

## 3. How to start thinking

1. **Combinatorics?** `C(m+n-2, m-1)` — valid, but grid obstacles variants need DP.
2. **State?** `dp[r][c]` paths to `(r,c)`.
3. **Base?** First row and column all `1`.
4. **Space optimization?** One row rolling left-to-right.

---

## 4. Approach 1 — Brute DFS

Every path branches right or down until out of bounds or goal.

```java
private static int dfs(int r, int c, int m, int n) {
    if (r >= m || c >= n) return 0;
    if (r == m - 1 && c == n - 1) return 1;
    return dfs(r + 1, c, m, n) + dfs(r, c + 1, m, n);
}
```

**Time:** exponential without memo — same subcells repeated.

---

## 5. Approach 2 — Optimal 1-row DP

### Steps

1. `dp[0..n-1]` all `1`.
2. For `r = 1 .. m-1`, for `c = 1 .. n-1`: `dp[c] += dp[c-1]`.
3. Return `dp[n-1]`.

### Java (matches repo)

```java
public static int uniquePaths(int m, int n) {
    int[] dp = new int[n];
    Arrays.fill(dp, 1);
    for (int r = 1; r < m; r++) {
        for (int c = 1; c < n; c++) {
            dp[c] = dp[c] + dp[c - 1];
        }
    }
    return dp[n - 1];
}
```

### Dry run — `m=3`, `n=3`

| row `r` | `dp` after row |
|---:|---|
| 0 | `[1,1,1]` |
| 1 | `[1,2,3]` |
| 2 | `[1,3,6]` |

Answer **6**.

### Complexity

| | 1D rolling |
|---|---|
| **Time** | `O(m*n)` |
| **Space** | `O(n)` |

---

## 6. Hardest part

When updating `dp[c] += dp[c-1]`, use **left-to-right** within row so `dp[c-1]` is current row’s value, not stale wrong dimension.

**Obstacles (LC 63):** skip blocked cells — same recurrence with `0` at blocks.

---

## 7. Edge cases

| `m,n` | paths |
|---|---:|
| 1x1 | 1 |
| 1xk | 1 |
| mx1 | 1 |
| 3x7 | 28 |

---

## 8. Common mistakes

| Mistake | Effect |
|---|---|
| Count diagonals | Wrong rules |
| Row update right-to-left | Wrong values |
| Forget first row/col init | Zeros |

---

## 9. Interview script

> “Paths to cell = from above + from left. First row/col ones. Compress to one row updated left to right. O(m*n) time O(n) space.”

---

## 10. Follow-ups

- **Unique Paths II** — obstacles.
- **Min path sum** — min not count.

---

## 11. Quick revision

- **Right + down only.**
- **`dp[c] += dp[c-1]`** per row.
- **Combinatorics shortcut** when no blocks.

---

## 12. Java

`../UniquePaths.java` — `uniquePathsBrute`, `uniquePaths`.

---

## 13. Next

**Problem 06 — 0/1 Knapsack**

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

Align method names with `../UniquePaths.java`.

```java
package DSA.DynamicProgramming;

import java.util.Arrays;

/*
 * Problem 05 — Unique Paths (LeetCode 62)
 */
public class UniquePaths {

    public static int uniquePathsBrute(int m, int n) {
        return dfs(0, 0, m, n);
    }

    private static int dfs(int r, int c, int m, int n) {
        if (r >= m || c >= n) {
            return 0;
        }
        if (r == m - 1 && c == n - 1) {
            return 1;
        }
        return dfs(r + 1, c, m, n) + dfs(r, c + 1, m, n);
    }

    public static int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                dp[c] = dp[c] + dp[c - 1];
            }
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        System.out.println("3x7 DP: " + uniquePaths(3, 7));
        System.out.println("3x7 brute: " + uniquePathsBrute(3, 7));
    }
}
```
