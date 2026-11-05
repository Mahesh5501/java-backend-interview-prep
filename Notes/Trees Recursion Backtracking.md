
# DSA CHEAT SHEET — TREES & RECURSION / BACKTRACKING
## Recognition → Algorithm → Variations → Java Templates → Interview Problems

---

# PART 1 — TREES

## 1. What is the core idea?

A tree is a hierarchical data structure made of nodes.

Each node can contain:
- A value
- A left child
- A right child

Example:

          1
         / \
        2   3
       / \
      4   5

Here:
- 1 is the root.
- 2 and 3 are children of 1.
- 4 and 5 are children of 2.
- 4, 5, and 3 are leaf nodes because they have no children.

In a binary tree, each node has at most two children.

## 2. How to recognize a tree problem

Look for these clues:

| Problem wording | Likely pattern |
|---|---|
| Visit every node | Tree traversal |
| Find maximum depth or height | DFS / Recursion |
| Count nodes or leaves | DFS / Recursion |
| Find a value or path | DFS or BFS |
| Process nodes level by level | BFS / Queue |
| Find shortest number of edges in an unweighted tree | BFS |
| Check whether two trees are identical | Recursive DFS |
| Check whether a tree is balanced | DFS returning height |
| Find the lowest common ancestor | Tree DFS |
| Find kth smallest in a BST | Inorder traversal |
| Find the maximum path sum | DFS returning a useful value |
| Generate all possible trees or paths | Recursion / Backtracking |

## 3. Tree pattern decision tree

START: What does the question ask?

1. Process nodes level by level?
   -> BFS using a Queue.

2. Visit nodes in a particular order?
   -> Preorder, Inorder, or Postorder DFS.

3. Calculate information from child subtrees?
   -> Recursive DFS, often Postorder.

4. Search for a target or a valid path?
   -> DFS; use BFS when level order or shortest distance matters.

5. Need sorted order from a Binary Search Tree?
   -> Inorder traversal.

6. Need the best result from multiple possible paths?
   -> DFS with a return value, sometimes a global answer.

7. Need to enumerate possible paths, combinations, or tree configurations?
   -> Recursion / Backtracking.

IMPORTANT:
Do not choose recursion just because the input is a tree.
Choose the traversal based on what the question needs.

---

# 4. The Tree Node Structure

