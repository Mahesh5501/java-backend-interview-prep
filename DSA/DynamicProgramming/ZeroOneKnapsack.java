package DSA.DynamicProgramming;

/*
 * Problem 06 — 0/1 Knapsack (classic teaching)
 */
public class ZeroOneKnapsack {

    public static int knapsack2D(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            int wt = weights[i - 1];
            int val = values[i - 1];
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];
                if (wt <= w) {
                    dp[i][w] = Math.max(dp[i][w], val + dp[i - 1][w - wt]);
                }
            }
        }
        return dp[n][capacity];
    }

    public static int knapsack1D(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < weights.length; i++) {
            int wt = weights[i], val = values[i];
            for (int w = capacity; w >= wt; w--) {
                dp[w] = Math.max(dp[w], val + dp[w - wt]);
            }
        }
        return dp[capacity];
    }

    public static void main(String[] args) {
        int[] w = { 1, 3, 4, 5 };
        int[] v = { 1, 4, 5, 7 };
        int cap = 7;
        System.out.println("2D: " + knapsack2D(w, v, cap));
        System.out.println("1D: " + knapsack1D(w, v, cap));
    }
}