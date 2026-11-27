# Problem 04 — Course Schedule II

> **Pattern:** Topological sort  
> **Goal:** Return any valid order to take all courses, or `[]` if impossible.

---

## 1. Problem statement

Same input as Course Schedule, but output an **ordering** of all courses.

### Example

```text
Input:  4 courses, prereqs [[1,0],[2,0],[3,1],[3,2]]
Output: e.g. [0,1,2,3] or [0,2,1,3]
```

---

## 2. Optimal — Kahn

Append course to `order` when indegree becomes 0. If `order.length < n`, return `[]`.

**Time:** `O(V+E)`.

---

## 3. Java

`../CourseScheduleII.java` — `findOrder`.

---

## 4. Next

**Problem 05 — Pacific Atlantic Water Flow**