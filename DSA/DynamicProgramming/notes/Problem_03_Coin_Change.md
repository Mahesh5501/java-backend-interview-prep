# Problem 03 — Coin Change

> **Pattern:** Unbounded knapsack / min DP on amount  
> **Goal:** **Fewest coins** to make amount `amount` (same coin reusable).

---

## 1. Problem statement

Integer array `coins` (denominations) and integer `amount`. Return the **minimum number of coins** needed to sum to `amount`, or **`-1`** if impossible.

Each coin has unlimited supply.

### Example 1

```text
Input:  coins = [1, 2, 5], amount = 11
Output: 3    (5 + 5 + 1)
```

### Example 2

```text
Input:  coins = [2], amount = 3
Output: -1
```

---

## 2. Mental picture

Fill a jar labelled `0 .. amount`. `dp[a]` = min coins to make exactly `a`.

```text
amount:  0  1  2  3  4  5  6 ... 11
dp:      0  1  1  2  2  1  2 ...  3
              ^           ^
         use 1¢      use 5¢
```

For each amount `a`, try every coin `c`:

```text
if c <= a:  dp[a] = min(dp[a], dp[a-c] + 1)
```

> **Build bigger amounts from smaller solved amounts.**

---

## 3. How to start thinking

1. **Greedy by largest coin?** Fails: coins `[1,3,4]`, amount `6` → greedy `4+1+1`=3 but optimal `3+3`=2.
2. **State?** `dp[a]` = min coins for amount `a`.
3. **Transition?** Last coin added some `c` → came from `a-c`.
4. **Base?** `dp[0] = 0` (zero coins for zero amount).
5. **Impossible?** Keep sentinel like `amount+1` if no coin fits.

---

## 4. Approach 1 — Brute / DFS (conceptual)

Try all combinations of coins summing to `amount`; track minimum length. Exponential without memo.

**Why slow:** Overlapping subproblems — e.g. making `5` appears on many paths.

---

## 5. Approach 2 — Optimal bottom-up DP

### Steps

1. `dp[0..amount]`, fill with `amount+1` (infinity).
2. `dp[0] = 0`.
3. For `a` from `1` to `amount`:
   - For each `coin` in `coins`:
     - If `coin <= a`: `dp[a] = min(dp[a], dp[a - coin] + 1)`.
4. Return `dp[amount] > amount ? -1 : dp[amount]`.

### Java (matches repo)

```java
public static int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int a = 1; a <= amount; a++) {
        for (int coin : coins) {
            if (coin <= a) {
                dp[a] = Math.min(dp[a], dp[a - coin] + 1);
            }
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```

### Complexity

| | Optimal |
|---|---|
| **Time** | `O(amount * len(coins))` |
| **Space** | `O(amount)` |

---

## 6. Hardest part

**Order of loops:**  
For **minimum coin count (unbounded)**, outer loop **amount**, inner **coins** is standard.

For **counting combinations** (LeetCode 518), you often swap / special order — do not mix patterns.

**Before:** “Why not BFS?” — BFS on amount states also works (`O(amount * coins)`), same spirit.

**After:** DP table is the textbook interview solution.

---

## 7. Dry run — coins `[1,2,5]`, amount `5`

| `a` | try coins | `dp[a]` |
|---:|---|---:|
| 0 | — | 0 |
| 1 | 1 | 1 |
| 2 | 1,2 | min(2,1)=1 |
| 3 | 1,2 | min(3,1+1)=2 |
| 4 | 1,2 | 2 |
| 5 | 1,2,5 | min(…, dp[0]+1)=**1** |

---

## 8. Edge cases

| Scenario | Result |
|---|---|
| `amount = 0` | 0 |
| empty way | `dp` stays INF → -1 |
| coin = 1 only | `amount` coins |
| large `amount` | watch array size |

---

## 9. Common mistakes

| Mistake | Effect |
|---|---|
| Using greedy | Wrong answer |
| `dp` initialized to 0 | Wrong min |
| Returning `dp[amount]` without INF check | False 0 |

---

## 10. Interview script

> “`dp[a]` is min coins for amount a. For each a I try every coin and relax from a-c. Initialize unreachable with amount+1. O(amount * coins).”

---

## 11. Follow-ups

- **Coin Change 2:** count ways — different recurrence + loop order.
- **Print coins:** parent pointers in `dp`.
- **BFS** level = number of coins.

---

## 12. Quick revision

- **`dp[0]=0`**, fill INF.
- **Outer amount, inner coins.**
- **`dp[a]=min(dp[a],dp[a-c]+1)`**.
- **Return -1** if unreachable.

---

## 13. Java source

`../CoinChange.java` — `coinChange`.

---

## 14. Next problem

**Problem 04 — Longest Increasing Subsequence** — `O(n^2)` vs patience sorting `O(n log n)`.

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

Align method names with `../CoinChange.java`.

```java
package DSA.DynamicProgramming;

import java.util.Arrays;

/*
 * Problem 03 — Coin Change (LeetCode 322)
 */
public class CoinChange {

    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int coin : coins) {
                if (coin <= a) {
                    dp[a] = Math.min(dp[a], dp[a - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = { 1, 2, 5 };
        System.out.println("Amount 11: " + coinChange(coins, 11));
        System.out.println("Amount 3: " + coinChange(coins, 3));
    }
}
```
