
# DSA PATTERN 1 & 2 — SLIDING WINDOW + TWO POINTERS
## Recognition → Algorithm → Variations → Interview Problems

---

# PATTERN 1 — SLIDING WINDOW

## 1. What Is Sliding Window?

Sliding Window is used when a problem asks us to examine a **continuous section of an array or string**.

Instead of recalculating the answer for every possible section, we maintain a window and update it as it moves.

Imagine this array:

[2, 1, 5, 1, 3, 2]

Suppose the question asks:

Find the maximum sum of 3 consecutive elements.

Windows:

[2, 1, 5] = 8
[1, 5, 1] = 7
[5, 1, 3] = 9
[1, 3, 2] = 6

Answer = 9

Notice that consecutive windows overlap. We don't need to calculate each sum from scratch.

When the window moves:
- Remove the element leaving the window.
- Add the element entering the window.

This is the central idea of Sliding Window.

---

## 2. How to Recognize Sliding Window

Look for these clues in the problem statement.

| Problem clue | Possible pattern |
|---|---|
| K consecutive elements | Fixed Sliding Window |
| Substring or subarray | Sliding Window may apply |
| Longest continuous section satisfying a condition | Variable Sliding Window |
| Smallest continuous section satisfying a condition | Variable Sliding Window |
| Maximum sum of K consecutive elements | Fixed Sliding Window |
| Longest substring without repeating characters | Variable Sliding Window |
| Minimum length subarray with sum at least target | Variable Sliding Window if values are positive |
| Count substrings satisfying a condition | Sliding Window may apply |
| Find a pair anywhere in a sorted array | Usually Two Pointers, not Sliding Window |
| Find the top K elements regardless of position | Usually Heap, not Sliding Window |

Important:

A subarray or substring is continuous.

Example:

Array: [2, 4, 6, 8]

Valid subarrays:
[2, 4], [4, 6], [6, 8], [2, 4, 6]

[2, 6] is NOT a subarray because the elements are not adjacent.

### Recognition checklist

Ask yourself:

1. Does the problem involve a continuous part of an array or string?
2. Can I represent that part using left and right boundaries?
3. Can I update information when the window expands or shrinks?

If yes, Sliding Window may be appropriate.

However, not every subarray problem can use the ordinary Sliding Window technique. Negative numbers can break some sum-based window strategies.

---

## 3. Sliding Window Decision Tree

Question: Is the problem about a continuous section?

- NO → Look for another pattern.
- YES → Continue.

Does the question specify a fixed size K?

- YES → Fixed Sliding Window.
- NO → Continue.

Does it ask for the longest or shortest valid section?

- YES → Consider Variable Sliding Window.
- NO → Check whether it asks for a count, sum, frequency, or another property of windows.

Does the condition depend on a running sum with negative numbers?

- YES → Ordinary expand/shrink logic may fail. Consider Prefix Sum + HashMap or another technique.
- NO → A standard Variable Sliding Window may work.

---

## 4. The Two Main Types

### Type A — Fixed-Size Sliding Window

The window must always contain exactly K elements.

Examples:
- Maximum sum of K consecutive elements.
- Minimum sum of K consecutive elements.
- Maximum average of K consecutive elements.
- Maximum number of vowels in a substring of length K.
- First negative number in every window of size K.

The window size never changes.

### Type B — Variable-Size Sliding Window

The window grows and shrinks depending on a condition.

Examples:
- Longest substring without repeating characters.
- Minimum-size subarray sum.
- Longest substring with at most K distinct characters.
- Longest subarray with at most K zeros.
- Smallest substring containing all required characters.

The window size changes.

---

# FAMILY A — FIXED-SIZE SLIDING WINDOW

## 5. How to Recognize It

Look for phrases such as:
- Exactly K consecutive elements.
- Window of size K.
- Every substring of length K.
- Maximum or minimum over every K-sized section.

## 6. Algorithm

1. Create a window containing the first K elements.
2. Calculate the initial window's answer.
3. Move the right boundary one position forward.
4. Remove the element leaving from the left.
5. Add the new element entering from the right.
6. Update the best answer.
7. Continue until the array ends.

The important formula is:

