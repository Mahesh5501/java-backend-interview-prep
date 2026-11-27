# Problem 01 — Number of Islands

> **Pattern:** Grid DFS/BFS flood fill  
> **Goal:** Count connected `'1'` land regions in a 2D grid.

---

## 1. Problem statement

Given `m x n` grid `grid` of `'1'` (land) and `'0'` (water), return the number of **islands**. An island is land connected **4-directionally** (not diagonally).

### Example 1

```text
Input:  grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
```

### Constraints (typical)

- `m, n` up to ~300
- Each cell is `'0'` or `'1'`

---

## 2. Mental picture

```text
1 1 0 0 0        Scan left→right, top→bottom.
1 1 0 0 0        Hit a '1'? → new island, paint whole blob.
0 0 1 0 0
0 0 0 1 1
```

**Pattern A (grid flood fill)** from `Graphs_Common_Patterns.md`.

---

## 3. Approach 1 — Brute with `visited[][]`

Same as flood fill but keep original grid and use `boolean[][] visited`.

**Time:** `O(m·n)` **Space:** `O(m·n)`.

---

## 4. Approach 2 — Optimal (mutate or visit in-place)

1. Double loop over grid.
2. On `'1'`, increment `count`, run DFS/BFS to mark entire island as visited (e.g. set to `'0'`).
3. Return `count`.

### DFS sketch

```java
void sink(int r, int c) {
    if (out of bounds || grid[r][c] != '1') return;
    grid[r][c] = '0';
    sink(r+1,c); sink(r-1,c); sink(r,c+1); sink(r,c-1);
}
```

### Complexity

| | Optimal |
|---|---|
| **Time** | `O(m·n)` |
| **Space** | `O(m·n)` worst stack/queue |

---

## 5. Hardest part

Do not forget to **mark visited when you start BFS** (enqueue), not only when you pop — otherwise duplicates flood the queue.

---

## 6. Edge cases

| Case | Behavior |
|---|---|
| All water | `0` |
| All land | `1` |
| Single cell | `'1'` → 1, `'0'` → 0 |

---

## 7. Common mistakes

| Mistake | Effect |
|---|---|
| 8-directional by mistake | Wrong count |
| Not marking during BFS enqueue | TLE / wrong |
| Counting `'1'` without clearing | Double count |

---

## 8. Interview script

> “I'll scan the grid. Each unvisited land cell starts a new island. I DFS or BFS to mark the whole component visited. Each DFS is O(size of island), total O(cells).”

---

## 9. Follow-ups

- **Diagonal connections?** Change neighbor loops to 8 dirs.
- **Distinct shapes?** Canonical hashing — advanced.

---

## 10. Complete runnable Java

`../NumberOfIslands.java` (`package DSA.Graphs`).

```bash
# From PreparationGuide/
javac DSA/Graphs/NumberOfIslands.java
java DSA.Graphs.NumberOfIslands
```

---

## 11. Practice checklist

- [ ] Code DFS without hints.
- [ ] Code BFS without hints.
- [ ] Trace 3-island example on paper.

---

## 12. Pattern used

**Grid DFS/BFS flood fill.**

---

## 13. Next problem

**Problem 02 — Clone Graph**