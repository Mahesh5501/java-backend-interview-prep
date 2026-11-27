package DSA.Trees;

/*
Problem: Diameter of Binary Tree (LeetCode 543)

The diameter is the length of the longest path between any two nodes
(edge count). The path may or may not pass through the root.

Example:
    [1, 2, 3, 4, 5] -> diameter = 3 (path 4-2-1-3 or 5-2-1-3)
*/

public class DiameterOfBinaryTree {

    private static int bestDiameter;

    public static int diameterOfBinaryTree(TreeNode root) {
        bestDiameter = 0;
        height(root);
        return bestDiameter;
    }

    /** Returns height in edges; updates global best diameter through this node. */
    private static int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = height(node.left);
        int right = height(node.right);
        bestDiameter = Math.max(bestDiameter, left + right);
        return 1 + Math.max(left, right);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3, 4, 5 });
        System.out.println("Diameter: " + diameterOfBinaryTree(root)); // 3

        TreeNode line = TreeNode.buildLevelOrder(new Integer[] { 1, 2, null, 3, null, 4 });
        System.out.println("Skewed diameter: " + diameterOfBinaryTree(line)); // 3
    }
}