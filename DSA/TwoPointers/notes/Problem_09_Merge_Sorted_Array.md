# Problem 09 — Merge Sorted Array

> **Pattern:** Two pointers from the **end** (in-place backward merge)  
> **Goal:** Understand why merging from the front destroys data, and why `write` starts at `m + n - 1`.

---

## 1. Problem statement

You are given two integer arrays `nums1` and `nums2`, sorted in **non-decreasing** order.

`nums1` has length `m + n`, where the first `m` elements are the real data and the last `n` slots are `0` placeholders with extra space.

`nums2` has length `n`.

Merge `nums2` into `nums1` as one sorted array **in-place**. The final sorted order must live inside `nums1`.

### Example

```text
Input:
  nums1 = [1,2,3,0,0,0], m = 3
  nums2 = [2,5,6],       n = 3

Output:
  nums1 = [1,2,2,3,5,6]
```

### Example 2

```text
Input:
  nums1 = [1], m = 1
  nums2 = [],  n = 0

Output:
  nums1 = [1]
```

### Example 3

```text
Input:
  nums1 = [0], m = 0
  nums2 = [1], n = 1

Output:
  nums1 = [1]
```

### Constraints

- `nums1.length == m + n`
- `nums2.length == n`
- `0 <= m, n <= 200`
- `1 <= m + n <= 200`
- `-10^9 <= nums1[i], nums2[j] <= 10^9`

---

## 2. Mental picture — one picture to keep in mind

`nums1` is a bookshelf with books already on the left and **empty space** on the right:

```text
nums1:  [ 1 | 2 | 3 | _ | _ | _ ]
         ^^^^^ m=3 real data ^   ^ n=3 free space at end

nums2:  [ 2 | 5 | 6 ]
```

You must place all numbers into one sorted row **inside nums1 only**.

Bad idea: start filling from the **left** — you would overwrite `2` or `3` before you read them.

Good idea: fill from the **right end**, where the empty slots already are.

```text
write --> starts at the last index (m+n-1)
         fill largest values first into the empty tail
```

> **Read it as:** “Pack the merged answer from the back like sliding the biggest books into the open space on the right.”

---

## 3. How to start thinking about the solution

1. **What is merged output?** The same multiset of values, sorted.
2. **Why not sort all of `nums1` after appending?** Works, but ignores that inputs are already sorted — and may need extra steps.
3. **Why not merge forward into a new array?** Correct, but uses `O(m+n)` extra space; problem asks in-place in `nums1`.
4. **Where is safe empty space?** The last `n` cells of `nums1`.
5. **What do we compare?** The current largest not-yet-placed values from `nums1` and `nums2`.

That leads to **three indices walking backward**.

---

## 4. Approach 1 — Brute force (merge to helper array)

### Idea

1. Use standard forward merge into a new array `merged` of size `m + n`
2. Copy `merged` back into `nums1`

### Why we mention it

It is the **easiest proof** that you understand merging. Interviewers accept it as a stepping stone, then ask for `O(1)` extra space.

### Java

```java
public void mergeBruteForce(int[] nums1, int m, int[] nums2, int n) {
    int[] merged = new int[m + n];
    int i = 0, j = 0, k = 0;

    while (i < m && j < n) {
        if (nums1[i] <= nums2[j]) {
            merged[k++] = nums1[i++];
        } else {
            merged[k++] = nums2[j++];
        }
    }
    while (i < m) merged[k++] = nums1[i++];
    while (j < n) merged[k++] = nums2[j++];

    for (int t = 0; t < m + n; t++) {
        nums1[t] = merged[t];
    }
}
```

### Dry run — main example

```text
nums1: [1,2,3], nums2: [2,5,6]

i,j forward:
  1 vs 2 -> merged [1]
  2 vs 2 -> merged [1,2]
  3 vs 2 -> merged [1,2,2]
  done i, take 5,6 -> [1,2,2,3,5,6]
```

### Complexity

| | Value |
|---|---|
| Time | `O(m + n)` |
| Space | `O(m + n)` helper |

### Limitation

Extra array. LeetCode wants in-place constant extra space.

### Alternate brute: “sort the tail”

Append mentally, then `Arrays.sort(nums1, 0, m+n)` — `O((m+n) log(m+n))`, ignores sorted structure. Mention briefly, do not prefer it.

---

## 5. Approach 2 — Optimal: two pointers from the end

### Step-by-step

1. `i = m - 1` — last real element in `nums1`
2. `j = n - 1` — last element in `nums2`
3. `write = m + n - 1` — last slot of `nums1`
4. While `i >= 0` **and** `j >= 0`:
   - If `nums1[i] > nums2[j]` → place `nums1[i]` at `write`, `i--`
   - Else → place `nums2[j]` at `write`, `j--`
   - Always `write--`
5. If `j >= 0`, copy remaining `nums2` into the front part (nums1 leftovers already sit correctly)

### Visual frames

