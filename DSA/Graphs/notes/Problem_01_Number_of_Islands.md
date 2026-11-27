# Problem 01 — Number of Islands

> **Pattern:** Grid DFS/BFS flood fill  
> **Goal:** Count connected `'1'` land regions in a 2D grid (4-directional).

---

## 1. Problem statement

Given `m x n` grid `grid` of `'1'` (land) and `'0'` (water), return the number of **islands**.

An island is formed by connecting adjacent lands **horizontally or vertically** (not diagonally).

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
- `grid[i][j]` is `'0'` or `'1'`

---

## 2. Mental picture — one picture to keep in mind

```text
Scan order:

 1 1 0 0 0
 1 1 0 0 0    (A) big island — sink when you hit top-left 1
 0 0 1 0 0    (B) single cell
 0 0 0 1 1    (C) two-wide island

Paint / sink each blob once → count += 1 per new unvisited land.
```

```text
DFS from (0,0):

  [1]→[1]        both become 0
   ↓
  [1]→[1]
```

> **Each time you discover a `'1'`, you found a new island. Flood until water or boundary.**

---

## 3. How to start thinking

1. **Connected components on a grid** — same as graph CC on implicit 4-neighbor graph.
2. **Need visited?** Mutate grid `'1'→'0'` OR separate `visited[][]`.
3. **When to increment count?** When scan finds `'1'` **before** flood starts.
4. **DFS vs BFS?** Same complexity; BFS needs enqueue-time marking.
5. **Diagonals?** Not unless follow-up says 8-direction.

---

## 4. Approach 1 — Brute with `visited[][]`

### Java method

`countIslandsVisited` in `NumberOfIslands.java`

### Idea

Never change grid; on `'1'` not visited, `count++`, flood with `visited[r][c]=true`.

### Why it works

Each land cell belongs to exactly one component; visited prevents recounting.

### Complexity

| | visited[][] |
|---|---|
| **Time** | `O(m·n)` |
| **Space** | `O(m·n)` extra |

### Limitation

Extra memory; in-place sinking is simpler for interviews.

---

## 5. Approach 2 — Optimal in-place DFS (`sink`)

### Step-by-step

1. Loop all cells.
2. If `grid[r][c]=='1'`, `count++`, call `sink(r,c)`.
3. `sink`: base if OOB or not `'1'`; set `'0'`; recurse 4 neighbors.
4. Return `count`.

### Java (matches repo)

```java
public static int countIslandsDFS(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    int rows = grid.length, cols = grid[0].length, count = 0;
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == '1') {
                count++;
                sink(r, c, grid);
            }
        }
    }
    return count;
}

private static void sink(int r, int c, char[][] grid) {
    int rows = grid.length, cols = grid[0].length;
    if (r < 0 || c < 0 || r >= rows || c >= cols || grid[r][c] != '1') return;
    grid[r][c] = '0';
    sink(r + 1, c, grid);
    sink(r - 1, c, grid);
    sink(r, c + 1, grid);
    sink(r, c - 1, grid);
}
```

### Java method

`countIslandsDFS` in `NumberOfIslands.java`

### Approach 3 — BFS (`countIslandsBFS`)

### Java method

`countIslandsBFS` in `NumberOfIslands.java`

Mark `'0'` **when enqueueing** a neighbor `'1'` — prevents duplicate queue entries.

---

## 6. Hardest part — before / after

### Before

“BFS: should I mark visited when I pop?” → duplicates and TLE.

### After

**On push/enqueue:** set `grid[nr][nc]='0'` (or `visited=true`) before adding to queue.

### DFS stack depth

Worst-case snake island `O(m·n)` stack — still ok for LC bounds; iterative DFS + stack optional.

---

## 7. Detailed dry run table

Example grid above.

| scan `(r,c)` | cell | action | count |
|---:|---|---|---:|
| (0,0) | 1 | sink blob A | 1 |
| (0,2) | 0 | skip | 1 |
| (2,2) | 1 | sink B | 2 |
| (3,3) | 1 | sink C | 3 |

**After sink A:** top-left `2x2` region all `0`.

---

## 8. Why the algorithm is correct

Every land cell is scanned once as start or visited during flood. Each start `'1'` marks a new connected component not reachable from earlier floods. Flood marks entire component, so no double counting.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| visited[][] | `O(m·n)` | `O(m·n)` |
| in-place DFS | `O(m·n)` | `O(m·n)` stack worst |
| in-place BFS | `O(m·n)` | queue |

Each cell processed constant times.

---

## 10. Edge cases

| Case | Output |
|---|---:|
| All water | 0 |
| All land | 1 |
| 1×1 `'1'` | 1 |
| 1×1 `'0'` | 0 |
| null / empty array | 0 (guard) |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| 8 neighbors | Wrong count |
| No sink / no visited | Infinite recursion / wrong count |
| BFS mark on pop | Too many enqueues |
| `char` compared wrong | logic bug |

