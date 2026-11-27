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

**Problem 09 — Linked List track complete**
Continue in order through the folder.
