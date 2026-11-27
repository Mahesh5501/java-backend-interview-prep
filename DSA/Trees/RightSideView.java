package DSA.Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
Problem: Binary Tree Right Side View (LeetCode 199)

Return values visible when looking from the right
(rightmost node at each level).

Example:
    [1, 2, 3, null, 5, null, 4] -> [1, 3, 4]
*/

public class RightSideView {

    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> view = new ArrayList<>();
        if (root == null) {
            return view;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (i == levelSize - 1) {
                    view.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return view;
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 1, 2, 3, null, 5, null, 4 });
        System.out.println("Right side view: " + rightSideView(root)); // [1, 3, 4]

        TreeNode single = new TreeNode(7);
        System.out.println("Single node: " + rightSideView(single)); // [7]
    }
}