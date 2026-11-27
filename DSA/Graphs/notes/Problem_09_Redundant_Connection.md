# Problem 09 — Redundant Connection / Graph Valid Tree

> **Pattern:** Union-Find (or DFS)  
> **Goal:** Find edge that creates a cycle in an undirected graph, or verify a **valid tree**.

---

## 1. Problem statement

`n` nodes, edges added one by one. Return the **last** edge that connects two nodes already connected.

**Valid tree:** exactly `n-1` edges and **connected** (no cycle).

### Example

```text
Input:  edges = [[1,2],[1,3],[2,3]]
Output: [2,3]
```

---

## 2. Union-Find

For each edge `(u,v)`:
- if `find(u)==find(v)` → redundant;
- else `union(u,v)`.

---

## 3. Valid tree check

`|E| == n-1` and no redundant edge when processing.

---

## 4. Java

`../RedundantConnection.java` — `findRedundantConnection`, `isValidTree`.

---

## 5. Next

**Problem 10 — Shortest Path in Binary Matrix**