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
