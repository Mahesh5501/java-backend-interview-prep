# Problem 07 — Diameter of Binary Tree

> **Pattern:** DFS height + global max
> **Goal:** Longest path between any two nodes (edges count).
> **LeetCode:** 543

---

## 1. Problem statement

Path may not pass through root.

### Example 1

```text
skew line of 3 nodes → diameter 2
```

### Example 2

```text
balanced tree → through root
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
At each node, diameter candidate = leftH + rightH.
```

**Pattern A — DFS aggregate** from `Trees_Common_Patterns.md`

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

Post-order height returns; update best.

### Key method

`diameterOfBinaryTree()` in `../DiameterOfBinaryTree.java`

### Dry run (summary)

```text
height fn returns depth, side effect on max
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

Global max updated during height DFS.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`diameterOfBinaryTree()` in `../DiameterOfBinaryTree.java`

```java
public static int diameterOfBinaryTree(TreeNode root) {
    return depth(root);
}
private static int depth(TreeNode n) {
    if (n == null) return 0;
    int L = depth(n.left), R = depth(n.right);
    best = Math.max(best, L + R);
    return 1 + Math.max(L, R);
}
```

### Visual walkthrough

```text
At each node, diameter candidate = leftH + rightH.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Edges vs nodes — problem uses edges.

---

## 7. Detailed dry run

```text
best = max path through current
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

Each node computes cross path once.

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
2. Confirm method names match `../DiameterOfBinaryTree.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Height DFS + global.

### Thirty-second version

> leftHeight+rightHeight at each node.

### Two-minute version

Brute is `diameterOfBinaryTree()` at `O(n)`. I use `diameterOfBinaryTree()` at `O(n)` because Each node computes cross path once.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Each node computes cross path once.

**Why space `O(h)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../DiameterOfBinaryTree.java` (`package DSA.Trees`).

```bash
cd Trees
javac DiameterOfBinaryTree.java
java -cp . DSA.Trees.DiameterOfBinaryTree
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

- [ ] Implement `diameterOfBinaryTree()` without hints.
- [ ] Implement `diameterOfBinaryTree()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — DFS aggregate.

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

Pattern: **Pattern A — DFS aggregate**.
Brute: `O(n)` — `diameterOfBinaryTree()`.
Optimal: `O(n)` — `diameterOfBinaryTree()`.
Say the invariant before coding.

One line:

> **leftHeight+rightHeight at each node.**

---

## 17. Pattern used

**Pattern A — DFS aggregate**

---

## 18. Next problem

**Problem 08 — Binary Tree Right Side View**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../DiameterOfBinaryTree.java`.

```java
package DSA.Trees;

/*
Problem: Diameter of Binary Tree (LeetCode 543)

The diameter is the length of the longest path between any two nodes
(edge count). The path may or may not pass through the root.

Example:
    [1, 2, 3, 4, 5] -> diameter = 3 (path 4-2-1-3 or 5-2-1-3)
*/

public class DiameterOfBinaryTree {

    private static int bestDiameter;

    public static int diameterOfBinaryTree(TreeNode root) {
        bestDiameter = 0;
        height(root);
        return bestDiameter;
    }

    /** Returns height in edges; updates global best diameter through this node. */
    private static int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = height(node.left);
        int right = height(node.right);
        bestDiameter = Math.max(bestDiameter, left + right);
        return 1 + Math.max(left, right);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3, 4, 5 });
        System.out.println("Diameter: " + diameterOfBinaryTree(root)); // 3

        TreeNode line = TreeNode.buildLevelOrder(new Integer[] { 1, 2, null, 3, null, 4 });
        System.out.println("Skewed diameter: " + diameterOfBinaryTree(line)); // 3
    }
}
```
