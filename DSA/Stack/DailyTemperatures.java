package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 739 — Daily Temperatures
 * Pattern: Monotonic decreasing stack of indices
 */
public class DailyTemperatures {

  // APPROACH 1: BRUTE FORCE — for each i, scan right until warmer day
  // Time: O(n^2), Space: O(1) besides output
  public static int[] bruteForce(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (temperatures[j] > temperatures[i]) {
          answer[i] = j - i;
          break;
        }
      }
    }
    return answer;
  }

  // APPROACH 2: MONOTONIC STACK — scan right to left (indices, decreasing temps on stack)
  // Time: O(n), Space: O(n)
  public static int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int day = n - 1; day >= 0; day--) {
      while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[day]) {
        stack.pop();
      }
      answer[day] = stack.isEmpty() ? 0 : stack.peek() - day;
      stack.push(day);
    }
    return answer;
  }

  // APPROACH 3: LEFT-TO-RIGHT variant (pop while current is warmer than stack top)
  public static int[] dailyTemperaturesLeftToRight(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int day = 0; day < n; day++) {
      while (!stack.isEmpty() && temperatures[day] > temperatures[stack.peek()]) {
        int prev = stack.pop();
        answer[prev] = day - prev;
      }
      stack.push(day);
    }
    return answer;
  }

  /**
   * Dry run (right to left): T = [73,74,75,71,...]
   * day 7: stack empty -> ans[7]=0, push 7
   * day 6: 76>73 at top -> pop, stack empty -> ans[6]=0, push 6
   * day 5: top 6 has 76>72 -> ans[5]=6-5=1, push 5
   */
  public static void main(String[] args) {
    int[][] tests = {
      {73, 74, 75, 71, 69, 72, 76, 73},
      {30, 40, 50, 60},
      {30, 20, 10},
      {55}
    };

    for (int[] t : tests) {
      System.out.println("Input:  " + Arrays.toString(t));
      System.out.println("Brute:  " + Arrays.toString(bruteForce(t)));
      System.out.println("Stack:  " + Arrays.toString(dailyTemperatures(t)));
      System.out.println("L->R:   " + Arrays.toString(dailyTemperaturesLeftToRight(t)));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Brute is O(n^2). I keep a decreasing stack of indices of future warmer candidates.");
    System.out.println(
        "Scanning right-to-left, I pop indices not warmer than today; top is next warmer day.");
  }
}