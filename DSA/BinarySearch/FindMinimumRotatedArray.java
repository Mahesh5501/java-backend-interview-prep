package DSA.BinarySearch;
/*
Problem: Find Minimum in Rotated Sorted Array

You are given an array that was originally sorted in ascending order but was rotated at some unknown position.
Find and return the minimum element.

All elements are unique.
Examples:
Input: nums = [3, 4, 5, 1, 2]
Output: 1

Input: nums = [4, 5, 6, 7, 0, 1, 2]
Output: 0

Input: nums = [11, 13, 15, 17]
Output: 11

Input: nums = [5, 1, 2, 3, 4]
Output: 1

Expected Approach: Binary Search
Time: O(log n)
Space: O(1)
*/

public class FindMinimumRotatedArray {

    public static void main(String[] args) {

        System.out.println(findMin(new int[]{3, 4, 5, 1, 2})); // 1
        System.out.println(findMin(new int[]{4, 5, 6, 7, 0, 1, 2})); // 0
        System.out.println(findMin(new int[]{11, 13, 15, 17})); // 11
        System.out.println(findMin(new int[]{5, 1, 2, 3, 4})); // 1
    }

    static int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        if (nums == null || nums.length == 0) {
            return -1;
        }
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return nums[left];
    }
}
