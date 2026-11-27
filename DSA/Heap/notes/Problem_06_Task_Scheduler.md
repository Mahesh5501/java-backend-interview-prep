# Problem 06 — Task Scheduler

> **Pattern:** Greedy + heap or formula
> **Goal:** Minimum intervals to finish tasks with cooldown n.
> **LeetCode:** 621

---

## 1. Problem statement

Same task must wait n slots between runs.

### Example 1

```text
tasks=ABAB, n=2 → length 5
```

### Example 2

```text
A repeated many times
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Schedule most frequent first with idle fillers.
```

**Pattern D — Scheduling** from `Heap_Common_Patterns.md`

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

Count frequencies; formula or queue simulation.

### Key method

`leastInterval()` in `../TaskScheduler.java`

### Dry run (summary)

```text
freq map drives idle slots
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(1)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Max freq and idle math in Java file.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`leastInterval()` in `../TaskScheduler.java`

```java
public static int leastInterval(char[] tasks, int n) {
    int[] count = new int[26];
    for (char c : tasks) count[c - 'A']++;
    Arrays.sort(count);
    int max = count[25];
    int idle = (max - 1) * n;
    for (int i = 24; i >= 0 && count[i] > 0; i--) idle -= Math.min(n, count[i]);
    return tasks.length + Math.max(0, idle);
}
```

### Visual walkthrough

```text
Schedule most frequent first with idle fillers.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

n can be 0.

---

## 7. Detailed dry run

```text
idle slots between max freq block
```

---

## 7b. Extra variants in the Java file

- leastIntervalFormula

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

Formula matches greedy heap schedule.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n)` | `O(1)` |
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
2. Confirm method names match `../TaskScheduler.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> See leastIntervalFormula variant.

### Thirty-second version

> Freq sort + idle fill.

### Two-minute version

Brute is `leastInterval()` at `O(n)`. I use `leastInterval()` at `O(n)` because Formula matches greedy heap schedule.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Formula matches greedy heap schedule.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../TaskScheduler.java` (`package DSA.Heap`).

```bash
cd Heap
javac TaskScheduler.java
java -cp . DSA.Heap.TaskScheduler
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

- [ ] Implement `leastInterval()` without hints.
- [ ] Implement `leastInterval()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern D — Scheduling.

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

Pattern: **Pattern D — Scheduling**.
Brute: `O(n)` — `leastInterval()`.
Optimal: `O(n)` — `leastInterval()`.
Say the invariant before coding.

One line:

> **Freq sort + idle fill.**

---

## 17. Pattern used

**Pattern D — Scheduling**

---

## 18. Next problem

**Problem 07 — Last Stone Weight**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../TaskScheduler.java`.

```java
package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: Task Scheduler (LeetCode 621)

You have tasks labeled A..Z and a cooldown n between same labels.
Return the minimum time units to finish all tasks.

Example:
    tasks = ["A","A","A","B","B","B"], n = 2 -> 8
    Layout like: A B _ A B _ A B
*/

public class TaskScheduler {

    // Max-heap greedy: always schedule the most frequent remaining task when idle allows.
    public static int leastInterval(char[] tasks, int n) {
        int[] frequency = new int[26];
        for (char task : tasks) {
            frequency[task - 'A']++;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        for (int count : frequency) {
            if (count > 0) {
                maxHeap.offer(count);
            }
        }

        int time = 0;

        while (!maxHeap.isEmpty()) {
            int batchSize = n + 1;
            int[] processed = new int[batchSize];
            int used = 0;

            while (!maxHeap.isEmpty() && used < batchSize) {
                int remaining = maxHeap.poll() - 1;
                if (remaining > 0) {
                    processed[used++] = remaining;
                }
            }

            for (int i = 0; i < used; i++) {
                maxHeap.offer(processed[i]);
            }

            if (maxHeap.isEmpty()) {
                time += used;
            } else {
                time += batchSize;
            }
        }

        return time;
    }

    // Formula shortcut: result = max(tasks.length, (maxFreq-1)*(n+1) + maxCount)
    public static int leastIntervalFormula(char[] tasks, int n) {
        int[] frequency = new int[26];
        for (char task : tasks) {
            frequency[task - 'A']++;
        }

        Arrays.sort(frequency);

        int maxFreq = frequency[25];
        int maxCount = 0;
        for (int count : frequency) {
            if (count == maxFreq) {
                maxCount++;
            }
        }

        int part1 = (maxFreq - 1) * (n + 1) + maxCount;
        return Math.max(tasks.length, part1);
    }

    public static void main(String[] args) {
        char[] tasks = { 'A', 'A', 'A', 'B', 'B', 'B' };
        System.out.println("n=2 (heap):    " + leastInterval(tasks, 2)); // 8
        System.out.println("n=2 (formula): " + leastIntervalFormula(tasks, 2)); // 8

        char[] tasks2 = { 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'D' };
        System.out.println("n=3 (formula): " + leastIntervalFormula(tasks2, 3)); // 11
    }
}
```
