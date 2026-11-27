package DSA.SlidingWindow;

import java.util.Arrays;

/*
Problem: Max Consecutive Ones III (LeetCode 1004)

Flip at most k zeros to 1's — return max length of consecutive 1's in a window.

Examples:
  nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2 → 6

See notes/Problem_06_Max_Consecutive_Ones_III.md — every approach below matches the note.
*/

public class MaxConsecutiveOnesIII {

    // APPROACH 1: BRUTE FORCE (every subarray)
    // Why: count zeros per [start..end]; break when zeros > k.
    // Time: O(n^2)   Extra space: O(1)
    public static int longestOnesBruteForce(int[] nums, int k) {
        int best = 0;

        for (int start = 0; start < nums.length; start++) {
            int zeros = 0;
            for (int end = start; end < nums.length; end++) {
                if (nums[end] == 0) {
                    zeros++;
                }
                if (zeros > k) {
                    break;
                }
                best = Math.max(best, end - start + 1);
            }
        }

        return best;
    }

    // APPROACH 2: VARIABLE SLIDING WINDOW — AT MOST k ZEROS (OPTIMAL)
    // Why: expand right; while zeroCount > k, shrink left — O(n).
    // Time: O(n)   Extra space: O(1)
    public static int longestOnes(int[] nums, int k) {
        int left = 0;
        int zeroCount = 0;
        int best = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeroCount++;
            }

            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }

            best = Math.max(best, right - left + 1);
        }

        return best;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 };
        int k = 2;

        System.out.println("nums = " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("Brute:  " + longestOnesBruteForce(nums.clone(), k));
        System.out.println("Window: " + longestOnes(nums.clone(), k));
    }
}