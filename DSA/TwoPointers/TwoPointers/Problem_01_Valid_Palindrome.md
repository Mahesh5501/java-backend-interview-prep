# Problem 1 --- Valid Palindrome

**Topic:** Two Pointers\
**Pattern:** Opposite-direction pointers\
**Language:** Java 8 / Java 17\
**Level:** Beginner-friendly interview preparation

------------------------------------------------------------------------

## 1. Problem statement

A string is a palindrome if, after converting all uppercase letters into
lowercase letters and removing every character that is not a letter or a
digit, it reads the same forward and backward.

Given a string `s`, return `true` if it is a palindrome; otherwise,
return `false`.

### Examples

**Example 1**

``` text
Input:  "A man, a plan, a canal: Panama"
Output: true
```

After ignoring spaces and punctuation and converting to lowercase:

``` text
amanaplanacanalpanama
```

It reads the same forward and backward.

**Example 2**

``` text
Input:  "race a car"
Output: false
```

After cleaning:

``` text
raceacar
```

The first and last characters differ (`r` and `r` match), but moving
inward eventually gives a mismatch (`e` and `a`).

**Example 3**

``` text
Input:  " "
Output: true
```

After removing non-alphanumeric characters, the string is empty. An
empty string is considered a palindrome.

### Clarify the requirements

-   Ignore spaces and punctuation.
-   Ignore letter case.
-   Keep letters and digits.
-   Return a boolean.
-   The input string does not need to be modified.

------------------------------------------------------------------------

## 2. How to start thinking about this problem

Before coding, translate the question into a simple task:

> Compare the first useful character with the last useful character. If
> they match, move inward and repeat.

For example:

``` text
s = "madam"

Index:  0 1 2 3 4
Char:   m a d a m
        ^       ^
       left    right
```

-   Compare `m` and `m`: they match.
-   Move both pointers inward.
-   Compare `a` and `a`: they match.
-   Move both pointers inward.
-   The pointers meet at `d`. There is no more pair to compare.
-   Return `true`.

Now consider:

``` text
s = "hello"

Index:  0 1 2 3 4
Char:   h e l l o
        ^       ^
       left    right
```

-   Compare `h` and `o`.
-   They do not match.
-   A palindrome must have matching characters at both ends.
-   Return `false` immediately.

### Why not start by reversing the string?

Reversing is a valid simple solution, but it creates another string. The
two-pointer approach compares the original string from both ends and
needs only a constant amount of extra memory.

------------------------------------------------------------------------

## 3. Approach 1 --- Brute force: reverse and compare

### Idea

1.  Convert the string to lowercase.
2.  Remove characters that are not letters or digits.
3.  Reverse the cleaned string.
4.  Compare the cleaned string with its reverse.

If both strings are equal, the input is a palindrome under the problem's
rules.

### Why does this work?

A palindrome reads identically forward and backward. Reversing the
cleaned string gives us the backward version. If the forward and
backward versions are equal, it is a palindrome.

### Java code

``` java
public class ValidPalindromeBruteForce {

    public static boolean isPalindrome(String s) {

        // StringBuilder is used because strings are immutable in Java.
        // We build a cleaned version containing only letters and digits.
        StringBuilder cleaned = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            char current = s.charAt(i);

            // Ignore spaces, punctuation, and every non-alphanumeric character.
            if (Character.isLetterOrDigit(current)) {

                // Normalize case so 'A' and 'a' are treated as equal.
                cleaned.append(Character.toLowerCase(current));
            }
        }

        // Create the reversed version of the cleaned string.
        String reversed = new StringBuilder(cleaned).reverse().toString();

        // equals() compares string contents.
        // Do not use == here because == compares object references.
        return cleaned.toString().equals(reversed);
    }

    public static void main(String[] args) {

        System.out.println(isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(isPalindrome("race a car"));                    // false
        System.out.println(isPalindrome(" "));                             // true
    }
}
```

### Dry run

Input:

``` text
"A man, a plan, a canal: Panama"
```

After removing punctuation/spaces and converting to lowercase:

``` text
amanaplanacanalpanama
```

