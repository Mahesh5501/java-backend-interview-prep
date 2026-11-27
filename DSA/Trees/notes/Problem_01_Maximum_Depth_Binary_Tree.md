# Problem 01 — Maximum Depth of Binary Tree

> **Pattern:** DFS depth
> **Goal:** Return max root-to-leaf depth.
> **LeetCode:** 104

---

## 1. Problem statement

Depth = number of nodes along longest path.

### Example 1

```text
[3,9,20,null,null,15,7] → depth 3
```

### Example 2

```text
[] → 0
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
1 + max(left depth, right depth).
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

Same optimal DFS.

### Key method

`maxDepthDFS()` in `../MaximumDepthBinaryTree.java`

### Dry run (summary)

```text
Leaf returns 1
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

Recursive: base null=0.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`maxDepthDFS()` in `../MaximumDepthBinaryTree.java`

```java
public static int maxDepthDFS(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepthDFS(root.left), maxDepthDFS(root.right));
}
```

### Visual walkthrough

```text
1 + max(left depth, right depth).
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Empty tree 0; single node 1.

---

## 7. Detailed dry run

```text
height at root = 1 + max(children)
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

Every node counted once.

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
2. Confirm method names match `../MaximumDepthBinaryTree.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Classic post-order DFS aggregate.

### Thirty-second version

> 1 + max(left,right).

### Two-minute version

Brute is `maxDepthDFS()` at `O(n)`. I use `maxDepthDFS()` at `O(n)` because Every node counted once.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Every node counted once.

**Why space `O(h)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../MaximumDepthBinaryTree.java` (`package DSA.Trees`).

```bash
cd Trees
javac MaximumDepthBinaryTree.java
java -cp . DSA.Trees.MaximumDepthBinaryTree
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

- [ ] Implement `maxDepthDFS()` without hints.
- [ ] Implement `maxDepthDFS()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — DFS.

---

## 16. Quick revision

Pattern: **Pattern A — DFS**.
Brute: `O(n)` — `maxDepthDFS()`.
Optimal: `O(n)` — `maxDepthDFS()`.
Say the invariant before coding.

One line:

> **1 + max(left,right).**

---

## 17. Pattern used

**Pattern A — DFS**

---

## 18. Next problem

**Problem 02 — Invert Binary Tree**
Continue in order through the folder.
