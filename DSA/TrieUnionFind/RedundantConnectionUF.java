package DSA.TrieUnionFind;

import java.util.*;

/*
Problem: Redundant Connection (Union-Find view) (LeetCode 684)

See notes/Problem_05_Redundant_Connection_UF.md — every approach below matches the note.
 */
public class RedundantConnectionUF {

    // APPROACH 1: INCREMENTAL GRAPH + BFS (BRUTE)
    // Why: if v reachable from u before adding edge, that edge closes a cycle.
    // Time: O(E² · V)   Extra space: O(V + E)
    public static int[] findRedundantConnectionBrute(int[][] edges) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            if (bfsReachable(adj, u, v)) {
                return e;
            }
            adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            adj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }
        return new int[0];
    }

    private static boolean bfsReachable(Map<Integer, List<Integer>> adj, int src, int target) {
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
    // Why: near-constant time connectivity; first failed union is the answer edge.
    // Time: O(E · α(n))   Extra space: O(n)
    public static int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        UnionFind uf = new UnionFind(n + 1);
        for (int[] e : edges) {
            if (!uf.union(e[0], e[1])) {
                return e;
            }
        }
        return new int[0];
    }

    public static void main(String[] args) {
        int[][] edges = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
        System.out.println("UF:    " + Arrays.toString(findRedundantConnection(edges)));
        System.out.println("BFS:   " + Arrays.toString(findRedundantConnectionBrute(edges)));
    }
}