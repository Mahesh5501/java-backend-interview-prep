# Problem 07 — Rotting Oranges

> **Pattern:** Multi-source BFS  
> **Goal:** Minutes until no fresh orange remains, or `-1`.

---

## 1. Problem statement

`0` empty, `1` fresh, `2` rotten. Each minute, rotten oranges infect 4-directional fresh neighbors.

### Example

```text
Input:  [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
```

---

## 2. Mental picture

Enqueue **all** rotten cells first. BFS **layers** = minutes.

---

## 4. Optimal

Track `fresh` count. When queue layer finishes spreading, increment `minutes`. End: `fresh==0 ? minutes : -1`.

**Time:** `O(m·n)`.

---

## 5. Java

`../RottingOranges.java`.

---

## 6. Next

**Problem 08 — Network Delay Time**