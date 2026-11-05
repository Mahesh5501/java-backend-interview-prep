package DSA.StackExamples;

/*
Problem: Valid Parentheses

Given a string containing only '(', ')', '{', '}', '[' and ']', determine whether the brackets are valid.

A valid string means:
1. Every opening bracket has a matching closing bracket.
2. Brackets close in the correct order.

Examples:
"()"       → true
"()[]{}"   → true
"(]"       → false
"([)]"     → false
"{[]}"     → true

Expected Pattern:
Stack

Time: O(n)
Space: O(n)
*/

import java.util.*;

public class ValidParentheses {
    public static void main(String[] args) {
        System.out.println(isValid("()"));       // true
        System.out.println(isValid("()[]{}"));   // true
        System.out.println(isValid("(]"));       // false
        System.out.println(isValid("([)]"));     // false
        System.out.println(isValid("{[]}"));     // true

        System.out.println(isValidAlternative("{[]}"));     // true

    }

    static boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char symbol = s.charAt(i);
            if (symbol == '(' || symbol == '[' || symbol == '{') {
                stack.push(symbol);
            } else if (symbol == ')' || symbol == ']' || symbol == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                if (symbol == ')' && stack.peek() == '(' ||
                        symbol == ']' && stack.peek() == '[' ||
                        symbol == '}' && stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    static boolean isValidAlternative(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') stack.push(')');
            else if (c == '{') stack.push('}');
            else if (c == '[') stack.push(']');
            else {
                if (stack.isEmpty() || stack.peek() != c) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
}
