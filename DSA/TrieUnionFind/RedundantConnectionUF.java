package DSA.TrieUnionFind;

import java.util.Arrays;

/*
 * Problem 05 — Redundant Connection (Union-Find view) (LeetCode 684)
 */
public class RedundantConnectionUF {

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
        System.out.println("Redundant edge: " + Arrays.toString(findRedundantConnection(edges)));
    }
}