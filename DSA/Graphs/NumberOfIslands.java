package DSA.Graphs;

import java.util.ArrayDeque;

/*
 * Problem 01 — Number of Islands (LeetCode 200)
 * Pattern: Grid DFS/BFS flood fill

See notes/Problem_01_Number_of_Islands.md — every approach below matches the note.
 */
public class NumberOfIslands {

    // APPROACH 1: DFS + visited[][]
    // Never mutate grid; mark visited when entering a land cell.
    // Why use it: preserves input — needed when grid must stay unchanged.
    // Time: O(m·n)   Extra space: O(m·n)
    public static int countIslandsVisited(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1' && !visited[r][c]) {
                    count++;
                    floodVisited(r, c, grid, visited);
                }
            }
        }
        return count;
    }

    private static void floodVisited(int r, int c, char[][] grid, boolean[][] visited) {
        int rows = grid.length, cols = grid[0].length;
        if (r < 0 || c < 0 || r >= rows || c >= cols || grid[r][c] != '1' || visited[r][c]) {
            return;
        }
        visited[r][c] = true;
        floodVisited(r + 1, c, grid, visited);
        floodVisited(r - 1, c, grid, visited);
        floodVisited(r, c + 1, grid, visited);
        floodVisited(r, c - 1, grid, visited);
    }

    // APPROACH 2: IN-PLACE DFS SINK (OPTIMAL — what we use)
    // Flip '1'→'0' while flooding — visited is encoded in the grid.
    // Why use it: O(1) extra space; typical interview solution.
    // Time: O(m·n)   Extra space: O(m·n) recursion stack worst case
    public static int countIslandsDFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length, cols = grid[0].length;
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    sink(r, c, grid);
                }
            }
        }
        return count;
    }

    private static void sink(int r, int c, char[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        if (r < 0 || c < 0 || r >= rows || c >= cols || grid[r][c] != '1') {
            return;
        }
        grid[r][c] = '0';
        sink(r + 1, c, grid);
        sink(r - 1, c, grid);
        sink(r, c + 1, grid);
        sink(r, c - 1, grid);
    }

    // APPROACH 3: BFS QUEUE
    // Same counting rule; mark neighbor when enqueueing to avoid duplicates.
    // Why BFS: avoids deep recursion on snake-shaped islands.
    // Time: O(m·n)   Extra space: O(min(m,n)) queue typical
    public static int countIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length, cols = grid[0].length;
        int count = 0;
        int[] dr = { 1, -1, 0, 0 };
        int[] dc = { 0, 0, 1, -1 };
        ArrayDeque<int[]> q = new ArrayDeque<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '1') {
                    continue;
                }
                count++;
                grid[r][c] = '0';
                q.offer(new int[] { r, c });
                while (!q.isEmpty()) {
                    int[] cell = q.poll();
                    for (int d = 0; d < 4; d++) {
                        int nr = cell[0] + dr[d];
                        int nc = cell[1] + dc[d];
                        if (nr >= 0 && nc >= 0 && nr < rows && nc < cols && grid[nr][nc] == '1') {
                            grid[nr][nc] = '0';
                            q.offer(new int[] { nr, nc });
                        }
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        char[][] grid = {
                { '1', '1', '0', '0', '0' },
                { '1', '1', '0', '0', '0' },
                { '0', '0', '1', '0', '0' },
                { '0', '0', '0', '1', '1' },
        };
        System.out.println("Visited: " + countIslandsVisited(deepCopy(grid)));
        System.out.println("DFS:     " + countIslandsDFS(deepCopy(grid)));
        System.out.println("BFS:     " + countIslandsBFS(deepCopy(grid)));
    }

    private static char[][] deepCopy(char[][] g) {
        char[][] d = new char[g.length][g[0].length];
        for (int i = 0; i < g.length; i++) {
            System.arraycopy(g[i], 0, d[i], 0, g[i].length);
        }
        return d;
    }
}