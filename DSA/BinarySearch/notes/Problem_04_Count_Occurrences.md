# Problem 04 — Count Occurrences

> **Pattern:** First + last  
> **Goal:** Interview-ready: mental model, brute force, optimal approach, dry run, complexity, edge cases, and what to say aloud.

---

## 1. Problem statement

Count occurrences of `target`.

Work through the examples in the matching `.java` file `main` method and on LeetCode (if applicable).

### Constraints (typical)

- Input sizes up to `10^5` (sometimes `10^4`) — aim for **O(n)** or **O(n log n)** unless constraints are tiny.
- Read whether the problem wants **indices**, **values**, or **counts**.

---

## 2. Mental picture

Before coding, draw the array and ask: **what do I need to remember from earlier positions?**

- If you need “the other value that completes a pair” → **hash map / set**.
- If you need “sum of a contiguous block” with negatives allowed → **prefix sum + frequency map**, not sliding window by default.
- If the array is **sorted** and you discard half the range → **binary search**, not hashing.

Re-open **`notes/Binary_Search_Common_Patterns.md`** and name the pattern letter (A–H for hashing, A–G for binary search) that matches this problem.

---

## 3. How to start thinking

1. State brute force complexity honestly (often `O(n²)`).
2. Identify the **bottleneck** (re-scanning, re-multiplying, re-walking chains).
3. Choose the structure: `Map`, `Set`, prefix map, or binary search bounds.
4. Write the **invariant** in one sentence before coding.

---

## 4. Approach 1 — Brute force

Implement the definition directly: nested loops, full rescans, or linear search on sorted data.

**Why mention it in interviews:** It proves you understand the problem and gives a correct baseline before optimizing.

**Typical complexity:** `O(n²)` or `O(n)` on sorted data — too slow when `n` is large.

---

## 5. Approach 2 — Optimal

Open the optimal method in the Java file below. While reading:

1. Name each variable (`prefix`, `seen`, `left`, `right`, …).
2. Say why each update is safe (no missed answers).
3. Trace one small example on paper (see section 7).

**Target complexity:** See section 9 — usually **O(n)** or **O(log n)** with **O(n)** or **O(1)** extra space.

---

## 6. Hardest part (slow down here)

For **Count Occurrences**, candidates usually slip on **boundary conditions** and **off-by-one** updates.

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

| Method | Time | Extra space |
|---|---:|---:|
| Brute | See your brute implementation | Usually `O(1)` |
| Optimal | Per pattern cheat sheet in `Binary_Search_Common_Patterns.md` | Usually `O(n)` for hash structures |

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

> “I'll start with brute force to confirm the definition, then optimize. For this problem the right tool is **first + last**. I'll maintain [map/set/prefix/left-right] so each step is one pass / logarithmic. Let me trace a small example, then I'll code the optimal solution.”

Then code the **optimal** method from memory, not brute force.

---

## 13. Follow-ups

- Ask yourself: “What if the array were sorted?” “What if we need indices not values?” “What if `k` changes?”
- Link to the **next problem** in this folder’s roadmap in `Binary_Search_Common_Patterns.md`.

---

## 14. Complete runnable Java

Package: `DSA.BinarySearch`  
File: `../CountOccurrences.java`

```java
package DSA.BinarySearch;

/*
Problem: Count Occurrences of Target

Return how many times target appears in sorted nums.

Example:
  [1,2,2,2,3,4], target=2 → 3

Approach: first + last index, count = last - first + 1
Time: O(log n)   Space: O(1)
*/

public class CountOccurrences {

    public static int countOccurrencesBrute(int[] nums, int target) {
        int count = 0;
        for (int x : nums) {
            if (x == target) {
                count++;
            }
        }
        return count;
    }

    public static int countOccurrences(int[] nums, int target) {
        int first = FirstOccurrence.searchFirst(nums, target);
        if (first == -1) {
            return 0;
        }
        int last = LastOccurrence.searchLast(nums, target);
        return last - first + 1;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 2, 2, 3, 4 };
        System.out.println("Brute: " + countOccurrencesBrute(nums, 2));
        System.out.println("BS:    " + countOccurrences(nums, 2));
        System.out.println("Zero:  " + countOccurrences(nums, 6));
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

One line: **Count Occurrences = First + last + optimal method in `CountOccurrences.java`.**

---

## 17. Pattern used

See **`Binary_Search_Common_Patterns.md`**.

---

## 18. Next problem

**Problem 05 — Search in Rotated Sorted Array**

---

---

## Repository Java (full)

Align with `../CountOccurrences.java`.

```java
package DSA.BinarySearch;

/*
Problem: Count Occurrences of Target

Return how many times target appears in sorted nums.

Example:
  [1,2,2,2,3,4], target=2 → 3

Approach: first + last index, count = last - first + 1
Time: O(log n)   Space: O(1)
*/

public class CountOccurrences {

    public static int countOccurrencesBrute(int[] nums, int target) {
        int count = 0;
        for (int x : nums) {
            if (x == target) {
                count++;
            }
        }
        return count;
    }

    public static int countOccurrences(int[] nums, int target) {
        int first = FirstOccurrence.searchFirst(nums, target);
        if (first == -1) {
            return 0;
        }
        int last = LastOccurrence.searchLast(nums, target);
        return last - first + 1;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 2, 2, 3, 4 };
        System.out.println("Brute: " + countOccurrencesBrute(nums, 2));
        System.out.println("BS:    " + countOccurrences(nums, 2));
        System.out.println("Zero:  " + countOccurrences(nums, 6));
    }
}
```
