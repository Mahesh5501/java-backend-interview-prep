package DSA.TwoPointers;

/*
Problem: Merge Sorted Array (simple two-array merge)

See notes/Problem_09_Merge_Sorted_Array.md — every approach below matches the note.
*/

import java.util.Arrays;

public class MergeSortedArray {

    // =====================================================
    // SIMPLE PROBLEM: merge two sorted arrays
    //
    // Input:  a = [1, 2, 3]
    //         b = [2, 5, 6]
    // Output:     [1, 2, 2, 3, 5, 6]
    //
    // Just two sorted arrays → one sorted result.
    // =====================================================


    // =====================================================
    // APPROACH 1: BRUTE FORCE
    // Copy both arrays into one big array, then sort it.
    // Time Complexity: O((a+b) log(a+b))
    // Auxiliary Space: O(a + b)
    // =====================================================

    public static int[] mergeBruteForce(int[] a, int[] b) {

        int[] result = new int[a.length + b.length]; // Create one array big enough for both inputs.

        // Copy every value from a into the result.
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }

        // Copy every value from b after a's values.
        for (int i = 0; i < b.length; i++) {
            result[a.length + i] = b[i];
        }

        Arrays.sort(result); // Sort the combined array.

        return result;
    }


    // =====================================================
    // APPROACH 2: TWO POINTERS (OPTIMAL for sorted inputs)
    // Compare the current heads of both arrays.
    // Always take the smaller one into the result.
    // Time Complexity: O(a + b)
    // Auxiliary Space: O(a + b) for the result array
    // =====================================================

    public static int[] mergeTwoPointers(int[] a, int[] b) {

       
        int[] result = new int[a.length + b.length];  // Final merged array.
        int i = 0;  // i reads from array a.
        int j = 0;  // j reads from array b.
        int k = 0; // k writes into the result.

        // While both arrays still have unread numbers.
        while (i < a.length && j < b.length) {

            // Take the smaller current number.
            if (a[i] <= b[j]) {
                result[k] = a[i];
                i++;
            } else {
                result[k] = b[j];
                j++;
            }
            k++; // Move to the next free write slot.
        }

        // If a still has leftover numbers, copy them.
        while (i < a.length) {
            result[k] = a[i];
            i++;
            k++;
        }

        // If b still has leftover numbers, copy them.
        while (j < b.length) {
            result[k] = b[j];
            j++;
            k++;
        }

        return result;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        int[] a = { 1, 2, 3 };
        int[] b = { 2, 5, 6 };

        System.out.println("Array a: " + Arrays.toString(a));
        System.out.println("Array b: " + Arrays.toString(b));

        int[] bruteForceAnswer = mergeBruteForce(a, b);
        System.out.println("Brute Force:  " + Arrays.toString(bruteForceAnswer));

        int[] twoPointersAnswer = mergeTwoPointers(a, b);
        System.out.println("Two Pointers: " + Arrays.toString(twoPointersAnswer));
    }
}
