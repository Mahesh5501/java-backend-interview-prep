package DSA.DynamicProgramming;

import java.util.Arrays;

/*
 * Problem 04 — Longest Increasing Subsequence (LeetCode 300)

See notes/Problem_04_Longest_Increasing_Subsequence.md — every approach below matches the note.
 */
public class LongestIncreasingSubsequence {

    // APPROACH 1: BRUTE — try all subsequences (small n only)
    // Time: O(2^n)   Extra space: O(n)
    public static int lengthOfLISBrute(int[] nums) {
        return lisBrute(0, Integer.MIN_VALUE, nums);
    }

    private static int lisBrute(int i, int prev, int[] nums) {
        if (i == nums.length) {
            return 0;
        }
        int skip = lisBrute(i + 1, prev, nums);
        int take = 0;
        if (nums[i] > prev) {
            take = 1 + lisBrute(i + 1, nums[i], nums);
        }
        return Math.max(skip, take);
    }

    // APPROACH 2: O(n^2) DP
    // dp[i] = LIS length ending at i.
    // Time: O(n^2)   Extra space: O(n)
    public static int lengthOfLISQuadratic(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int best = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    // APPROACH 3: PATIENCE SORTING / TAILS (OPTIMAL — what we use)
    // Time: O(n log n)   Extra space: O(n)
    public static int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int lo = 0, hi = size;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails[mid] < x) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            tails[lo] = x;
            if (lo == size) {
                size++;
            }
        }
        return size;
    }

    public static void main(String[] args) {
        int[] nums = { 10, 9, 2, 5, 3, 7, 101, 18 };
        System.out.println("brute:   " + lengthOfLISBrute(new int[] { 4, 2, 3, 1 }));
        System.out.println("n^2:     " + lengthOfLISQuadratic(nums));
        System.out.println("n log n: " + lengthOfLIS(nums));
    }
}