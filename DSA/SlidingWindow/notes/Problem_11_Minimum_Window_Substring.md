# Problem 11 — Minimum Window Substring

> **Pattern:** Variable-size sliding window — **shortest** valid window  
> **LeetCode:** 76  
> **Goal:** Return the **smallest** substring of `s` that contains **every** character in `t` (including duplicates). If impossible, return `""`.

---

## 1. Problem statement

Given strings `s` and `t`, return the minimum window substring of `s` such that every character in `t` (including duplicates) is included in the window. If there is no such substring, return the empty string `""`.

### Examples

```text
Input:  s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: "BANC" covers A, B, C — shorter than "ADOBEC" or "CODEBA".

Input:  s = "a", t = "a"
Output: "a"

Input:  s = "a", t = "aa"
Output: ""
Explanation: Need two a's; only one in s.
```

### Constraints (typical)

- `1 <= s.length, t.length <= 10^5`
- `s` and `t` consist of English letters

---

## 2. Mental picture — a shopping list inside a moving frame

Think of `t` as a **shopping list with quantities**:

```text
t = "AABC"  →  need:  A×2,  C×1  (B not in list)
```

Slide a frame over `s`. The frame is **valid** when everything on the list is inside the frame **at least** as many times as required.

```text
s:  A D O B E C O D E B A N C
            |-----------|
            ADOBEC  → has A,B,C but maybe not enough counts

                 want SMALLEST valid frame
```

```text
     RIGHT grows until valid  (you found enough items)
     LEFT  moves while still valid  (try to shrink the box)
```

Same spirit as **Problem 03 — Minimum Size Subarray Sum**, but validity is **frequency match**, not sum.

---

## 3. How to start thinking

1. **Substring** → continuous → sliding window.
2. **Smallest** that **covers** requirement → shortest valid window → shrink **while** still valid (like Problem 03).
3. **Duplicates in `t`** → you need **counts**, not a set.
4. How to know “we have enough” without comparing full maps every step? → **`need` / `have`** counter.

---

## 4. Approach 1 — Brute force

### Idea

For each start index `i`, grow end `j` until `s[i..j]` covers `t`. Track the shortest window found.

To check coverage: build frequency of `t` and subtract counts as you read the window (or recount each time).

### Pseudocode

```text
for start in 0..n-1:
  for end from start:
    add s[end] to window counts
    if window covers t:
      record length, break inner loop
return best substring
```

### Complexity

| | Brute |
|---|---|
| Time | `O(n²)` or `O(n² · Σ)` if you recount each window |
| Space | `O(Σ)` for frequency maps |

Good for understanding **what “covers” means** — not for interviews at large `n`.

---

## 5. Approach 2 — Optimal sliding window + frequency maps

### Two maps + one counter

| Symbol | Meaning |
|---|---|
| `need` | How many of each character `t` requires |
| `window` | How many of each character sit in `s[left..right]` |
| `required` | How many **distinct** characters appear in `t` (keys in `need`) |
| `formed` | How many of those distinct chars are **fully satisfied** right now |

**Fully satisfied** for character `c` means:

```text
window.get(c) == need.get(c)
```

When you first reach that equality, `formed++`.  
When you shrink and count drops **below** `need`, `formed--`.

Window is valid when:

```text
formed == required
```

### Loop shape (shortest window)

```text
left = 0
for right in 0..n-1:
  add s[right] to window
  maybe formed++

  while formed == required:        // valid — try shrink
    update best answer
    remove s[left] from window
    maybe formed--
    left++
```

### Java sketch

See `../MinimumWindowSubstring.java`.

```java
Map<Character, Integer> need = new HashMap<>();
Map<Character, Integer> window = new HashMap<>();
for (char c : t.toCharArray()) {
    need.put(c, need.getOrDefault(c, 0) + 1);
}
int required = need.size();
int formed = 0;
int left = 0;
// ... expand right, while (formed == required) shrink ...
```

---

## 6. Hardest part — `need` / `have` (`formed`) without scanning 26 cells every time

### Before (confusing)

“I’ll compare `window` and `need` maps on every step — if all keys match, it’s valid.”

That works but feels slow and easy to get wrong with extra keys in `window`.

### After (clear)

1. Only characters in **`need`** matter for `formed`.
2. When `window[c]` **becomes equal to** `need[c]`, one requirement is **met** → `formed++`.
3. When shrinking makes `window[c]` **drop below** `need[c]`, one requirement is **lost** → `formed--`.
4. Valid ⇔ `formed == required` (not “window map equals need map” literally — extra chars in `s` are fine).

