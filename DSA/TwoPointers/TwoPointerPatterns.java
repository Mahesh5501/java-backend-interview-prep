package DSA.TwoPointers;

import java.util.Arrays;

/**
 * =====================================================================
 * MASTER CLASS: TwoPointerPatterns
 * =====================================================================
 * Pattern: Two Pointers / In-Place Array Rearrangement
 * 
 * Core Concept: 
 * Instead of allocating a new array (which costs O(n) space), we use two index trackers ('left' and 'right') to read, evaluate, and swap 
 * elements directly within the existing array in O(n) time and O(1) space.
 * =====================================================================
 */
public class TwoPointerPatterns {

    public static void main(String[] args) {
        System.out.println("=== RUNNING TWO-POINTER PATTERN EXAMPLES ===");

        // 1. Move Zeroes Example
        int[] arr1 = {0, 1, 0, 3, 12};
        moveZeroes(arr1);
        System.out.println("1. Move Zeroes:          " + Arrays.toString(arr1));

        // 2. Remove Specific Element Example
        int[] arr2 = {3, 2, 2, 3, 4, 3};
        int newLength = removeElement(arr2, 3);
        System.out.println("2. Remove Element (val=3): " + Arrays.toString(Arrays.copyOf(arr2, newLength)) + " (New Length: " + newLength + ")");

        // 3. Squares of a Sorted Array Example (Two Ends Pointer)
        int[] arr3 = {-4, -1, 0, 3, 10};
        int[] squaredArr = sortedSquares(arr3);
        System.out.println("3. Sorted Squares:       " + Arrays.toString(squaredArr));
    }

    /**
     * -----------------------------------------------------------------
     * 1. MOVE ZEROES TO THE END
     * -----------------------------------------------------------------
     * Logic: 
     * - 'left' tracks the insertion position for the next non-zero element.
     * - 'right' scans through the entire array.
     * - Whenever 'right' finds a non-zero, it swaps it with 'left' and advances 'left'.
     * 
     * Time Complexity:  O(n) - Single pass through the array.
     * Space Complexity: O(1) - In-place manipulation.
     */
    public static void moveZeroes(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int left = 0;

        for (int right = 0; right < arr.length; right++) {
            // Found a non-zero element
            if (arr[right] != 0) {
                // Swap elements to push non-zero to the front partition
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;

                left++; // Advance the placement pointer
            }
        }
    }

    /**
     * -----------------------------------------------------------------
     * 2. REMOVE ALL INSTANCES OF A VALUE IN-PLACE
     * -----------------------------------------------------------------
     * Logic:
     * - Similar to Move Zeroes. 'left' tracks where valid (non-target) elements go.
     * - 'right' scans for elements that DO NOT equal the target value.
     * 
     * Time Complexity:  O(n) - Single pass.
     * Space Complexity: O(1) - Constant auxiliary space.
     */
    public static int removeElement(int[] arr, int val) {
        if (arr == null) {
            return 0;
        }

        int left = 0;

        for (int right = 0; right < arr.length; right++) {
            // If the current element is NOT the value we want to remove
            if (arr[right] != val) {
                arr[left] = arr[right]; // Keep it by placing it at 'left'
                left++;
            }
        }

        return left; // 'left' represents the new effective length of the array
    }

    /**
     * -----------------------------------------------------------------
     * 3. SQUARES OF A SORTED ARRAY (Two Pointers from Opposite Ends)
     * -----------------------------------------------------------------
     * Logic:
     * - Array might have negative numbers whose squares could be large (e.g., -4^2 = 16).
     * - Place one pointer at the start (left) and one at the end (right).
     * - Compare absolute values: the larger absolute value squared belongs at the end of our result array. Move pointers inward.
     * 
     * Time Complexity:  O(n) - Single pass filling the result array backwards.
     * Space Complexity: O(n) - Required to store the sorted output array.
     */
    public static int[] sortedSquares(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];
        
        int left = 0;
        int right = n - 1;
        int resultIdx = n - 1; // Fill result array from largest to smallest

        while (left <= right) {
            int leftSquare = arr[left] * arr[left];
            int rightSquare = arr[right] * arr[right];

            // Put the larger square at the current end of the result array
            if (leftSquare > rightSquare) {
                result[resultIdx] = leftSquare;
                left++;
            } else {
                result[resultIdx] = rightSquare;
                right--;
            }
            resultIdx--;
        }

        return result;
    }
}
