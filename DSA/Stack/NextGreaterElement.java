package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/*
Problem: Next Greater Element I (+ single-array variant)

See notes/Problem_04_Next_Greater_Element.md — every approach below matches the note.
*/

/**
 * LeetCode 496 — Next Greater Element I
 * Pattern: Monotonic decreasing stack + hash map for nums2 lookup
 */
public class NextGreaterElement {

  // APPROACH 1: BRUTE FORCE — for each num in nums1, find it in nums2 then scan right
  // Time: O(n*m), Space: O(1)
  public static int[] bruteForce(int[] nums1, int[] nums2) {
    int[] answer = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
      int target = nums1[i];
      int idx = -1;
      for (int j = 0; j < nums2.length; j++) {
        if (nums2[j] == target) {
          idx = j;
          break;
        }
      }
      int nextGreater = -1;
      for (int j = idx + 1; j < nums2.length; j++) {
        if (nums2[j] > target) {
          nextGreater = nums2[j];
          break;
        }
      }
      answer[i] = nextGreater;
    }
    return answer;
  }

  // APPROACH 2: MONOTONIC STACK on nums2, then map value -> next greater
  // Time: O(n + m), Space: O(m)
  public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
    Map<Integer, Integer> nextGreaterByValue = new HashMap<>();
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = nums2.length - 1; i >= 0; i--) {
      int value = nums2[i];
      while (!stack.isEmpty() && stack.peek() <= value) {
        stack.pop();
      }
      nextGreaterByValue.put(value, stack.isEmpty() ? -1 : stack.peek());
      stack.push(value);
    }

    int[] answer = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
      answer[i] = nextGreaterByValue.get(nums1[i]);
    }
    return answer;
  }

  // Variant: next greater for every index in a single array (no map)
  public static int[] nextGreaterForArray(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && stack.peek() <= nums[i]) {
        stack.pop();
      }
      result[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(nums[i]);
    }
    return result;
  }

  /**
   * Dry run on nums2 = [1,3,4,2] right to left:
   * i=3 val 2: stack [] -> map 2->-1, push 2
   * i=2 val 4: pop 2 -> map 4->-1, push 4
   * i=1 val 3: top 4>3 -> map 3->4, push 3
   */
  public static void main(String[] args) {
    int[] nums1 = {4, 1, 2};
    int[] nums2 = {1, 3, 4, 2};

    System.out.println("nums1: " + Arrays.toString(nums1));
    System.out.println("nums2: " + Arrays.toString(nums2));
    System.out.println("Brute:  " + Arrays.toString(bruteForce(nums1, nums2)));
    System.out.println("Stack:  " + Arrays.toString(nextGreaterElement(nums1, nums2)));

    System.out.println();
    System.out.println(
        "Single array NGE: "
            + Arrays.toString(nextGreaterForArray(new int[] {4, 5, 2, 10, 8})));

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "I preprocess nums2 with a decreasing monotonic stack to record next greater per value.");
    System.out.println("Then answer nums1 in O(1) per element via the map. Overall O(n+m).");
  }
}