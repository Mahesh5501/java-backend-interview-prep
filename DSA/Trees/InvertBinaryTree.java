package DSA.Trees;

import java.util.List;

/*
Problem: Invert Binary Tree (LeetCode 226)

Mirror the tree: swap left and right children at every node.

Example:
    Input:  [4, 2, 7, 1, 3, 6, 9]
    Output: [4, 7, 2, 9, 6, 3, 1] (structure mirrored)
*/

public class InvertBinaryTree {

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
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 4, 2, 7, 1, 3, 6, 9 });
        System.out.println("Before invert (BFS order): " + TreeNode.toLevelOrderList(root));

        invertTree(root);
        System.out.println("After invert (BFS order):  " + TreeNode.toLevelOrderList(root));

        TreeNode empty = invertTree(null);
        System.out.println("Inverted empty tree: " + empty);
    }
}