```text
Start:
nums1: [ 1, 2, 3, _, _, _ ]
        i=2              write=5
nums2: [ 2, 5, 6 ]
              j=2

Compare 3 vs 6 -> write 6
nums1: [ 1, 2, 3, _, _, 6 ]

Compare 3 vs 5 -> write 5
nums1: [ 1, 2, 3, _, 5, 6 ]

Compare 3 vs 2 -> write 3
nums1: [ 1, 2, 3, 3, 5, 6 ]  // index 3 got 3 (write was 3)

... continue until done ...
```

Final: `[1,2,2,3,5,6]`

### Why pointer moves this way

- We always pick the **larger** of the two current values because we fill **from the end** (descending order).
- We move the pointer that supplied the placed value — that value is “used”.
- When `nums1[i]` wins, it was already in `nums1`, so overwriting `write` does not lose unread data **if** `write > i`.

```text
Safe zone:
...  [ unread nums1 ]  |  empty or already-written larger values  |
                      i                         write
     only true while write > i (or we copy nums2 only)
```

---

## 6. The hardest part — why forward merge breaks in-place

### Before (broken forward merge)

```text
nums1: [1, 2, 3, 0, 0, 0]
        ^
        i and write both at 0

Put 1 -> ok
Compare 2 vs 2 -> put 2 at index 1 (ok)
Compare 3 vs 2 -> need to put 2 before 3...
If you write at i=2 with nums2's 2, you still have trouble shifting 3 right.
```

Forward in-place merge needs **shifting** — expensive and messy.

### After (backward merge)

```text
Largest values go to indices 5, 4, 3 ... first
Those slots were empty (zeros) — no unread data destroyed
```

**Before / after table**

| Strategy | Overwrites unread nums1? | Extra space |
|---|---|---|
| Forward in nums1 | Yes — dangerous | — |
| Helper array merge | No | `O(m+n)` |
| **Backward in nums1** | No — uses tail buffer | `O(1)` |

Edge insight: when `m = 0`, all of `nums1` is “empty”; loop only copies `nums2` backward — still works.

---

## 7. Detailed dry run with role diagram

Input: `nums1 = [1,2,3,0,0,0], m=3`, `nums2 = [2,5,6], n=3`

```text
Roles:
  i    = cursor in nums1 real part
  j    = cursor in nums2
  W    = write index
```

| step | nums1[i] | nums2[j] | action | W | nums1 after (conceptual) |
|---:|---:|---:|---|---:|---|
| 1 | 3 | 6 | place 6 | 5 | `[?,?,?, ?,?,6]` |
| 2 | 3 | 5 | place 5 | 4 | `[?,?,?, ?,5,6]` |
| 3 | 3 | 2 | place 3 | 3 | `[?,?,3, ?,5,6]` |
| 4 | 2 | 2 | place 2 (from nums2) | 2 | `[?,2,3, ?,5,6]` |
| 5 | 2 | - | place 2 | 1 | `[2,2,3, ?,5,6]` |
| 6 | 1 | - | place 1 | 0 | `[1,2,2,3,5,6]` |

ASCII roles at step 3:

```text
nums1:  1   2   3   ?   5   6
                i       W
nums2:  2   5   6
            j
```

---

## 8. Why the algorithm is correct

**Invariant:** All positions `> write` already contain the correct largest elements of the final merged array.

**Each step:** We place the largest among `nums1[i]` and `nums2[j]`, which must appear at the end of the not-yet-written suffix. Decrementing the source pointer removes exactly that element from the remaining pool.

**Termination:**

- If `i < 0`, remaining `nums2` values are all smaller than everything written — copy them leftward.
- If `j < 0`, remaining `nums1` values are already in place at the front — no action needed.

Thus `nums1` becomes a sorted merge of the two inputs.

---

## 9. Complexity comparison

| Approach | Time | Extra space | Notes |
|---|---:|---:|---|
| Helper merge | `O(m + n)` | `O(m + n)` | Teaching baseline |
| Sort entire nums1 | `O((m+n) log(m+n))` | `O(1)` | Ignores sorted input |
| **Backward two pointers** | `O(m + n)` | `O(1)` | Interview answer |

---

## 10. Edge cases

| nums1 / m | nums2 / n | Result | What it checks |
|---|---|---|
| `[1,2,3,0,0,0]` / 3 | `[2,5,6]` / 3 | `[1,2,2,3,5,6]` | Standard |
| `[1]` / 1 | `[]` / 0 | `[1]` | Empty nums2 |
| `[0]` / 0 | `[1]` / 1 | `[1]` | Empty nums1 part |
| `[2,0]` / 1 | `[1]` / 1 | `[1,2]` | m=1, n=1 |
| `[4,5,6,0,0,0]` / 3 | `[1,2,3]` / 3 | `[1,2,3,4,5,6]` | All nums2 smaller |
| `[1,2,3,0,0,0]` / 3 | `[4,5,6]` / 3 | `[1,2,3,4,5,6]` | All nums2 larger |

---

## 11. Common mistakes

