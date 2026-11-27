package DSA.ArraysHashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
Problem: Top K Frequent Elements

Return the k most frequent integers (any order).

Example:
  nums = [1,1,1,2,2,3], k = 2  →  [1,2]

See notes/Problem_05_Top_K_Frequent_Elements.md for approach explanations.
*/

public class TopKFrequentElements {

    // APPROACH 1: FREQUENCY MAP + BUCKETS (OPTIMAL — often preferred)
    // Why buckets: frequency is at most n, so we can "sort" by freq in O(n).
    // bucket[f] = values that appear exactly f times; read from high f downward.
    // Time: O(n)   Extra space: O(n)
    public static int[] topKFrequentBucket(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            freq.put(x, freq.getOrDefault(x, 0) + 1);
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] bucket = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            int f = e.getValue();
            if (bucket[f] == null) {
                bucket[f] = new ArrayList<>();
            }
            bucket[f].add(e.getKey());
        }

        int[] ans = new int[k];
        int idx = 0;
        for (int f = bucket.length - 1; f >= 0 && idx < k; f--) {
            if (bucket[f] == null) {
                continue;
            }
            for (int val : bucket[f]) {
                ans[idx++] = val;
                if (idx == k) {
                    return ans;
                }
            }
        }
        return ans;
    }

    // APPROACH 2: FREQUENCY MAP + MIN-HEAP OF SIZE K
    // Why min-heap: keep only k elements; pop the least frequent when size > k.
    // Why not max-heap of all: that is O(n log n); size-k heap is O(n log k).
    // Use when: interviewer wants heap, or k is small.
    // Time: O(n log k)   Extra space: O(n)
    public static int[] topKFrequentHeap(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            freq.put(x, freq.getOrDefault(x, 0) + 1);
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a, b) -> freq.get(a) - freq.get(b));

        for (int key : freq.keySet()) {
            minHeap.offer(key);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] ans = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            ans[i] = minHeap.poll();
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 1, 1, 2, 2, 3 };
        int k = 2;

        System.out.print("Bucket: ");
        for (int x : topKFrequentBucket(nums, k)) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.print("Heap:   ");
        for (int x : topKFrequentHeap(nums, k)) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
