# Problem 05 — Pacific Atlantic Water Flow

> **Pattern:** Multi-source DFS from oceans  
> **Goal:** Cells draining to both oceans.

---

## 1. Problem statement

See LeetCode / class **`PacificAtlanticWaterFlow.java`** in the repo for full constraints and examples.

**Core idea (one line):** DFS from Pacific borders and Atlantic borders; intersect reachability.

---

## 2. Mental picture — ASCII

```text
ocean borders --DFS uphill--> reachable\nintersect Pacific & Atlantic sets
```

Read the pattern as:

> **Multi-source DFS from oceans** — know what you store per node/edge and what you return when traversal finishes.

---

## 3. How to start thinking (simple questions)

1. **Graph type?** Directed vs undirected; weighted vs unweighted; implicit vs explicit.
2. **What is the node?** Index, coordinate, string, object reference.
3. **Can brute BFS/DFS work?** Usually yes for reachability; shortest unweighted → BFS; non‑negative weights → Dijkstra.
4. **Visited strategy?** `boolean[]`, hash set, mutate grid, or map for clones.
5. **Match repo methods?** Implement `pacificAtlantic` so tests align with Java file.

---

## 4. Approach 1 — Brute force

### Idea

Enumerate all possibilities without reusing work (full search, repeated states).

### Why we avoid it

Overlapping subproblems / exponential states — too slow at interview constraints.

### Typical complexity

| | Brute |
|---|---|
| **Time** | Exponential or `O(n! / …)` |
| **Space** | Proportional to recursion depth |

---

## 5. Approach 2 — Optimal (pattern standard)

### Step-by-step

1. Build adjacency / map / trie as required.
2. Choose BFS, DFS, Kahn, Dijkstra, or Union-Find per pattern above.
3. Track visited / distance / indegree / parent / clone map.
4. Stop when goal met or queue empty; validate cycles or unreachable nodes.

### Why it is safe

Each canonical state processed once (with correct visited / dist relax rules).

### Java entry points

File: `../PacificAtlanticWaterFlow.java` — `pacificAtlanticBrute`, `pacificAtlantic`.

---

## 6. Hardest part — before / after

### Before (confusion)

Mixing **visited timing** (enqueue vs dequeue), **directed edge direction**, or **skipping stale Dijkstra heap nodes**.

### After (clear)

**DFS from Pacific borders and Atlantic borders; intersect reachability.**

---

## 7. Detailed dry run table

| step | state / queue | action | note |
|---:|---|---|---|
| 0 | init | load input | set bases |
| 1 | expand | push neighbors | mark visited |
| 2 | … | … | stop when goal |

Trace the **small example** from the Java `main` method on paper before coding.

---

## 8. Why the algorithm is correct

**Safety:** Valid edges only; no illegal moves.  
**Completeness:** BFS/DFS covers reachable component; topo visits all nodes iff acyclic; Dijkstra relaxes optimally with non‑negative weights.  
**Termination:** Finite graph / grid bounds.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---|---|
| Brute search | Too large | High |
| Optimal (Multi-source DFS from oceans) | See pattern guide | `O(V+E)` or `O(n)` typical |

---

## 10. Edge cases

| Case | Expected behavior |
|---|---|
| Empty input / null | Return problem-specific sentinel |
| Single node | Often base answer = 0 or 1 |
| Disconnected | BFS/Dijkstra may return -1 / false |
| Cycle (directed) | Topo fails; UF returns bad edge |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| Wrong edge direction | Wrong topo / wrong reachability |
| No visited on implicit graph | TLE / infinite loop |
| BFS mark on pop not push | Duplicates in queue |
| Forget `endWord` in dict (Word Ladder) | Return 0 incorrectly |

---

## 12. Interview — say this aloud

> “I'll model this as **Multi-source DFS from oceans**. Build the structure, run **pacificAtlantic**, track visited/distance, and return the required value. Time is linear in vertices plus edges for traversal-style problems.”

---

## 13. Follow-up Q&A

**Q: BFS or DFS?**  
A: Shortest steps in unweighted → BFS; path existence / cloning / flood fill → either with care.

**Q: Can we use Union-Find here instead?**  
A: If question is dynamic connectivity or redundant edge — yes; see TrieUnionFind folder variants.

**Q: Space optimize?**  
A: In-place grid marking, iterative DFS, or parent array instead of full stack log.

---

## 14. Complete runnable Java

See `../PacificAtlanticWaterFlow.java` for `package` and `main` smoke tests.

Compile from `PreparationGuide/` root:

```bash
javac DSA/Graphs/PacificAtlanticWaterFlow.java
java DSA.Graphs.PacificAtlanticWaterFlow
```

---

## 15. Practice checklist

- [ ] Explain pattern without opening notes.
- [ ] Code optimal from memory matching repo method names.
- [ ] Dry-run mini example on paper.
- [ ] State time/space aloud.
- [ ] Handle one edge case deliberately.

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

| Approach | Method in `PacificAtlanticWaterFlow.java` |
|---|---|
| Brute — per-cell forward DFS | `pacificAtlanticBrute` |
| Reverse DFS from ocean borders (preferred) | `pacificAtlantic` |

Run `main` to verify brute and optimal agree on the sample grid.

---

## 16. Quick revision

1. **Pattern:** Multi-source DFS from oceans.
2. **Methods:** `pacificAtlantic`.
3. **DFS from Pacific borders and Atlantic borders; intersect reachability.**
4. Check **visited** and **edge direction**.
5. Read `Graphs_Common_Patterns.md` or Trie/UF guide.

One line: **Cells draining to both oceans.**

---

## 17. Pattern used

**Multi-source DFS from oceans** — cross-link `../../Graphs/notes` pattern cheatsheet if present.

---

## 18. Next problem

**Problem 06** — continue in `Graphs` track.

---

---

## Repository Java (full)

Align method names with `../PacificAtlanticWaterFlow.java`.

```java
package DSA.Graphs;

import java.util.*;

/*
 * Problem 05 — Pacific Atlantic Water Flow (LeetCode 417)
 */
public class PacificAtlanticWaterFlow {

    private static final int[] DR = { 1, -1, 0, 0 };
    private static final int[] DC = { 0, 0, 1, -1 };

    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0) {
            return result;
        }
        int m = heights.length, n = heights[0].length;
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        for (int c = 0; c < n; c++) {
            dfs(0, c, heights, pacific);
            dfs(m - 1, c, heights, atlantic);
        }
        for (int r = 0; r < m; r++) {
            dfs(r, 0, heights, pacific);
            dfs(r, n - 1, heights, atlantic);
        }

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (pacific[r][c] && atlantic[r][c]) {
                    result.add(Arrays.asList(r, c));
                }
            }
        }
        return result;
    }

    private static void dfs(int r, int c, int[][] h, boolean[][] reach) {
        reach[r][c] = true;
        for (int d = 0; d < 4; d++) {
            int nr = r + DR[d], nc = c + DC[d];
            if (nr < 0 || nc < 0 || nr >= h.length || nc >= h[0].length) {
                continue;
            }
            if (reach[nr][nc]) {
                continue;
            }
            if (h[nr][nc] >= h[r][c]) {
                dfs(nr, nc, h, reach);
            }
        }
    }

    public static void main(String[] args) {
        int[][] heights = {
                { 1, 2, 2, 3, 5 },
                { 3, 2, 3, 4, 4 },
                { 2, 4, 5, 3, 1 },
                { 6, 7, 1, 4, 5 },
                { 5, 1, 1, 2, 4 },
        };
        System.out.println("Cells: " + pacificAtlantic(heights));
    }
}
```
