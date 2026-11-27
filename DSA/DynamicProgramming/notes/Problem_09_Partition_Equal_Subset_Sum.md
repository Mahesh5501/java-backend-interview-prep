# Problem 09 — Partition Equal Subset Sum

> **Pattern:** 0/1 knapsack as boolean — subset sums  
> **Goal:** Split array into two subsets with **equal sum**.

---

## 1. Problem statement

Integer array `nums`. Return `true` if can partition into two subsets with same total sum.

### Example

```text
nums = [1, 5, 11, 5]
Output: true    [1,5,5] and [11]
```

### Example 2

```text
nums = [1, 2, 3, 5]
Output: false
```

---

## 2. Mental picture

Total sum `S`. Need subset with sum **`S/2`** (if `S` odd → impossible).

```text
nums: 1  5  11  5     sum=22 → target=11

dp[t] = can we make sum t using some items?
```

Same as knapsack, but **boolean** and **values = numbers**.

---

## 3. How to start thinking

1. **Sum odd?** Immediate false.
2. **Target?** `sum / 2`.
3. **State?** `dp[t]` reachable sums.
4. **0/1?** Loop `t` downward.
5. **Answer?** `dp[target]`.

---

## 4. Brute

Try all `2^n` subsets — compare half sums.

---

## 5. Optimal

### Java (matches repo)

```java
public static boolean canPartition(int[] nums) {
    int sum = 0;
    for (int x : nums) sum += x;
    if (sum % 2 != 0) return false;
    int target = sum / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int x : nums) {
        for (int t = target; t >= x; t--) {
            dp[t] = dp[t] || dp[t - x];
        }
    }
    return dp[target];
}
```

### Dry run — `[1,5,11,5]`, target 11

After processing 1,5,11,5 → `dp[11]` becomes true.

### Complexity

| | 1D boolean |
|---|---|
| **Time** | `O(n * target)` |
| **Space** | `O(target)` |

---

## 6. Hardest part

Using **OR** not **max** — boolean reachability.

Descending `t` — same 0/1 rule as knapsack.

**Overflow:** sum of ints within constraints on LC.

---

## 7. Edge cases

| Case | Result |
|---|---|
| single element | false (unless 0?) |
| all zeros | true (split empty) |
| sum odd | false |

---

## 8. Common mistakes

| Mistake | Effect |
|---|---|
| Forward `t` loop | Reuse same number |
| Forget odd check | Wasted DP |

---

## 9. Interview script

> “Equal partition → subset sum target half total. dp[t] if sum t reachable. Process each number, t down to x, dp[t] |= dp[t-x].”

---

## 10. Follow-ups

- Return actual partition — backtrack.
- **Partition to K equal subsets** — harder.

---

## 11. Quick revision

- **Odd sum → false.**
- **Target sum/2.**
- **Boolean knapsack downward.**

---

## 12. Java

`../PartitionEqualSubsetSum.java`.

---

## 13. Next

**Problem 10 — Edit Distance**

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

Align method names with `../PartitionEqualSubsetSum.java`.

```java
package DSA.DynamicProgramming;

/*
 * Problem 09 — Partition Equal Subset Sum (LeetCode 416)
 */
public class PartitionEqualSubsetSum {

    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int x : nums) {
            for (int t = target; t >= x; t--) {
                dp[t] = dp[t] || dp[t - x];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] nums = { 1, 5, 11, 5 };
        System.out.println("Can partition: " + canPartition(nums));
    }
}
```
