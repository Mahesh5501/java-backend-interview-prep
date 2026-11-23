package DSA.TwoPointers;

import java.util.Arrays;

public class SquaresOfASortedArray {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (square, then sort)
    // Time Complexity: O(n log n)
    // Auxiliary Space: O(n) for the answer array
    // =====================================================

    public static int[] sortedSquaresBruteForce(int[] nums) {

        int[] squared = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            squared[i] = nums[i] * nums[i];
        }

        Arrays.sort(squared);
        return squared;
    }


    // =====================================================
    // APPROACH 2: TWO POINTERS (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(n) for the answer array
    // =====================================================

    public static int[] sortedSquaresTwoPointers(int[] nums) {

        int left = 0;
        int right = nums.length - 1;
        int[] result = new int[nums.length];
        int write = nums.length - 1;

        // The largest squared value must be at one of the two ends.
        while (left <= right) {

            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];

            if (leftSquare > rightSquare) {
                result[write] = leftSquare;
                left++;
            } else {
                result[write] = rightSquare;
                right--;
            }

            write--;
        }

        return result;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        int[] nums = { -4, -1, 0, 3, 10 };

        System.out.println("Input:      " + Arrays.toString(nums));
        System.out.println(
                "Brute:      " + Arrays.toString(sortedSquaresBruteForce(nums.clone()))
        );
        System.out.println(
                "Two Ptrs:   " + Arrays.toString(sortedSquaresTwoPointers(nums.clone()))
        );
    }
}