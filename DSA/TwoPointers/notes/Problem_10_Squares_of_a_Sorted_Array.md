# Problem 10 — Squares of a Sorted Array

> **Pattern:** Compare values at both ends; fill answer from the **largest** square downward  
> **Goal:** See why the biggest squared value always lives at one of the two ends — not in the middle.

---

## 1. Problem statement

Given an integer array `nums` sorted in **non-decreasing** order, return an array of the **squares of each number**, also sorted in non-decreasing order.

### Example

```text
Input:  nums = [-4, -1, 0, 3, 10]
Output: [0, 1, 9, 16, 100]
```

Explanation:

```text
(-4)^2 = 16
(-1)^2 = 1
0^2 = 0
3^2 = 9
10^2 = 100

Sorted squares: [0, 1, 9, 16, 100]
```

### Example 2

```text
Input:  nums = [-7,-3,-2,3,11]
Output: [4,9,9,49,121]
```

### Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is sorted in non-decreasing order

---

## 2. Mental picture — one picture to keep in mind

Sorted numbers with negatives sit like this on a number line:

```text
   -4  -1   0   3        10
    ^               ^
  left            right
```

Squares grow as you move **away from 0**:

```text
distance from 0:  4   1   0   3   10
squared:       16   1   0   9  100
                      ^
                   smallest near 0
```

The **largest** square is always at one of the **ends** (most negative or most positive).

```text
nums:  [ ?,  ?,  small near 0,  ?,  ? ]
        ^                            ^
     |big neg|                  |big pos|
     largest squares live here
```

> **Read it as:** “Sort squares by picking the bigger end each time and writing into the back of the answer.”

---

## 3. How to start thinking about the solution

1. **Output size?** Same length as input — new array (or overwrite if allowed).
2. **Brute?** Square every number, then sort — simple, slower.
3. **Why not scan left to right for squares?** Squares are **not** sorted left-to-right when negatives exist.
4. **Where is the largest square?** Compare `|nums[left]|` and `|nums[right]|`.
5. **Where should I write the largest?** At the **end** of the answer, then move inward — same spirit as merge-from-end.

---

## 4. Approach 1 — Brute force (square, then sort)

### Idea

1. Create `squared[i] = nums[i] * nums[i]`
2. `Arrays.sort(squared)`

### Why we start here

It proves you understand the output format. One line of reasoning: “I can always sort.”

### Java

```java
public int[] sortedSquaresBruteForce(int[] nums) {
    int[] squared = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
        squared[i] = nums[i] * nums[i];
    }
    Arrays.sort(squared);
    return squared;
}
```

### Dry run

```text
nums = [-4, -1, 0, 3, 10]
squared = [16, 1, 0, 9, 100]
sorted  = [0, 1, 9, 16, 100]
```

### Complexity

| | Value |
|---|---|
| Time | `O(n log n)` (sort dominates) |
| Space | `O(n)` for output |

### Limitation

Ignores that input was already sorted. We can do `O(n)`.

---

## 5. Approach 2 — Optimal: two pointers from both ends

### Step-by-step

1. `left = 0`, `right = n - 1`
2. `write = n - 1` in new `result`
3. While `left <= right`:
   - `leftSq = nums[left] * nums[left]`
   - `rightSq = nums[right] * nums[right]`
   - If `leftSq > rightSq` → `result[write] = leftSq`, `left++`
   - Else → `result[write] = rightSq`, `right--`
   - `write--`
4. Return `result`

### Visual frames

```text
nums:    [-4, -1,  0,  3, 10]
          L                  R

leftSq=16, rightSq=100 -> write 100 at index 4
nums:    [-4, -1,  0,  3, 10]
          L              R

16 vs 9 -> write 16 at index 3
nums:    [-4, -1,  0,  3, 10]
          L          R

16 vs 9 -> write 9 at index 2
...
```

### Why pointer moves

- The **bigger square** must appear at the **end** of the still-empty part of `result`.
- After placing it, discard that end by moving `left` or `right` inward.
- Smaller squares are decided later toward the front of `result`.

When squares are **equal** (e.g. `-3` and `3`), pick either side — order among equals does not matter.

---

## 6. The hardest part — why the maximum square is always at an end

