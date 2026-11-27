package DSA.Graphs;

import java.util.*;

/*
Problem: Pacific Atlantic Water Flow (LeetCode 417)

See notes/Problem_05_Pacific_Atlantic_Water_Flow.md — every approach below matches the note.
 */
public class PacificAtlanticWaterFlow {

    private static final int[] DR = { 1, -1, 0, 0 };
    private static final int[] DC = { 0, 0, 1, -1 };

    // APPROACH 1: PER-CELL FORWARD DFS (BRUTE)
    // Why: from each cell, DFS downhill to see if both oceans reachable — easy but repeats work.
    // Time: O(m²·n²) worst   Extra space: O(m·n) per cell
    public static List<List<Integer>> pacificAtlanticBrute(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0) {
            return result;
        }
        int m = heights.length, n = heights[0].length;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                boolean[][] visP = new boolean[m][n];
                boolean[][] visA = new boolean[m][n];
                boolean toPacific = canReachOcean(r, c, heights, visP, true);
                boolean toAtlantic = canReachOcean(r, c, heights, visA, false);
                if (toPacific && toAtlantic) {
                    result.add(Arrays.asList(r, c));
                }
            }
        }
        return result;
    }

    private static boolean canReachOcean(int r, int c, int[][] h, boolean[][] vis, boolean pacific) {
        int m = h.length, n = h[0].length;
        if (pacific && (r == 0 || c == 0)) {
            return true;
        }
        if (!pacific && (r == m - 1 || c == n - 1)) {
            return true;
        }
        vis[r][c] = true;
        for (int d = 0; d < 4; d++) {
            int nr = r + DR[d], nc = c + DC[d];
            if (nr < 0 || nc < 0 || nr >= m || nc >= n || vis[nr][nc]) {
                continue;
            }
            if (h[nr][nc] <= h[r][c] && canReachOcean(nr, nc, h, vis, pacific)) {
                return true;
            }
        }
        return false;
    }

    // APPROACH 2: REVERSE DFS FROM OCEAN BORDERS (OPTIMAL — what we use)
    // Why: water flows to oceans — walk uphill from borders; intersect marks both-reachable cells.
    // Time: O(m·n)   Extra space: O(m·n)
    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0) {
            return result;
        }
        int m = heights.length, n = heights[0].length;
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        for (int c = 0; c < n; c++) {
            dfsReverse(0, c, heights, pacific);
            dfsReverse(m - 1, c, heights, atlantic);
        }
        for (int r = 0; r < m; r++) {
            dfsReverse(r, 0, heights, pacific);
            dfsReverse(r, n - 1, heights, atlantic);
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

    private static void dfsReverse(int r, int c, int[][] h, boolean[][] reach) {
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
                dfsReverse(nr, nc, h, reach);
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
        List<List<Integer>> opt = pacificAtlantic(heights);
        List<List<Integer>> brute = pacificAtlanticBrute(heights);
        System.out.println("Optimal count: " + opt.size() + "  Brute count: " + brute.size());
        System.out.println("Match: " + opt.equals(brute));
    }
}