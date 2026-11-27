package DSA.SlidingWindow;

import java.util.Arrays;

/*
Problem: Maximum Sum of K Consecutive Elements

Return the maximum sum among all contiguous subarrays of length exactly k.

Examples:
  arr = [2, 1, 5, 1, 3, 2], k = 3 → 9

See notes/Problem_01_Maximum_Sum_of_K_Elements.md — every approach below matches the note.
*/

public class MaximumSumOfKElements {

    // APPROACH 1: BRUTE FORCE (every window of size k)
    // Why: try every start index — always correct baseline.
    // Time: O(n * k)   Extra space: O(1)
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

    // APPROACH 2: FIXED SLIDING WINDOW — ADD ENTER, SUBTRACT LEAVE (OPTIMAL)
    // Why: each slide updates sum in O(1) instead of rescanning k cells.
    // Time: O(n)   Extra space: O(1)
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
            windowSum += arr[right];
            windowSum -= arr[right - k];
            best = Math.max(best, windowSum);
        }
        return best;
    }

    // APPROACH 3: GROW WITH RIGHT, SHRINK LEFT WHEN LENGTH == k
    // Why: same O(n) idea using left/right — matches variable-window mental model.
    // Time: O(n)   Extra space: O(1)
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
        System.out.println("Brute:         " + maxSumBruteForce(nums, k));
        System.out.println("Fixed window:  " + maxSumFixedWindow(nums, k));
        System.out.println("Grow / shrink: " + maxSumGrowShrink(nums, k));
        System.out.println();

        int[] nums2 = { 2, 3, 4, 1 };
        System.out.println("Array: " + Arrays.toString(nums2) + ", k = 2");
        System.out.println("Fixed window:  " + maxSumFixedWindow(nums2, 2));
    }
}