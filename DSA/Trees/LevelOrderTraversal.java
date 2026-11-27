package DSA.Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
Problem: Binary Tree Level Order Traversal (LeetCode 102)

Return values level by level, left to right.

Example:
    [3, 9, 20, null, null, 15, 7] -> [[3], [9, 20], [15, 7]]

See notes/Problem_04_Level_Order_Traversal.md — every approach below matches the note.
*/

public class LevelOrderTraversal {

    // APPROACH 1: BRUTE — DFS with depth map, then bucket into levels
    // Why: teaches "level = depth from root" before introducing BFS queue.
    // Time: O(n)   Extra space: O(n)
    public static List<List<Integer>> levelOrderBruteDFS(TreeNode root) {
        Map<Integer, List<Integer>> depthToVals = new HashMap<>();
        dfsCollect(root, 0, depthToVals);
        List<List<Integer>> result = new ArrayList<>();
        int maxDepth = depthToVals.keySet().stream().mapToInt(Integer::intValue).max().orElse(-1);
        for (int d = 0; d <= maxDepth; d++) {
            result.add(depthToVals.getOrDefault(d, new ArrayList<>()));
        }
        return result;
    }

    private static void dfsCollect(TreeNode node, int depth, Map<Integer, List<Integer>> depthToVals) {
        if (node == null) {
            return;
        }
        depthToVals.computeIfAbsent(depth, k -> new ArrayList<>()).add(node.val);
        dfsCollect(node.left, depth + 1, depthToVals);
        dfsCollect(node.right, depth + 1, depthToVals);
    }

    // APPROACH 2: BFS QUEUE (OPTIMAL — interview default)
    // Time: O(n)   Extra space: O(n)
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.buildLevelOrder(new Integer[] { 3, 9, 20, null, null, 15, 7 });
        System.out.println("DFS buckets: " + levelOrderBruteDFS(root));
        System.out.println("BFS levels:  " + levelOrder(root));
        System.out.println("Empty:       " + levelOrder(null));
    }
}