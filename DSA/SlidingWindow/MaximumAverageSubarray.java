package DSA.SlidingWindow;

/*
Problem: Maximum Average Subarray I
Given an integer array nums and an integer k, find the maximum average value of any contiguous subarray of length k.
Return the maximum average.
Examples:
Input:
nums = [1, 12, -5, -6, 50, 3]
k = 4

Output:
12.75

Explanation:
The subarray [12, -5, -6, 50] has the maximum average:
(12 - 5 - 6 + 50) / 4 = 12.75

Input:
nums = [5]
k = 1

Output:
5.0

Expected Approach:
Think about the size of the window.
*/

public class MaximumAverageSubarray {

    public static void main(String[] args) {
        System.out.println(maxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4)); // 12.75
        System.out.println(maxAverage(new int[]{5}, 1)); // 5.0
    }

    static double maxAverage(int[] nums, int k) {
        int left = 0;
        double avarage = 0;
        int sum = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            if (right - left + 1 == k) {
                double currentAvarage = (double) sum / k;
                avarage = Math.max(currentAvarage, avarage);
                sum -= nums[left];
                left++;
            }
        }
        return avarage;
    }
}
