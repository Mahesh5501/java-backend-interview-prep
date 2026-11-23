# Problem 01 — Valid Palindrome

> **Pattern:** Opposite-direction Two Pointers  
> **Goal:** See how two indexes walk inward from both ends to check symmetry — without building a second string if you do not need one.

---

## 1. Problem statement

A **palindrome** is text that reads the same forward and backward.

After you:

1. Convert every letter to lowercase (ignore case).
2. Remove every character that is not a letter or a digit (ignore spaces and punctuation).

…does the remaining string read the same from left to right and from right to left?

Given a string `s`, return `true` if it is a palindrome under these rules; otherwise return `false`.

### Example 1

```text
Input:  "A man, a plan, a canal: Panama"
Output: true
```

After cleaning:

```text
amanaplanacanalpanama
```

### Example 2

```text
Input:  "race a car"
Output: false
```

After cleaning:

```text
raceacar
```

Eventually `e` on the left does not match `a` on the right.

### Example 3

```text
Input:  " "
Output: true
```

After removing non-alphanumeric characters, nothing is left. An empty string counts as a palindrome.

### Constraints (LeetCode version)

- `1 <= s.length <= 2 * 10^5`
- `s` contains only printable ASCII characters.

---

## 2. Understand the problem before coding

You are not asked to reverse the whole string in the final solution.

You are asked to check **mirrored pairs**: the first useful character versus the last useful character, then the next pair inward, and so on.

### The one picture you should keep in mind

Think of the string as a line. Two fingers start at opposite ends:

```text
s = "madam"

Index:  0   1   2   3   4
Char:   m   a   d   a   m
        ^                   ^
      left                right
```

Read it as:

> “Compare the two ends. If they match, step both fingers one step inward. If they ever differ, stop — not a palindrome.”

For the full LeetCode version, skip spaces and punctuation before you compare:

```text
s = "A man, a plan, a canal: Panama"

        A   m   a   n   ,   a   ...   m   a
        ^                           (skip junk)     ^
      left                                        right

Useful chars only:  a ... a   → compare those
```

### Jargon — explained once

| Term | Meaning here |
|---|---|
| **Index** | Position in the string. First character is index `0`. |
| **Two pointers** | Two index variables (often called `left` and `right`) that you move while solving the problem. |
| **Alphanumeric** | A letter (`a`–`z`, `A`–`Z`) or a digit (`0`–`9`). |
| **Auxiliary space** | Extra memory besides the input (not counting the output). |

---

## 3. How to start thinking about the solution

Before writing code, ask yourself:

1. What am I comparing? Characters that count after cleaning rules.
2. Which characters should I ignore? Spaces, punctuation, and anything not a letter or digit.
3. What is the simplest correct approach? Build a cleaned string, reverse it, compare.
4. Can I avoid extra strings? Yes — walk inward with two indexes and skip junk on the fly.
5. When can I stop early? As soon as one pair does not match.

This tells us: start with brute force for confidence, then switch to two pointers for `O(1)` extra space.

---

## 4. Approach 1 — Brute force: clean, reverse, compare

### Idea

1. Walk the whole string once.
2. Keep only letters and digits, all lowercase.
3. Reverse that cleaned string.
4. If cleaned equals reversed, return `true`.

### Why does it work?

A palindrome looks identical forward and backward. Reversing the cleaned string gives you the backward view. If both views match, the input satisfies the rules.

### Java method (LeetCode-style cleaning)

```java
public static boolean validPalindromeBruteForce(String s) {

    StringBuilder cleaned = new StringBuilder();

    for (int i = 0; i < s.length(); i++) {
        char current = s.charAt(i);

        if (Character.isLetterOrDigit(current)) {
            cleaned.append(Character.toLowerCase(current));
        }
    }

    String reversed = new StringBuilder(cleaned).reverse().toString();
    return cleaned.toString().equals(reversed);
}
```

