package DSA.StackExamples;

/*
Problem: Remove Adjacent Duplicates
Given a string, repeatedly remove two adjacent equal characters.

Examples:
"abbaca" → "ca"
"azxxzy" → "ay"
"aaaa"  → ""

Pattern: Stack

Time: O(n)
Space: O(n)
*/

import java.util.*;

public class RemoveAdjacentDuplicates {

    public static void main(String[] args) {

        System.out.println(removeDuplicates("abbaca")); // ca
        System.out.println(removeDuplicates("azxxzy")); // ay
        System.out.println(removeDuplicates("aaaa"));   // ""
    }

    static String removeDuplicates(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek() == c) stack.pop();
            else stack.push(c);
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.removeLast());
        }
        return result.toString();
    }
}
