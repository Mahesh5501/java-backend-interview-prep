package DSA.Heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
Problem: Top K Frequent Elements (LeetCode 347)

Return the k most frequent integers (any order).

Example:
    nums = [1, 1, 1, 2, 2, 3], k = 2 -> [1, 2] (order may vary)

See notes/Problem_02_Top_K_Frequent_Elements.md — every approach below matches the note.
*/

public class TopKFrequent {

    static class FrequencyPair {
        int value;
        int count;

        FrequencyPair(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }

    // APPROACH 1: FREQUENCY MAP + SORT ENTRIES
    // Count each value, sort (value, count) pairs by count descending, take first k keys.
    // Why use it: clear baseline before introducing a heap.
    // Time: O(n + u log u)   Extra space: O(u) unique keys u
    public static List<Integer> topKFrequentSort(int[] nums, int k) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(frequency.entrySet());
        entries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k && i < entries.size(); i++) {
            result.add(entries.get(i).getKey());
        }
        return result;
    }

    // APPROACH 2: FREQUENCY MAP + MIN-HEAP OF SIZE K (OPTIMAL — what we use)
    // Keep k highest counts; heap ordered by count so root is the weakest survivor.
    // Why heap: O(log k) per unique key vs sorting all uniques.
    // Time: O(n + u log k)   Extra space: O(u + k)
    public static List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<FrequencyPair> minHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.count, b.count));

        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            minHeap.offer(new FrequencyPair(entry.getKey(), entry.getValue()));
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().value);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 1, 1, 2, 2, 3 };
        System.out.println("Sort: " + topKFrequentSort(nums, 2));
        System.out.println("Heap: " + topKFrequent(nums, 2));

        int[] nums2 = { 4, 4, 4, 6, 6, 6, 6, 7 };
        System.out.println("Sort: " + topKFrequentSort(nums2, 2));
        System.out.println("Heap: " + topKFrequent(nums2, 2));
    }
}