newWindowSum = oldWindowSum - outgoingElement + incomingElement

## 7. Java Template — Maximum Sum

    static int maxSum(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid window size");
        }

        int windowSum = 0;

        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        int maxSum = windowSum;

        for (int right = k; right < arr.length; right++) {
            windowSum -= arr[right - k];
            windowSum += arr[right];

            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

Complexity:
- Time: O(n)
- Extra space: O(1)

## 8. Dry Run

Array: [2, 1, 5, 1, 3, 2]
K = 3

Initial window:

[2, 1, 5] → Sum = 8

Move right to the next element, 1.

Remove 2 and add 1.

New sum = 8 - 2 + 1 = 7

Window: [1, 5, 1]

Move right to 3.

Remove 1 and add 3.

New sum = 7 - 1 + 3 = 9

Window: [5, 1, 3]

Move right to 2.

Remove 5 and add 2.

New sum = 9 - 5 + 2 = 6

Window: [1, 3, 2]

Maximum = 9

## 9. How to Change the Algorithm for Variations

| Problem | What changes? |
|---|---|
| Maximum sum | Keep the largest sum |
| Minimum sum | Keep the smallest sum |
| Maximum average | Keep the largest sum, then divide by K |
| Count vowels | Add 1 when a vowel enters; subtract 1 when one leaves |
| First negative in each window | Maintain a deque of indices of negative values |
| Maximum element in each window | Use a monotonic deque |
| Count distinct values in each window | Use a frequency HashMap |

The window movement remains the same. The information maintained inside the window changes.

---

# FAMILY B — VARIABLE-SIZE SLIDING WINDOW

## 10. How to Recognize It

Look for:
- Longest continuous section.
- Shortest continuous section.
- Maximum or minimum length satisfying a condition.
- A window that must become valid or remain valid.

Examples:
- Longest substring without duplicate characters.
- Minimum subarray length whose sum is at least a target.
- Longest subarray with at most K zeros.
- Longest substring with at most K distinct characters.

## 11. The Three Operations

Every standard variable window has three important operations.

### Operation 1 — Expand

Move the right pointer to include a new element.

    right++;

### Operation 2 — Shrink

Move the left pointer forward to remove elements.

    left++;

### Operation 3 — Update the Answer

Record the current window's length or other result.

    length = right - left + 1;

But the order of these operations depends on whether the problem asks for the longest or shortest valid window.

---

## 12. Longest Valid Window

Typical question:

Find the longest substring without repeating characters.

Strategy:

1. Expand the window by moving right.
2. Add the new character to the window's tracking data.
3. If the window becomes invalid, shrink it until valid again.
4. Update the longest answer.

General template:

    int left = 0;
    int best = 0;

    for (int right = 0; right < n; right++) {

        // Add arr[right] to the window.

        while (/* window is invalid */) {
            // Remove arr[left] from the window.
            left++;
        }

        // Window is valid.
        best = Math.max(best, right - left + 1);
    }

Why update after shrinking?

Because we want the longest VALID window. We must first restore validity.

### Example — Longest Substring Without Repeating Characters

Input: "abcabcbb"

Start with an empty window.

- Add 'a' → "a", length 1
- Add 'b' → "ab", length 2
- Add 'c' → "abc", length 3
- Add another 'a' → "abca", invalid

Shrink from the left:
- Remove 'a' → "bca", valid

Continue expanding and shrinking.

Answer = 3, because "abc" has no repeated characters.

Java solution:

    static int lengthOfLongestSubstring(String s) {
        Set<Character> seen = new HashSet<>();

        int left = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);

            while (seen.contains(current)) {
                seen.remove(s.charAt(left));
                left++;
            }

            seen.add(current);

            best = Math.max(best, right - left + 1);
        }

        return best;
    }

Complexity:
- Time: O(n) expected
- Extra space: O(min(n, character-set size))

---

## 13. Shortest Valid Window

Typical question:

Find the minimum length of a subarray whose sum is at least the target.

For the standard version, assume all array elements are positive.

Strategy:

1. Expand right and add the new element to the running sum.
2. While the window satisfies the target condition:
   - Record the current answer.
   - Remove the leftmost element.
   - Move left forward.
3. Continue expanding.

