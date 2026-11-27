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
