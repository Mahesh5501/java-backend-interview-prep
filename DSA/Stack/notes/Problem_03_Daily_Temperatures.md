# Problem 03 — Daily Temperatures

> **Pattern:** Monotonic decreasing stack of indices  
> **Java file:** `../DailyTemperatures.java`  
> **Goal:** Understand every approach — what it does, why we pick it, and which Java method is which.  
> **LeetCode:** 739

---

## 1. Problem statement

Given daily temperatures, return wait days until warmer; 0 if none.

### Example 1

```text
Input: T = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
```

### Example 2

```text
Input: T = [30,40,50,60]
Output: [1,1,1,0]
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Stack holds indices of days waiting for a warmer future day.
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

For each i scan right until warmer.

### Key method

`bruteForce()` in `../DailyTemperatures.java`

### Dry run (summary)

```text
i=0 scan to 74 at i+1 → answer 1
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

Right-to-left: pop colder/equal; top is next warmer index.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`dailyTemperatures()` in `../DailyTemperatures.java`

```java
public static int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    for (int day = n - 1; day >= 0; day--) {
        while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[day]) {
            stack.pop();
        }
        answer[day] = stack.isEmpty() ? 0 : stack.peek() - day;
        stack.push(day);
    }
    return answer;
}
```

### Visual walkthrough

```text
Stack holds indices of days waiting for a warmer future day.
```

---

## 5b. Approach 3 — Left-to-right monotonic stack

### Idea

Walk left → right; pop colder days from stack while current is warmer; assign distances when popping.

### Java method

`dailyTemperaturesLeftToRight` in `../DailyTemperatures.java`

---

## 5c. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Choose left-to-right or right-to-left consistently.

---

## 7. Detailed dry run

```text
day 5 temp 72: pop indices not warmer; top 6 (76) → wait 1
```

---

## 7b. Extra variants in the Java file

- dailyTemperaturesLeftToRight

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

Each index pushed/popped once.

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
2. Confirm method names match `../DailyTemperatures.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Monotonic stack keeps nearest warmer candidate to the right.

### Thirty-second version

> Decreasing index stack; distance from popped warmer day.

### Two-minute version

Brute is `bruteForce()` at `O(n^2)`. I use `dailyTemperatures()` at `O(n)` because Each index pushed/popped once.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Each index pushed/popped once.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../DailyTemperatures.java` (`package DSA.Stack`).

```bash
cd Stack
javac DailyTemperatures.java
java -cp . DSA.Stack.DailyTemperatures
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
- [ ] Implement `dailyTemperatures()` without hints.
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
Optimal: `O(n)` — `dailyTemperatures()`.
Say the invariant before coding.

One line:

> **Decreasing index stack; distance from popped warmer day.**

---

## 17. Pattern used

**Pattern B — Monotonic stack**

---

## 18. Next problem

**Problem 04 — Next Greater Element**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../DailyTemperatures.java`.

```java
package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 739 — Daily Temperatures
 * Pattern: Monotonic decreasing stack of indices
 */
public class DailyTemperatures {

  // APPROACH 1: BRUTE FORCE — for each i, scan right until warmer day
  // Time: O(n^2), Space: O(1) besides output
  public static int[] bruteForce(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (temperatures[j] > temperatures[i]) {
          answer[i] = j - i;
          break;
        }
      }
    }
    return answer;
  }

  // APPROACH 2: MONOTONIC STACK — scan right to left (indices, decreasing temps on stack)
  // Time: O(n), Space: O(n)
  public static int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int day = n - 1; day >= 0; day--) {
      while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[day]) {
        stack.pop();
      }
      answer[day] = stack.isEmpty() ? 0 : stack.peek() - day;
      stack.push(day);
    }
    return answer;
  }

  // APPROACH 3: LEFT-TO-RIGHT variant (pop while current is warmer than stack top)
  public static int[] dailyTemperaturesLeftToRight(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int day = 0; day < n; day++) {
      while (!stack.isEmpty() && temperatures[day] > temperatures[stack.peek()]) {
        int prev = stack.pop();
        answer[prev] = day - prev;
      }
      stack.push(day);
    }
    return answer;
  }

  /**
   * Dry run (right to left): T = [73,74,75,71,...]
   * day 7: stack empty -> ans[7]=0, push 7
   * day 6: 76>73 at top -> pop, stack empty -> ans[6]=0, push 6
   * day 5: top 6 has 76>72 -> ans[5]=6-5=1, push 5
   */
  public static void main(String[] args) {
    int[][] tests = {
      {73, 74, 75, 71, 69, 72, 76, 73},
      {30, 40, 50, 60},
      {30, 20, 10},
      {55}
    };

    for (int[] t : tests) {
      System.out.println("Input:  " + Arrays.toString(t));
      System.out.println("Brute:  " + Arrays.toString(bruteForce(t)));
      System.out.println("Stack:  " + Arrays.toString(dailyTemperatures(t)));
      System.out.println("L->R:   " + Arrays.toString(dailyTemperaturesLeftToRight(t)));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Brute is O(n^2). I keep a decreasing stack of indices of future warmer candidates.");
    System.out.println(
        "Scanning right-to-left, I pop indices not warmer than today; top is next warmer day.");
  }
}
```
