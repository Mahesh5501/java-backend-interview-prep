# Problem 04 — Number of Provinces

> **Pattern:** Union-Find / DFS  
> **Goal:** Connected components in adjacency matrix.

---

## 1. Problem statement

See LeetCode / class **`NumberOfProvinces.java`** in the repo for full constraints and examples.

**Core idea (one line):** Union or DFS count components.

---

## 2. Mental picture — ASCII

```text
matrix[i][j]=1 => edge; count components
```

Read the pattern as:

> **Union-Find / DFS** — know what you store per node/edge and what you return when traversal finishes.

---

## 3. How to start thinking (simple questions)

1. **Graph type?** Directed vs undirected; weighted vs unweighted; implicit vs explicit.
2. **What is the node?** Index, coordinate, string, object reference.
3. **Can brute BFS/DFS work?** Usually yes for reachability; shortest unweighted → BFS; non‑negative weights → Dijkstra.
4. **Visited strategy?** `boolean[]`, hash set, mutate grid, or map for clones.
5. **Match repo methods?** Implement `findCircleNum` so tests align with Java file.

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

File: `../NumberOfProvinces.java` — use `findCircleNum`.

---

## 6. Hardest part — before / after

### Before (confusion)

Mixing **visited timing** (enqueue vs dequeue), **directed edge direction**, or **skipping stale Dijkstra heap nodes**.

### After (clear)

**Union or DFS count components.**

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
| Optimal (Union-Find) | See pattern guide | `O(V+E)` or `O(n)` typical |

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

> “I'll model this as **Union-Find / DFS**. Build the structure, run **findCircleNum**, track visited/distance, and return the required value. Time is linear in vertices plus edges for traversal-style problems.”

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

See `../NumberOfProvinces.java` for `package` and `main` smoke tests.

Compile from `PreparationGuide/` root:

```bash
javac DSA/TrieUnionFind/NumberOfProvinces.java
java DSA.TrieUnionFind.NumberOfProvinces
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

| Approach | Method in `NumberOfProvinces.java` |
|---|---|
| DFS flood fill | `findCircleNumDFS` |
| Union-Find (preferred) | `findCircleNumUF` |

Run `main` to compare DFS vs UF province count.

---

## 16. Quick revision

1. **Pattern:** Union-Find / DFS.
2. **Methods:** `findCircleNumDFS`, `findCircleNumUF`.
3. **Union or DFS count components.**
4. Check **visited** and **edge direction**.
5. Read `Graphs_Common_Patterns.md` or Trie/UF guide.

One line: **Connected components in adjacency matrix.**

---

## 17. Pattern used

**Union-Find / DFS** — cross-link `../../TrieUnionFind/notes` pattern cheatsheet if present.

---

## 18. Next problem

**Problem 05** — continue in `TrieUnionFind` track.

---

---

## Repository Java (full)

Align method names with `../NumberOfProvinces.java`.

```java
package DSA.TrieUnionFind;

/*
 * Problem 04 — Number of Provinces (LeetCode 547)
 */
public class NumberOfProvinces {

    public static int findCircleNumUF(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.getComponents();
    }

    public static int findCircleNumDFS(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] seen = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!seen[i]) {
                dfs(i, isConnected, seen);
                count++;
            }
        }
        return count;
    }

    private static void dfs(int u, int[][] m, boolean[] seen) {
        seen[u] = true;
        for (int v = 0; v < m.length; v++) {
            if (m[u][v] == 1 && !seen[v]) {
                dfs(v, m, seen);
            }
        }
    }

    public static void main(String[] args) {
        int[][] mat = { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } };
        System.out.println("UF provinces: " + findCircleNumUF(mat));
        System.out.println("DFS provinces: " + findCircleNumDFS(mat));
    }
}
```
