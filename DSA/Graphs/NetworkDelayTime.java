package DSA.Graphs;

import java.util.*;

/*
Problem: Network Delay Time (LeetCode 743)

See notes/Problem_08_Network_Delay_Time.md — every approach below matches the note.
 */
public class NetworkDelayTime {

    // APPROACH 2: DIJKSTRA MIN-HEAP (OPTIMAL — what we use)
    // Relax edges from smallest known distance; skip stale heap entries.
    // Time: O(E log V)   Extra space: O(V + E)
    public static int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] t : times) {
            adj.get(t[0]).add(new int[] { t[1], t[2] });
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[] { k, 0 });

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0], d = cur[1];
            if (d != dist[u]) {
                continue;
            }
            for (int[] e : adj.get(u)) {
                int v = e[0], w = e[1];
                if (d + w < dist[v]) {
                    dist[v] = d + w;
                    pq.offer(new int[] { v, dist[v] });
                }
            }
        }

        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }
            ans = Math.max(ans, dist[i]);
        }
        return ans;
    }

    // APPROACH 1: BELLMAN-FORD RELAXATION (BRUTE)
    // Repeat up to V-1 rounds relaxing every edge — correct on these LC inputs.
    // Time: O(V·E)   Extra space: O(V)
    public static int networkDelayBellmanFord(int[][] times, int n, int k) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean updated = false;
            for (int[] t : times) {
                int u = t[0], v = t[1], w = t[2];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    updated = true;
                }
            }
            if (!updated) {
                break;
            }
        }
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }
            ans = Math.max(ans, dist[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] times = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
        System.out.println("Dijkstra: " + networkDelayTime(times, 4, 2));
        System.out.println("Bellman-Ford: " + networkDelayBellmanFord(times, 4, 2));
    }
}