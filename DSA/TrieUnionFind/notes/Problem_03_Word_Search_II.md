# Problem 03 — Word Search II

> **Pattern:** Trie + board backtracking  
> **Goal:** Find all dictionary words on the grid.

---

## 1. Example

```text
board 4x4 + words ["oath","pea","eat","rain"]
Output: ["eat","oath"]
```

---

## 2. Optimal sketch

Build trie; DFS each cell; store `word` at terminal nodes; prune when trie path ends.

---

## 3. Java

`../WordSearchII.java`.

---

## 4. Next

**Problem 04 — Number of Provinces**