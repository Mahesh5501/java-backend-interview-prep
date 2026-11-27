# Problem 10 — Edit Distance

> **Pattern:** 2D DP — insert / delete / replace  
> **Goal:** Minimum operations to transform `word1` into `word2`.

Allowed: insert, delete, replace (each cost 1).

---

## 1. Problem statement

### Example

```text
word1 = "horse", word2 = "ros"
Output: 3
```

`horse` → `rorse` → `rose` → `ros`.

---

## 2. Mental picture

Table `dp[i][j]` = min edits to turn `word1[0..i-1]` into `word2[0..j-1]`.

```text
        ø  r  o  s
    ø   0  1  2  3
    h   1  1  2  3
    o   2  2  1  2
    r   3  2  2  2
    s   4  3  3  2
    e   5  4  4  3  ← answer
```

```text
match:     dp[i][j] = dp[i-1][j-1]
mismatch:  1 + min(replace, delete from w1, insert into w1)
           = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])
```

---

## 3. How to start thinking

1. **LCS cousin** — extra costs on mismatch.
2. **Base row/col** — transform prefix to empty → `i` or `j` deletions/inserts.
3. **Three operations** — collapse to three neighbors in grid.

---

## 4. Brute

DFS all edit sequences — exponential.

---

## 5. Optimal

### Java (matches repo)

```java
public static int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                        Math.min(dp[i - 1][j], dp[i][j - 1]));
            }
        }
    }
    return dp[m][n];
}
```

### Complexity

| | 2D |
|---|---|
| **Time** | `O(m * n)` |
| **Space** | `O(m * n)` |

---

## 6. Hardest part

Mapping **dp[i-1][j]** to delete from word1, **dp[i][j-1]** to insert — remember interview convention.

**Space:** two rows `O(n)`.

**Follow-up** one replace only / etc. — change costs.

---

## 7. Dry run cell — mismatch 'e' vs 's'

Take min of three predecessors + 1.

Final `dp[5][3]=3`.

---

## 8. Edge cases

| Case | distance |
|---|---:|
| both empty | 0 |
| one empty | length of other |
| identical | 0 |

---

## 9. Common mistakes

| Mistake | Effect |
|---|---|
| Wrong base initialization | Off by one |
| Only replace on mismatch | Must allow insert/delete |

---

## 10. Interview script

> “Same table as LCS but mismatch adds min of replace delete insert. First row and column are index counts. Answer bottom right.”

---

## 11. Follow-ups

- Print sequence of edits — backtrack.
- **One edit distance** — boolean variant.
- **DP on Damerau-Levenshtein** — transpositions.

---

## 12. Quick revision

- **Base: dp[i][0]=i, dp[0][j]=j.**
- **Match → diag.**
- **Else 1+min(3).**

---

## 13. Java

`../EditDistance.java`.

---

## 14. Next

Back to **Dynamic Programming** overview or **Graphs Problem 01**.

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

Align method names with `../EditDistance.java`.

```java
package DSA.DynamicProgramming;

/*
 * Problem 10 — Edit Distance (LeetCode 72)
 */
public class EditDistance {

    public static int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("Edit distance: " + minDistance("horse", "ros"));
    }
}
```
