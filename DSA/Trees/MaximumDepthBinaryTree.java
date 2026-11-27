package DSA.Trees;

import java.util.ArrayDeque;
import java.util.Queue;

/*
Problem: Maximum Depth of Binary Tree (LeetCode 104)

Given the root of a binary tree, return its maximum depth
(the number of nodes along the longest root-to-leaf path).

Example:
    root = [3, 9, 20, null, null, 15, 7]
    Output: 3

See notes/Problem_01_Maximum_Depth_Binary_Tree.md — every approach below matches the note.
*/

public class MaximumDepthBinaryTree {

    // APPROACH 1: BRUTE — BFS level counting (clear but uses a queue)
    // Why: mirrors "how many layers?" without recursion.
    // Time: O(n)   Extra space: O(n) worst-case queue
    public static int maxDepthBruteBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return depth;
    }

    // APPROACH 2: DFS RECURSIVE (OPTIMAL — interview default)
    // Why: one-line recurrence; space is only recursion height.
    // Time: O(n)   Extra space: O(h)
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
        System.out.println("BFS depth:  " + maxDepthBruteBFS(root));
        System.out.println("DFS depth:  " + maxDepthDFS(root));

        System.out.println("Empty tree: " + maxDepthDFS(null));
        System.out.println("Single node: " + maxDepthDFS(new TreeNode(1)));
    }
}