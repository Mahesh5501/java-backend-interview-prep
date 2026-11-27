# Problem 06 — Accounts Merge

> **Pattern:** Union-Find + group + sort  
> **Goal:** Merge accounts sharing any email.

---

## 1. Example

```text
John johnsmith@ + john00@ merged with John johnsmith@ + john_newyork@
```

---

## 2. Steps

1. Map email → first account index; union duplicates.
2. Group emails by `find(i)` in `TreeSet`.
3. Output `[name, sorted emails...]`.

---

## 3. Java

`../AccountsMerge.java`.

---

## 4. Done

Review **Graphs_Common_Patterns.md** and full roadmap.