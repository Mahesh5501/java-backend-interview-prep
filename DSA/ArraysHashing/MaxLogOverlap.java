package DSA.ArraysHashing;

import java.io.*;
import java.util.*;

/*
Problem: Maximum Log Overlap
Each process runs from startTime to endTime (both inclusive).
Find the highest number of processes running at the same minute.

Example 1: [[1, 3], [2, 4], [3, 6]] -> 3
At minute 3, all three intervals [1, 3], [2, 4], and [3, 6] are active together.

Example 2: [[2, 6], [1, 2], [3, 5]] -> 2
At minute 2, [1, 2] and [2, 6] overlap (2 processes).
*/

public class MaxLogOverlap {

    public static int getMaxLogOverlap(List<List<Integer>> interval) {
        // Store time changes in sorted order.
        // Key = minute, Value = number of processes starting/stopping.
        TreeMap<Integer, Integer> events = new TreeMap<>();

        // STEP 1: Record when each process starts and stops.
        for (List<Integer> log : interval) {

            int start = log.get(0);
            int end = log.get(1);

            // Process starts → increase active count by 1.
            events.put(start, events.getOrDefault(start, 0) + 1);

            // Process stops at end + 1 because end time is inclusive.
            events.put(end + 1, events.getOrDefault(end + 1, 0) - 1);
        }

        // STEP 2: Walk through events in time order.
        int active = 0;
        int maxOverlap = 0;

        for (int change : events.values()) {

            // Update how many processes are currently running.
            active += change;

            // Remember the highest number of running processes.
            maxOverlap = Math.max(maxOverlap, active);
        }

        return maxOverlap;
    }

    public static void main(String[] args) throws IOException {
        // Sample 1: at minute 3, all 3 intervals overlap -> answer is 3
        List<List<Integer>> interval1 = Arrays.asList(
                Arrays.asList(1, 3),
                Arrays.asList(2, 4),
                Arrays.asList(3, 6));
        System.out.println("Test Case 1 Result (Expected: 3): " + getMaxLogOverlap(interval1));

        // Sample 2: max overlap is 2 (e.g., at minute 2 or minutes 3..5) -> answer is 2
        List<List<Integer>> interval2 = Arrays.asList(
                Arrays.asList(2, 6),
                Arrays.asList(1, 2),
                Arrays.asList(3, 5));
        System.out.println("Test Case 2 Result (Expected: 2): " + getMaxLogOverlap(interval2));
    }
}