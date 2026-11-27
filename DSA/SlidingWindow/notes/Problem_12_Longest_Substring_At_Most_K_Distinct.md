# Problem 12 — Longest Substring with At Most K Distinct Characters

> **Pattern:** Variable-size sliding window — **at most K distinct** characters  
> **Sources:** Classic interview / LintCode 386 / LeetCode Premium (159)  
> **Goal:** Length of the **longest substring** of `s` with **at most `k` distinct** characters.

---

## 1. Problem statement

Given a string `s` and an integer `k`, return the length of the **longest substring** of `s` that contains at most `k` **distinct** characters.

### Examples

```text
Input:  s = "eceba", k = 2
Output: 3
Explanation: "ece" has distinct {e, c} — length 3.

Input:  s = "aa", k = 1
Output: 2
Explanation: "aa" has one distinct character.

Input:  s = "abcde", k = 3
Output: 3
Explanation: "abc" (or "bcd") — length 3.
```

### Constraints (typical)

- `1 <= s.length <= 5 * 10^4`
- `1 <= k <= 50`
- `s` consists of English letters

---

## 2. Mental picture — K empty labels on your window

Imagine at most **K different stickers** allowed on the current substring:

```text
k = 2,  s = e c e b a

[e]           → 1 distinct  OK  len 1
[e c]         → 2 distinct  OK  len 2
[e c e]       → 2 distinct  OK  len 3  ★ best so far
[e c e b]     → 3 distinct  BAD → shrink from left
[c e b]       → 3 still? e,c,b → shrink until ≤ 2
...
```

```text
     RIGHT  always tries to grow
     LEFT   moves when distinct count > k
```

**Problem 07 — Fruit Into Baskets** is this problem with **`k = 2`** and an array instead of a string. Same code shape.

---

## 3. How to start thinking

1. **Longest** valid window → expand with `right`; shrink **only when invalid** (like Problems 04, 06, 07).
2. **Distinct count** → `HashMap<Character, Integer>` (counts per char).
3. `map.size()` = number of distinct characters with count > 0 **if you remove keys at zero**.
4. Edge: if `k == 0` or empty `s`, define carefully (often length 0).

---

## 4. Approach 1 — Brute force

### Idea

Every start index, extend end while at most `k` distinct characters.

### Complexity

| | Brute |
|---|---|
| Time | `O(n²)` |
| Space | `O(k)` map per inner stretch |

---

## 5. Approach 2 — Optimal sliding window + HashMap counts

### Step-by-step

1. `left = 0`, `best = 0`, empty `HashMap<Character,Integer> count`.
2. For each `right`:
   - `count[s[right]]++`.
   - **While** `count.size() > k`:
     - Decrement `s[left]`; remove key if count becomes 0.
     - `left++`.
   - `best = max(best, right - left + 1)`.
3. Return `best`.

### Java

See `../LongestSubstringAtMostKDistinct.java`.

```java
Map<Character, Integer> count = new HashMap<>();
int left = 0, best = 0;
for (int right = 0; right < s.length(); right++) {
    char c = s.charAt(right);
    count.put(c, count.getOrDefault(c, 0) + 1);
    while (count.size() > k) {
        char out = s.charAt(left);
        count.put(out, count.get(out) - 1);
        if (count.get(out) == 0) count.remove(out);
        left++;
    }
    best = Math.max(best, right - left + 1);
}
return best;
```

### Special case: `k = 2` = Fruit Into Baskets

```text
fruits[i]  →  s.charAt(i)
type       →  character
K = 2      →  two baskets
```

Same loop; only the type of “fruit” changes.

---

## 6. Hardest part — counts vs set (same lesson as Problem 07)

### Before (wrong)

“When I shrink, I remove `s[left]` from a `HashSet` of distinct chars.”

Fails when the same character appears twice: removing one copy should not remove the type from the window.

### After (correct)

**Counts.** Remove the map key only when count hits **0** → `size()` is the true distinct count.

```text
Window "ece"  count: e=2, c=1  size=2  OK for k=2

Remove left 'e' once → e=1  still size=2  window "ce" valid
```

---

## 7. Detailed dry run — s = "eceba", k = 2

| right | char | map (char→count) | size | action | left | best |
|---:|---|---|---|---:|---:|---:|
| 0 | e | {e:1} | 1 | ok | 0 | 1 |
| 1 | c | {e:1,c:1} | 2 | ok | 0 | 2 |
| 2 | e | {e:2,c:1} | 2 | ok | 0 | **3** |
| 3 | b | {e:2,c:1,b:1} | **3** | shrink | 1 | 3 |
| | | drop e→{e:1,c:1,b:1} | 3 | shrink | 2 | 3 |
| | | drop c→{e:1,b:1} | 2 | ok | 2 | 3 |
| 4 | a | {e:1,b:1,a:1} | 3 | shrink | … | 3 |

