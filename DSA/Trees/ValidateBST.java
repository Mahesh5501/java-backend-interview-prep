package DSA.Trees;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Validate Binary Search Tree (LeetCode 98)

A BST: all nodes in left subtree < root < all nodes in right subtree.

Example:
    [2, 1, 3] -> true
    [5, 1, 4, null, null, 3, 6] -> false (4 with 3 left-child violates BST)

See notes/Problem_05_Validate_BST.md — every approach below matches the note.
*/

public class ValidateBST {

    // APPROACH 1: BRUTE — inorder traversal must be strictly increasing
    // Why: BST definition in one sorted list check.
    // Time: O(n)   Extra space: O(n) for values list
    public static boolean isValidBSTBrute(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        inorderValues(root, values);
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) <= values.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static void inorderValues(TreeNode node, List<Integer> values) {
        if (node == null) {
            return;
        }
        inorderValues(node.left, values);
        values.add(node.val);
        inorderValues(node.right, values);
    }

    // APPROACH 2: DFS WITH MIN/MAX BOUNDS (OPTIMAL — O(1) extra besides stack)
    // Use long bounds to handle Integer.MIN_VALUE duplicates.
    // Time: O(n)   Extra space: O(h)
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
        TreeNode invalid = TreeNode.buildLevelOrder(new Integer[] { 5, 1, 4, null, null, 3, 6 });

        System.out.println("[2,1,3] brute:    " + isValidBSTBrute(valid));
        System.out.println("[2,1,3] bounds:   " + isValidBST(valid));
        System.out.println("Invalid brute:    " + isValidBSTBrute(invalid));
        System.out.println("Invalid bounds:   " + isValidBST(invalid));
    }
}