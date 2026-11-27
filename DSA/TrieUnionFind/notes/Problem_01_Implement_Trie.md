# Problem 01 — Implement Trie

> **Pattern:** Prefix tree  
> **Goal:** Design `insert`, `search`, `startsWith`.

---

## 1. Example

```text
insert("apple") → search("apple") true, search("app") false
startsWith("app") true
```

---

## 2. Structure

`TrieNode[26] children` + `boolean isEnd`.

---

## 3. Java

`../ImplementTrie.java`.

---

## 4. Next

**Problem 02 — Design Add and Search Words**