# Problem 01 — Climbing Stairs

> **Pattern:** 1D DP (Fibonacci)  
> **Goal:** Count distinct ways to climb `n` stairs (1 or 2 steps).

---

## 1. Problem statement

### Example

```text
Input:  n = 3
Output: 3    (1+1+1, 1+2, 2+1)
```

---

## 2. Recurrence

`ways(i) = ways(i-1) + ways(i-2)`.

---

## 3. Brute

Pure recursion — `O(2^n)`.

---

## 4. Optimal

Two variables rolling Fibonacci — `O(n)` time, `O(1)` space.

---

## 5. Java

`../ClimbingStairs.java`.

---

## 6. Next

**Problem 02 — House Robber**