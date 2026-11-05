package Wise;

import java.io.*;
import java.util.*;
import java.util.stream.*;


/*
 * Complete the 'getMaxLogOverlap' function below.
 *
 * Problem Statement:
 * You are given n process logs[cite: 6]. Each log is an interval:
 * [startTime, endTime] (both inclusive), meaning the process is running
 * for every minute from startTime through endTime[cite: 6].
 *
 * Determine the maximum number of processes running at the same time at any minute[cite: 6].
 *
 * Example 1:
 * Input: n = 3, interval = [[1, 3], [2, 4], [3, 6]][cite: 6]
 * Output: 3[cite: 6]
 *
 * Example 2:
 * Input: n = 3, interval = [[2, 6], [1, 2], [3, 5]][cite: 5]
 * Output: 2[cite: 5]
 *
 * Constraints:
 * - 1 <= n <= 2 * 10^5[cite: 5]
 * - 1 <= interval[i][0] < interval[i][1] <= 2 * 10^5[cite: 5]
 *
 * The function is expected to return an INTEGER.
 * The function accepts 2D_INTEGER_ARRAY interval as parameter.
 */


public class MaxLogOverlap {

    public static int getMaxLogOverlap(List<List<Integer>> interval) {
        // TreeMap to store timeline events sorted by time
        TreeMap<Integer, Integer> events = new TreeMap<>();

        for (List<Integer> log : interval) {
            int start = log.get(0);
            int end = log.get(1);

            // When a process starts, active count increases by +1
            events.put(start, events.getOrDefault(start, 0) + 1);

            // When a process ends, active count decreases by -1 at the NEXT minute (end + 1)
            if (end < Integer.MAX_VALUE) {
                events.put(end + 1, events.getOrDefault(end + 1, 0) - 1);
            }
        }

        int active = 0;
        int maxOverlap = 0;

        // Traverse through the sorted timeline events
        for (int change : events.values()) {
            active += change;
            maxOverlap = Math.max(maxOverlap, active);
        }

        return maxOverlap;
    }

    public static void main(String[] args) throws IOException {
        // Test Sample 1
        List<List<Integer>> interval1 = Arrays.asList(
                Arrays.asList(1, 3),
                Arrays.asList(2, 4),
                Arrays.asList(3, 6)
        );

        System.out.println("Test Case 1 Result (Expected: 3): " + getMaxLogOverlap(interval1));

        // Test Sample 2
        List<List<Integer>> interval2 = Arrays.asList(
                Arrays.asList(2, 6),
                Arrays.asList(1, 2),
                Arrays.asList(3, 5)
        );

        System.out.println("Test Case 2 Result (Expected: 2): " + getMaxLogOverlap(interval2));

    }


}