### Dry run

Input:

```text
"A man, a plan, a canal: Panama"
```

After cleaning:

```text
amanaplanacanalpanama
```

Reversed:

```text
amanaplanacanalpanama
```

Equal → return `true`.

### Complexity

| Measure | Value | Why |
|---|---|---|
| **Time** | `O(n)` | One pass to clean, one to reverse, one to compare (`n` = input length). |
| **Auxiliary space** | `O(n)` | The cleaned and reversed strings need extra room. |

### Limitation

Correct and easy to explain, but it uses extra memory. Two pointers avoid building new strings.

---

## 5. Approach 2 — Two pointers (optimal for LeetCode)

### Core idea

```text
left  = 0
right = last index

while left < right:
    skip junk on the left
    skip junk on the right
    compare lowercase letters/digits
    if mismatch → return false
    else move both inward
```

### One-step visual frame

```text
s = "race a car"   (useful: raceacar)

Step 4 — first mismatch:

Index:  0   1   2   3   4   5   6
Char:   r   a   c   e   a   c   r
                        ^
                      left

                      right at 'a'  (index 4)

        e  !=  a  →  return false
```

### Optimal Java method (full LeetCode rules)

```java
public static boolean validPalindrome(String s) {

    int left = 0;
    int right = s.length() - 1;

    while (left < right) {

        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
            left++;
        }

        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
            right--;
        }

        char leftChar = Character.toLowerCase(s.charAt(left));
        char rightChar = Character.toLowerCase(s.charAt(right));

        if (leftChar != rightChar) {
            return false;
        }

        left++;
        right--;
    }

    return true;
}
```

---

## 6. The hardest part explained carefully — skipping and bounds

Many bugs come from **when** to move pointers and **what** to compare.

### Case A — Forgetting to skip punctuation

#### Before (wrong mental model)

Compare raw characters at `left` and `right` without skipping:

```text
"nurses run"
        ^
      's' at left index 0

        ' ' at some inner index on the right side
```

You might compare `s` with a space. The problem says spaces do not count. That can give a wrong answer.

#### After (correct)

Skip until both sides point at alphanumeric characters, then compare.

```text
nurses run  →  useful: nursesrun
n ... n  match, then u ... u, and so on
```

### Case B — Skip loops without `left < right`

#### Before (wrong)

```java
while (!Character.isLetterOrDigit(s.charAt(left))) {
    left++;
}
```

If the string is all punctuation, `left` can run past `right` and you still try to compare invalid positions.

#### After (correct)

```java
while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
    left++;
}
```

The extra check keeps both pointers inside the valid comparison zone.

### Case C — Moving both pointers after a mismatch

#### Before (wrong)

```text
Pair does not match → still do left++ and right--
```

That wastes work and is illogical. One bad pair already proves the string is not a palindrome.

#### After (correct)

```java
if (leftChar != rightChar) {
    return false;  // stop immediately
}
```

---

## 7. Detailed dry run — Optimal approach (success)

Input:

```text
"A man, a plan, a canal: Panama"
```

We only show **useful** comparisons (skipping is implied).

```text
roles:   L ------------------------------------------------> R
         a    m    a    n    a    p    l    a    n    a    c    a    n    a    l    p    a    n    a    m    a    n    a
```

| Step | left char | right char | Action |
|---:|---|---|---|
| 1 | `a` | `a` | Match → move both |
| 2 | `m` | `m` | Match → move both |
| 3 | `a` | `a` | Match → move both |
| … | … | … | … |
| last | — | — | `left >= right` → stop |

No mismatch → return `true`.

### Role diagram at start

```text
Original string (view of the two ends):

   A ... Panama
   ^
 left

              ^
            right
```

After skips, both point at `a`.

---

## 8. Detailed dry run — Optimal approach (failure)

Input:

```text
"race a car"
```

Useful characters:

