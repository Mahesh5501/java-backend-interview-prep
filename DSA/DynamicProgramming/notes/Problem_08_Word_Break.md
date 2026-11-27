# Problem 08 — Word Break

> **Pattern:** 1D boolean DP on string prefix  
> **Goal:** Can string `s` be segmented into **dictionary words** (concatenation)?

---

## 1. Problem statement

String `s`, list `wordDict`. Return whether `s` can be built from dictionary words separated by spaces (each word may repeat).

### Example

```text
s = "leetcode", dict = ["leet","code"]
Output: true   ("leet" + "code")
```

### Example 2

```text
s = "catsandog", dict = ["cats","dog","sand","and","cat"]
Output: false
```

---

## 2. Mental picture

```text
s:  l e e t c o d e
    ^
    dp[i] = can prefix s[0..i-1] be segmented?

dp[0] = true (empty prefix)

try cuts:  [0..j) | [j..i)  if dp[j] && word([j,i))
```

```text
  leet | code
  ✓    +  ✓   → dp[8]
```

---

## 3. How to start thinking

1. **Greedy longest match?** Fails on overlaps / wrong splits.
2. **State?** `dp[i]` for first `i` characters.
3. **Transition?** For each `j < i`, if `dp[j]` and `s[j:i)` in dict → `dp[i]=true`.
4. **Base?** `dp[0]=true`.

---

## 4. Brute DFS

Try every split point from each start — exponential.

```java
private static boolean canBreak(int start, String s, Set<String> dict) {
    if (start == s.length()) return true;
    for (int end = start + 1; end <= s.length(); end++) {
        if (dict.contains(s.substring(start, end)) && canBreak(end, s, dict))
            return true;
    }
    return false;
}
```

Memo on `start` → `O(n^2 * n)` worst with hash lookups — similar to DP below.

---

## 5. Optimal DP

### Java (matches repo)

```java
public static boolean wordBreak(String s, List<String> wordDict) {
    Set<String> dict = new HashSet<>(wordDict);
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    for (int i = 1; i <= s.length(); i++) {
        for (int j = 0; j < i; j++) {
            if (dp[j] && dict.contains(s.substring(j, i))) {
                dp[i] = true;
                break;
            }
        }
    }
    return dp[s.length()];
}
```

### Dry run — `leetcode`

| i | segment tried | dp[i] |
|---:|---|---|
| 4 | "leet" from 0 | true |
| 8 | "code" from 4 | true |

### Complexity

| | DP |
|---|---|
| **Time** | `O(n^2 * L)` with `L` average word length / hash |
| **Space** | `O(n)` |

---

## 6. Hardest part

**Substring indices:** `substring(j, i)` is `[j, i)` — `i` exclusive.

**Word Break II** — generate all sentences (backtracking), not just boolean.

**TLE on huge inputs:** Dictionary in `HashSet`; for huge dict use trie (Trie folder).

---

## 7. Edge cases

| Case | Result |
|---|---|
| empty `s` | true (dp[0]) |
| word not used | false |
| repeated use of word | allowed |

---

## 8. Common mistakes

| Mistake | Effect |
|---|---|
| `dp[0]=false` | Nothing works |
| Skip `break` after true | OK but slower |

---

## 9. Interview script

> “dp[i] means prefix length i is breakable. Try every last word start j. dp[j] and dict word → dp[i]. Return dp[n].”

---

## 10. Follow-ups

- **Word Break II** — collect paths.
- **Word Break III** with trie / memo.

---

## 11. Quick revision

- **`dp[0]=true`.**
- **Try all `j < i`.**
- **`substring(j,i)` in dict.**

---

## 12. Java

`../WordBreak.java` — `wordBreakBrute`, `wordBreak`.

---

## 13. Next

**Problem 09 — Partition Equal Subset Sum**

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

Align method names with `../WordBreak.java`.

```java
package DSA.DynamicProgramming;

import java.util.*;

/*
 * Problem 08 — Word Break (LeetCode 139)
 */
public class WordBreak {

    public static boolean wordBreakBrute(String s, List<String> wordDict) {
        return canBreak(0, s, new HashSet<>(wordDict));
    }

    private static boolean canBreak(int start, String s, Set<String> dict) {
        if (start == s.length()) {
            return true;
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (dict.contains(s.substring(start, end)) && canBreak(end, s, dict)) {
                return true;
            }
        }
        return false;
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        List<String> dict = Arrays.asList("leet", "code");
        System.out.println("wordBreak: " + wordBreak("leetcode", dict));
    }
}
```
