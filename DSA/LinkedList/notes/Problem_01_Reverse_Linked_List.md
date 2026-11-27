# Problem 01 — Reverse Linked List

> **Pattern:** Three-pointer reversal
> **Goal:** Return head of reversed singly linked list.
> **LeetCode:** 206

---

## 1. Problem statement

Reverse a singly linked list in O(1) extra space.

### Example 1

```text
1→2→3→null becomes 3→2→1→null
```

### Example 2

```text
1→null stays 1
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
prev=null; each step rewires curr.next backward.
```

**Pattern A — In-place reversal** from `Linked_List_Common_Patterns.md`

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

Copy to list, reverse, rebuild with ListNode.of.

### Key method

`bruteForce()` in `../ReverseLinkedList.java`

### Dry run (summary)

```text
1→2→3 copy reverse rebuild
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(n)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Iterative prev/curr/next flip.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`reverseList()` in `../ReverseLinkedList.java`

```java
public static ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

### Visual walkthrough

```text
prev=null; each step rewires curr.next backward.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Save next before breaking link.

---

## 7. Detailed dry run

```text
Step: 1→null, 2→1, 3→2, return 3
```

---

## 7b. Extra variants in the Java file

- reverseRecursive

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

Each node visited once; old list becomes reversed.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n)` | `O(n)` |
| Optimal | `O(n)` | `O(1)` |

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
2. Confirm method names match `../ReverseLinkedList.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Three pointers iterative or tail-recursive variant.

### Thirty-second version

> prev/curr/next flip.

### Two-minute version

Brute is `bruteForce()` at `O(n)`. I use `reverseList()` at `O(n)` because Each node visited once; old list becomes reversed.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Each node visited once; old list becomes reversed.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../ReverseLinkedList.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac ReverseLinkedList.java
java -cp . DSA.LinkedList.ReverseLinkedList
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

- [ ] Implement `bruteForce()` without hints.
- [ ] Implement `reverseList()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — In-place reversal.

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

Pattern: **Pattern A — In-place reversal**.
Brute: `O(n)` — `bruteForce()`.
Optimal: `O(n)` — `reverseList()`.
Say the invariant before coding.

One line:

> **prev/curr/next flip.**

---

## 17. Pattern used

**Pattern A — In-place reversal**

---

## 18. Next problem

**Problem 02 — Middle of Linked List**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../ReverseLinkedList.java`.

```java
package DSA.LinkedList;

import java.util.Collections;
import java.util.List;

/**
 * LeetCode 206 — Reverse Linked List
 * Pattern: Three-pointer in-place reversal
 */
public class ReverseLinkedList {

  // APPROACH 1: BRUTE — copy to list, reverse, rebuild
  // Time: O(n), Space: O(n)
  public static ListNode bruteForce(ListNode head) {
    List<Integer> values = ListNode.toList(head);
    Collections.reverse(values);
    int[] arr = new int[values.size()];
    for (int i = 0; i < values.size(); i++) {
      arr[i] = values.get(i);
    }
    return ListNode.of(arr);
  }

  // APPROACH 2: ITERATIVE three pointers
  // Time: O(n), Space: O(1)
  public static ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
      ListNode next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    return prev;
  }

  // APPROACH 3: RECURSIVE
  public static ListNode reverseRecursive(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode newHead = reverseRecursive(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
  }

  /**
   * Dry run: 1->2->3
   * prev=null, curr=1: 1->null, prev=1, curr=2
   * prev=1, curr=2: 2->1, prev=2, curr=3
   * prev=2, curr=3: 3->2, prev=3, curr=null => return 3
   */
  public static void main(String[] args) {
    ListNode head = ListNode.of(1, 2, 3, 4, 5);

    System.out.println("Original:");
    ListNode.print(head);

    ListNode brute = bruteForce(ListNode.of(1, 2, 3, 4, 5));
    System.out.print("Brute reversed: ");
    ListNode.print(brute);

    ListNode iter = reverseList(ListNode.of(1, 2, 3, 4, 5));
    System.out.print("Iterative:      ");
    ListNode.print(iter);

    ListNode rec = reverseRecursive(ListNode.of(1, 2));
    System.out.print("Recursive:      ");
    ListNode.print(rec);

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "I keep prev, curr, next. For each node I point curr.next to prev and slide all three forward.");
    System.out.println("Brute uses O(n) extra; iterative is O(1) space.");
  }
}
```
