# Problem 03 — Valid Anagram

> **Pattern:** Frequency count (or sort-and-compare)  
> **Java file:** `../ValidAnagram.java`  
> **Goal:** Clear approaches — sort vs count array vs map — and why interviews prefer `int[26]`.

---

## 1. Problem statement

Given two strings `s` and `t`, return `true` if `t` is an **anagram** of `s`.

Anagram = same characters with the **same frequencies**, order does not matter.

### Example 1

```text
Input:  s = "anagram", t = "nagaram"
Output: true
```

### Example 2

```text
Input:  s = "rat", t = "car"
Output: false
```

### Constraints (typical LeetCode)

- Same length required for anagrams (different length → false immediately)
- Often lowercase English letters only → `int[26]` works
- If Unicode / mixed case → use `HashMap` instead of `int[26]`

---

## 2. Mental picture

Two strings are anagrams if their character **bags** match.

```text
s = "listen"   bag: e:1 i:1 l:1 n:1 s:1 t:1
t = "silent"   bag: e:1 i:1 l:1 n:1 s:1 t:1  → same → true
```

Ways to compare bags:

1. Sort both strings → must become identical
2. Count letters in both → counts must match

### Jargon

| Term | Meaning |
|---|---|
| **Frequency** | How many times a character appears |
| **int[26]** | Fixed array for `'a'`..`'z'`; index = `ch - 'a'` |
| **Canonical form** | Sorted string used as a fingerprint |

---

## 3. How to start thinking

1. Different lengths? → false immediately.
2. Brute / simple? Sort both and compare.
3. Faster / common interview? Count characters.
4. Alphabet size known? Prefer `int[26]` over HashMap.
5. Unicode? Must use HashMap (or similar).

---

## 4. Approach 1 — Sort both strings

### Idea

```text
sort(s) == sort(t)  →  anagram
```

```text
"anagram" → "aaagmnr"
"nagaram" → "aaagmnr"  → equal → true
```

### Why it works

Sorting puts the same multiset of characters into the same order.

### Java method

`isAnagramSort`

```java
public static boolean isAnagramSort(String s, String t) {
    if (s.length() != t.length()) return false;
    char[] a = s.toCharArray();
    char[] b = t.toCharArray();
    Arrays.sort(a);
    Arrays.sort(b);
    return Arrays.equals(a, b);
}
```

### Complexity

| Time | Extra space |
|---|---|
| `O(n log n)` | `O(n)` for char arrays (depending on language) |

### When to use

Quick to write; fine if `n` is small. Good backup if you blank on counting.

---

## 5. Approach 2 — Count array `int[26]` (optimal — what we use)

### Core idea

Assume lowercase `a`–`z`:

1. If lengths differ → false
2. One array `count[26]`
3. For each index `i`: `count[s[i]-'a']++` and `count[t[i]-'a']--`
4. If every bucket is `0` → same frequencies → true

### Why this approach?

- One pass over both strings (same length)
- Fixed alphabet → `O(1)` extra space (26 slots)
- Faster than sorting: `O(n)` time

### Why increment and decrement together?

After processing both strings, leftovers cancel. Any leftover ≠ 0 means a mismatch.

You can also count `s` fully, then decrement with `t` and fail early on negative — same idea.

### Java method

`isAnagram`

```java
public static boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;

    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }
    for (int c : count) {
        if (c != 0) return false;
    }
    return true;
}
```

### Dry run

`s = "rat"`, `t = "car"`

| step | action | count idea |
|---|---|---|
| i=0 | +r, -c | r:1, c:-1 |
| i=1 | +a, -a | r:1, c:-1, a:0 |
| i=2 | +t, -r | r:0, c:-1, t:1 |

`c` is `-1`, `t` is `1` → not all zero → **false**

### Complexity

| Measure | Value |
|---|---|
| **Time** | `O(n)` |
| **Extra space** | `O(1)` — fixed 26 |

---

## 6. Approach 3 — HashMap frequency (when alphabet is huge)

### Idea

`Map<Character, Integer>` for counts of `s`, then decrement with `t`.

### Why use Map instead of int[26]?

- Unicode characters
- Mixed case without normalizing
- Alphabet not limited to 26 letters

### Why we don’t default to Map here

LeetCode Valid Anagram is usually lowercase English → `int[26]` is simpler and `O(1)` space.

*(Map version can be added later if you practise Unicode follow-ups; core file keeps sort + int[26].)*

---

## 7. What we use and why

| Approach | Java method | Time | Space | Use when |
|---|---|---|---|---|
| Sort + equals | `isAnagramSort` | `O(n log n)` | `O(n)` | Quick / backup |
| **Count int[26]** | `isAnagram` | `O(n)` | `O(1)` | **Default interview** |
| HashMap counts | *(follow-up)* | `O(n)` | `O(k)` alphabet | Unicode / unknown alphabet |

---

## 8. Hardest part

### Mistake — Forgetting length check

Different lengths can never be anagrams. Check first.

### Mistake — Using int[26] on non a–z

`'A' - 'a'` is wrong index. Confirm constraints.

### Mistake — Comparing with `==` on Strings

After sorting char arrays, use `Arrays.equals`, not `==`.

---

## 9. Edge cases

| Input | Expected | Checks |
|---|---|---|
| `"", ""` | true | Empty |
| `"a", "a"` | true | Single |
| `"ab", "a"` | false | Length |
| `"aacc", "ccac"` | false | Frequency mismatch |

---

## 10. Interview explanation — say this aloud

“First I check lengths. Brute-ish approach: sort both and compare — `O(n log n)`. Better: count letters with `int[26]`. I increment for `s` and decrement for `t` in one loop; if every count is zero, they are anagrams. Time `O(n)`, space `O(1)` for fixed alphabet. If the alphabet were Unicode, I’d switch to a HashMap.”

---

## 11. Link to Java

| Approach | Method |
|---|---|
| Sort | `isAnagramSort` |
| Count int[26] (preferred) | `isAnagram` |

Run `main` to try both.
