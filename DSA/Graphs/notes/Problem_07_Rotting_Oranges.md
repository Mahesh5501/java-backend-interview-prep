# Problem 07 — Rotting Oranges

> **Pattern:** Multi-source BFS  
> **Goal:** Minutes until all fresh rotten or -1.

---

## 1. Problem statement

See LeetCode / class **`RottingOranges.java`** in the repo for full constraints and examples.

**Core idea (one line):** Enqueue all 2s; level BFS; track fresh count.

---

## 2. Mental picture — ASCII

```text
2 spreads to adjacent 1 each minute\nmulti-source BFS ring
```

Read the pattern as:

> **Multi-source BFS** — know what you store per node/edge and what you return when traversal finishes.

---

## 3. How to start thinking (simple questions)

1. **Graph type?** Directed vs undirected; weighted vs unweighted; implicit vs explicit.
2. **What is the node?** Index, coordinate, string, object reference.
3. **Can brute BFS/DFS work?** Usually yes for reachability; shortest unweighted → BFS; non‑negative weights → Dijkstra.
4. **Visited strategy?** `boolean[]`, hash set, mutate grid, or map for clones.
5. **Match repo methods?** Implement `orangesRotting` so tests align with Java file.

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

File: `../RottingOranges.java` — use `orangesRotting`.

---

## 6. Hardest part — before / after

### Before (confusion)

Mixing **visited timing** (enqueue vs dequeue), **directed edge direction**, or **skipping stale Dijkstra heap nodes**.

### After (clear)

**Enqueue all 2s; level BFS; track fresh count.**

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
| Optimal (Multi-source BFS) | See pattern guide | `O(V+E)` or `O(n)` typical |

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

> “I'll model this as **Multi-source BFS**. Build the structure, run **orangesRotting**, track visited/distance, and return the required value. Time is linear in vertices plus edges for traversal-style problems.”

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

See `../RottingOranges.java` for `package` and `main` smoke tests.

Compile from `PreparationGuide/` root:

```bash
javac DSA/Graphs/RottingOranges.java
java DSA.Graphs.RottingOranges
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

| Approach | Method in `RottingOranges.java` |
|---|---|
| Brute — scan grid every minute | `orangesRottingBrute` |
| Multi-source BFS (preferred) | `orangesRotting` |

Run `main` to compare scan simulation vs BFS.

---

## 16. Quick revision

1. **Pattern:** Multi-source BFS.
2. **Methods:** `orangesRotting`.
3. **Enqueue all 2s; level BFS; track fresh count.**
4. Check **visited** and **edge direction**.
5. Read `Graphs_Common_Patterns.md` or Trie/UF guide.

One line: **Minutes until all fresh rotten or -1.**

---

## 17. Pattern used

**Multi-source BFS** — cross-link `../../Graphs/notes` pattern cheatsheet if present.

---

## 18. Next problem

**Problem 08** — continue in `Graphs` track.

---

---

## Repository Java (full)

Align method names with `../RottingOranges.java`.

```java
package DSA.Graphs;

import java.util.*;

/*
 * Problem 07 — Rotting Oranges (LeetCode 994)
 */
public class RottingOranges {

    public static int orangesRotting(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int fresh = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) {
                    q.offer(new int[] { r, c });
                } else if (grid[r][c] == 1) {
                    fresh++;
                }
            }
        }
        if (fresh == 0) {
            return 0;
        }
        int minutes = 0;
        int[] dr = { 1, -1, 0, 0 };
        int[] dc = { 0, 0, 1, -1 };

        while (!q.isEmpty()) {
            int size = q.size();
            boolean spread = false;
            for (int s = 0; s < size; s++) {
                int[] cell = q.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = cell[0] + dr[d];
                    int nc = cell[1] + dc[d];
                    if (nr < 0 || nc < 0 || nr >= rows || nc >= cols) {
                        continue;
                    }
                    if (grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        fresh--;
                        q.offer(new int[] { nr, nc });
                        spread = true;
                    }
                }
            }
            if (spread) {
                minutes++;
            }
        }
        return fresh == 0 ? minutes : -1;
    }

    public static void main(String[] args) {
        int[][] grid = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
        System.out.println("Minutes: " + orangesRotting(grid));
    }
}
```