Why shrink while the condition is satisfied?

Because the current window is valid, but a smaller valid window may exist.

General template:

    int left = 0;
    int best = Integer.MAX_VALUE;
    int sum = 0;

    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];

        while (sum >= target) {
            best = Math.min(best, right - left + 1);

            sum -= arr[left];
            left++;
        }
    }

    return best == Integer.MAX_VALUE ? 0 : best;

Java solution:

    static int minSubArrayLen(int target, int[] arr) {
        int left = 0;
        int sum = 0;
        int best = Integer.MAX_VALUE;

        for (int right = 0; right < arr.length; right++) {
            sum += arr[right];

            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= arr[left];
                left++;
            }
        }

        return best == Integer.MAX_VALUE ? 0 : best;
    }

Example:

Array = [2, 3, 1, 2, 4, 3]
Target = 7

One valid window is [2, 3, 1, 2], sum = 8, length = 4.

Continue shrinking and expanding. The shortest valid window is [4, 3], sum = 7, length = 2.

Answer = 2.

Important limitation:

This approach relies on positive values for its usual sum-based shrinking logic. If negative numbers are allowed, consider Prefix Sum + Monotonic Deque or Prefix Sum + HashMap depending on the exact question.

---

## 14. Why Do We Sometimes Use `if` and Sometimes `while`?

This is a very common interview question.

### Use `if` when:

You only need one action, or the problem guarantees that one adjustment is sufficient.

Example:

    if (windowSum > maxSum) {
        maxSum = windowSum;
    }

This updates an answer once.

### Use `while` when:

The window may remain invalid after one removal, or you need to keep shrinking while a condition holds.

Example:

    while (seen.contains(current)) {
        seen.remove(s.charAt(left));
        left++;
    }

There may be multiple elements to remove before the duplicate disappears.

Memory trick:

- `if` = check once.
- `while` = repeat until the condition changes.

---

## 15. Why Is Window Length `right - left + 1`?

Both boundaries are included.

Example:

Array = [10, 20, 30, 40, 50]

If left = 1 and right = 3, the window is:

[20, 30, 40]

Length = 3

Calculation:

3 - 1 + 1 = 3

The `+1` is needed because both endpoints are part of the window.

---

## 16. Important Sliding Window Variations

### Longest substring with at most K distinct characters

Recognition:
- Continuous substring.
- Longest length.
- At most K different characters.

Approach:
- Use a frequency map.
- Expand right and increase the character's count.
- While the map contains more than K distinct characters, shrink left.
- Update the maximum length.

### Longest subarray with at most K zeros

Recognition:
- Continuous section.
- Can contain at most K zeros.
- Find maximum length.

Approach:
- Count zeros in the window.
- Expand right.
- While zero count exceeds K, shrink left.
- Update maximum length.

### Count subarrays with sum exactly K

Do not automatically use ordinary Sliding Window.

If negative numbers are possible, use Prefix Sum + HashMap.

If all values are positive, other sliding-window approaches may be possible, but Prefix Sum + HashMap is a widely useful general solution.

### Maximum element in every window of size K

Use a monotonic deque of indices.

Why?
A running sum is not enough because we need the maximum value of each window.

### Smallest substring containing all required characters

Use a frequency map and a variable window.

Expand until all required characters are covered, then shrink while coverage remains valid to find the smallest window.

---

## 17. Sliding Window Mistakes

1. Forgetting to remove the outgoing element in a fixed-size window.
2. Updating the answer before the window becomes valid.
3. Shrinking only once when several removals may be necessary.
4. Using `right - left` instead of `right - left + 1`.
5. Using Sliding Window for sum conditions with negative values without proving the logic works.
6. Forgetting empty arrays or strings.
7. Ignoring K = 0, K > array length, or invalid input.
8. Using a HashSet when a frequency map is necessary.
9. Confusing a subarray with a subsequence. A subsequence does not need to be continuous.

---

# PATTERN 2 — TWO POINTERS

## 18. What Is Two Pointers?

Two Pointers means maintaining two indices that move through an array, string, or linked structure to solve a problem efficiently.

Instead of checking every possible pair or repeatedly scanning the same elements, we use the relationship between the two pointers to eliminate unnecessary work.

