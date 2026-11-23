package DSA.TwoPointers;

import java.util.Arrays;

public class TrappingRainWater {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (per index)
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int trapBruteForce(int[] height) {

        int water = 0;

        // For every position (except first and last), ask how much water sits there.
        for (int i = 1; i < height.length - 1; i++) {

            // Find the tallest bar on the left of i.
            int leftMax = 0;
            for (int left = 0; left < i; left++) {
                leftMax = Math.max(leftMax, height[left]);
            }

            // Find the tallest bar on the right of i.
            int rightMax = 0;
            for (int right = i + 1; right < height.length; right++) {
                rightMax = Math.max(rightMax, height[right]);
            }

            // Water height is limited by the shorter of the two walls.
            int trappedHere = Math.min(leftMax, rightMax) - height[i];

            // Count only if the bar is below the water level.
            if (trappedHere > 0) {
                water += trappedHere;
            }
        }

        return water;
    }


    // =====================================================
    // APPROACH 2: PREFIX MAX ARRAYS
    // Time Complexity: O(n)
    // Auxiliary Space: O(n)
    // =====================================================

    public static int trapPrefixMax(int[] height) {

        int n = height.length;
        if (n <= 2) {
            return 0;
        }

        // leftMax[i] = tallest bar from index 0 up to index i.
        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        // rightMax[i] = tallest bar from index n-1 down to index i.
        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int water = 0;

        // Every index can trap water based only on precomputed left and right maxima.
        for (int i = 0; i < n; i++) {
            int trappedHere = Math.min(leftMax[i], rightMax[i]) - height[i];
            if (trappedHere > 0) {
                water += trappedHere;
            }
        }

        return water;
    }


    // =====================================================
    // APPROACH 3: TWO POINTERS (OPTIMAL SPACE)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int trapTwoPointers(int[] height) {

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;

        // Move the side whose current maximum is the smaller one.
        while (left < right) {

            if (height[left] < height[right]) {

                // The left side is the limiting wall right now.
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;

            } else {

                // The right side is the limiting wall (or equal height — still safe to process right).
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }

        return water;
    }


    // =====================================================
    // MAIN METHOD
    // Run all approaches using the same input.
    // =====================================================

    public static void main(String[] args) {

        int[] height = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };

        System.out.println("Heights: " + Arrays.toString(height));
        System.out.println("Brute Force:   " + trapBruteForce(height.clone()));
        System.out.println("Prefix Max:    " + trapPrefixMax(height.clone()));
        System.out.println("Two Pointers:  " + trapTwoPointers(height.clone()));
    }
}