Reverse:

``` text
amanaplanacanalpanama
```

The strings are equal, so return `true`.

### Complexity

Let `n` be the length of the original string.

-   **Time:** O(n). We scan the input, then reverse and compare the
    cleaned string. Each operation is linear.
-   **Auxiliary space:** O(n). The cleaned and reversed strings require
    additional storage.

### Limitation

This approach is easy to understand, but it uses extra memory for the
cleaned and reversed strings. We can do better with two pointers.

------------------------------------------------------------------------

## 4. Approach 2 --- Two pointers (optimal)

### Core idea

Use two indexes:

-   `left` starts at index `0`.
-   `right` starts at the last index.

Move inward from both ends. Skip any character that is not a letter or
digit. Compare the remaining characters without considering case.

If a pair differs, return `false`. If all pairs match, return `true`.

### Step-by-step algorithm

1.  Initialize `left = 0`.
2.  Initialize `right = s.length() - 1`.
3.  Continue while `left < right`.
4.  If `s.charAt(left)` is not alphanumeric, increment `left` and
    continue.
5.  If `s.charAt(right)` is not alphanumeric, decrement `right` and
    continue.
6.  Compare both characters after converting them to lowercase.
7.  If they differ, return `false`.
8.  If they match, increment `left` and decrement `right`.
9.  If the loop finishes, return `true`.

### Java code --- complete runnable class

``` java
public class ValidPalindromeTwoPointers {

    public static boolean isPalindrome(String s) {

        // Start at the first and last positions.
        // We do not create a cleaned copy of the string.
        int left = 0;
        int right = s.length() - 1;

        // Stop when the pointers meet or cross.
        // At that point, every required pair has been checked.
        while (left < right) {

            // Skip characters on the left that are not letters or digits.
            // We do not compare punctuation or spaces.
            while (left < right &&
                   !Character.isLetterOrDigit(s.charAt(left))) {

                left++;
            }

            // Skip characters on the right that are not letters or digits.
            while (left < right &&
                   !Character.isLetterOrDigit(s.charAt(right))) {

                right--;
            }

            // Convert both characters to lowercase before comparing.
            // This makes uppercase and lowercase versions equivalent.
            char leftChar = Character.toLowerCase(s.charAt(left));
            char rightChar = Character.toLowerCase(s.charAt(right));

            // A palindrome must have matching characters at both ends.
            // One mismatch is enough to prove the string is not a palindrome.
            if (leftChar != rightChar) {
                return false;
            }

            // Both characters matched, so this pair is complete.
            // Move inward to check the next pair.
            left++;
            right--;
        }

        // No mismatch was found, so the string is a palindrome.
        return true;
    }

    public static void main(String[] args) {

        System.out.println(isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(isPalindrome("race a car"));                    // false
        System.out.println(isPalindrome(" "));                             // true
        System.out.println(isPalindrome("0P"));                            // false
        System.out.println(isPalindrome("Madam"));                         // true
    }
}
```

------------------------------------------------------------------------

## 5. Understand every important pointer decision

### Why do we use `left < right`?

We compare characters from opposite ends. Once the pointers meet, they
refer to the same middle character. There is no separate pair left to
compare.

For an odd-length string, the middle character does not need to be
compared with another character.

For an even-length string, the pointers eventually cross after all pairs
have been checked.

### Why skip non-alphanumeric characters?

The problem explicitly says spaces and punctuation do not count.

For example:

``` text
"nurses run"
```

The space should not be compared with a letter. We move the pointer past
it and compare the next meaningful character.

### Why check `left < right` inside the skip loops too?

The pointer may move while skipping punctuation. We must ensure it does
not move beyond the other pointer.

The extra condition prevents us from reading outside the intended
comparison range and keeps the two sides from crossing during skipping.

### Why move both pointers after a match?

Once the two current characters match, that pair has been verified. The
next possible pair is one position inward from each side.

So we do:

``` java
left++;
right--;
```

### Why return immediately after a mismatch?

A palindrome requires every mirrored pair to match. If even one pair
differs, checking more pairs cannot make the whole string a palindrome.

