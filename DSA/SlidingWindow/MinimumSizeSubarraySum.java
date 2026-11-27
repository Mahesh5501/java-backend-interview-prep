package DSA.SlidingWindow;

import java.util.Arrays;

/*
Problem: Minimum Size Subarray Sum (LeetCode 209)

Return the minimal length of a contiguous subarray whose sum is >= target.
Assume positive integers only.

Examples:
  target = 7, nums = [2,3,1,2,4,3] → 2

See notes/Problem_03_Minimum_Size_Subarray_Sum.md — every approach below matches the note.
*/

public class MinimumSizeSubarraySum {

    // APPROACH 1: BRUTE FORCE (every subarray from each start)
    // Why: try every start, extend until sum >= target.
    // Time: O(n^2)   Extra space: O(1)
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

    // APPROACH 2: VARIABLE SLIDING WINDOW — EXPAND RIGHT, SHRINK WHILE VALID (OPTIMAL)
    // Why: each index enters and leaves at most once → shortest valid window per end.
    // Time: O(n)   Extra space: O(1)
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
        int[] nums = { 2, 3, 1, 2, 4, 3 };
        System.out.println("target=7, nums=" + Arrays.toString(nums));
        System.out.println("Brute:   " + minSubArrayLenBruteForce(7, nums));
        System.out.println("Window:  " + minSubArrayLen(7, nums));
        System.out.println();

        System.out.println("target=4, nums=[1,4,4] -> " + minSubArrayLen(4, new int[] { 1, 4, 4 }));
        System.out.println("target=20, nums=[1,2,3,4,5] -> " + minSubArrayLen(20, new int[] { 1, 2, 3, 4, 5 }));
    }
}