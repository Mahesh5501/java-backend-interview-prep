package DSA.BinarySearch;

/*
Problem: Search in Rotated Sorted Array

You are given an integer array nums that was originally sorted in ascending order, but then rotated at an unknown position.
Return the index of target if it exists.
Otherwise return -1.

All values are unique.
Examples:
Input:
nums = [4, 5, 6, 7, 0, 1, 2]
target = 0

Output: 4

Input:
nums = [4, 5, 6, 7, 0, 1, 2]
target = 3

Output: -1

Input:
nums = [1]
target = 1

Output: 0

Expected Approach:
Binary Search

Time: O(log n)
Space: O(1)
*/

public class SearchRotatedArray {
    public static void main(String[] args) {
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0)); // 4
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3)); // -1
        System.out.println(search(new int[]{1}, 1)); // 0
    }

    static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}