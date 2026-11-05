package DSA.BinarySearch;

/*
Problem: First Occurrence of Target

Given a sorted integer array nums that may contain duplicates, return the index of the first occurrence of target.
If target does not exist, return -1.

Examples:

Input:
nums = [1, 2, 2, 2, 3, 4]
target = 2

Output: 1

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

public class FirstOccurrence {

    public static void main(String[] args) {
        System.out.println(searchFirst(new int[]{1, 2, 2, 2, 3, 4}, 2)); // 1
        System.out.println(searchFirst(new int[]{1, 2, 3, 4, 5}, 4)); // 3
        System.out.println(searchFirst(new int[]{1, 2, 3, 4, 5}, 6)); // -1
    }

    static int searchFirst(int[] nums, int target) {
        int left =0;
        int right = nums.length-1;
        int ans = -1;
        while(left <= right){
            int mid = left + (right - left) /2;
            if(nums[mid] == target){
                ans = mid;
                right = mid -1;
            } else if(nums[mid] > target){
                right = mid -1;
            } else {
                left = mid +1;
            }
        }
        return ans;
    }
}
