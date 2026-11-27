# Problem 06 — Word Ladder

> **Pattern:** BFS on implicit graph  
> **Goal:** Shortest transformation sequence length.

---

## 1. Problem statement

See LeetCode / class **`WordLadder.java`** in the repo for full constraints and examples.

**Core idea (one line):** Change one letter; BFS levels; endWord must be in dict.

---

## 2. Mental picture — ASCII

```text
hit -> hot -> dot -> dog\none letter change per edge; BFS levels
```

Read the pattern as:

> **BFS on implicit graph** — know what you store per node/edge and what you return when traversal finishes.

---

## 3. How to start thinking (simple questions)

1. **Graph type?** Directed vs undirected; weighted vs unweighted; implicit vs explicit.
2. **What is the node?** Index, coordinate, string, object reference.
3. **Can brute BFS/DFS work?** Usually yes for reachability; shortest unweighted → BFS; non‑negative weights → Dijkstra.
4. **Visited strategy?** `boolean[]`, hash set, mutate grid, or map for clones.
5. **Match repo methods?** Implement `ladderLength` so tests align with Java file.

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

File: `../WordLadder.java` — use `ladderLength`.

---

## 6. Hardest part — before / after

### Before (confusion)

Mixing **visited timing** (enqueue vs dequeue), **directed edge direction**, or **skipping stale Dijkstra heap nodes**.

### After (clear)

**Change one letter; BFS levels; endWord must be in dict.**

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
| Optimal (BFS on implicit graph) | See pattern guide | `O(V+E)` or `O(n)` typical |

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

> “I'll model this as **BFS on implicit graph**. Build the structure, run **ladderLength**, track visited/distance, and return the required value. Time is linear in vertices plus edges for traversal-style problems.”

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

See `../WordLadder.java` for `package` and `main` smoke tests.

Compile from `PreparationGuide/` root:

```bash
javac DSA/Graphs/WordLadder.java
java DSA.Graphs.WordLadder
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

1. **Pattern:** BFS on implicit graph.
2. **Methods:** `ladderLength`.
3. **Change one letter; BFS levels; endWord must be in dict.**
4. Check **visited** and **edge direction**.
5. Read `Graphs_Common_Patterns.md` or Trie/UF guide.

One line: **Shortest transformation sequence length.**

---

## 17. Pattern used

**BFS on implicit graph** — cross-link `../../Graphs/notes` pattern cheatsheet if present.

---

## 18. Next problem

**Problem 07** — continue in `Graphs` track.

---

---

## Repository Java (full)

Align method names with `../WordLadder.java`.

```java
package DSA.Graphs;

import java.util.*;

/*
 * Problem 06 — Word Ladder (LeetCode 127)
 */
public class WordLadder {

    // BFS — shortest path in unweighted implicit graph
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return 0;
        }
        ArrayDeque<String> q = new ArrayDeque<>();
        q.offer(beginWord);
        Set<String> seen = new HashSet<>();
        seen.add(beginWord);
        int level = 1;
        int len = beginWord.length();

        while (!q.isEmpty()) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                String word = q.poll();
                char[] arr = word.toCharArray();
                for (int i = 0; i < len; i++) {
                    char old = arr[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == old) {
                            continue;
                        }
                        arr[i] = c;
                        String next = new String(arr);
                        if (next.equals(endWord)) {
                            return level + 1;
                        }
                        if (dict.contains(next) && seen.add(next)) {
                            q.offer(next);
                        }
                    }
                    arr[i] = old;
                }
            }
            level++;
        }
        return 0;
    }

    public static void main(String[] args) {
        String begin = "hit";
        String end = "cog";
        List<String> list = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        System.out.println("Ladder length: " + ladderLength(begin, end, list));
    }
}
```