```text
raceacar
 0 1 2 3 4 5 6
```

| Step | left idx | right idx | Compare | Action |
|---:|---:|---:|---|---|
| 1 | 0 | 6 | `r` vs `r` | Match → inward |
| 2 | 1 | 5 | `a` vs `c` | **Mismatch** → `false` |

```text
Step 2:

r a c e a c r
  L       R
  a ≠ c  →  return false
```

---

## 9. Why two pointers never miss the answer

Every character that **counts** must mirror its partner from the opposite end.

The algorithm checks exactly those mirrored useful pairs, in order, from outside to inside.

- If any required pair differs → we return `false` at that moment. Correct.
- If all required pairs match → pointers meet or cross with no mismatch → `true`. Correct.

Each pointer only moves forward (left increases, right decreases). Neither revisits an old pair. So no pair is skipped.

---

## 10. Complexity comparison

| Approach | Time | Auxiliary space | Notes |
|---|---:|---:|---|
| Clean + reverse + compare | `O(n)` | `O(n)` | Easiest to trust |
| Two pointers (full rules) | `O(n)` | `O(1)` | Optimal extra space |

**Interview choice:** Two pointers with skip + lowercase compare.

Why `O(n)` time?

Each index moves at most once inward across the whole run. Total pointer movement is at most about `2n`.

Why `O(1)` auxiliary space?

Only a few `int` and `char` variables — no new string proportional to input size.

---

## 11. Edge cases to practise

| Input | Expected | What it checks |
|---|---|---|
| `""` | `true` | Empty |
| `" "` | `true` | Only spaces |
| `"a"` | `true` | Single useful char |
| `"aa"` | `true` | Pair |
| `"ab"` | `false` | Ends differ |
| `"Madam"` | `true` | Case |
| `"0P"` | `false` | Digits count; `0` ≠ `p` |
| `"!!!"` | `true` | All ignored |
| `"race a car"` | `false` | Mismatch inside |
| `"A man, a plan, a canal: Panama"` | `true` | Full example |

---

## 12. Common mistakes

1. **Comparing without `toLowerCase`:** `'A'` and `'a'` must match.
2. **Not skipping punctuation and spaces:** They must not participate in comparison.
3. **Using `==` on strings (in brute force):** Use `.equals()` for content.
4. **Moving both pointers after a mismatch:** Return `false` immediately.
5. **Moving only one pointer after a successful match:** Both sides of that pair are done — move both.
6. **Skip loops without `left < right`:** Prevents pointers crossing badly.
7. **Using `left <= right` for pair logic:** `left < right` is enough; middle char has no partner in odd-length useful strings.

---

## 13. How to recognise this pattern in an interview

Look for:

- Symmetry or “reads the same both ways”.
- Compare **first vs last**, then inward.
- Optional filtering (skip junk) before compare.

Ask:

> Can I place one index at each end and walk inward?

If yes, opposite-direction two pointers are a strong fit.

---

## 14. Interview explanation — Say this aloud

“A brute-force way is to build a cleaned lowercase string, reverse it, and compare. That is `O(n)` time but `O(n)` extra space. I can do better with two pointers: `left` at the start and `right` at the end. I skip non-alphanumeric characters on each side, compare the remaining characters in lowercase, and if they differ I return false. If they match I move both inward. If the loop finishes, return true. Each pointer scans the string at most once, so time is `O(n)` and auxiliary space is `O(1)`.”

---

## 15. Interview follow-up questions

### Q1. What if we only had lowercase letters, no punctuation?

Use simple two pointers: compare `s.charAt(left)` and `s.charAt(right)` directly, no skip loops.

### Q2. Can you avoid creating another string?

Yes — the full LeetCode version still uses only `O(1)` extra variables.

### Q3. What is Valid Palindrome II?

Allow **at most one deletion**. On first mismatch, try skipping left **or** skipping right once, then check if the middle is a palindrome. (Problem 07 in this roadmap.)