1. **Starting `write` at `m` instead of `m + n - 1`** — writes into middle too early.
2. **Starting `i` at `m` instead of `m - 1`** — treats placeholder `0` as real data.
3. **Using `>` vs `>=` wrong** — `>=` on `nums2` side is fine for stability; be consistent.
4. **Forgetting to copy leftover nums2** — when `i` finishes first, remaining nums2 must still be written.
5. **Trying to copy leftover nums1** — already in correct positions; extra copy can scramble.
6. **Forward merge without shifting** — overwrites unread values.

---

## 12. How to recognise the pattern

**Clues:**

- Two sorted sequences
- Merge into one
- Extra buffer at the **end** of the first array
- “In-place” with `O(1)` space

**Compared to earlier problems**

| Problem | Pointer direction | Why |
|---|---|---|
| Two Sum II | Ends → inward | Sum steering |
| Trapping Rain Water | Ends → inward | Running max |
| **Merge Sorted Array** | Ends → inward **writing backward** | Free space at tail |

Reusable sentence:

> **If the free buffer is at the end, merge from the end.**

---

## 13. Interview explanation — say this aloud

“The arrays are sorted. I could merge forward into a helper array in `O(m+n)` time and space, then copy back. For `O(1)` extra space, I merge backward: `i` at the last real element of `nums1`, `j` at the end of `nums2`, and `write` at the last index of `nums1`. I repeatedly place the larger of `nums1[i]` and `nums2[j]` at `write` and move pointers backward. This never overwrites unread `nums1` values because I only write into positions that were empty or already filled with larger final values. If `nums2` still has elements when `i` is exhausted, I copy them. Leftover `nums1` elements are already correct. Time `O(m+n)`, space `O(1)`.”

---

## 14. Interview follow-up Q&A

**Q1. Why not merge forward?**  
Forward writes clash with unread `nums1` values unless you shift.

**Q2. Why is time still `O(m+n)`?**  
Each index written once; each pointer moves monotonically.

**Q3. Do we need stable merge?**  
Either tie-break works; explain your choice.

**Q4. What if `m = 0`?**  
Only copy `nums2` from the back forward.

**Q5. Related problems?**  
Merge two linked lists (forward), merge k lists, merge intervals (different pattern).

**Q6. Can we use two pointers from the start with extra space at end?**  
That is still backward filling logic — the write index moves left.

---

## 15. Complete runnable Java class

Save as **`MergeSortedArray.java`** (lives in `java/`).

```java


import java.util.Arrays;

public class MergeSortedArray {

  public static void mergeBruteForce(int[] nums1, int m, int[] nums2, int n) {
    int[] merged = new int[m + n];
    int i = 0, j = 0, k = 0;
    while (i < m && j < n) {
      if (nums1[i] <= nums2[j]) merged[k++] = nums1[i++];
      else merged[k++] = nums2[j++];
    }
    while (i < m) merged[k++] = nums1[i++];
    while (j < n) merged[k++] = nums2[j++];
    for (int t = 0; t < m + n; t++) nums1[t] = merged[t];
  }

  public static void mergeTwoPointersFromEnd(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1;
    int j = n - 1;
    int write = m + n - 1;
    while (i >= 0 && j >= 0) {
      if (nums1[i] > nums2[j]) {
        nums1[write--] = nums1[i--];
      } else {
        nums1[write--] = nums2[j--];
      }
    }
    while (j >= 0) nums1[write--] = nums2[j--];
  }

  public static void main(String[] args) {
    int[] nums1 = {1, 2, 3, 0, 0, 0};
    int m = 3;
    int[] nums2 = {2, 5, 6};
    int n = 3;
    System.out.println("Before: " + Arrays.toString(nums1));
    mergeTwoPointersFromEnd(nums1, m, nums2, n);
    System.out.println("After:  " + Arrays.toString(nums1));
  }
}
```

---

## 16. Independent IDE practice

- [ ] Implement helper-array merge
- [ ] Implement backward merge
- [ ] Trace `m=0, n=1` by hand
- [ ] Trace main example with `i`, `j`, `write`
- [ ] Explain why leftover nums1 needs no copy
- [ ] Run `../MergeSortedArray.java` main

### Small challenge

Predict the result without code:

```text
nums1 = [4,5,6,0,0,0], m = 3
nums2 = [1,2,3],       n = 3
```

Then predict:

```text
nums1 = [1], m = 1
nums2 = [2], n = 1  (note: nums1 length is 2 → [1,0])
```

---

## 17. Quick revision

1. Buffer is at the **tail** of `nums1`.
2. `i = m-1`, `j = n-1`, `write = m+n-1`.
3. Place the **larger** value at `write`, move `write` left.
4. Copy remaining `nums2` only.
5. `O(m+n)` time, `O(1)` space.

---

## 18. Pattern used in this problem + next problem

**Backward two pointers / in-place merge from the end.**

> **Merge Sorted Array = merge from the back into free tail space.**

**Next:** **Problem 10 — Squares of a Sorted Array** (compare absolute values at both ends; fill answer from the end).