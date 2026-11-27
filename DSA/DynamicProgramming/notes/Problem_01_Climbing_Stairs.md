# Problem 01 — Climbing Stairs

> **Pattern:** 1D DP — Fibonacci-style recurrence  
> **Goal:** Count **distinct** ways to reach step `n` if each move is **1 or 2** steps.

---

## 1. Problem statement

You are climbing a staircase. It has `n` steps.

Each time you can climb **1** or **2** steps. In how many **distinct** ways can you reach the top?

Order matters: `1+2` and `2+1` are different ways.

### Example 1

```text
Input:  n = 3
Output: 3
```

Ways: `1+1+1`, `1+2`, `2+1`.

### Example 2

```text
Input:  n = 2
Output: 2
```

Ways: `1+1`, `2`.

### Constraints (typical)

- `1 <= n <= 45`

---

## 2. Mental picture — one picture to keep in mind

Think of the top as step `n`. You are always asking: “How many paths end **here**?”

```text
Step:   0   1   2   3   4  ...  n
Ways:   1   1   2   3   5       ?
              \   |   /
               \  |  /
        reach i only from (i-1) or (i-2)
```

```text
                    (top = n)
                   /
              (n-1) ---- one step
             /
        (n-2) -------- two steps
```

> **Last move is either 1 step or 2 steps. Sum the ways that land on the step before each.**

That is the whole recurrence.

---

## 3. How to start thinking (simple questions)

1. **What am I counting?** Paths (sequences of 1s and 2s), not the minimum steps.
2. **Base cases?** One step → 1 way (`[1]`). Two steps → 2 ways (`[1,1]`, `[2]`).
3. **How do I reach step `i`?** Last move from `i-1` (+1) or `i-2` (+2).
4. **Overlapping subproblems?** Reaching step `i` uses counts for smaller steps — same sub-questions repeat in recursion.
5. **Can I reuse only two numbers?** Yes — only `i-1` and `i-2` matter → rolling variables.

If you can say `ways(i) = ways(i-1) + ways(i-2)`, you have the DP state.

---

## 4. Approach 1 — Brute force recursion

### Idea

From step `0`, try “jump 1” or “jump 2” until you reach or pass `n`. Count leaves where position equals `n`.

### Why it works

Every distinct sequence of 1s and 2s is explored. Each successful path increments the count.

### Java

```java
public static int climbStairsBrute(int n) {
    if (n <= 2) {
        return n;
    }
    return climbStairsBrute(n - 1) + climbStairsBrute(n - 2);
}
```

### Recursion tree (n = 5) — sketch

```text
                    f(5)
                   /    \
               f(4)      f(3)
              /    \      /    \
           f(3)  f(2)  f(2)  f(1)
```

Same `f(3)`, `f(2)` computed many times → exponential blow-up.

### Complexity

| | Brute recursion |
|---|---|
| **Time** | `O(2^n)` (rough upper bound; Fibonacci recursion) |
| **Space** | `O(n)` recursion stack |

### Limitation

Fine for `n ≤ 20`; fails for `n = 45`.

---

## 5. Approach 2 — Top-down DP (memoization)

### Steps

1. Same recursion as brute.
2. `memo[i]` = ways to reach step `i`.
3. If `memo[i]` known, return it; else compute and store.

### Why safe

Each `i` computed once. Still the Fibonacci recurrence.

### Complexity

| | Memoized |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(n)` array + `O(n)` stack |

---

## 6. Approach 3 — Optimal: bottom-up rolling `O(1)` space

### Step-by-step

1. If `n <= 2`, return `n`.
2. `a = 1` (ways at step 1), `b = 2` (ways at step 2).
3. For `i` from `3` to `n`: `c = a + b`, then shift `a = b`, `b = c`.
4. Return `b`.

### Visual — rolling window

```text
i=3:  a=1, b=2  →  c=3  →  a=2, b=3
i=4:  a=2, b=3  →  c=5  →  a=3, b=5
i=5:  a=3, b=5  →  c=8  →  a=5, b=8
```

### Why safe

At each `i`, `b` always equals `ways(i)` and `a` equals `ways(i-1)` by induction.

### Java (optimal — matches repo)

```java
public static int climbStairsDP(int n) {
    if (n <= 2) {
        return n;
    }
    int a = 1, b = 2;
    for (int i = 3; i <= n; i++) {
        int c = a + b;
        a = b;
        b = c;
    }
    return b;
}
```

### Complexity

| | Rolling bottom-up |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(1)` |

---

## 7. Hardest part — before vs after

### Before (confusion)

“Do I multiply options at each step? Is it `2^n`?”

No — you **add** ways from the two predecessors. You are counting **paths**, not choosing independent branches at every level without merging.

### After (clear)

```text
ways(i) = ways(i-1) + ways(i-2)
```

Same as Fibonacci shifted: `F(n)` with `F(1)=1`, `F(2)=2`.

### Another trap

Using `int` for large `n` — for LeetCode 45, result fits in 32-bit; for huge `n` use `long`.

---

## 8. Detailed dry run table

