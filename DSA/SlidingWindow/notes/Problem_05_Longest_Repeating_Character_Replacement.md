# Problem 05 — Longest Repeating Character Replacement

> **Pattern:** Variable window + **frequency array**  
> **Goal:** Longest substring you can make with **only one letter** after at most `k` changes (LeetCode 424).  
> **Java file:** `../LongestRepeatingCharacterReplacement.java`  

---

## 1. Problem statement

You are given a string `s` and integer `k`. In one operation you may pick any character and change it to **any** uppercase English letter.

Return the length of the **longest** substring that can become **all the same character** using at most `k` operations.

### Example 1

```text
Input:  s = "AABABBA", k = 1
Output: 4
```

Explanation: Change one `B` to `A` → `"AAAA"` length 4 in window `"AABA"` (multiple valid windows).

### Example 2

```text
Input:  s = "ABAB", k = 2
Output: 4
```

Change both `B` to `A` (or both `A` to `B`).

---

## 2. Mental picture — majority vote inside the window

```text
Window:  A A B A
         ^^^^^
counts:  A:3  B:1
length = 4
maxFreq (most common) = 3
others = 4 - 3 = 1 replacement  ≤ k=1  → VALID
```

```text
Window:  A A B A B
         ^^^^^^^
A:3 B:2 → need 2 replacements  ≤ k=1  → INVALID → shrink left
```

> **Keep the dominant letter. Change everyone else.**

---

## 3. Thinking questions

1. **Which char do we try to make uniform?** The one with **highest count** in the window (best ROI).
2. **How many changes needed?** `windowLength - maxFrequency`.
3. **When invalid?** `windowLength - maxFreq > k` → shrink from left.
4. **Do we try all 26 letters explicitly?** No — `maxFreq` in the window captures the best choice implicitly.

---

## 4. Approach 1 — Brute force

### Java method

`characterReplacementBruteForce`

For each start, extend end, maintain counts, check `len - maxFreq <= k`.

Time `O(n²)`, space `O(1)` (26 counts per window).

---

## 5. Approach 2 — Optimal sliding window

```java
int[] freq = new int[26];
int left = 0, maxFreq = 0, best = 0;
for (int right = 0; right < s.length(); right++) {
    freq[s.charAt(right) - 'A']++;
    maxFreq = Math.max(maxFreq, freq[...]);
    while ((right - left + 1) - maxFreq > k) {
        freq[s.charAt(left) - 'A']--;
        left++;
    }
    best = Math.max(best, right - left + 1);
}
```

### Java method

`characterReplacement`

### Validity formula (memorize)

```text
replacementsNeeded = windowLen - maxFreq
VALID  when replacementsNeeded <= k
```

---

## 6. Hardest part — does maxFreq need to decrease on shrink?

### Worry

“When I shrink, I don't recompute maxFreq — is it stale?”

### Answer

**Yes, maxFreq can be stale** when the tallest pile loses chars. That sounds scary.

**Why the answer is still correct:**

- Stale `maxFreq` only makes `replacementsNeeded` look **smaller** → we might not shrink when we should.
- That can make the window **longer** in calculation — but the **true** longest valid window length is still captured because when we eventually shrink enough, `best` is updated correctly.
- Classic proof trick: `maxFreq` never needs to decrease for finding **maximum length** in this formulation (interview-level: cite the formula and O(n) window).

**Beginner safe rule:** Recompute maxFreq inside `while` if you want clarity (still O(26·n) = O(n)).

### Before / after shrink

```text
Before: [A A B A B]  maxFreq=3  len=5  need 2 > k=1  INVALID
After left++: shrink until need ≤ k
```

---

## 7. Detailed dry run — "AABABBA", k=1

```text
right=0 A  freq A=1 maxF=1 len=1 ok best=1
right=1 A  A=2 maxF=2 len=2 ok best=2
right=2 B  A=2 B=1 maxF=2 len=3 need=1 ok best=3
right=3 A  A=3 maxF=3 len=4 need=1 ok best=4  ← answer
right=4 B  need 2 >1 → shrink...
...
```

ASCII at best:

```text
A A B A B B A
^-------^
"AABA" → 4 with 1 change
```

---

## 8. Why correct

If `windowLen - maxFreq <= k`, we can change all non-majority chars to the majority char using at most `k` ops.

We explore all windows by moving `right` and minimally shrinking `left` when invalid — standard longest-valid-window argument. `best` stores the maximum valid length seen.

---

## 9. Complexity

| Method | Time | Space |
|---|---:|---:|
| Brute | `O(n²)` | `O(1)` |
| Sliding window | `O(n)` — each char enters/leaves once | `O(1)` — 26 ints |

---

## 10. Edge cases

| Case | Note |
|---|---|
| `k = 0` | Longest run of same char |
| All same char | `s.length()` |
| `k` very large | Whole string |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| Wrong formula `k - windowLen` | Inverted logic |
| Forgetting uppercase `'A'` offset | Wrong index |
| Shrinking with `if` once when need many steps | `while` required |
| Trying to pick replacement char in outer loop | Window maxFreq is enough |

---

## 12. Interview — say aloud

> “In any window, I count letters. The character with highest frequency is the one I'd keep. Others need `windowLen - maxFreq` replacements. If that's at most k, the window is valid. I expand right, shrink left while invalid, and track the max length. Twenty-six frequency slots — O(n) time.”

---

## 13. Follow-ups

**Q: Binary search on answer length?**  
A: Possible alternative — less common than window.

**Q: Recompute maxFreq on each shrink?**  
A: Clearer for teaching; still O(26n).

**Q: Lowercase strings?**  
Use `'a'` offset or `HashMap`.

---

## Link to Java

| Approach | Method |
|---|---|
| Brute force | `characterReplacementBruteForce` |
| Sliding window + int[26] (optimal) | `characterReplacement` |

Run `main` in the Java class to exercise every approach.

---

```text
"AABABBA", k=1 → 4
"ABAB", k=2 → 4
```

---

## 15. Practice checklist

- [ ] Write formula `len - maxFreq <= k` on flash card.
- [ ] Implement with `int[26]`.
- [ ] Trace "AABABBA" to length 4.
- [ ] Explain why positives / counts fit in window.

---

## 16. Quick revision

```text
VALID ⟺ windowLen - maxFreq <= k
expand right, shrink left while INVALID
track max windowLen
```

---

## 17. Next problem

**Problem 06 — Max Consecutive Ones III**  
Same variable window spirit — at most `k` zeros inside the window.