# Problem 02 --- Two Sum II: Input Array Is Sorted

## 1. Problem statement

You are given a **1-indexed** integer array `numbers` that is sorted in
non-decreasing order, and an integer `target`.

Find two numbers such that they add up to `target`. Return their indices
as a 1-based integer array `[index1, index2]`.

-   The two indices must be different.
-   There is exactly one solution.
-   You may not use the same element twice.
-   The array is sorted, so equal values are allowed.

### Example 1

``` text
Input:  numbers = [2, 7, 11, 15], target = 9
Output: [1, 2]
Explanation: numbers[1] + numbers[2] = 2 + 7 = 9.
```

### Example 2

``` text
Input:  numbers = [2, 3, 4], target = 6
Output: [1, 3]
Explanation: 2 + 4 = 6.
```

### Example 3

``` text
Input:  numbers = [-1, 0], target = -1
Output: [1, 2]
```

**Important:** The problem statement calls the array 1-indexed. Java
arrays are still 0-indexed. When returning an answer, add `1` to each
Java index.

------------------------------------------------------------------------

## 2. How to start thinking

Before choosing an algorithm, identify:

1.  **What are we looking for?** Two different elements whose sum equals
    `target`.
2.  **What information do we have?** The array is sorted in
    ascending/non-decreasing order.
3.  **What does sorted order give us?** Moving right from an index gives
    us a value that is greater than or equal to the current value.
    Moving left gives us a value that is less than or equal to the
    current value.
4.  **Can we use this order to avoid checking every pair?** Yes. Start
    with the smallest and largest values and decide which pointer to
    move based on the sum.

The sorted property is the key that makes the two-pointer solution
possible in linear time and constant auxiliary space.

------------------------------------------------------------------------

## 3. Approach 1 --- Brute force (check every pair)

### Idea

Try every possible pair:

-   Choose one element using an outer loop.
-   Choose a later element using an inner loop.
-   Add them.
-   If the sum equals `target`, return their 1-based indices.

### Why it works

Every possible pair of different positions is eventually checked.
Therefore, if a solution exists, this approach will find it.

### Java code

``` java
public static int[] twoSumBruteForce(int[] numbers, int target) {
    // Pick the first element of the pair.
    for (int i = 0; i < numbers.length; i++) {

        // Start at i + 1 so we do not use the same element twice
        // and do not check the same pair in reverse order.
        for (int j = i + 1; j < numbers.length; j++) {

            // Check whether this pair produces the required sum.
            if (numbers[i] + numbers[j] == target) {

                // The requested answer is 1-based, but Java indices
                // are 0-based, so add 1 to both.
                return new int[] { i + 1, j + 1 };
            }
        }
    }

    // The problem guarantees exactly one solution.
    // This fallback keeps the method's return path explicit.
    return new int[] { -1, -1 };
}
```

### Dry run

Input:

``` text
numbers = [2, 7, 11, 15]
target = 9
```

    i   j Values     Sum Result
  --- --- -------- ----- --------
    0   1 2 + 7        9 Found

Return `[1, 2]`.

### Complexity

-   **Time:** `O(n²)` --- in the worst case, we examine many pairs.
-   **Auxiliary space:** `O(1)` --- only loop variables are used. The
    returned two-element array is the required output.

Brute force is easy to understand, but it does not take advantage of the
sorted array.

------------------------------------------------------------------------

## 4. Approach 2 --- HashMap (better for unsorted Two Sum)

This is an important alternative to know, even though it is not the best
solution for this particular sorted-array problem.

### Idea

For each current number, calculate the value we still need:

``` text
needed = target - currentNumber
```

Store previously seen values and their indices in a `HashMap`.

If `needed` is already in the map, we have found the pair.

### Java code

