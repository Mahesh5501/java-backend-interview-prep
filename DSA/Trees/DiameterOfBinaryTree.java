package DSA.Trees;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Diameter of Binary Tree (LeetCode 543)

The diameter is the length of the longest path between any two nodes (edge count).

Example:
    [1, 2, 3, 4, 5] -> diameter = 3

See notes/Problem_07_Diameter_of_Binary_Tree.md — every approach below matches the note.
*/

public class DiameterOfBinaryTree {

    // APPROACH 1: BRUTE — for every node, height(left) + height(right) as candidate
    // Why: obvious; recomputes heights from each pivot → slow on skewed trees.
    // Time: O(n^2) worst   Extra space: O(h)
    public static int diameterBrute(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        collectNodes(root, nodes);
        int best = 0;
        for (TreeNode node : nodes) {
            int left = edgeHeight(node.left);
            int right = edgeHeight(node.right);
            best = Math.max(best, left + right);
        }
        return best;
    }

    private static void collectNodes(TreeNode node, List<TreeNode> nodes) {
        if (node == null) {
            return;
        }
        nodes.add(node);
        collectNodes(node.left, nodes);
        collectNodes(node.right, nodes);
    }

    private static int edgeHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(edgeHeight(node.left), edgeHeight(node.right));
    }

    private static int bestDiameter;

    // APPROACH 2: SINGLE DFS — height return + global best (OPTIMAL)
    // Time: O(n)   Extra space: O(h)
    public static int diameterOfBinaryTree(TreeNode root) {
        bestDiameter = 0;
        heightWithDiameter(root);
        return bestDiameter;
    }

    /** Returns height in edges; updates global best diameter through this node. */
    private static int heightWithDiameter(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = heightWithDiameter(node.left);
        int right = heightWithDiameter(node.right);
        bestDiameter = Math.max(bestDiameter, left + right);
        return 1 + Math.max(left, right);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3, 4, 5 });
        System.out.println("Brute diameter:  " + diameterBrute(root));
        System.out.println("Optimal diameter: " + diameterOfBinaryTree(root));

        TreeNode line = TreeNode.buildLevelOrder(new Integer[] { 1, 2, null, 3, null, 4 });
        System.out.println("Skewed optimal: " + diameterOfBinaryTree(line));
    }
}