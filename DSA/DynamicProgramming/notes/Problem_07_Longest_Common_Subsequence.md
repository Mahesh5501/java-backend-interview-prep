# Problem 07 — Longest Common Subsequence

> **Pattern:** 2D string DP  
> **Goal:** Length of longest subsequence common to **both** strings.

---

## 1. Problem statement

Strings `text1`, `text2`. Return LCS **length** (LeetCode 1143).

### Example

```text
text1 = "abcde", text2 = "ace"
Output: 3    ("ace")
```

Subsequence: delete zero or more chars **without reordering**.

---

## 2. Mental picture

Fill table `dp[i][j]` = LCS of prefixes `text1[0..i-1]`, `text2[0..j-1]`.

```text
        ø  a  c  e
    ø   0  0  0  0
    a   0  1  1  1
    b   0  1  1  1
    c   0  1  2  2
    d   0  1  2  2
    e   0  1  2  3  ← answer
```

```text
chars match at (i,j)?  → dp[i][j] = 1 + dp[i-1][j-1]
else                   → max(dp[i-1][j], dp[i][j-1])
```

---

## 3. How to start thinking

1. **Subsequence not substring** — gaps allowed.
2. **States on prefixes** — classic 2D DP.
3. **Match** — consume both chars, diagonal `+1`.
4. **Mismatch** — best of drop one char from either side.
5. **Space?** Two rows enough (`O(min(m,n))`).

---

## 4. Brute

Generate all subsequences of shorter string, check in longer — exponential.

---

## 5. Optimal 2D DP

### Java (matches repo)

```java
public static int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            } else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
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

**Indexing:** `charAt(i-1)` when `dp` index is `i` — off-by-one errors.

**Before:** Trying to match from both ends simultaneously without table.

**After:** Only **four moves**: match diagonal, up, left.

---

## 7. Dry run — "ab" vs "a"

| i,j | char | dp |
|---|---|---:|
| 1,1 | a=a | 1 |
| 2,1 | b vs a | max(1,0)=1 |

Answer 1.

---

## 8. Edge cases

| Input | Output |
|---|---:|
| one empty | 0 |
| identical | length |
| no common letter | 0 |

---

## 9. Common mistakes

| Mistake | Effect |
|---|---|
| Use substring windows only | Wrong |
| `dp[i][j]` without +1 on match | Undercount |

---

## 10. Interview script

> “dp[i][j] is LCS of prefixes. Match → plus one from diagonal. Else max of up and left. Fill row by row, O(mn).”

---

## 11. Follow-ups

- Print LCS — backtrack from `dp[m][n]`.
- **Edit distance** — nearby recurrence (Problem 10).
- **SCS** — dual construction.

---

## 12. Quick revision

- **Match → `1+diag`.**
- **Else → max(up, left).**
- **Answer `dp[m][n]`.**

---

## 13. Java

`../LongestCommonSubsequence.java`.

---

## 14. Next

**Problem 08 — Word Break**

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

Align method names with `../LongestCommonSubsequence.java`.

```java
package DSA.DynamicProgramming;

/*
 * Problem 07 — Longest Common Subsequence (LeetCode 1143)
 */
public class LongestCommonSubsequence {

    public static int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("LCS: " + longestCommonSubsequence("abcde", "ace"));
    }
}
```
