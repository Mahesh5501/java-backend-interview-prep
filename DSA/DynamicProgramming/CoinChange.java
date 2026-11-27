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