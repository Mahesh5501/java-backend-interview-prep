package DSA.Trees;

/*
Problem: Validate Binary Search Tree (LeetCode 98)

A BST: all nodes in left subtree < root < all nodes in right subtree.

Example:
    [2, 1, 3] -> true
    [5, 1, 4, null, null, 3, 6] -> false (4 with 3 left-child violates BST)
*/

public class ValidateBST {

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
        System.out.println("[2,1,3] is BST: " + isValidBST(valid)); // true

        TreeNode invalid = TreeNode.buildLevelOrder(new Integer[] { 5, 1, 4, null, null, 3, 6 });
        System.out.println("[5,1,4,null,null,3,6] is BST: " + isValidBST(invalid)); // false
    }
}