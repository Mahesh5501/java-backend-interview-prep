package DSA.PriorityQueueHeap;

import java.util.*;

public class TopKFrequent {

    /*
    Problem: Top K Frequent Elements

    Given an integer array nums and an integer k, return the k most frequent elements.

    Example 1:
    nums = [1, 1, 1, 2, 2, 3]
    k = 2

    Output: [1, 2]

    Example 2:
    nums = [1]
    k = 1

    Output: [1]

    Expected Approach:
    1. Use a HashMap to count the frequency of each number.
    2. Use a Heap to keep the Top K frequent elements.
    */

    static int[] topKFrequent(int[] nums, int k) {
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] result = new int[k];
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            pq.add(entry);

            if (pq.size() > k) {
                pq.poll();
            }
        }
        for (int i = 0; i < k; i++) {
            result[i] = pq.poll().getKey();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2))); // [1, 2]
        System.out.println(Arrays.toString(topKFrequent(new int[]{1}, 1))); // [1]
    }
}
