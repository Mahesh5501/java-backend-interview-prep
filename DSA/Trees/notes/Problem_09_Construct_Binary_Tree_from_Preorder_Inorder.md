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

---

---

## Repository Java (full)

Align with `../ConstructBinaryTreeFromPreorderInorder.java`.

```java
package DSA.Trees;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Problem: Construct Binary Tree from Preorder and Inorder Traversal (LeetCode 105)

preorder: root first, then left subtree, then right.
inorder: left subtree, root, right subtree.

Example:
    preorder = [3, 9, 20, 15, 7]
    inorder  = [9, 3, 15, 20, 7]
    Output tree matches level-order: [3, 9, 20, null, null, 15, 7]
*/

public class ConstructBinaryTreeFromPreorderInorder {

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        int[] preIndex = { 0 };
        return build(preorder, 0, inorder.length - 1, inorderIndex, preIndex);
    }

    private static TreeNode build(int[] preorder, int inStart, int inEnd,
            Map<Integer, Integer> inorderIndex, int[] preIndex) {

        if (inStart > inEnd) {
            return null;
        }

        int rootVal = preorder[preIndex[0]++];
        TreeNode root = new TreeNode(rootVal);

        int rootIndex = inorderIndex.get(rootVal);

        root.left = build(preorder, inStart, rootIndex - 1, inorderIndex, preIndex);
        root.right = build(preorder, rootIndex + 1, inEnd, inorderIndex, preIndex);

        return root;
    }

    public static void main(String[] args) {
        int[] preorder = { 3, 9, 20, 15, 7 };
        int[] inorder = { 9, 3, 15, 20, 7 };

        TreeNode root = buildTree(preorder, inorder);
        List<Integer> levelOrder = TreeNode.toLevelOrderList(root);
        System.out.println("Built tree (BFS): " + levelOrder);

        TreeNode p = TreeNode.buildLevelOrder(new Integer[] { 3, 9, 20, null, null, 15, 7 });
        System.out.println("Expected shape:   " + TreeNode.toLevelOrderList(p));
        System.out.println("Same structure: " + SameTree.isSameTree(root, p));
    }
}
```
