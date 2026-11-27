# Problem 02 — Min Stack

> **Pattern:** Auxiliary min stack  
> **Java file:** `../MinStack.java`  
> **Goal:** Understand every approach — what it does, why we pick it, and which Java method is which.  
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

Inner class `MinStackBrute` in `../MinStack.java` (`getMin()` scans the stack)

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

## 5. Approach 2 — Single stack of pairs

### Idea

One deque stores `[value, minSoFar]` pairs so `getMin()` reads the second field.

### Key method

Inner class `MinStackPairs` in `../MinStack.java`

---

## 5b. Approach 3 — Dual deque (optimal — LeetCode API)

### Idea

`values` stack plus parallel `mins` stack; always pop both together.

### Key method

`MinStack` instance methods `push` / `pop` / `top` / `getMin` in `../MinStack.java`

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

## 5c. Complexity intuition (optimal)

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

---

---

## Repository Java (full)

Align with `../MinStack.java`.

```java
package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 155 — Min Stack
 * Pattern: Auxiliary stack (or paired nodes) for O(1) getMin
 */
public class MinStack {

  // --- Optimal design used by LeetCode instance methods ---
  private final Deque<Integer> values = new ArrayDeque<>();
  private final Deque<Integer> mins = new ArrayDeque<>();

  public void push(int val) {
    values.push(val);
    int minSoFar = mins.isEmpty() ? val : Math.min(mins.peek(), val);
    mins.push(minSoFar);
  }

  public void pop() {
    values.pop();
    mins.pop();
  }

  public int top() {
    return values.peek();
  }

  public int getMin() {
    return mins.peek();
  }

  // APPROACH 1: BRUTE FORCE — scan all values on each getMin()
  static class MinStackBrute {
    private final Deque<Integer> stack = new ArrayDeque<>();

    void push(int val) {
      stack.push(val);
    }

    void pop() {
      stack.pop();
    }

    int top() {
      return stack.peek();
    }

    int getMin() {
      int min = Integer.MAX_VALUE;
      for (int x : stack) {
        min = Math.min(min, x);
      }
      return min;
    }
  }

  // APPROACH 2: SINGLE STACK OF PAIRS (value, minSoFar) — same idea, one deque
  static class MinStackPairs {
    private final Deque<long[]> stack = new ArrayDeque<>();

    void push(int val) {
      long minSoFar = stack.isEmpty() ? val : Math.min(stack.peek()[1], val);
      stack.push(new long[] {val, minSoFar});
    }

    void pop() {
      stack.pop();
    }

    int top() {
      return (int) stack.peek()[0];
    }

    int getMin() {
      return (int) stack.peek()[1];
    }
  }

  /**
   * Dry run after pushes 3, 1, 5:
   * values: [3,1,5] (top 5)   mins: [3,1,1] (top 1) -> getMin() = 1
   * pop -> top 5 removed, getMin still 1
   */
  public static void main(String[] args) {
    MinStack optimal = new MinStack();
    optimal.push(3);
    optimal.push(1);
    optimal.push(5);
    System.out.println("Optimal — top=" + optimal.top() + ", min=" + optimal.getMin());
    optimal.pop();
    System.out.println("After pop — top=" + optimal.top() + ", min=" + optimal.getMin());

    MinStackBrute brute = new MinStackBrute();
    brute.push(3);
    brute.push(1);
    System.out.println("\nBrute getMin scans stack: " + brute.getMin());

    MinStackPairs pairs = new MinStackPairs();
    pairs.push(2);
    pairs.push(0);
    pairs.push(3);
    System.out.println("Pairs — top=" + pairs.top() + ", min=" + pairs.getMin());

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "Brute getMin is O(n). I keep a second stack of running minimums so each push/pop");
    System.out.println("updates min in O(1). Duplicating min values is OK.");
  }
}
```
