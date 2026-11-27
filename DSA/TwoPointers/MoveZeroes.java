package DSA.TwoPointers;

/*
Problem: Move Zeroes

See notes/Problem_05_Move_Zeroes.md — every approach below matches the note.
*/

import java.util.Arrays;

public class MoveZeroes {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (EXTRA ARRAY)
    // Time Complexity: O(n)
    // Auxiliary Space: O(n)
    // =====================================================

    public static void moveZeroesBruteForce(int[] nums) {

        // Step 1: Build a temporary array.
        // New int[] slots are already filled with 0 by default.
        int[] temp = new int[nums.length];

        // Step 2: Copy every non-zero value in original order.
        int tempIndex = 0;
        for (int value : nums) {
            if (value != 0) {
                temp[tempIndex] = value;
                tempIndex++;
            }
        }

        // Step 3: Copy the result back into the original array.
        for (int i = 0; i < nums.length; i++) {
            nums[i] = temp[i];
        }
    }


    // =====================================================
    // APPROACH 2: TWO POINTERS — SLOW / FAST (READ / WRITE)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static void moveZeroesTwoPointers(int[] nums) {

        // writeIndex tells us where the next non-zero should go.
        int writeIndex = 0;

        // fastIndex reads every element from left to right.
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {

            // If we found a non-zero, place it at the write position.
            if (nums[fastIndex] != 0) {
                nums[writeIndex] = nums[fastIndex];
                writeIndex++;
            }
        }

        // Fill the rest of the array with zeros.
        while (writeIndex < nums.length) {
            nums[writeIndex] = 0;
            writeIndex++;
        }
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        int[] example = { 0, 1, 0, 3, 12 };

        int[] bruteInput = example.clone();
        int[] optimalInput = example.clone();

        System.out.println("Input:              " + Arrays.toString(example));

        moveZeroesBruteForce(bruteInput);
        System.out.println("Brute Force (extra): " + Arrays.toString(bruteInput));

        moveZeroesTwoPointers(optimalInput);
        System.out.println("Two Pointers:        " + Arrays.toString(optimalInput));
    }
}