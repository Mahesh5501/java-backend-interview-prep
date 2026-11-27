package DSA.TwoPointers;

/*
Problem: Container With Most Water

See notes/Problem_03_Container_With_Most_Water.md — every approach below matches the note.
*/

import java.util.Arrays;

public class ContainerWithMostWater {

    // =====================================================
    // APPROACH 1: BRUTE FORCE
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int maxAreaBruteForce(int[] height) {

        // Stores the largest area found so far.
        int maxArea = 0;

        // Choose the first wall.
        for (int i = 0; i < height.length; i++) {

            // Choose a different wall after i.
            // Starting at i + 1 avoids using the same wall twice and avoids checking the same pair in reverse order.
            for (int j = i + 1; j < height.length; j++) {
                int width = j - i; // The distance between the indices is the width.
                int waterHeight = Math.min(height[i], height[j]); // The shorter wall limits the water height.
                int area = width * waterHeight; // Calculate the area formed by this pair.
                maxArea = Math.max(maxArea, area);  // Keep the largest area found so far.
            }
        }

        return maxArea;
    }


    // =====================================================
    // APPROACH 2: TWO POINTERS (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int maxAreaTwoPointers(int[] height) {

        int left = 0; // Start at the first wall.
        int right = height.length - 1; // Start at the last wall.
        int maxArea = 0; // Stores the largest area found so far.

        // Continue while two different walls are available.
        while (left < right) {
            int width = right - left; // Calculate the distance between the selected walls.
            int waterHeight = Math.min(height[left], height[right]); // The shorter wall determines the water height.
            int currentArea = width * waterHeight; // Calculate the current container's area.
            maxArea = Math.max(maxArea, currentArea); // Keep track of the maximum area found so far.

            // Move only the shorter wall.
            if (height[left] < height[right]) {
                left++; // Left wall is shorter, so try a new left wall.
            } else {
                right--; // Right wall is shorter OR both are equal. Moving right is safe in either case.
            }
        }

        return maxArea;
    }


    // =====================================================
    // MAIN METHOD
    // Run both approaches using the same input.
    // =====================================================

    public static void main(String[] args) {

        int[] height = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        System.out.println("Heights: " + Arrays.toString(height));

        // Run the brute-force approach.
        int bruteForceAnswer = maxAreaBruteForce(height);
        System.out.println("Brute Force Maximum Area: " + bruteForceAnswer);

        // Run the optimal two-pointer approach.
        int twoPointerAnswer = maxAreaTwoPointers(height);
        System.out.println("Two Pointers Maximum Area: " + twoPointerAnswer);
    }
}
