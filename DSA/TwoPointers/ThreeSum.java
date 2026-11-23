package DSA.TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThreeSum {

    // =====================================================
    // APPROACH 1: BRUTE FORCE
    // Time Complexity: O(n^3)
    // Auxiliary Space: O(t) for the set of unique triplets
    // =====================================================

    public static List<List<Integer>> threeSumBruteForce(int[] nums) {

        // A set stores unique sorted triplets.
        Set<List<Integer>> uniqueTriplets = new HashSet<>();

        // Choose the first value.
        for (int i = 0; i < nums.length; i++) {

            // Choose the second value after i.
            for (int j = i + 1; j < nums.length; j++) {

                // Choose the third value after j.
                for (int k = j + 1; k < nums.length; k++) {

                    // Check whether these three values sum to zero.
                    if (nums[i] + nums[j] + nums[k] == 0) {

                        // Build one candidate triplet.
                        List<Integer> triplet = Arrays.asList(
                                nums[i], nums[j], nums[k]
                        );

                        // Sort so that order differences do not create duplicates.
                        Collections.sort(triplet);

                        // The set discards duplicate triplets automatically.
                        uniqueTriplets.add(triplet);
                    }
                }
            }
        }

        // Convert the set into a list for the required return type.
        return new ArrayList<>(uniqueTriplets);
    }


    // =====================================================
    // APPROACH 2: SORT + HASHSET
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(n) for the HashSet + output
    // =====================================================

    public static List<List<Integer>> threeSumHashSet(int[] nums) {

        // Sorting groups duplicates and makes skip logic easy.
        Arrays.sort(nums);

        // A set of sorted triplets avoids duplicate answers cleanly.
        Set<List<Integer>> uniqueTriplets = new HashSet<>();

        // Fix the first number of the triplet.
        for (int i = 0; i < nums.length; i++) {

            // Skip the same fixed value we already processed.
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // We need two numbers that sum to -nums[i].
            int needed = -nums[i];

            // Store values seen in the remaining subarray.
            Set<Integer> seen = new HashSet<>();

            // Scan the remaining numbers after i.
            for (int j = i + 1; j < nums.length; j++) {

                int complement = needed - nums[j];

                // If the complement was already seen, we found a pair.
                if (seen.contains(complement)) {
                    List<Integer> triplet = Arrays.asList(
                            nums[i], complement, nums[j]
                    );
                    Collections.sort(triplet);
                    uniqueTriplets.add(triplet);
                }

                // Remember the current value for later complements.
                seen.add(nums[j]);
            }
        }

        return new ArrayList<>(uniqueTriplets);
    }


    // =====================================================
    // APPROACH 3: SORT + TWO POINTERS (OPTIMAL)
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1) extra besides the output
    // =====================================================

    public static List<List<Integer>> threeSumTwoPointers(int[] nums) {

        // Sorting enables two-pointer search and duplicate skipping.
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        // Fix the first number of every candidate triplet.
        for (int i = 0; i < nums.length; i++) {

            // After sorting, a positive first number cannot make sum 0.
            if (nums[i] > 0) {
                break;
            }

            // Skip duplicate first numbers.
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Search for two numbers that sum to -nums[i].
            int left = i + 1;
            int right = nums.length - 1;
            int needed = -nums[i];

            while (left < right) {

                int sum = nums[left] + nums[right];

                if (sum == needed) {

                    // Record one unique triplet.
                    result.add(Arrays.asList(
                            nums[i], nums[left], nums[right]
                    ));

                    // Move both pointers inward after a successful match.
                    left++;
                    right--;

                    // Skip duplicate left values.
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }

                    // Skip duplicate right values.
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }

                } else if (sum < needed) {

                    // Current pair is too small, so increase it.
                    left++;

                } else {

                    // Current pair is too large, so decrease it.
                    right--;
                }
            }
        }

        return result;
    }


    // =====================================================
    // MAIN METHOD
    // Run all approaches using the same input.
    // =====================================================

    public static void main(String[] args) {

        int[] nums = { -1, 0, 1, 2, -1, -4 };

        System.out.println("Input: " + Arrays.toString(nums));

        // Clone so sorting in later approaches does not affect earlier ones.
        System.out.println(
                "Brute Force:  " + threeSumBruteForce(nums.clone())
        );
        System.out.println(
                "HashSet:      " + threeSumHashSet(nums.clone())
        );
        System.out.println(
                "Two Pointers: " + threeSumTwoPointers(nums.clone())
        );
    }
}
