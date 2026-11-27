package DSA.DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Problem 03 — Coin Change (LeetCode 322)

See notes/Problem_03_Coin_Change.md — every approach below matches the note.
 */
public class CoinChange {

    // APPROACH 1: BRUTE DFS + MEMO
    // Try every coin on remaining amount; memoize amount → min coins.
    // Time: O(amount · |coins|) with memo   Extra space: O(amount)
    public static int coinChangeBrute(int[] coins, int amount) {
        Map<Integer, Integer> memo = new HashMap<>();
        int ans = dfs(amount, coins, memo);
        return ans > amount ? -1 : ans;
    }

    private static int dfs(int amount, int[] coins, Map<Integer, Integer> memo) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return amount + 1;
        }
        if (memo.containsKey(amount)) {
            return memo.get(amount);
        }
        int best = amount + 1;
        for (int c : coins) {
            best = Math.min(best, 1 + dfs(amount - c, coins, memo));
        }
        memo.put(amount, best);
        return best;
    }

    // APPROACH 2: BOTTOM-UP DP (OPTIMAL — what we use)
    // dp[a] = min coins to make amount a.
    // Time: O(amount · |coins|)   Extra space: O(amount)
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
        System.out.println("Brute 11: " + coinChangeBrute(coins, 11));
        System.out.println("DP    11: " + coinChange(coins, 11));
        System.out.println("DP     3: " + coinChange(coins, 3));
    }
}