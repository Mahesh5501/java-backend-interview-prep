package DSA.SlidingWindow;

/*
Problem: Max Consecutive Ones III (LeetCode 1004)
Given a binary array nums and an integer k, return the maximum number of consecutive 1's
in the array if you can flip at most k 0's to 1's.

Example:
nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2 → 6

Expected Approach:
Variable-size sliding window — at most k zeros inside the window.
*/

public class MaxConsecutiveOnesIII {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (check every subarray)
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1)
    // =====================================================

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


    // =====================================================
    // APPROACH 2: SLIDING WINDOW (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

  public static int longestOnes(int[] nums, int k) {

        int left = 0;
        int zeroCount = 0;
        int best = 0;

        for (int right = 0; right < nums.length; right++) {

            // Expand: include nums[right]
            if (nums[right] == 0) {
                zeroCount++;
            }

            // Shrink while window has too many zeros
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }

            int currentLength = right - left + 1;
            best = Math.max(best, currentLength);
        }

        return best;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        int[] nums = { 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 };
        int k = 2;

        System.out.println("Input:  nums = " + java.util.Arrays.toString(nums) + ", k = " + k);
        System.out.println("Brute:  " + longestOnesBruteForce(nums.clone(), k));
        System.out.println("Window: " + longestOnes(nums.clone(), k)); // 6
    }
}