package DSA.Graphs;

/*
 * Problem 01 — Number of Islands (LeetCode 200)
 * Pattern: Grid DFS/BFS flood fill
 */
public class NumberOfIslands {

    // Brute is same visit pattern — optimal is DFS/BFS O(m·n)
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

    public static int countIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length, cols = grid[0].length;
        int count = 0;
        int[] dr = { 1, -1, 0, 0 };
        int[] dc = { 0, 0, 1, -1 };
        java.util.ArrayDeque<int[]> q = new java.util.ArrayDeque<>();

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
        char[][] copy = deepCopy(grid);
        System.out.println("DFS islands: " + countIslandsDFS(copy));
        System.out.println("BFS islands: " + countIslandsBFS(deepCopy(grid)));
    }

    private static char[][] deepCopy(char[][] g) {
        char[][] d = new char[g.length][g[0].length];
        for (int i = 0; i < g.length; i++) {
            System.arraycopy(g[i], 0, d[i], 0, g[i].length);
        }
        return d;
    }
}