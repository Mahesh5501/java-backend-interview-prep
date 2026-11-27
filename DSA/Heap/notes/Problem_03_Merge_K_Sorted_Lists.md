# Problem 03 — Merge K Sorted Lists

> **Pattern:** Min-heap of list heads
> **Goal:** Merge k sorted linked lists.
> **LeetCode:** 23

---

## 1. Problem statement

Return one sorted list.

### Example 1

```text
lists=[[1,4,5],[1,3,4],[2,6]] → 1→1→2→3→4→4→5→6
```

### Example 2

```text
empty lists
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Push non-null heads; pop min, advance that list.
```

**Pattern B — K-way merge** from `Heap_Common_Patterns.md`

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

Divide-and-conquer merge also O(N log k).

### Key method

`mergeKLists()` in `../MergeKSortedLists.java`

### Dry run (summary)

```text
always pick smallest among k heads
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(N log k)` |
| **Space** | `O(k)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Entry stores node with val compare.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`mergeKLists()` in `../MergeKSortedLists.java`

```java
public static ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
    for (ListNode node : lists) if (node != null) pq.offer(node);
    ListNode dummy = new ListNode(0), tail = dummy;
    while (!pq.isEmpty()) {
        ListNode node = pq.poll();
        tail.next = node;
        tail = tail.next;
        if (node.next != null) pq.offer(node.next);
    }
    return dummy.next;
}
```

### Visual walkthrough

```text
Push non-null heads; pop min, advance that list.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

N total nodes across lists.

---

## 7. Detailed dry run

```text
poll 1 from list1, offer its next
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

Each node heap ops log k.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(N log k)` | `O(k)` |
| Optimal | `O(N log k)` | `O(k)` |

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
2. Confirm method names match `../MergeKSortedLists.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Classic heap merge.

### Thirty-second version

> Min-heap of heads.

### Two-minute version

Brute is `mergeKLists()` at `O(N log k)`. I use `mergeKLists()` at `O(N log k)` because Each node heap ops log k.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(N log k)`?**  Each node heap ops log k.

**Why space `O(k)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(N log k)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../MergeKSortedLists.java` (`package DSA.Heap`).

```bash
cd Heap
javac MergeKSortedLists.java
java -cp . DSA.Heap.MergeKSortedLists
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

- [ ] Implement `mergeKLists()` without hints.
- [ ] Implement `mergeKLists()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern B — K-way merge.

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

| Approach | Method in `MergeKSortedLists.java` |
|---|---|
| Merge pairs repeatedly | `mergeKListsBrute` |
| Min-heap of list heads (preferred) | `mergeKLists` |

Run `main` to merge sample k lists.

---

## 16. Quick revision

Pattern: **Pattern B — K-way merge**.
Brute: `O(N log k)` — `mergeKLists()`.
Optimal: `O(N log k)` — `mergeKLists()`.
Say the invariant before coding.

One line:

> **Min-heap of heads.**

---

## 17. Pattern used

**Pattern B — K-way merge**

---

## 18. Next problem

**Problem 04 — Find Median from Data Stream**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../MergeKSortedLists.java`.

```java
package DSA.Heap;

import java.util.PriorityQueue;

/*
Problem: Merge k Sorted Lists (LeetCode 23)

Each list is sorted. Return one sorted linked list.

Example:
    [[1,4,5],[1,3,4],[2,6]] -> 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
*/

public class MergeKSortedLists {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.val, b.val));

        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            tail.next = node;
            tail = tail.next;

            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }

    static void printList(ListNode head) {
        StringBuilder builder = new StringBuilder();
        while (head != null) {
            builder.append(head.val);
            if (head.next != null) {
                builder.append(" -> ");
            }
            head = head.next;
        }
        System.out.println(builder.toString());
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));

        ListNode merged = mergeKLists(new ListNode[] { l1, l2, l3 });
        System.out.print("Merged: ");
        printList(merged);

        System.out.print("Empty input: ");
        printList(mergeKLists(new ListNode[0]));
    }
}
```