Example:

Array = [1, 2, 4, 6, 9]
Target = 10

Start with:

left = 0 → value 1
right = 4 → value 9

Sum = 1 + 9 = 10

Found the answer.

The two pointers move according to a rule. The rule is more important than the pointer names.

---

## 19. How to Recognize Two Pointers

| Problem clue | Possible technique |
|---|---|
| Pair with a target sum in a sorted array | Opposite-end pointers |
| Check whether a string is a palindrome | Opposite-end pointers |
| Reverse an array or string | Opposite-end pointers |
| Remove duplicates in a sorted array | Read/write pointers |
| Move zeros to the end | Read/write pointers |
| Merge two sorted arrays | One pointer per array |
| Compare two strings or arrays | One pointer per input |
| Find triplets that sum to a target in a sorted array | Sorting + Two Pointers |
| Find a continuous section with a condition | Consider Sliding Window |
| Find the middle or cycle of a linked list | Fast & Slow Pointers |

Important:

Two Pointers is a broad technique. It is not limited to sorted arrays, but sorting often makes pointer movement predictable.

---

## 20. Two Pointers Decision Tree

Does the problem involve a pair or comparison between positions?

- NO → Another pattern may be better.
- YES → Continue.

Is the input sorted, or can sorting be used without breaking the requirements?

- YES → Consider pointers moving from opposite ends.
- NO → Consider HashMap/HashSet, read/write pointers, or another approach.

Does the problem ask you to modify an array in place?

- YES → Consider read/write pointers.
- NO → Continue.

Are there two sorted inputs?

- YES → Consider one pointer per input.
- NO → Determine what relationship allows a pointer to move safely.

The key question:

**When I move one pointer, can I prove that some candidates no longer need to be checked?**

If yes, Two Pointers may help.

---

# FAMILY A — OPPOSITE-END POINTERS

## 21. How to Recognize It

Look for:
- Pair sum in a sorted array.
- Palindrome validation.
- Reverse an array or string.
- Pair comparisons from both ends.

Typical initialization:

    int left = 0;
    int right = arr.length - 1;

Typical loop:

    while (left < right) {
        // Compare values and decide which pointer moves.
    }

## 22. Example — Two Sum in a Sorted Array

Input:

[1, 2, 4, 6, 9]
Target = 10

Algorithm:

1. Start at both ends.
2. Calculate the sum.
3. If the sum equals target, return the pair.
4. If the sum is too small, move left forward.
5. If the sum is too large, move right backward.

Why?

Because the array is sorted.

If the sum is too small, moving right backward would make the sum even smaller or equal. We need a larger value, so move left forward.

If the sum is too large, move left forward would make the sum even larger or equal. We need a smaller value, so move right backward.

Java solution:

    static int[] twoSumSorted(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int sum = arr[left] + arr[right];

            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{-1, -1};
    }

Complexity:
- Time: O(n)
- Extra space: O(1), excluding the returned array.

Note:
This solution assumes the input is sorted and returns indices. If the original unsorted order must be preserved, sorting the array directly can break the index requirement. A HashMap is often suitable for the unsorted Two Sum problem.

---

## 23. How to Change the Algorithm

| Problem | Pointer movement |
|---|---|
| Pair sum in sorted array | Move left if sum is too small; right if too large |
| Palindrome check | Compare both ends, move both inward |
| Reverse an array | Swap both ends, move both inward |
| Container With Most Water | Move the pointer at the shorter height |
| Three Sum | Fix one value, then use two pointers for the remaining pair |
| Closest pair to target | Compare current difference, then move according to sorted order |

The pointer movement rule changes with the problem.

Do not memorize “always move left” or “always move the smaller value.” First understand what each movement eliminates.

---

# FAMILY B — PALINDROME AND REVERSAL

## 24. Palindrome Recognition

A palindrome reads the same forward and backward.

Examples:
- "level" → palindrome
- "madam" → palindrome
- "hello" → not a palindrome

Algorithm:
1. Put left at the first character.
2. Put right at the last character.
3. Compare both characters.
4. If they differ, return false.
5. Otherwise, move both inward.
6. If the pointers meet or cross, return true.

