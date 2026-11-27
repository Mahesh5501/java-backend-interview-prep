package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
Problem: Largest Rectangle in Histogram

See notes/Problem_05_Largest_Rectangle_in_Histogram.md — every approach below matches the note.
*/

/**
 * LeetCode 84 — Largest Rectangle in Histogram
 * Pattern: Monotonic increasing stack of indices
 */
public class LargestRectangleHistogram {

  // APPROACH 1: BRUTE FORCE — every pair (left, right) as width for height at left
  // Time: O(n^2), Space: O(1)
  public static int bruteForce(int[] heights) {
    int maxArea = 0;
    int n = heights.length;
    for (int left = 0; left < n; left++) {
      int minHeight = heights[left];
      for (int right = left; right < n; right++) {
        minHeight = Math.min(minHeight, heights[right]);
        int width = right - left + 1;
        maxArea = Math.max(maxArea, minHeight * width);
      }
    }
    return maxArea;
  }

  // APPROACH 2: MONOTONIC STACK (increasing indices)
  // When heights[i] < heights[stack.top], popped bar cannot extend right past i
  // Time: O(n), Space: O(n)
  public static int largestRectangleArea(int[] heights) {
    int n = heights.length;
    int maxArea = 0;
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i <= n; i++) {
      int currentHeight = (i == n) ? 0 : heights[i];
      while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
        int heightIndex = stack.pop();
        int height = heights[heightIndex];
        int right = i;
        int left = stack.isEmpty() ? 0 : stack.peek() + 1;
        int width = right - left;
        maxArea = Math.max(maxArea, height * width);
      }
      stack.push(i);
    }
    return maxArea;
  }

  /**
   * Dry run: heights = [2,1,5,6,2,3]
   * i=1 height 1: pop 0 (h=2) width 1*1=2, push 1
   * i=3 height 6: push ...
   * sentinel at end forces remaining pops
   */
  public static void main(String[] args) {
    int[][] tests = {
      {2, 1, 5, 6, 2, 3},
      {2, 4},
      {1},
      {5, 5, 5}
    };

    for (int[] h : tests) {
      System.out.println("Heights: " + Arrays.toString(h));
      System.out.println("  Brute:  " + bruteForce(h));
      System.out.println("  Stack:  " + largestRectangleArea(h));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Brute checks all sub-arrays O(n^2). I store indices in increasing height order.");
    System.out.println(
        "When current is lower, I pop; width is from new stack top+1 to current i; area = h*width.");
    System.out.println("Append sentinel height 0 to flush the stack.");
  }
}