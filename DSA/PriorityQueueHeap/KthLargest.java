package DSA.PriorityQueueHeap;

import java.util.PriorityQueue;

public class KthLargest {

    /*
    Problem: Kth Largest Element

    Given an integer array nums and an integer k, return the kth largest element in the array.

    Example 1:
    nums = [3, 2, 1, 5, 6, 4]
    k = 2

    Output: 5

    Example 2:
    nums = [3, 2, 3, 1, 2, 4, 5, 5, 6]
    k = 4

    Output: 4

    Expected Approach:
    Use a Min Heap of size k.
    */

    static int findKthLargest(int[] nums, int k) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : nums) {
            pq.add(num);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2)); // 5
        System.out.println(findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4)); // 4
    }
}
