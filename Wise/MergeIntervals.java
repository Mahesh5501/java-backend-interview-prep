
package Wise;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/*
Algorithm: Merge Overlapping Intervals

1. Sort all intervals by their starting values.
2. Create an empty list called merged to store the answer.
3. Process each interval one by one:
   - If merged is empty, add the current interval.
   - Otherwise, get the last interval in merged.
   - If current start <= last end, the intervals overlap.
     Update last end to the maximum of both ending values.
   - If they do not overlap, add the current interval separately.
4. Print all intervals in merged.

Example:
Input:  [[1,3], [2,6], [8,10], [9,12]]
Output: [[1,6], [8,12]]

Why it works:
Sorting puts intervals in start-time order.
Comparing current start with last end tells us whether the intervals overlap.

Time Complexity: O(n log n) because of sorting.
Space Complexity: O(n) for the merged result.
*/

public class MergeIntervals {

    public static void main(String[] args) {

        int[][] intervals = {
                {1, 3},
                {2, 6},
                {8, 10},
                {9, 12}
        };

        // Step 1: Sort intervals by their start value.
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> merged = new ArrayList<>();

        // Step 2: Process each interval.
        for (int[] current : intervals) {

            // Add the first interval directly.
            if (merged.isEmpty()) {
                merged.add(current);
                continue;
            }

            // Get the last interval we added.
            int[] last = merged.get(merged.size() - 1);

            // If they overlap, extend the last interval.
            if (current[0] <= last[1]) {
                last[1] = Math.max(last[1], current[1]);
            } else {
                // No overlap: add a separate interval.
                merged.add(current);
            }
        }

        // Step 3: Print the result.
        for (int[] interval : merged) {
            System.out.println("[" + interval[0] + ", " + interval[1] + "]");
        }
    }
}
