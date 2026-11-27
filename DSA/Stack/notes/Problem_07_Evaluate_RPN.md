# Problem 07 — Evaluate RPN

> **Pattern:** Operand stack  
> **Java file:** `../EvaluateRPN.java`  
> **Goal:** Understand every approach — what it does, why we pick it, and which Java method is which.  
> **LeetCode:** 150

---

## 1. Problem statement

Evaluate Reverse Polish Notation with + - * / on integers.

### Example 1

```text
["2","1","+","3","*"] → 9
```

### Example 2

```text
["4","13","5","/","+"] → 6
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Numbers push; operator pops two operands in correct order.
```

**Pattern E — Expression stack** from `Stack_Common_Patterns.md`

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

Use `ArrayList` as an operand stack (push/pop at end) — same postfix rules as the optimal deque.

### Key method

`evalRPNBrute()` in `../EvaluateRPN.java`

### Dry run (summary)

```text
2,1,+ → 3; then *3 → 9
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

Second pop is right operand for - and /.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`evalRPN()` in `../EvaluateRPN.java`

```java
public static int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (String t : tokens) {
        if (t.equals("+")) { int b=stack.pop(), a=stack.pop(); stack.push(a+b); }
        else if (t.equals("-")) { int b=stack.pop(), a=stack.pop(); stack.push(a-b); }
        else if (t.equals("*")) { int b=stack.pop(), a=stack.pop(); stack.push(a*b); }
        else if (t.equals("/")) { int b=stack.pop(), a=stack.pop(); stack.push(a/b); }
        else stack.push(Integer.parseInt(t));
    }
    return stack.pop();
}
```

### Visual walkthrough

```text
Numbers push; operator pops two operands in correct order.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Order: first pop = right, second = left.

---

## 7. Detailed dry run

```text
/ : pop b then a → a/b truncates toward zero
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

Postfix yields operands before their operator.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n)` | `O(n)` |
| Optimal | `O(n)` | `O(n)` |

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
2. Confirm method names match `../EvaluateRPN.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Stack holds partial results; one value remains.

### Thirty-second version

> Push numbers; pop order matters for - /.

### Two-minute version

Brute is `evalRPN()` at `O(n)`. I use `evalRPN()` at `O(n)` because Postfix yields operands before their operator.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Postfix yields operands before their operator.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../EvaluateRPN.java` (`package DSA.Stack`).

```bash
cd Stack
javac EvaluateRPN.java
java -cp . DSA.Stack.EvaluateRPN
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

- [ ] Implement `evalRPN()` without hints.
- [ ] Implement `evalRPN()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern E — Expression stack.

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

Pattern: **Pattern E — Expression stack**.
Brute: `O(n)` — `evalRPN()`.
Optimal: `O(n)` — `evalRPN()`.
Say the invariant before coding.

One line:

> **Push numbers; pop order matters for - /.**

---

## 17. Pattern used

**Pattern E — Expression stack**

---

## 18. Next problem

**Problem 08 — Remove Adjacent Duplicates**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../EvaluateRPN.java`.

```java
package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 150 — Evaluate Reverse Polish Notation
 * Pattern: Operand stack; operators pop two, push result
 */
public class EvaluateRPN {

  // APPROACH 1: BRUTE — convert to list and simulate with a mutable structure (same as stack)
  // Shown as explicit stack for teaching parity with optimal.

  // APPROACH 2: STACK evaluation
  // Time: O(n), Space: O(n)
  public static int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();

    for (String token : tokens) {
      if (isOperator(token)) {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(apply(a, b, token));
      } else {
        stack.push(Integer.parseInt(token));
      }
    }
    return stack.pop();
  }

  private static boolean isOperator(String token) {
    return "+".equals(token)
        || "-".equals(token)
        || "*".equals(token)
        || "/".equals(token);
  }

  private static int apply(int a, int b, String op) {
    switch (op) {
      case "+":
        return a + b;
      case "-":
        return a - b;
      case "*":
        return a * b;
      case "/":
        return a / b; // truncates toward zero in Java
      default:
        throw new IllegalArgumentException("Unknown operator: " + op);
    }
  }

  /**
   * Dry run: ["2","1","+","3","*"]
   * push 2, push 1, '+' -> pop 1,2 push 3, push 3, '*' -> pop 3,3 push 9
   */
  public static void main(String[] args) {
    String[][] tests = {
      {"2", "1", "+", "3", "*"},
      {"4", "13", "5", "/", "+"},
      {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"},
      {"18"}
    };

    for (String[] t : tests) {
      System.out.println("Tokens: " + String.join(" ", t));
      System.out.println("Result: " + evalRPN(t));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Numbers push. On operator, second pop is right operand, first is left — important for - and /.");
    System.out.println("Single value left at end. O(n) time.");
  }
}
```
