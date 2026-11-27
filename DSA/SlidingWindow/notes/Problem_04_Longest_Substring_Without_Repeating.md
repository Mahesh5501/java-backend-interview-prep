# Problem 04 — Longest Substring Without Repeating Characters

> **Pattern:** Variable-size window + **HashSet** (or HashMap)  
> **Goal:** Length of longest substring with **all distinct** characters (LeetCode 3).  
> **Java file:** `../LongestSubstringWithoutRepeating.java`  

---

## 1. Problem statement

Given a string `s`, return the length of the **longest substring** without repeating characters.

### Examples

```text
Input:  s = "abcabcbb"
Output: 3
Explanation: "abc" (also "bca", "cab" — length 3 max)

Input:  s = "bbbbb"
Output: 1

Input:  s = "pwwkew"
Output: 3
Explanation: "wke"

Input:  s = ""
Output: 0
```

---

## 2. Mental picture — a basket that cannot hold duplicates

```text
s:  a b c a b c b b
    ^-----^
    window has {a,b,c} — OK

        try add 'a' again → DUPLICATE
        remove from left until 'a' is gone

    shrink →  b c a
                ^---^
```

```text
     LEFT shrinks when duplicate
     RIGHT always expands
     SET holds what's inside [left..right]
```

---

## 3. Thinking questions

1. **Subarray vs substring?** Same idea — continuous characters.
2. **How do I know there's a duplicate?** Set contains `s.charAt(right)`.
3. **How to fix duplicate?** Remove `s.charAt(left)` from set, `left++`, repeat until valid.
4. **Track answer when?** After each valid expansion: `max(len, right-left+1)`.

---

## 4. Approach 1 — Brute force

### Java method

`lengthOfLongestSubstringBruteForce`

For each start, extend end while chars are unique (use a set per inner loop).

Time `O(n²)` worst case, space `O(Σ)`.

---

## 5. Approach 2 — Sliding window + HashSet

### Java method

`lengthOfLongestSubstring`

### Loop

```text
for right in 0..n-1:
  while s[right] in set:
    set.remove(s[left])
    left++
  set.add(s[right])
  best = max(best, right - left + 1)
```

### Why `while` on duplicate?

Rare but possible with pathological patterns — standard pattern for repeat at `right`.

### Dry run — "abcabcbb"

| right | char | set after fix | window | len | best |
|---:|---|---|---|---:|---:|
| 0 | a | {a} | a | 1 | 1 |
| 1 | b | {a,b} | ab | 2 | 2 |
| 2 | c | {a,b,c} | abc | 3 | **3** |
| 3 | a | dup→shrink | bca | 3 | 3 |
| 4 | b | dup→shrink | cab | 3 | 3 |
| ... | | | | | 3 |

---

## 5b. Approach 3 — HashMap last index (jump left)

Store last seen index; jump `left` past the previous duplicate.

### Java method

`lengthOfLongestSubstringMap`

---

## 6. Hardest part — shrinking from the left

### Worry

“Do I remove only one char or jump left?”

### Set version

Remove **one at a time** until duplicate is gone.

### Map version (optimization)

Store last index of each char. If `lastIndex[c] >= left`, set `left = lastIndex[c] + 1`.

```text
"abba" with only set → careful stepping
"abba" with map → jump left past previous 'a'
```

Both `O(n)` time.

---

## 7. Detailed dry run — "pwwkew"

```text
p → {p} len 1
w → {p,w} len 2
w → duplicate → remove p,w → {w} len 1 at w
k → {w,k} len 2
e → {w,k,e} len 3 best=3
w → duplicate → shrink to {k,e,w}? step by step → max 3
```

Answer **3** ("wke").

---

## 8. Why correct

- Window always has distinct chars — enforced before adding new char.
- Every valid substring appears as some `[left, right]` when `right` is its end and duplicates are resolved.
- `best` tracks maximum length over all steps.

---

## 9. Complexity

| Method | Time | Space |
|---|---:|---:|
| Brute | `O(n²)` | `O(Σ)` |
| Set window | `O(n)` — each char enters/leaves set once | `O(min(n,Σ))` |
| Map jump | `O(n)` | `O(Σ)` |

Σ = alphabet size (26, 128, or Unicode map).

---

## 10. Edge cases

| s | ans |
|---|---|
| `""` | 0 |
| `"a"` | 1 |
| `"dvdf"` | 3 |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| `if` instead of `while` on duplicate | Stale chars in set |
| Forgetting to add char after shrink | Empty window logic bugs |
| Using sub**sequence** | Not continuous — wrong pattern |

---

## 12. Interview — say aloud

> “I keep a set of characters in the current window. I expand right. If the new character is already in the set, I shrink from the left until it's safe, then add it. I track the max window length. Each character is added and removed at most once — O(n) time.”

---

## 13. Follow-ups

**Q: Print the substring?**  
Track `bestLeft` when updating `best`.

**Q: At most K distinct characters?**  
Different problem — use frequency map and shrink when distinct > K.

**Q: ASCII only?**  
`int[128]` instead of set for speed.

---

## Link to Java

| Approach | Method |
|---|---|
| Brute force | `lengthOfLongestSubstringBruteForce` |
| Sliding window + HashSet | `lengthOfLongestSubstring` |
| HashMap last index | `lengthOfLongestSubstringMap` |

Run `main` in the Java class to exercise every approach.

---

```text
"abcabcbb" → 3
"pwwkew"   → 3
```

---

## 15. Practice checklist

- [ ] Code set version from memory.
- [ ] Trace "abcabcbb" with L,R, set contents.
- [ ] Explain map jump variant.

---

## 16. Quick revision

> **Expand right. While duplicate: remove s[left], left++. Add s[right]. Update max len.**

---

## 17. Next problem

**Problem 05 — Longest Repeating Character Replacement**  
Window may repeat chars if you have `k` replacements — use **frequencies**.