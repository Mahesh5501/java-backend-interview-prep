# Problem 04 — Next Greater Element

> **Pattern:** Monotonic stack + hash map
> **Goal:** Map each nums1 element to next greater in nums2.
> **LeetCode:** 496

---

## 1. Problem statement

For each element in nums1, find the next greater element in nums2 to its right.

### Example 1

```text
nums1 = [4,1,2], nums2 = [1,3,4,2]
Output: [-1,3,-1]
```

### Example 2

```text
nums2 = [2,4] → next greater for 2 is -1
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Preprocess nums2 with decreasing stack → O(1) lookups.
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

For each nums1 value, locate in nums2 and scan right.

### Key method

`bruteForce()` in `../NextGreaterElement.java`

### Dry run (summary)

```text
4 in nums2 at index 2 → no greater to right → -1
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n·m)` |
| **Space** | `O(1)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Build map value→NGE on nums2 via right-to-left stack.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`nextGreaterElement()` in `../NextGreaterElement.java`

```java
public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
    Map<Integer, Integer> map = new HashMap<>();
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = nums2.length - 1; i >= 0; i--) {
        int v = nums2[i];
        while (!stack.isEmpty() && stack.peek() <= v) stack.pop();
        map.put(v, stack.isEmpty() ? -1 : stack.peek());
        stack.push(v);
    }
    int[] ans = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) ans[i] = map.get(nums1[i]);
    return ans;
}
```

### Visual walkthrough

```text
Preprocess nums2 with decreasing stack → O(1) lookups.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Use strictly greater (pop <=) to skip equal values.

---

## 7. Detailed dry run

```text
nums2 [1,3,4,2]: map 3→4, 1→3 from stack
```

---

## 7b. Extra variants in the Java file

- nextGreaterForArray

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

Stack ensures first greater to right per value when scanning backward.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n·m)` | `O(1)` |
| Optimal | `O(n+m)` | `O(m)` |

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
2. Confirm method names match `../NextGreaterElement.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Preprocess nums2 once; answer queries via hash map.

### Thirty-second version

> Decreasing stack on nums2 → map lookup.

### Two-minute version

Brute is `bruteForce()` at `O(n·m)`. I use `nextGreaterElement()` at `O(n+m)` because Stack ensures first greater to right per value when scanning backward.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n+m)`?**  Stack ensures first greater to right per value when scanning backward.

**Why space `O(m)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n+m)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../NextGreaterElement.java` (`package DSA.Stack`).

```bash
cd Stack
javac NextGreaterElement.java
java -cp . DSA.Stack.NextGreaterElement
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
- [ ] Implement `nextGreaterElement()` without hints.
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
Brute: `O(n·m)` — `bruteForce()`.
Optimal: `O(n+m)` — `nextGreaterElement()`.
Say the invariant before coding.

One line:

> **Decreasing stack on nums2 → map lookup.**

---

## 17. Pattern used

**Pattern B — Monotonic stack**

---

## 18. Next problem

**Problem 05 — Largest Rectangle in Histogram**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../NextGreaterElement.java`.

```java
package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 496 — Next Greater Element I
 * Pattern: Monotonic decreasing stack + hash map for nums2 lookup
 */
public class NextGreaterElement {

  // APPROACH 1: BRUTE FORCE — for each num in nums1, find it in nums2 then scan right
  // Time: O(n*m), Space: O(1)
  public static int[] bruteForce(int[] nums1, int[] nums2) {
    int[] answer = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
      int target = nums1[i];
      int idx = -1;
      for (int j = 0; j < nums2.length; j++) {
        if (nums2[j] == target) {
          idx = j;
          break;
        }
      }
      int nextGreater = -1;
      for (int j = idx + 1; j < nums2.length; j++) {
        if (nums2[j] > target) {
          nextGreater = nums2[j];
          break;
        }
      }
      answer[i] = nextGreater;
    }
    return answer;
  }

  // APPROACH 2: MONOTONIC STACK on nums2, then map value -> next greater
  // Time: O(n + m), Space: O(m)
  public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
    Map<Integer, Integer> nextGreaterByValue = new HashMap<>();
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = nums2.length - 1; i >= 0; i--) {
      int value = nums2[i];
      while (!stack.isEmpty() && stack.peek() <= value) {
        stack.pop();
      }
      nextGreaterByValue.put(value, stack.isEmpty() ? -1 : stack.peek());
      stack.push(value);
    }

    int[] answer = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
      answer[i] = nextGreaterByValue.get(nums1[i]);
    }
    return answer;
  }

  // Variant: next greater for every index in a single array (no map)
  public static int[] nextGreaterForArray(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && stack.peek() <= nums[i]) {
        stack.pop();
      }
      result[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(nums[i]);
    }
    return result;
  }

  /**
   * Dry run on nums2 = [1,3,4,2] right to left:
   * i=3 val 2: stack [] -> map 2->-1, push 2
   * i=2 val 4: pop 2 -> map 4->-1, push 4
   * i=1 val 3: top 4>3 -> map 3->4, push 3
   */
  public static void main(String[] args) {
    int[] nums1 = {4, 1, 2};
    int[] nums2 = {1, 3, 4, 2};

    System.out.println("nums1: " + Arrays.toString(nums1));
    System.out.println("nums2: " + Arrays.toString(nums2));
    System.out.println("Brute:  " + Arrays.toString(bruteForce(nums1, nums2)));
    System.out.println("Stack:  " + Arrays.toString(nextGreaterElement(nums1, nums2)));

    System.out.println();
    System.out.println(
        "Single array NGE: "
            + Arrays.toString(nextGreaterForArray(new int[] {4, 5, 2, 10, 8})));

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "I preprocess nums2 with a decreasing monotonic stack to record next greater per value.");
    System.out.println("Then answer nums1 in O(1) per element via the map. Overall O(n+m).");
  }
}
```
