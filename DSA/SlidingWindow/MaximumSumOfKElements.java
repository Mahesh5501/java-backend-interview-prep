package DSA.SlidingWindow;

import java.util.Arrays;

/*
 * =============================================================================
 * Problem 01 — Maximum Sum of K Consecutive Elements
 * Pattern:    Fixed-size sliding window
 * Idea:       Keep window size exactly k — add entering, subtract leaving
 * =============================================================================
 */
public class MaximumSumOfKElements {

    // -------------------------------------------------------------------------
    // Approach 1 — Brute force: try every window of size k
    // Time: O(n * k)   Space: O(1)
    // -------------------------------------------------------------------------
    public static int maxSumBruteForce(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) {
            return 0;
        }

        int best = Integer.MIN_VALUE;

        for (int start = 0; start <= arr.length - k; start++) {
            int sum = 0;
            for (int i = start; i < start + k; i++) {
                sum += arr[i];
            }
            best = Math.max(best, sum);
        }
        return best;
    }

    // -------------------------------------------------------------------------
    // Approach 2 — Optimal: fixed sliding window
    // Time: O(n)   Space: O(1)
    // -------------------------------------------------------------------------
    public static int maxSumFixedWindow(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) {
            return 0;
        }

        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        int best = windowSum;

        for (int right = k; right < arr.length; right++) {
            windowSum += arr[right];       // new element enters
            windowSum -= arr[right - k];   // old element leaves
            best = Math.max(best, windowSum);
        }
        return best;
    }

    // Same style as older files: grow with right, shrink left when full
    public static int maxSumGrowShrink(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) {
            return 0;
        }

        int left = 0;
        int sum = 0;
        int best = Integer.MIN_VALUE;

        for (int right = 0; right < arr.length; right++) {
            sum += arr[right];

            if (right - left + 1 == k) {
                best = Math.max(best, sum);
                sum -= arr[left];
                left++;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] nums = { 2, 1, 5, 1, 3, 2 };
        int k = 3;

        System.out.println("Array: " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("Brute Force:     " + maxSumBruteForce(nums, k));
        System.out.println("Fixed Window:    " + maxSumFixedWindow(nums, k));
        System.out.println("Grow / Shrink:   " + maxSumGrowShrink(nums, k));
        System.out.println();

        int[] nums2 = { 2, 3, 4, 1 };
        System.out.println("Array: " + Arrays.toString(nums2) + ", k = 2");
        System.out.println("Fixed Window:    " + maxSumFixedWindow(nums2, 2));
    }
}