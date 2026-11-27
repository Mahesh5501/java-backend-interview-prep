# Problem 02 — Middle of Linked List

> **Pattern:** Fast / slow pointers
> **Goal:** Return middle node (second middle if even length).
> **LeetCode:** 876

---

## 1. Problem statement

Find middle of linked list in one pass.

### Example 1

```text
1→2→3→4→5 → node 3
```

### Example 2

```text
1→2→3→4 → node 3 (second middle)
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Slow advances 1, fast advances 2 per step.
```

**Pattern B — Fast & slow** from `Linked_List_Common_Patterns.md`

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

Two passes: count then locate n/2.

### Key method

`bruteForce()` in `../MiddleOfLinkedList.java`

### Dry run (summary)

```text
len=5 → walk 2 steps from head
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(1)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

When fast reaches end, slow is middle.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`middleNode()` in `../MiddleOfLinkedList.java`

```java
public static ListNode middleNode(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}
```

### Visual walkthrough

```text
Slow advances 1, fast advances 2 per step.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Even length: fast can't move twice → second middle.

---

## 7. Detailed dry run

```text
fast hits null → slow at middle
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

When fast stops, slow traveled half the steps.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n)` | `O(1)` |
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
2. Confirm method names match `../MiddleOfLinkedList.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Tortoise-hare until fast exhausts.

### Thirty-second version

> Fast 2x slow.

### Two-minute version

Brute is `bruteForce()` at `O(n)`. I use `middleNode()` at `O(n)` because When fast stops, slow traveled half the steps.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  When fast stops, slow traveled half the steps.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../MiddleOfLinkedList.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac MiddleOfLinkedList.java
java -cp . DSA.LinkedList.MiddleOfLinkedList
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
- [ ] Implement `middleNode()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern B — Fast & slow.

---

## 16. Quick revision

Pattern: **Pattern B — Fast & slow**.
Brute: `O(n)` — `bruteForce()`.
Optimal: `O(n)` — `middleNode()`.
Say the invariant before coding.

One line:

> **Fast 2x slow.**

---

## 17. Pattern used

**Pattern B — Fast & slow**

---

## 18. Next problem

**Problem 03 — Linked List Cycle**
Continue in order through the folder.
