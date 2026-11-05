package DSA.BinarySearch;

/*
Problem: Binary Search

Given a sorted integer array nums and an integer target, return the index of target if it exists.
If target does not exist, return -1.

Examples:

Input:
nums = [1, 3, 5, 7, 9]
target = 7

Output: 3

Input:
nums = [1, 3, 5, 7, 9]
target = 4

Output: -1

Input:
nums = [2, 4, 6, 8]
target = 2

Output: 0

Expected Approach:
Binary Search

Time: O(log n)
Space: O(1)
*/

public class BinarySearch {

    public static void main(String[] args) {
        System.out.println(search(new int[]{1, 3, 5, 7, 9}, 7)); // 3
        System.out.println(search(new int[]{1, 3, 5, 7, 9}, 4)); // -1
        System.out.println(search(new int[]{2, 4, 6, 8}, 2)); // 0
    }

    static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