``` java
import java.util.HashMap;
import java.util.Map;

public static int[] twoSumHashMap(int[] numbers, int target) {
    // Map a number to the 0-based index where we first saw it.
    Map<Integer, Integer> seen = new HashMap<>();

    for (int i = 0; i < numbers.length; i++) {

        // Find the value that would complete the required sum.
        int needed = target - numbers[i];

        // If we have already seen that value, the pair is complete.
        if (seen.containsKey(needed)) {
            int previousIndex = seen.get(needed);

            // Convert both Java indices to the requested 1-based indices.
            return new int[] { previousIndex + 1, i + 1 };
        }

        // Store the current value only after checking its complement.
        // This prevents an element from being paired with itself.
        seen.put(numbers[i], i);
    }

    return new int[] { -1, -1 };
}
```

### Dry run

Input:

``` text
numbers = [2, 7, 11, 15]
target = 9
```

    Current value   Needed value Map before check   Action
  --------------- -------------- ------------------ -----------------------------
                2              7 `{}`               7 is absent; store `2 -> 0`
                7              2 `{2=0}`            2 exists; return `[1, 2]`

### Complexity

-   **Time:** `O(n)` average case --- each lookup and insertion is
    average `O(1)`.
-   **Auxiliary space:** `O(n)` --- the map may store every element.

### When is HashMap useful?

For the classic **Two Sum** problem where the input array is not sorted,
a HashMap is usually the preferred linear-time solution.

For this problem, the array is sorted and the two-pointer method also
takes `O(n)` time but uses `O(1)` auxiliary space. Therefore, two
pointers are preferable here.

------------------------------------------------------------------------

## 5. Approach 3 --- Optimal two pointers

### Idea

Use two indices:

-   `left = 0`: starts at the smallest value.
-   `right = numbers.length - 1`: starts at the largest value.

Calculate:

``` text
sum = numbers[left] + numbers[right]
```

Then make a decision:

  Condition         What it tells us       Correct action
  ----------------- ---------------------- --------------------
  `sum == target`   We found the pair      Return the indices
  `sum < target`    The sum is too small   Move `left` right
  `sum > target`    The sum is too large   Move `right` left

### Why does moving `left` work when the sum is too small?

Suppose:

``` text
numbers = [2, 7, 11, 15]
target = 18

left value  = 2
right value = 15
sum         = 17
```

The sum is too small.

Because the array is sorted, every value to the left of `right` is less
than or equal to `15`. If we move `right` left, we would replace `15`
with an equal or smaller value, making the sum no larger. That cannot
fix a sum that is already too small.

Instead, move `left` right. This replaces `2` with an equal or larger
value, which can increase the sum.

### Why does moving `right` work when the sum is too large?

Suppose:

``` text
numbers = [2, 7, 11, 15]
target = 20

left value  = 2
right value = 15
sum         = 17
```

This example is actually too small, so let's use target `16`:

``` text
2 + 15 = 17
```

The sum is too large.

Moving `left` right would replace `2` with an equal or larger value,
making the sum no smaller. That cannot fix a sum that is already too
large.

Instead, move `right` left. This replaces `15` with an equal or smaller
value, which can reduce the sum.

### Complete Java solution

``` java
public static int[] twoSumTwoPointers(int[] numbers, int target) {

    // Start at the smallest value.
    int left = 0;

    // Start at the largest value.
    int right = numbers.length - 1;

    // Continue while two different positions are available.
    while (left < right) {

        // Calculate the sum of the values at the two pointers.
        int sum = numbers[left] + numbers[right];

        if (sum == target) {

            // Found the required pair.
            // Convert Java's 0-based indices to 1-based indices.
            return new int[] { left + 1, right + 1 };

        } else if (sum < target) {

            // The sum is too small.
            // Move left forward to try a larger value.
            left++;

        } else {

            // The sum is too large.
            // Move right backward to try a smaller value.
            right--;
        }
    }

    // The problem guarantees one solution.
    // This is a defensive fallback.
    return new int[] { -1, -1 };
}
```

