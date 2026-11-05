package DSA.SlidingWindow;

/*
Problem: Maximum Sum of K Consecutive Elements

Given an integer array and an integer k, find the maximum sum of any contiguous subarray of size k.
Examples:
Input:
arr = [2, 1, 5, 1, 3, 2]
k = 3

Output: 9

Explanation:
[5, 1, 3] has the maximum sum = 9.

Input:
arr = [2, 3, 4, 1]
k = 2

Output: 7

Explanation:
[3, 4] has the maximum sum = 7.

Expected Approach:
Think about a fixed-size window.
*/

public class MaximumSumK {

    public static void main(String[] args) {
        System.out.println(maxSum(new int[]{2, 1, 5, 1, 3, 2}, 3)); // 9
        System.out.println(maxSum(new int[]{2, 3, 4, 1}, 2)); // 7
    }

    static int maxSum(int[] arr, int k) {
        int left = 0;
        int sum = 0;
        int maxSum = 0;

        for (int right = 0; right < arr.length; right++) {
            sum += arr[right];
            if (right - left + 1 == k) {
                maxSum = Math.max(sum, maxSum);
                sum -= arr[left];
                left++;
            }
        }
        return maxSum;
    }
}
