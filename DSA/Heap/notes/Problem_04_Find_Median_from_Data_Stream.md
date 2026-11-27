# Problem 04 — Find Median from Data Stream

> **Pattern:** Two heaps balancing halves
> **Goal:** Median after each addNum.
> **LeetCode:** 295

---

## 1. Problem statement

Maintain lower half max-heap + upper half min-heap.

### Example 1

```text
add 1,2 → median 1.5
```

### Example 2

```text
add 3 → median 2
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
lowHalf size ≥ highHalf; sizes differ by at most 1.
```

**Pattern C — Dual heap** from `Heap_Common_Patterns.md`

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

Two heaps rebalance after each insert.

### Key method

`addNum()` in `../FindMedianFromDataStream.java`

### Dry run (summary)

```text
1 in low, 2 in high → avg
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(log n)` |
| **Space** | `O(n)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Poll/move if skewed.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`findMedian()` in `../FindMedianFromDataStream.java`

```java
public void addNum(int num) {
    if (lowHalf.isEmpty() || num <= lowHalf.peek()) lowHalf.offer(num);
    else highHalf.offer(num);
    if (lowHalf.size() > highHalf.size() + 1) highHalf.offer(lowHalf.poll());
    else if (highHalf.size() > lowHalf.size()) lowHalf.offer(highHalf.poll());
}
public double findMedian() {
    if (lowHalf.size() > highHalf.size()) return lowHalf.peek();
    return (lowHalf.peek() + highHalf.peek()) / 2.0;
}
```

### Visual walkthrough

```text
lowHalf size ≥ highHalf; sizes differ by at most 1.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

low max-heap, high min-heap.

---

## 7. Detailed dry run

```text
rebalance after skew >1
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

Median from tops.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(log n)` | `O(n)` |
| Optimal | `O(1) query` | `O(n)` |

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
2. Confirm method names match `../FindMedianFromDataStream.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Dual heap stream median.

### Thirty-second version

> Balance two heaps.

### Two-minute version

Brute is `addNum()` at `O(log n)`. I use `findMedian()` at `O(1) query` because Median from tops.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(1) query`?**  Median from tops.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(1) query`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../FindMedianFromDataStream.java` (`package DSA.Heap`).

```bash
cd Heap
javac FindMedianFromDataStream.java
java -cp . DSA.Heap.FindMedianFromDataStream
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

- [ ] Implement `addNum()` without hints.
- [ ] Implement `findMedian()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern C — Dual heap.

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

Pattern: **Pattern C — Dual heap**.
Brute: `O(log n)` — `addNum()`.
Optimal: `O(1) query` — `findMedian()`.
Say the invariant before coding.

One line:

> **Balance two heaps.**

---

## 17. Pattern used

**Pattern C — Dual heap**

---

## 18. Next problem

**Problem 05 — K Closest Points to Origin**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../FindMedianFromDataStream.java`.

```java
package DSA.Heap;

import java.util.PriorityQueue;

/*
Problem: Find Median from Data Stream (LeetCode 295)

Support addNum(int) and findMedian() on a growing stream.

Example:
    add 1, add 2 -> median 1.5
    add 3 -> median 2
*/

public class FindMedianFromDataStream {

    /** Lower half — max-heap (largest of the lows on top). */
    private final PriorityQueue<Integer> lowHalf;

    /** Upper half — min-heap (smallest of the highs on top). */
    private final PriorityQueue<Integer> highHalf;

    public FindMedianFromDataStream() {
        lowHalf = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        highHalf = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (lowHalf.isEmpty() || num <= lowHalf.peek()) {
            lowHalf.offer(num);
        } else {
            highHalf.offer(num);
        }

        if (lowHalf.size() > highHalf.size() + 1) {
            highHalf.offer(lowHalf.poll());
        } else if (highHalf.size() > lowHalf.size()) {
            lowHalf.offer(highHalf.poll());
        }
    }

    public double findMedian() {
        if (lowHalf.size() > highHalf.size()) {
            return lowHalf.peek();
        }
        return (lowHalf.peek() + highHalf.peek()) / 2.0;
    }

    public static void main(String[] args) {
        FindMedianFromDataStream medianFinder = new FindMedianFromDataStream();

        medianFinder.addNum(1);
        medianFinder.addNum(2);
        System.out.println("Median after 1, 2: " + medianFinder.findMedian()); // 1.5

        medianFinder.addNum(3);
        System.out.println("Median after 1, 2, 3: " + medianFinder.findMedian()); // 2.0

        medianFinder.addNum(4);
        medianFinder.addNum(5);
        System.out.println("Median after 1..5: " + medianFinder.findMedian()); // 3.0
    }
}
```
