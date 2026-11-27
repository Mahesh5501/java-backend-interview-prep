# Trees / BST — Common Patterns Reference

A reusable revision guide for Java interviews (Java 8/17).

This file explains patterns shared by many tree problems. Pair it with the
runnable `.java` files in the parent `Trees/` folder (`package DSA.Trees`).

------------------------------------------------------------------------

## 1. What is a binary tree?

A **binary tree** is a hierarchy of nodes. Each node has:

- a value (`val`)
- a **left** child (or `null`)
- a **right** child (or `null`)

```text
        3
       / \
      9  20
        /  \
       15   7
```

In Java we usually model this with a `TreeNode` class (see `TreeNode.java`).

**Interview rule:** Clarify whether the tree is a **general binary tree** or a
**binary search tree (BST)**. The same traversal code may apply, but validity and
search rules differ.

------------------------------------------------------------------------

## 2. How to recognise a tree problem

Look for:

- Input is a tree root (`TreeNode`), not an array (unless you must rebuild a tree).
- Words like **ancestor**, **depth**, **height**, **subtree**, **path**, **leaf**.
- BST wording: **inorder**, **sorted order**, **kth smallest**, **range**, **LCA on BST**.
- Output is a tree, a boolean, a list of levels, or a single aggregated value.

------------------------------------------------------------------------

## 3. Pattern A — DFS (recursive depth-first search)

### Shape

Visit a node, then recurse on left and right.

``` java
void dfs(TreeNode node) {
    if (node == null) {
        return;
    }
    // Preorder: process node here
    dfs(node.left);
    // Inorder: process node here (BST inorder → sorted)
    dfs(node.right);
    // Postorder: process node here
}
```

### When to use it

- **Maximum depth / height**
- **Invert / mirror**
- **Same tree**
- **Diameter** (often postorder with a return value)
- **Path sums** (variant)

### Complexity

- Time: **O(n)** — visit each node once.
- Space: **O(h)** recursion stack, `h` = height (worst **O(n)** for a skewed tree).

### Interview tip

State the **base case** (`node == null`) first. Say what you **return** to the parent
(max of children, boolean AND, etc.).

------------------------------------------------------------------------

## 4. Pattern B — BFS (level-order / queue)

### Shape

``` java
Queue<TreeNode> queue = new ArrayDeque<>();
queue.add(root);

while (!queue.isEmpty()) {
    int size = queue.size(); // level size
  for (int i = 0; i < size; i++) {
    TreeNode node = queue.poll();
    // process node
    if (node.left != null) queue.add(node.left);
    if (node.right != null) queue.add(node.right);
  }
}
```

### When to use it

- **Level order traversal**
- **Right side view** (last node each level)
- **Minimum depth** (first time you reach a leaf)
- Serialization by levels (some variants)

### Complexity

- Time: **O(n)**
- Space: **O(w)** where `w` is max width (up to **O(n)**)

------------------------------------------------------------------------

## 5. Pattern C — BST properties

### Definitions

- Every node: **left subtree** values **&lt;** node, **right subtree** values **&gt;** node.
- **Inorder traversal** of a valid BST prints values in **sorted order**.

### Validate BST

Do **not** only compare parent and child. Pass a **valid range** `(min, max)` down:

``` java
boolean isValid(TreeNode node, long min, long max)
```

Use `long` to avoid overflow when comparing with `Integer.MIN_VALUE` / `MAX_VALUE`.

### Kth smallest in BST

**Inorder** with a counter, or iterative inorder with a stack — stops at k.

### LCA in BST

Use BST ordering:

- If both targets are smaller → go **left**
- If both larger → go **right**
- Otherwise current node is the **split point** (LCA)

------------------------------------------------------------------------

## 6. Pattern D — Global answer vs returned subtree value

Problems like **diameter** need:

1. A **return value** for the parent (e.g. height of subtree).
2. A **side effect** or shared variable for the **global best** (diameter through this node).

``` java
int height(TreeNode node) {
    if (node == null) return 0;
    int left = height(node.left);
    int right = height(node.right);
    best = Math.max(best, left + right); // path through this node
    return 1 + Math.max(left, right);     // height upward
}
```

------------------------------------------------------------------------

## 7. Pattern E — Build tree from traversals

**Preorder + inorder** (classic):

- Preorder gives **root** order.
- Inorder splits **left vs right** subtrees.

Use a **HashMap** for inorder index of each value for O(1) lookup.

Watch for **duplicate values** — interview problems usually forbid duplicates.

------------------------------------------------------------------------

## 8. Common mistakes

1. Forgetting **null** checks before accessing `left` / `right`.
2. **Validate BST** with only parent comparison (fails on deeper violations).
3. Confusing **depth** (root → node) and **height** (node → deepest leaf).
4. **Same tree**: checking only values, not **structure**.
5. BFS without fixing **level size** when you need level-by-level output.
6. Skewed tree → recursion depth → stack overflow (mention iterative stack if asked).
7. **LCA**: mixing BST logic on a **non-BST**.

------------------------------------------------------------------------

## 9. Complexity quick reference

  Pattern                    Time        Extra space
  -------------------------- ----------- -------------
  DFS visit all nodes        O(n)        O(h) stack
  BFS level order            O(n)        O(w) queue
  BST validate (range)       O(n)        O(h) stack
  Build from pre + in        O(n)        O(n) map + stack
  Kth smallest BST           O(h + k)    O(h) iterative stack

------------------------------------------------------------------------

## 10. How to explain a tree solution in an interview

1. **Clarify** tree type (general vs BST) and what to return.
2. **Pattern name** — DFS postorder, BFS, inorder, etc.
3. **Base case** — `null` node behavior.
4. **Recursive meaning** — what the method returns to its parent.
5. **Global vs local** — if you update a best answer (diameter).
6. **Complexity** — O(n) visits; space O(h) or O(w).

------------------------------------------------------------------------

## 11. Practice roadmap (this folder)

Run `main` in each file after you attempt the problem yourself.

1. Maximum Depth of Binary Tree — DFS depth.
2. Invert Binary Tree — swap children, DFS/BFS.
3. Same Tree — parallel DFS.
4. Binary Tree Level Order Traversal — BFS.
5. Validate Binary Search Tree — range DFS.
6. Lowest Common Ancestor of BST — BST walk.
7. Diameter of Binary Tree — height + global max.
8. Binary Tree Right Side View — BFS last per level.
9. Construct Binary Tree from Preorder and Inorder — divide & conquer.
10. Kth Smallest Element in BST — inorder.

Shared helper: `TreeNode.java` (`buildLevelOrder`, `toLevelOrderList`).