### Why use `Character.toLowerCase()`?

The question says to ignore case. Without normalization, `'A'` and `'a'`
would be treated as different characters.

### Why use `Character.isLetterOrDigit()`?

It accepts letters and digits and rejects spaces and punctuation,
matching the problem's filtering rule.

------------------------------------------------------------------------

## 6. Detailed dry run --- successful case

Input:

``` text
"A man, a plan, a canal: Panama"
```

The meaningful characters are:

``` text
amanaplanacanalpanama
```

The pointers skip spaces and punctuation and compare from both ends.

    Step Left character   Right character   Action
  ------ ---------------- ----------------- ----------------------------
       1 `a`              `a`               Match; move both inward
       2 `m`              `m`               Match; move both inward
       3 `a`              `a`               Match; move both inward
       4 `n`              `n`               Match; move both inward
       5 `a`              `a`               Match; move both inward
       6 `p`              `p`               Match; move both inward
       7 `l`              `l`               Match; move both inward
       8 `a`              `a`               Match; move both inward
       9 `n`              `n`               Match; move both inward
      10 `a`              `a`               Match; pointers meet/cross

No mismatch occurs, so the method returns `true`.

The table shows the meaningful comparisons; punctuation and spaces are
skipped before comparison.

------------------------------------------------------------------------

## 7. Detailed dry run --- unsuccessful case

Input:

``` text
"race a car"
```

Ignore the space and normalize case:

``` text
raceacar
```

    Step Left   Right   Comparison   Result
  ------ ------ ------- ------------ ----------------
       1 `r`    `r`     Equal        Move both
       2 `a`    `a`     Equal        Move both
       3 `c`    `c`     Equal        Move both
       4 `e`    `a`     Different    Return `false`

The first mismatch proves the string is not a palindrome.

------------------------------------------------------------------------

## 8. Complexity of the optimal solution

Let `n` be the length of the input string.

### Time complexity: O(n)

Each pointer only moves inward. Neither pointer moves backward or
restarts from the beginning.

Across the whole algorithm, the pointers scan at most the string's
characters a constant number of times.

Therefore, time complexity is O(n).

### Auxiliary space complexity: O(1)

We use only a fixed number of variables:

-   `left`
-   `right`
-   `leftChar`
-   `rightChar`

We do not create a cleaned string or reversed string.

Therefore, auxiliary space is O(1).

  Approach                      Time   Extra space
  --------------------------- ------ -------------
  Clean + reverse + compare     O(n)          O(n)
  Two pointers                  O(n)          O(1)

------------------------------------------------------------------------

## 9. Edge cases to test

  ---------------------------------------------------------------------------------------
  Input                                             Expected output Why
  ------------------------------------ ---------------------------- ---------------------
  `""`                                                       `true` Empty string has no
                                                                    mismatching pair

  `" "`                                                      `true` Space is ignored

  `"a"`                                                      `true` One character is a
                                                                    palindrome

  `"aa"`                                                     `true` Both characters match

  `"ab"`                                                    `false` The two ends differ

  `"Madam"`                                                  `true` Case is ignored

  `"A man, a plan, a canal: Panama"`                         `true` Punctuation, spaces
                                                                    and case are ignored

  `"race a car"`                                            `false` A meaningful pair
                                                                    differs

  `"!!!"`                                                    `true` All characters are
                                                                    ignored

  `"0P"`                                                    `false` Digits count; `0` and
                                                                    `p` differ
  ---------------------------------------------------------------------------------------

------------------------------------------------------------------------

## 10. Common mistakes

1.  **Comparing the original characters directly.** This fails when
    uppercase and lowercase should be treated equally.
2.  **Not skipping punctuation and spaces.** The problem says those
    characters must be ignored.
3.  **Using `==` to compare strings.** Use `.equals()` for string
    content in the brute-force approach.
4.  **Moving only one pointer after a successful comparison.** Both
    sides of the current pair have been checked, so move both.
5.  **Moving both pointers after a mismatch.** Return `false`
    immediately instead.
