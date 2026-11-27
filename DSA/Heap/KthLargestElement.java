package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: Kth Largest Element in an Array (LeetCode 215)

Note: "kth largest" is not the same as sorting in ascending order and picking index k.

Example:
    nums = [3, 2, 1, 5, 6, 4], k = 2 -> 5

See notes/Problem_01_Kth_Largest_Element.md — every approach below matches the note.
*/

public class KthLargestElement {

    // APPROACH 1: FULL SORT
    // Sort ascending, take the k-th from the end.
    // Why use it: simplest baseline; fine when k is close to n.
    // Time: O(n log n)   Extra space: O(log n) sort stack
    public static int findKthLargestSort(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    // APPROACH 2: MIN-HEAP OF SIZE K (OPTIMAL — what we use in interviews)
    // Root is smallest among the k largest kept — that value is the kth largest overall.
    // Why heap: O(log k) per insert vs full sort when k << n.
    // Time: O(n log k)   Extra space: O(k)
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int value : nums) {
            minHeap.offer(value);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        return minHeap.peek();
    }

    public static void main(String[] args) {
        int[] nums = { 3, 2, 1, 5, 6, 4 };
        System.out.println("k=2 (heap):  " + findKthLargest(nums.clone(), 2)); // 5
        System.out.println("k=2 (sort):  " + findKthLargestSort(nums.clone(), 2)); // 5

        int[] nums2 = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
        System.out.println("k=4 (heap):  " + findKthLargest(nums2, 4)); // 4
    }
}