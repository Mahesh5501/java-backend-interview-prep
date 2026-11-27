package DSA.Stack;

import java.util.ArrayDeque;
import java.util.Deque;

/*
Problem: Decode String

See notes/Problem_06_Decode_String.md — every approach below matches the note.
*/

/**
 * LeetCode 394 — Decode String
 * Pattern: Stack of (prefix string, repeat count) states
 */
public class DecodeString {

  // APPROACH 1: RECURSIVE — treat '[' as subproblem boundary
  public static String decodeBruteRecursive(String s) {
    int[] index = {0};
    return decodeSegment(s, index);
  }

  private static String decodeSegment(String s, int[] index) {
    StringBuilder current = new StringBuilder();
    int repeat = 0;

    while (index[0] < s.length()) {
      char c = s.charAt(index[0]);
      if (Character.isDigit(c)) {
        repeat = repeat * 10 + (c - '0');
        index[0]++;
      } else if (c == '[') {
        index[0]++;
        String inner = decodeSegment(s, index);
        for (int k = 0; k < repeat; k++) {
          current.append(inner);
        }
        repeat = 0;
      } else if (c == ']') {
        index[0]++;
        return current.toString();
      } else {
        current.append(c);
        index[0]++;
      }
    }
    return current.toString();
  }

  // APPROACH 2: STACK — push counts and strings; build on ']'
  // Time: O(n * max repeat) output size, Space: O(n)
  public static String decodeString(String s) {
    Deque<Integer> counts = new ArrayDeque<>();
    Deque<StringBuilder> strings = new ArrayDeque<>();

    StringBuilder current = new StringBuilder();
    int repeat = 0;

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        repeat = repeat * 10 + (c - '0');
      } else if (c == '[') {
        counts.push(repeat);
        strings.push(current);
        current = new StringBuilder();
        repeat = 0;
      } else if (c == ']') {
        StringBuilder decoded = strings.pop();
        int times = counts.pop();
        for (int t = 0; t < times; t++) {
          decoded.append(current);
        }
        current = decoded;
      } else {
        current.append(c);
      }
    }
    return current.toString();
  }

  /**
   * Dry run: "3[a2[c]]"
   * read 3, '[', push count 3 and "", current="a"
   * read 2, '[', push 2 and "a", current="c"
   * ']' -> pop repeat 2, merge "cc" into "a" -> "acc"
   * ']' -> pop repeat 3, merge -> "accaccacc"
   */
  public static void main(String[] args) {
    String[] tests = {"3[a]2[bc]", "3[a2[c]]", "2[abc]3[cd]ef", "abc", "10[a]"};

    for (String s : tests) {
      System.out.println("Input:  " + s);
      System.out.println("Recursive: " + decodeBruteRecursive(s));
      System.out.println("Stack:     " + decodeString(s));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Digits build k; '[' saves k and current string on stacks; on ']' pop and repeat substring.");
  }
}