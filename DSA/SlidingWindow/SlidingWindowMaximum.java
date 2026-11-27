package DSA.SlidingWindow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
Problem: Sliding Window Maximum (LeetCode 239)

For each window of size k, return the maximum element.

Examples:
  nums = [1,3,-1,-3,5,3,6,7], k = 3 → [3,3,5,5,6,7]

See notes/Problem_10_Sliding_Window_Maximum.md — every approach below matches the note.
*/

public class SlidingWindowMaximum {

    // APPROACH 1: BRUTE FORCE (scan each window)
    // Why: direct max per window — O(n*k).
    // Time: O(n * k)   Extra space: O(1) besides output
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

    // APPROACH 2: MONOTONIC DEQUE OF INDICES (OPTIMAL)
    // Why: deque keeps decreasing values; front = max; expire with right - k.
    // Time: O(n)   Extra space: O(k)
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int right = 0; right < nums.length; right++) {
            while (!deque.isEmpty() && deque.peekFirst() <= right - k) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
                deque.pollLast();
            }

            deque.offerLast(right);

            if (right >= k - 1) {
                result[right - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 3, -1, -3, 5, 3, 6, 7 };
        int k = 3;

        System.out.println("nums = " + Arrays.toString(nums) + ", k = " + k);
        System.out.println("Brute:  " + Arrays.toString(maxSlidingWindowBruteForce(nums.clone(), k)));
        System.out.println("Deque:  " + Arrays.toString(maxSlidingWindow(nums.clone(), k)));
    }
}