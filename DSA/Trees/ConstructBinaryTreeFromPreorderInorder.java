package DSA.Trees;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Problem: Construct Binary Tree from Preorder and Inorder Traversal (LeetCode 105)

Example:
    preorder = [3, 9, 20, 15, 7]
    inorder  = [9, 3, 15, 20, 7]
    Output tree matches level-order: [3, 9, 20, null, null, 15, 7]

See notes/Problem_09_Construct_Binary_Tree_from_Preorder_Inorder.md — every approach below matches the note.
*/

public class ConstructBinaryTreeFromPreorderInorder {

    // APPROACH 1: BRUTE — linear scan in inorder subarray for each root (no hash map)
    // Time: O(n^2) worst   Extra space: O(n) recursion
    public static TreeNode buildTreeBrute(int[] preorder, int[] inorder) {
        int[] preIndex = { 0 };
        return buildBrute(preorder, 0, inorder.length - 1, inorder, preIndex);
    }

    private static TreeNode buildBrute(int[] preorder, int inStart, int inEnd, int[] inorder, int[] preIndex) {
        if (inStart > inEnd) {
            return null;
        }
        int rootVal = preorder[preIndex[0]++];
        TreeNode root = new TreeNode(rootVal);

        int rootIndex = inStart;
        while (rootIndex <= inEnd && inorder[rootIndex] != rootVal) {
            rootIndex++;
        }

        root.left = buildBrute(preorder, inStart, rootIndex - 1, inorder, preIndex);
        root.right = buildBrute(preorder, rootIndex + 1, inEnd, inorder, preIndex);
        return root;
    }

    // APPROACH 2: HASH MAP FOR INORDER INDICES (OPTIMAL — O(n))
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        int[] preIndex = { 0 };
        return build(preorder, 0, inorder.length - 1, inorderIndex, preIndex);
    }

    private static TreeNode build(int[] preorder, int inStart, int inEnd,
            Map<Integer, Integer> inorderIndex, int[] preIndex) {

        if (inStart > inEnd) {
            return null;
        }

        int rootVal = preorder[preIndex[0]++];
        TreeNode root = new TreeNode(rootVal);

        int rootIndex = inorderIndex.get(rootVal);

        root.left = build(preorder, inStart, rootIndex - 1, inorderIndex, preIndex);
        root.right = build(preorder, rootIndex + 1, inEnd, inorderIndex, preIndex);

        return root;
    }

    public static void main(String[] args) {
        int[] preorder = { 3, 9, 20, 15, 7 };
        int[] inorder = { 9, 3, 15, 20, 7 };

        TreeNode bruteRoot = buildTreeBrute(preorder, inorder);
        TreeNode optimalRoot = buildTree(preorder, inorder);

        List<Integer> bruteLo = TreeNode.toLevelOrderList(bruteRoot);
        List<Integer> optimalLo = TreeNode.toLevelOrderList(optimalRoot);
        System.out.println("Brute BFS:   " + bruteLo);
        System.out.println("Optimal BFS: " + optimalLo);

        TreeNode expected = TreeNode.buildLevelOrder(new Integer[] { 3, 9, 20, null, null, 15, 7 });
        System.out.println("Same structure: " + SameTree.isSameTree(optimalRoot, expected));
    }
}