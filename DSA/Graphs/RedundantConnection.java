package DSA.Graphs;

import java.util.*;

/*
Problem: Redundant Connection (LeetCode 684)

See notes/Problem_09_Redundant_Connection.md — every approach below matches the note.
 */
public class RedundantConnection {

    static class UnionFind {
        int[] parent;
        int[] rank;

        UnionFind(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) {
                return false;
            }
            if (rank[ra] < rank[rb]) {
                parent[ra] = rb;
            } else if (rank[ra] > rank[rb]) {
                parent[rb] = ra;
            } else {
                parent[rb] = ra;
                rank[ra]++;
            }
            return true;
        }
    }

    // APPROACH 1: INCREMENTAL GRAPH + BFS REACHABILITY (BRUTE)
    // Why: before adding (u,v), if v is reachable from u, edge is redundant.
    // Time: O(E² · V)   Extra space: O(V + E)
    public static int[] findRedundantConnectionBrute(int[][] edges) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            if (reachable(adj, u, v)) {
                return e;
            }
            adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            adj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }
        return new int[0];
    }

    private static boolean reachable(Map<Integer, List<Integer>> adj, int src, int target) {
        if (src == target) {
            return true;
        }
        if (!adj.containsKey(src)) {
            return false;
        }
        ArrayDeque<Integer> q = new ArrayDeque<>();
        Set<Integer> seen = new HashSet<>();
        q.offer(src);
        seen.add(src);
        while (!q.isEmpty()) {
            int u = q.poll();
            if (u == target) {
                return true;
            }
            for (int v : adj.getOrDefault(u, List.of())) {
                if (seen.add(v)) {
                    q.offer(v);
                }
            }
        }
        return false;
    }

    // APPROACH 2: UNION-FIND (OPTIMAL — what we use)
    // Why: first edge that fails union already connects two components → cycle edge.
    // Time: O(E · α(n))   Extra space: O(n)
    public static int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(edges.length);
        for (int[] e : edges) {
            if (!uf.union(e[0], e[1])) {
                return e;
            }
        }
        return new int[0];
    }

    public static boolean isValidTree(int n, int[][] edges) {
        if (edges.length != n - 1) {
            return false;
        }
        UnionFind uf = new UnionFind(n);
        for (int[] e : edges) {
            if (!uf.union(e[0], e[1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] edges = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
        System.out.println("UF redundant:    " + Arrays.toString(findRedundantConnection(edges)));
        System.out.println("BFS redundant:   " + Arrays.toString(findRedundantConnectionBrute(edges)));
        int[][] tree = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
        System.out.println("Valid tree:      " + isValidTree(4, tree));
    }
}