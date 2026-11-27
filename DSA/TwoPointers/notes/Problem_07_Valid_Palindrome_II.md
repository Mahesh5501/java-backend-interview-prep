# Problem 07 — Valid Palindrome II

> **Pattern:** Opposite-direction pointers + **one mismatch branch**  
> **Goal:** Decide if the string can become a palindrome after deleting **at most one** character.

---

## 1. Problem statement

Given a string `s`, return `true` if you can make it a palindrome by deleting **at most one** character.

If it is already a palindrome, return `true`.

### Example 1

```text
Input:  s = "aba"
Output: true
```

Already a palindrome — no delete needed.

### Example 2

```text
Input:  s = "abca"
Output: true
```

Delete `'c'` → `"aba"`, which is a palindrome.

### Example 3

```text
Input:  s = "abc"
Output: false
```

Even after one delete, it cannot be a palindrome (try deleting each letter yourself).

### Constraints

- `1 <= s.length <= 10^5`
- `s[i]` is lowercase English letter

---

## 2. Mental picture — one picture to keep in mind

Normal palindrome check — two fingers walk inward:

```text
s = "abca"

index:  0  1  2  3
char:   a  b  c  a
        L        R
```

- Compare `a` and `a` → match → move in.
- Compare `b` and `c` → **mismatch**.

You get **one free delete**. So ask:

```text
Skip left?  (delete b) → check indices 2..3 → "ca" → fails
Skip right? (delete c) → check index 1..1 → "b" alone → OK
```

At most **one** mismatch “event” where you try both skips and stop.

```text
        L     R
s:  a  b  c  a
    |  |  |  |
    ok  ok MISMATCH → try delete L or delete R once
```

---

## 3. How to start thinking (simple questions)

1. **Same as Valid Palindrome?** Almost — but one mismatch may be OK if fixed by one delete.
2. **When do we stop early?** First mismatch: try both skips; if either inner range is palindrome, return true.
3. **Brute force?** Try deleting each index, test palindrome — works, slow.
4. **Key insight?** Until mismatch, behave exactly like Problem 01. After mismatch, **no more deletes** — just check a substring.

---

## 4. Approach 1 — Brute force (try delete each character)

### Idea

For every index `skipIndex`:

1. Build a string without that character.
2. Check if the result is a plain palindrome.
3. If any skip works, return `true`.

### Why does it work?

If at most one delete can fix the string, that delete removes one specific index. Brute force tries every index, so it finds the right one.

### Java method

`validPalindromeBruteForce` in `ValidPalindromeII.java`

```java
public static boolean validPalindromeBruteForce(String s) {

    for (int skipIndex = 0; skipIndex < s.length(); skipIndex++) {
        StringBuilder candidate = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != skipIndex) {
                candidate.append(s.charAt(i));
            }
        }
        if (isPlainPalindrome(candidate.toString())) {
            return true;
        }
    }
    return false;
}
```

### Dry run on `"abca"`

| skip | string left | palindrome? |
|---|---|---|
| 0 | `bca` | no |
| 1 | `aca` | yes → **return true** |

(Also skipping index 2 gives `aba` — would also work.)

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n²)` — n candidates × O(n) check each |
| **Extra space** | `O(n)` for temporary strings |

### Limitation

Too slow for `n = 10^5`. Need **one linear scan**.

---

## 5. Approach 2 — Optimal: two pointers + one skip branch

### Step-by-step

1. `left = 0`, `right = s.length() - 1`.
2. While `left < right`:
   - If `s[left] == s[right]`: `left++`, `right--`.
   - Else: **first mismatch** — return  
     `isPalindromeRange(s, left+1, right) || isPalindromeRange(s, left, right-1)`.
3. If loop finishes, return `true`.

`isPalindromeRange` is normal two-pointer palindrome on `s[left..right]` with **zero** deletes left.

### Visual frames — `"abca"`

**Frame 1**

```text
a  b  c  a
L        R     match a,a → L=1, R=2
```

**Frame 2**

```text
   b  c
   L  R          mismatch b != c
