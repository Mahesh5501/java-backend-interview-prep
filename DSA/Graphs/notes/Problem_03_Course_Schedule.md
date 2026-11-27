# Problem 03 — Course Schedule

> **Pattern:** Cycle detection (Kahn BFS / DFS colors)  
> **Goal:** Return whether you can finish all courses given prerequisites.

---

## 1. Problem statement

`prerequisites[i] = [a, b]` means you must take `b` before `a`. Detect if the prerequisite graph has a **cycle**.

### Example

```text
Input:  numCourses = 2, prerequisites = [[1,0]]
Output: true
```

---

## 2. Mental picture

Directed edges `b → a`. Cycle ⇒ deadlock ⇒ **false**.

---

## 3. Kahn (indegree BFS)

Push all indegree-0 nodes; pop, relax edges; if processed < n → cycle.

---

## 4. DFS three colors

`0` unvisited, `1` on stack, `2` finished. Edge to state `1` ⇒ cycle.

---

## 5. Java

`../CourseSchedule.java` — `canFinishKahn`, `canFinishDFS`.

---

## 6. Next

**Problem 04 — Course Schedule II**