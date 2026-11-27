package DSA.Graphs;

import java.util.*;

/*
Problem: Clone Graph (LeetCode 133)

Deep-copy an undirected graph.

See notes/Problem_02_Clone_Graph.md — every approach below matches the note.
 */
public class CloneGraph {

    static class Node {
        int val;
        List<Node> neighbors;

        Node(int val) {
            this.val = val;
            neighbors = new ArrayList<>();
        }
    }

    // APPROACH 1: BFS + HASHMAP OLD→NEW
    // Why: level-order creates each node once; wire neighbors as edges are discovered.
    // Time: O(V + E)   Extra space: O(V)
    public static Node cloneBFS(Node node) {
        if (node == null) {
            return null;
        }
        Map<Node, Node> map = new HashMap<>();
        map.put(node, new Node(node.val));
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.offer(node);
        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (Node nei : cur.neighbors) {
                if (!map.containsKey(nei)) {
                    map.put(nei, new Node(nei.val));
                    q.offer(nei);
                }
                map.get(cur).neighbors.add(map.get(nei));
            }
        }
        return map.get(node);
    }

    // APPROACH 2: DFS + HASHMAP OLD→NEW (OPTIMAL — either BFS or DFS is fine)
    // Why: recursion visits each node once; map breaks cycles on revisits.
    // Time: O(V + E)   Extra space: O(V)
    public static Node cloneDFS(Node node) {
        if (node == null) {
            return null;
        }
        return dfs(node, new HashMap<>());
    }

    private static Node dfs(Node node, Map<Node, Node> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }
        Node copy = new Node(node.val);
        map.put(node, copy);
        for (Node nei : node.neighbors) {
            copy.neighbors.add(dfs(nei, map));
        }
        return copy;
    }

    public static void main(String[] args) {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        one.neighbors = Arrays.asList(two, four);
        two.neighbors = Arrays.asList(one, three);
        three.neighbors = Arrays.asList(two, four);
        four.neighbors = Arrays.asList(one, three);

        Node byBfs = cloneBFS(one);
        Node byDfs = cloneDFS(one);
        System.out.println("BFS clone val: " + byBfs.val + ", neighbors: " + byBfs.neighbors.size());
        System.out.println("DFS clone val: " + byDfs.val + ", neighbors: " + byDfs.neighbors.size());
        System.out.println("Different roots: " + (byBfs != one && byDfs != one));
    }
}