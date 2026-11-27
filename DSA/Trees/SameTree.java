package DSA.Trees;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Same Tree (LeetCode 100)

Two trees are the same if structure and node values match everywhere.

Example:
    p = [1, 2, 3], q = [1, 2, 3]  -> true
    p = [1, 2],    q = [1, null, 2] -> false

See notes/Problem_03_Same_Tree.md — every approach below matches the note.
*/

public class SameTree {

    // APPROACH 1: BRUTE — flatten both trees with null sentinels, compare lists
    // Why: shows the definition literally; extra space.
    // Time: O(n)   Extra space: O(n)
    public static boolean isSameTreeBrute(TreeNode p, TreeNode q) {
        List<Integer> flatP = flattenWithNulls(p);
        List<Integer> flatQ = flattenWithNulls(q);
        if (flatP.size() != flatQ.size()) {
            return false;
        }
        for (int i = 0; i < flatP.size(); i++) {
            if (!flatP.get(i).equals(flatQ.get(i))) {
                return false;
            }
        }
        return true;
    }

  /** Preorder with null placeholder -10000 for missing children (demo brute only). */
    private static final int NULL_MARK = -10000;

    private static List<Integer> flattenWithNulls(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        flattenPreorder(root, out);
        return out;
    }

    private static void flattenPreorder(TreeNode node, List<Integer> out) {
        if (node == null) {
            out.add(NULL_MARK);
            return;
        }
        out.add(node.val);
        flattenPreorder(node.left, out);
        flattenPreorder(node.right, out);
    }

    // APPROACH 2: RECURSIVE DFS (OPTIMAL)
    // Time: O(n)   Extra space: O(h)
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
        System.out.println("Match (brute):    " + isSameTreeBrute(p, q));
        System.out.println("Match (recursive): " + isSameTree(p, q));

        TreeNode p2 = TreeNode.buildLevelOrder(new Integer[] { 1, 2 });
        TreeNode q2 = TreeNode.buildLevelOrder(new Integer[] { 1, null, 2 });
        System.out.println("Shape diff (brute):    " + isSameTreeBrute(p2, q2));
        System.out.println("Shape diff (recursive): " + isSameTree(p2, q2));
    }
}