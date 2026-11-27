# Problem 05 — K Closest Points to Origin

> **Pattern:** Max-heap size k by distance
> **Goal:** Return k points with smallest distance².
> **LeetCode:** 973

---

## 1. Problem statement

Use squared distance to avoid sqrt.

### Example 1

```text
points=[[1,3],[-2,2]], k=1 → [[-2,2]]
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Keep k closest; max-heap evicts farthest among k.
```

**Pattern A — Top-K** from `Heap_Common_Patterns.md`

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

Max-heap of size k on distance.

### Key method

`kClosest()` in `../KClosestPoints.java`

### Dry run (summary)

```text
furthest of k popped when new closer
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n log k)` |
| **Space** | `O(k)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Comparator on x*x+y*y.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`kClosest()` in `../KClosestPoints.java`

```java
public static int[][] kClosest(int[][] points, int k) {
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->dist(b)-dist(a));
    for (int[] p : points) {
        pq.offer(p);
        if (pq.size() > k) pq.poll();
    }
    return pq.toArray(new int[k][]);
}
```

### Visual walkthrough

```text
Keep k closest; max-heap evicts farthest among k.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

k may equal n.

---

## 7. Detailed dry run

```text
compare dist squared
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

Max heap among k smallest.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n log k)` | `O(k)` |
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
2. Confirm method names match `../KClosestPoints.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Quickselect O(n) follow-up.

### Thirty-second version

> Max-heap size k on dist².

### Two-minute version

Brute is `kClosest()` at `O(n log k)`. I use `kClosest()` at `O(n log k)` because Max heap among k smallest.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n log k)`?**  Max heap among k smallest.

**Why space `O(k)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n log k)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../KClosestPoints.java` (`package DSA.Heap`).

```bash
cd Heap
javac KClosestPoints.java
java -cp . DSA.Heap.KClosestPoints
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

- [ ] Implement `kClosest()` without hints.
- [ ] Implement `kClosest()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — Top-K.

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

Pattern: **Pattern A — Top-K**.
Brute: `O(n log k)` — `kClosest()`.
Optimal: `O(n log k)` — `kClosest()`.
Say the invariant before coding.

One line:

> **Max-heap size k on dist².**

---

## 17. Pattern used

**Pattern A — Top-K**

---

## 18. Next problem

**Problem 06 — Task Scheduler**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../KClosestPoints.java`.

```java
package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: K Closest Points to Origin (LeetCode 973)

Each point is [x, y]. Return the k points with smallest distance squared to (0, 0).

Example:
    points = [[1,1],[3,-2],[-2,2]], k = 2 -> two closest among them
*/

public class KClosestPoints {

    public static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(distanceSquared(b), distanceSquared(a)));

        for (int[] point : points) {
            maxHeap.offer(point);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        int[][] result = new int[maxHeap.size()][2];
        int index = result.length - 1;
        while (!maxHeap.isEmpty()) {
            result[index--] = maxHeap.poll();
        }
        return result;
    }

    private static int distanceSquared(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    public static void main(String[] args) {
        int[][] points = { { 1, 1 }, { 3, -2 }, { -2, 2 } };
        int[][] closest = kClosest(points, 2);
        System.out.println("Two closest: " + Arrays.deepToString(closest));

        int[][] points2 = { { 3, 3 }, { 5, -1 }, { -2, 4 } };
        System.out.println("k=1: " + Arrays.deepToString(kClosest(points2, 1)));
    }
}
```
