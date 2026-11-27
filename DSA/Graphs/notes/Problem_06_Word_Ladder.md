# Problem 06 — Word Ladder

> **Pattern:** BFS shortest path (unweighted)  
> **Goal:** Shortest transformation sequence from `beginWord` to `endWord`.

---

## 1. Problem statement

Change one letter at a time. Each intermediate word must be in `wordList`. Return **number of words** in sequence (including `beginWord`), or `0`.

### Example

```text
Input:  begin="hit", end="cog", wordList=[hot,dot,dog,lot,log,cog]
Output: 5   (hit → hot → dot → dog → cog)
```

---

## 2. Mental picture

Vertices = words; edges = differ by one letter. **BFS** gives shortest path layers.

---

## 3. Brute

DFS with depth — finds a path but not necessarily shortest.

---

## 4. Optimal — BFS

Try all 26 replacements at each index; enqueue unseen dict words. Increment level each round.

**Time:** `O(N · L²)` for N words, length L.

---

## 5. Java

`../WordLadder.java` — `ladderLength`.

---

## 6. Next

**Problem 07 — Rotting Oranges**