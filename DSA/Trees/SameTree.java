package DSA.Trees;

/*
Problem: Same Tree (LeetCode 100)

Two trees are the same if structure and node values match everywhere.

Example:
    p = [1, 2, 3], q = [1, 2, 3]  -> true
    p = [1, 2],    q = [1, null, 2] -> false
*/

public class SameTree {

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public static void main(String[] args) {
        TreeNode p = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3 });
        TreeNode q = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3 });
        System.out.println("Same structure: " + isSameTree(p, q)); // true

        TreeNode p2 = TreeNode.buildLevelOrder(new Integer[] { 1, 2 });
        TreeNode q2 = TreeNode.buildLevelOrder(new Integer[] { 1, null, 2 });
        System.out.println("Different shape: " + isSameTree(p2, q2)); // false
    }
}