package DSA.PriorityQueueHeap;

import java.util.*;

public class KthSmallest {

    /*
    Problem:
    Given an integer array nums and an integer k, return the kth smallest element in the array.

    Example:
    Input:  nums = [7, 10, 4, 3, 20, 15], k = 3
    Output: 7

    Explanation:
    The sorted order is [3, 4, 7, 10, 15, 20].
    The 3rd smallest element is 7.
    */

    static int findKthSmallest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());

        for(int num : nums){
            pq.add(num);
            if(pq.size() > k){
                pq.poll();
            }
        }
        return pq.peek();
    }

    public static void main(String[] args) {

        System.out.println(findKthSmallest(new int[]{7, 10, 4, 3, 20, 15}, 3)); // 7
        System.out.println(findKthSmallest(new int[]{3, 2, 1, 5, 6, 4}, 2)); // 2
        System.out.println(findKthSmallest(new int[]{5, 5, 5, 5}, 2)); // 5
    }
}