```

**Frame 3 — branch A (pretend we deleted `b` at index 1)**

Check `isPalindromeRange(s, 2, 3)` → substring `"ca"`

```text
c  a
L  R            mismatch → branch fails
```

**Frame 3 — branch B (pretend we deleted `c` at index 2)**

Check `isPalindromeRange(s, 1, 1)` → one character `'b'`

```text
b
^
L,R             length 1 → palindrome → return true
```

Deleting `c` gives the effective string `"aba"`.

### Why pointers move

| Situation | Action |
|---|---|
| Chars match | Both move inward — still have 0 or 1 delete budget unused |
| First mismatch | **Stop** looping; spend the one delete on left **or** right; inner check uses **no** more deletes |

### Java method

`validPalindromeTwoPointers` in `ValidPalindromeII.java` (optimal)

```java
public static boolean validPalindromeTwoPointers(String s) {

    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        if (s.charAt(left) == s.charAt(right)) {
            left++;
            right--;
        } else {
            return isPalindromeRange(s, left + 1, right)
                    || isPalindromeRange(s, left, right - 1);
        }
    }

    return true;
}

private static boolean isPalindromeRange(String s, int left, int right) {
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}
```

---

## 6. Hardest part — only one mismatch allowed

### Before / after thinking

**Wrong:** On mismatch, skip left and **continue** the main loop with another skip later.

```text
s = "abbxba"   (made-up)
```

If you “skip and continue,” you might use **two** deletes by accident.

**Right:** First mismatch triggers **exactly one** choice: pretend we deleted left **or** right, then run a **strict** palindrome check with **no** more skips.

### Example `"ecckkce"` style

If two mismatches appear inside the helper check, return false — you already used your one delete.

### Before / after on `"abc"`

```text
a b c
L   R   a!=c immediately

Try skip left → "bc" → not palindrome
Try skip right → "ab" → not palindrome
Return false ✓
```

---

## 7. Detailed dry run with role diagram

**`s = "abca"`**

| step | L | R | chars | action |
|---|---:|---:|---|---|
| 1 | 0 | 3 | a,a | match → L=1,R=2 |
| 2 | 1 | 2 | b,c | mismatch — try branches |
| 3a | — | — | skip L (`left+1..right` = `2..3`) | `"ca"` → not palindrome |
| 3b | — | — | skip R (`left..right-1` = `1..1`) | one char `b` → palindrome → **true** |

Role diagram at mismatch:

```text
s:  a  b  c  a
        L  R
        \  /
     mismatch → fork once, no second delete
```

---

## 8. Why the algorithm is correct

**If the main loop finishes:** Every compared pair matched. The whole string is already a palindrome → return `true`.

**If we hit one mismatch:** Any valid solution can delete **at most one** character. That delete must be either the current left index or the current right index (other deletes would leave this mismatch in place). We try both possibilities exhaustively with `isPalindromeRange`, which allows **no** further deletes. If either sub-range is a palindrome, a single delete exists → `true`. If both fail, **no** single delete fixes the string → `false`.

**No missed cases:** Brute force proves that trying each delete index is complete. The optimal method only checks the two deletes that matter at the **first** mismatch — if a solution exists, the first mismatch is where the delete must occur when scanning from both ends.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Try delete each index | `O(n²)` | `O(n)` for rebuilt strings |
| Two pointers + helper | `O(n)` | `O(1)` |

The helper runs at most **once**, after at most one mismatch in the main scan. Total work is still linear.

---

## 10. Edge cases

| Input | Output | Reason |
|---|---|---|
| `"a"` | true | One character |
| `"aa"` | true | Already palindrome |
| `"ab"` | true | Delete either end |
| `"abc"` | false | No single delete works |
| `"aguokepatgbnvfqmgmlcupufeimckduqdeupcjgzkyacpcdqzqhbwpebdjyuyeboqp"` | false | Classic long false case |
| `"deeee"` | true | Delete one `e` from middle run |

---

## 11. Common mistakes

| Mistake | Why it breaks |
|---|---|
| Allowing **two** skips in the main loop | Violates “at most one delete” |
| Using `continue` after mismatch instead of `return` | May consume two deletes |
| Forgetting helper is **strict** (no more skips) | False positives |
| Building new string in optimal path | Works but wastes `O(n)` space |
| Off-by-one in range `left+1..right` | Wrong substring tested |

---

## 12. How to recognise this pattern

**Signals:**

- Palindrome language + “remove / delete at most **k** characters”
- Opposite pointers on a string or array
- **Branch** when values differ

**Compare to earlier problems:**

| Problem | On mismatch | Extra |
|---|---|---|
| Valid Palindrome (01) | return false | none |
| **Valid Palindrome II (07)** | try skip L or R once | branch |
| Move Zeroes (05) | N/A | read/write |
| Remove Duplicates (06) | N/A (sorted) | read/write |

---

## 13. Interview explanation — say this aloud

> “I use left and right pointers like a normal palindrome check. While characters match, I move both inward. On the first mismatch, I’m allowed one delete — so I check whether the substring skipping the left character is a palindrome, or skipping the right character is a palindrome. The helper does a strict two-pointer check with no further deletes. If either works, return true; otherwise false. Overall O(n) time and O(1) space.”

---

## 14. Interview follow-up Q&A

**Q: At most **two** deletes?**  
A: LeetCode 1008 — recurse or try both branches at each mismatch; still two-pointer style but more branches.

**Q: Can you solve without a helper method?**  
A: Yes — inline the same while-loop twice in the mismatch branch. Helper is clearer for learning.

**Q: What about ignoring non-alphanumeric?**  
A: Add skip loops like Problem 01 before compare; branch logic stays the same on cleaned indices (LeetCode 125 variant).

**Q: Recursive solution?**  
A: On mismatch, `return dfs(left+1, right, used) || dfs(left, right-1, used)` with a `used` flag — same idea, `O(n)` depth worst case.

---

## 15. Complete runnable Java class

Full source: `../ValidPalindromeII.java`.

```java


