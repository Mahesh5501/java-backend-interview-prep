# Problem 06 — Find Minimum Rotated

> **Pattern:** Rotation pivot  
> **Java file:** `../FindMinimumRotatedArray.java`  
> **Goal:** Understand every approach — what it does, why we pick it, and which Java method is which.

---

## 1. Problem statement

Sorted asc, then rotated (distinct). Return minimum value.

### Example

```text
[3,4,5,1,2] → 1
```

### Constraints (typical)

- `n` up to `10^5` → prefer **O(log n)** when the array is sorted or the answer space is monotone.
- Clarify return type: index, value, or count.

---

## 2. Mental picture

Minimum is at rotation pivot where smaller segment begins.

Re-open **`notes/Binary_Search_Common_Patterns.md`** and name the pattern that matches this problem.

---

## 3. How to start thinking

1. State brute force honestly (usually linear scan or linear search on answer).
2. Ask: can I **discard half** of candidates each step? → binary search.
3. For biased BS: on equal `target`, which direction keeps searching?
4. For answer-space BS: write a monotone `feasible(x)` first, then search `x`.

---

## 4. Approach 1 — Brute force

### Idea

Track running minimum in one pass.

### Why mention it?

Correct baseline before you exploit sorted structure or monotonic feasibility.

### Java method

`findMinLinear` in `FindMinimumRotatedArray.java`

### Complexity

| Time | Extra space |
|---|---|
| `O(n)` | `O(1)` |

---

## 5. Approach 2 — Optimal

### Idea

Compare `nums[mid]` vs `nums[right]`; shrink to smaller side.

### Why this approach?

Fits the **Find pivot without full rotation recovery.** pattern — logarithmic search instead of rescanning.

### Java method

`findMin` in `FindMinimumRotatedArray.java`

### Complexity

| Time | Extra space |
|---|---|
| `O(log n)` | `O(1)` |

---


## 6. Hardest part (slow down here)

For **Find Minimum in Rotated Sorted Array**, candidates usually slip on **boundary conditions** and **off-by-one** updates.

- Re-read the optimal method comments in Java.
- Compare your mental model to the pattern section in `Binary_Search_Common_Patterns.md`.
- If you use a map, ask: what is the **key** and what does the **value** mean?

---

## 7. Detailed dry run

`nums = [3, 4, 5, 1, 2]` — loop uses `left < right`, compare `nums[mid]` vs `nums[right]`

| step | left | right | mid | nums[mid] | nums[right] | action |
|---:|---:|---:|---:|---:|---:|---|
| 1 | 0 | 4 | 2 | 5 | 2 | 5 > 2 → min in right → left = 3 |
| 2 | 3 | 4 | 3 | 1 | 2 | 1 ≤ 2 → right = **3** (not mid−1) |
| end | 3 | 3 | — | — | — | return nums[3] = **1** |

If you used `right = mid - 1` on step 2, you could skip the true minimum at index 3.

---

## 8. Why the optimal approach is correct

Give a **one-paragraph proof sketch**:

- Hashing: “When we are at index `i`, the structure contains exactly the information we need from indices `< i`.”
- Prefix map: “Each subarray sum equals difference of two prefix sums; we count valid earlier prefixes.”
- Binary search: “The answer always lies in `[left, right]`; each step discards a half that cannot contain it.”

---

## 9. Complexity comparison

| Approach | Java method | Time | Extra space | Interview? |
|---|---|---|---|---|
| Brute force | `findMinLinear` | `O(n)` | `O(1)` | Start verbally |
| Optimal | `findMin` | `O(log n)` | `O(1)` | **Final answer** |


---

## 10. Edge cases

Build a tiny table before you submit:

| Case | Expected behavior |
|---|---|
| Minimum length input | Single element / pair |
| All equal values | Duplicates / freq / first-last BS |
| Negatives (if allowed) | Prefix map still works; sliding sum may not |
| Empty / null | Return `0`, `false`, or `-1` per spec |

---

## 11. Common mistakes

- Using the wrong loop condition (`left < right` vs `left <= right`).
- Forgetting `prefixCount.put(0, 1)` on subarray-sum problems.
- Starting a consecutive walk from **every** element (re-sums chains).
- Returning immediately on first binary-search hit when the problem asks for **first/last** occurrence.

