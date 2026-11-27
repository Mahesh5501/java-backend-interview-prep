package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Problem: Two Sum

Given nums and target, return two different indices whose values sum to target.
Exactly one solution exists (LeetCode-style).

Examples:
  [2, 7, 11, 15], target = 9  →  [0, 1]
  [3, 2, 4], target = 6       →  [1, 2]
  [3, 3], target = 6          →  [0, 1]

See notes/Problem_01_Two_Sum.md for full explanation of each approach.
*/

public class TwoSum {

    // APPROACH 1: BRUTE FORCE
    // Check every pair (i, j) with j > i.
    // Why use it: simplest correct baseline in an interview.
    // Time: O(n^2)   Extra space: O(1)
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

    // APPROACH 2: HASHMAP ONE PASS (OPTIMAL — what we use in interviews)
    // Store value → index. At each i, look up complement = target - nums[i].
    // Why HashMap: need fast "have I seen this value?" AND its index.
    // Why check before put: avoid reusing the same index (e.g. [3,3], target 6).
    // Why not Set: Set cannot return the partner index.
    // Why not sort+two pointers here: sorting loses indices unless we keep pairs.
    // Time: O(n)   Extra space: O(n)
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
        System.out.println("Brute:   " + Arrays.toString(twoSumBruteForce(a, 9)));
        System.out.println("Optimal: " + Arrays.toString(twoSum(a, 9)));

        int[] b = { 3, 2, 4 };
        System.out.println("Optimal: " + Arrays.toString(twoSum(b, 6)));

        int[] c = { 3, 3 };
        System.out.println("Dupes:   " + Arrays.toString(twoSum(c, 6)));
    }
}
