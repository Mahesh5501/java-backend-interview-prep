package DSA.DynamicProgramming;

import java.util.Arrays;

/*
 * Problem 05 — Unique Paths (LeetCode 62)
 */
public class UniquePaths {

    public static int uniquePathsBrute(int m, int n) {
        return dfs(0, 0, m, n);
    }

    private static int dfs(int r, int c, int m, int n) {
        if (r >= m || c >= n) {
            return 0;
        }
        if (r == m - 1 && c == n - 1) {
            return 1;
        }
        return dfs(r + 1, c, m, n) + dfs(r, c + 1, m, n);
    }

    public static int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                dp[c] = dp[c] + dp[c - 1];
            }
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        System.out.println("3x7 DP: " + uniquePaths(3, 7));
        System.out.println("3x7 brute: " + uniquePathsBrute(3, 7));
    }
}