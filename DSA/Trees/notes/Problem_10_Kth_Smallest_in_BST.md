# Problem 10 — Kth Smallest Element in a BST

> **Pattern:** Inorder traversal
> **Goal:** Return k-th smallest value.
> **LeetCode:** 230

---

## 1. Problem statement

Inorder of BST is sorted.

### Example 1

```text
k=1 → smallest
```

### Example 2

```text
k=3 → third smallest
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Iterative stack inorder until k counts.
```

**Pattern F — BST inorder** from `Trees_Common_Patterns.md`

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

Inorder simulation.

### Key method

`kthSmallest()` in `../KthSmallestInBST.java`

### Dry run (summary)

```text
increment counter on pop
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(h+k)` |
| **Space** | `O(h)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Early exit when counter==k.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`kthSmallest()` in `../KthSmallestInBST.java`

```java
public static int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> st = new ArrayDeque<>();
    TreeNode cur = root;
    while (cur != null || !st.isEmpty()) {
        while (cur != null) { st.push(cur); cur = cur.left; }
        cur = st.pop();
        if (--k == 0) return cur.val;
        cur = cur.right;
    }
    throw new IllegalArgumentException();
}
```

### Visual walkthrough

```text
Iterative stack inorder until k counts.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

k valid 1..n.

---

## 7. Detailed dry run

```text
3rd pop gives answer
```

---

## 7b. Extra variants in the Java file

- kthSmallestRecursive

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

BST inorder sorted property.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(h+k)` | `O(h)` |
| Optimal | `O(h+k)` | `O(h)` |

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
2. Confirm method names match `../KthSmallestInBST.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Iterative inorder preferred.

### Thirty-second version

> Inorder stop at k.

### Two-minute version

Brute is `kthSmallest()` at `O(h+k)`. I use `kthSmallest()` at `O(h+k)` because BST inorder sorted property.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(h+k)`?**  BST inorder sorted property.

**Why space `O(h)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(h+k)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../KthSmallestInBST.java` (`package DSA.Trees`).

```bash
cd Trees
javac KthSmallestInBST.java
java -cp . DSA.Trees.KthSmallestInBST
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

- [ ] Implement `kthSmallest()` without hints.
- [ ] Implement `kthSmallest()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern F — BST inorder.

---

## 16. Quick revision

Pattern: **Pattern F — BST inorder**.
Brute: `O(h+k)` — `kthSmallest()`.
Optimal: `O(h+k)` — `kthSmallest()`.
Say the invariant before coding.

One line:

> **Inorder stop at k.**

---

## 17. Pattern used

**Pattern F — BST inorder**

---

## 18. Next problem

**Problem 11 — Trees track complete**
Continue in order through the folder.
