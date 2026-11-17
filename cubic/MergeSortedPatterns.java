package cubic;

import java.util.Arrays;

/**
 * =====================================================================
 * MASTER CLASS: MergeSortedPatterns
 * =====================================================================
 * Pattern: Two Pointers for Sorted Sequences (Merge Pattern)
 * 
 * Core Concept: 
 * When inputs are already sorted, taking advantage of that order allows you to compare elements side-by-side using independent pointers without needing expensive sorting algorithms like MergeSort or QuickSort.
 * =====================================================================
 */
public class MergeSortedPatterns {

    public static void main(String[] args) {
        System.out.println("=== RUNNING MERGE SORTED PATTERN EXAMPLES ===");

        // 1. Merge into a New Array Example
        int[] a = {1, 3, 5};
        int[] b = {2, 4, 6};
        int[] mergedNew = mergeIntoNewArray(a, b);
        System.out.println("1. Merge Into New Array: " + Arrays.toString(mergedNew));

        // 2. In-Place Merge from the Back Example (LeetCode 88 Style)
        // Array A has extra trailing space (zeros) to accommodate Array B
        int[] nums1 = {1, 3, 5, 0, 0, 0}; 
        int m = 3; // Number of valid elements in nums1
        int[] nums2 = {2, 4, 6};
        int n = 3; // Number of valid elements in nums2
        
        mergeInPlace(nums1, m, nums2, n);
        System.out.println("2. Merge In-Place:       " + Arrays.toString(nums1));
    }

    /**
     * -----------------------------------------------------------------
     * 1. MERGE TWO SORTED ARRAYS INTO A NEW ARRAY
     * -----------------------------------------------------------------
     * Logic:
     * - Pointer 'i' tracks array A, 'j' tracks array B, 'k' tracks result array.
     * - Compare elements at a[i] and b[j], place the smaller one into result[k].
     * - Whichever array has leftover elements gets appended at the end.
     * 
     * Time Complexity:  O(n + m) - We visit every element across both arrays once.
     * Space Complexity: O(n + m) - Required to store the newly created result array.
     */
    public static int[] mergeIntoNewArray(int[] a, int[] b) {
        if (a == null) return b;
        if (b == null) return a;

        int[] result = new int[a.length + b.length];
        int i = 0; // Pointer for array a
        int j = 0; // Pointer for array b
        int k = 0; // Pointer for result array

        // Compare elements from both arrays and add the smaller one
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[k++] = a[i++];

                // We can do above one line with below three lines for better readability
                // result[k] = a[i]; // 1. Assign the value at index i to index k
                // i++; // 2. Move forward in array 'a'
                // k++; // 3. Move forward in the 'result' array

            } else {
                result[k++] = b[j++];
            }
        }

        // Copy any remaining elements from array a (if any)
        while (i < a.length) {
            result[k++] = a[i++];
        }

        // Copy any remaining elements from array b (if any)
        while (j < b.length) {
            result[k++] = b[j++];
        }

        return result;
    }

    /**
     * -----------------------------------------------------------------
     * 2. IN-PLACE MERGE FROM THE BACK (Zero Extra Space)
     * -----------------------------------------------------------------
     * Logic:
     * - If array A has enough trailing space at the end to fit array B, merging from the *front* will overwrite elements we haven't read yet.
     * - Solution: Start pointers at the **back** of the valid elements (`i = m - 1`, `j = n - 1`) and fill the result backwards from the absolute end.
     * 
     * Time Complexity:  O(n + m) - Single pass from back to front.
     * Space Complexity: O(1) - Done entirely in-place without auxiliary arrays.
     */
    public static void mergeInPlace(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;          // Pointer to last valid element in nums1
        int j = n - 1;          // Pointer to last element in nums2
        int k = m + n - 1;      // Pointer to the very end of nums1 (the writing target)

        // Merge backwards, placing the larger element at the back of nums1
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        // If there are any remaining elements in nums2, copy them over
        // (If elements remain in nums1, they are already in their correct sorted positions)
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }
}
