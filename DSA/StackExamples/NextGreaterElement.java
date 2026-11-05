package DSA.StackExamples;

/*
Problem: Next Greater Element

Given an integer array, for every element find the first greater element to its right.
If there is no greater element, return -1.
Examples:
Input:  [4, 5, 2, 10, 8]
Output: [5, 10, 10, -1, -1]

Input:  [1, 3, 2, 4]
Output: [3, 4, 4, -1]

Input:  [5, 4, 3, 2, 1]
Output: [-1, -1, -1, -1, -1]

Pattern: Monotonic Stack

Time: O(n)
Space: O(n)
*/

import java.util.*;

public class NextGreaterElement {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGreater(new int[]{4, 5, 2, 10, 8}))); // [5, 10, 10, -1, -1]
        System.out.println(Arrays.toString(nextGreater(new int[]{1, 3, 2, 4}))); // [3, 4, 4, -1]
        System.out.println(Arrays.toString(nextGreater(new int[]{5, 4, 3, 2, 1}))); // [-1, -1, -1, -1, -1]
    }

    static int[] nextGreater(int[] nums) {

        Deque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[nums.length];

        for (int right = nums.length - 1; right >= 0; right--) {
            while (!stack.isEmpty() && stack.peek() <= nums[right]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                result[right] = -1;
            } else {
                result[right] = stack.peek();
            }
            stack.push(nums[right]);
        }
        return result;
    }
}