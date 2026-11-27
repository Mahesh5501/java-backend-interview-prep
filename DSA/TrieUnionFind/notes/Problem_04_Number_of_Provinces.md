# Problem 04 — Number of Provinces

> **Pattern:** Union-Find or DFS components  
> **Goal:** Count connected components in friend matrix.

---

## 1. Example

```text
isConnected = [[1,1,0],[1,1,0],[0,0,1]]
Output: 2
```

---

## 2. Union-Find

Union all pairs with `isConnected[i][j]==1`. Answer = `components` count.

---

## 3. Java

`../NumberOfProvinces.java` — UF + DFS.

---

## 4. Next

**Problem 05 — Redundant Connection (UF)**