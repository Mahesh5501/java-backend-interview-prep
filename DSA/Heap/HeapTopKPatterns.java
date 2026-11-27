package DSA.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * =====================================================================
 * MASTER CLASS: HeapTopKPatterns
 * =====================================================================
 * Pattern: Bounded Heap of Size K (Min Heap vs. Max Heap)
 *
 * Core Concept:
 * - Want K-th LARGEST?  Use a MIN Heap of size K (evict smallest).
 * - Want K-th SMALLEST? Use a MAX Heap of size K (evict largest).
 * =====================================================================
 */
public class HeapTopKPatterns {

    public static void main(String[] args) {
        int[] arr = {7, 10, 4, 3, 20, 15};
        int k = 3;

        // Sorted view for mental check: [3, 4, 7, 10, 15, 20]
        // 3rd smallest = 7 | 3rd largest = 10
        System.out.println(k + "rd Smallest Element : " + findKthSmallest(arr, k));
        System.out.println(k + "rd Largest Element  : " + findKthLargest(arr, k));
        System.out.println(k + " Smallest Elements : " + findKSmallestElements(arr, k));
    }

    /**
     * -----------------------------------------------------------------
     * 1. K-TH SMALLEST ELEMENT (Max Heap of Size K)
     * -----------------------------------------------------------------
     * Time Complexity:  O(n log k) - Each insertion/removal takes O(log k).
     * Space Complexity: O(k)       - Heap never holds more than k elements.
     */
    public static int findKthSmallest(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input or k out of range");
        }

        // Max-heap: largest element is at the head
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : nums) {
            maxHeap.offer(num);

            // Evict the largest element if size exceeds k
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // Top of max-heap is the largest among the k smallest -> k-th smallest overall
        return maxHeap.peek();
    }

    /**
     * -----------------------------------------------------------------
     * 2. K-TH LARGEST ELEMENT (Min Heap of Size K)
     * -----------------------------------------------------------------
     * Time Complexity:  O(n log k)
     * Space Complexity: O(k)
     */
    public static int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input or k out of range");
        }

        // Min-heap: smallest element is at the head
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);

            // Evict the smallest element if size exceeds k
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // Top of min-heap is the smallest among the k largest -> k-th largest overall
        return minHeap.peek();
    }

    /**
     * -----------------------------------------------------------------
     * 3. RETURN ALL K SMALLEST ELEMENTS (Max Heap of Size K)
     * -----------------------------------------------------------------
     * Time Complexity:  O(n log k)
     * Space Complexity: O(k)
     */
    public static List<Integer> findKSmallestElements(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input or k out of range");
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : nums) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        return new ArrayList<>(maxHeap);
    }
}
