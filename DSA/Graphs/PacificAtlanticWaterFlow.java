package DSA.Graphs;

import java.util.*;

/*
 * Problem 05 — Pacific Atlantic Water Flow (LeetCode 417)
 */
public class PacificAtlanticWaterFlow {

    private static final int[] DR = { 1, -1, 0, 0 };
    private static final int[] DC = { 0, 0, 1, -1 };

    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0) {
            return result;
        }
        int m = heights.length, n = heights[0].length;
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        for (int c = 0; c < n; c++) {
            dfs(0, c, heights, pacific);
            dfs(m - 1, c, heights, atlantic);
        }
        for (int r = 0; r < m; r++) {
            dfs(r, 0, heights, pacific);
            dfs(r, n - 1, heights, atlantic);
        }

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (pacific[r][c] && atlantic[r][c]) {
                    result.add(Arrays.asList(r, c));
                }
            }
        }
        return result;
    }

    private static void dfs(int r, int c, int[][] h, boolean[][] reach) {
        reach[r][c] = true;
        for (int d = 0; d < 4; d++) {
            int nr = r + DR[d], nc = c + DC[d];
            if (nr < 0 || nc < 0 || nr >= h.length || nc >= h[0].length) {
                continue;
            }
            if (reach[nr][nc]) {
                continue;
            }
            if (h[nr][nc] >= h[r][c]) {
                dfs(nr, nc, h, reach);
            }
        }
    }

    public static void main(String[] args) {
        int[][] heights = {
                { 1, 2, 2, 3, 5 },
                { 3, 2, 3, 4, 4 },
                { 2, 4, 5, 3, 1 },
                { 6, 7, 1, 4, 5 },
                { 5, 1, 1, 2, 4 },
        };
        System.out.println("Cells: " + pacificAtlantic(heights));
    }
}