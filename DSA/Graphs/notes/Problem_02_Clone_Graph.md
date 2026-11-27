# Problem 02 — Clone Graph

> **Pattern:** BFS/DFS + HashMap<Node,Node>  
> **Goal:** Deep copy undirected graph nodes.

---

## 1. Problem statement

See LeetCode / class **`CloneGraph.java`** in the repo for full constraints and examples.

**Core idea (one line):** Map each original node to a new Node; wire neighbors through the map.

---

## 2. Mental picture — ASCII

```text
old node --map--> new node\n  |                  |\n  v                  v\n neighbors ----> cloned neighbors
```

Read the pattern as:

> **BFS/DFS + HashMap<Node,Node>** — know what you store per node/edge and what you return when traversal finishes.

---

## 3. How to start thinking (simple questions)

1. **Graph type?** Directed vs undirected; weighted vs unweighted; implicit vs explicit.
2. **What is the node?** Index, coordinate, string, object reference.
3. **Can brute BFS/DFS work?** Usually yes for reachability; shortest unweighted → BFS; non‑negative weights → Dijkstra.
4. **Visited strategy?** `boolean[]`, hash set, mutate grid, or map for clones.
5. **Match repo methods?** Implement `cloneBFS`, `cloneDFS` so tests align with Java file.

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

File: `../CloneGraph.java` — `cloneBFS`, `cloneDFS`.

---

## 6. Hardest part — before / after

### Before (confusion)

Mixing **visited timing** (enqueue vs dequeue), **directed edge direction**, or **skipping stale Dijkstra heap nodes**.

### After (clear)

**Map each original node to a new Node; wire neighbors through the map.**

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
| Optimal (BFS) | See pattern guide | `O(V+E)` or `O(n)` typical |

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

> “I'll model this as **BFS/DFS + HashMap<Node,Node>**. Build the structure, run **cloneBFS**, track visited/distance, and return the required value. Time is linear in vertices plus edges for traversal-style problems.”

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

See `../CloneGraph.java` for `package` and `main` smoke tests.

Compile from `PreparationGuide/` root:

```bash
javac DSA/Graphs/CloneGraph.java
java DSA.Graphs.CloneGraph
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

| Approach | Method in `CloneGraph.java` |
|---|---|
| BFS + clone map | `cloneBFS` |
| DFS + clone map (either is fine) | `cloneDFS` |

Run `main` to compare BFS vs DFS clones on the sample graph.

---

## 16. Quick revision

1. **Pattern:** BFS/DFS + HashMap<Node,Node>.
2. **Methods:** `cloneBFS`, `cloneDFS`.
3. **Map each original node to a new Node; wire neighbors through the map.**
4. Check **visited** and **edge direction**.
5. Read `Graphs_Common_Patterns.md` or Trie/UF guide.

One line: **Deep copy undirected graph nodes.**

---

## 17. Pattern used

**BFS/DFS + HashMap<Node,Node>** — cross-link `../../Graphs/notes` pattern cheatsheet if present.

---

## 18. Next problem

**Problem 03** — continue in `Graphs` track.

---

---

## Repository Java (full)

Align method names with `../CloneGraph.java`.

```java
package DSA.Graphs;

import java.util.*;

/*
 * Problem 02 — Clone Graph (LeetCode 133)
 */
public class CloneGraph {

    static class Node {
        int val;
        List<Node> neighbors;

        Node(int val) {
            this.val = val;
            neighbors = new ArrayList<>();
        }
    }

    public static Node cloneBFS(Node node) {
        if (node == null) {
            return null;
        }
        Map<Node, Node> map = new HashMap<>();
        map.put(node, new Node(node.val));
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.offer(node);
        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (Node nei : cur.neighbors) {
                if (!map.containsKey(nei)) {
                    map.put(nei, new Node(nei.val));
                    q.offer(nei);
                }
                map.get(cur).neighbors.add(map.get(nei));
            }
        }
        return map.get(node);
    }

    public static Node cloneDFS(Node node) {
        if (node == null) {
            return null;
        }
        return dfs(node, new HashMap<>());
    }

    private static Node dfs(Node node, Map<Node, Node> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }
        Node copy = new Node(node.val);
        map.put(node, copy);
        for (Node nei : node.neighbors) {
            copy.neighbors.add(dfs(nei, map));
        }
        return copy;
    }

    public static void main(String[] args) {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        one.neighbors = Arrays.asList(two, four);
        two.neighbors = Arrays.asList(one, three);
        three.neighbors = Arrays.asList(two, four);
        four.neighbors = Arrays.asList(one, three);

        Node cloned = cloneDFS(one);
        System.out.println("Clone root val: " + cloned.val);
        System.out.println("Clone neighbors: " + cloned.neighbors.size());
        System.out.println("Different object: " + (cloned != one));
        System.out.println("Neighbor is clone: " + (cloned.neighbors.get(0) != two));
    }
}
```
