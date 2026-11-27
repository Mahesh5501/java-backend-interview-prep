# Problem 04 — Course Schedule II

> **Pattern:** Kahn topological order  
> **Goal:** Return course order or empty.

---

## 1. Problem statement

See LeetCode / class **`CourseScheduleII.java`** in the repo for full constraints and examples.

**Core idea (one line):** Same as 207 but output vertices; cycle → new int[0].

---

## 2. Mental picture — ASCII

```text
prereq b --> a (must take b before a)\nindegree[a]++; Kahn queue indegree 0
```

Read the pattern as:

> **Kahn topological order** — know what you store per node/edge and what you return when traversal finishes.

---

## 3. How to start thinking (simple questions)

1. **Graph type?** Directed vs undirected; weighted vs unweighted; implicit vs explicit.
2. **What is the node?** Index, coordinate, string, object reference.
3. **Can brute BFS/DFS work?** Usually yes for reachability; shortest unweighted → BFS; non‑negative weights → Dijkstra.
4. **Visited strategy?** `boolean[]`, hash set, mutate grid, or map for clones.
5. **Match repo methods?** Implement `findOrder` so tests align with Java file.

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

File: `../CourseScheduleII.java` — use `findOrder`.

---

## 6. Hardest part — before / after

### Before (confusion)

Mixing **visited timing** (enqueue vs dequeue), **directed edge direction**, or **skipping stale Dijkstra heap nodes**.

### After (clear)

**Same as 207 but output vertices; cycle → new int[0].**

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
| Optimal (Kahn topological order) | See pattern guide | `O(V+E)` or `O(n)` typical |

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

> “I'll model this as **Kahn topological order**. Build the structure, run **findOrder**, track visited/distance, and return the required value. Time is linear in vertices plus edges for traversal-style problems.”

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

See `../CourseScheduleII.java` for `package` and `main` smoke tests.

Compile from `PreparationGuide/` root:

```bash
javac DSA/Graphs/CourseScheduleII.java
java DSA.Graphs.CourseScheduleII
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

## 16. Quick revision

1. **Pattern:** Kahn topological order.
2. **Methods:** `findOrder`.
3. **Same as 207 but output vertices; cycle → new int[0].**
4. Check **visited** and **edge direction**.
5. Read `Graphs_Common_Patterns.md` or Trie/UF guide.

One line: **Return course order or empty.**

---

## 17. Pattern used

**Kahn topological order** — cross-link `../../Graphs/notes` pattern cheatsheet if present.

---

## 18. Next problem

**Problem 05** — continue in `Graphs` track.

---

---

## Repository Java (full)

Align method names with `../CourseScheduleII.java`.

```java
package DSA.Graphs;

import java.util.*;

/*
 * Problem 04 — Course Schedule II (LeetCode 210)
 */
public class CourseScheduleII {

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        int[] indegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            int a = p[0], b = p[1];
            adj.get(b).add(a);
            indegree[a]++;
        }
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }
        int[] order = new int[numCourses];
        int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            order[idx++] = u;
            for (int v : adj.get(u)) {
                if (--indegree[v] == 0) {
                    q.offer(v);
                }
            }
        }
        if (idx != numCourses) {
            return new int[0];
        }
        return order;
    }

    public static void main(String[] args) {
        int[][] pre = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
        System.out.println("Order: " + Arrays.toString(findOrder(4, pre)));

        int[][] bad = { { 1, 0 }, { 0, 1 } };
        System.out.println("Cycle order: " + Arrays.toString(findOrder(2, bad)));
    }
}
```