Java solution:

    static boolean isPalindrome(String s) {
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

Complexity:
- Time: O(n)
- Extra space: O(1)

Variation:
If the problem says to ignore spaces, punctuation, or letter case, skip or normalize those characters before comparing.

---

## 25. Reverse an Array In Place

Algorithm:
1. Start at both ends.
2. Swap the values.
3. Move left forward and right backward.
4. Stop when left is no longer less than right.

Java solution:

    static void reverse(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }

Complexity:
- Time: O(n)
- Extra space: O(1)

---

# FAMILY C — READ/WRITE POINTERS

## 26. How to Recognize It

Look for:
- Remove duplicates in a sorted array.
- Move zeros to the end.
- Remove a particular value in place.
- Compact or filter an array.
- Keep valid elements at the beginning.

The key difference:

- Read pointer: examines each element.
- Write pointer: identifies where the next accepted element should go.

Usually, the read pointer moves through the array, while the write pointer moves only when an element should be retained or written.

---

## 27. Example — Remove Duplicates From a Sorted Array

Input:

[1, 1, 2, 2, 3]

Expected unique prefix:

[1, 2, 3, ...]

We keep one copy of each value.

Algorithm:
1. The first element is already unique.
2. Start the write pointer at index 1.
3. Read each later element.
4. If it differs from the previous unique value, write it at the write pointer.
5. Increase the write pointer.
6. Return the number of unique elements.

Java solution:

    static int removeDuplicates(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }

        int write = 1;

        for (int read = 1; read < arr.length; read++) {
            if (arr[read] != arr[write - 1]) {
                arr[write] = arr[read];
                write++;
            }
        }

        return write;
    }

For [1, 1, 2, 2, 3]:

- Keep the first 1.
- Skip the second 1.
- Write 2.
- Skip the second 2.
- Write 3.

The first three positions become [1, 2, 3].

Return value = 3.

Complexity:
- Time: O(n)
- Extra space: O(1)

Important:
The array length itself does not change. The returned number tells us how many positions contain the meaningful result.

---

## 28. Example — Move Zeros to the End

Input:

[0, 1, 0, 3, 12]

Expected:

[1, 3, 12, 0, 0]

Algorithm:
1. Keep a write pointer at the next position for a nonzero element.
2. Read every element.
3. When a nonzero element appears, write it at the write position.
4. After reading all elements, fill the remaining positions with zeros.

Java solution:

    static void moveZeroes(int[] arr) {
        int write = 0;

        for (int read = 0; read < arr.length; read++) {
            if (arr[read] != 0) {
                arr[write] = arr[read];
                write++;
            }
        }

        while (write < arr.length) {
            arr[write] = 0;
            write++;
        }
    }

Complexity:
- Time: O(n)
- Extra space: O(1)

Variation:
If relative order must be preserved, the write-pointer strategy is especially useful.

---

# FAMILY D — TWO POINTERS ON TWO INPUTS

## 29. How to Recognize It

Look for:
- Two sorted arrays.
- Merge two sorted lists.
- Compare two strings.
- Find common elements in sorted arrays.
- Check whether one string is a subsequence of another.

The idea is to use one pointer for each input.

## 30. Example — Merge Two Sorted Arrays

Input:

A = [1, 3, 5]
B = [2, 4, 6]

Algorithm:
1. Compare the current elements from A and B.
2. Add the smaller one to the result.
3. Move the pointer belonging to the chosen array.
4. When one array finishes, copy the remaining elements from the other array.

Java solution:

    static int[] merge(int[] a, int[] b) {
        int i = 0;
        int j = 0;
        int k = 0;

        int[] result = new int[a.length + b.length];

        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }

        while (i < a.length) {
            result[k++] = a[i++];
        }

        while (j < b.length) {
            result[k++] = b[j++];
        }

        return result;
    }

Complexity:
- Time: O(n + m)
- Extra space: O(n + m) for the result.

---

# FAMILY E — THREE SUM AND RELATED PROBLEMS

## 31. How to Recognize It

Look for:
- Find three numbers that sum to a target.
- Find triplets satisfying a relationship.
- Find a combination of three values in an array.

A common approach:

