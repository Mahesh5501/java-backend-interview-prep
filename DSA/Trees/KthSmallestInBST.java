package DSA.Trees;

import java.util.ArrayDeque;
import java.util.Deque;

/*
Problem: Kth Smallest Element in a BST (LeetCode 230)

Return the kth smallest value (1-indexed) in the BST.

Example:
    root = [3, 1, 4, null, 2], k = 1 -> 1
    k = 3 -> 3
*/

public class KthSmallestInBST {

    // Iterative inorder — stops once we have read k values.
    public static int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        int count = 0;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            count++;
            if (count == k) {
                return current.val;
            }
            current = current.right;
        }
        throw new IllegalArgumentException("k is larger than tree size");
    }

    // Recursive inorder with counter (alternative style).
    public static int kthSmallestRecursive(TreeNode root, int k) {
        int[] state = { 0, 0 }; // [count, answer]
        inorder(root, k, state);
        return state[1];
    }

    private static void inorder(TreeNode node, int k, int[] state) {
        if (node == null || state[0] >= k) {
            return;
        }
        inorder(node.left, k, state);
        state[0]++;
        if (state[0] == k) {
            state[1] = node.val;
            return;
        }
        inorder(node.right, k, state);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 3, 1, 4, null, 2 });

        System.out.println("1st smallest (iterative): " + kthSmallest(root, 1)); // 1
        System.out.println("3rd smallest (iterative): " + kthSmallest(root, 3)); // 3

        TreeNode root2 = TreeNode.buildLevelOrder(new Integer[] { 3, 1, 4, null, 2 });
        System.out.println("2nd smallest (recursive): " + kthSmallestRecursive(root2, 2)); // 2
    }
}