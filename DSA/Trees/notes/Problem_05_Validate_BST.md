# Problem 05 — Validate Binary Search Tree

> **Pattern:** Inorder or min/max bounds
> **Goal:** Verify BST ordering property.
> **LeetCode:** 98

---

## 1. Problem statement

Left < node < right for entire subtrees.

### Example 1

```text
[2,1,3] → true
```

### Example 2

```text
[5,1,4,null,null,3,4] → false
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Pass down (min,max) allowed range per node.
```

**Pattern C — BST bounds** from `Trees_Common_Patterns.md`

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

DFS with bounds.

### Key method

`isValidBST()` in `../ValidateBST.java`

### Dry run (summary)

```text
node 4 with max 3 fails
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

Long bounds use Long.MIN/MAX.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`isValidBST()` in `../ValidateBST.java`

```java
public static boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}
private static boolean validate(TreeNode n, long lo, long hi) {
    if (n == null) return true;
    if (n.val <= lo || n.val >= hi) return false;
    return validate(n.left, lo, n.val) && validate(n.right, n.val, hi);
}
```

### Visual walkthrough

```text
Pass down (min,max) allowed range per node.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Integer.MIN_VALUE edge duplicates need long.

---

## 7. Detailed dry run

```text
Strict inequalities required
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

Range shrinks as we descend.

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
2. Confirm method names match `../ValidateBST.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> BST validate template.

### Thirty-second version

> Bounds DFS.

### Two-minute version

Brute is `isValidBST()` at `O(n)`. I use `isValidBST()` at `O(n)` because Range shrinks as we descend.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Range shrinks as we descend.

**Why space `O(h)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../ValidateBST.java` (`package DSA.Trees`).

```bash
cd Trees
javac ValidateBST.java
java -cp . DSA.Trees.ValidateBST
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

- [ ] Implement `isValidBST()` without hints.
- [ ] Implement `isValidBST()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern C — BST bounds.

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

Pattern: **Pattern C — BST bounds**.
Brute: `O(n)` — `isValidBST()`.
Optimal: `O(n)` — `isValidBST()`.
Say the invariant before coding.

One line:

> **Bounds DFS.**

---

## 17. Pattern used

**Pattern C — BST bounds**

---

## 18. Next problem

**Problem 06 — Lowest Common Ancestor (BST)**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../ValidateBST.java`.

```java
package DSA.Trees;

/*
Problem: Validate Binary Search Tree (LeetCode 98)

A BST: all nodes in left subtree < root < all nodes in right subtree.

Example:
    [2, 1, 3] -> true
    [5, 1, 4, null, null, 3, 6] -> false (4 with 3 left-child violates BST)
*/

public class ValidateBST {

    public static boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean validate(TreeNode node, long minAllowed, long maxAllowed) {
        if (node == null) {
            return true;
        }
        if (node.val <= minAllowed || node.val >= maxAllowed) {
            return false;
        }
        boolean leftOk = validate(node.left, minAllowed, node.val);
        boolean rightOk = validate(node.right, node.val, maxAllowed);
        return leftOk && rightOk;
    }

    public static void main(String[] args) {
        TreeNode valid = TreeNode.buildLevelOrder(new Integer[] { 2, 1, 3 });
        System.out.println("[2,1,3] is BST: " + isValidBST(valid)); // true

        TreeNode invalid = TreeNode.buildLevelOrder(new Integer[] { 5, 1, 4, null, null, 3, 6 });
        System.out.println("[5,1,4,null,null,3,6] is BST: " + isValidBST(invalid)); // false
    }
}
```
