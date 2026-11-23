package DSA.TwoPointers.TwoPointers;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumII {

    // =====================================================
    // APPROACH 1: BRUTE FORCE
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int[] twoSumBruteForce(int[] numbers, int target) {

        // Pick the first number.
        for (int i = 0; i < numbers.length; i++) {

            // Pick another number after i.
            // Starting at i + 1 avoids using the same element twice.
            for (int j = i + 1; j < numbers.length; j++) {

                // Check whether these two numbers equal the target.
                if (numbers[i] + numbers[j] == target) {
                    return new int[] { i + 1, j + 1 }; // Convert Java's 0-based indices to 1-based indices.
                }
            }
        }

        return new int[] { -1, -1 }; // Defensive fallback: problem guarantees one solution.
    }


    // =====================================================
    // APPROACH 2: HASHMAP
    // Time Complexity: O(n) average
    // Auxiliary Space: O(n)
    // =====================================================

    public static int[] twoSumHashMap(int[] numbers, int target) {

        // Store number -> its 0-based index.
        Map<Integer, Integer> seen = new HashMap<>();

        // Visit each number once.
        for (int i = 0; i < numbers.length; i++) {

            int needed = target - numbers[i]; // Calculate the number needed to complete the target.

            // Check whether we have already seen the needed number.
            if (seen.containsKey(needed)) {
                int previousIndex = seen.get(needed); // Retrieve the index of the previously seen number.
                return new int[] { previousIndex + 1, i + 1 }; // Return both indices using 1-based indexing.
            }

            seen.put(numbers[i], i); // Store the current number and its index.
        }

        return new int[] { -1, -1 }; // Defensive fallback: problem guarantees one solution.
    }

    // =====================================================
    // APPROACH 3: TWO POINTERS (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int[] twoSumTwoPointers(int[] numbers, int target) {

        int left = 0; // Start from the smallest element.
        int right = numbers.length - 1; // Start from the largest element.

        // Continue while two different positions are available.
        while (left < right) {

            int sum = numbers[left] + numbers[right]; // Calculate the sum of the two selected numbers.

            // If the sum matches, we found the answer.
            if (sum == target) {
                return new int[] { left + 1, right + 1 }; // Convert both indices to 1-based indexing.
            }

            // If sum is too small, we need a larger value. The array is sorted, so move left forward.
            else if (sum < target) {
                left++;
            }

            // If sum is too large, we need a smaller value. The array is sorted, so move right backward.
            else {
                right--;
            }
        }

        return new int[] { -1, -1 };
    }


    // =====================================================
    // MAIN METHOD
    // Run all three approaches using the same input.
    // =====================================================

    public static void main(String[] args) {

        int[] numbers = { 2, 7, 11, 15 };
        int target = 9;

        System.out.println("Input: " + Arrays.toString(numbers));
        System.out.println("Target: " + target);

        // Run brute-force approach.
        int[] bruteForceAnswer = twoSumBruteForce(numbers, target);
        System.out.println("Brute Force: " + Arrays.toString(bruteForceAnswer));

        // Run HashMap approach.
        int[] hashMapAnswer = twoSumHashMap(numbers, target);
        System.out.println("HashMap: " + Arrays.toString(hashMapAnswer));

        // Run optimal two-pointer approach.
        int[] twoPointerAnswer = twoSumTwoPointers(numbers, target);
        System.out.println("Two Pointers: " + Arrays.toString(twoPointerAnswer));
    }
}