## Complete runnable Java class — All 3 approaches

This class contains all three approaches:

1. Brute Force — `O(n²)` time, `O(1)` auxiliary space.
2. HashMap — `O(n)` average time, `O(n)` auxiliary space.
3. Two Pointers — `O(n)` time, `O(1)` auxiliary space.

You can copy the complete Java class into your IDE. Save the file as `TwoSumII.java`.

### Complete Java code

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumII {

    // =====================================================
    // APPROACH 1: BRUTE FORCE
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int[] twoSumBruteForce(int[] numbers, int target) {

        // Pick the first number.
        for (int i = 0; i < numbers.length; i++) {

            // Pick another number after i.
            // Starting at i + 1 avoids using the same element twice.
            for (int j = i + 1; j < numbers.length; j++) {

                // Check whether these two numbers equal the target.
                if (numbers[i] + numbers[j] == target) {

                    // Convert Java's 0-based indices to 1-based indices.
                    return new int[] { i + 1, j + 1 };
                }
            }
        }

        // Defensive fallback: problem guarantees one solution.
        return new int[] { -1, -1 };
    }


    // =====================================================
    // APPROACH 2: HASHMAP
    // Time Complexity: O(n) average
    // Auxiliary Space: O(n)
    // =====================================================

    public static int[] twoSumHashMap(int[] numbers, int target) {

        // Store number -> its 0-based index.
        Map<Integer, Integer> seen = new HashMap<>();

        // Visit each number once.
        for (int i = 0; i < numbers.length; i++) {

            // Calculate the number needed to complete the target.
            int needed = target - numbers[i];

            // Check whether we have already seen the needed number.
            if (seen.containsKey(needed)) {

                // Retrieve the index of the previously seen number.
                int previousIndex = seen.get(needed);

                // Return both indices using 1-based indexing.
                return new int[] { previousIndex + 1, i + 1 };
            }

            // Store the current number and its index.
            // We store it after checking to avoid pairing an element
            // with itself.
            seen.put(numbers[i], i);
        }

        return new int[] { -1, -1 };
    }


    // =====================================================
    // APPROACH 3: TWO POINTERS (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int[] twoSumTwoPointers(int[] numbers, int target) {

        // Start from the smallest element.
        int left = 0;

        // Start from the largest element.
        int right = numbers.length - 1;

        // Continue while two different positions are available.
        while (left < right) {

            // Calculate the sum of the two selected numbers.
            int sum = numbers[left] + numbers[right];

            // If the sum matches, we found the answer.
            if (sum == target) {

                // Convert both indices to 1-based indexing.
                return new int[] { left + 1, right + 1 };
            }

            // If sum is too small, we need a larger value.
            // The array is sorted, so move left forward.
            else if (sum < target) {
                left++;
            }

            // If sum is too large, we need a smaller value.
            // The array is sorted, so move right backward.
            else {
                right--;
            }
        }

        return new int[] { -1, -1 };
    }


    // =====================================================
    // MAIN METHOD
    // Run all three approaches using the same input.
    // =====================================================

    public static void main(String[] args) {

        int[] numbers = { 2, 7, 11, 15 };
        int target = 9;

        System.out.println("Input: " + Arrays.toString(numbers));
        System.out.println("Target: " + target);

        // Run brute-force approach.
        int[] bruteForceAnswer =
                twoSumBruteForce(numbers, target);

        System.out.println(
                "Brute Force: " + Arrays.toString(bruteForceAnswer)
        );

        // Run HashMap approach.
        int[] hashMapAnswer =
                twoSumHashMap(numbers, target);

        System.out.println(
                "HashMap: " + Arrays.toString(hashMapAnswer)
        );

        // Run optimal two-pointer approach.
        int[] twoPointerAnswer =
                twoSumTwoPointers(numbers, target);

        System.out.println(
                "Two Pointers: " + Arrays.toString(twoPointerAnswer)
        );
    }
}
```

### Expected output

```text
Input: [2, 7, 11, 15]
Target: 9
Brute Force: [1, 2]
HashMap: [1, 2]
Two Pointers: [1, 2]
```

### Approach comparison

| Approach | Time Complexity | Auxiliary Space | Best use case |
|---|---|---|---|
| Brute Force | O(n²) | O(1) | Basic understanding |
| HashMap | O(n) average | O(n) | Unsorted Two Sum |
| Two Pointers | O(n) | O(1) | Sorted Two Sum II |

**Important interview distinction:**

- HashMap works whether the array is sorted or unsorted.
- Two Pointers relies on the array being sorted.
- For this problem, Two Pointers is optimal because it achieves O(n) time and O(1) auxiliary space.
- The returned indices are 1-based, so we add `1` to the Java indices.


------------------------------------------------------------------------

## 6. Detailed dry run of the optimal solution

Input:

``` text
numbers = [2, 7, 11, 15]
target = 9
```

Initial state:

``` text
left = 0
right = 3