---

## 12. Interview — say this aloud

> “I'll start with brute force to confirm the definition, then optimize. For this problem the right tool is **mid vs right**. I'll maintain [map/set/prefix/left-right] so each step is one pass / logarithmic. Let me trace a small example, then I'll code the optimal solution.”

Then code the **optimal** method from memory, not brute force.

---

## 13. Follow-ups

- Ask yourself: “What if the array were sorted?” “What if we need indices not values?” “What if `k` changes?”
- Link to the **next problem** in this folder’s roadmap in `Binary_Search_Common_Patterns.md`.

---

## 14. Complete runnable Java

Package: `DSA.BinarySearch`  
File: `../FindMinimumRotatedArray.java`

```java
package DSA.BinarySearch;

/*
Problem: Find Minimum in Rotated Sorted Array

Sorted ascending, then rotated. All elements unique.
Return the minimum element.

Examples:
  [3,4,5,1,2] → 1
  [11,13,15,17] (not rotated) → 11

Approach: Compare nums[mid] with nums[right]
Time: O(log n)   Space: O(1)
*/

public class FindMinimumRotatedArray {

    public static int findMinLinear(int[] nums) {
        int min = nums[0];
        for (int x : nums) {
            min = Math.min(min, x);
        }
        return min;
    }

    public static int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {
        System.out.println(findMin(new int[] { 3, 4, 5, 1, 2 }));
        System.out.println(findMin(new int[] { 4, 5, 6, 7, 0, 1, 2 }));
        System.out.println(findMin(new int[] { 11, 13, 15, 17 }));
        System.out.println(findMin(new int[] { 5, 1, 2, 3, 4 }));
    }
}
```

Run `main` after implementing from memory.

---

## 15. Practice checklist

- [ ] Implement brute force without looking.
- [ ] Implement optimal without looking.
- [ ] Trace one example on paper.
- [ ] State time and space complexity.
- [ ] List three edge cases from section 10.

---

---

## Depth addendum — extended workbook

### Mental picture (second angle)

Sketch input on paper and mark **what changes each loop iteration**. If the pattern uses a stack, queue, heap, or map, write its contents as a column beside the array/tree.

### Brute force reminder

State the naive approach in one sentence, its time/space, and **one sentence why** the optimal pattern beats it.

### Hardest-part drill

| Before coding | After understanding |
|---|---|
| Unsure what moves | Name the invariant pointers/stack maintain |
| Edge case worry | Check empty, single, duplicates |

### Extended dry-run columns

| iter | key variables | container snapshot | output so far |
|---:|---|---|---|

Fill five rows from the matching Java main example in the sibling .java file.

### Interview say-aloud (30s)

> I recognize the pattern, use the right structure for the invariant, avoid brute time because each element is handled once.

### Follow-up Q&A

**Q: In-place?** Only if allowed.  
**Q: Unsorted input?** State preprocessing.

## 16. Quick revision

One line: **Find Minimum in Rotated Sorted Array = mid vs right + optimal method in `FindMinimumRotatedArray.java`.**

---

## 17. Pattern used

See **`Binary_Search_Common_Patterns.md`**.

---

## 18. Next problem

**Problem 07 — Search Insert Position**

---

---

## Repository Java (full)

Align with `../FindMinimumRotatedArray.java`.

```java
package DSA.BinarySearch;

/*
Problem: Find Minimum in Rotated Sorted Array

Sorted ascending, then rotated. All elements unique.
Return the minimum element.

Examples:
  [3,4,5,1,2] → 1
  [11,13,15,17] (not rotated) → 11

Approach: Compare nums[mid] with nums[right]
Time: O(log n)   Space: O(1)
*/

public class FindMinimumRotatedArray {

    public static int findMinLinear(int[] nums) {
        int min = nums[0];
        for (int x : nums) {
            min = Math.min(min, x);
        }
        return min;
    }

    public static int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {
        System.out.println(findMin(new int[] { 3, 4, 5, 1, 2 }));
        System.out.println(findMin(new int[] { 4, 5, 6, 7, 0, 1, 2 }));
        System.out.println(findMin(new int[] { 11, 13, 15, 17 }));
        System.out.println(findMin(new int[] { 5, 1, 2, 3, 4 }));
    }
}
```
