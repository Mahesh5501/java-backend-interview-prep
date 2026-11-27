package DSA.Graphs;

import java.util.*;

/*
Problem: Course Schedule (LeetCode 207)

See notes/Problem_03_Course_Schedule.md — every approach below matches the note.
 */
public class CourseSchedule {

    // APPROACH 1: BRUTE BACKTRACKING ALL ORDERINGS
    // Try to place courses respecting prereqs — exponential but validates understanding.
    // Time: O(n!) worst   Extra space: O(n)
    public static boolean canFinishBrute(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);
            indegree[p[0]]++;
        }
        boolean[] used = new boolean[numCourses];
        return backtrack(numCourses, indegree, adj, used, 0);
    }

    private static boolean backtrack(int n, int[] indegree, List<List<Integer>> adj, boolean[] used, int taken) {
        if (taken == n) {
            return true;
        }
        for (int c = 0; c < n; c++) {
            if (used[c] || indegree[c] > 0) {
                continue;
            }
            used[c] = true;
            List<Integer> freed = new ArrayList<>();
            for (int v : adj.get(c)) {
                indegree[v]--;
                freed.add(v);
            }
            if (backtrack(n, indegree, adj, used, taken + 1)) {
                return true;
            }
            used[c] = false;
            for (int v : freed) {
                indegree[v]++;
            }
        }
        return false;
    }

    // APPROACH 2: KAHN TOPOLOGICAL SORT (OPTIMAL — BFS)
    // Repeatedly take zero-indegree courses.
    // Time: O(V + E)   Extra space: O(V + E)
    public static boolean canFinishKahn(int numCourses, int[][] prerequisites) {
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
        int taken = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            taken++;
            for (int v : adj.get(u)) {
                if (--indegree[v] == 0) {
                    q.offer(v);
                }
            }
        }
        return taken == numCourses;
    }

    // APPROACH 3: CYCLE DETECTION DFS (three-color)
    // Visiting state on stack detects cycles in prereq graph.
    // Time: O(V + E)   Extra space: O(V)
    public static boolean canFinishDFS(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);
        }
        int[] state = new int[numCourses]; // 0 unvisited, 1 visiting, 2 done
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0 && hasCycleDFS(i, adj, state)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasCycleDFS(int u, List<List<Integer>> adj, int[] state) {
        state[u] = 1;
        for (int v : adj.get(u)) {
            if (state[v] == 1) {
                return true;
            }
            if (state[v] == 0 && hasCycleDFS(v, adj, state)) {
                return true;
            }
        }
        state[u] = 2;
        return false;
    }

    public static void main(String[] args) {
        int n = 2;
        int[][] prereq = { { 1, 0 } };
        System.out.println("Brute can finish: " + canFinishBrute(n, prereq));
        System.out.println("Kahn can finish: " + canFinishKahn(n, prereq));
        System.out.println("DFS can finish: " + canFinishDFS(n, prereq));

        int[][] cycle = { { 1, 0 }, { 0, 1 } };
        System.out.println("Cycle (Kahn): " + canFinishKahn(2, cycle));
    }
}