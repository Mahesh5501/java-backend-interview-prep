# Problem 08 — Binary Tree Right Side View

> **Java file:** `../RightSideView.java`

> **Pattern:** BFS last of level / DFS right first
> **Goal:** Visible nodes from the right.
> **LeetCode:** 199

---

## 1. Problem statement

Return rightmost node at each depth.

### Example 1

```text
[1,2,3,null,5] → [1,3,5]
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
BFS: last element each level; or DFS visit right before left.
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

DFS right child before left; last write per depth wins.

### Key method

`rightSideViewBruteDFS()` in `../RightSideView.java`

### Dry run (summary)

```text
level [2,3] take 3
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

DFS records depth on first right visit.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`rightSideView()` in `../RightSideView.java`

```java
public static List<Integer> rightSideView(TreeNode root) {
    List<Integer> view = new ArrayList<>();
    dfs(root, 0, view);
    return view;
}
private static void dfs(TreeNode n, int d, List<Integer> view) {
    if (n == null) return;
    if (d == view.size()) view.add(n.val);
    dfs(n.right, d + 1, view);
    dfs(n.left, d + 1, view);
}
```

### Visual walkthrough

```text
BFS: last element each level; or DFS visit right before left.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Only rightmost stored per depth.

---

## 7. Detailed dry run

```text
depth index equals list size → first time at depth
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

Right-first DFS captures view.

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
2. Confirm method names match `../RightSideView.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> BFS last-in-level equivalent.

### Thirty-second version

> DFS right then left.

### Two-minute version

Brute is `rightSideView()` at `O(n)`. I use `rightSideView()` at `O(n)` because Right-first DFS captures view.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Right-first DFS captures view.

**Why space `O(n)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## Link to Java — which method is which

| Approach | Method in `RightSideView` |
|---|---|
| DFS right-first (brute) | `rightSideViewBruteDFS` |
| BFS last per level (optimal) | `rightSideView` |

Run `main` to print all approaches on sample inputs.

---

## 14. Complete runnable Java

Full source: `../RightSideView.java` (`package DSA.Trees`).

```bash
cd Trees
javac RightSideView.java
java -cp . DSA.Trees.RightSideView
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

- [ ] Implement `rightSideView()` without hints.
- [ ] Implement `rightSideView()` without hints.
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
Brute: `O(n)` — `rightSideView()`.
Optimal: `O(n)` — `rightSideView()`.
Say the invariant before coding.

One line:

> **DFS right then left.**

---

## 17. Pattern used

**Pattern B — BFS**

---

## 18. Next problem

**Problem 09 — Construct Binary Tree from Preorder and Inorder**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../RightSideView.java`.

```java
package DSA.Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
Problem: Binary Tree Right Side View (LeetCode 199)

Return values visible when looking from the right
(rightmost node at each level).

Example:
    [1, 2, 3, null, 5, null, 4] -> [1, 3, 4]
*/

public class RightSideView {

    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> view = new ArrayList<>();
        if (root == null) {
            return view;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (i == levelSize - 1) {
                    view.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return view;
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3, null, 5, null, 4 });
        System.out.println("Right side view: " + rightSideView(root)); // [1, 3, 4]

        TreeNode single = new TreeNode(7);
        System.out.println("Single node: " + rightSideView(single)); // [7]
    }
}
```