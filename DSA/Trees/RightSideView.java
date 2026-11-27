package DSA.Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
Problem: Binary Tree Right Side View (LeetCode 199)

Return values visible when looking from the right (rightmost node at each level).

Example:
    [1, 2, 3, null, 5, null, 4] -> [1, 3, 4]

See notes/Problem_08_Right_Side_View.md — every approach below matches the note.
*/

public class RightSideView {

    // APPROACH 1: BRUTE — DFS: visit right child before left; last value at each depth wins
    // Time: O(n)   Extra space: O(h) map
    public static List<Integer> rightSideViewBruteDFS(TreeNode root) {
        Map<Integer, Integer> depthToVal = new HashMap<>();
        dfsRightFirst(root, 0, depthToVal);
        List<Integer> view = new ArrayList<>();
        int maxDepth = depthToVal.keySet().stream().mapToInt(Integer::intValue).max().orElse(-1);
        for (int d = 0; d <= maxDepth; d++) {
            view.add(depthToVal.get(d));
        }
        return view;
    }

    private static void dfsRightFirst(TreeNode node, int depth, Map<Integer, Integer> depthToVal) {
        if (node == null) {
            return;
        }
        depthToVal.put(depth, node.val);
        dfsRightFirst(node.right, depth + 1, depthToVal);
        dfsRightFirst(node.left, depth + 1, depthToVal);
    }

    // APPROACH 2: BFS — append last node of each level (OPTIMAL — easy to explain)
    // Time: O(n)   Extra space: O(n)
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
        System.out.println("DFS right view: " + rightSideViewBruteDFS(root));
        System.out.println("BFS right view: " + rightSideView(root));

        TreeNode single = new TreeNode(7);
        System.out.println("Single node: " + rightSideView(single));
    }
}