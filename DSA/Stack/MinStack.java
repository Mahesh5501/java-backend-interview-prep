package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/*
Problem: Min Stack (O(1) getMin)

See notes/Problem_02_Min_Stack.md — every approach below matches the note.
*/

/**
 * LeetCode 155 — Min Stack
 * Pattern: Auxiliary stack (or paired nodes) for O(1) getMin
 */
public class MinStack {

  // APPROACH 3: DUAL DEQUE — values + running mins (OPTIMAL — LeetCode instance API)
  // Why two stacks: pop top and min together; min deque always mirrors prefix minimum.
  private final Deque<Integer> values = new ArrayDeque<>();
  private final Deque<Integer> mins = new ArrayDeque<>();

  public void push(int val) {
    values.push(val);
    int minSoFar = mins.isEmpty() ? val : Math.min(mins.peek(), val);
    mins.push(minSoFar);
  }

  public void pop() {
    values.pop();
    mins.pop();
  }

  public int top() {
    return values.peek();
  }

  public int getMin() {
    return mins.peek();
  }

  // APPROACH 1: BRUTE — scan entire stack on every getMin()
  // Why mention: shows cost before adding auxiliary structure.
  static class MinStackBrute {
    private final Deque<Integer> stack = new ArrayDeque<>();

    void push(int val) {
      stack.push(val);
    }

    void pop() {
      stack.pop();
    }

    int top() {
      return stack.peek();
    }

    int getMin() {
      int min = Integer.MAX_VALUE;
      for (int x : stack) {
        min = Math.min(min, x);
      }
      return min;
    }
  }

  // APPROACH 2: SINGLE STACK OF PAIRS (value, minSoFar)
  // Why: one deque instead of two; store min snapshot on each node.
  static class MinStackPairs {
    private final Deque<long[]> stack = new ArrayDeque<>();

    void push(int val) {
      long minSoFar = stack.isEmpty() ? val : Math.min(stack.peek()[1], val);
      stack.push(new long[] {val, minSoFar});
    }

    void pop() {
      stack.pop();
    }

    int top() {
      return (int) stack.peek()[0];
    }

    int getMin() {
      return (int) stack.peek()[1];
    }
  }

  /**
   * Dry run after pushes 3, 1, 5:
   * values: [3,1,5] (top 5)   mins: [3,1,1] (top 1) -> getMin() = 1
   * pop -> top 5 removed, getMin still 1
   */
  public static void main(String[] args) {
    MinStack optimal = new MinStack();
    optimal.push(3);
    optimal.push(1);
    optimal.push(5);
    System.out.println("Optimal — top=" + optimal.top() + ", min=" + optimal.getMin());
    optimal.pop();
    System.out.println("After pop — top=" + optimal.top() + ", min=" + optimal.getMin());

    MinStackBrute brute = new MinStackBrute();
    brute.push(3);
    brute.push(1);
    System.out.println("\nBrute getMin scans stack: " + brute.getMin());

    MinStackPairs pairs = new MinStackPairs();
    pairs.push(2);
    pairs.push(0);
    pairs.push(3);
    System.out.println("Pairs — top=" + pairs.top() + ", min=" + pairs.getMin());

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "Brute getMin is O(n). I keep a second stack of running minimums so each push/pop");
    System.out.println("updates min in O(1). Duplicating min values is OK.");
  }
}