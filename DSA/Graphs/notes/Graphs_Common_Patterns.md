# Graphs — Common Patterns Reference

A reusable revision guide for Java interviews.

Pair this with `notes/Problem_XX_*.md` and runnable `.java` files (`package DSA.Graphs`).

```text
DSA/Graphs/
├── notes/          ← Graphs_Common_Patterns.md + Problem_XX_*.md
└── *.java          ← runnable programs
```

---

## 1. What is a graph problem?

Vertices (nodes) and **edges** (relationships). The input may be:

- **Adjacency list** (`List<List<Integer>>` or custom `Node`)
- **Edge list** (`int[][] edges`)
- **Implicit graph** (grid cells are nodes; neighbors are up/down/left/right or 8-way)

Ask in the interview:

1. **Directed or undirected?**
2. **Weighted or unweighted?**
3. **Cyclic?** Need cycle detection or topo sort?
4. **Connected components?**

---

## 2. How to recognise graph patterns

| Clue words | Likely technique |
|---|---|
| shortest path, fewest steps | **BFS** (unweighted) or **Dijkstra** (non-negative weights) |
| islands, connected regions | **DFS/BFS flood fill** on grid or graph |
| prerequisites, ordering | **Topological sort** (Kahn BFS or DFS postorder) |
| can finish / cycle | **Cycle detection** (indegree or DFS colors) |
| multi-source spread | **Multi-source BFS** (enqueue all sources at once) |
| redundant edge, tree check | **Union-Find** (also in `TrieUnionFind/`) |
| reach both borders | **Two DFS/BFS from borders** |

---

## 3. Pattern A — Grid DFS / BFS (flood fill)

```java
int[] dr = { -1, 1, 0, 0 };
int[] dc = { 0, 0, -1, 1 };

void dfs(int r, int c) {
    // mark visited, explore 4 neighbors in bounds
}
```

**When:** Number of Islands, rotten oranges style grids, Pacific Atlantic (from borders).

**Time:** `O(rows·cols)` — each cell visited once.

---

## 4. Pattern B — Graph BFS (shortest unweighted path)

```java
Queue<Integer> q = new ArrayDeque<>();
boolean[] seen = new boolean[n];
q.offer(start);
seen[start] = true;
while (!q.isEmpty()) {
    int size = q.size();
    for (int i = 0; i < size; i++) {
        int u = q.poll();
        for (int v : adj.get(u)) {
            if (!seen[v]) {
                seen[v] = true;
                q.offer(v);
            }
        }
    }
    // level++ for “minutes” / “distance”
}
```

**When:** Word Ladder, Shortest Path in Binary Matrix, Rotting Oranges.

---

## 5. Pattern C — Topological sort / cycle (courses)

**Kahn (BFS on indegree):**

```java
int[] indegree = new int[n];
// build graph; queue nodes with indegree 0; pop and relax
// if processed < n → cycle
```

**DFS three colors:** unvisited / visiting / done — back-edge to visiting ⇒ cycle.

**When:** Course Schedule I & II.

---

## 6. Pattern D — Dijkstra (non-negative weights)

```java
int[] dist = new int[n];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[src] = 0;
PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
pq.offer(new int[] { src, 0 });
while (!pq.isEmpty()) {
  int[] cur = pq.poll();
  int u = cur[0], d = cur[1];
  if (d != dist[u]) continue;
  for (int[] e : adj.get(u)) {
    int v = e[0], w = e[1];
    if (d + w < dist[v]) {
      dist[v] = d + w;
      pq.offer(new int[] { v, dist[v] });
    }
  }
}
```

**When:** Network Delay Time.

**Not for:** negative edges (use Bellman-Ford).

---

## 7. Pattern E — Union-Find (disjoint set)

See `TrieUnionFind/` for detailed UF notes; **Redundant Connection** in this folder also uses UF.

```java
// find with path compression; union by rank
```

**When:** detect cycle in undirected graph edge-by-edge; valid tree check (`|E| == n-1` and connected).

---

## 8. Pattern F — Clone / map old → new

BFS/DFS with `Map<Node, Node>` when copying graphs (**Clone Graph**).

---

## 9. Problem map (study order)

| # | Problem | Pattern |
|---:|---|---|
| 01 | Number of Islands | Grid DFS/BFS |
| 02 | Clone Graph | DFS/BFS + hash map |
| 03 | Course Schedule | Cycle / Kahn |
| 04 | Course Schedule II | Topological sort |
| 05 | Pacific Atlantic Water Flow | Multi-source DFS |
| 06 | Word Ladder | BFS shortest |
| 07 | Rotting Oranges | Multi-source BFS |
| 08 | Network Delay Time | Dijkstra |
| 09 | Redundant Connection / Valid Tree | Union-Find |
| 10 | Shortest Path in Binary Matrix | BFS 8-direction |

Study order: **01 → 10**.

---

## 10. Common mistakes

1. Forgetting to mark visited **when enqueuing** (BFS), not when dequeuing.
2. Off-by-one on grid bounds.
3. Using DFS for **shortest path in unweighted** graph without understanding it's not always wrong on unweighted—but BFS is safer for levels.
4. Topo sort on graph with **cycle** — check processed count.
5. Dijkstra without **stale entry check** (`if (d != dist[u]) continue`).

---

## 11. Complexity cheat sheet

| Technique | Typical time |
|---|---|
| DFS/BFS all nodes | `O(V + E)` |
| Grid flood fill | `O(m·n)` |
| Dijkstra + binary heap | `O(E log V)` |
| Union-Find (m ops) | `O(m α(n))` |

---

## 12. How to use this folder

1. Read this file.
2. Open problem notes in order.
3. Run matching `.java` from `DSA/Graphs/`.
4. Re-code without looking.