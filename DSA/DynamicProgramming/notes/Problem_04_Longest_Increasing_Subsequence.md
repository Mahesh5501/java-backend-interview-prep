# Problem 04 — Longest Increasing Subsequence

> **Pattern:** 1D DP / Patience sorting (tails + binary search)  
> **Goal:** Length of **longest strictly increasing subsequence** (not necessarily contiguous).

---

## 1. Problem statement

Given integer array `nums`, return the length of the longest **strictly increasing subsequence** in `nums`.

Subsequence: keep relative order, skip elements.

### Example

```text
Input:  nums = [10, 9, 2, 5, 3, 7, 101, 18]
Output: 4
```

One LIS: `[2, 3, 7, 18]` (also `[2, 5, 7, 101]`).

### Constraints

- `1 <= nums.length <= 2500`
- `-10^4 <= nums[i] <= 10^4`

---

## 2. Mental picture

Pick numbers left-to-right without moving backward — “cards on piles” (patience sorting).

```text
nums:  10  9  2  5  3  7  101  18

pile tails (conceptual):  [2] → [2,5] → [2,3] → [2,3,7] → ...
                                    length of tails array = LIS length
```

For classic DP on each index:

```text
i:     0  1  2  3  4  5   6    7
dp[i]: ?  ?  1  2  2  3   4    4
       "best LIS ending at i"
```

---

## 3. How to start thinking

1. **Contiguous?** No — subsequence.
2. **State ending at `i`?** `dp[i]` = LIS length that **must end** at `nums[i]`.
3. **Transition?** Look all `j < i` with `nums[j] < nums[i]` → `dp[i] = max(dp[j]) + 1`.
4. **Answer?** `max(dp)` not `dp[n-1]`.
5. **Faster?** `tails[]` + binary search on smallest tail per length.

---

## 4. Approach 1 — Brute

Generate all `2^n` subsequences; filter increasing; track max length — `O(2^n)`.

Only for teaching.

---

## 5. Approach 2 — `O(n^2)` DP

### Java (matches repo)

```java
public static int lengthOfLISQuadratic(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, 1);
    int best = 1;
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        best = Math.max(best, dp[i]);
    }
    return best;
}
```

### Dry run snippet — `i=5`, value `7`

| `j` | `nums[j]` | extends? | candidate |
|---:|---:|---|---:|
| 2 | 2 | yes | dp[2]+1=2 |
| 3 | 5 | yes | 3 |
| 4 | 3 | yes | 3 |

`dp[5]=3`.

### Complexity

| | `n^2` DP |
|---|---|
| **Time** | `O(n^2)` |
| **Space** | `O(n)` |

---

## 6. Approach 3 — Optimal `O(n log n)` (tails)

### Idea

`t` = smallest possible tail value of an increasing subsequence of length `len+1`.  
For each `x`, binary search position to replace or extend `tails`.

### Java (matches repo)

```java
public static int lengthOfLIS(int[] nums) {
    int[] tails = new int[nums.length];
    int size = 0;
    for (int x : nums) {
        int lo = 0, hi = size;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (tails[mid] < x) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        tails[lo] = x;
        if (lo == size) {
            size++;
        }
    }
    return size;
}
```

### Why safe

Replacing a larger tail with smaller `x` keeps same length but better future extension. `size` at end equals LIS length.

### Complexity

| | Patience + BS |
|---|---|
| **Time** | `O(n log n)` |
| **Space** | `O(n)` |

---

## 7. Hardest part

**Before:** Confusing **subsequence length** with **ending at last element**.

**After:** Return global max for `n^2` DP; `size` for tails method.

**Binary search side:** Use **lower_bound** style (`tails[mid] < x` → search right half) for strict increase.

**Non-strict LIS:** change comparison to `<=`.

---

## 8. Edge cases

| Input | Output |
|---|---:|
| `[1]` | 1 |
| `[3,2,1]` | 1 |
| all equal | 1 (strict) |
| sorted ascending | `n` |

---

## 9. Common mistakes

| Mistake | Effect |
|---|---|
| Return `dp[n-1]` | Wrong |
| Greedy longest contiguous | Wrong problem |
| `tails` without BS | Wrong insertion logic |

---

## 10. Interview script

> “Quadratic: dp[i] is LIS ending at i; answer max dp. For n=2500 use tails array — binary search where x fits, extend or replace tail — O(n log n).”

---

## 11. Follow-ups

- Reconstruct actual subsequence (parent indices or backtrack on tails with care).
- Number of LIS (harder DP).
- Longest increasing **path** in matrix — different problem.

---

## 12. Quick revision

1. **`dp[i]`** ending at `i`, answer **`max dp`**.
2. **`O(n^2)`** all `j < i`.
3. **Tails + BS** → `O(n log n)`.
4. Not contiguous.

---

## 13. Runnable Java

`../LongestIncreasingSubsequence.java` — `lengthOfLISQuadratic`, `lengthOfLIS`.

---

## 14. Next problem

**Problem 05 — Unique Paths** — 2D grid DP.

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

Align method names with `../LongestIncreasingSubsequence.java`.

```java
package DSA.DynamicProgramming;

import java.util.Arrays;

/*
 * Problem 04 — Longest Increasing Subsequence (LeetCode 300)
 */
public class LongestIncreasingSubsequence {

    public static int lengthOfLISQuadratic(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int best = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    public static int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int lo = 0, hi = size;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails[mid] < x) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            tails[lo] = x;
            if (lo == size) {
                size++;
            }
        }
        return size;
    }

    public static void main(String[] args) {
        int[] nums = { 10, 9, 2, 5, 3, 7, 101, 18 };
        System.out.println("n log n: " + lengthOfLIS(nums));
        System.out.println("n^2: " + lengthOfLISQuadratic(nums));
    }
}
```
