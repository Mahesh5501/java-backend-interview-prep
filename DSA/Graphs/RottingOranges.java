package DSA.Graphs;

import java.util.*;

/*
Problem: Rotting Oranges (LeetCode 994)

See notes/Problem_07_Rotting_Oranges.md — every approach below matches the note.
 */
public class RottingOranges {

    // APPROACH 1: MINUTE-BY-MINUTE GRID SCAN (BRUTE)
    // Why: each round, every rotten orange tries to rot neighbors — clear but slow.
    // Time: O((m·n)²) worst   Extra space: O(m·n) copy
    public static int orangesRottingBrute(int[][] grid) {
        int[][] copy = deepCopy(grid);
        int minutes = 0;
        while (true) {
            boolean spread = false;
            for (int r = 0; r < copy.length; r++) {
                for (int c = 0; c < copy[0].length; c++) {
                    if (copy[r][c] != 2) {
                        continue;
                    }
                    spread |= rotNeighbors(copy, r, c);
                }
            }
            if (!spread) {
                break;
            }
            minutes++;
        }
        for (int[] row : copy) {
            for (int cell : row) {
                if (cell == 1) {
                    return -1;
                }
            }
        }
        return minutes;
    }

    private static boolean rotNeighbors(int[][] g, int r, int c) {
        int[] dr = { 1, -1, 0, 0 };
        int[] dc = { 0, 0, 1, -1 };
        boolean any = false;
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d], nc = c + dc[d];
            if (nr < 0 || nc < 0 || nr >= g.length || nc >= g[0].length) {
                continue;
            }
            if (g[nr][nc] == 1) {
                g[nr][nc] = 2;
                any = true;
            }
        }
        return any;
    }

    private static int[][] deepCopy(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }

    // APPROACH 2: MULTI-SOURCE BFS (OPTIMAL — what we use)
    // Why: enqueue all rotten cells; level sizes count minutes in one pass.
    // Time: O(m·n)   Extra space: O(m·n)
    public static int orangesRotting(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int fresh = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) {
                    q.offer(new int[] { r, c });
                } else if (grid[r][c] == 1) {
                    fresh++;
                }
            }
        }
        if (fresh == 0) {
            return 0;
        }
        int minutes = 0;
        int[] dr = { 1, -1, 0, 0 };
        int[] dc = { 0, 0, 1, -1 };

        while (!q.isEmpty()) {
            int size = q.size();
            boolean spread = false;
            for (int s = 0; s < size; s++) {
                int[] cell = q.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = cell[0] + dr[d];
                    int nc = cell[1] + dc[d];
                    if (nr < 0 || nc < 0 || nr >= rows || nc >= cols) {
                        continue;
                    }
                    if (grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        fresh--;
                        q.offer(new int[] { nr, nc });
                        spread = true;
                    }
                }
            }
            if (spread) {
                minutes++;
            }
        }
        return fresh == 0 ? minutes : -1;
    }

    public static void main(String[] args) {
        int[][] grid = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
        System.out.println("BFS minutes:   " + orangesRotting(deepCopy(grid)));
        System.out.println("Scan minutes:  " + orangesRottingBrute(grid));
    }
}