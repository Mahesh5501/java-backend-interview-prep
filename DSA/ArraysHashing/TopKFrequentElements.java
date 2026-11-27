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

See notes/Problem_05_Top_K_Frequent_Elements.md — every approach below matches the note.
*/

public class TopKFrequentElements {

    // Shared: build value → frequency
    private static Map<Integer, Integer> buildFreq(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            freq.put(x, freq.getOrDefault(x, 0) + 1);
        }
        return freq;
    }

    // APPROACH 1: FREQUENCY MAP + SORT ENTRIES
    // Why: easiest after counting — sort unique keys by frequency descending, take k.
    // Use when: quick coding / constraints mild.
    // Time: O(n log n)   Extra space: O(n)
    public static int[] topKFrequentSort(int[] nums, int k) {
        Map<Integer, Integer> freq = buildFreq(nums);
        List<Integer> keys = new ArrayList<>(freq.keySet());
        keys.sort((a, b) -> Integer.compare(freq.get(b), freq.get(a))); // high freq first

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = keys.get(i);
        }
        return ans;
    }

    // APPROACH 2: FREQUENCY MAP + MIN-HEAP OF SIZE K
    // Why min-heap: keep only k elements; pop the least frequent when size > k.
    // Use when: interviewer wants heap, or k is small.
    // Time: O(n log k)   Extra space: O(n)
    public static int[] topKFrequentHeap(int[] nums, int k) {
        Map<Integer, Integer> freq = buildFreq(nums);

        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(freq.get(a), freq.get(b)));

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

    // APPROACH 3: FREQUENCY MAP + BUCKETS (OPTIMAL — often preferred)
    // Why buckets: frequency is at most n, so we can "sort" by freq in O(n).
    // bucket[f] = values that appear exactly f times; read from high f downward.
    // Time: O(n)   Extra space: O(n)
    public static int[] topKFrequentBucket(int[] nums, int k) {
        Map<Integer, Integer> freq = buildFreq(nums);

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

    private static void printArr(String label, int[] arr) {
        System.out.print(label);
        for (int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] nums = { 1, 1, 1, 2, 2, 3 };
        int k = 2;
        printArr("Sort:   ", topKFrequentSort(nums, k));
        printArr("Heap:   ", topKFrequentHeap(nums, k));
        printArr("Bucket: ", topKFrequentBucket(nums, k));
    }
}
