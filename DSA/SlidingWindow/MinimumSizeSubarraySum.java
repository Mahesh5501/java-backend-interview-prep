package DSA.SlidingWindow;

/*
Problem: Minimum Size Subarray Sum
Given an array of positive integers and a positive integer target, find the minimum length of a contiguous subarray whose sum is greater than or equal to target.
If no such subarray exists, return 0.

Examples:
Input:
target = 7
arr = [2, 3, 1, 2, 4, 3]

Output:
2

Explanation:
The subarray [4, 3] has sum = 7
and length = 2, which is the minimum.

Input:
target = 4
arr = [1, 4, 4]

Output:
1

Input:
target = 20
arr = [1, 2, 3, 4, 5]

Output:
0

Expected Approach:
Sliding Window
*/

public class MinimumSizeSubarraySum {

    public static void main(String[] args) {
        System.out.println(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3})); // 2
        System.out.println(minSubArrayLen(4, new int[]{1, 4, 4})); // 1
        System.out.println(minSubArrayLen(20, new int[]{1, 2, 3, 4, 5})); // 0
    }

    static int minSubArrayLen(int target, int[] arr) {
        int left = 0;
        int length = Integer.MAX_VALUE;
        int sum = 0;
        for (int right = 0; right < arr.length; right++) {
            sum = sum + arr[right];
            while (sum >= target) {
                int currLen = right - left + 1;
                length = Math.min(currLen, length);
                sum = sum - arr[left];
                left++;
            }
        }
        return length == Integer.MAX_VALUE ? 0 : length;
    }
}
