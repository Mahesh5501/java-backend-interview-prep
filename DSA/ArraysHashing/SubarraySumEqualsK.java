package DSA.ArraysHashing;

import java.util.HashMap;
import java.util.Map;

/*
Problem: Subarray Sum Equals K

Count contiguous subarrays whose sum equals k.

See notes/Problem_08_Subarray_Sum_Equals_K.md — every approach below matches the note.

IMPORTANT: sliding window only works when all numbers are non-negative.
LeetCode allows negatives → use prefix + map as the real answer.
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

    // APPROACH 2: SLIDING WINDOW — ONLY SAFE WHEN ALL nums[i] >= 0
    // Why it can work then: growing the right end increases sum; shrinking left decreases it.
    // Why NOT for LeetCode general case: negatives break that assumption.
    // Included so you can see the pattern and its limitation.
    // Time: O(n)   Extra space: O(1)
    public static int subarraySumSlidingWindowNonNegative(int[] nums, int k) {
        int count = 0;
        int left = 0;
        int sum = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] < 0) {
                throw new IllegalArgumentException(
                    "Sliding window version requires all nums[i] >= 0");
            }
            sum += nums[right];
            while (left <= right && sum > k) {
                sum -= nums[left];
                left++;
            }
            if (sum == k) {
                count++;
            }
        }
        return count;
    }

    // APPROACH 3: PREFIX SUM + FREQUENCY MAP (OPTIMAL — what we use)
    // Why: subarray (left..right) sum = prefix[right] - prefix[left-1].
    // Want that == k → look for earlier prefixes equal to (prefix - k).
    // Why map of counts (not Set): same prefix can appear multiple times.
    // Why map.put(0, 1): empty prefix so subarrays starting at index 0 are counted.
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
        System.out.println("Window:  " + subarraySumSlidingWindowNonNegative(a, 2));
        System.out.println("Optimal: " + subarraySum(a, 2));

        int[] b = { 1, 2, 3 };
        System.out.println("Optimal: " + subarraySum(b, 3)); // [1,2] and [3]

        int[] neg = { 1, -1, 0 };
        System.out.println("Neg map: " + subarraySum(neg, 0));
        // Do NOT call sliding window on neg — it throws on purpose.
    }
}
