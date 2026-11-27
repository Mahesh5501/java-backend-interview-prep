# Problem 05 — Remove Nth Node From End of List

> **Pattern:** Two pointers n apart
> **Goal:** Remove the nth node from the end in one pass.
> **LeetCode:** 19

---

## 1. Problem statement

Given n, delete that node and return head.

### Example 1

```text
1→2→3→4→5, n=2 → remove 4
```

### Example 2

```text
1→2, n=1 → remove 2
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Advance fast n steps; then move both until fast hits end.
```

**Pattern D — N-gap pointers** from `Linked_List_Common_Patterns.md`

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

Count length L, remove node L-n.

### Key method

`bruteForce()` in `../RemoveNthNode.java`

### Dry run (summary)

```text
L=5 n=2 → remove index 3 (value 4)
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

Dummy head; gap of n+1 for predecessor.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`removeNthFromEnd()` in `../RemoveNthNode.java`

```java
public static ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0, head);
    ListNode fast = dummy, slow = dummy;
    for (int i = 0; i <= n; i++) fast = fast.next;
    while (fast != null) { slow = slow.next; fast = fast.next; }
    slow.next = slow.next.next;
    return dummy.next;
}
```

### Visual walkthrough

```text
Advance fast n steps; then move both until fast hits end.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Use dummy to simplify head deletion.

---

## 7. Detailed dry run

```text
slow ends just before node to delete
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

Gap n+1 makes slow the predecessor.

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
2. Confirm method names match `../RemoveNthNode.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> One pass after fast lead.

### Thirty-second version

> Fast n+1 ahead; unlink slow.next.

### Two-minute version

Brute is `bruteForce()` at `O(n)`. I use `removeNthFromEnd()` at `O(n)` because Gap n+1 makes slow the predecessor.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Gap n+1 makes slow the predecessor.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../RemoveNthNode.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac RemoveNthNode.java
java -cp . DSA.LinkedList.RemoveNthNode
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
- [ ] Implement `removeNthFromEnd()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern D — N-gap pointers.

---

## 16. Quick revision

Pattern: **Pattern D — N-gap pointers**.
Brute: `O(n)` — `bruteForce()`.
Optimal: `O(n)` — `removeNthFromEnd()`.
Say the invariant before coding.

One line:

> **Fast n+1 ahead; unlink slow.next.**

---

## 17. Pattern used

**Pattern D — N-gap pointers**

---

## 18. Next problem

**Problem 06 — Palindrome Linked List**
Continue in order through the folder.
