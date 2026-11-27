# Problem 02 — Invert Binary Tree

> **Pattern:** Swap children DFS/BFS
> **Goal:** Mirror the tree.
> **LeetCode:** 226

---

## 1. Problem statement

Swap left and right subtrees at every node.

### Example 1

```text
[4,2,7,1,3,6,9] → mirrored
```

### Example 2

```text
[] → []
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Preorder swap then recurse children.
```

**Pattern A — DFS** from `Trees_Common_Patterns.md`

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

Recursive swap — also brute equivalent.

### Key method

`invertTree()` in `../InvertBinaryTree.java`

### Dry run (summary)

```text
swap at root then children
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(h)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Visit each node once swapping kids.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`invertTree()` in `../InvertBinaryTree.java`

```java
public static TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode tmp = root.left;
    root.left = invertTree(root.right);
    root.right = invertTree(tmp);
    return root;
}
```

### Visual walkthrough

```text
Preorder swap then recurse children.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Return null for null.

---

## 7. Detailed dry run

```text
Children swapped bottom-up
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

Bijection between original and inverted.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n)` | `O(h)` |
| Optimal | `O(n)` | `O(h)` |

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
2. Confirm method names match `../InvertBinaryTree.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Swap then recurse.

### Thirty-second version

> Swap subtrees recursively.

### Two-minute version

Brute is `invertTree()` at `O(n)`. I use `invertTree()` at `O(n)` because Bijection between original and inverted.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Bijection between original and inverted.

**Why space `O(h)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../InvertBinaryTree.java` (`package DSA.Trees`).

```bash
cd Trees
javac InvertBinaryTree.java
java -cp . DSA.Trees.InvertBinaryTree
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

- [ ] Implement `invertTree()` without hints.
- [ ] Implement `invertTree()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — DFS.

---

## 16. Quick revision

Pattern: **Pattern A — DFS**.
Brute: `O(n)` — `invertTree()`.
Optimal: `O(n)` — `invertTree()`.
Say the invariant before coding.

One line:

> **Swap subtrees recursively.**

---

## 17. Pattern used

**Pattern A — DFS**

---

## 18. Next problem

**Problem 03 — Same Tree**
Continue in order through the folder.
