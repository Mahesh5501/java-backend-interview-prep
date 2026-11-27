# Problem 02 — Min Stack

> **Pattern:** Auxiliary min stack
> **Goal:** O(1) push, pop, top, getMin.
> **LeetCode:** 155

---

## 1. Problem statement

Design a stack that supports getMin in constant time.

### Example 1

```text
push -2, 0, -3 → getMin = -3
```

### Example 2

```text
pop → getMin = -2
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Parallel stack stores minimum-so-far after each push.
```

**Pattern C — Auxiliary stack** from `Stack_Common_Patterns.md`

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

Scan entire stack whenever getMin is called.

### Key method

`MinStackBrute()` in `../MinStack.java`

### Dry run (summary)

```text
push 3,1 → brute scans for min 1
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n) per getMin` |
| **Space** | `O(n)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Push running min onto mins deque alongside values.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`push()` in `../MinStack.java`

```java
public void push(int val) {
    values.push(val);
    int m = mins.isEmpty() ? val : Math.min(mins.peek(), val);
    mins.push(m);
}
```

### Visual walkthrough

```text
Parallel stack stores minimum-so-far after each push.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Always pop values and mins together.

---

## 7. Detailed dry run

```text
push 3 (min 3), push 1 (min 1), pop both → min back to 3
```

---

## 7b. Extra variants in the Java file

- MinStackPairs stores (value,min) in one deque

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

mins[i] = min of all elements in values stack after i pushes.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n) per getMin` | `O(n)` |
| Optimal | `O(1)` | `O(n)` |

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
2. Confirm method names match `../MinStack.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Duplicate mins on stack are OK; they represent historical states.

### Thirty-second version

> Two deques; min stack mirrors prefix minimum.

### Two-minute version

Brute is `MinStackBrute()` at `O(n) per getMin`. I use `push()` at `O(1)` because mins[i] = min of all elements in values stack after i pushes.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(1)`?**  mins[i] = min of all elements in values stack after i pushes.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(1)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../MinStack.java` (`package DSA.Stack`).

```bash
cd Stack
javac MinStack.java
java -cp . DSA.Stack.MinStack
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

- [ ] Implement `MinStackBrute()` without hints.
- [ ] Implement `push()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern C — Auxiliary stack.

---

## 16. Quick revision

Pattern: **Pattern C — Auxiliary stack**.
Brute: `O(n) per getMin` — `MinStackBrute()`.
Optimal: `O(1)` — `push()`.
Say the invariant before coding.

One line:

> **Two deques; min stack mirrors prefix minimum.**

---

## 17. Pattern used

**Pattern C — Auxiliary stack**

---

## 18. Next problem

**Problem 03 — Daily Temperatures**
Continue in order through the folder.
