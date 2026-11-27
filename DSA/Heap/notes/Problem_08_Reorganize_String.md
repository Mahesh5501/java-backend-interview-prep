# Problem 08 — Reorganize String

> **Pattern:** Max-heap + cooldown queue
> **Goal:** Rearrange so no equal neighbors.
> **LeetCode:** 767

---

## 1. Problem statement

Return any valid string or "".

### Example 1

```text
aab → aba
```

### Example 2

```text
aaab → empty string
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Place most frequent chars with gap 1.
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

Heap by freq; delay queue for cooling.

### Key method

`reorganizeString()` in `../ReorganizeString.java`

### Dry run (summary)

```text
top freq can't touch until after others
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n log 26)` |
| **Space** | `O(1)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Max freq > (n+1)/2 impossible.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`reorganizeString()` in `../ReorganizeString.java`

```java
public static String reorganizeString(String s) {
    int[] f = new int[26];
    for (char c : s.toCharArray()) f[c - 'a']++;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->b[1]-a[1]);
    for (int i = 0; i < 26; i++) if (f[i] > 0) pq.offer(new int[]{i, f[i]});
    StringBuilder ans = new StringBuilder();
    Queue<int[]> cool = new ArrayDeque<>();
    while (!pq.isEmpty()) {
        int[] cur = pq.poll();
        ans.append((char)('a'+cur[0]));
        cool.offer(new int[]{cur[0], cur[1]-1});
        if (ans.length() > 1) {
            int[] prev = cool.poll();
            if (prev[1] > 0) pq.offer(prev);
        }
    }
    return ans.length() == s.length() ? ans.toString() : "";
}
```

### Visual walkthrough

```text
Place most frequent chars with gap 1.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Similar to task scheduler with gap 1.

---

## 7. Detailed dry run

```text
impossible when one char dominates
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

Greedy max freq valid iff possible.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n log 26)` | `O(1)` |
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
2. Confirm method names match `../ReorganizeString.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Heap + cooldown queue.

### Thirty-second version

> Max freq heap + wait queue.

### Two-minute version

Brute is `reorganizeString()` at `O(n log 26)`. I use `reorganizeString()` at `O(n)` because Greedy max freq valid iff possible.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Greedy max freq valid iff possible.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../ReorganizeString.java` (`package DSA.Heap`).

```bash
cd Heap
javac ReorganizeString.java
java -cp . DSA.Heap.ReorganizeString
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

- [ ] Implement `reorganizeString()` without hints.
- [ ] Implement `reorganizeString()` without hints.
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
Brute: `O(n log 26)` — `reorganizeString()`.
Optimal: `O(n)` — `reorganizeString()`.
Say the invariant before coding.

One line:

> **Max freq heap + wait queue.**

---

## 17. Pattern used

**Pattern D — Scheduling**

---

## 18. Next problem

**Problem 09 — Heap track complete**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../ReorganizeString.java`.

```java
package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: Reorganize String (LeetCode 767)

Rearrange so no two adjacent letters are the same.
Return any valid string or "" if impossible.

Example:
    "aab" -> "aba"
    "aaab" -> ""
*/

public class ReorganizeString {

    static class LetterCount {
        char letter;
        int count;

        LetterCount(char letter, int count) {
            this.letter = letter;
            this.count = count;
        }
    }

    public static String reorganizeString(String s) {
        int[] frequency = new int[26];
        for (char ch : s.toCharArray()) {
            frequency[ch - 'a']++;
        }

        int maxCount = 0;
        for (int count : frequency) {
            maxCount = Math.max(maxCount, count);
        }
        if (maxCount > (s.length() + 1) / 2) {
            return "";
        }

        PriorityQueue<LetterCount> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.count, a.count));

        for (int i = 0; i < 26; i++) {
            if (frequency[i] > 0) {
                maxHeap.offer(new LetterCount((char) ('a' + i), frequency[i]));
            }
        }

        StringBuilder result = new StringBuilder();

        while (!maxHeap.isEmpty()) {
            LetterCount first = maxHeap.poll();
            result.append(first.letter);

            if (!maxHeap.isEmpty()) {
                LetterCount second = maxHeap.poll();
                result.append(second.letter);
                second.count--;
                if (second.count > 0) {
                    maxHeap.offer(second);
                }
            }

            first.count--;
            if (first.count > 0) {
                maxHeap.offer(first);
            }
        }

        return result.length() == s.length() ? result.toString() : "";
    }

    public static void main(String[] args) {
        String[] samples = { "aab", "aaab", "vvvlo", "aabbcc" };
        for (String sample : samples) {
            String answer = reorganizeString(sample);
            System.out.println("Input: " + sample + " -> " + answer
                    + " (valid: " + isValidReorganization(sample, answer) + ")");
        }
    }

    private static boolean isValidReorganization(String original, String result) {
        if (result.isEmpty() && !original.isEmpty() && original.chars().distinct().count() == 1) {
            return true;
        }
        if (result.length() != original.length()) {
            return result.isEmpty();
        }
        for (int i = 1; i < result.length(); i++) {
            if (result.charAt(i) == result.charAt(i - 1)) {
                return false;
            }
        }
        char[] a = original.toCharArray();
        char[] b = result.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }
}
```
