# Problem 10 — Shortest Path in Binary Matrix

> **Pattern:** BFS on grid (8 directions)  
> **Goal:** Shortest path length from top-left to bottom-right through `0` cells.

---

## 1. Problem statement

`grid[i][j]` is `0` (open) or `1` (blocked). Move 8-directionally including diagonals. Return path **length** (cell count), or `-1`.

### Example

```text
Input:  grid = [[0,1],[1,0]]
Output: 2
```

---

## 2. Mental picture

Unweighted shortest path → **BFS** with distance in queue state.

---

## 3. Optimal

Mark `seen` when enqueuing. First time you pop `(n-1,n-1)` return distance.

**Time:** `O(n²)`.

---

## 4. Java

`../ShortestPathBinaryMatrix.java`.

---

## 5. Pattern complete

Move to **DynamicProgramming/** starting with **Climbing Stairs**.