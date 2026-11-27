package DSA.Trees;

/*
Problem: Maximum Depth of Binary Tree (LeetCode 104)

Given the root of a binary tree, return its maximum depth
(the number of nodes along the longest root-to-leaf path).

Example:
    root = [3, 9, 20, null, null, 15, 7]
    Output: 3
*/

public class MaximumDepthBinaryTree {

    // DFS: depth of empty tree is 0; otherwise 1 + best of subtrees.
    // Time O(n), space O(h) for recursion.
    public static int maxDepthDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepthDFS(root.left);
        int rightDepth = maxDepthDFS(root.right);
        return 1 + Math.max(leftDepth, rightDepth);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 3, 9, 20, null, null, 15, 7 });
        System.out.println("Maximum depth: " + maxDepthDFS(root)); // 3

        TreeNode single = new TreeNode(1);
        System.out.println("Single node depth: " + maxDepthDFS(single)); // 1

        System.out.println("Empty tree depth: " + maxDepthDFS(null)); // 0
    }
}