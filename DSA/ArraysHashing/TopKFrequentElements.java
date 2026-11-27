package DSA.ArraysHashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
Problem: Top K Frequent Elements

Return the k most frequent integers in nums (any order).

Example:
  nums = [1,1,1,2,2,3], k = 2  →  [1,2] (or [2,1])

Approach 1: Frequency map + bucket by frequency — O(n)
Approach 2: Frequency map + min-heap of size k — O(n log k)
*/

public class TopKFrequentElements {

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