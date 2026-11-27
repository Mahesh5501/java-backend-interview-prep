package DSA.DynamicProgramming;

/*
 * Problem 07 — Longest Common Subsequence (LeetCode 1143)

See notes/Problem_07_Longest_Common_Subsequence.md — every approach below matches the note.
 */
public class LongestCommonSubsequence {

    // APPROACH 1: BRUTE — recurse on prefixes
    // Time: O(2^(m+n)) worst   Extra space: O(m+n) stack
    public static int longestCommonSubsequenceBrute(String text1, String text2) {
        return lcsBrute(text1.length(), text2.length(), text1, text2);
    }

    private static int lcsBrute(int i, int j, String a, String b) {
        if (i == 0 || j == 0) {
            return 0;
        }
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return 1 + lcsBrute(i - 1, j - 1, a, b);
        }
        return Math.max(lcsBrute(i - 1, j, a, b), lcsBrute(i, j - 1, a, b));
    }

    // APPROACH 2: 2D DP (OPTIMAL — what we use)
    // Time: O(m·n)   Extra space: O(m·n)
    public static int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("Brute LCS: " + longestCommonSubsequenceBrute("abcde", "ace"));
        System.out.println("DP LCS:    " + longestCommonSubsequence("abcde", "ace"));
    }
}