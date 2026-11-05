package DSA.TwoPointers;

/*
Problem: Two Sum

Given an integer array nums and an integer target, return true if there are two different elements whose sum equals the target.
Examples:

Input:
nums = [2, 7, 11, 15]
target = 9

Output: true

Explanation:
2 + 7 = 9

Input:
nums = [1, 2, 3, 4]
target = 10

Output: false

Input:
nums = [3, 3]
target = 6

Output: true

Expected:
Try to solve in O(n) time.
*/

public class TwoSum {

    public static void main(String[] args) {
        System.out.println(hasTwoSum(new int[]{2, 7, 11, 15}, 9)); // true
        System.out.println(hasTwoSum(new int[]{1, 2, 3, 4}, 10)); // false
        System.out.println(hasTwoSum(new int[]{3, 3}, 6)); // true
    }

    static boolean hasTwoSum(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                return true;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }

        }

        return false;
    }
}
