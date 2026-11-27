# Problem 04 — Binary Tree Level Order Traversal

> **Pattern:** BFS with queue
> **Goal:** Return values grouped by depth level.
> **LeetCode:** 102

---

## 1. Problem statement

Left-to-right levels in nested lists.

### Example 1

```text
[3,9,20,null,null,15,7] → [[3],[9,20],[15,7]]
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
Queue processes level size batches.
```

**Pattern B — BFS** from `Trees_Common_Patterns.md`

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

BFS is standard.

### Key method

`levelOrder()` in `../LevelOrderTraversal.java`

### Dry run (summary)

```text
layer sizes from queue snapshot
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

For i in levelSize poll children.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`levelOrder()` in `../LevelOrderTraversal.java`

```java
public static List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new ArrayDeque<>();
    q.offer(root);
    while (!q.isEmpty()) {
        int size = q.size();
        List<Integer> level = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = q.poll();
            level.add(node.val);
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        res.add(level);
    }
    return res;
}
```

### Visual walkthrough

```text
Queue processes level size batches.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Snapshot q.size() before loop.

---

## 7. Detailed dry run

```text
size=2 at level with 9 and 20
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

Each node enqueued once.

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
2. Confirm method names match `../LevelOrderTraversal.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> BFS template for many tree problems.

### Thirty-second version

> Queue + level size loop.

### Two-minute version

Brute is `levelOrder()` at `O(n)`. I use `levelOrder()` at `O(n)` because Each node enqueued once.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Each node enqueued once.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../LevelOrderTraversal.java` (`package DSA.Trees`).

```bash
cd Trees
javac LevelOrderTraversal.java
java -cp . DSA.Trees.LevelOrderTraversal
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

- [ ] Implement `levelOrder()` without hints.
- [ ] Implement `levelOrder()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern B — BFS.

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

Pattern: **Pattern B — BFS**.
Brute: `O(n)` — `levelOrder()`.
Optimal: `O(n)` — `levelOrder()`.
Say the invariant before coding.

One line:

> **Queue + level size loop.**

---

## 17. Pattern used

**Pattern B — BFS**

---

## 18. Next problem

**Problem 05 — Validate BST**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../LevelOrderTraversal.java`.

```java
package DSA.Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
Problem: Binary Tree Level Order Traversal (LeetCode 102)

Return values level by level, left to right.

Example:
    [3, 9, 20, null, null, 15, 7] -> [[3], [9, 20], [15, 7]]
*/

public class LevelOrderTraversal {

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 3, 9, 20, null, null, 15, 7 });
        System.out.println("Level order: " + levelOrder(root));

        System.out.println("Empty tree levels: " + levelOrder(null));
    }
}
```
