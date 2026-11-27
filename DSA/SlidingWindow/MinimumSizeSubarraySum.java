package DSA.SlidingWindow;

import java.util.Arrays;

/*
 * =============================================================================
 * Problem 03 — Minimum Size Subarray Sum (LeetCode 209)
 * Pattern:    Variable-size window — expand right, shrink left while valid
 * Condition:  sum of window >= target (array has positive integers only)
 * =============================================================================
 */
public class MinimumSizeSubarraySum {

    // -------------------------------------------------------------------------
    // Approach 1 — Brute force: every subarray
    // Time: O(n^2)   Space: O(1)
    // -------------------------------------------------------------------------
    public static int minSubArrayLenBruteForce(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int best = Integer.MAX_VALUE;

        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum >= target) {
                    best = Math.min(best, end - start + 1);
                    break;
                }
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    // -------------------------------------------------------------------------
    // Approach 2 — Optimal: sliding window
    // Time: O(n)   Space: O(1)
    // -------------------------------------------------------------------------
    public static int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int left = 0;
        int sum = 0;
        int best = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum >= target) {
                int windowLen = right - left + 1;
                best = Math.min(best, windowLen);
                sum -= nums[left];
                left++;
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    public static void main(String[] args) {
        System.out.println("target=7, nums=" + Arrays.toString(new int[] { 2, 3, 1, 2, 4, 3 }));
        System.out.println("Brute:   " + minSubArrayLenBruteForce(7, new int[] { 2, 3, 1, 2, 4, 3 }));
        System.out.println("Optimal: " + minSubArrayLen(7, new int[] { 2, 3, 1, 2, 4, 3 }));
        System.out.println();

        System.out.println("target=4, nums=[1,4,4] -> " + minSubArrayLen(4, new int[] { 1, 4, 4 }));
        System.out.println("target=20, nums=[1,2,3,4,5] -> " + minSubArrayLen(20, new int[] { 1, 2, 3, 4, 5 }));
    }
}