1. Sort the array.
2. Fix one element.
3. Use opposite-end pointers for the remaining elements.
4. Move the pointers based on the current sum.
5. Skip duplicate values when unique triplets are required.

Example:

Input = [-1, 0, 1, 2, -1, -4]
Target = 0

After sorting:

[-4, -1, -1, 0, 1, 2]

Fix -1 at index 1.

Search the remaining section for a pair whose sum is 1.

Pair (-1, 2) gives the triplet:

[-1, -1, 2]

Pair (0, 1) gives:

[-1, 0, 1]

These are the unique triplets that sum to zero.

Complexity:
- Sorting: O(n log n)
- Two-pointer search for each fixed element: O(n²) overall
- Extra space depends on the sorting implementation and result size.

Important:
Sorting is useful when the problem permits it. If the original order or original indices must be preserved, account for that requirement.

---

# 32. How to Choose the Correct Pointer Movement

| Situation | What to do |
|---|---|
| Sorted pair sum is too small | Move left forward |
| Sorted pair sum is too large | Move right backward |
| Palindrome characters match | Move both inward |
| Reversing an array | Swap, then move both inward |
| Read element should be retained | Write it at the write pointer |
| Read element should be skipped | Move read only |
| Two sorted inputs: first value is smaller | Consume that input's value |
| Three Sum current sum is too small | Move left forward |
| Three Sum current sum is too large | Move right backward |

Never move pointers randomly. Every movement should be justified by the sorted order, comparison rule, or requirement to retain elements.

---

# 33. Sliding Window vs Two Pointers

These patterns are related, but they solve different kinds of problems.

| Question | Sliding Window | Two Pointers |
|---|---|---|
| Main purpose | Maintain a continuous window | Compare or process positions efficiently |
| Typical boundaries | Left and right define a window | Pointers may represent separate candidates |
| Common movement | Expand and possibly shrink | Move according to a comparison rule |
| Typical problem | Longest substring without repeats | Pair sum in a sorted array |
| Continuous section required? | Usually yes | Not necessarily |
| Can use a frequency map? | Frequently | Sometimes |
| Common complexity | O(n) | O(n), or O(n²) for patterns such as Three Sum |

Important:

A Sliding Window solution often uses two pointers, but not every Two Pointers solution is a Sliding Window solution.

Example:
- Longest substring without repeating characters → Sliding Window.
- Palindrome validation → Opposite-end Two Pointers.
- Remove duplicates in a sorted array → Read/Write Two Pointers.
- Find the middle of a linked list → Fast & Slow Pointers.

---

# 34. Common Two Pointers Mistakes

1. Using opposite-end pointers on an unsorted array without a valid reason.
2. Sorting an array when the original indices or order must be preserved.
3. Moving the wrong pointer after comparing values.
4. Using `left <= right` when the algorithm requires two distinct positions and should stop at `left == right`.
5. Forgetting to skip duplicates in Three Sum.
6. Forgetting to process remaining elements when merging two arrays.
7. Returning the array's original length after in-place filtering instead of the meaningful result length.
8. Forgetting to handle an empty array.
9. Assuming every pair-sum problem should use Two Pointers; unsorted Two Sum often uses a HashMap.
10. Confusing read/write pointers with pointers at opposite ends.

---

# 35. Interview Follow-Up Questions

## Sliding Window

Q: Why is Sliding Window often O(n)?

A: Each pointer usually moves only forward. Although there is a nested while loop, the left pointer does not restart from zero for every right-pointer position. Across the entire algorithm, each element enters and leaves the window at most once.

Q: When should you use a frequency map?

A: When validity depends on the number of occurrences or distinct values, such as character frequencies or at most K distinct characters.

Q: Does Sliding Window always work for subarray sum problems?

A: No. Ordinary sum-based expand/shrink logic generally relies on positive values. Negative values can make the sum increase or decrease unpredictably when the window changes.

## Two Pointers

Q: Why is sorted order important for pair sum?

A: It tells us which pointer movement can safely eliminate candidates.

Q: Can Two Pointers work on an unsorted array?

A: Yes, for techniques such as read/write pointers. Opposite-end pair-sum logic, however, usually needs sorted input.

Q: What is the difference between Two Pointers and Fast & Slow Pointers?

