# Problem 03 — Linked List Cycle

> **Pattern:** Floyd cycle detection
> **Goal:** Detect if linked list has a cycle.
> **LeetCode:** 141

---

## 1. Problem statement

Return true if some node is reachable again.

### Example 1

```text
3→2→0→-4↘
       └── cycle
```

### Example 2

```text
1→2→null → false
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
If fast meets slow inside cycle, cycle exists.
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

HashSet of visited nodes.

### Key method

`hasCycleHashSet()` in `../LinkedListCycle.java`

### Dry run (summary)

```text
Reuse node → set hit
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

Fast moves 2, slow 1; meeting implies cycle.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`hasCycle()` in `../LinkedListCycle.java`

```java
public static boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

### Visual walkthrough

```text
If fast meets slow inside cycle, cycle exists.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Compare references, not values.

---

## 7. Detailed dry run

```text
Meeting inside loop proves cycle
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

In cycle, fast gains 1 step per iteration → must catch slow.

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
2. Confirm method names match `../LinkedListCycle.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Floyd: O(1) space vs HashSet.

### Thirty-second version

> Slow/fast meet → cycle.

### Two-minute version

Brute is `hasCycleHashSet()` at `O(n)`. I use `hasCycle()` at `O(n)` because In cycle, fast gains 1 step per iteration → must catch slow.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  In cycle, fast gains 1 step per iteration → must catch slow.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../LinkedListCycle.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac LinkedListCycle.java
java -cp . DSA.LinkedList.LinkedListCycle
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

- [ ] Implement `hasCycleHashSet()` without hints.
- [ ] Implement `hasCycle()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern B — Fast & slow.

---

## 16. Quick revision

Pattern: **Pattern B — Fast & slow**.
Brute: `O(n)` — `hasCycleHashSet()`.
Optimal: `O(n)` — `hasCycle()`.
Say the invariant before coding.

One line:

> **Slow/fast meet → cycle.**

---

## 17. Pattern used

**Pattern B — Fast & slow**

---

## 18. Next problem

**Problem 04 — Merge Two Sorted Lists**
Continue in order through the folder.
