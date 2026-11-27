# Problem 09 — Construct Binary Tree from Preorder and Inorder

> **Pattern:** Divide by inorder root index
> **Goal:** Reconstruct tree from traversals.
> **LeetCode:** 105

---

## 1. Problem statement

Preorder gives root order; inorder splits L/R.

### Example 1

```text
pre=[3,9,20,15,7], in=[9,3,15,20,7]
```

### Example 2

```text
Single node
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Map inorder indices; recurse on ranges.
```

**Pattern E — Build tree** from `Trees_Common_Patterns.md`

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

Hash map + recursive partition.

### Key method

`buildTree()` in `../ConstructBinaryTreeFromPreorderInorder.java`

### Dry run (summary)

```text
root=pre[0]; split inorder
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(n)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Copy subranges or pass indices.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`buildTree()` in `../ConstructBinaryTreeFromPreorderInorder.java`

```java
public static TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> idx = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) idx.put(inorder[i], i);
    return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, idx);
}
```

### Visual walkthrough

```text
Map inorder indices; recurse on ranges.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Values must be unique (typical constraint).

---

## 7. Detailed dry run

```text
left subtree sizes from inorder root position
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

Unique mapping locates root in O(1).

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n)` | `O(n)` |
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
2. Confirm method names match `../ConstructBinaryTreeFromPreorderInorder.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Classic recursion template.

### Thirty-second version

> pre root + inorder split.

### Two-minute version

Brute is `buildTree()` at `O(n)`. I use `buildTree()` at `O(n)` because Unique mapping locates root in O(1).

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Unique mapping locates root in O(1).

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../ConstructBinaryTreeFromPreorderInorder.java` (`package DSA.Trees`).

```bash
cd Trees
javac ConstructBinaryTreeFromPreorderInorder.java
java -cp . DSA.Trees.ConstructBinaryTreeFromPreorderInorder
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

- [ ] Implement `buildTree()` without hints.
- [ ] Implement `buildTree()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern E — Build tree.

---

## 16. Quick revision

Pattern: **Pattern E — Build tree**.
Brute: `O(n)` — `buildTree()`.
Optimal: `O(n)` — `buildTree()`.
Say the invariant before coding.

One line:

> **pre root + inorder split.**

---

## 17. Pattern used

**Pattern E — Build tree**

---

## 18. Next problem

**Problem 10 — Kth Smallest Element in a BST**
Continue in order through the folder.
