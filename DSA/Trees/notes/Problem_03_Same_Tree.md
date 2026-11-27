# Problem 03 — Same Tree

> **Pattern:** Parallel DFS
> **Goal:** Check structural equality.
> **LeetCode:** 100

---

## 1. Problem statement

Two trees same if nodes and children match.

### Example 1

```text
[1,2,3] vs [1,2,3] → true
```

### Example 2

```text
vs skewed → false
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Both null OK; one null fail; values match and recurse.
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

DFS compare.

### Key method

`isSameTree()` in `../SameTree.java`

### Dry run (summary)

```text
mismatch at first different val
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

Short-circuit on first false.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`isSameTree()` in `../SameTree.java`

```java
public static boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;
    return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}
```

### Visual walkthrough

```text
Both null OK; one null fail; values match and recurse.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Handles null children.

---

## 7. Detailed dry run

```text
identical preorder traversal
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

Structural + value equality.

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
2. Confirm method names match `../SameTree.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Mirror of invert check.

### Thirty-second version

> Null guards + val + recurse.

### Two-minute version

Brute is `isSameTree()` at `O(n)`. I use `isSameTree()` at `O(n)` because Structural + value equality.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Structural + value equality.

**Why space `O(h)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../SameTree.java` (`package DSA.Trees`).

```bash
cd Trees
javac SameTree.java
java -cp . DSA.Trees.SameTree
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

- [ ] Implement `isSameTree()` without hints.
- [ ] Implement `isSameTree()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern A — DFS.

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

Pattern: **Pattern A — DFS**.
Brute: `O(n)` — `isSameTree()`.
Optimal: `O(n)` — `isSameTree()`.
Say the invariant before coding.

One line:

> **Null guards + val + recurse.**

---

## 17. Pattern used

**Pattern A — DFS**

---

## 18. Next problem

**Problem 04 — Level Order Traversal**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../SameTree.java`.

```java
package DSA.Trees;

/*
Problem: Same Tree (LeetCode 100)

Two trees are the same if structure and node values match everywhere.

Example:
    p = [1, 2, 3], q = [1, 2, 3]  -> true
    p = [1, 2],    q = [1, null, 2] -> false
*/

public class SameTree {

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public static void main(String[] args) {
        TreeNode p = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3 });
        TreeNode q = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3 });
        System.out.println("Same structure: " + isSameTree(p, q)); // true

        TreeNode p2 = TreeNode.buildLevelOrder(new Integer[] { 1, 2 });
        TreeNode q2 = TreeNode.buildLevelOrder(new Integer[] { 1, null, 2 });
        System.out.println("Different shape: " + isSameTree(p2, q2)); // false
    }
}
```
