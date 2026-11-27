package DSA.Graphs;

import java.util.*;

/*
 * Problem 10 — Shortest Path in Binary Matrix (LeetCode 1091)
 */
public class ShortestPathBinaryMatrix {

    public static int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
            return -1;
        }
        if (n == 1) {
            return 1;
        }
        int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };

        boolean[][] seen = new boolean[n][n];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[] { 0, 0, 1 });
        seen[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1], dist = cur[2];
            if (r == n - 1 && c == n - 1) {
                return dist;
            }
            for (int d = 0; d < 8; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr < 0 || nc < 0 || nr >= n || nc >= n) {
                    continue;
                }
                if (grid[nr][nc] == 1 || seen[nr][nc]) {
                    continue;
                }
                seen[nr][nc] = true;
                q.offer(new int[] { nr, nc, dist + 1 });
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[][] grid = { { 0, 1 }, { 1, 0 } };
        System.out.println("Shortest path length: " + shortestPathBinaryMatrix(grid));

        int[][] grid2 = {
                { 0, 0, 0 },
                { 1, 1, 0 },
                { 1, 1, 0 },
        };
        System.out.println("3x3 path: " + shortestPathBinaryMatrix(grid2));
    }
}