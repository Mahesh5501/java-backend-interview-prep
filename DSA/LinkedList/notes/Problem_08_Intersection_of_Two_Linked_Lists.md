# Problem 08 — Intersection of Two Linked Lists

> **Pattern:** Length align or two-switch walk
> **Goal:** Return shared node or null.
> **LeetCode:** 160

---

## 1. Problem statement

Lists may intersect at a node (by reference).

### Example 1

```text
Shared tail from node c
```

### Example 2

```text
No intersection → null
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
After aligning lengths, first common node is answer.
```

**Pattern G — Intersection** from `Linked_List_Common_Patterns.md`

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

Compare every pair of nodes.

### Key method

`bruteForce()` in `../IntersectionOfTwoLinkedLists.java`

### Dry run (summary)

```text
Nested loops find match
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n·m)` |
| **Space** | `O(1)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Switch heads when a list ends — equalizes offset.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`getIntersectionNode()` in `../IntersectionOfTwoLinkedLists.java`

```java
public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) return null;
    ListNode a = headA, b = headB;
    while (a != b) {
        a = (a == null) ? headB : a.next;
        b = (b == null) ? headA : b.next;
    }
    return a;
}
```

### Visual walkthrough

```text
After aligning lengths, first common node is answer.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Not same value — same reference.

---

## 7. Detailed dry run

```text
a walks A+B, b walks B+A → meet at intersection
```

---

## 7b. Extra variants in the Java file

- getIntersectionNodeByLength

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

Equal path length after switch.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n·m)` | `O(1)` |
| Optimal | `O(n+m)` | `O(1)` |

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
2. Confirm method names match `../IntersectionOfTwoLinkedLists.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Elegant without length precompute (alternate: getIntersectionNodeByLength).

### Thirty-second version

> Switch heads at end.

### Two-minute version

Brute is `bruteForce()` at `O(n·m)`. I use `getIntersectionNode()` at `O(n+m)` because Equal path length after switch.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n+m)`?**  Equal path length after switch.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n+m)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../IntersectionOfTwoLinkedLists.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac IntersectionOfTwoLinkedLists.java
java -cp . DSA.LinkedList.IntersectionOfTwoLinkedLists
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
- [ ] Implement `getIntersectionNode()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern G — Intersection.

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

Pattern: **Pattern G — Intersection**.
Brute: `O(n·m)` — `bruteForce()`.
Optimal: `O(n+m)` — `getIntersectionNode()`.
Say the invariant before coding.

One line:

> **Switch heads at end.**

---

## 17. Pattern used

**Pattern G — Intersection**

---

## 18. Next problem

**Problem 09 — Happy Number** (LeetCode 202)  
Same Floyd fast/slow pattern as Problem 03, but the “list” is the sum-of-squares-of-digits chain.

---

---

## Repository Java (full)

Align with `../IntersectionOfTwoLinkedLists.java`.

```java
package DSA.LinkedList;

/**
 * LeetCode 160 — Intersection of Two Linked Lists
 * Pattern: Align lengths, or two-pointer head swap
 */
public class IntersectionOfTwoLinkedLists {

  // APPROACH 1: BRUTE — nested traversal (compare references)
  public static ListNode bruteForce(ListNode headA, ListNode headB) {
    ListNode a = headA;
    while (a != null) {
      ListNode b = headB;
      while (b != null) {
        if (a == b) {
          return a;
        }
        b = b.next;
      }
      a = a.next;
    }
    return null;
  }

  // APPROACH 2: LENGTH ALIGNMENT
  public static ListNode getIntersectionNodeByLength(ListNode headA, ListNode headB) {
    int lenA = ListNode.length(headA);
    int lenB = ListNode.length(headB);
    ListNode a = headA;
    ListNode b = headB;

    if (lenA > lenB) {
      for (int i = 0; i < lenA - lenB; i++) {
        a = a.next;
      }
    } else {
      for (int i = 0; i < lenB - lenA; i++) {
        b = b.next;
      }
    }
    while (a != null && b != null) {
      if (a == b) {
        return a;
      }
      a = a.next;
      b = b.next;
    }
    return null;
  }

  // APPROACH 3: TWO POINTERS — switch heads on null (a walks A+B, b walks B+A)
  public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) {
      return null;
    }
    ListNode a = headA;
    ListNode b = headB;
    while (a != b) {
      a = (a == null) ? headB : a.next;
      b = (b == null) ? headA : b.next;
    }
    return a;
  }

  /** Build classic Y-shaped lists sharing tail from shared node. */
  public static ListNode[] buildIntersectingLists() {
    ListNode shared = ListNode.of(8, 4, 5);
    ListNode headA = new ListNode(4);
    headA.next = new ListNode(1);
    headA.next.next = shared;

    ListNode headB = new ListNode(5);
    headB.next = new ListNode(6);
    headB.next.next = new ListNode(1);
    headB.next.next.next = shared;

    return new ListNode[] {headA, headB, shared};
  }

  public static void main(String[] args) {
    ListNode[] lists = buildIntersectingLists();
    ListNode headA = lists[0];
    ListNode headB = lists[1];

    System.out.println("List A:");
    ListNode.print(headA);
    System.out.println("List B:");
    ListNode.print(headB);

    ListNode brute = bruteForce(headA, headB);
    ListNode byLen = getIntersectionNodeByLength(headA, headB);
    ListNode optimal = getIntersectionNode(headA, headB);

    System.out.println(
        "Intersection node value (same reference expected): "
            + (brute != null ? brute.val : "null"));
    System.out.println("Length method matches: " + (byLen == optimal));
    System.out.println("Two-pointer method val: " + (optimal != null ? optimal.val : "null"));

    ListNode noA = ListNode.of(2, 6);
    ListNode noB = ListNode.of(1, 5);
    System.out.println("\nNo intersection: " + getIntersectionNode(noA, noB));

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "Brute is O(n*m). Align longer list by length diff, or swap heads on null — same node reference.");
  }
}
```
