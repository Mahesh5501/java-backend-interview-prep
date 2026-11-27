# Problem 09 — Find All Anagrams in a String

> **Pattern:** Fixed-size window — length `|p|` + frequency counts  
> **LeetCode:** 438  
> **Goal:** Return **all start indices** in `s` where the substring is an **anagram** of `p`.  
> **Java file:** `../FindAllAnagramsInAString.java`  

---

## 1. Problem statement

Given strings `s` and `p`, return a list of all **start indices** of `p`’s anagrams in `s`.

An **anagram** of `p` is a permutation: same letters, same counts.

### Example

```text
Input:  s = "cbaebabacd", p = "abc"
Output: [0, 6]

Explanation:
- index 0: "cba"  → anagram of "abc"
- index 6: "bac"  → anagram of "abc"
```

### Constraints

- `s.length`, `p.length` ≤ `3 * 10^4`
- Lowercase English letters

---

## 2. Mental picture — one picture to keep in mind

Same camera as Problem 08 — frame width `|p|`, slid along `s`.

```text
p = "abc"  →  a:1, b:1, c:1

s:  c  b  a  e  b  a  b  a  c  d
    |-----|                    → index 0 match
              ...
                    |-----|     → index 6 match
```

Each time the window’s counts **match** `p`’s counts, **record `left`** (start index of the window).

```text
need[26]   ← from p
window[26] ← current frame

match?  →  answer.add(left)
slide:  --window[s[left]]
```

---

## 3. How to start thinking

1. Almost **identical** to Permutation in String (08).
2. Difference: boolean **any** match → **list all** start positions.
3. Still fixed `k = p.length()`.
4. Brute: sort each window vs sort `p` — fine for learning only.

---

## 4. Approach 1 — Brute force

### Java method

`findAnagramsBruteForce`

Sort `p` once. For each `start`, sort `s[start..start+k-1]`, if equal add `start`.

Time `O(n · k log k)`.

---

## 5. Approach 2 — Fixed window + frequency (optimal)

### Java method

`findAnagrams`

### Code shape

Same loop as Problem 08, but:

```java
if (arraysEqual(need, window)) {
    answer.add(left);
}
```

`left = right - k + 1` when window is full.

### Optional optimisation

Maintain `matches` count (how many of 26 letters satisfied) to avoid scanning 26 each time — still `O(n)` either way.

### Java

See `../FindAllAnagramsInAString.java`.

---

## 6. Hardest part — recording the **start** index

### Before (off-by-one)

“I’ll add `right` when I find a match.”

The window `[left..right]` has length `k`; the **start** of the substring is **`left`**, not `right`.

### After

When window is full at index `right`:

```text
left  = right - k + 1
right = right

substring s[left..right] has start = left
```

**Example**

```text
k=3, right=6  →  left=4?  No: left = 6-3+1 = 4 for indices... 
right=6, k=3  →  left = 6-3+1 = 4

Check: s[4..6] — for "cbaebabacd" index 4 is 'b', 5 'a', 6 'b' → "bab" not "bac"

Actually for index 6 match: window should be s[6..8] = "acd"? 
s = c b a e b a b a c d
    0 1 2 3 4 5 6 7 8 9
idx 6..8 = b,a,c = "bac" ✓

When right=8: left = 8-3+1 = 6 ✓ add 6
```

When `right=2`, `left=0` → `"cba"` ✓ add 0.

---

## 7. Detailed dry run

`s = "cbaebabacd"`, `p = "abc"`, `k = 3`

`need`: a=1,b=1,c=1

| right | c | left | window letters | match? | answer |
|---:|---|---:|---|---|---|
| 2 | a | 0 | cba | yes | [0] |
| 3 | e | 1 | bae | no | [0] |
| … | … | … | … | … | … |
| 8 | d | 6 | bac | yes | [0,6] |

(Fill middle rows on paper for practice.)

---

## 8. Why the algorithm is correct

Every anagram occurrence is a length-`k` window with multiset equal to `p`. The slide visits each length-`k` window exactly once at the moment `right` is the window’s right end, and records the correct `left` start.

No duplicates: each window has one start index.

---

## 9. Complexity comparison

| Method | Time | Space |
|---|---:|---:|
| Brute | `O(n · k log k)` | `O(k)` output |
| **Window + freq** | **`O(n)`** | **`O(1)`** aux + output list |

---

## 10. Edge cases

| Case | Output |
|---|---|
| `p` longer than `s` | `[]` |
| No anagram | `[]` |
| `p = "a"`, many `a` in `s` | index each `a` |
| Overlapping matches | include all (e.g. `s="aaa", p="aa"` → `[0,1]`) |

---

## 11. Common mistakes

- Adding `right` instead of `left`.
- Not removing `window[s[left]]` before next step (stale counts).
- Confusing Problem 08 return `true` with list here.

---

## 12. Comparison: Problem 08 vs 09

| | 08 Permutation in String | 09 Find All Anagrams |
|---|---|---|
| Question | Any permutation in `s2`? | All starts in `s` |
| Return | `boolean` | `List<Integer>` |
| On match | return `true` early | `answer.add(left)` |
| Core slide | **Same** | **Same** |

Memorise **one** template; adapt return value.

---

## 13. Interview explanation — say this aloud

> “This is the same fixed-size sliding window as checking for a permutation. I build frequency counts for p, maintain a window of that length on s, and when counts match I add the window’s start index. I slide by incrementing at right and decrementing the character leaving at left. O(n) time.”

---

## 14. Interview follow-up Q&A

**Q: Return count instead of indices?**  
A: `answer.size()`.

**Q: Words separated by space?**  
A: Tokenize — different problem unless still contiguous chars.

**Q: Case-insensitive anagrams?**  
A: Normalize case into counts first.

---

## Link to Java

| Approach | Method |
|---|---|
| Brute force (sort windows) | `findAnagramsBruteForce` |
| Fixed window + int[26] (optimal) | `findAnagrams` |

Run `main` in the Java class to exercise every approach.

---

## 15. Runnable Java

Use **Link to Java** above; open the file and run `main`.

---

## 16. Practice checklist

- [ ] Reuse 08 template; only change to list + collect all.
- [ ] Trace indices 0 and 6 on paper.
- [ ] Handle `p.length() > s.length()`.

---

## 17. Quick revision

> **Same as permutation check, but push `left` into a list every time counts match.**

---

## 18. Next problem

**Problem 10 — Sliding Window Maximum** — fixed window again, but answer needs **max value** each slide → **monotonic deque**.