Answer **3** (`"ece"`).

---

## 8. Why the algorithm is correct

After the `while` loop, the map exactly represents `[left..right]` and `size() ≤ k`.

For fixed `right`, you have the **smallest** valid `left` → **longest** valid window ending at `right`. Max over `right` is optimal.

---

## 9. Complexity comparison

| Method | Time | Space |
|---|---:|---:|
| Brute | `O(n²)` | `O(k)` |
| **Window + map** | **`O(n)`** | **`O(k)`** — at most `k+1` keys briefly |

---

## 10. Edge cases

| s | k | ans | Note |
|---|---|---:|---|
| `"aa"` | 1 | 2 | Whole string |
| `"abc"` | 3 | 3 | All distinct allowed |
| `"abc"` | 1 | 1 | Any single char |
| `""` | 2 | 0 | Empty |
| large `k` ≥ alphabet | | `s.length()` | Never need shrink |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| `if` instead of `while` when size > k | Invalid window counted |
| Remove key when count > 0 | Distinct count too small |
| Confuse with **at least** K distinct | Different problem |
| Use longest-window rule on minimum-window problem | Shrink logic inverted |

---

## 12. How to recognise this pattern

- “**At most K distinct** characters / types / values”
- “**Longest** substring / subarray …”
- Story: Fruit Into Baskets → **K = 2**

---

## 13. Interview explanation — say this aloud

> “I keep a hash map from character to count in the current window. I expand right and add the character. If more than k distinct keys exist, I move left forward, decrementing the leaving character until its count hits zero and I remove the key. I track the maximum window length. Each index enters and leaves once — O(n) time.”

---

## 14. Interview follow-up Q&A

**Q: Return the substring itself, not length?**  
A: Store `bestLeft`/`bestLen` when updating `best`.

**Q: Exactly K distinct?**  
A: Harder — often two passes or different shrink/expand rules.

**Q: Binary array, at most K zeros?**  
A: Problem 06 — same template, `k` is flip budget / zero budget.

**Q: Minimum window covering `t`?**  
A: Problem 11 — shortest valid, `need`/`formed` counters.

---

## 15. Complete runnable Java class

`LongestSubstringAtMostKDistinct.java` — brute + optimal + `main`.

---

## 16. Practice checklist

- [ ] Implement from memory with `HashMap`.
- [ ] Trace `"eceba"`, `k=2`.
- [ ] Explain link to Fruit Into Baskets (`k=2`).
- [ ] Contrast with Problem 11 (shortest vs longest).

---

## 17. Quick revision

> **Longest substring with ≤ k distinct — map counts, expand right, shrink while `size() > k`, update max length. K=2 = Fruit Into Baskets.**

---

## 18. Sliding Window core interview set complete (Problems 01–12)

You finished the **core Sliding Window** set in this folder. Review **`Sliding_Window_Common_Patterns.md`** and practise re-coding from memory.

| # | Problem | Type | Core idea |
|---:|---|---|---|
| 01 | Maximum Sum of K Consecutive Elements | Fixed | Enter/leave sum update |
| 02 | Maximum Average Subarray I | Fixed | Same window, divide by k |
| 03 | Minimum Size Subarray Sum | Variable | Shrink while sum ≥ target |
| 04 | Longest Substring Without Repeating Characters | Variable + set | Shrink on duplicate |
| 05 | Longest Repeating Character Replacement | Variable + freq | `length - maxFreq ≤ k` |
| 06 | Max Consecutive Ones III | Variable | At most k zeros |
| 07 | Fruit Into Baskets | Variable + map | At most 2 distinct types |
| 08 | Permutation in String | Fixed + freq | Window len(s1), counts match |
| 09 | Find All Anagrams in a String | Fixed + freq | Same slide, collect starts |
| 10 | Sliding Window Maximum | Fixed + deque | Decreasing deque of indices |
| 11 | **Minimum Window Substring** | Variable + freq | Shortest cover — `need` / `formed` |
| 12 | **Longest Substring At Most K Distinct** | Variable + map | Longest — at most k distinct |

### Patterns you now own

```text
Fixed:     sum/avg → freq/anagram → max via deque
Variable:  shortest sum → shortest cover → longest unique → longest “at most K”
```

Reusable closing line:

> **Two pointers bound a continuous window; update state on enter and leave; only the validity rule changes.**

### Suggested revision loop

1. One **fixed** problem (01 or 08) from a blank file.
2. One **variable shortest** (03 or 11).
3. One **variable longest at K** (07 or 12).
4. Problem **10** deque once a week — easy to forget.
5. Trace one dry run on paper before running Java `main`.

---

## 19. Independent practice challenge

Without code: for `s = "araaci"`, `k = 2`, what is the longest valid substring length?

Answer: **4** (`"araa"` has distinct `a` and `r`).

Then explain in one sentence how Problem 11 differs from this problem.