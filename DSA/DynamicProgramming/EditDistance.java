package DSA.DynamicProgramming;

/*
 * Problem 10 — Edit Distance (LeetCode 72)

See notes/Problem_10_Edit_Distance.md — every approach below matches the note.
 */
public class EditDistance {

    // APPROACH 1: BRUTE RECURSION (three operations)
    // Time: exponential   Extra space: O(m+n) stack
    public static int minDistanceBrute(String word1, String word2) {
        return editBrute(word1.length(), word2.length(), word1, word2);
    }

    private static int editBrute(int i, int j, String a, String b) {
        if (i == 0) {
            return j;
        }
        if (j == 0) {
            return i;
        }
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return editBrute(i - 1, j - 1, a, b);
        }
        return 1 + Math.min(editBrute(i - 1, j - 1, a, b),
                Math.min(editBrute(i - 1, j, a, b), editBrute(i, j - 1, a, b)));
    }

    // APPROACH 2: 2D DP (OPTIMAL — what we use)
    // Time: O(m·n)   Extra space: O(m·n)
    public static int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println("Brute: " + minDistanceBrute("horse", "ros"));
        System.out.println("DP:    " + minDistance("horse", "ros"));
    }
}