package DSA.ArraysHashing;

import java.util.HashMap;
import java.util.Map;

/*
Problem: Subarray Sum Equals K

Count contiguous subarrays whose sum equals k.
(Negatives allowed — do not use sliding window for the general case.)

Example:
  [1,1,1], k = 2 → 2

See notes/Problem_08_Subarray_Sum_Equals_K.md for approach explanations.
*/

public class SubarraySumEqualsK {

    // APPROACH 1: BRUTE FORCE
    // For every start, expand end with a running sum; count sum == k.
    // Why mention it: clear definition of "every contiguous subarray".
    // Time: O(n^2)   Extra space: O(1)
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

    // APPROACH 2: PREFIX SUM + FREQUENCY MAP (OPTIMAL — what we use)
    // Why: subarray (left..right) sum = prefix[right] - prefix[left-1].
    // Want that == k → look for earlier prefixes equal to (prefix - k).
    // Why map of counts (not Set): same prefix can appear multiple times.
    // Why map.put(0, 1): empty prefix so subarrays starting at index 0 are counted.
    // Why not sliding window: negatives break window grow/shrink assumptions.
    // Time: O(n)   Extra space: O(n)
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
        System.out.println("Optimal: " + subarraySum(a, 2));

        int[] b = { 1, 2, 3 };
        System.out.println("Optimal: " + subarraySum(b, 3)); // [1,2] and [3]

        int[] neg = { 1, -1, 0 };
        System.out.println("Neg:     " + subarraySum(neg, 0));
    }
}
