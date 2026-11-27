package DSA.Graphs;

import java.util.*;

/*
Problem: Shortest Path in Binary Matrix (LeetCode 1091)

See notes/Problem_10_Shortest_Path_Binary_Matrix.md — every approach below matches the note.
 */
public class ShortestPathBinaryMatrix {

    private static final int[] DR = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] DC = { -1, 0, 1, -1, 1, -1, 0, 1 };

    // APPROACH 1: DFS ENUMERATE PATHS (BRUTE)
    // Why: try all 8-direction paths; keep minimum length — not shortest-first.
    // Time: exponential worst   Extra space: O(n²)
    public static int shortestPathBinaryMatrixBrute(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
            return -1;
        }
        if (n == 1) {
            return 1;
        }
        int[] best = { Integer.MAX_VALUE };
        boolean[][] seen = new boolean[n][n];
        dfsPath(0, 0, 1, grid, seen, best);
        return best[0] == Integer.MAX_VALUE ? -1 : best[0];
    }

    private static void dfsPath(int r, int c, int dist, int[][] grid, boolean[][] seen, int[] best) {
        if (grid[r][c] == 1) {
            return;
        }
        if (dist >= best[0]) {
            return;
        }
        int n = grid.length;
        if (r == n - 1 && c == n - 1) {
            best[0] = Math.min(best[0], dist);
            return;
        }
        seen[r][c] = true;
        for (int d = 0; d < 8; d++) {
            int nr = r + DR[d], nc = c + DC[d];
            if (nr < 0 || nc < 0 || nr >= n || nc >= n || seen[nr][nc]) {
                continue;
            }
            dfsPath(nr, nc, dist + 1, grid, seen, best);
        }
        seen[r][c] = false;
    }

    // APPROACH 2: BFS (OPTIMAL — what we use)
    // Why: unweighted grid; first arrival at (n-1,n-1) is shortest cell count.
    // Time: O(n²)   Extra space: O(n²)
    public static int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
            return -1;
        }
        if (n == 1) {
            return 1;
        }

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
                int nr = r + DR[d], nc = c + DC[d];
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
        System.out.println("BFS:  " + shortestPathBinaryMatrix(copy(grid)));
        System.out.println("DFS:  " + shortestPathBinaryMatrixBrute(grid));

        int[][] grid2 = {
                { 0, 0, 0 },
                { 1, 1, 0 },
                { 1, 1, 0 },
        };
        System.out.println("3x3 BFS: " + shortestPathBinaryMatrix(grid2));
    }

    private static int[][] copy(int[][] g) {
        int[][] c = new int[g.length][];
        for (int i = 0; i < g.length; i++) {
            c[i] = g[i].clone();
        }
        return c;
    }
}