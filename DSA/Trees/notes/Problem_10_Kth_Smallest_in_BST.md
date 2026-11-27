# Problem 10 — Kth Smallest Element in a BST

> **Pattern:** Inorder traversal
> **Goal:** Return k-th smallest value.
> **LeetCode:** 230

---

## 1. Problem statement

Inorder of BST is sorted.

### Example 1

```text
k=1 → smallest
```

### Example 2

```text
k=3 → third smallest
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Iterative stack inorder until k counts.
```

**Pattern F — BST inorder** from `Trees_Common_Patterns.md`

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

Inorder simulation.

### Key method

`kthSmallest()` in `../KthSmallestInBST.java`

### Dry run (summary)

```text
increment counter on pop
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(h+k)` |
| **Space** | `O(h)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Early exit when counter==k.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`kthSmallest()` in `../KthSmallestInBST.java`

```java
public static int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> st = new ArrayDeque<>();
    TreeNode cur = root;
    while (cur != null || !st.isEmpty()) {
        while (cur != null) { st.push(cur); cur = cur.left; }
        cur = st.pop();
        if (--k == 0) return cur.val;
        cur = cur.right;
    }
    throw new IllegalArgumentException();
}
```

### Visual walkthrough

```text
Iterative stack inorder until k counts.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

k valid 1..n.

---

## 7. Detailed dry run

```text
3rd pop gives answer
```

---

## 7b. Extra variants in the Java file

- kthSmallestRecursive

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

BST inorder sorted property.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(h+k)` | `O(h)` |
| Optimal | `O(h+k)` | `O(h)` |

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
2. Confirm method names match `../KthSmallestInBST.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Iterative inorder preferred.

### Thirty-second version

> Inorder stop at k.

### Two-minute version

Brute is `kthSmallest()` at `O(h+k)`. I use `kthSmallest()` at `O(h+k)` because BST inorder sorted property.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(h+k)`?**  BST inorder sorted property.

**Why space `O(h)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(h+k)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../KthSmallestInBST.java` (`package DSA.Trees`).

```bash
cd Trees
javac KthSmallestInBST.java
java -cp . DSA.Trees.KthSmallestInBST
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

- [ ] Implement `kthSmallest()` without hints.
- [ ] Implement `kthSmallest()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern F — BST inorder.

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

Pattern: **Pattern F — BST inorder**.
Brute: `O(h+k)` — `kthSmallest()`.
Optimal: `O(h+k)` — `kthSmallest()`.
Say the invariant before coding.

One line:

> **Inorder stop at k.**

---

## 17. Pattern used

**Pattern F — BST inorder**

---

## 18. Next problem

**Problem 11 — Trees track complete**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../KthSmallestInBST.java`.

```java
package DSA.Trees;

import java.util.ArrayDeque;
import java.util.Deque;

/*
Problem: Kth Smallest Element in a BST (LeetCode 230)

Return the kth smallest value (1-indexed) in the BST.

Example:
    root = [3, 1, 4, null, 2], k = 1 -> 1
    k = 3 -> 3
*/

public class KthSmallestInBST {

    // Iterative inorder — stops once we have read k values.
    public static int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        int count = 0;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            count++;
            if (count == k) {
                return current.val;
            }
            current = current.right;
        }
        throw new IllegalArgumentException("k is larger than tree size");
    }

    // Recursive inorder with counter (alternative style).
    public static int kthSmallestRecursive(TreeNode root, int k) {
        int[] state = { 0, 0 }; // [count, answer]
        inorder(root, k, state);
        return state[1];
    }

    private static void inorder(TreeNode node, int k, int[] state) {
        if (node == null || state[0] >= k) {
            return;
        }
        inorder(node.left, k, state);
        state[0]++;
        if (state[0] == k) {
            state[1] = node.val;
            return;
        }
        inorder(node.right, k, state);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 3, 1, 4, null, 2 });

        System.out.println("1st smallest (iterative): " + kthSmallest(root, 1)); // 1
        System.out.println("3rd smallest (iterative): " + kthSmallest(root, 3)); // 3

        TreeNode root2 = TreeNode.buildLevelOrder(new Integer[] { 3, 1, 4, null, 2 });
        System.out.println("2nd smallest (recursive): " + kthSmallestRecursive(root2, 2)); // 2
    }
}
```
