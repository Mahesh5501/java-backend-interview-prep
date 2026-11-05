package Wise;

import java.util.*;

class Result {

    /*
     * Complete the 'solvePeaksProblem' function below.
     *
     * Problem Statement:
     * Given an integer array arr of length n, identify all peak indices, where an index i is considered a peak if:
     * - 1 <= i <= n - 2[cite: 11]
     * - arr[i] > arr[i - 1][cite: 11]
     * - arr[i] > arr[i + 1][cite: 11]
     *
     * Your task is to find the largest distance between any two consecutive peak indices[cite: 11].
     * - The distance between two peak indices i and j is j - i[cite: 11].
     * - Only adjacent peak indices in sorted order should be considered[cite: 11].
     * - If the array contains fewer than two peak indices, return 0[cite: 11].
     */

    public static int solvePeaksProblem(List<Integer> arr) {
        int n = arr.size();

        if (n < 3) {
            return 0;
        }

        int previousPeak = -1;
        int maxDistance = 0;

        for (int i = 1; i < n - 1; i++) {
            // Check if current element is a peak
            if (arr.get(i) > arr.get(i - 1) && arr.get(i) > arr.get(i + 1)) {
                // If we found a previous peak, calculate the distance
                if (previousPeak != -1) {
                    maxDistance = Math.max(maxDistance, i - previousPeak);
                }
                // Update previousPeak to current index
                previousPeak = i;
            }
        }
        return maxDistance;
    }
}

public class MaxPeakDistance {
    public static void main(String[] args) {
        // Example 1 Test Case
        List<Integer> arr1 = Arrays.asList(1, 5, 2, 3, 1);
        int result1 = Result.solvePeaksProblem(arr1);
        System.out.println("Example 1 Result (Expected: 2): " + result1);

        // Example 2 Test Case
        List<Integer> arr2 = Arrays.asList(1, 2, 1, 3, 2, 1, 4, 1);
        int result2 = Result.solvePeaksProblem(arr2);
        System.out.println("Example 2 Result (Expected: 3): " + result2);
    }
}
