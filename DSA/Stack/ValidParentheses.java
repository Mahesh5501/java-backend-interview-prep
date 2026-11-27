package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 20 — Valid Parentheses
 * Pattern: Stack validation (LIFO nesting)
 */
public class ValidParentheses {

  // APPROACH 1: BRUTE FORCE
  // Repeatedly remove valid pairs "()", "[]", "{}" until no change.
  // Time: O(n^2) worst case (nested deletes), Space: O(n) for string builds.
  public static boolean bruteForce(String s) {
    String prev = "";
    String current = s;
    while (!current.equals(prev)) {
      prev = current;
      current = current.replace("()", "")
          .replace("[]", "")
          .replace("{}", "");
    }
    return current.isEmpty();
  }

  // APPROACH 2: STACK — push openers, match closers against peek
  // Time: O(n), Space: O(n)
  public static boolean isValidOpeners(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '(' || c == '[' || c == '{') {
        stack.push(c);
      } else if (c == ')' || c == ']' || c == '}') {
        if (stack.isEmpty()) {
          return false;
        }
        char top = stack.pop();
        if (c == ')' && top != '(') return false;
        if (c == ']' && top != '[') return false;
        if (c == '}' && top != '{') return false;
      }
    }
    return stack.isEmpty();
  }

  // APPROACH 3: STACK — push expected closing bracket (interview favourite)
  // Time: O(n), Space: O(n)
  public static boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '(') {
        stack.push(')');
      } else if (c == '{') {
        stack.push('}');
      } else if (c == '[') {
        stack.push(']');
      } else {
        if (stack.isEmpty() || stack.pop() != c) {
          return false;
        }
      }
    }
    return stack.isEmpty();
  }

  // APPROACH 4: MAP + STACK (same logic, explicit pairs)
  public static boolean isValidWithMap(String s) {
    Map<Character, Character> pairs = new HashMap<>();
    pairs.put(')', '(');
    pairs.put(']', '[');
    pairs.put('}', '{');

    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
      if (pairs.containsValue(c)) {
        stack.push(c);
      } else if (pairs.containsKey(c)) {
        if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
          return false;
        }
      }
    }
    return stack.isEmpty();
  }

  /**
   * Dry run (optimal): s = "([)]"
   * ( -> push ')'
   * [ -> push ']'
   * ) -> pop must be ')' but top is ']' -> false
   */
  public static void main(String[] args) {
    String[] tests = {"()", "()[]{}", "(]", "([)]", "{[]}", "", "(((", ")))"};

    for (String s : tests) {
      System.out.println("Input: \"" + s + "\"");
      System.out.println("  Brute force:     " + bruteForce(s));
      System.out.println("  Openers stack:   " + isValidOpeners(s));
      System.out.println("  Expected closer: " + isValid(s));
      System.out.println("  Map stack:       " + isValidWithMap(s));
      System.out.println("-------------------------");
    }

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "I scan left to right. For each opener I push the expected closer.");
    System.out.println(
        "On a closer I must pop a matching character; empty stack or mismatch => false.");
    System.out.println("At the end the stack must be empty. Time O(n), space O(n).");
  }
}