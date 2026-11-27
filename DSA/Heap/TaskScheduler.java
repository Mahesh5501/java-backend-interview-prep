package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: Task Scheduler (LeetCode 621)

You have tasks labeled A..Z and a cooldown n between same labels.
Return the minimum time units to finish all tasks.

Example:
    tasks = ["A","A","A","B","B","B"], n = 2 -> 8
    Layout like: A B _ A B _ A B

See notes/Problem_06_Task_Scheduler.md — every approach below matches the note.
*/

public class TaskScheduler {

    // APPROACH 1: SIMULATE WITH ARRAY SORT (BRUTE)
    // Each cooldown window: pick top counts by sorting freq array — same schedule idea, slower.
    // Why use it: shows greedy batch scheduling before a heap.
    // Time: O(n · 26 log 26) roughly   Extra space: O(26)
    public static int leastIntervalBrute(char[] tasks, int n) {
        int[] frequency = new int[26];
        for (char task : tasks) {
            frequency[task - 'A']++;
        }
        int time = 0;
        while (hasWork(frequency)) {
            Arrays.sort(frequency);
            int batch = n + 1;
            for (int slot = 25; slot >= 0 && batch > 0; slot--) {
                if (frequency[slot] == 0) {
                    continue;
                }
                frequency[slot]--;
                batch--;
            }
            if (hasWork(frequency)) {
                time += n + 1;
            } else {
                time += (n + 1) - batch;
            }
        }
        return time;
    }

    private static boolean hasWork(int[] freq) {
        for (int c : freq) {
            if (c > 0) {
                return true;
            }
        }
        return false;
    }

    // APPROACH 2: MAX-HEAP GREEDY (OPTIMAL — what we use in interviews)
    // Each batch takes up to n+1 tasks from largest remaining counts.
    // Why heap: faster batch extraction than resorting every round.
    // Time: O(n) heap ops   Extra space: O(26)
    public static int leastInterval(char[] tasks, int n) {
        int[] frequency = new int[26];
        for (char task : tasks) {
            frequency[task - 'A']++;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        for (int count : frequency) {
            if (count > 0) {
                maxHeap.offer(count);
            }
        }

        int time = 0;

        while (!maxHeap.isEmpty()) {
            int batchSize = n + 1;
            int[] processed = new int[batchSize];
            int used = 0;

            while (!maxHeap.isEmpty() && used < batchSize) {
                int remaining = maxHeap.poll() - 1;
                if (remaining > 0) {
                    processed[used++] = remaining;
                }
            }

            for (int i = 0; i < used; i++) {
                maxHeap.offer(processed[i]);
            }

            if (maxHeap.isEmpty()) {
                time += used;
            } else {
                time += batchSize;
            }
        }

        return time;
    }

    // APPROACH 3: CLOSED FORMULA
    // max(tasks.length, (maxFreq-1)*(n+1) + countOfMaxFreq) matches greedy layout.
    // Why use it: O(26) after counting — quick check against heap simulation.
    // Time: O(n)   Extra space: O(1)
    public static int leastIntervalFormula(char[] tasks, int n) {
        int[] frequency = new int[26];
        for (char task : tasks) {
            frequency[task - 'A']++;
        }

        Arrays.sort(frequency);

        int maxFreq = frequency[25];
        int maxCount = 0;
        for (int count : frequency) {
            if (count == maxFreq) {
                maxCount++;
            }
        }

        int part1 = (maxFreq - 1) * (n + 1) + maxCount;
        return Math.max(tasks.length, part1);
    }

    public static void main(String[] args) {
        char[] tasks = { 'A', 'A', 'A', 'B', 'B', 'B' };
        System.out.println("n=2 (brute):   " + leastIntervalBrute(tasks, 2));
        System.out.println("n=2 (heap):    " + leastInterval(tasks, 2)); // 8
        System.out.println("n=2 (formula): " + leastIntervalFormula(tasks, 2)); // 8

        char[] tasks2 = { 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'D' };
        System.out.println("n=3 (formula): " + leastIntervalFormula(tasks2, 3)); // 11
    }
}