### Q4. Does this work for digits?

Yes. `Character.isLetterOrDigit` keeps digits, and they must match like letters.

### Q5. Why is time not `O(n²)`?

There is no nested “try every pair” loop. Pointers only march inward.

---

## 16. Complete runnable Java class — All applicable approaches

The repo file `../ValidPalindrome.java` includes:

1. **Brute force** — reverse entire string (simple strings like `madam`; not LeetCode cleaning).
2. **Two pointers** — simple lowercase strings, no skip logic.
3. **`validPalindrome`** — full LeetCode rules (skip + case).

Copy into your IDE as **`ValidPalindrome.java`** (lives in `java/`).

### Complete Java code

```java


public class ValidPalindrome {

    // APPROACH 1: BRUTE FORCE
    // Reverse the string and compare it with the original.
    // Time: O(n), Auxiliary space: O(n).
    public static boolean bruteForce(String s) {

        StringBuilder reversed = new StringBuilder(s);
        reversed.reverse();

        return s.equals(reversed.toString());  // equals() compares contents, unlike ==.
    }

    // APPROACH 2: TWO POINTERS
    // Compare characters at opposite ends and move inward.
    // Time: O(n), Auxiliary space: O(1).
    // Assumption: lowercase English letters only.
    public static boolean twoPointers(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    // APPROACH 3: FULL LEETCODE VERSION
    // Ignore punctuation, spaces, and letter case.
    // Time: O(n), Auxiliary space: O(1).
    public static boolean validPalindrome(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            char leftChar = Character.toLowerCase(s.charAt(left));
            char rightChar = Character.toLowerCase(s.charAt(right));

            if (leftChar != rightChar) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    public static void main(String[] args) {

        String[] testCases = {
            "madam",
            "hello",
            "",
            "a",
            "aa",
            "ab",
            "racecar",
            "A man, a plan, a canal: Panama",
            "race a car",
            "!!!"
        };

        for (String s : testCases) {

            System.out.println("Input: " + s);
            System.out.println("Brute force: " + bruteForce(s));
            System.out.println("Two pointers: " + twoPointers(s));
            System.out.println("Full version: " + validPalindrome(s));
            System.out.println("-------------------------");
        }
    }
}
```

### Which method for interviews?

| Method | When |
|---|---|
| `validPalindrome()` | LeetCode / real interview wording |
| `twoPointers()` | Teaching core pointer movement on clean strings |
| `bruteForce()` | Quick intuition only |

---

## 17. Independent IDE practice

Before Problem 02, try without looking at the code:

- [ ] Write clean + reverse brute force for full LeetCode rules.
- [ ] Write `validPalindrome` with skip loops and lowercase compare.
- [ ] Explain why `left < right` in skip loops.
- [ ] Trace `"race a car"` and `"A man, a plan, a canal: Panama"`.
- [ ] State time and auxiliary space for the optimal method.
- [ ] List three edge cases from the table.

### Small challenge

Trace by hand:

```text
s = "0P"
```

1. What do `left` and `right` point to after skips?
2. What is compared?
3. Final answer?

---

## 18. Quick revision

Remember these four things:

1. **Goal:** Mirrored useful characters must match.
2. **Skip:** Non-alphanumeric does not count.
3. **Compare:** Use lowercase letters/digits.
4. **Move:** Both inward on match; `return false` on mismatch.

Reusable sentence:

> **Valid Palindrome = opposite pointers + skip junk + case-insensitive compare.**

---

## 19. Pattern used in this problem

**Opposite-direction Two Pointers.**

Reusable templates live in **`Two_Pointers_Common_Patterns.md`**. This file is the problem-specific walkthrough.

---

## 20. Next problem

When you are ready:

**Problem 02 — Two Sum II: Input Array Is Sorted**  
Main concept: Use sorted order to decide whether to move `left` or `right` based on the sum.