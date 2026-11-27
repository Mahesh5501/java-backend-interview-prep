package DSA.SlidingWindow;

import java.util.Arrays;

/*
Problem: Maximum Average Subarray I (LeetCode 643)

Return the maximum average of any contiguous subarray of length k.

Examples:
  nums = [1, 12, -5, -6, 50, 3], k = 4 → 12.75

See notes/Problem_02_Maximum_Average_Subarray.md — every approach below matches the note.
*/

public class MaximumAverageSubarray {

    // APPROACH 1: BRUTE FORCE (sum each window, divide by k)
    // Why: direct definition — compare (sum/k) for every start index.
    // Time: O(n * k)   Extra space: O(1)
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

    // APPROACH 2: FIXED WINDOW — TRACK MAX SUM, DIVIDE ONCE (OPTIMAL)
    // Why: fixed k → argmax(average) = argmax(sum); slide in O(1) per step.
    // Time: O(n)   Extra space: O(1)
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

    // APPROACH 3: GROW / SHRINK — UPDATE MAX AVERAGE EACH FULL WINDOW
    // Why: compares (double) sum/k at every step — interview-friendly mental model.
    // Time: O(n)   Extra space: O(1)
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
        System.out.println("Brute:    " + findMaxAverageBruteForce(nums, k));
        System.out.println("Max sum:  " + findMaxAverage(nums, k));
        System.out.println("Per-step: " + findMaxAveragePerStep(nums, k));
        System.out.println();

        System.out.println("Single element: " + findMaxAverage(new int[] { 5 }, 1));
    }
}