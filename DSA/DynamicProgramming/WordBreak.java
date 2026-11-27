package DSA.DynamicProgramming;

import java.util.*;

/*
 * Problem 08 — Word Break (LeetCode 139)

See notes/Problem_08_Word_Break.md — every approach below matches the note.
 */
public class WordBreak {

    // APPROACH 1: BRUTE DFS
    // Try every prefix cut; recurse on suffix when prefix is in dict.
    // Time: exponential   Extra space: O(n) stack
    public static boolean wordBreakBrute(String s, List<String> wordDict) {
        return canBreak(0, s, new HashSet<>(wordDict));
    }

    private static boolean canBreak(int start, String s, Set<String> dict) {
        if (start == s.length()) {
            return true;
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (dict.contains(s.substring(start, end)) && canBreak(end, s, dict)) {
                return true;
            }
        }
        return false;
    }

    // APPROACH 2: 1D DP (OPTIMAL — what we use)
    // dp[i] = can prefix s[0..i) be segmented?
    // Time: O(n^2 · L)   Extra space: O(n)
    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        List<String> dict = Arrays.asList("leet", "code");
        System.out.println("Brute: " + wordBreakBrute("leetcode", dict));
        System.out.println("DP:    " + wordBreak("leetcode", dict));
    }
}