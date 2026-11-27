# Problem 10 — Shortest Path Binary Matrix

> **Pattern:** BFS 8-direction  
> **Goal:** Shortest clear path top-left to bottom-right.

---

## 1. Problem statement

See LeetCode / class **`ShortestPathBinaryMatrix.java`** in the repo for full constraints and examples.

**Core idea (one line):** 8 neighbors; dist in queue; block start/end 1.

---

## 2. Mental picture — ASCII

```text
0 cells, 8-neigh BFS, dist in state
```

Read the pattern as:

> **BFS 8-direction** — know what you store per node/edge and what you return when traversal finishes.

---

## 3. How to start thinking (simple questions)

1. **Graph type?** Directed vs undirected; weighted vs unweighted; implicit vs explicit.
2. **What is the node?** Index, coordinate, string, object reference.
3. **Can brute BFS/DFS work?** Usually yes for reachability; shortest unweighted → BFS; non‑negative weights → Dijkstra.
4. **Visited strategy?** `boolean[]`, hash set, mutate grid, or map for clones.
5. **Match repo methods?** Implement `shortestPathBinaryMatrix` so tests align with Java file.

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

File: `../ShortestPathBinaryMatrix.java` — use `shortestPathBinaryMatrix`.

---

## 6. Hardest part — before / after

### Before (confusion)

Mixing **visited timing** (enqueue vs dequeue), **directed edge direction**, or **skipping stale Dijkstra heap nodes**.

### After (clear)

**8 neighbors; dist in queue; block start/end 1.**

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
| Optimal (BFS 8-direction) | See pattern guide | `O(V+E)` or `O(n)` typical |

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

> “I'll model this as **BFS 8-direction**. Build the structure, run **shortestPathBinaryMatrix**, track visited/distance, and return the required value. Time is linear in vertices plus edges for traversal-style problems.”

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

See `../ShortestPathBinaryMatrix.java` for `package` and `main` smoke tests.

Compile from `PreparationGuide/` root:

```bash
javac DSA/Graphs/ShortestPathBinaryMatrix.java
java DSA.Graphs.ShortestPathBinaryMatrix
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

| Approach | Method in `ShortestPathBinaryMatrix.java` |
|---|---|
| Brute — DFS enumerate paths | `shortestPathBinaryMatrixBrute` |
| BFS 8-direction (preferred) | `shortestPathBinaryMatrix` |

Run `main` to compare DFS vs BFS path length.

---

## 16. Quick revision

1. **Pattern:** BFS 8-direction.
2. **Methods:** `shortestPathBinaryMatrix`.
3. **8 neighbors; dist in queue; block start/end 1.**
4. Check **visited** and **edge direction**.
5. Read `Graphs_Common_Patterns.md` or Trie/UF guide.

One line: **Shortest clear path top-left to bottom-right.**

---

## 17. Pattern used

**BFS 8-direction** — cross-link `../../Graphs/notes` pattern cheatsheet if present.

---

## 18. Next problem

**Problem —** — continue in `Graphs` track.

---

---

## Repository Java (full)

Align method names with `../ShortestPathBinaryMatrix.java`.

```java
package DSA.Graphs;

import java.util.*;

/*
 * Problem 10 — Shortest Path in Binary Matrix (LeetCode 1091)
 */
public class ShortestPathBinaryMatrix {

    public static int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
            return -1;
        }
        if (n == 1) {
            return 1;
        }
        int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };

        boolean[][] seen = new boolean[n][n];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.offer(new int[] { 0, 0, 1 });
        seen[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1], dist = cur[2];
            if (r == n - 1 && c == n - 1) {
                return dist;
            }
            for (int d = 0; d < 8; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr < 0 || nc < 0 || nr >= n || nc >= n) {
                    continue;
                }
                if (grid[nr][nc] == 1 || seen[nr][nc]) {
                    continue;
                }
                seen[nr][nc] = true;
                q.offer(new int[] { nr, nc, dist + 1 });
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[][] grid = { { 0, 1 }, { 1, 0 } };
        System.out.println("Shortest path length: " + shortestPathBinaryMatrix(grid));

        int[][] grid2 = {
                { 0, 0, 0 },
                { 1, 1, 0 },
                { 1, 1, 0 },
        };
        System.out.println("3x3 path: " + shortestPathBinaryMatrix(grid2));
    }
}
```
