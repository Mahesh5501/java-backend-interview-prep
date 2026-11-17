package DSA.TwoPointers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Problem: Two Sum

Given an integer array nums and an integer target, return true if there are two different elements whose sum equals the target.
Examples:

Input: nums = [2, 7, 11, 15] target = 9
Output: true

Input: nums = [1, 2, 3, 4] target = 10
Output: false

Input: nums = [3, 3] target = 6
Output: true

Expected: Try to solve in O(n) time.
*/

public class TwoSum {

    public static void main(String[] args) {
        System.out.println(hasTwoSum(new int[]{2, 7, 11, 15}, 9)); // true
        System.out.println(hasTwoSum(new int[]{1, 2, 3, 4}, 10)); // false
        System.out.println(hasTwoSum(new int[]{3, 3}, 6)); // true
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9))); // [0, 1]
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

    public static int[] twoSum(int[] arr, int target) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int current = arr[i];
            int required = target - current;

            // Have we already seen the required number?
            if (map.containsKey(required)) {
                return new int[]{map.get(required), i};
            }
            map.put(current, i); // Store current number and its index
        }
        return new int[]{-1, -1};
    }
}
