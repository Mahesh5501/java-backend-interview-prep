# Problem 02 — Clone Graph

> **Pattern:** BFS/DFS + `Map<Node, Node>`  
> **Goal:** Deep copy an undirected graph.

---

## 1. Problem statement

Each node has `val` and `neighbors`. Return a **copy** of the entire graph (new node objects, same structure).

### Example

```text
Input:  node 1 connected to 2 and 4 (as in LeetCode sample)
Output: deep copy with same adjacency
```

---

## 2. Mental picture

Old node → new node map. When you first visit a node, create its clone. Wire `neighbors` using clones only.

---

## 3. Brute

Same as optimal — you must visit every node once. Avoid infinite loops with the map.

---

## 4. Optimal — BFS or DFS

```text
Map original → clone
DFS(nei): if not in map, create clone; clone.neighbors.add(clone(nei))
```

**Time:** `O(V+E)` **Space:** `O(V)`.

---

## 5. Java

`../CloneGraph.java` — `cloneDFS`, `cloneBFS`.

---

## 6. Next

**Problem 03 — Course Schedule**