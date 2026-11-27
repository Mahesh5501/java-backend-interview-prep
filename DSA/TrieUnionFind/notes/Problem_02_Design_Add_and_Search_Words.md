# Problem 02 — Design Add and Search Words

> **Pattern:** Trie + DFS on `'.'`  
> **Goal:** Dictionary supporting wildcard search.

---

## 1. Example

```text
add "bad","dad","mad"
search(".ad") → true
```

---

## 2. Key idea

On `'.'`, branch to all non-null children.

---

## 3. Java

`../WordDictionary.java`.

---

## 4. Next

**Problem 03 — Word Search II**