[2, 7, 11, 15]
 ↑           ↑
left        right
```

    Step   left   right   Left value   Right value   Sum Decision
  ------ ------ ------- ------------ ------------- ----- ------------------------
       1      0       3            2            15    17 Too large; `right--`
       2      0       2            2            11    13 Too large; `right--`
       3      0       1            2             7     9 Match; return `[1, 2]`

At step 3:

``` java
return new int[] { left + 1, right + 1 };
```

This returns `[1, 2]`, not `[0, 1]`, because the problem wants 1-based
positions.

------------------------------------------------------------------------

## 7. Why does the algorithm never miss the answer?

This is the key correctness reasoning to explain in an interview.

At any moment, the answer---if it has not already been found---must
still be inside the current range from `left` to `right`.

-   If the current sum is too small, the current `left` value cannot
    form the target with `right` or with any value to the left of
    `right`, because those values are no larger than `numbers[right]`.
    So that `left` position can safely be discarded.
-   If the current sum is too large, the current `right` value cannot
    form the target with `left` or with any value to the right of
    `left`, because those values are no smaller than `numbers[left]`. So
    that `right` position can safely be discarded.
-   If the sum matches, we have the required pair.

Each step removes one impossible position. The pointers move inward
until the answer is found.

------------------------------------------------------------------------

## 8. Complexity comparison

  Approach                   Time   Auxiliary space Uses sorted property?
  -------------- ---------------- ----------------- -----------------------
  Brute force             `O(n²)`            `O(1)` No
  HashMap          `O(n)` average            `O(n)` No
  Two pointers             `O(n)`            `O(1)` Yes

**Optimal choice for this problem:** Two pointers, because it achieves
linear time and constant auxiliary space.

------------------------------------------------------------------------

## 9. Edge cases to practise

  Input                    Target Expected output   What it checks
  ---------------------- -------- ----------------- ------------------------------
  `[2, 3]`                      5 `[1, 2]`          Minimum array size
  `[-1, 0]`                    -1 `[1, 2]`          Negative values
  `[1, 2, 3, 4, 4, 9]`          8 `[4, 5]`          Duplicate values
  `[1, 2, 3, 4, 5]`             6 `[1, 5]`          Pair at both ends
  `[-5, -2, 0, 3, 8]`           1 `[2, 4]`          Negative and positive values

The problem guarantees one solution, so a production implementation for
this exact problem does not need to define a special no-solution result.
The `[-1, -1]` return is included only as a defensive fallback.

------------------------------------------------------------------------

## 10. Common mistakes

1.  **Returning 0-based indices:** Return `left + 1` and `right + 1`.
2.  **Using `left <= right`:** Use `left < right`, because two different
    elements are required.
3.  **Moving the wrong pointer:** If sum is too small, increment `left`;
    if too large, decrement `right`.
4.  **Forgetting why sorting matters:** The pointer decisions depend on
    the array being sorted.
5.  **Sorting the input unnecessarily:** It is already sorted. Sorting
    again wastes time and can also change index meanings in problems
    where original indices matter.
6.  **Using HashMap automatically:** HashMap works, but it uses extra
    memory and ignores the useful sorted property.

------------------------------------------------------------------------

## 11. How to recognise this pattern in an interview

Look for these clues:

-   The input is sorted.
-   You need to find a pair.
-   You need a target sum or comparison.
-   You want to avoid checking every pair.
-   The answer can be found by moving inward from both ends.

Ask yourself:

> If I choose the smallest and largest remaining values, can their
> comparison tell me which one is safe to discard?

If yes, opposite-direction two pointers may be useful.

### Important distinction: sorted vs unsorted Two Sum

-   **Sorted array:** Opposite-direction two pointers can solve it in
    `O(n)` time and `O(1)` auxiliary space.
-   **Unsorted array:** A HashMap is generally the standard `O(n)`
    approach.
-   If you sort an unsorted array first, be careful: sorting changes
    positions. If the original indices are required, preserve the
    original indices alongside the values.

------------------------------------------------------------------------

## 12. Interview explanation (say this aloud)

"The array is already sorted, so I can use two pointers instead of
checking every pair. I initialise one pointer at the beginning and the
other at the end. I calculate their sum. If the sum is smaller than the
target, I move the left pointer forward because I need a larger value.
If the sum is greater than the target, I move the right pointer backward
because I need a smaller value. If the sum matches, I return both
indices after converting them to 1-based indexing. Each pointer moves at
most `n` times, so the time complexity is `O(n)` and the auxiliary space
complexity is `O(1)`."

------------------------------------------------------------------------

## 13. Interview follow-up questions

### Q1. What if the array is not sorted?

Use a HashMap for the standard Two Sum problem. Store previously seen
values and check whether `target - currentValue` has already appeared.

### Q2. Why not move both pointers every time?

Because the sum tells us which side is preventing a match. Moving both
could discard a valid answer without checking it.

### Q3. Why is `left < right` used?

We need two different elements. When the pointers meet, only one element
remains, so no valid pair can be formed.

### Q4. Does this work with negative numbers?

Yes. The reasoning depends on sorted order, not on all values being
positive.

### Q5. What if there are duplicate values?

It still works. The array is sorted in non-decreasing order, so
duplicates are allowed. The pointers refer to positions, and
`left < right` ensures two distinct positions.

### Q6. Can we solve it in `O(1)` time?

No, not in general. We may need to inspect a linear number of positions.
The two-pointer algorithm is `O(n)`.

### Q7. What is the space complexity?

`O(1)` auxiliary space. We only use two pointer variables and a few
local variables. The required output array is not counted as auxiliary
working space.

------------------------------------------------------------------------

## 14. Independent IDE practice

Before moving to Problem 3, write the solution yourself without looking
at the code.

-   [ ] Write the brute-force solution.
-   [ ] Write the HashMap solution.
-   [ ] Write the two-pointer solution.
-   [ ] Explain why `sum < target` means `left++`.
-   [ ] Explain why `sum > target` means `right--`.
-   [ ] Remember to return 1-based indices.
-   [ ] Test negative numbers and duplicates.
-   [ ] State time and auxiliary-space complexity.

### Small challenge

Without running the code, trace this input:

``` text
numbers = [-10, -3, 0, 5, 9, 12]
target = 2
```

Write down each pair checked, each pointer movement, and the final
1-based indices.

------------------------------------------------------------------------

## 15. Pattern used in this problem

**Opposite-direction two pointers on a sorted array.**

This problem-specific explanation belongs here because it explains how
the general pattern is applied to this exact task. The reusable pattern
rules and templates belong in the separate
`Two_Pointers_Common_Patterns.md` reference file.