6.  **Forgetting bounds in the skip loops.** Keep `left < right` in the
    inner conditions.
7.  **Using `left <= right` without thinking.** For this pair-comparison
    solution, `left < right` is sufficient.
8.  **Building a cleaned string in the optimal approach.** That would
    use O(n) extra space and lose the O(1)-space benefit.

------------------------------------------------------------------------

## 11. How to explain this in an interview

A concise explanation:

> "A straightforward approach is to normalize the string, reverse it,
> and compare the two strings. That takes O(n) time and O(n) extra
> space. We can optimize the space using two pointers. I place one
> pointer at the beginning and one at the end. I skip non-alphanumeric
> characters, compare the current characters after converting them to
> lowercase, and move both pointers inward when they match. If any pair
> differs, I return false. If the pointers meet or cross without a
> mismatch, I return true. Each pointer traverses the string at most
> once, so the time complexity is O(n) and auxiliary space is O(1)."

------------------------------------------------------------------------

## 12. Interview follow-up questions

### Q1. What if the input contains uppercase letters?

Convert both characters to lowercase before comparing, as in the optimal
code.

### Q2. What if punctuation should count?

Remove the `Character.isLetterOrDigit()` skip loops. Compare every
character according to the revised requirement.

### Q3. Can you solve it without creating another string?

Yes. The two-pointer approach compares characters directly in the
original string and uses O(1) auxiliary space.

### Q4. What if you are allowed to delete at most one character to make the string a palindrome?

That is a variation called **Valid Palindrome II**.

Use two pointers until the first mismatch. At that mismatch, try
skipping either the left character or the right character, then check
whether the remaining range is a palindrome.

### Q5. Does this approach work for digits?

Yes. `Character.isLetterOrDigit()` accepts digits, and the comparison
checks them too.

### Q6. Why is the time complexity not O(n²)?

There is no nested scan over all pairs. The two pointers only move
inward. The total number of pointer movements is proportional to the
input length.

------------------------------------------------------------------------

## 13. Pattern learned --- Opposite-direction pointers

This problem teaches the basic opposite-direction pattern.

``` java
int left = 0;
int right = s.length() - 1;

while (left < right) {

    // Optional: skip values that should not be considered.

    // Compare the values at both ends.

    // If they satisfy the condition, move inward.
    left++;
    right--;
}
```

Remember: this is only a structural template. The correct pointer
movement depends on the problem.

### Recognition clues

Consider opposite-direction pointers when:

-   You need to compare mirrored positions.
-   You need to inspect both ends of a sequence.
-   You need to reverse or validate symmetry.
-   You can safely move inward after processing the current pair.

------------------------------------------------------------------------

## 14. Independent IDE practice

Before looking at the solution, try to write the optimal method
yourself.

-   [ ] Create a class named `ValidPalindromePractice`.
-   [ ] Write `public static boolean isPalindrome(String s)`.
-   [ ] Initialize the two pointers.
-   [ ] Add the correct loop condition.
-   [ ] Skip non-alphanumeric characters.
-   [ ] Compare characters without case sensitivity.
-   [ ] Return `false` on a mismatch.
-   [ ] Move both pointers after a match.
-   [ ] Return `true` after the loop.
-   [ ] Run all edge cases from the table.
-   [ ] Explain why the time is O(n) and extra space is O(1) without
    reading the notes.

### Final self-check

You are ready to move on when you can answer these without help:

1.  Why do we start one pointer at each end?
2.  Why do we skip punctuation?
3.  Why do we move both pointers after a match?
4.  Why can we return immediately after a mismatch?
5.  Why is the auxiliary space O(1)?
6.  What changes if one deletion is allowed?

------------------------------------------------------------------------

## 15. Next problem

**Problem 2 --- Two Sum II: Input Array Is Sorted**

New learning points:

-   Using sorted order to guide pointer movement.
-   Understanding why a sum that is too small means moving `left`.
-   Understanding why a sum that is too large means moving `right`.
-   Comparing brute force, HashMap, and optimal two pointers.
-   Returning 1-based indices as required by the common interview
    problem.
