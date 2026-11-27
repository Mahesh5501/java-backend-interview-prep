# Problem 02 — House Robber

> **Pattern:** 1D DP — take / skip adjacent constraint  
> **Goal:** Max money robbing **non-adjacent** houses along a street.

---

## 1. Problem statement

Given integer array `nums` where `nums[i]` is money in house `i`, return the **maximum** you can rob **without robbing two adjacent houses**.

### Example 1

```text
Input:  nums = [2, 7, 9, 3, 1]
Output: 12    (2 + 9 + 1)
```

### Example 2

```text
Input:  nums = [1, 2, 3, 1]
Output: 4     (1 + 3)
```

### Constraints (typical)

- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 400`

---

## 2. Mental picture

Houses in a line. Robbing house `i` **blocks** houses `i-1` and `i+1`.

```text
Street:  [ 2 | 7 | 9 | 3 | 1 ]
          ^       ^       ^
          pick    skip    pick  (one optimal plan)
```

At each house, two choices:

```text
         (skip i)              (rob i)
            |                      |
     best up to i-1         nums[i] + best up to i-2
```

> **Best loot up to house `i` = max(skip rob house i, rob house i).**

---

## 3. How to start thinking

1. **Greedy by max single house?** Fails — `[2,1,1,2]` → greedy might rob wrong neighbors.
2. **State at index `i`?** `dp[i]` = max money using houses `0..i`.
3. **If I rob `i`?** Cannot have used `i-1` → add `nums[i]` to best through `i-2`.
4. **If I skip `i`?** Best through `i-1` unchanged.
5. **Only need two previous values?** Yes → `prev2`, `prev1`.

---

## 4. Approach 1 — Brute recursion

### Idea

At index `i`, branch: rob (jump to `i+2`) or skip (jump to `i+1`).

### Java (matches repo)

```java
public static int robBrute(int[] nums) {
    return robFrom(0, nums);
}

private static int robFrom(int i, int[] nums) {
    if (i >= nums.length) {
        return 0;
    }
    return Math.max(nums[i] + robFrom(i + 2, nums), robFrom(i + 1, nums));
}
```

### Dry run — `nums = [2, 7, 9]`

| call | rob `i` branch | skip branch |
|---|---|---|
| `i=0` | `2 + rob(2)` | `rob(1)` |
| `i=1` | `7 + rob(3)` | ... |

Many repeated subproblems → `O(2^n)` time without memo.

### Complexity

| | Brute |
|---|---|
| **Time** | `O(2^n)` |
| **Space** | `O(n)` stack |

---

## 5. Approach 2 — Optimal rolling DP

### Recurrence

```text
dp[i] = max(dp[i-1], nums[i] + dp[i-2])
```

### Step-by-step

1. `prev2 = 0` (nothing before house 0), `prev1 = 0` (best through “before start”).
2. For each `x` in `nums`:
   - `cur = max(prev1, prev2 + x)`
   - shift `prev2 = prev1`, `prev1 = cur`
3. Return `prev1`.

### Why safe

`prev1` always stores optimal loot including current house; skipping never decreases optimality vs taking an illegal neighbor.

### Java (optimal)

```java
public static int rob(int[] nums) {
    int prev2 = 0, prev1 = 0;
    for (int x : nums) {
        int cur = Math.max(prev1, prev2 + x);
        prev2 = prev1;
        prev1 = cur;
    }
    return prev1;
}
```

### Complexity

| | Optimal |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(1)` |

---

## 6. Hardest part

### Before

“Should I compare `nums[i] + dp[i-2]` with `dp[i-1] + nums[i+1]`?” — overcomplicating lookahead.

### After

Only **prefix** matters: at each house, decision uses **two** prior totals (`i-1` and `i-2`), not future houses explicitly.

### Circular street (follow-up)

Split into two linear problems: rob `0..n-2` or `1..n-1` — not this note’s base problem.

---

## 7. Detailed dry run table

**Input:** `[2, 7, 9, 3, 1]`

