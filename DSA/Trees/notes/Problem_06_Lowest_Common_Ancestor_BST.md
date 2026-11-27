# Problem 06 — Lowest Common Ancestor of a BST

> **Pattern:** BST search walk
> **Goal:** Find LCA of two nodes in a BST.
> **LeetCode:** 235

---

## 1. Problem statement

Use BST ordering to go left/right or stop.

### Example 1

```text
p=2,q=8 in sample tree → 6
```

### Example 2

```text
p=2,q=4 → 2
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
If both sides of node, this node is LCA.
```

**Pattern D — BST navigation** from `Trees_Common_Patterns.md`

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

Iterative walk from root.

### Key method

`lowestCommonAncestor()` in `../LowestCommonAncestorBST.java`

### Dry run (summary)

```text
p and q on different sides of 6
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(h)` |
| **Space** | `O(1)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

While p,q not both in subtree side.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`lowestCommonAncestor()` in `../LowestCommonAncestorBST.java`

```java
public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    while (root != null) {
        if (p.val < root.val && q.val < root.val) root = root.left;
        else if (p.val > root.val && q.val > root.val) root = root.right;
        else return root;
    }
    return null;
}
```

### Visual walkthrough

```text
If both sides of node, this node is LCA.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

p,q distinct and present.

---

## 7. Detailed dry run

```text
Split at ancestor where paths diverge
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

BST property localizes search.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(h)` | `O(1)` |
| Optimal | `O(h)` | `O(1)` |

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
2. Confirm method names match `../LowestCommonAncestorBST.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> O(h) iterative.

### Thirty-second version

> Walk toward split point.

### Two-minute version

Brute is `lowestCommonAncestor()` at `O(h)`. I use `lowestCommonAncestor()` at `O(h)` because BST property localizes search.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(h)`?**  BST property localizes search.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(h)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../LowestCommonAncestorBST.java` (`package DSA.Trees`).

```bash
cd Trees
javac LowestCommonAncestorBST.java
java -cp . DSA.Trees.LowestCommonAncestorBST
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

- [ ] Implement `lowestCommonAncestor()` without hints.
- [ ] Implement `lowestCommonAncestor()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern D — BST navigation.

---

## 16. Quick revision

Pattern: **Pattern D — BST navigation**.
Brute: `O(h)` — `lowestCommonAncestor()`.
Optimal: `O(h)` — `lowestCommonAncestor()`.
Say the invariant before coding.

One line:

> **Walk toward split point.**

---

## 17. Pattern used

**Pattern D — BST navigation**

---

## 18. Next problem

**Problem 07 — Diameter of Binary Tree**
Continue in order through the folder.