Use this structure in the examples:

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}
```

A reference to a child can be null.

For example, a leaf node has:

```java
node.left == null
node.right == null
```

Always handle null nodes correctly.

---

# 5. The Three DFS Traversals

The main difference is WHEN you process the current node.

Example:

          1
         / \
        2   3

## A. Preorder: Node → Left → Right

```java
void preorder(TreeNode root) {
    if (root == null) {
        return;
    }

    System.out.println(root.val);
    preorder(root.left);
    preorder(root.right);
}
```

Output: 1, 2, 3

Use when:
- You need to process or copy the current node before its children.
- You want to serialize a tree in preorder.
- You want to build a root-first representation.

Memory trick: PRE means process the node BEFORE its children.

## B. Inorder: Left → Node → Right

```java
void inorder(TreeNode root) {
    if (root == null) {
        return;
    }

    inorder(root.left);
    System.out.println(root.val);
    inorder(root.right);
}
```

Output: 2, 1, 3

Use when:
- The tree is a Binary Search Tree (BST).
- You need values in sorted order.
- You need kth smallest in a BST.

IMPORTANT: Inorder produces sorted values only when the tree satisfies the BST property.

## C. Postorder: Left → Right → Node

```java
void postorder(TreeNode root) {
    if (root == null) {
        return;
    }

    postorder(root.left);
    postorder(root.right);
    System.out.println(root.val);
}
```

Output: 2, 3, 1

Use when:
- The answer for a parent depends on its children.
- You need height or subtree information.
- You want to delete a tree from the leaves upward.
- You need to calculate subtree results.

Memory trick: POST means process the node AFTER its children.

## How to choose the traversal

| If you need... | Choose |
|---|---|
| Parent before children | Preorder |
| Sorted values from a BST | Inorder |
| Children before parent | Postorder |
| Nodes grouped by level | BFS |

---

# 6. Maximum Depth / Height of a Binary Tree

## Recognition

Look for:
- Maximum depth
- Height of the tree
- Longest root-to-leaf path measured in nodes

## Main idea

The height of a node is:

height = 1 + maximum(left height, right height)

A null node has height 0.

Example:

        1
       /
      2
     /
    3

Height = 3 nodes.

## Java code

```java
int maxDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }

    int left = maxDepth(root.left);
    int right = maxDepth(root.right);

    return 1 + Math.max(left, right);
}
```

## Dry run

For node 3:
- Left height = 0
- Right height = 0
- Return 1

For node 2:
- Left height = 1
- Right height = 0
- Return 2

For node 1:
- Left height = 2
- Right height = 0
- Return 3

The recursive calls go downward first. The answers return upward.

## Complexity

- Time: O(n), because each node is visited once.
- Space: O(h), where h is tree height due to the recursion stack.
- A balanced tree has height around log n.
- A completely skewed tree can have height n.

## Common mistake

Do not return 1 for null when measuring depth in this implementation. That changes the height calculation.

---

# 7. Count Nodes and Sum Values

These questions use the same recursive structure as maximum depth.

## Count all nodes

```java
int countNodes(TreeNode root) {
    if (root == null) {
        return 0;
    }

    return 1
        + countNodes(root.left)
        + countNodes(root.right);
}
```

## Sum all node values

```java
int sumNodes(TreeNode root) {
    if (root == null) {
        return 0;
    }

    return root.val
        + sumNodes(root.left)
        + sumNodes(root.right);
}
```

## Recognition rule

If a question asks you to calculate something for every subtree, ask:

"What should a null subtree return?"

Examples:
- Count: 0
- Sum: 0
- Height: 0
- Boolean validity: often true for an empty subtree

Then decide how to combine the left result, right result, and current node.

---

# 8. Check Whether Two Trees Are Identical

## Recognition

"Are these two trees the same?"

Both structure and values must match.

## Algorithm

1. If both nodes are null, they match.
2. If exactly one is null, they do not match.
3. If values differ, they do not match.
4. Compare left subtrees.
5. Compare right subtrees.

## Java code

```java
boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) {
        return true;
    }

    if (p == null || q == null) {
        return false;
    }

    if (p.val != q.val) {
        return false;
    }

    return isSameTree(p.left, q.left)
        && isSameTree(p.right, q.right);
}
```

## Key lesson

When comparing two recursive structures, compare:
- Empty cases
- Current values
- Corresponding children

Time: O(n) in the worst case, where n is the number of nodes compared.

---

# 9. Check Whether a Tree Is Balanced

## Recognition

"Is the height difference between the left and right subtrees at every node at most 1?"

For every node:

abs(leftHeight - rightHeight) <= 1

## Simple solution and its weakness

You could calculate the height at every node and then recursively check its children.

But height may be recalculated repeatedly. In a skewed tree, this can take O(n²) time.

## Better solution: Return height or failure

Use -1 to indicate an unbalanced subtree.

```java
boolean isBalanced(TreeNode root) {
    return heightOrFailure(root) != -1;
}

int heightOrFailure(TreeNode root) {
    if (root == null) {
        return 0;
    }

    int left = heightOrFailure(root.left);

    if (left == -1) {
        return -1;
    }

    int right = heightOrFailure(root.right);

    if (right == -1) {
        return -1;
    }

    if (Math.abs(left - right) > 1) {
        return -1;
    }

    return 1 + Math.max(left, right);
}
```

## Why return -1?

A valid height is zero or greater. Therefore, -1 can act as a special failure signal.

If the left subtree is already unbalanced, there is no need to process the remaining subtree.

Time: O(n).

## Recognition lesson

If you need height AND need to know whether the tree is balanced, return both pieces of information or encode failure in the returned value.

---

# 10. Binary Search Tree (BST)

## Recognition

The question explicitly mentions:
- Binary Search Tree
- Search, insert, or delete in a BST
- Kth smallest or kth largest
- Validate BST

A BST follows an ordering rule:

- Values in the left subtree are smaller than the node.
- Values in the right subtree are greater than the node.

The exact duplicate-value policy depends on the problem.

## Search in a BST

```java
TreeNode searchBST(TreeNode root, int target) {
    if (root == null || root.val == target) {
        return root;
    }

    if (target < root.val) {
        return searchBST(root.left, target);
    }

    return searchBST(root.right, target);
}
```

Why does this work?

If the target is smaller than the current value, the target cannot be in the right subtree of a valid BST.

Average time in a balanced BST: O(log n).
Worst-case time in a skewed BST: O(n).

## Validate a BST

Do not check only whether each node is greater than its immediate left child and smaller than its immediate right child.

Every node must satisfy constraints from ALL its ancestors.

Use lower and upper bounds.

```java
boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