| house `x` | `prev2` | `prev1` before | `cur = max(prev1, prev2+x)` | `prev1` after |
|---:|---:|---:|---:|---:|
| 2 | 0 | 0 | 2 | 2 |
| 7 | 0 | 2 | max(2,7)=7 | 7 |
| 9 | 2 | 7 | max(7,2+9)=11 | 11 |
| 3 | 7 | 11 | max(11,7+3)=11 | 11 |
| 1 | 11 | 11 | max(11,11+1)=12 | 12 |

**Answer:** 12.

---

## 8. Why correct

For any optimal solution on `0..i`, either house `i` is unused (→ value `dp[i-1]`) or used (→ `nums[i] + dp[i-2]`). No other case exists. Taking max is optimal by optimal substructure.

---

## 9. Edge cases

| Input | Output | Note |
|---|---:|---|
| `[5]` | 5 | One house |
| `[5, 1]` | 5 | Adjacent — pick max |
| `[1,2,3,1]` | 4 | Classic |
| all zeros | 0 | Still valid |

---

## 10. Common mistakes

| Mistake | Effect |
|---|---|
| `dp[i] = nums[i] + dp[i-1]` | Robs adjacent houses |
| Starting `prev1 = nums[0]` only | Wrong for some implementations; rolling from 0 works |
| Modifying array instead of DP | Unnecessary |

---

## 11. Interview — say aloud

> “`dp[i]` is the best I can do on houses zero through i. I either skip i or take nums[i] plus the best through i-2. That’s max of previous line and prev2 plus current. Two rolling variables, O(n) time O(1) space.”

---

## 12. Follow-ups

- **House Robber II** (circle): max of two linear runs.
- **House Robber III** (tree): post-order tree DP.
- **Return which houses?** Backtrack from full `dp[]` array.

---

## 13. Quick revision

1. **Cannot take neighbors.**
2. **`cur = max(skip, take)`** = `max(prev1, prev2+x)`.
3. **Roll two vars.**
4. Brute **`robFrom(i+2)` vs `robFrom(i+1)`**.

---

## 14. Runnable Java

See `../HouseRobber.java` — `robBrute`, `rob`.

---

## 15. Next problem

**Problem 03 — Coin Change** — unbounded min coins (different DP shape).

---

## Depth addendum — extended workbook

### Mental picture (second angle)

Sketch input on paper and mark **what changes each loop iteration**. If the pattern uses a stack, queue, heap, or map, write its contents as a column beside the array/tree.

### Brute force reminder

State the naive approach in one sentence, its time/space, and **one sentence why** the optimal pattern beats it.

### Hardest-part drill

| Before coding | After understanding |
|---|---|
| Unsure what moves | Name the invariant pointers/stack maintain |
| Edge case worry | Check empty, single, duplicates |

### Extended dry-run columns

| iter | key variables | container snapshot | output so far |
|---:|---|---|---|

Fill five rows from the matching Java main example in the sibling .java file.

### Interview say-aloud (30s)

> I recognize the pattern, use the right structure for the invariant, avoid brute time because each element is handled once.

### Follow-up Q&A

**Q: In-place?** Only if allowed.  
**Q: Unsorted input?** State preprocessing.

---

---

## Repository Java (full)

Align method names with `../HouseRobber.java`.

```java
package DSA.DynamicProgramming;

/*
 * Problem 02 — House Robber (LeetCode 198)
 */
public class HouseRobber {

    public static int robBrute(int[] nums) {
        return robFrom(0, nums);
    }

    private static int robFrom(int i, int[] nums) {
        if (i >= nums.length) {
            return 0;
        }
        return Math.max(nums[i] + robFrom(i + 2, nums), robFrom(i + 1, nums));
    }

    public static int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int x : nums) {
            int cur = Math.max(prev1, prev2 + x);
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }

    public static void main(String[] args) {
        int[] nums = { 2, 7, 9, 3, 1 };
        System.out.println("Optimal: " + rob(nums));
        System.out.println("Brute: " + robBrute(nums));
    }
}
```
