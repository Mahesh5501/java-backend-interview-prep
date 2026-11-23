# Problem 02 — Two Sum II: Input Array Is Sorted

> **Pattern:** Opposite-direction Two Pointers on a sorted array  
> **Goal:** Understand why “sum too small → move `left`” and “sum too large → move `right`” — not just memorise the branches.

---

## 1. Problem statement

You are given a **1-indexed** array `numbers` sorted in **non-decreasing** order (each value is greater than or equal to the one before it).

You are also given an integer `target`.

Find **two different** numbers whose sum is `target`. Return their **1-based** indices `[index1, index2]`.

- Exactly one solution exists.
- You may not use the same element twice.

### Example 1

```text
Input:  numbers = [2, 7, 11, 15], target = 9
Output: [1, 2]
```

`2 + 7 = 9`.

### Example 2

```text
Input:  numbers = [2, 3, 4], target = 6
Output: [1, 3]
```

`2 + 4 = 6`.

### Example 3

```text
Input:  numbers = [-1, 0], target = -1
Output: [1, 2]
```

### Important for Java

Java arrays are still **0-based** internally.

The problem wants **1-based** answers, so add `1` to each index when you return:

```java
return new int[] { left + 1, right + 1 };
```

### Constraints (LeetCode version)

- `2 <= numbers.length <= 3 * 10^4`
- `-1000 <= numbers[i] <= 1000`
- `-1000 <= target <= 1000`
- Exactly one valid pair exists.

---

## 2. Understand the problem before coding

You need one pair of numbers that adds to `target`.

The array is already sorted. That order is the gift that makes two pointers work in linear time.

### The one picture you should keep in mind

```text
numbers = [2, 7, 11, 15],  target = 9

values:    2    7   11   15
roles:     L -------------- R
sum:       2 + 15 = 17  (too large)
action:    move R leftward
```

Next:

```text
values:    2    7   11   15
roles:     L -------- R
sum:       2 + 7 = 9  (match!)
return:    [1, 2] in 1-based indexing
```

Read it as:

> “Start with the smallest and largest remaining values. The sum tells me which side cannot possibly help anymore.”

### Jargon — explained once

| Term | Meaning here |
|---|---|
| **Sorted / non-decreasing** | Values never go down as you move right. |
| **Target** | The sum you need. |
| **Complement** | For value `x`, the other value you need is `target - x`. |
| **1-based index** | First position is `1`, not `0`. |

---

## 3. How to start thinking about the solution

Before writing code, ask yourself:

1. What am I looking for? Two distinct positions whose values sum to `target`.
2. What do I know? The array is sorted.
3. What is the guaranteed-correct slow way? Check every pair — brute force.
4. Does unsorted Two Sum use a HashMap? Yes — store complements. Still works here, but uses extra memory.
5. Can sorted order guide elimination? Yes — compare smallest + largest, then move one pointer.

---

## 4. Approach 1 — Brute force (every pair)

### Idea

Nested loops: every `i` with every `j > i`. If sum equals `target`, return 1-based indices.

### Why does it work?

Every pair of distinct indices is tried. The unique solution will appear.

### Java method

```java
public static int[] twoSumBruteForce(int[] numbers, int target) {

    for (int i = 0; i < numbers.length; i++) {
        for (int j = i + 1; j < numbers.length; j++) {
            if (numbers[i] + numbers[j] == target) {
                return new int[] { i + 1, j + 1 };
            }
        }
    }

    return new int[] { -1, -1 };
}
```

### Dry run

```text
numbers = [2, 7, 11, 15], target = 9
```

| i | j | Values | Sum | Result |
|---:|---:|---|---:|---|
| 0 | 1 | 2 + 7 | 9 | Found → `[1, 2]` |

### Complexity

- **Time:** `O(n²)`.
- **Auxiliary space:** `O(1)`.

### Limitation

Ignores sorted order. Too slow for large `n`.

---

## 5. Approach 2 — HashMap (classic Two Sum style)

### Idea

Walk left to right. For each value, check if `target - value` was seen before.

A **HashMap** maps each seen value to its index.

### Why include this here?

Interviews often compare:

- **Unsorted Two Sum** → HashMap is the standard `O(n)` approach.
- **Two Sum II (sorted)** → Two pointers also `O(n)` but `O(1)` extra space.

### Java method

```java
public static int[] twoSumHashMap(int[] numbers, int target) {

    Map<Integer, Integer> seen = new HashMap<>();

    for (int i = 0; i < numbers.length; i++) {

        int needed = target - numbers[i];

        if (seen.containsKey(needed)) {
            int previousIndex = seen.get(needed);
            return new int[] { previousIndex + 1, i + 1 };
        }

        seen.put(numbers[i], i);
    }

    return new int[] { -1, -1 };
}
```

### Dry run

```text
numbers = [2, 7, 11, 15], target = 9
```