boolean validate(TreeNode node, long min, long max) {
    if (node == null) {
        return true;
    }

    if (node.val <= min || node.val >= max) {
        return false;
    }

    return validate(node.left, min, node.val)
        && validate(node.right, node.val, max);
}
```

This version assumes duplicates are not allowed.

Why use long bounds? So integer values at the `Integer.MIN_VALUE` and `Integer.MAX_VALUE` boundaries are handled correctly.

Time: O(n).

## Kth smallest in a BST

Because inorder traversal produces sorted values, the kth visited node is the kth smallest.

```java
int kthSmallest(TreeNode root, int k) {
    List<Integer> values = new ArrayList<>();
    inorderCollect(root, values);
    return values.get(k - 1);
}

void inorderCollect(TreeNode root, List<Integer> values) {
    if (root == null) {
        return;
    }

    inorderCollect(root.left, values);
    values.add(root.val);
    inorderCollect(root.right, values);
}
```

This simple implementation takes O(n) time and O(n) extra space.

For a more memory-efficient solution, stop the inorder traversal as soon as the kth node is reached.

---

# 11. Level Order Traversal — BFS

## Recognition

Use BFS when the question says:
- Level by level
- Each level separately
- Nearest node by number of edges
- Minimum depth in an unweighted tree

BFS uses a Queue.

## Java code: Return each level separately

```java
List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();

    if (root == null) {
        return result;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        result.add(level);
    }

    return result;
}
```

## Why capture queue.size()?

Suppose the queue initially contains:

[1]

Process node 1:
- Add children 2 and 3.
- Queue becomes [2, 3].

The next level contains exactly those two nodes.

Capturing `size` at the start of each iteration ensures that newly added children belong to the next level, not the current one.

Time: O(n).
Space: O(w), where w is the maximum width of the tree.

---

# 12. Lowest Common Ancestor (LCA)

## Recognition

"Find the lowest node that has both target nodes as descendants."

For a general binary tree, one common recursive solution is:

```java
TreeNode lowestCommonAncestor(
        TreeNode root, TreeNode p, TreeNode q) {

    if (root == null || root == p || root == q) {
        return root;
    }

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    if (left != null && right != null) {
        return root;
    }

    return left != null ? left : right;
}
```

Assumptions:
- `p` and `q` are node references in the tree.
- Both target nodes exist in the tree.

## How to reason about it

If one target is found in the left subtree and the other in the right subtree, the current root is their LCA.

If both targets are in the same subtree, that subtree returns the answer.

For a BST, use the ordering property to find the LCA more directly.

Time: O(n) for a general binary tree.

---

# 13. Maximum Path Sum — Advanced Tree DFS

## Recognition

"Find the maximum sum of a path in a binary tree."

A path can start and end at different nodes. It does not necessarily start at the root.

This problem needs two different values:

1. A value returned to the parent.
2. A global best answer.

```java
int maxPathSum(TreeNode root) {
    int[] best = {Integer.MIN_VALUE};
    maxGain(root, best);
    return best[0];
}

