package DSA.Trees;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Problem: Construct Binary Tree from Preorder and Inorder Traversal (LeetCode 105)

preorder: root first, then left subtree, then right.
inorder: left subtree, root, right subtree.

Example:
    preorder = [3, 9, 20, 15, 7]
    inorder  = [9, 3, 15, 20, 7]
    Output tree matches level-order: [3, 9, 20, null, null, 15, 7]
*/

public class ConstructBinaryTreeFromPreorderInorder {

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

        TreeNode root = buildTree(preorder, inorder);
        List<Integer> levelOrder = TreeNode.toLevelOrderList(root);
        System.out.println("Built tree (BFS): " + levelOrder);

        TreeNode p = TreeNode.buildLevelOrder(new Integer[] { 3, 9, 20, null, null, 15, 7 });
        System.out.println("Expected shape:   " + TreeNode.toLevelOrderList(p));
        System.out.println("Same structure: " + SameTree.isSameTree(root, p));
    }
}