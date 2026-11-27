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
*/

public class TaskScheduler {

    // Max-heap greedy: always schedule the most frequent remaining task when idle allows.
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

    // Formula shortcut: result = max(tasks.length, (maxFreq-1)*(n+1) + maxCount)
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
        System.out.println("n=2 (heap):    " + leastInterval(tasks, 2)); // 8
        System.out.println("n=2 (formula): " + leastIntervalFormula(tasks, 2)); // 8

        char[] tasks2 = { 'A', 'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'D' };
        System.out.println("n=3 (formula): " + leastIntervalFormula(tasks2, 3)); // 11
    }
}