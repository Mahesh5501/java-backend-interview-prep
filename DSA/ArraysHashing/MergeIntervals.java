
package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/*
 * PROBLEM: Merge Overlapping Intervals
 *
 * Given a list of intervals, merge all intervals that overlap.
 *
 * An interval contains:
 * - Start value
 * - End value
 *
 * TASK: Combine overlapping intervals and return the merged intervals.
 *
 * EXAMPLE 1:
 * Input:  [[1,3], [2,6], [8,10], [9,12]]
 * Output: [[1,6], [8,12]]
 *
 * Explanation:
 * - [1,3] and [2,6] overlap, so merge them into [1,6].
 * - [8,10] and [9,12] overlap, so merge them into [8,12].
 *
 * EXAMPLE 2:
 * Input:  [[1,4], [5,7], [8,10]]
 * Output: [[1,4], [5,7], [8,10]]
 *
 * Explanation: No intervals overlap, so all remain separate.
 *
 *
 * APPROACH: SORT + MERGE
 *
 * IDEA: First sort intervals by their start values.Then compare each interval with the last merged interval.
 *
 * STEPS:
 * 1. Sort intervals by their starting values.
 * 2. Create a list to store merged intervals.
 * 3. Take each interval one by one:
 *    - If the merged list is empty, add the interval. Otherwise, compare it with the last merged interval.
 *    - If current start <= last end, they overlap.
 *      Extend the last interval's end if needed. Otherwise, add the current interval separately.
 * 4. Return the merged intervals.
 *
 * WHY IT WORKS:
 * Sorting ensures intervals are processed from left to right.
 * So we only need to compare the current interval with the last interval in our merged list.
 *
 * IMPORTANT: current start <= last end means they overlap or touch.
 *
 * COMPLEXITY:
 * Time:  O(n log n) - Sorting takes O(n log n).
 * Space: O(n) - Merged list can store up to n intervals.
 */

public class MergeIntervals {

    public static List<int[]> merge(int[][] intervals) {

        // STEP 1: Sort intervals by their starting values.
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>(); // Stores the final merged intervals.

        for (int[] current : intervals) {

            // If this is the first interval, add it directly.
            if (merged.isEmpty()) {
                merged.add(current);
                continue;
            }
            int[] last = merged.get(merged.size() - 1); // Get the last interval added to our result.

            // Check if current interval overlaps with the last one.
            if (current[0] <= last[1]) {
                last[1] = Math.max(last[1], current[1]); // Merge them by keeping the larger ending value.
            } else {
                merged.add(current);  // No overlap, so add it as a separate interval.
            }
        }
        return merged;
    }

    public static void main(String[] args) {

        // Example 1: Some intervals overlap.
        int[][] intervals1 = {
                {1, 3},
                {2, 6},
                {8, 10},
                {9, 12}
        };

        List<int[]> result1 = merge(intervals1);

        System.out.println("Example 1 (Expected: [[1, 6], [8, 12]]):");
        for (int[] interval : result1) {
            System.out.println(Arrays.toString(interval));
        }

        // Example 2: No intervals overlap.
        int[][] intervals2 = {
                {1, 4},
                {5, 7},
                {8, 10}
        };

        List<int[]> result2 = merge(intervals2);

        System.out.println("\nExample 2 (Expected: [[1, 4], [5, 7], [8, 10]]):");
        for (int[] interval : result2) {
            System.out.println(Arrays.toString(interval));
        }

        // Example 3: All intervals overlap.
        int[][] intervals3 = {
                {1, 5},
                {2, 4},
                {3, 8}
        };

        List<int[]> result3 = merge(intervals3);

        System.out.println("\nExample 3 (Expected: [[1, 8]]):");
        for (int[] interval : result3) {
            System.out.println(Arrays.toString(interval));
        }
    }
}
