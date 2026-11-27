# Problem 01 — Kth Largest Element in an Array

> **Pattern:** Min-heap size k
> **Goal:** Return k-th largest element.
> **LeetCode:** 215

---

## 1. Problem statement

Not necessarily distinct; sort position from largest.

### Example 1

```text
nums=[3,2,1,5,6,4], k=2 → 5
```

### Example 2

```text
k=1 → 6
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Keep k largest in min-heap; root is kth.
```

**Pattern A — Min-heap of size K** from `Heap_Common_Patterns.md`

---

## 2b. What you are really being tested on

Can you recognise the stack/structure pattern, state invariants clearly, implement without index bugs, and justify time/space?

---

## 3. How to start thinking (simple questions)

1. What structure matches the constraint?
2. What is brute time complexity?
3. Can we process each item once?

---

## 4. Approach 1 — Brute force

### Idea

Sort descending pick index k-1.

### Java method

`findKthLargestSort` in `KthLargestElement.java`

### Dry run (summary)

```text
sorted [6,5,4,...] index 1 = 5
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n log n)` |
| **Space** | `O(n)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Poll when size>k.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Java method

`findKthLargest` in `KthLargestElement.java`

```java
public static int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int x : nums) {
        pq.offer(x);
        if (pq.size() > k) pq.poll();
    }
    return pq.peek();
}
```

### Visual walkthrough

```text
Keep k largest in min-heap; root is kth.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Use max-heap of size n-k+1 alternatively.

---

## 7. Detailed dry run

```text
heap holds {5,6} peek 5 for k=2
```

---

## 7c. Paper-trace template

| Step | State / stack / pointers | Action |
|---:|---|---|
| 0 | Initial | Read input size |
| 1 | … | First push / move |
| 2 | … | Update answer |
| n | Done | Return result |

Fill this table while running the `main()` example from the Java file.

---

## 8. Why the algorithm is correct

Min root is smallest among kept k → kth largest.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n log n)` | `O(n)` |
| Optimal | `O(n log k)` | `O(k)` |

---

## 10. Edge cases

| Case | Behavior |
|---|---|
| Minimum size input | Check carefully |
| Empty / null | Per Java demos |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| Off-by-one in indices | Wrong distances |
| Forgetting sentinel flush | Miss max area |

---

## 11b. Debug checklist (when answer is wrong)

1. Re-run the smallest example by hand.
2. Confirm method names match `../KthLargestElement.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Default PQ is min-heap in Java.

### Thirty-second version

> Size-k min-heap; peek.

### Two-minute version

Brute is `findKthLargestSort()` at `O(n log n)`. I use `findKthLargest()` at `O(n log k)` because Min root is smallest among kept k → kth largest.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n log k)`?**  Min root is smallest among kept k → kth largest.

**Why space `O(k)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n log k)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../KthLargestElement.java` (`package DSA.Heap`).

```bash
cd Heap
javac KthLargestElement.java
java -cp . DSA.Heap.KthLargestElement
```

---

## 14b. Re-code drill (no IDE)

1. Close the notes.
2. Write method signature from memory.
3. Run one example on paper.
4. Open Java only to diff imports and edge-case guards.
5. Explain aloud using section 12 script.

---

## 15. Practice checklist

- [ ] Implement `findKthLargestSort()` without hints.
- [ ] Implement `findKthLargest()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — Min-heap of size K.

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

## 15. Link to Java — which method is which

| Approach | Method in `KthLargestElement.java` |
|---|---|
| Brute — full sort | `findKthLargestSort` |
| Optimal — size-k min-heap | `findKthLargest` |

Run `main` to compare sort vs heap on sample arrays.

---

## 16. Quick revision

Pattern: **Pattern A — Min-heap of size K**.
Brute: `O(n log n)` — `findKthLargestSort()`.
Optimal: `O(n log k)` — `findKthLargest()`.
Say the invariant before coding.

One line:

> **Size-k min-heap; peek.**

---

## 17. Pattern used

**Pattern A — Min-heap of size K**

---

## 18. Next problem

**Problem 02 — Top K Frequent Elements**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../KthLargestElement.java`.

```java
package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: Kth Largest Element in an Array (LeetCode 215)

Note: "kth largest" is not the same as sorting in ascending order and picking index k.

Example:
    nums = [3, 2, 1, 5, 6, 4], k = 2 -> 5
*/

public class KthLargestElement {

    // Min-heap of size k — root is the kth largest among kept elements.
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int value : nums) {
            minHeap.offer(value);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        return minHeap.peek();
    }

    public static int findKthLargestSort(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    public static void main(String[] args) {
        int[] nums = { 3, 2, 1, 5, 6, 4 };
        System.out.println("k=2 (heap):  " + findKthLargest(nums.clone(), 2)); // 5
        System.out.println("k=2 (sort):  " + findKthLargestSort(nums.clone(), 2)); // 5

        int[] nums2 = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
        System.out.println("k=4 (heap):  " + findKthLargest(nums2, 4)); // 4
    }
}
```
