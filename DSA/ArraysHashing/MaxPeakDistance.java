
package DSA.ArraysHashing;

import java.util.*;

public class MaxPeakDistance {

    /*
     * PROBLEM: Maximum Distance Between Consecutive Peaks
     *
     * Given an integer array, find all peak indices.
     *
     * An index i is a PEAK if:
     * 1. It is not the first or last index.
     * 2. arr[i] > arr[i - 1] (greater than left neighbour).
     * 3. arr[i] > arr[i + 1] (greater than right neighbour).
     *
     * TASK: Find the largest distance between two consecutive peaks.
     *
     * Distance = Current Peak Index - Previous Peak Index
     *
     * IMPORTANT:
     * - Compare only consecutive peaks.
     * - If fewer than 2 peaks exist, return 0.
     *
     * EXAMPLE 1:
     * Input:  [1, 5, 2, 3, 1]
     * Peaks:  Index 1 (5) and Index 3 (3)
     * Distance: 3 - 1 = 2
     * Output: 2
     *
     * EXAMPLE 2:
     * Input:  [1, 2, 1, 3, 2, 1, 4, 1]
     * Peaks:  Index 1, 3 and 6
     * Distances: 3 - 1 = 2, 6 - 3 = 3
     * Output: 3
     *
     *
     * APPROACH: Single Pass (One Loop)
     *
     * IDEA: Walk through the array and identify peaks.
     *
     * We maintain two variables:
     * 1. previousPeak = Index of the last peak found.
     * 2. maxDistance = Largest distance found so far.
     *
     * STEPS:
     * 1. Start from index 1 (first element cannot be a peak).
     * 2. Check if current element is greater than both neighbours.
     * 3. If it is a peak:
     *    - If it is the first peak, just save its index.
     *    - Otherwise, calculate distance from the previous peak.
     * 4. Update the maximum distance.
     * 5. Save the current peak index for the next comparison.
     * 6. Return the maximum distance.
     *
     * COMPLEXITY:
     * Time:  O(n) - We visit each element once.
     * Space: O(1) - We use only two variables.
     */

    public static int solvePeaksProblem(List<Integer> arr) {

        int n = arr.size();

        // At least 3 elements are needed to have a peak.
        if (n < 3) {
            return 0;
        }

        int previousPeak = -1;  // Stores the last peak index.
        int maxDistance = 0;    // Stores the biggest distance found.

        // First and last elements cannot be peaks.
        for (int i = 1; i < n - 1; i++) {
            if (arr.get(i) > arr.get(i - 1) && arr.get(i) > arr.get(i + 1)) {           
                if (previousPeak != -1) { // If we already found a peak, calculate the distance.
                    int distance = i - previousPeak;
                    maxDistance = Math.max(maxDistance, distance);
                }
                previousPeak = i; // Save current peak to compare with the next peak.
            }
        }

        return maxDistance;
    }

    public static void main(String[] args) {

        // Example 1: Peaks at indices 1 and 3. Maximum distance = 2.
        List<Integer> arr1 = Arrays.asList(1, 5, 2, 3, 1);
        System.out.println("Example 1 (Expected: 2): " + solvePeaksProblem(arr1));

        // Example 2: Peaks at indices 1, 3 and 6. Maximum distance = 3.
        List<Integer> arr2 = Arrays.asList(1, 2, 1, 3, 2, 1, 4, 1);
        System.out.println("Example 2 (Expected: 3): " + solvePeaksProblem(arr2));

        // Example 3: Only one peak, so return 0.
        List<Integer> arr3 = Arrays.asList(1, 5, 2);
        System.out.println("Example 3 (Expected: 0): " + solvePeaksProblem(arr3));

        // Example 4: No peaks in the array.
        List<Integer> arr4 = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Example 4 (Expected: 0): " + solvePeaksProblem(arr4));
    }
}
