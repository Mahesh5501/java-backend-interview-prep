# Problem 03 — Minimum Size Subarray Sum

> **Pattern:** Variable-size Sliding Window (shortest valid window)  
> **Goal:** Smallest length of a contiguous subarray with sum ≥ `target` (LeetCode 209).

---

## 1. Problem statement

Given an array of **positive** integers `nums` and a positive integer `target`, return the **minimal length** of a contiguous subarray whose sum is ≥ `target`. If none exists, return `0`.

### Example 1

```text
Input:  target = 7, nums = [2, 3, 1, 2, 4, 3]
Output: 2
```

Explanation: `[4, 3]` has sum 7 and length 2 (shorter than `[2,3,1,2]`).

### Example 2

```text
Input:  target = 4, nums = [1, 4, 4]
Output: 1
```

### Example 3

```text
Input:  target = 20, nums = [1, 2, 3, 4, 5]
Output: 0
```

---

## 2. Mental picture — stretch right, trim left

```text
nums: [ 2 | 3 | 1 | 2 | 4 | 3 ]
      L-------R
      sum growing →

When sum >= 7:
  record length, shrink from L

      [ 2 | 3 | 1 | 2 | 4 | 3 ]
            L-----------R
            try to make window smaller while still >= 7
```

> **Right hand adds numbers. Left hand removes numbers when we can afford to.**

Window size is **not** fixed.

---

## 3. Thinking questions

1. **Why positives only?** When sum is too big, removing from left **always** decreases sum — safe to shrink. Negatives break this.
2. **Longest or shortest?** Shortest → once valid, shrink while still valid.
3. **Brute?** Try all start/end pairs — `O(n²)`.

---

## 4. Approach 1 — Brute force

```text
for each start:
  for each end from start:
    sum += nums[end]
    if sum >= target: record length, break inner
```

Time `O(n²)`, space `O(1)`.

---

## 5. Approach 2 — Optimal sliding window

```java
int left = 0, sum = 0, best = INF;
for (int right = 0; right < n; right++) {
    sum += nums[right];
    while (sum >= target) {
        best = min(best, right - left + 1);
        sum -= nums[left++];
    }
}
return best == INF ? 0 : best;
```

### Why `while` not `if`?

One `right` step might make sum huge. You may shrink **multiple** times.

```text
target=100, nums=[1,1,...,1,50,50] at end
→ may need several left++ before sum < target again
```

---

## 6. Hardest part — shortest vs longest window

| Goal | Shrink rule |
|---|---|
| **Shortest** valid | Shrink **while** still valid (`sum >= target`) |
| **Longest** valid | Shrink only **when** invalid |

This problem = **shortest**.

---

## 7. Detailed dry run — target 7

```text
nums = [2, 3, 1, 2, 4, 3]
```

| right | add | sum | while shrink? | best len |
|---:|---|---:|---|---:|
| 0 | 2 | 2 | no | ∞ |
| 1 | 3 | 5 | no | ∞ |
| 2 | 1 | 6 | no | ∞ |
| 3 | 2 | 8 | yes: len 4→left | 4 |
|  | -2 | 6 | stop | 4 |
| 4 | 4 | 10 | yes | 4 then len 3 | 3 |
|  | -3 | 7 | yes len 2 | **2** |
|  | -1 | 6 | stop | **2** |
| 5 | 3 | 9 | shrink chain | still **2** |

Answer: **2** (`[4,3]`).

ASCII at best moment:

```text
[ 2 | 3 | 1 | 2 | 4 | 3 ]
                  L R
                  [4,3] len=2
```

---

## 8. Why correct

- **Every** valid subarray is considered: any window with sum ≥ target will be seen when `right` is its right end and `left` has been advanced as far as possible while staying valid — that gives the **shortest** window ending at `right`.
- Taking min over all `right` yields global minimum length.

---

## 9. Complexity

| Method | Time | Space |
|---|---:|---:|
| Brute | `O(n²)` | `O(1)` |
| Sliding window | `O(n)` — each index moves left/right at most once | `O(1)` |

---

## 10. Edge cases

| Case | Output |
|---|---|
| Single element ≥ target | 1 |
| No subarray reaches target | 0 |
| Whole array needed | `nums.length` |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| `if` instead of `while` when shrinking | Miss shorter windows |
| Using on array with negatives | Wrong shrink logic |
| Returning `best` when still `MAX_VALUE` | Forgot to return 0 |

---

## 12. Interview — say aloud

> “I expand with right until sum is at least target. Then I shrink from the left while sum stays valid, updating the minimum length. Positives guarantee that removing from the left lowers the sum, so each pointer moves at most n times.”

---

## 13. Follow-ups

**Q: Return the subarray itself?**  
A: Track `bestLeft` when updating `best`.

**Q: Negative numbers?**  
A: Need different approach (prefix sum + binary search or deque).

**Q: Count subarrays with sum ≥ target?**  
A: Still tricky with only positives — related but not identical.

---

## 14. Complete Java

`../MinimumSizeSubarraySum.java`

```java
package DSA.SlidingWindow;

public class MinimumSizeSubarraySum {

    public static int minSubArrayLen(int target, int[] nums) {
        int left = 0, sum = 0, best = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= nums[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    public static void main(String[] args) {
        System.out.println(minSubArrayLen(7, new int[] { 2, 3, 1, 2, 4, 3 })); // 2
    }
}
```

```text
target=7 → 2
target=20 → 0
```

---

## 15. Practice checklist

- [ ] Brute then optimal.
- [ ] Trace with `while` shrink on a long all-1s example.
- [ ] State why positives matter.

---

## 16. Quick revision

> **Expand right → while sum ≥ target: update min len, left++.**

---

## 17. Next problem

**Problem 04 — Longest Substring Without Repeating Characters**  
Variable window on a **string** — validity = all chars unique.