| i | Value | Needed | Map before | Action |
|---:|---:|---:|---|---|
| 0 | 2 | 7 | `{}` | Store `2 → 0` |
| 1 | 7 | 2 | `{2=0}` | `2` exists → return `[1, 2]` |

### Complexity

- **Time:** `O(n)` average.
- **Auxiliary space:** `O(n)` for the map.

### Limitation

Works, but does not use the sorted property for space savings. Prefer two pointers for this problem.

---

## 6. Approach 3 — Optimal two pointers

### Core idea

```text
left  = 0
right = last index

while left < right:
    sum = numbers[left] + numbers[right]
    if sum == target → return indices
    if sum < target → left++
    else             → right--
```

### Decision table

| Condition | Meaning | Move |
|---|---|---|
| `sum == target` | Found the pair | Return |
| `sum < target` | Need a bigger sum | `left++` |
| `sum > target` | Need a smaller sum | `right--` |

### Optimal Java method

```java
public static int[] twoSumTwoPointers(int[] numbers, int target) {

    int left = 0;
    int right = numbers.length - 1;

    while (left < right) {

        int sum = numbers[left] + numbers[right];

        if (sum == target) {
            return new int[] { left + 1, right + 1 };
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }

    return new int[] { -1, -1 };
}
```

---

## 7. The hardest part explained carefully — which pointer to move?

This is the step interviewers push on.

### Case A — Sum is too small (`sum < target`)

Setup:

```text
numbers = [2, 7, 11, 15],  target = 9
left at 2, right at 15, sum = 17  ← actually too LARGE for this target

Better teaching example for "too small":
numbers = [1, 2, 3, 4, 5, 6], target = 10
left at 1, right at 6, sum = 7  (too small)
```

#### Before (wrong habit)

“I will move `right--` to shrink the sum.”

But `right` is already at the **largest** value in the whole array. Anything to its left is `<= 6`. Keeping `left` fixed and only moving `right` left **never increases** the sum above `1 + 6 = 7`.

So you cannot fix “too small” by only moving `right` when `left` is at the minimum side.

#### After (correct)

Move `left++` to try a **larger** left value. That is the only direction that can increase the sum while keeping a valid pair.

```text
After left++:

1  [2]  3  4  5  6
    L -------- R
2 + 6 = 8 still small → left++ again ...
```

### Case B — Sum is too large (`sum > target`)

```text
numbers = [2, 7, 11, 15], target = 9
left at 2, right at 15, sum = 17 (too large)
```

#### Before (wrong habit)

Move `left++` hoping the sum drops a lot — but larger left values can **increase** the sum further.

#### After (correct)

Move `right--` to use a **smaller** right value.

```text
2  7  11  [15]
L ----- R

Next: 2 + 11 = 13 still large → right--
Next: 2 + 7 = 9 → match
```

### Case C — Why not move both pointers every step?

You might throw away the answer before testing it.

Move **one** side per iteration based on what the sum tells you.

---

## 8. Detailed dry run — Optimal approach

Input:

```text
numbers = [2, 7, 11, 15], target = 9
```

```text
index:     0    1    2    3
values:    2    7   11   15
```

| Step | left | right | nums[left] | nums[right] | sum | Action |
|---:|---:|---:|---:|---:|---:|---|
| 1 | 0 | 3 | 2 | 15 | 17 | Too large → `right--` |
| 2 | 0 | 2 | 2 | 11 | 13 | Too large → `right--` |
| 3 | 0 | 1 | 2 | 7 | 9 | Match → return `[1, 2]` |

### Role diagram — step 1

```text
values:   2    7   11   15
roles:    L -------------- R
          ^smallest        ^largest
sum = 17 > 9  →  discard pairing anything with 15 as right partner for left=0
           actually we move R inward to try smaller right values
```

---

## 9. Why the algorithm never misses the answer

At any time, if the answer is not already returned, both answer indices still lie in `[left, right]`.

**Too small:** For fixed `right`, every index to the left of `right` has a value `<= numbers[right]`. None of those can pair with `numbers[left]` to exceed the current sum without increasing the left side. So `left` cannot be part of any future valid pair with this `right` or any smaller `right` — safe to move `left++`.

**Too large:** Symmetric argument — `right` is safe to discard with `right--`.

**Equal:** Done.

Each step shrinks the window by one impossible position. The guaranteed solution must be found before `left` meets `right`.

---

## 10. Complexity comparison

| Approach | Time | Auxiliary space | Uses sorted order? |
|---|---:|---:|---|
| Brute force | `O(n²)` | `O(1)` | No |
| HashMap | `O(n)` avg | `O(n)` | No |
| Two pointers | `O(n)` | `O(1)` | **Yes** |

**Optimal for this problem:** Two pointers.

At most `n` pointer moves total (each index walks at most across the array once).

---

## 11. Edge cases to practise

