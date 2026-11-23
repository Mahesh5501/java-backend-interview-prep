package DSA.TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RemoveDuplicatesFromSortedArray {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (EXTRA LIST / SET)
    // Time Complexity: O(n)
    // Auxiliary Space: O(n)
    // =====================================================

    public static int removeDuplicatesBruteForce(int[] nums) {

        Set<Integer> uniqueValues = new LinkedHashSet<>(); // LinkedHashSet keeps insertion order and removes duplicates.

        for (int value : nums) {
            uniqueValues.add(value);
        }

        // Copy unique values back into the front of the original array.
        int index = 0;
        for (int value : uniqueValues) {
            nums[index] = value;
            index++;
        }

        return uniqueValues.size(); // The new logical length is the number of unique values.
    }


    // =====================================================
    // APPROACH 2: TWO POINTERS — READ / WRITE (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int removeDuplicatesTwoPointers(int[] nums) {

        // If the array is empty, there is nothing to deduplicate.
        if (nums.length == 0) {
            return 0;
        }

        int writeIndex = 0; // writeIndex always points at the last confirmed unique value.

        // readIndex scans the entire array.
        for (int readIndex = 1; readIndex < nums.length; readIndex++) {

            // Because the array is sorted, a new value means a new unique item.
            if (nums[readIndex] != nums[writeIndex]) {
                writeIndex++;
                nums[writeIndex] = nums[readIndex];
            }
        }

        return writeIndex + 1; // Unique items occupy indices 0 through writeIndex inclusive.
    }


    // =====================================================
    // HELPER — print first k elements only (for debugging)
    // =====================================================

    public static void printFirstK(int[] nums, int k) {
        List<Integer> visible = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            visible.add(nums[i]);
        }
        System.out.println(visible);
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        int[] example = { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };

        int[] bruteInput = example.clone();
        int[] optimalInput = example.clone();

        System.out.println("Input: " + Arrays.toString(example));

        int bruteLength = removeDuplicatesBruteForce(bruteInput);
        System.out.println("Brute Force length:  " + bruteLength);
        System.out.print("Brute Force array:   ");
        printFirstK(bruteInput, bruteLength);

        int optimalLength = removeDuplicatesTwoPointers(optimalInput);
        System.out.println("Two Pointers length: " + optimalLength);
        System.out.print("Two Pointers array:  ");
        printFirstK(optimalInput, optimalLength);
    }
}