package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 1047 — Remove All Adjacent Duplicates In String
 * Pattern: Stack cancellation on push
 */
public class RemoveAdjacentDuplicates {

  // APPROACH 1: BRUTE FORCE — repeatedly scan and remove pairs until stable
  // Time: O(n^2) worst case, Space: O(n)
  public static String bruteForce(String s) {
    StringBuilder sb = new StringBuilder(s);
    boolean found;
    do {
      found = false;
      for (int i = 0; i < sb.length() - 1; i++) {
        if (sb.charAt(i) == sb.charAt(i + 1)) {
          sb.deleteCharAt(i + 1);
          sb.deleteCharAt(i);
          found = true;
          break;
        }
      }
    } while (found);
    return sb.toString();
  }

  // APPROACH 2: STACK — if char equals peek, pop; else push
  // Time: O(n), Space: O(n)
  public static String removeDuplicates(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (char c : s.toCharArray()) {
      if (!stack.isEmpty() && stack.peek() == c) {
        stack.pop();
      } else {
        stack.push(c);
      }
    }

    StringBuilder result = new StringBuilder();
    while (!stack.isEmpty()) {
      result.append(stack.removeLast());
    }
    return result.toString();
  }

  // APPROACH 3: StringBuilder as stack (same logic, no Deque)
  public static String removeDuplicatesStringBuilder(String s) {
    StringBuilder stack = new StringBuilder();
    for (char c : s.toCharArray()) {
      int len = stack.length();
      if (len > 0 && stack.charAt(len - 1) == c) {
        stack.deleteCharAt(len - 1);
      } else {
        stack.append(c);
      }
    }
    return stack.toString();
  }

  /**
   * Dry run: "abbaca"
   * a push, b push, b matches -> pop b, a push, c push, a matches -> pop a => "ca"
   */
  public static void main(String[] args) {
    String[] tests = {"abbaca", "azxxzy", "aaaa", "a", "", "baab"};

    for (String s : tests) {
      System.out.println("Input: \"" + s + "\"");
      System.out.println("  Brute:          " + bruteForce(s));
      System.out.println("  Stack:          " + removeDuplicates(s));
      System.out.println("  StringBuilder:  " + removeDuplicatesStringBuilder(s));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Brute rescans after each removal. Stack collapses pairs in one pass: cancel with peek or push.");
  }
}