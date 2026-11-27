# Problem 06 — Decode String

> **Pattern:** Nested string stack
> **Goal:** Expand k[encoded] patterns to a plain string.
> **LeetCode:** 394

---

## 1. Problem statement

Decode string with form k[encoded_string] (digits, letters, brackets).

### Example 1

```text
s = "3[a]2[bc]" → "aaabbc"
```

### Example 2

```text
s = "3[a2[c]]" → "accaccacc"
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Digits build k; '[' saves state; ']' restores and repeats.
```

**Pattern D — Multi-stack state** from `Stack_Common_Patterns.md`

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

Recursion on bracket boundaries.

### Key method

`decodeBruteRecursive()` in `../DecodeString.java`

### Dry run (summary)

```text
3[a2[c]] → inner cc, outer repeat
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(output)` |
| **Space** | `O(depth)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Two stacks: repeat counts + prior string builders.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`decodeString()` in `../DecodeString.java`

```java
public static String decodeString(String s) {
    Deque<Integer> counts = new ArrayDeque<>();
    Deque<StringBuilder> strings = new ArrayDeque<>();
    StringBuilder cur = new StringBuilder();
    int k = 0;
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) k = k * 10 + (c - '0');
        else if (c == '[') { counts.push(k); strings.push(cur); cur = new StringBuilder(); k = 0; }
        else if (c == ']') {
            StringBuilder prev = strings.pop();
            for (int t = 0; t < counts.pop(); t++) prev.append(cur);
            cur = prev;
        } else cur.append(c);
    }
    return cur.toString();
}
```

### Visual walkthrough

```text
Digits build k; '[' saves state; ']' restores and repeats.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Multi-digit k via k = k*10 + digit.

---

## 7. Detailed dry run

```text
On ']' merge current into popped builder k times
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

Stack mirrors nested expansion inside-out.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(output)` | `O(depth)` |
| Optimal | `O(output)` | `O(n)` |

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
2. Confirm method names match `../DecodeString.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> On ] pop count and prior string; append repeated segment.

### Thirty-second version

> Save on [; expand on ].

### Two-minute version

Brute is `decodeBruteRecursive()` at `O(output)`. I use `decodeString()` at `O(output)` because Stack mirrors nested expansion inside-out.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(output)`?**  Stack mirrors nested expansion inside-out.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(output)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../DecodeString.java` (`package DSA.Stack`).

```bash
cd Stack
javac DecodeString.java
java -cp . DSA.Stack.DecodeString
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

- [ ] Implement `decodeBruteRecursive()` without hints.
- [ ] Implement `decodeString()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern D — Multi-stack state.

---

## 16. Quick revision

Pattern: **Pattern D — Multi-stack state**.
Brute: `O(output)` — `decodeBruteRecursive()`.
Optimal: `O(output)` — `decodeString()`.
Say the invariant before coding.

One line:

> **Save on [; expand on ].**

---

## 17. Pattern used

**Pattern D — Multi-stack state**

---

## 18. Next problem

**Problem 07 — Evaluate RPN**
Continue in order through the folder.