int maxGain(TreeNode node, int[] best) {
    if (node == null) {
        return 0;
    }

    int left = Math.max(0, maxGain(node.left, best));
    int right = Math.max(0, maxGain(node.right, best));

    int pathThroughNode = node.val + left + right;
    best[0] = Math.max(best[0], pathThroughNode);

    return node.val + Math.max(left, right);
}
```

## Why are the two values different?

The global answer can use both left and right branches.

But the value returned to the parent can use only ONE branch. Otherwise, the path would split into two branches and could no longer continue as a single path through the parent.

Using `Math.max(0, ...)` avoids adding a negative subtree contribution.

Important: The initial best value is `Integer.MIN_VALUE` so an all-negative tree is handled correctly.

Time: O(n).

---

# 14. Tree Problem Recognition Summary

| Problem | Main technique |
|---|---|
| Visit all nodes | DFS |
| Maximum depth | Postorder DFS |
| Count nodes | DFS |
| Identical trees | Recursive DFS |
| Balanced tree | DFS returning height/failure |
| Sorted BST values | Inorder |
| Kth smallest BST value | Inorder with counter |
| Level order | BFS + Queue |
| Lowest common ancestor | DFS |
| Maximum path sum | DFS + global answer |
| Minimum depth | BFS or DFS |
| Root-to-leaf paths | DFS + Backtracking |

---

# PART 2 — RECURSION / BACKTRACKING

# 15. What Is Recursion?

Recursion means a method calls itself to solve a smaller version of the same problem.

Every recursive method needs:

1. A base case: when to stop.
2. A recursive step: how to reduce the problem.
3. Progress toward the base case.

Example: Count down from 3.

```java
void countDown(int n) {
    if (n == 0) {
        return;
    }

    System.out.println(n);
    countDown(n - 1);
}
```

Output:

3
2
1

The recursive calls are:

countDown(3)
  -> countDown(2)
       -> countDown(1)
            -> countDown(0)
                 -> return

When `n == 0`, the method stops calling itself.

IMPORTANT:
A recursive call is not automatically a backtracking problem. Backtracking is a particular kind of recursion used to explore choices and undo changes when needed.

---

# 16. How to Recognize Recursion vs Backtracking

| Problem clue | Likely technique |
|---|---|
| Calculate factorial or sum recursively | Basic recursion |
| Process a tree or nested structure | Recursive DFS |
| Generate all subsequences | Recursion / Backtracking |
| Generate all subsets | Backtracking |
| Generate all permutations | Backtracking |
| Find combinations that meet a target | Backtracking |
| Place queens while avoiding attacks | Backtracking |
| Generate valid parentheses | Backtracking |
| Find all paths through a grid | Backtracking |
| Find one valid answer and stop | DFS / Backtracking with early return |
| Find the best answer among many choices | Backtracking or optimization DP |

## Recognition rule

Ask:

"Do I need to explore multiple possible choices?"

- No: basic recursion may be enough.
- Yes, and I need to enumerate or search choices: consider backtracking.
- Yes, but many subproblems repeat: consider dynamic programming or memoization.

---

# 17. The Three Questions for Every Recursive Problem

Before writing code, answer:

1. What does one method call represent?
2. What is the base case?
3. What smaller calls must I make?

For backtracking, add two more:

4. What choices are available?
5. Do I need to undo a change after exploring a choice?

Example: Generate subsequences of `"ab"`.

A subsequence preserves the original character order but can skip characters.

Possible subsequences:

""
"a"
"b"
"ab"

For each character, there are two choices:
- Include it.
- Exclude it.

That gives the general rule:

For each character:
  Choice 1: do not include it.
  Choice 2: include it.

---

# 18. Generate All Subsequences

## Recognition

The question asks for:
- All subsequences
- All include/exclude combinations
- All subsets of positions in an ordered input

## Java code

```java
void generateSubsequences(
        String str, int index, String current) {

    if (index == str.length()) {
        System.out.println(current);
        return;
    }

    // Choice 1: Exclude current character
    generateSubsequences(str, index + 1, current);

    // Choice 2: Include current character
    generateSubsequences(
        str,
        index + 1,
        current + str.charAt(index)
    );
}
```

Call:

```java
generateSubsequences("ab", 0, "");
```

## Dry run

Start:

index = 0
current = ""

Current character = 'a'

Choice 1: Exclude 'a'

  index = 1
  current = ""

  Current character = 'b'

  Choice 1: Exclude 'b'
    index = 2
    current = ""
    Print ""

  Choice 2: Include 'b'
    index = 2
    current = "b"
    Print "b"

Choice 2: Include 'a'

  index = 1
  current = "a"

  Current character = 'b'

  Choice 1: Exclude 'b'
    index = 2
    current = "a"
    Print "a"

  Choice 2: Include 'b'
    index = 2
    current = "ab"
    Print "ab"

Output order:

""
"b"
"a"
"ab"

The output order depends on the order in which the choices are explored.

## Why are there three parameters?

`str`: the original input.

`index`: the position of the next character to decide about.

`current`: the subsequence built so far.

Each parameter has a separate job.

## Complexity

For a string of length n:
- Number of subsequences: 2^n.
- Time: O(n × 2^n) when counting the cost of constructing or printing each result.
- Recursion depth: O(n).

---

# 19. Generate All Subsets of an Integer Array

## Recognition

"Return every possible subset."

Each element is either included or excluded.

## Java code

```java
void subsets(
        int[] nums,
        int index,
        List<Integer> current,
        List<List<Integer>> result) {

    if (index == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }

    // Choice 1: Exclude nums[index]
    subsets(nums, index + 1, current, result);

    // Choice 2: Include nums[index]
    current.add(nums[index]);

    subsets(nums, index + 1, current, result);

    // Undo the choice
    current.remove(current.size() - 1);
}
```

Call:

```java
List<List<Integer>> result = new ArrayList<>();

