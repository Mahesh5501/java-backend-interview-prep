package DSA.TrieUnionFind;

/*
 * Problem 04 — Number of Provinces (LeetCode 547)

See notes/Problem_04_Number_of_Provinces.md — every approach below matches the note.
 */
public class NumberOfProvinces {

    // APPROACH 1: DFS FLOOD FILL (BRUTE / BASELINE)
    // Each unvisited city starts a new province; DFS marks component.
    // Time: O(n^2)   Extra space: O(n)
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

    // APPROACH 2: UNION-FIND (OPTIMAL — what we use)
    // Union adjacent cities; count disjoint sets.
    // Time: O(n^2 · α(n))   Extra space: O(n)
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

    public static void main(String[] args) {
        int[][] mat = { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } };
        System.out.println("UF provinces: " + findCircleNumUF(mat));
        System.out.println("DFS provinces: " + findCircleNumDFS(mat));
    }
}