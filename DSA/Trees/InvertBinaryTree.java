package DSA.Trees;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/*
Problem: Invert Binary Tree (LeetCode 226)

Mirror the tree: swap left and right children at every node.

Example:
    Input:  [4, 2, 7, 1, 3, 6, 9]
    Output: [4, 7, 2, 9, 6, 3, 1] (structure mirrored)

See notes/Problem_02_Invert_Binary_Tree.md — every approach below matches the note.
*/

public class InvertBinaryTree {

    // APPROACH 1: BRUTE — iterative BFS swap at each dequeued node
    // Why: no recursion stack; easy to trace on the board.
    // Time: O(n)   Extra space: O(n) queue
    public static TreeNode invertTreeBruteBFS(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return root;
    }

    // APPROACH 2: RECURSIVE DFS (OPTIMAL — compact interview solution)
    // Time: O(n)   Extra space: O(h)
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    public static void main(String[] args) {
        TreeNode a = TreeNode.buildLevelOrder(new Integer[] { 4, 2, 7, 1, 3, 6, 9 });
        TreeNode b = TreeNode.buildLevelOrder(new Integer[] { 4, 2, 7, 1, 3, 6, 9 });

        System.out.println("Before: " + TreeNode.toLevelOrderList(a));
        invertTreeBruteBFS(a);
        System.out.println("BFS invert: " + TreeNode.toLevelOrderList(a));

        invertTree(b);
        System.out.println("DFS invert: " + TreeNode.toLevelOrderList(b));

        List<Integer> empty = TreeNode.toLevelOrderList(invertTree(null));
        System.out.println("Empty: " + empty);
    }
}