```text
need:  A=1 B=1 C=1   required = 3

window has A=2 B=1 C=1  → still formed=3 (extras of A are OK)

window has A=1 B=1 C=0  → formed=2 → INVALID
```

**Critical:** Only increment `formed` when `need.containsKey(c)` and counts **hit** the target. Only decrement when counts **fall below** target.

---

## 7. Detailed dry run — s = "ADOBECODEBANC", t = "ABC"

`need`: A=1, B=1, C=1 → `required = 3`

Abbreviated trace:

| right | char | formed | note |
|---:|---|---:|---|
| 0 | A | 1 | A satisfied |
| 1 | D | 1 | |
| 2 | O | 1 | |
| 3 | B | 2 | B satisfied |
| 4 | E | 2 | |
| 5 | C | **3** | **valid** `"ADOBEC"` len 6 — record, shrink |
| … | … | … | shrink until invalid, expand again |
| end | … | 3 | best becomes `"BANC"` len 4 |

At `right=5`, window `"ADOBEC"` is valid (length 6).  
Shrink `left`: removing `A` drops `formed` — no longer valid.  
Later at `"BANC"` (indices 9..12), valid with length **4** — that is the minimum.

```text
s:  A D O B E C O D E B A N C
                      |----|
                      B A N C  → answer
```

---

## 8. Why the algorithm is correct

- **Expand** until valid: first time `formed == required` for a given `right`, you have **some** valid window ending at `right`.
- **Shrink while valid**: for that `right`, you find the **smallest** `left` still valid — hence the **shortest** window **ending at** `right`.
- Taking the minimum length over all `right` gives the global minimum window.

Each index enters and leaves the window at most once → `O(n)`.

---

## 9. Complexity comparison

| Method | Time | Space |
|---|---:|---:|
| Brute | `O(n²)` | `O(Σ)` |
| **Window + maps + formed** | **`O(n + |s| + |t|)`** | **`O(Σ)`** |

Σ = alphabet size (map size bounded by characters in `t` and window).

---

## 10. Edge cases

| s | t | ans |
|---|---|---|
| `"a"` | `"a"` | `"a"` |
| `"a"` | `"aa"` | `""` |
| `"aa"` | `"aa"` | `"aa"` |
| `"ab"` | `"b"` | `"b"` |
| `t` longer than `s` | | `""` |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| Shrink only with `if` once | Miss shorter windows |
| Using set instead of counts when `t` has duplicates | Wrong validity |
| `formed++` on every add of needed char | Must be **exactly when count reaches need** |
| Forgetting chars in `s` not in `t` | Usually OK — only check `need.containsKey` |
| Longest-window shrink rule | Wrong — this is **shortest** valid |

---

## 12. How to recognise this pattern

- “**Minimum** substring containing all characters of …”
- “**Cover** `t`” / “**includes every** character from `t`”
- Pair with Problem 03: same **shrink while valid**, different validity test.

---

## 13. Interview explanation — say this aloud

> “I build a frequency map for `t` called `need`. I slide `right` over `s`, updating a `window` map. I track how many distinct required characters are fully satisfied in a variable `formed`. When `formed` equals the number of unique characters in `t`, the window is valid. I record the smallest length and shrink from `left` while it stays valid. Each character enters and leaves once, so time is linear in the length of `s`.”

---

## 14. Interview follow-up Q&A

**Q: What if you need to return indices instead of the string?**  
A: Store `bestLeft` and `bestLen` when updating the answer.

**Q: ASCII only — faster?**  
A: `int[128]` arrays instead of `HashMap` — same logic.

**Q: What if all characters of `s` must be used exactly once?**  
A: Different problem (permutation) — fixed window + match (Problems 08–09).

**Q: Relation to Problem 12?**  
A: Problem 12 is **longest** window with **at most K distinct** — opposite goal, similar maps.

---

## 15. Complete runnable Java class

`MinimumWindowSubstring.java` — brute + optimal + `main`.

---

## 16. Practice checklist

- [ ] Build `need` and explain `required` vs `formed`.
- [ ] Code optimal version from memory.
- [ ] Trace `"ADOBECODEBANC"`, `"ABC"` on paper.
- [ ] Explain why shrink uses `while`, not `if`.
- [ ] Handle `t = "aa"`, `s = "a"` → `""`.

---

## 17. Quick revision

> **`need` map from `t`. Expand right. When `formed == required`, update min window and shrink left while still valid. Count hits equality → formed++; drop below need → formed--.**

---

## 18. Next problem

**Problem 12 — Longest Substring with At Most K Distinct Characters** — same frequency-map window idea, but **longest** valid window (generalises Fruit Into Baskets).