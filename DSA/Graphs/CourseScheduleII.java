package DSA.Graphs;

import java.util.*;

/*
 * Problem 04 — Course Schedule II (LeetCode 210)
 */
public class CourseScheduleII {

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
        System.out.println("Order: " + Arrays.toString(findOrder(4, pre)));

        int[][] bad = { { 1, 0 }, { 0, 1 } };
        System.out.println("Cycle order: " + Arrays.toString(findOrder(2, bad)));
    }
}