# Problem 05 — Pacific Atlantic Water Flow

> **Pattern:** Multi-source DFS/BFS from borders  
> **Goal:** Cells that can reach both Pacific and Atlantic oceans.

---

## 1. Problem statement

Heights grid. Water flows to **equal or lower** neighbors. Pacific touches top/left; Atlantic touches bottom/right.

### Example

```text
Input:  heights 5x5 grid (LeetCode sample)
Output: list of [row,col] that reach both oceans
```

---

## 2. Mental picture

Reverse the flow: from ocean inward, climb to **equal or higher** cells. Intersection of two reachability sets.

---

## 3. Optimal

1. DFS from Pacific borders → `pacific[][]`.
2. DFS from Atlantic borders → `atlantic[][]`.
3. Collect `pacific && atlantic`.

**Time:** `O(m·n)`.

---

## 4. Java

`../PacificAtlanticWaterFlow.java`.

---

## 5. Next

**Problem 06 — Word Ladder**