### Before (wrong mental model)

```text
"I will walk left to right and squares will stay sorted."
nums = [-3, -2, 0, 1, 2]
squares left-to-right: 9, 4, 0, 1, 4  --> not sorted
```

### After (correct model)

For sorted `nums`, values near index `0` are **most negative**; values near `n-1` are **most positive**. Squaring is symmetric around 0:

```text
...  -5  -2   0   1   4  ...
      |large|     |large|
```

Anything in the **middle** is closer to 0 → **smaller absolute value** → **smaller square** than at least one end.

**Before / after example**

```text
nums = [-7, -3, -2, 3, 11]

Middle value -2 -> square 4
Left end -7 -> 49
Right end 11 -> 121

Max square 121 is at R, not in the middle.
```

Formal sketch: if `i < j`, then for negatives at the left end, `|nums[0]| >= |nums[i]|`; for positives at the right end, `|nums[n-1]| >= |nums[j]|`. The global max of squares is `max(nums[0]^2, nums[n-1]^2)`.

---

## 7. Detailed dry run with role diagram

`nums = [-4, -1, 0, 3, 10]`

```text
L = left, R = right, W = write index in result
```

| step | L | R | leftSq | rightSq | place at W | move | result (concept) |
|---:|---:|---:|---:|---:|---|---|---|
| 1 | 0 | 4 | 16 | 100 | 100 @4 | R-- | `[?,?,?,?,100]` |
| 2 | 0 | 3 | 16 | 9 | 16 @3 | L++ | `[?,?,?,16,100]` |
| 3 | 1 | 3 | 1 | 9 | 9 @2 | R-- | `[?,?,9,16,100]` |
| 4 | 1 | 2 | 1 | 0 | 1 @1 | L++ | `[?,1,9,16,100]` |
| 5 | 2 | 2 | 0 | 0 | 0 @0 | L++ | `[0,1,9,16,100]` |

Role diagram at step 1:

```text
nums:  -4  -1   0   3  10
       L               R
result write slot:  [ ? ? ? ? |100 ]
```

---

## 8. Why the algorithm is correct

**Claim:** At any step, the largest square among unprocessed elements is either `nums[left]^2` or `nums[right]^2`.

**Reason:** All unprocessed elements lie in `nums[left..right]`. The smallest absolute value in that range is closest to the middle of the subarray; the largest absolute values are at the ends of the **original** sorted array restricted to this window — proved by monotonicity away from 0.

**Loop:** We place that maximum at `result[write]`, shrink the window, repeat. By induction, `result` is sorted descending as we fill; hence ascending when read normally.

---

## 9. Complexity comparison

| Approach | Time | Extra space | Notes |
|---|---:|---:|---|
| Square + sort | `O(n log n)` | `O(n)` output | Brute |
| **Two pointers** | `O(n)` | `O(n)` output | Optimal time |

Output array is required — `O(n)` space is unavoidable for a new return value.

---

## 10. Edge cases

| Input | Output | What it checks |
|---|---|---|
| `[-4,-1,0,3,10]` | `[0,1,9,16,100]` | Classic |
| `[-7,-3,-2,3,11]` | `[4,9,9,49,121]` | Duplicate squares |
| `[-5,-3,-1]` | `[1,9,25]` | All negative |
| `[1,2,3]` | `[1,4,9]` | All positive |
| `[0]` | `[0]` | Single element |
| `[-1,0,1]` | `[0,1,1]` | Symmetric around 0 |

---

## 11. Common mistakes

1. **Moving left when right square is larger** — scrambles descending fill order.
2. **Writing from index 0 forward** — fights the “largest at end” logic.
3. **Using `left < right` instead of `<=`** — can leave one element unplaced.
4. **Forgetting that negatives square to positive** — compare **squares**, not raw values.
5. **Integer overflow** — in these constraints `10^4^2 = 10^8` fits in `int`; mention `long` for bigger limits.

---

## 12. How to recognise the pattern

**Clues:**

- Sorted input
- Transform each element (square)
- Output must be sorted
- Negatives make naive left-to-right order wrong
- **Largest results come from the ends**

**Compared to earlier problems**