| Input | Target | Expected | What it checks |
|---|---:|---|---|
| `[2, 3]` | 5 | `[1, 2]` | Smallest length |
| `[-1, 0]` | -1 | `[1, 2]` | Negatives |
| `[1, 2, 3, 4, 4, 9]` | 8 | `[4, 5]` | Duplicates |
| `[1, 2, 3, 4, 5]` | 6 | `[1, 5]` | Ends of array |
| `[-5, -2, 0, 3, 8]` | 1 | `[2, 4]` | Mixed signs |

---

## 12. Common mistakes

1. **Returning 0-based indices:** Add one before return.
2. **Using `left <= right`:** Need two different elements → `left < right`.
3. **Moving wrong pointer:** Small sum → `left++`; large sum → `right--`.
4. **Re-sorting the array:** Wastes time; array is already sorted.
5. **Defaulting to HashMap:** Works, but ignores `O(1)` space win.
6. **Moving both pointers blindly:** Can skip the answer.

---

## 13. How to recognise this pattern in an interview

Clues:

- Sorted input.
- Need a pair with a target sum.
- Want better than `O(n²)`.

Ask:

> If I take the smallest and largest remaining numbers, does the sum tell me who is hopeless?

If yes → opposite-direction two pointers.

### Sorted vs unsorted Two Sum

| Version | Typical best |
|---|---|
| Unsorted | HashMap `O(n)` time, `O(n)` space |
| Sorted (Two Sum II) | Two pointers `O(n)` time, `O(1)` space |

---

## 14. Interview explanation — Say this aloud

“The array is sorted, so I use two pointers at the ends. I compute the sum. If it is less than the target, I move `left` forward because I need a larger value. If it is greater, I move `right` backward. If it matches, I return the indices in 1-based form. Each pointer moves at most `n` times, so time is `O(n)` and auxiliary space is `O(1)`. I know brute force is `O(n²)` and HashMap is `O(n)` time but `O(n)` space; for a sorted array, two pointers is the preferred tradeoff.”

---

## 15. Interview follow-up questions

### Q1. What if the array were not sorted?

Use HashMap for classic Two Sum. If you sort first, mind original index requirements.

### Q2. Why not move both pointers each time?

The sum implicates one side. Moving both can discard a valid index without proof.

### Q3. Negative numbers?

Yes. Sorted order still holds; logic does not require positives.

### Q4. Duplicate values?

Allowed. `left < right` still enforces two distinct positions.

### Q5. Can we do better than `O(n)`?

Not in the usual comparison model — you must look at Omega(n) positions in the worst case.

---

## 16. Complete runnable Java class — All applicable approaches

Matches **`../TwoSumII.java`** in this folder.

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

        for (int i = 0; i < numbers.length; i++) {

            for (int j = i + 1; j < numbers.length; j++) {

                if (numbers[i] + numbers[j] == target) {
                    return new int[] { i + 1, j + 1 };
                }
            }
        }

        return new int[] { -1, -1 };
    }


    // =====================================================
    // APPROACH 2: HASHMAP
    // Time Complexity: O(n) average
    // Auxiliary Space: O(n)
    // =====================================================

    public static int[] twoSumHashMap(int[] numbers, int target) {

        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {

            int needed = target - numbers[i];

            if (seen.containsKey(needed)) {
                int previousIndex = seen.get(needed);
                return new int[] { previousIndex + 1, i + 1 };
            }

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

        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {

            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[] { left + 1, right + 1 };
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[] { -1, -1 };
    }


    public static void main(String[] args) {

        int[] numbers = { 2, 7, 11, 15 };
        int target = 9;

        System.out.println("Input: " + Arrays.toString(numbers));
        System.out.println("Target: " + target);

        System.out.println(
                "Brute Force: " + Arrays.toString(twoSumBruteForce(numbers, target))
        );
        System.out.println(
                "HashMap: " + Arrays.toString(twoSumHashMap(numbers, target))
        );
        System.out.println(
                "Two Pointers: " + Arrays.toString(twoSumTwoPointers(numbers, target))
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

---

## 17. Independent IDE practice

Before Problem 03:

- [ ] Code all three approaches.
- [ ] Explain Case A and Case B (pointer choice) with your own example.
- [ ] Return 1-based indices.
- [ ] Trace `numbers = [-10, -3, 0, 5, 9, 12], target = 2` by hand.
- [ ] State complexities in one sentence each.

### Small challenge

Without running code, trace:

```text
numbers = [-10, -3, 0, 5, 9, 12]
target = 2
```

List each `(left, right)`, sum, and move until you find the answer.

---

## 18. Quick revision

1. **Start:** `left = 0`, `right = end`.
2. **Small sum:** `left++`.
3. **Large sum:** `right--`.
4. **Return:** `left + 1`, `right + 1`.

Reusable sentence:

> **Two Sum II = sorted array + opposite pointers + sum steers movement.**

---

## 19. Pattern used in this problem

**Opposite-direction Two Pointers on a sorted array.**

Shared pattern notes: **`Two_Pointers_Common_Patterns.md`**.

---

## 20. Next problem

**Problem 03 — Container With Most Water**  
Main concept: Opposite pointers still — but you move the **shorter wall**, not the sum.