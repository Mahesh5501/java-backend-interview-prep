# Problem 08 — Remove All Adjacent Duplicates In String

> **Pattern:** Cancellation stack
> **Goal:** Collapse adjacent duplicate pairs until stable.
> **LeetCode:** 1047

---

## 1. Problem statement

Repeatedly remove adjacent equal letters until impossible.

### Example 1

```text
s = "abbaca" → "ca"
```

### Example 2

```text
s = "azxxzy" → "ay"
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
If char equals stack top, pop (cancel); else push.
```

**Pattern A — Stack cancellation** from `Stack_Common_Patterns.md`

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

Scan and delete first adjacent pair repeatedly.

### Key method

`bruteForce()` in `../RemoveAdjacentDuplicates.java`

### Dry run (summary)

```text
abb → remove bb → aa? continue
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

Stack cancel on duplicate peek.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`removeDuplicates()` in `../RemoveAdjacentDuplicates.java`

```java
public static String removeDuplicates(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (!stack.isEmpty() && stack.peek() == c) stack.pop();
        else stack.push(c);
    }
    StringBuilder sb = new StringBuilder();
    for (char c : stack) sb.append(c);
    return sb.reverse().toString();
}
```

### Visual walkthrough

```text
If char equals stack top, pop (cancel); else push.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Rebuild from stack bottom→top (deque removeLast loop).

---

## 7. Detailed dry run

```text
abbaca: second b cancels first b; later a cancels
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

Mirrors one pass of pairwise deletion.

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
2. Confirm method names match `../RemoveAdjacentDuplicates.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Each char pushed/popped once.

### Thirty-second version

> Cancel with peek or push.

### Two-minute version

Brute is `bruteForce()` at `O(n^2)`. I use `removeDuplicates()` at `O(n)` because Mirrors one pass of pairwise deletion.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Mirrors one pass of pairwise deletion.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../RemoveAdjacentDuplicates.java` (`package DSA.Stack`).

```bash
cd Stack
javac RemoveAdjacentDuplicates.java
java -cp . DSA.Stack.RemoveAdjacentDuplicates
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
- [ ] Implement `removeDuplicates()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — Stack cancellation.

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

Pattern: **Pattern A — Stack cancellation**.
Brute: `O(n^2)` — `bruteForce()`.
Optimal: `O(n)` — `removeDuplicates()`.
Say the invariant before coding.

One line:

> **Cancel with peek or push.**

---

## 17. Pattern used

**Pattern A — Stack cancellation**

---

## 18. Next problem

**Problem 09 — Stack track complete**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../RemoveAdjacentDuplicates.java`.

```java
package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 1047 — Remove All Adjacent Duplicates In String
 * Pattern: Stack cancellation on push
 */
public class RemoveAdjacentDuplicates {

  // APPROACH 1: BRUTE FORCE — repeatedly scan and remove pairs until stable
  // Time: O(n^2) worst case, Space: O(n)
  public static String bruteForce(String s) {
    StringBuilder sb = new StringBuilder(s);
    boolean found;
    do {
      found = false;
      for (int i = 0; i < sb.length() - 1; i++) {
        if (sb.charAt(i) == sb.charAt(i + 1)) {
          sb.deleteCharAt(i + 1);
          sb.deleteCharAt(i);
          found = true;
          break;
        }
      }
    } while (found);
    return sb.toString();
  }

  // APPROACH 2: STACK — if char equals peek, pop; else push
  // Time: O(n), Space: O(n)
  public static String removeDuplicates(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (char c : s.toCharArray()) {
      if (!stack.isEmpty() && stack.peek() == c) {
        stack.pop();
      } else {
        stack.push(c);
      }
    }

    StringBuilder result = new StringBuilder();
    while (!stack.isEmpty()) {
      result.append(stack.removeLast());
    }
    return result.toString();
  }

  // APPROACH 3: StringBuilder as stack (same logic, no Deque)
  public static String removeDuplicatesStringBuilder(String s) {
    StringBuilder stack = new StringBuilder();
    for (char c : s.toCharArray()) {
      int len = stack.length();
      if (len > 0 && stack.charAt(len - 1) == c) {
        stack.deleteCharAt(len - 1);
      } else {
        stack.append(c);
      }
    }
    return stack.toString();
  }

  /**
   * Dry run: "abbaca"
   * a push, b push, b matches -> pop b, a push, c push, a matches -> pop a => "ca"
   */
  public static void main(String[] args) {
    String[] tests = {"abbaca", "azxxzy", "aaaa", "a", "", "baab"};

    for (String s : tests) {
      System.out.println("Input: \"" + s + "\"");
      System.out.println("  Brute:          " + bruteForce(s));
      System.out.println("  Stack:          " + removeDuplicates(s));
      System.out.println("  StringBuilder:  " + removeDuplicatesStringBuilder(s));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Brute rescans after each removal. Stack collapses pairs in one pass: cancel with peek or push.");
  }
}
```
