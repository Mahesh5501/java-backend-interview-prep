package DSA.TrieUnionFind;

/*
 * Problem 04 — Number of Provinces (LeetCode 547)
 */
public class NumberOfProvinces {

    public static int findCircleNumUF(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.getComponents();
    }

    public static int findCircleNumDFS(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] seen = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!seen[i]) {
                dfs(i, isConnected, seen);
                count++;
            }
        }
        return count;
    }

    private static void dfs(int u, int[][] m, boolean[] seen) {
        seen[u] = true;
        for (int v = 0; v < m.length; v++) {
            if (m[u][v] == 1 && !seen[v]) {
                dfs(v, m, seen);
            }
        }
    }

    public static void main(String[] args) {
        int[][] mat = { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } };
        System.out.println("UF provinces: " + findCircleNumUF(mat));
        System.out.println("DFS provinces: " + findCircleNumDFS(mat));
    }
}