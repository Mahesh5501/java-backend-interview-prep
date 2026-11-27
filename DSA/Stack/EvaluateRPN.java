package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 150 — Evaluate Reverse Polish Notation
 * Pattern: Operand stack; operators pop two, push result
 */
public class EvaluateRPN {

  // APPROACH 1: BRUTE — convert to list and simulate with a mutable structure (same as stack)
  // Shown as explicit stack for teaching parity with optimal.

  // APPROACH 2: STACK evaluation
  // Time: O(n), Space: O(n)
  public static int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();

    for (String token : tokens) {
      if (isOperator(token)) {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(apply(a, b, token));
      } else {
        stack.push(Integer.parseInt(token));
      }
    }
    return stack.pop();
  }

  private static boolean isOperator(String token) {
    return "+".equals(token)
        || "-".equals(token)
        || "*".equals(token)
        || "/".equals(token);
  }

  private static int apply(int a, int b, String op) {
    switch (op) {
      case "+":
        return a + b;
      case "-":
        return a - b;
      case "*":
        return a * b;
      case "/":
        return a / b; // truncates toward zero in Java
      default:
        throw new IllegalArgumentException("Unknown operator: " + op);
    }
  }

  /**
   * Dry run: ["2","1","+","3","*"]
   * push 2, push 1, '+' -> pop 1,2 push 3, push 3, '*' -> pop 3,3 push 9
   */
  public static void main(String[] args) {
    String[][] tests = {
      {"2", "1", "+", "3", "*"},
      {"4", "13", "5", "/", "+"},
      {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"},
      {"18"}
    };

    for (String[] t : tests) {
      System.out.println("Tokens: " + String.join(" ", t));
      System.out.println("Result: " + evalRPN(t));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Numbers push. On operator, second pop is right operand, first is left — important for - and /.");
    System.out.println("Single value left at end. O(n) time.");
  }
}