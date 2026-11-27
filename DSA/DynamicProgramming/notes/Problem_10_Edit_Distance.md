# Problem 10 — Edit Distance

> **Pattern:** 2D string DP  
> **Goal:** Minimum insert/delete/replace ops to transform `word1` → `word2`.

---

## 1. Example

```text
Input:  word1="horse", word2="ros"
Output: 3
```

---

## 2. Recurrence

Match → diagonal; else `1 + min(replace, delete, insert)` from neighbors.

---

## 3. Java

`../EditDistance.java`.

---

## 4. Next

**TrieUnionFind/** — **Implement Trie**