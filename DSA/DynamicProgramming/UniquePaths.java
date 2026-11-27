package DSA.DynamicProgramming;

import java.util.Arrays;

/*
 * Problem 05 — Unique Paths (LeetCode 62)

See notes/Problem_05_Unique_Paths.md — every approach below matches the note.
 */
public class UniquePaths {

    // APPROACH 1: BRUTE DFS
    // Time: O(C(m+n-2,m-1))   Extra space: O(m+n) stack
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

    // APPROACH 2: 1-ROW DP (OPTIMAL — what we use)
    // Time: O(m·n)   Extra space: O(n)
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