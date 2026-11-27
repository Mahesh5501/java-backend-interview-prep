package DSA.Trees;

/*
Problem: Lowest Common Ancestor of a BST (LeetCode 235)

Given a BST and two nodes p and q, return their lowest common ancestor
(lowest node that has both p and q as descendants).

Example:
    tree = [6, 2, 8, 0, 4, 7, 9, null, null, 3, 5], p = 2, q = 8
    Output: LCA value = 6
*/

public class LowestCommonAncestorBST {

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        return root;
    }

  /** Find first node with target value (for demos). */
    public static TreeNode findNode(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        if (root.val == target) {
            return root;
        }
        TreeNode left = findNode(root.left, target);
        if (left != null) {
            return left;
        }
        return findNode(root.right, target);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 6, 2, 8, 0, 4, 7, 9, null, null, 3, 5 });

        TreeNode p = findNode(root, 2);
        TreeNode q = findNode(root, 8);
        TreeNode lca = lowestCommonAncestor(root, p, q);
        System.out.println("LCA of 2 and 8: " + lca.val); // 6

        p = findNode(root, 2);
        q = findNode(root, 4);
        lca = lowestCommonAncestor(root, p, q);
        System.out.println("LCA of 2 and 4: " + lca.val); // 2
    }
}