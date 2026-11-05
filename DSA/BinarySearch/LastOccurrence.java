package DSA.BinarySearch;

/*
Problem: Last Occurrence of Target

Given a sorted integer array nums that may contain duplicates, return the index of the last occurrence of target.
If target does not exist, return -1.

Examples:

Input:
nums = [1, 2, 2, 2, 3, 4]
target = 2

Output: 3

Input:
nums = [1, 2, 3, 4, 5]
target = 4

Output: 3

Input:
nums = [1, 2, 3, 4, 5]
target = 6

Output: -1

Expected Approach:
Binary Search

Time: O(log n)
Space: O(1)
*/

public class LastOccurrence {
    public static void main(String[] args) {
        System.out.println(searchLast(new int[]{1, 2, 2, 2, 3, 4}, 2)); // 3
        System.out.println(searchLast(new int[]{1, 2, 3, 4, 5}, 4)); // 3
        System.out.println(searchLast(new int[]{1, 2, 3, 4, 5}, 6)); // -1
        System.out.println(searchLast(new int[]{2, 2, 2, 2}, 2)); // 3
    }

    static int searchLast(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int ans = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                ans = mid;
                left = mid+1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}