# Problem 01 — Classic Binary Search

> **Pattern:** Classic BS  
> **Java file:** `../BinarySearch.java`  
> **Goal:** Understand every approach — what it does, why we pick it, and which Java method is which.

---

## 1. Problem statement

Find `target` in sorted ascending `nums`. Return index or `-1`.

### Example

```text
[1,3,5,7,9], target=7 → 3
```

### Constraints (typical)

- `n` up to `10^5` → prefer **O(log n)** when the array is sorted or the answer space is monotone.
- Clarify return type: index, value, or count.

---

## 2. Mental picture

Halve the index range: if `nums[mid]` is too small, answer is in the right half.

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

Linear scan every index.

### Why mention it?

Correct baseline before you exploit sorted structure or monotonic feasibility.

### Java method

`searchLinear` in `BinarySearch.java`

### Complexity

| Time | Extra space |
|---|---|
| `O(n)` | `O(1)` |

---

## 5. Approach 2 — Optimal

### Idea

Classic BS with `left <= right`; discard half each step.

### Why this approach?

Fits the **Classic BS on sorted array.** pattern — logarithmic search instead of rescanning.

### Java method

`search` in `BinarySearch.java`

### Complexity

| Time | Extra space |
|---|---|
| `O(log n)` | `O(1)` |

---


## 6. Hardest part (slow down here)

For **Classic Binary Search**, candidates usually slip on **boundary conditions** and **off-by-one** updates.

- Re-read the optimal method comments in Java.
- Compare your mental model to the pattern section in `Binary_Search_Common_Patterns.md`.
- If you use a map, ask: what is the **key** and what does the **value** mean?

---

## 7. Detailed dry run

Pick the **smallest interesting example** from `main` in the Java file.

| Step | What to write on paper |
|---|---|
| Initialization | Empty map/set, `left/right`, or `prefix=0` + `{0:1}` if prefix problem |
| Each index | Show map/set after the step; or show which half of the array remains in BS |
| Answer | Final return value |

Do this once with the video off. Interviewers care that you can trace without running the IDE.

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
| Brute force | `searchLinear` | `O(n)` | `O(1)` | Start verbally |
| Optimal | `search` | `O(log n)` | `O(1)` | **Final answer** |


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

> “I'll start with brute force to confirm the definition, then optimize. For this problem the right tool is **classic bs**. I'll maintain [map/set/prefix/left-right] so each step is one pass / logarithmic. Let me trace a small example, then I'll code the optimal solution.”

Then code the **optimal** method from memory, not brute force.

---

## 13. Follow-ups

- Ask yourself: “What if the array were sorted?” “What if we need indices not values?” “What if `k` changes?”
- Link to the **next problem** in this folder’s roadmap in `Binary_Search_Common_Patterns.md`.

---

## 14. Complete runnable Java

Package: `DSA.BinarySearch`  
File: `../BinarySearch.java`

```java
package DSA.BinarySearch;

/*
Problem: Classic Binary Search

Given sorted array nums and target, return index of target or -1.

Examples:
  [1,3,5,7,9], target=7 → 3
  [1,3,5,7,9], target=4 → -1

Time: O(log n)   Space: O(1)
*/

public class BinarySearch {

    // BRUTE: linear scan
    public static int searchLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // OPTIMAL: binary search on sorted array
    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 3, 5, 7, 9 };
        System.out.println("Linear:  " + searchLinear(nums, 7));
        System.out.println("Binary:  " + search(nums, 7));
        System.out.println("Missing: " + search(nums, 4));
        System.out.println("First:   " + search(nums, 1));
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

One line: **Classic Binary Search = Classic BS + optimal method in `BinarySearch.java`.**

---

## 17. Pattern used

See **`Binary_Search_Common_Patterns.md`**.

---

## 18. Next problem

**Problem 02 — First Occurrence**

---

---

## Repository Java (full)

Align with `../BinarySearch.java`.

```java
package DSA.BinarySearch;

/*
Problem: Classic Binary Search

Given sorted array nums and target, return index of target or -1.

Examples:
  [1,3,5,7,9], target=7 → 3
  [1,3,5,7,9], target=4 → -1

Time: O(log n)   Space: O(1)
*/

public class BinarySearch {

    // BRUTE: linear scan
    public static int searchLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // OPTIMAL: binary search on sorted array
    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 3, 5, 7, 9 };
        System.out.println("Linear:  " + searchLinear(nums, 7));
        System.out.println("Binary:  " + search(nums, 7));
        System.out.println("Missing: " + search(nums, 4));
        System.out.println("First:   " + search(nums, 1));
    }
}
```
