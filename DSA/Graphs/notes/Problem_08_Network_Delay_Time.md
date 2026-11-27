# Problem 08 — Network Delay Time

> **Pattern:** Dijkstra (non-negative weights)  
> **Goal:** Time for signal from node `k` to reach all nodes.

---

## 1. Problem statement

Directed weighted edges `times[i] = [u, v, w]`. Return **max** distance to any node, or `-1` if unreachable.

### Example

```text
Input:  times=[[2,1,1],[2,3,1],[3,4,1]], n=4, k=2
Output: 2
```

---

## 2. Mental picture

Shortest paths from source — but answer is **worst** arrival time (bottleneck).

---

## 3. Brute

Bellman-Ford: relax all edges `n-1` times — `O(V·E)` teaching version.

---

## 4. Optimal — Dijkstra

Min-heap `(node, dist)`. Skip stale heap entries: `if (d != dist[u]) continue`.

**Time:** `O(E log V)`.

---

## 5. Java

`../NetworkDelayTime.java` — Dijkstra + Bellman-Ford demo.

---

## 6. Next

**Problem 09 — Redundant Connection**