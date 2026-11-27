# Problem 08 — Permutation in String

> **Pattern:** Fixed-size window — length `|s1|` + frequency counts  
> **LeetCode:** 567  
> **Goal:** Return `true` if `s2` contains **some contiguous substring** that is a **permutation** of `s1`.  
> **Java file:** `../PermutationInString.java`  

---

## 1. Problem statement

Given strings `s1` and `s2`, return `true` if `s2` contains a permutation of `s1`.

A permutation of `s1` has the **same letters with the same counts** (any order).

### Examples

```text
Input:  s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: "ba" at indices 3..4 is a permutation of "ab".

Input:  s1 = "ab", s2 = "eidboaoo"
Output: false
```

### Constraints

- `s1.length`, `s2.length` ≤ `10^4`
- Lowercase English letters only

---

## 2. Mental picture — one picture to keep in mind

A permutation of `s1` is not “sorted `s1`” — it is any **reordering** with identical letter counts.

```text
s1 = "ab"  →  need: a=1, b=1

s2:  e  i  d  b  a  o  o  o
              |--|
         window size 2 = "ba" ✓ same counts
```

Slide a **rigid frame** of width `k = s1.length()` along `s2`:

```text
k = |s1|

  enter char at right edge of frame
  leave char at left edge when frame is full

Compare: frequency in frame == frequency of s1 ?
```

Use `int[26]` for `a`–`z` — fast and interview-friendly.

---

## 3. How to start thinking

1. Window size is **fixed** — always `s1.length()`.
2. Check is **not** alphabetical equality — it is **multiset equality** (counts).
3. Brute: extract every substring of length `k`, sort, compare — slow.
4. Better: maintain count array for the current window; update in O(1) per step.

---

## 4. Approach 1 — Brute force (sort each window)

### Java method

`checkInclusionBruteForce`

### Idea

Sort characters of `s1` once. For each `start` in `s2`, take `s2[start..start+k-1]`, sort, compare.

### Complexity

| | Brute |
|---|---|
| Time | `O(n · k log k)` |
| Space | `O(k)` |

### Limitation

Sorting every window is wasteful when counts update in constant time.

---

## 5. Approach 2 — Fixed window + frequency (optimal)

### Java method

`checkInclusion`

### Step-by-step

1. If `s1.length() > s2.length()`, return `false`.
2. Build `need[26]` from `s1`.
3. `window[26] = {0}`, `k = s1.length()`.
4. For `right` from `0` to `s2.length()-1`:
   - `window[s2[right]-'a']++`.
   - `left = right - k + 1`.
   - If `left < 0`, window not full yet — continue.
   - If `need` equals `window`, return `true`.
   - `window[s2[left]-'a']--` (slide).

### Alternative style (Problems 1–2 in folder)

Build first window of `k` characters, then loop `right` from `k` with leave at `right-k`.

Both are the same fixed-window pattern.

### Java

See `../PermutationInString.java`.

---

## 6. Hardest part — equality check every step

### Before (slow)

Loop 26 cells to compare `need` and `window` at every `right` — still `O(26·n) = O(n)`, acceptable.

### After (smarter, optional)

Track **`matches`** — how many letters have `need[i] == window[i]`. Increment/decrement `matches` when counts change; when `matches == 26`, you have a permutation.

For beginners, **full array compare** is easier to code correctly in interviews.

**Before / after — wrong goal**

```text
Before: "Check if sorted window equals sorted s1"  →  works but sorts each time

After:  "Check if count arrays are equal"  →  O(1) update per slide
```

**Before / after — wrong window size**

```text
s1 = "abc" (k=3), comparing window size 2  →  never a permutation
Always k = s1.length()
```

---

## 7. Detailed dry run

`s1 = "ab"`, `s2 = "eidbaooo"`, `k = 2`

`need`: a=1, b=1 (others 0)

| right | char | window (a,b,…) | left | substring | match? |
|---:|---|---|---|---|---|
| 0 | e | e:1 | -1 | (not full) | |
| 1 | i | e,i | 0 | "ei" | no |
| 2 | d | … | 1 | "id" | no |
| 3 | b | a:0,b:1,… | 2 | "db" | no |
| 4 | a | a:1,b:1 | 3 | **"ba"** | **yes** → return true |

Role diagram at success:

```text
s2:  e  i  d  b  a  o  o  o
              L  R
              "ba"
```

---

## 8. Why the algorithm is correct

Every length-`k` substring of `s2` appears exactly once as the window when `right` reaches its last index inside that substring.

Comparing multisets is equivalent to “is this substring a permutation of `s1`?”.

---

## 9. Complexity comparison

| Method | Time | Space |
|---|---:|---:|
| Sort each window | `O(n · k log k)` | `O(k)` |
| **Fixed window + freq** | **`O(n)`** | **`O(1)`** (26 ints) |

---

## 10. Edge cases

| Case | Result |
|---|---|
| `s1` longer than `s2` | `false` |
| `s1` equals some window exactly | `true` |
| Same letters, wrong length window | Must be exactly `|s1|` |
| `s1 = "a"`, `s2` contains `a` | `true` |

---

## 11. Common mistakes

- Using variable-size window (not needed).
- Forgetting to subtract `window[left]` when sliding.
- Sorting `s1` and window as strings but ignoring duplicate letters in count sense.
- Off-by-one on window start: `left = right - k + 1`.

---

## 12. How to recognise this pattern

- “Permutation / anagram of X in Y”
- Window length = **`|pattern|`**
- **Frequency map** or `int[26]`

**Next problem (09)** collects **all** start indices when counts match — same machinery.

---

## 13. Interview explanation — say this aloud

> “A permutation of s1 has the same character counts. I use a fixed window of s1’s length on s2. I keep a 26-length frequency array for the window, add the new right character, and when the window is full, compare to s1’s counts. Then I remove the left character and slide. If any window matches, return true. O(n) time and O(1) space.”

---

## 14. Interview follow-up Q&A

**Q: Minimum window substring containing permutation?**  
A: Variable window — harder (LeetCode 76 family).

**Q: Unicode not just `a-z`?**  
A: Use `HashMap<Character,Integer>` instead of `int[26]`.

**Q: Case sensitivity?**  
A: Normalize or separate counts for A/Z.

---

## Link to Java

| Approach | Method |
|---|---|
| Brute force (sort windows) | `checkInclusionBruteForce` |
| Fixed window + int[26] (optimal) | `checkInclusion` |

Run `main` in the Java class to exercise every approach.

---

## 15. Runnable Java

Use **Link to Java** above; open the file and run `main`.

---

## 16. Practice checklist

- [ ] Build `need` and slide window from memory.
- [ ] Trace `s1="ab"`, `s2="eidbaooo"`.
- [ ] State why fixed size = `|s1|`.

---

## 17. Quick revision

> **Fixed window of |s1|; compare letter counts each slide; match ⇒ permutation found.**

---

## 18. Next problem

**Problem 09 — Find All Anagrams in a String** — same fixed window, record every matching **start index**.