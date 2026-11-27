package DSA.Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
Problem: Kth Smallest Element in a BST (LeetCode 230)

Return the kth smallest value (1-indexed) in the BST.

Example:
    root = [3, 1, 4, null, 2], k = 1 -> 1

See notes/Problem_10_Kth_Smallest_in_BST.md — every approach below matches the note.
*/

public class KthSmallestInBST {

    // APPROACH 1: BRUTE — full inorder list, return element at k-1
    // Time: O(n)   Extra space: O(n)
    public static int kthSmallestBrute(TreeNode root, int k) {
        List<Integer> sorted = new ArrayList<>();
        inorderCollect(root, sorted);
        if (k < 1 || k > sorted.size()) {
            throw new IllegalArgumentException("k out of range");
        }
        return sorted.get(k - 1);
    }

    private static void inorderCollect(TreeNode node, List<Integer> sorted) {
        if (node == null) {
            return;
        }
        inorderCollect(node.left, sorted);
        sorted.add(node.val);
        inorderCollect(node.right, sorted);
    }

    // APPROACH 2: ITERATIVE INORDER — stop at k (OPTIMAL for interviews)
    // Time: O(h + k)   Extra space: O(h)
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

    // APPROACH 3: RECURSIVE INORDER with counter
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

        System.out.println("k=1 brute:     " + kthSmallestBrute(root, 1));
        System.out.println("k=3 iterative: " + kthSmallest(root, 3));

        TreeNode root2 = TreeNode.buildLevelOrder(new Integer[] { 3, 1, 4, null, 2 });
        System.out.println("k=2 recursive: " + kthSmallestRecursive(root2, 2));
    }
}