A: Fast & Slow is a specialized form where pointers move at different speeds, often to find a middle or detect a cycle.

---

# 36. How to Solve an Unfamiliar Interview Problem

Use this checklist before writing code.

## Step 1 — Identify the data

Is it an array, string, linked list, or two sorted arrays?

## Step 2 — Identify the requirement

Does the problem ask for:
- A continuous section?
- A pair or triplet?
- A comparison from both ends?
- In-place modification?
- A maximum or minimum length?
- A fixed-size section?

## Step 3 — Choose the family

- Exactly K consecutive elements → Fixed Sliding Window.
- Longest or shortest valid continuous section → Variable Sliding Window, if the condition supports it.
- Pair sum in sorted data → Opposite-end Two Pointers.
- Reverse or palindrome → Opposite-end Two Pointers.
- Remove or compact elements in place → Read/Write Two Pointers.
- Merge sorted inputs → One pointer per input.
- Find the middle or detect a cycle → Fast & Slow Pointers.

## Step 4 — Define each pointer's responsibility

For every pointer, say in plain English what it represents.

Examples:
- `left`: first index still inside the current window.
- `right`: newest element entering the window.
- `read`: element currently being examined.
- `write`: next position where a valid element should be stored.

If you cannot explain a pointer's responsibility, pause before coding.

## Step 5 — Decide when to update the answer

- Longest valid window → after restoring validity.
- Shortest valid window → while the window is valid, before removing its leftmost element.
- Pair sum → when the comparison identifies a matching pair.
- In-place filtering → when an element satisfies the retention condition.

## Step 6 — Dry-run a small input

Track every pointer movement manually.

## Step 7 — Check edge cases

Try:
- Empty input.
- One element.
- No valid answer.
- All values equal.
- Duplicate values.
- Target at the beginning or end.
- Invalid window size.
- Negative values, if allowed.

## Step 8 — Explain complexity

State the time and extra space, and explain why.

---

# 37. Five-Second Revision Table

| If the question says... | Think... |
|---|---|
| K consecutive elements | Fixed Sliding Window |
| Longest valid substring | Variable Sliding Window |
| Shortest valid subarray | Variable Sliding Window, if the condition supports it |
| At most K distinct values | Variable Sliding Window + frequency map |
| Pair sum in sorted array | Opposite-end Two Pointers |
| Palindrome | Opposite-end Two Pointers |
| Reverse array in place | Opposite-end Two Pointers |
| Remove duplicates in place | Read/Write Two Pointers |
| Move zeros to end | Read/Write Two Pointers |
| Merge two sorted arrays | One pointer per input |
| Find three values summing to a target | Sort + Two Pointers |
| Find middle or detect cycle | Fast & Slow Pointers |
| Subarray sum equals K with negative values | Prefix Sum + HashMap |

---

# 38. Practice Order

Solve these in order. For each one, explain why the pattern fits before writing code.

## Sliding Window

1. Maximum sum of K consecutive elements.
2. Maximum number of vowels in a substring of size K.
3. Minimum-size subarray sum.
4. Longest substring without repeating characters.
5. Longest substring with at most K distinct characters.
6. Maximum of every sliding window.
7. Minimum window substring.
8. Subarray sum equals K — compare Sliding Window limitations with Prefix Sum + HashMap.

## Two Pointers

1. Reverse an array.
2. Valid palindrome.
3. Two Sum in a sorted array.
4. Remove duplicates from a sorted array.
5. Move zeros to the end.
6. Merge two sorted arrays.
7. Container With Most Water.
8. Three Sum.
9. Is Subsequence.
10. Sort Colors — compare read/write or multi-pointer approaches.

---

# FINAL MEMORY RULE

Sliding Window:

**Maintain a continuous section. Expand, shrink when necessary, and update the answer at the correct time.**

Two Pointers:

**Give each pointer a clear responsibility, then move it only when the algorithm's logic justifies that movement.**

Before solving, ask:

1. Is this about a continuous section?
2. Is the section fixed-size or variable-size?
3. Is the input sorted?
4. Am I comparing both ends, reading and writing, or processing two inputs?
5. What exactly does each pointer mean?
6. Why is this pointer allowed to move?
