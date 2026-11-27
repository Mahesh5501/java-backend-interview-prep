package DSA.SlidingWindow;

import java.util.Arrays;

/*
 * =============================================================================
 * Problem 02 — Maximum Average Subarray I (LeetCode 643)
 * Pattern:    Fixed-size sliding window (same as max sum, then divide by k)
 * Return:     double — maximum average of any subarray of length k
 * =============================================================================
 */
public class MaximumAverageSubarray {

    // -------------------------------------------------------------------------
    // Approach 1 — Brute force
    // Time: O(n * k)   Space: O(1)
    // -------------------------------------------------------------------------
    public static double findMaxAverageBruteForce(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return 0.0;
        }

        double best = Integer.MIN_VALUE;

        for (int start = 0; start <= nums.length - k; start++) {
            int sum = 0;
            for (int i = start; i < start + k; i++) {
                sum += nums[i];
            }
            best = Math.max(best, (double) sum / k);
        }
        return best;
    }

    // -------------------------------------------------------------------------
    // Approach 2 — Optimal: track max SUM in window, divide once at end
    // (Dividing each step also works — we show both ideas in notes.)
    // Time: O(n)   Space: O(1)
    // -------------------------------------------------------------------------
    public static double findMaxAverage(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return 0.0;
        }

        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int bestSum = windowSum;

        for (int right = k; right < nums.length; right++) {
            windowSum += nums[right];
            windowSum -= nums[right - k];
            bestSum = Math.max(bestSum, windowSum);
        }

        return (double) bestSum / k;
    }

    // Alternative: update max average on every full window (matches mental model)
    public static double findMaxAveragePerStep(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return 0.0;
        }

        int left = 0;
        int sum = 0;
        double bestAvg = Double.NEGATIVE_INFINITY;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            if (right - left + 1 == k) {
                double currentAvg = (double) sum / k;
                bestAvg = Math.max(bestAvg, currentAvg);
                sum -= nums[left];
                left++;
            }
        }
        return bestAvg;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 12, -5, -6, 50, 3 };
        int k = 4;

        System.out.println("nums: " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("Brute Force:  " + findMaxAverageBruteForce(nums, k));
        System.out.println("Optimal:      " + findMaxAverage(nums, k));
        System.out.println("Per-step avg: " + findMaxAveragePerStep(nums, k));
        System.out.println();

        System.out.println("Single element: " + findMaxAverage(new int[] { 5 }, 1));
    }
}