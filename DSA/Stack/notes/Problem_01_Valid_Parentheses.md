# Problem 01 — Valid Parentheses

> **Pattern:** Bracket validation stack (LIFO)
> **Goal:** Return whether the bracket string is valid.
> **LeetCode:** 20

---

## 1. Problem statement

Given s containing only parentheses, brackets, and braces, determine if it is valid.

### Example 1

```text
Input: s = "()[]{}"
Output: true
```

### Example 2

```text
Input: s = "([)]"
Output: false
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Scan left→right; push openers/expected closers; closers must match stack top.
```

**Pattern A — Bracket / nesting validation** from `Stack_Common_Patterns.md`

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

Repeatedly remove adjacent valid pairs until no change.

### Key method

`bruteForce()` in `../ValidParentheses.java`

### Dry run (summary)

```text
"()[]" reduces to empty → valid
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n^2)` |
| **Space** | `O(n)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

On opener push expected closer; on closer pop must equal.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`isValid()` in `../ValidParentheses.java`

```java
public static boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (c == '(') stack.push(')');
        else if (c == '{') stack.push('}');
        else if (c == '[') stack.push(']');
        else if (stack.isEmpty() || stack.pop() != c) return false;
    }
    return stack.isEmpty();
}
```

### Visual walkthrough

```text
Scan left→right; push openers/expected closers; closers must match stack top.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Pop only when closer matches; empty stack at end.

---

## 7. Detailed dry run

```text
([)] : push ) ] then ) mismatches ] → false
```

---

## 7b. Extra variants in the Java file

- isValidOpeners
- isValidWithMap

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

LIFO nesting is exactly stack matching.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n^2)` | `O(n)` |
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
2. Confirm method names match `../ValidParentheses.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Push expected closer; mismatch or leftover stack → false. O(n) time.

### Thirty-second version

> Push expected closer; pop must match.

### Two-minute version

Brute is `bruteForce()` at `O(n^2)`. I use `isValid()` at `O(n)` because LIFO nesting is exactly stack matching.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  LIFO nesting is exactly stack matching.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../ValidParentheses.java` (`package DSA.Stack`).

```bash
cd Stack
javac ValidParentheses.java
java -cp . DSA.Stack.ValidParentheses
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
- [ ] Implement `isValid()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — Bracket / nesting validation.

---

## 16. Quick revision

Pattern: **Pattern A — Bracket / nesting validation**.
Brute: `O(n^2)` — `bruteForce()`.
Optimal: `O(n)` — `isValid()`.
Say the invariant before coding.

One line:

> **Push expected closer; pop must match.**

---

## 17. Pattern used

**Pattern A — Bracket / nesting validation**

---

## 18. Next problem

**Problem 02 — Min Stack**
Continue in order through the folder.
