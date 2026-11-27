package DSA.Stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
Problem: Evaluate Reverse Polish Notation

See notes/Problem_07_Evaluate_RPN.md — every approach below matches the note.
*/

/**
 * LeetCode 150 — Evaluate Reverse Polish Notation
 * Pattern: Operand stack; operators pop two, push result
 */
public class EvaluateRPN {

  // APPROACH 1: ARRAYLIST AS OPERAND STACK
  // Same postfix semantics as Deque; teaches stack without java.util.Deque.
  // Why mention: separates "stack behavior" from a specific JDK type.
  // Time: O(n), Space: O(n)
  public static int evalRPNBrute(ArrayList<String> tokens) {
    List<Integer> stack = new ArrayList<>();

    for (String token : tokens) {
      if (isOperator(token)) {
        int b = stack.remove(stack.size() - 1);
        int a = stack.remove(stack.size() - 1);
        stack.add(apply(a, b, token));
      } else {
        stack.add(Integer.parseInt(token));
      }
    }
    return stack.get(0);
  }

  // APPROACH 2: DEQUE STACK (OPTIMAL — interview default)
  // Numbers push. Operator pops right then left operand (order matters for - and /).
  // Why Deque: O(1) push/pop at stack top with clear LIFO.
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
        return a / b;
      default:
        throw new IllegalArgumentException("Unknown operator: " + op);
    }
  }

  public static void main(String[] args) {
    String[][] tests = {
      {"2", "1", "+", "3", "*"},
      {"4", "13", "5", "/", "+"},
      {"18"}
    };

    for (String[] t : tests) {
      ArrayList<String> list = new ArrayList<>(List.of(t));
      System.out.println("Tokens: " + String.join(" ", t));
      System.out.println("List:   " + evalRPNBrute(list));
      System.out.println("Deque:  " + evalRPN(t));
      System.out.println("-------------------------");
    }
  }
}