public class ValidPalindromeII {

    public static boolean validPalindromeBruteForce(String s) {
        if (s.length() <= 1) {
            return true;
        }
        for (int skip = 0; skip < s.length(); skip++) {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (i != skip) {
                    b.append(s.charAt(i));
                }
            }
            if (isPlainPalindrome(b.toString())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPlainPalindrome(String text) {
        int left = 0, right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left++) != text.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validPalindromeTwoPointers(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                return isPalindromeRange(s, left + 1, right)
                        || isPalindromeRange(s, left, right - 1);
            }
        }
        return true;
    }

    private static boolean isPalindromeRange(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "abca";
        System.out.println("Input:   " + s);
        System.out.println("Brute:   " + validPalindromeBruteForce(s));
        System.out.println("Optimal: " + validPalindromeTwoPointers(s));
    }
}
```

```text
Input:   abca
Brute:   true
Optimal: true
```

---

## 16. Independent IDE practice checklist

- [ ] Implement brute force (skip each index).
- [ ] Implement optimal with `isPalindromeRange`.
- [ ] Trace `"abca"` and `"abc"` by hand.
- [ ] Explain why you must **return** on mismatch, not continue.
- [ ] Code `"aba"` (no delete needed) and `"ab"` (one delete).
- [ ] State time/space aloud.

### Small challenge

Trace without code:

```text
s = "ecebab"
```

1. List L/R moves until mismatch or finish.
2. If mismatch, test both branches.
3. Final boolean answer.

---

## 17. Quick revision

1. Match inward until mismatch or done.
2. **First mismatch** → try delete left **or** right (one time).
3. Helper = plain palindrome, **zero** skips left.
4. **`O(n)`** time, **`O(1)`** space.
5. Extension of **Problem 01** with one branch.

One line:

> **Palindrome scan + at first mismatch, OR two strict inner checks.**

---

## 18. Pattern used in this problem

**Opposite-direction two pointers with controlled branching.**

Reusable idea:

> When you have a **small budget** (one delete, one swap, one change), usually handle it at the **first failure point** instead of scattering logic through the whole scan.

---

## 19. Link to Java — which method is which

| Approach | Method in `ValidPalindromeII.java` |
|---|---|
| Try deleting each character | `validPalindromeBruteForce` |
| Two pointers + one skip (optimal) | `validPalindromeTwoPointers` |

Run `main` to print both approaches on sample inputs.

---

## 20. Next problem

Continue the Two Pointers roadmap — common next topics include **sorting + opposite pointers**, **linked list cycle (slow/fast)**, or **minimum window** style problems. Pick the next numbered file in your preparation guide when ready.
</think>
Fixing incorrect dry-run details in Problem 07's notes.
<｜tool▁calls▁begin｜><｜tool▁call▁begin｜>
StrReplace