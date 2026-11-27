# Problem 05 — Largest Rectangle in Histogram

> **Pattern:** Monotonic increasing index stack
> **Goal:** Maximum rectangle area under histogram.
> **LeetCode:** 84

---

## 1. Problem statement

Given bar heights, find largest rectangle area in histogram.

### Example 1

```text
heights = [2,1,5,6,2,3] → Output: 10
```

### Example 2

```text
heights = [2,4] → Output: 4
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
When height drops, popped bar’s width ends at current index.
```

**Pattern B — Monotonic stack** from `Stack_Common_Patterns.md`

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

All subarrays, track min height × width.

### Key method

`bruteForce()` in `../LargestRectangleHistogram.java`

### Dry run (summary)

```text
Best area often centered on tall bar 5 width 2
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n^2)` |
| **Space** | `O(1)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Increasing stack; pop when current lower; sentinel height 0.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`largestRectangleArea()` in `../LargestRectangleHistogram.java`

```java
public static int largestRectangleArea(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int maxArea = 0;
    for (int i = 0; i <= heights.length; i++) {
        int h = (i == heights.length) ? 0 : heights[i];
        while (!stack.isEmpty() && heights[stack.peek()] > h) {
            int idx = stack.pop();
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, heights[idx] * width);
        }
        stack.push(i);
    }
    return maxArea;
}
```

### Visual walkthrough

```text
When height drops, popped bar’s width ends at current index.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Width from new stack top+1 to i (exclusive i).

---

## 7. Detailed dry run

```text
Pop height 5 when seeing 2: width spans while 5 was minimum
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

Each bar defines height of rect extending until a shorter bar.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n^2)` | `O(1)` |
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
2. Confirm method names match `../LargestRectangleHistogram.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Sentinel 0 flushes remaining indices.

### Thirty-second version

> Increasing stack; pop on shorter current; multiply h×width.

### Two-minute version

Brute is `bruteForce()` at `O(n^2)`. I use `largestRectangleArea()` at `O(n)` because Each bar defines height of rect extending until a shorter bar.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Each bar defines height of rect extending until a shorter bar.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../LargestRectangleHistogram.java` (`package DSA.Stack`).

```bash
cd Stack
javac LargestRectangleHistogram.java
java -cp . DSA.Stack.LargestRectangleHistogram
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
- [ ] Implement `largestRectangleArea()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern B — Monotonic stack.

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

Pattern: **Pattern B — Monotonic stack**.
Brute: `O(n^2)` — `bruteForce()`.
Optimal: `O(n)` — `largestRectangleArea()`.
Say the invariant before coding.

One line:

> **Increasing stack; pop on shorter current; multiply h×width.**

---

## 17. Pattern used

**Pattern B — Monotonic stack**

---

## 18. Next problem

**Problem 06 — Decode String**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../LargestRectangleHistogram.java`.

```java
package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 84 — Largest Rectangle in Histogram
 * Pattern: Monotonic increasing stack of indices
 */
public class LargestRectangleHistogram {

  // APPROACH 1: BRUTE FORCE — every pair (left, right) as width for height at left
  // Time: O(n^2), Space: O(1)
  public static int bruteForce(int[] heights) {
    int maxArea = 0;
    int n = heights.length;
    for (int left = 0; left < n; left++) {
      int minHeight = heights[left];
      for (int right = left; right < n; right++) {
        minHeight = Math.min(minHeight, heights[right]);
        int width = right - left + 1;
        maxArea = Math.max(maxArea, minHeight * width);
      }
    }
    return maxArea;
  }

  // APPROACH 2: MONOTONIC STACK (increasing indices)
  // When heights[i] < heights[stack.top], popped bar cannot extend right past i
  // Time: O(n), Space: O(n)
  public static int largestRectangleArea(int[] heights) {
    int n = heights.length;
    int maxArea = 0;
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i <= n; i++) {
      int currentHeight = (i == n) ? 0 : heights[i];
      while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
        int heightIndex = stack.pop();
        int height = heights[heightIndex];
        int right = i;
        int left = stack.isEmpty() ? 0 : stack.peek() + 1;
        int width = right - left;
        maxArea = Math.max(maxArea, height * width);
      }
      stack.push(i);
    }
    return maxArea;
  }

  /**
   * Dry run: heights = [2,1,5,6,2,3]
   * i=1 height 1: pop 0 (h=2) width 1*1=2, push 1
   * i=3 height 6: push ...
   * sentinel at end forces remaining pops
   */
  public static void main(String[] args) {
    int[][] tests = {
      {2, 1, 5, 6, 2, 3},
      {2, 4},
      {1},
      {5, 5, 5}
    };

    for (int[] h : tests) {
      System.out.println("Heights: " + Arrays.toString(h));
      System.out.println("  Brute:  " + bruteForce(h));
      System.out.println("  Stack:  " + largestRectangleArea(h));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Brute checks all sub-arrays O(n^2). I store indices in increasing height order.");
    System.out.println(
        "When current is lower, I pop; width is from new stack top+1 to current i; area = h*width.");
    System.out.println("Append sentinel height 0 to flush the stack.");
  }
}
```
