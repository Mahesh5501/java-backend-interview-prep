# Trie & Union-Find — Common Patterns Reference

Java interview prep for **prefix trees** and **disjoint set union (DSU)**.

```text
DSA/TrieUnionFind/
├── notes/
└── *.java          ← package DSA.TrieUnionFind
```

---

## 1. Trie (prefix tree)

Stores strings character-by-character. Each edge is one letter.

```java
static class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
}
```

**Operations:**

- **insert(word)** — walk/create nodes; mark `isEnd` at last char.
- **search(word)** — walk; fail on null child; require `isEnd` at end.
- **startsWith(prefix)** — walk; only require path exists.

**Time per op:** `O(L)` where `L` = word length.  
**Space:** total characters stored (up to sum of word lengths).

**When:** autocomplete, prefix search, **Word Search II** (prune board DFS).

---

## 2. Trie + wildcards

**Design Add and Search Words:** on `'.'`, DFS all 26 children at that level.

Worst case exponential in number of dots — still OK for interview bounds.

---

## 3. Trie + board backtracking

**Word Search II:**

1. Build trie from dictionary.
2. DFS from each cell; at each step follow trie child for letter.
3. When node `isEnd`, add word; optionally remove from trie to prune.

---

## 4. Union-Find (Disjoint Set Union)

Maintains disjoint sets with:

- `find(x)` — representative root (with **path compression**).
- `union(x, y)` — merge sets (**union by rank** or size).

```java
class UnionFind {
    int[] parent, rank;
    int components;

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return false;
        // attach by rank ...
        components--;
        return true;
    }
}
```

**When:**

- **Number of Provinces** — components in implicit graph.
- **Redundant Connection** — first edge that connects already-connected nodes.
- **Accounts Merge** — union users sharing any email.

**Time:** nearly `O(1)` amortized per op → `O(α(n))`.

---

## 5. Problem map

| # | Problem | Pattern |
|---:|---|---|
| 01 | Implement Trie | Basic trie |
| 02 | Design Add and Search Words | Trie + `'.'` DFS |
| 03 | Word Search II | Trie + grid DFS |
| 04 | Number of Provinces | Union-Find |
| 05 | Redundant Connection (UF) | Union-Find cycle edge |
| 06 | Accounts Merge | Union-Find + group/sort |

Study order: **01 → 06**.

**Related:** `Graphs/RedundantConnection.java` (same UF idea, different package).

---

## 6. Common mistakes

**Trie:**

1. Forgetting to set `isEnd` only on complete words.
2. Index `c - 'a'` without bounds (use lowercase assumption).
3. Not pruning trie in Word Search II (TLE risk).

**Union-Find:**

1. Off-by-one on 1-based vs 0-based node ids.
2. Forgetting to count **components** on init vs after unions.
3. Using UF on **directed** graph for topo sort (wrong tool).

---

## 7. How to use this folder

1. Read this file.
2. Implement **TrieNode** and **UnionFind** from memory.
3. Do problems 01 → 06; run each `.java`.
4. Compare UF redundant edge with `Graphs/RedundantConnection.java`.