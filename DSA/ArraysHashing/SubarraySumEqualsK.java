package DSA.ArraysHashing;

import java.util.HashMap;
import java.util.Map;

/*
Problem: Subarray Sum Equals K

Return the number of contiguous subarrays whose sum equals k.

Example:
  nums = [1,1,1], k = 2  →  2

Approach: Prefix sum + map of prefix → count of times seen
Time: O(n)   Space: O(n)
*/

public class SubarraySumEqualsK {

    public static int subarraySumBrute(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);

        int prefix = 0;
        int count = 0;

        for (int x : nums) {
            prefix += x;
            count += prefixCount.getOrDefault(prefix - k, 0);
            prefixCount.put(prefix, prefixCount.getOrDefault(prefix, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        int[] a = { 1, 1, 1 };
        System.out.println("Brute:   " + subarraySumBrute(a, 2));
        System.out.println("Optimal:" + subarraySum(a, 2));

        int[] b = { 1, 2, 3 };
        System.out.println("Optimal:" + subarraySum(b, 3)); // [1,2] and [3]

        int[] neg = { 1, -1, 0 };
        System.out.println("Neg:     " + subarraySum(neg, 0));
    }
}