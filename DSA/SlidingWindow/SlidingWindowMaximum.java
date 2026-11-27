package DSA.SlidingWindow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
Problem: Sliding Window Maximum (LeetCode 239)
Given an integer array nums and an integer k, return the maximum element
in every contiguous subarray of size k.

Example:
nums = [1,3,-1,-3,5,3,6,7], k = 3 → [3,3,5,5,6,7]

Expected Approach:
Brute O(n*k), optimal monotonic deque of indices O(n).
*/

public class SlidingWindowMaximum {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (scan each window)
    // Time Complexity: O(n * k)
    // Auxiliary Space: O(1) excluding output
    // =====================================================

  public static int[] maxSlidingWindowBruteForce(int[] nums, int k) {

        int[] result = new int[nums.length - k + 1];

        for (int start = 0; start <= nums.length - k; start++) {
            int max = Integer.MIN_VALUE;
            for (int i = start; i < start + k; i++) {
                max = Math.max(max, nums[i]);
            }
            result[start] = max;
        }

        return result;
    }


    // =====================================================
    // APPROACH 2: MONOTONIC DEQUE OF INDICES (OPTIMAL)
    // Time Complexity: O(n) — each index pushed/popped at most once
    // Auxiliary Space: O(k) for deque
    //
    // Deque stores **indices** whose values are in decreasing order.
    // Front = index of current window maximum.
    // =====================================================

  public static int[] maxSlidingWindow(int[] nums, int k) {

        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int right = 0; right < nums.length; right++) {

            // Remove indices that fell out of the window
            while (!deque.isEmpty() && deque.peekFirst() <= right - k) {
                deque.pollFirst();
            }

            // Remove smaller values from the back — they can never be max
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
                deque.pollLast();
            }

            deque.offerLast(right);

            // Window of size k is ready when right >= k - 1
            if (right >= k - 1) {
                result[right - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        int[] nums = { 1, 3, -1, -3, 5, 3, 6, 7 };
        int k = 3;

        System.out.println("nums = " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("Brute:  " + Arrays.toString(maxSlidingWindowBruteForce(nums.clone(), k)));
        System.out.println("Deque:  " + Arrays.toString(maxSlidingWindow(nums.clone(), k)));
        // [3, 3, 5, 5, 6, 7]
    }
}