---

## 12. Interview — say this aloud

> “I scan the grid. When I see land, I increment the island count and run DFS or BFS to mark the whole four-connected component as visited — I sink to zero in place. Total time O(cells), space O(cells) for stack or queue in worst case.”

---

## 13. Follow-up Q&A

**Q: Diagonal islands?**  
A: 8 directions in neighbor loop.

**Q: Largest island area?**  
A: Return max flood size.

**Q: Destroy input?**  
A: Clone grid if mutation forbidden.

---

## 14. Complete runnable Java

`../NumberOfIslands.java` — `countIslandsDFS`, `countIslandsBFS`, `main`.

```bash
javac DSA/Graphs/NumberOfIslands.java
java DSA.Graphs.NumberOfIslands
```

---

## 15. Practice checklist

- [ ] DFS sink from memory.
- [ ] BFS with enqueue mark.
- [ ] Trace 3-island example.

---

---

## Depth addendum — extended workbook

### Brute force reminder

Enumerate states without memo — exponential. Optimal visits each vertex/edge once.

### Extended dry-run columns

| step | structure (queue/stack/map) | note |
|---:|---|---|

### Hardest-part drill

| Before | After |
|---|---|
| Cycle fear | Map or visited breaks infinite revisits |
| Wrong direction | Redraw edge arrows on paper |

### Interview say-aloud

> Model as graph pattern, build adjacency, run BFS/DFS/topo/UF, track visited — linear in V+E.

### Follow-up Q&A

**Q: Weighted?** Dijkstra. **Q: Multi-source?** Seed queue with all sources.

## Link to Java — which method is which

| Approach | Method in `NumberOfIslands.java` |
|---|---|
| Brute — `visited[][]` | `countIslandsVisited` |
| Optimal — in-place DFS | `countIslandsDFS` |
| BFS flood fill | `countIslandsBFS` |

Run `main` to compare all three on the sample grid.

---

## 16. Quick revision

1. **4-direction** only.
2. **Count on unseen `'1'`.**
3. **Flood mark** whole component.
4. **BFS mark on enqueue.**
5. **`O(m·n)` time.**

---

## 17. Pattern used

**Grid flood fill** — see `Graphs_Common_Patterns.md`.

---

## 18. Next problem

**Problem 02 — Clone Graph**

---

---

## Repository Java (full)

Align method names with `../NumberOfIslands.java`.

```java
package DSA.Graphs;

/*
 * Problem 01 — Number of Islands (LeetCode 200)
 * Pattern: Grid DFS/BFS flood fill
 */
public class NumberOfIslands {

    // Brute is same visit pattern — optimal is DFS/BFS O(m·n)
    public static int countIslandsDFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length, cols = grid[0].length;
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    sink(r, c, grid);
                }
            }
        }
        return count;
    }

    private static void sink(int r, int c, char[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        if (r < 0 || c < 0 || r >= rows || c >= cols || grid[r][c] != '1') {
            return;
        }
        grid[r][c] = '0';
        sink(r + 1, c, grid);
        sink(r - 1, c, grid);
        sink(r, c + 1, grid);
        sink(r, c - 1, grid);
    }

    public static int countIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length, cols = grid[0].length;
        int count = 0;
        int[] dr = { 1, -1, 0, 0 };
        int[] dc = { 0, 0, 1, -1 };
        java.util.ArrayDeque<int[]> q = new java.util.ArrayDeque<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '1') {
                    continue;
                }
                count++;
                grid[r][c] = '0';
                q.offer(new int[] { r, c });
                while (!q.isEmpty()) {
                    int[] cell = q.poll();
                    for (int d = 0; d < 4; d++) {
                        int nr = cell[0] + dr[d];
                        int nc = cell[1] + dc[d];
                        if (nr >= 0 && nc >= 0 && nr < rows && nc < cols && grid[nr][nc] == '1') {
                            grid[nr][nc] = '0';
                            q.offer(new int[] { nr, nc });
                        }
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        char[][] grid = {
                { '1', '1', '0', '0', '0' },
                { '1', '1', '0', '0', '0' },
                { '0', '0', '1', '0', '0' },
                { '0', '0', '0', '1', '1' },
        };
        char[][] copy = deepCopy(grid);
        System.out.println("DFS islands: " + countIslandsDFS(copy));
        System.out.println("BFS islands: " + countIslandsBFS(deepCopy(grid)));
    }

    private static char[][] deepCopy(char[][] g) {
        char[][] d = new char[g.length][g[0].length];
        for (int i = 0; i < g.length; i++) {
            System.arraycopy(g[i], 0, d[i], 0, g[i].length);
        }
        return d;
    }
}
```