**Input:** `n = 5`

| step `i` | `a` (= ways i-1) | `b` (= ways i) | action |
|---:|---:|---:|---|
| start | 1 | 2 | base for steps 1,2 |
| 3 | 2 | 3 | `c=3` |
| 4 | 3 | 5 | `c=5` |
| 5 | 5 | 8 | `c=8` |

**Answer:** `8`.

Check manually for `n=4`: ways = `5` → `1+1+1+1`, `1+1+2`, `1+2+1`, `2+1+1`, `2+2`.

---

## 9. Why the algorithm is correct

**Induction on `i`:** Assume `a` and `b` are correct for steps `i-1` and `i`. The only legal last moves onto `i+1` are from `i` (one step) or `i-1` (two steps), and those sets of full paths are disjoint. So `ways(i+1) = ways(i) + ways(i-1)`, which is exactly what the loop computes.

**Base:** `n=1` → 1, `n=2` → 2 match enumeration.

---

## 10. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Naive recursion | `O(2^n)` | `O(n)` stack |
| Memoization | `O(n)` | `O(n)` |
| Full array `dp[0..n]` | `O(n)` | `O(n)` |
| Rolling two vars | `O(n)` | `O(1)` |

---

## 11. Edge cases

| Input | Output | Note |
|---|---:|---|
| `n = 1` | 1 | Single step |
| `n = 2` | 2 | Return early in code |
| `n = 45` | 1836311903 | Max constraint |
| `n = 0` | (not in LC) | Define 1 if ever asked — one way to “stay” |

---

## 12. Common mistakes

| Mistake | What goes wrong |
|---|---|
| Using `2^n` combinatorics | Overcounts; steps are not independent choices without overlap |
| Off-by-one in loop (`i <= n` vs `< n`) | Wrong step counted |
| Forgetting base `n <= 2` | Wrong for small inputs |
| Building full Fibonacci from `F(0)=0` | Off-by-one vs this problem’s indexing |

---

## 13. Interview — say this aloud

> “To stand on step `i`, my last move was either one step from `i-1` or two from `i-2`, so `ways(i) = ways(i-1) + ways(i-2)`. That’s Fibonacci. I handle `n ≤ 2` directly, then roll two variables for `O(n)` time and `O(1)` space.”

---

## 14. Follow-up Q&A

**Q: What if you can take steps in `{1,2,3}`?**  
A: `ways(i) = ways(i-1) + ways(i-2) + ways(i-3)` — triple roll or length-3 DP.

**Q: Minimum steps instead of counting ways?**  
A: Greedy / `ceil(n/2)` — different problem.

**Q: Can you do `O(log n)` with matrix exponentiation?**  
A: Yes for Fibonacci; overkill for `n ≤ 45`.

**Q: Relationship to Fibonacci?**  
A: `ways(n)` equals `F_{n+1}` in the usual `F_1=1, F_2=1` sequence if you align indices carefully.

---

## 15. Complete runnable Java

Full source: `../ClimbingStairs.java`.

```java
package DSA.DynamicProgramming;

public class ClimbingStairs {

    public static int climbStairsBrute(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairsBrute(n - 1) + climbStairsBrute(n - 2);
    }

    public static int climbStairsDP(int n) {
        if (n <= 2) {
            return n;
        }
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println("n=5 DP: " + climbStairsDP(n));
        System.out.println("n=5 brute: " + climbStairsBrute(n));
    }
}
```

```text
n=5 DP: 8
n=5 brute: 8
```

---

## 16. Practice checklist

- [ ] State recurrence without looking.
- [ ] Trace `n=5` with `a`, `b` on paper.
- [ ] Explain why time is not `2^n`.
- [ ] Code rolling DP from memory.
- [ ] Answer “steps in {1,2,3}`” follow-up.

---

## 17. Quick revision

1. **Count paths** with steps 1 or 2.
2. **`ways(i) = ways(i-1) + ways(i-2)`**.
3. **Bases:** `n=1 → 1`, `n=2 → 2`.
4. **Roll `a`, `b`** for `O(1)` space.
5. Same family as **Fibonacci** — classic 1D DP intro.

One line:

> **Last step is from one or two below — add those counts.**

---

## 18. Pattern used

**1D linear DP** from `DynamicProgramming_Common_Patterns.md`: state = index on a line; transition from previous one or two positions.

---

## 19. Next problem

**Problem 02 — House Robber** — 1D DP with a **skip constraint** (cannot take adjacent houses).

---

---

## Repository Java (full)

Align method names with `../ClimbingStairs.java`.

```java
package DSA.DynamicProgramming;

/*
 * Problem 01 — Climbing Stairs (LeetCode 70)
 */
public class ClimbingStairs {

    public static int climbStairsBrute(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairsBrute(n - 1) + climbStairsBrute(n - 2);
    }

    public static int climbStairsDP(int n) {
        if (n <= 2) {
            return n;
        }
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println("n=5 DP: " + climbStairsDP(n));
        System.out.println("n=5 brute: " + climbStairsBrute(n));
    }
}
```
