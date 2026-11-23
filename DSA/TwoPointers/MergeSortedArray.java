package DSA.TwoPointers;

import java.util.Arrays;

public class MergeSortedArray {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (merge into helper, copy back)
    // Time Complexity: O(m + n)
    // Auxiliary Space: O(m + n)
    // =====================================================

    public static void mergeBruteForce(int[] nums1, int m, int[] nums2, int n) {

        int[] merged = new int[m + n];
        int i = 0;
        int j = 0;
        int k = 0;

        // Standard merge of two sorted arrays into a fresh array.
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }

        while (i < m) {
            merged[k++] = nums1[i++];
        }

        while (j < n) {
            merged[k++] = nums2[j++];
        }

        // Copy the merged result back into nums1.
        for (int index = 0; index < m + n; index++) {
            nums1[index] = merged[index];
        }
    }


    // =====================================================
    // APPROACH 2: TWO POINTERS FROM THE END (OPTIMAL)
    // Time Complexity: O(m + n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static void mergeTwoPointersFromEnd(int[] nums1, int m, int[] nums2, int n) {

        // Last real element of the original nums1 part.
        int i = m - 1;

        // Last element of nums2.
        int j = n - 1;

        // Last slot of nums1 (includes the trailing empty buffer).
        int write = m + n - 1;

        // Fill from the back so we never overwrite unread nums1 values.
        while (i >= 0 && j >= 0) {

            if (nums1[i] > nums2[j]) {
                nums1[write] = nums1[i];
                i--;
            } else {
                nums1[write] = nums2[j];
                j--;
            }

            write--;
        }

        // If nums2 still has values, copy them (nums1 leftovers are already in place).
        while (j >= 0) {
            nums1[write] = nums2[j];
            j--;
            write--;
        }
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        int[] nums1 = { 1, 2, 3, 0, 0, 0 };
        int m = 3;
        int[] nums2 = { 2, 5, 6 };
        int n = 3;

        System.out.println("nums1 (before): " + Arrays.toString(nums1));
        System.out.println("nums2:          " + Arrays.toString(nums2));

        int[] nums1Brute = nums1.clone();
        mergeBruteForce(nums1Brute, m, nums2, n);
        System.out.println("Brute Force:      " + Arrays.toString(nums1Brute));

        int[] nums1Optimal = nums1.clone();
        mergeTwoPointersFromEnd(nums1Optimal, m, nums2, n);
        System.out.println("Two Pointers:     " + Arrays.toString(nums1Optimal));
    }
}