package DSA.Graphs;

import java.util.*;

/*
Problem: Course Schedule II (LeetCode 210)

Return any topological order, or empty if impossible.

See notes/Problem_04_Course_Schedule_II.md — every approach below matches the note.
 */
public class CourseScheduleII {

    // APPROACH 1: BACKTRACK ALL VALID ORDERINGS (BRUTE)
    // Why: same as Course Schedule — try zero-indegree courses until all placed.
    // Time: O(n!) worst   Extra space: O(n)
    public static int[] findOrderBrute(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);
            indegree[p[0]]++;
        }
        int[] order = new int[numCourses];
        boolean[] used = new boolean[numCourses];
        if (backtrackOrder(numCourses, indegree, adj, used, order, 0)) {
            return order;
        }
        return new int[0];
    }

    private static boolean backtrackOrder(int n, int[] indegree, List<List<Integer>> adj,
            boolean[] used, int[] order, int idx) {
        if (idx == n) {
            return true;
        }
        for (int c = 0; c < n; c++) {
            if (used[c] || indegree[c] > 0) {
                continue;
            }
            used[c] = true;
            order[idx] = c;
            List<Integer> freed = new ArrayList<>();
            for (int v : adj.get(c)) {
                indegree[v]--;
                freed.add(v);
            }
            if (backtrackOrder(n, indegree, adj, used, order, idx + 1)) {
                return true;
            }
            used[c] = false;
            for (int v : freed) {
                indegree[v]++;
            }
        }
        return false;
    }

    // APPROACH 2: KAHN TOPOLOGICAL SORT (OPTIMAL — what we use)
    // Why: linear-time greedy peel of zero-indegree nodes.
    // Time: O(V + E)   Extra space: O(V + E)
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            int a = p[0], b = p[1];
            adj.get(b).add(a);
            indegree[a]++;
        }
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }
        int[] order = new int[numCourses];
        int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            order[idx++] = u;
            for (int v : adj.get(u)) {
                if (--indegree[v] == 0) {
                    q.offer(v);
                }
            }
        }
        if (idx != numCourses) {
            return new int[0];
        }
        return order;
    }

    public static void main(String[] args) {
        int[][] pre = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
        System.out.println("Brute order: " + Arrays.toString(findOrderBrute(4, pre)));
        System.out.println("Kahn order:  " + Arrays.toString(findOrder(4, pre)));

        int[][] bad = { { 1, 0 }, { 0, 1 } };
        System.out.println("Cycle:       " + Arrays.toString(findOrder(2, bad)));
    }
}