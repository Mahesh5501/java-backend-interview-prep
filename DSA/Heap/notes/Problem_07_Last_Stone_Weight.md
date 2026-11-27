# Problem 07 — Last Stone Weight

> **Pattern:** Max-heap smash
> **Goal:** Repeat smash two largest stones.
> **LeetCode:** 1046

---

## 1. Problem statement

If equal both break; else push difference.

### Example 1

```text
stones=[2,7,4,1,8,1] → 1
```

### Example 2

```text
one stone left → that weight
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Max-heap always pop two largest.
```

**Pattern E — Greedy heap** from `Heap_Common_Patterns.md`

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

Each smash is heap operations.

### Key method

`lastStoneWeight()` in `../LastStoneWeight.java`

### Dry run (summary)

```text
8 vs 7 → push 1
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

Custom max PQ.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`lastStoneWeight()` in `../LastStoneWeight.java`

```java
public static int lastStoneWeight(int[] stones) {
    PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
    for (int s : stones) pq.offer(s);
    while (pq.size() > 1) {
        int y = pq.poll(), x = pq.poll();
        if (y > x) pq.offer(y - x);
    }
    return pq.isEmpty() ? 0 : pq.poll();
}
```

### Visual walkthrough

```text
Max-heap always pop two largest.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Single stone returns it.

---

## 7. Detailed dry run

```text
y>=x after two pops
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

Simulates problem rules.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n log n)` | `O(n)` |
| Optimal | `O(n log n)` | `O(n)` |

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
2. Confirm method names match `../LastStoneWeight.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Max-heap smash loop.

### Thirty-second version

> Pop two max; push diff.

### Two-minute version

Brute is `lastStoneWeight()` at `O(n log n)`. I use `lastStoneWeight()` at `O(n log n)` because Simulates problem rules.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n log n)`?**  Simulates problem rules.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n log n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../LastStoneWeight.java` (`package DSA.Heap`).

```bash
cd Heap
javac LastStoneWeight.java
java -cp . DSA.Heap.LastStoneWeight
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

- [ ] Implement `lastStoneWeight()` without hints.
- [ ] Implement `lastStoneWeight()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern E — Greedy heap.

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

Pattern: **Pattern E — Greedy heap**.
Brute: `O(n log n)` — `lastStoneWeight()`.
Optimal: `O(n log n)` — `lastStoneWeight()`.
Say the invariant before coding.

One line:

> **Pop two max; push diff.**

---

## 17. Pattern used

**Pattern E — Greedy heap**

---

## 18. Next problem

**Problem 08 — Reorganize String**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../LastStoneWeight.java`.

```java
package DSA.Heap;

import java.util.PriorityQueue;

/*
Problem: Last Stone Weight (LeetCode 1046)

Each turn: take two heaviest stones; if equal they smash to nothing,
else the difference remains. Return weight of the last stone, or 0.

Example:
    [2, 7, 4, 1, 8, 1] -> 1
*/

public class LastStoneWeight {

    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        for (int stone : stones) {
            maxHeap.offer(stone);
        }

        while (maxHeap.size() > 1) {
            int first = maxHeap.poll();
            int second = maxHeap.poll();
            if (first != second) {
                maxHeap.offer(first - second);
            }
        }

        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    public static void main(String[] args) {
        int[] stones = { 2, 7, 4, 1, 8, 1 };
        System.out.println("Last stone weight: " + lastStoneWeight(stones)); // 1

        int[] stones2 = { 1 };
        System.out.println("Single stone: " + lastStoneWeight(stones2)); // 1

        int[] stones3 = { 2, 2 };
        System.out.println("Both equal: " + lastStoneWeight(stones3)); // 0
    }
}
```
