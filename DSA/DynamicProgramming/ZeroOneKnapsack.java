package DSA.DynamicProgramming;

/*
 * Problem 06 — 0/1 Knapsack (classic teaching)

See notes/Problem_06_Zero_One_Knapsack.md — every approach below matches the note.
 */
public class ZeroOneKnapsack {

    // APPROACH 1: BRUTE RECURSION
    // Include or skip each item — exponential without memo.
    // Time: O(2^n)   Extra space: O(n) stack
    public static int knapsackBrute(int[] weights, int[] values, int capacity) {
        return brute(0, weights, values, capacity);
    }

    private static int brute(int i, int[] weights, int[] values, int capacity) {
        if (i == weights.length || capacity == 0) {
            return 0;
        }
        int skip = brute(i + 1, weights, values, capacity);
        int take = 0;
        if (weights[i] <= capacity) {
            take = values[i] + brute(i + 1, weights, values, capacity - weights[i]);
        }
        return Math.max(skip, take);
    }

    // APPROACH 2: 2D DP TABLE
    // dp[i][w] = best value using first i items at capacity w.
    // Time: O(n·W)   Extra space: O(n·W)
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

    // APPROACH 3: 1D OPTIMIZED (OPTIMAL — what we use)
    // Walk capacity backward so each item used at most once.
    // Time: O(n·W)   Extra space: O(W)
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
        System.out.println("Brute: " + knapsackBrute(w, v, cap));
        System.out.println("2D:    " + knapsack2D(w, v, cap));
        System.out.println("1D:    " + knapsack1D(w, v, cap));
    }
}