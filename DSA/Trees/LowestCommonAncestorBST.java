package DSA.Trees;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Lowest Common Ancestor of a BST (LeetCode 235)

Given a BST and two nodes p and q, return their lowest common ancestor.

Example:
    tree = [6, 2, 8, 0, 4, 7, 9, null, null, 3, 5], p = 2, q = 8
    Output: LCA value = 6

See notes/Problem_06_Lowest_Common_Ancestor_BST.md — every approach below matches the note.
*/

public class LowestCommonAncestorBST {

    // APPROACH 1: BRUTE — record root-to-p and root-to-q paths, walk until paths diverge
    // Time: O(h)   Extra space: O(h) for paths
    public static TreeNode lowestCommonAncestorBrute(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathP = new ArrayList<>();
        List<TreeNode> pathQ = new ArrayList<>();
        findPath(root, p.val, pathP);
        findPath(root, q.val, pathQ);
        TreeNode ancestor = root;
        int limit = Math.min(pathP.size(), pathQ.size());
        for (int i = 0; i < limit; i++) {
            if (pathP.get(i) == pathQ.get(i)) {
                ancestor = pathP.get(i);
            } else {
                break;
            }
        }
        return ancestor;
    }

    private static boolean findPath(TreeNode node, int target, List<TreeNode> path) {
        if (node == null) {
            return false;
        }
        path.add(node);
        if (node.val == target) {
            return true;
        }
        if (target < node.val && findPath(node.left, target, path)) {
            return true;
        }
        if (target > node.val && findPath(node.right, target, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    // APPROACH 2: BST WALK (OPTIMAL — no extra structures)
    // Time: O(h)   Extra space: O(h) recursion only
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

    // APPROACH 3: ITERATIVE BST WALK (same logic, no recursion)
    public static TreeNode lowestCommonAncestorIterative(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;
        while (current != null) {
            if (p.val < current.val && q.val < current.val) {
                current = current.left;
            } else if (p.val > current.val && q.val > current.val) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
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
        System.out.println("2 & 8 brute:       " + lowestCommonAncestorBrute(root, p, q).val);
        System.out.println("2 & 8 BST walk:    " + lowestCommonAncestor(root, p, q).val);
        System.out.println("2 & 8 iterative:   " + lowestCommonAncestorIterative(root, p, q).val);

        p = findNode(root, 2);
        q = findNode(root, 4);
        System.out.println("2 & 4 optimal:     " + lowestCommonAncestor(root, p, q).val);
    }
}