| Problem | End pointer lesson |
|---|---|
| Merge Sorted Array | Fill merged answer from back |
| **Squares of Sorted Array** | Fill **largest squares** from back |
| Valid Palindrome | Compare ends directly |
| Trapping Rain Water | Process weaker height end |

Reusable sentence:

> **Sorted array with negatives → biggest squares at the ends → two pointers write backward.**

---

## 13. Interview explanation — say this aloud

“I could square every number and sort in `O(n log n)`. Because the input is sorted, the largest squared value is always at one of the two ends — the most negative or the most positive number. I use two pointers at the ends and fill a result array from the back with the larger square at each step, moving the pointer that I used. Each step removes one element, so `O(n)` time and `O(n)` space for the output.”

---

## 14. Interview follow-up Q&A

**Q1. Why not sort after squaring?**  
Valid brute force; two pointers exploit sorted order.

**Q2. Why `left <= right`?**  
One element left must still be written when pointers meet.

**Q3. What if all numbers are non-negative?**  
Squares are already non-decreasing left-to-right; you could scan forward — two pointers still work uniformly.

**Q4. Generalisation?**  
Any monotone transform where extremes are at ends (careful with custom functions).

**Q5. Stable order for equal squares?**  
Either tie-break is fine for this problem.

---

## 15. Complete runnable Java class

Save as **`SquaresOfASortedArray.java`** (lives in `java/`).

```java


import java.util.Arrays;

public class SquaresOfASortedArray {

  public static int[] sortedSquaresBruteForce(int[] nums) {
    int[] squared = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      squared[i] = nums[i] * nums[i];
    }
    Arrays.sort(squared);
    return squared;
  }

  public static int[] sortedSquaresTwoPointers(int[] nums) {
    int left = 0;
    int right = nums.length - 1;
    int[] result = new int[nums.length];
    int write = nums.length - 1;
    while (left <= right) {
      int leftSq = nums[left] * nums[left];
      int rightSq = nums[right] * nums[right];
      if (leftSq > rightSq) {
        result[write--] = leftSq;
        left++;
      } else {
        result[write--] = rightSq;
        right--;
      }
    }
    return result;
  }

  public static void main(String[] args) {
    int[] nums = {-4, -1, 0, 3, 10};
    System.out.println("Input:    " + Arrays.toString(nums));
    System.out.println("Brute:    " + Arrays.toString(sortedSquaresBruteForce(nums.clone())));
    System.out.println("Two Ptrs: " + Arrays.toString(sortedSquaresTwoPointers(nums.clone())));
  }
}
```

---

## 16. Independent IDE practice

- [ ] Implement brute and optimal
- [ ] Trace `[-1,0,1]` by hand
- [ ] Explain why max square is at an end
- [ ] Run main and verify `[0,1,9,16,100]`
- [ ] Handle `[-7,-3,-2,3,11]` duplicate squares

### Small challenge

Without running code, write the two-pointer trace for:

```text
nums = [-3, -2, 0, 1, 2]
```

List each value written to `result` from index `4` down to `0`.

---

## 17. Quick revision

1. Squaring breaks left-to-right order when negatives exist.
2. Largest square is always at `left` or `right`.
3. Fill `result` from the back with the bigger square.
4. `O(n)` time, `O(n)` output space.
5. Same “write from end” spirit as merge sorted array.

---

## 18. Pattern used in this problem + roadmap complete

**Opposite-end two pointers comparing transformed values; fill answer backward.**

This is the **last problem** in this Two Pointers roadmap (Problems 01–10).

You now have:

| # | Problem | Core idea |
|---|---|---|
| 01 | Valid Palindrome | Compare ends |
| 02 | Two Sum II | Sorted pair steering |
| 03 | Container With Most Water | Greedy shorter wall |
| 04 | 3Sum | Sort + fix + two pointers |
| 05–07 | (companion notes in folder) | Slow/fast, duplicates, etc. |
| 08 | Trapping Rain Water | Left/right max |
| 09 | Merge Sorted Array | Merge from end |
| 10 | Squares of Sorted Array | Ends → largest squares |

Reusable closing line:

> **Two pointers from the ends = compare the extremes, then shrink the window.**

Review **`Two_Pointers_Common_Patterns.md`** and redo one problem per pattern from memory.