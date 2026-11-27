package DSA.Heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
Problem: Top K Frequent Elements (LeetCode 347)

Return the k most frequent integers (any order).

Example:
    nums = [1, 1, 1, 2, 2, 3], k = 2 -> [1, 2] (order may vary)
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
        System.out.println("Top 2 frequent: " + topKFrequent(nums, 2));

        int[] nums2 = { 4, 4, 4, 6, 6, 6, 6, 7 };
        System.out.println("Top 2 frequent: " + topKFrequent(nums2, 2));
    }
}