subsets(
    new int[]{1, 2},
    0,
    new ArrayList<>(),
    result
);
```

## Why copy current?

This is essential:

```java
result.add(new ArrayList<>(current));
```

Without the copy, all result entries could reference the same mutable list. Later modifications would change the lists already stored in the result.

## Why remove the last element?

The `current` list is shared across recursive calls.

After exploring the branch that includes a value, remove that value before returning to the parent branch.

This restores the previous state.

## Complexity

- Number of subsets: 2^n.
- Time: O(n × 2^n) for copying all subsets.
- Output space: O(n × 2^n).

---

# 20. Generate All Permutations

## Recognition

"Arrange all elements in every possible order."

Unlike subsets, order matters.

For `[1, 2, 3]`, both `[1, 2, 3]` and `[2, 1, 3]` are different permutations.

## Java code using a used array

```java
void permute(
        int[] nums,
        boolean[] used,
        List<Integer> current,
        List<List<Integer>> result) {

    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) {
            continue;
        }

        used[i] = true;
        current.add(nums[i]);

        permute(nums, used, current, result);

        current.remove(current.size() - 1);
        used[i] = false;
    }
}
```

Call:

```java
int[] nums = {1, 2, 3};

List<List<Integer>> result = new ArrayList<>();

permute(
    nums,
    new boolean[nums.length],
    new ArrayList<>(),
    result
);
```

## How is this different from subsets?

Subsets:
- Each element has an include/exclude choice.
- Each element is considered in its original position.
- There are 2^n subsets.

Permutations:
- At every position, choose one of the unused elements.
- Order matters.
- There are n! permutations when all elements are distinct.

Time: O(n × n!) when copying every result.

## Common mistake

Do not confuse `index`-based include/exclude recursion with choosing any unused element. They solve different problems.

---

# 21. Combination Sum

## Recognition

"Find combinations that add up to a target."

You may need to decide:
- Can an element be reused?
- Are duplicate combinations forbidden?
- Must the result contain a fixed number of elements?
- Can values be negative?

These conditions determine the algorithm.

## Example: Reuse candidates, assume positive values

Input:

candidates = [2, 3]
target = 6

One valid combination is:

[2, 2, 2]
[3, 3]

## Java code

```java
void combinationSum(
        int[] candidates,
        int start,
        int remaining,
        List<Integer> current,
        List<List<Integer>> result) {

    if (remaining == 0) {
        result.add(new ArrayList<>(current));
        return;
    }

    if (remaining < 0) {
        return;
    }

    for (int i = start; i < candidates.length; i++) {
        current.add(candidates[i]);

        // i, not i + 1: reuse is allowed
        combinationSum(
            candidates,
            i,
            remaining - candidates[i],
            current,
            result
        );

        current.remove(current.size() - 1);
    }
}
```

## Why use start?

It prevents generating the same combination in different orders.

For example, `[2, 3]` and `[3, 2]` should not be treated as different combinations.

## Why recurse with i?

Because the current candidate can be selected again.

If each candidate could be used only once, the recursive call would use `i + 1`.

## Important limitation

The `remaining < 0` stopping condition is safe here because the candidates are assumed positive. It is not generally safe when negative values are allowed.

---

# 22. Generate Valid Parentheses

## Recognition

"Generate all valid combinations of n pairs of parentheses."

This is not ordinary include/exclude recursion because invalid choices should be rejected before exploring them.

## Rules

You may add `(` when:

open < n

You may add `)` when:

close < open

The second rule prevents closing a parenthesis that has not been opened.

## Java code

```java
void generateParentheses(
        int n,
        int open,
        int close,
        String current,
        List<String> result) {

    if (current.length() == 2 * n) {
        result.add(current);
        return;
    }

    if (open < n) {
        generateParentheses(
            n, open + 1, close,
            current + "(", result
        );
    }

    if (close < open) {
        generateParentheses(
            n, open, close + 1,
            current + ")", result
        );
    }
}
```

Call:

```java
List<String> result = new ArrayList<>();
generateParentheses(3, 0, 0, "", result);
```

## Why not generate every possible string first?

There are 2^(2n) strings of length 2n made from `(` and `)`.

Most are invalid.

Backtracking avoids exploring branches that already violate the rules.

This is called pruning.

---

# 23. N-Queens

## Recognition

"Place n queens on an n × n chessboard so that no two queens attack each other."

A queen cannot share:
- The same column
- The same diagonal

## Backtracking idea

Place one queen per row.

For each row:
1. Try every column.
2. Check whether the position is safe.
3. Place the queen.
4. Solve the next row.
5. Remove the queen when returning.

The removal step allows another placement to be tried.

## General structure

```java
void solve(int row) {
    if (row == n) {
        // A complete valid arrangement was found.
        saveSolution();
        return;
    }

    for (int col = 0; col < n; col++) {
        if (!isSafe(row, col)) {
            continue;
        }

        placeQueen(row, col);

        solve(row + 1);

        removeQueen(row, col);
    }
}
```

This is a structural template rather than a complete implementation.

For efficient implementations, track occupied columns and diagonals with boolean arrays or sets.

## Key lesson

Use backtracking when you must explore choices while maintaining constraints.

---

# 24. Tree Paths and Backtracking

Tree path problems often combine tree DFS with backtracking.

Example: Find every root-to-leaf path.

Recognition clues:
- Return every path.
- Start at the root and end at a leaf.
- Maintain a path while visiting nodes.

## Java code

```java
void rootToLeafPaths(
        TreeNode node,
        List<Integer> path,
        List<List<Integer>> result) {

    if (node == null) {
        return;
    }

    path.add(node.val);

    if (node.left == null && node.right == null) {
        result.add(new ArrayList<>(path));
    } else {
        rootToLeafPaths(node.left, path, result);
        rootToLeafPaths(node.right, path, result);
    }

    path.remove(path.size() - 1);
}
```

## Why backtrack?

Suppose the tree is:

        1
       / \
      2   3

After saving `[1, 2]`, the path must return to `[1]` before exploring node 3.

Otherwise, the second path could incorrectly contain node 2.

---

# 25. The Backtracking Template to Memorize

Most backtracking problems follow this shape:

```java
void backtrack(State state) {

    if (isComplete(state)) {
        saveSolution(state);
        return;
    }

    for (Choice choice : availableChoices(state)) {

        if (!isValid(choice, state)) {
            continue;
        }

        makeChoice(choice, state);

        backtrack(state);

        undoChoice(choice, state);
    }
}
```

Understand the job of every part:

- Base case: Are we done?
- Choices: What can we try next?
- Validation: Is this choice allowed?
- Make choice: Update the current state.
- Recursive call: Explore the future.
- Undo choice: Restore the previous state.

For some problems, state is immutable or copied, so an explicit undo step may not be needed.

---

# 26. When Should You Use `index`, `start`, or `used[]`?

This is one of the most important distinctions.

| Variable | Use it when |
|---|---|
| `index` | Each input position gets a decision, such as include/exclude |
| `start` | Choosing combinations while avoiding different orderings of the same combination |
| `used[]` | Choosing any unused element, especially permutations |
| `remaining` | Tracking how much of a target is left |
| `open`, `close` | Tracking constraints in parentheses generation |
| `row`, `col` | Tracking board positions in grid or chessboard problems |
| `path` / `current` | Storing the partial answer built so far |

Do not add all these parameters to every recursive method. Use only the state the problem needs.

---

# 27. Common Recursion Mistakes

## Mistake 1: Missing the base case

Without a valid stopping condition, recursion may continue until a StackOverflowError occurs.

## Mistake 2: Not making progress

Every recursive call should move closer to a base case or reduce the remaining work.

## Mistake 3: Wrong base-case condition

For subsequences, stop when `index == str.length()`, not when the string itself becomes empty unless your algorithm actually shortens the string.

## Mistake 4: Forgetting to return

When a base case finishes a branch, use `return` if the method must stop processing that branch.

## Mistake 5: Forgetting to undo a choice

If a mutable list is shared across branches, undo changes after the recursive call.

## Mistake 6: Storing the same mutable list repeatedly

Use:

```java
result.add(new ArrayList<>(current));
```

not:

```java
result.add(current);
```

## Mistake 7: Mixing subsets and permutations

- Subsets: include or exclude elements.
- Permutations: choose the next unused element.
- Combinations: choose elements while controlling ordering and reuse.

## Mistake 8: Confusing recursion with dynamic programming

If the same subproblem is recalculated repeatedly, consider memoization or dynamic programming.

## Mistake 9: Forgetting the cost of output

Generating 2^n results cannot generally be completed in polynomial time because the output itself is exponential.

---

# 28. How to Solve an Unfamiliar Tree Problem

Use this checklist:

1. Identify whether the input is a general binary tree or a BST.
2. Decide whether you need DFS or BFS.
3. If DFS, decide preorder, inorder, or postorder based on when you need the current node.
4. Define what the recursive method returns.
5. Define the null-node behavior.
6. Decide whether the answer is returned from recursion or stored as a global/best-so-far value.
7. Check whether the parent needs information from both children.
8. Dry-run a tree with three or four nodes.
9. Check empty, single-node, and skewed trees.
10. Calculate time and recursion-stack space.

# 29. How to Solve an Unfamiliar Backtracking Problem

Use this checklist:

1. What is one complete answer?
2. What are the choices at each step?
3. What state describes the partial answer?
4. What makes a choice invalid?
5. What is the base case?
6. Should the next call use `index + 1`, `start`, `i`, or `used[]`?
7. Can a choice be reused?
8. Do I need to undo the choice?
9. Can I prune invalid branches early?
10. Can I avoid repeated work with memoization?
11. How many results can exist?
12. Is the required output all answers, one answer, or the best answer?

---

# 30. Final Recognition Table

| Question | First technique to consider |
|---|---|
| Maximum tree depth | Recursive DFS |
| Is a tree balanced? | DFS returning height or failure |
| Kth smallest in BST | Inorder traversal |
| Process each level | BFS + Queue |
| Find maximum path sum | DFS + global best |
| Find all root-to-leaf paths | DFS + Backtracking |
| Generate subsequences | Include/exclude recursion |
| Generate subsets | Include/exclude backtracking |
| Generate permutations | `used[]` backtracking |
| Find combinations for a target | Backtracking with `start` and `remaining` |
| Generate valid parentheses | Backtracking with constraints |
| Place queens on a board | Backtracking with pruning |
| Repeatedly calculate the same states | Consider Dynamic Programming |

---

# 31. Recommended Practice Order

## Trees

1. Maximum Depth of Binary Tree
2. Inorder Traversal
3. Same Tree
4. Level Order Traversal
5. Balanced Binary Tree
6. Validate Binary Search Tree
7. Kth Smallest Element in a BST
8. Lowest Common Ancestor
9. Path Sum / Root-to-Leaf Paths
10. Binary Tree Maximum Path Sum

## Recursion / Backtracking

1. Factorial and sum of numbers
2. Generate all subsequences
3. Subsets
4. Permutations
5. Combination Sum
6. Generate Parentheses
7. Root-to-Leaf Paths
8. Word Search
9. N-Queens

Do not try to memorize all implementations at once. For each problem, first explain the base case, recursive state, and choices aloud. Then write the Java code from scratch.

---

# 32. Five-Second Memory Sheet

TREES:
- Parent before children → Preorder
- Sorted BST values → Inorder
- Children before parent → Postorder
- Level by level → BFS
- Parent needs child results → Return information from DFS
- Best path can differ from returned path → Global answer + DFS return value

RECURSION:
- Smaller version of same problem → Recursion
- Include or exclude each item → Subsets / Subsequences
- Choose unused items → Permutations
- Choose ordered combinations without duplicates → `start`
- Must satisfy constraints → Backtracking + pruning
- Shared mutable state → Make choice, recurse, undo
- Repeated subproblems → Consider memoization / DP
