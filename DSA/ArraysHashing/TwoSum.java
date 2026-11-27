package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Problem: Two Sum

Given an integer array nums and an integer target, return indices i and j
such that nums[i] + nums[j] == target and i != j. Exactly one solution exists.

Examples:
  nums = [2, 7, 11, 15], target = 9  →  [0, 1]
  nums = [3, 2, 4], target = 6       →  [1, 2]

Approach: One pass HashMap — complement lookup
Time: O(n)   Space: O(n)
*/

public class TwoSum {

    // BRUTE: check every pair
    public static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    // OPTIMAL: store value → index; look for target - nums[i]
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> valueToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (valueToIndex.containsKey(complement)) {
                return new int[] { valueToIndex.get(complement), i };
            }
            valueToIndex.put(nums[i], i);
        }
        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] a = { 2, 7, 11, 15 };
        System.out.println("Brute:  " + Arrays.toString(twoSumBruteForce(a, 9)));
        System.out.println("Optimal:" + Arrays.toString(twoSum(a, 9)));

        int[] b = { 3, 2, 4 };
        System.out.println("Optimal:" + Arrays.toString(twoSum(b, 6)));
    }
}