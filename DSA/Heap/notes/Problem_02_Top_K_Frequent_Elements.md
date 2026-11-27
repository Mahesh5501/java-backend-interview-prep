# Problem 02 — Top K Frequent Elements

> **Pattern:** Heap on frequency
> **Goal:** Return k most frequent integers.
> **LeetCode:** 347

---

## 1. Problem statement

May return any order (check spec).

### Example 1

```text
nums=[1,1,1,2,2,3], k=2 → [1,2]
```

### Example 2

```text
k equals unique count
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Count with map; min-heap of size k by freq.
```

**Pattern A — Top-K** from `Heap_Common_Patterns.md`

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

Bucket sort variant possible O(n).

### Key method

`topKFrequent()` in `../TopKFrequent.java`

### Dry run (summary)

```text
freq 1:3, 2:2, 3:1
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n log k)` |
| **Space** | `O(n)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Compare heap by frequency.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`topKFrequent()` in `../TopKFrequent.java`

```java
public static List<Integer> topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int x : nums) freq.merge(x, 1, Integer::sum);
    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(freq::get));
    for (int key : freq.keySet()) {
        pq.offer(key);
        if (pq.size() > k) pq.poll();
    }
    return new ArrayList<>(pq);
}
```

### Visual walkthrough

```text
Count with map; min-heap of size k by freq.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Comparator on freq not key.

---

## 7. Detailed dry run

```text
pq keeps keys with highest freq
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

Heap size k over unique keys.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n log k)` | `O(n)` |
| Optimal | `O(n log k)` | `O(n)` |

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
2. Confirm method names match `../TopKFrequent.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Bucket sort O(n) follow-up.

### Thirty-second version

> Count + min-heap size k.

### Two-minute version

Brute is `topKFrequent()` at `O(n log k)`. I use `topKFrequent()` at `O(n log k)` because Heap size k over unique keys.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n log k)`?**  Heap size k over unique keys.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n log k)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../TopKFrequent.java` (`package DSA.Heap`).

```bash
cd Heap
javac TopKFrequent.java
java -cp . DSA.Heap.TopKFrequent
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

- [ ] Implement `topKFrequent()` without hints.
- [ ] Implement `topKFrequent()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — Top-K.

---

## 16. Quick revision

Pattern: **Pattern A — Top-K**.
Brute: `O(n log k)` — `topKFrequent()`.
Optimal: `O(n log k)` — `topKFrequent()`.
Say the invariant before coding.

One line:

> **Count + min-heap size k.**

---

## 17. Pattern used

**Pattern A — Top-K**

---

## 18. Next problem

**Problem 03 — Merge K Sorted Lists**
Continue in order through the folder.
