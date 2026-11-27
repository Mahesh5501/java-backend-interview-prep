package DSA.TwoPointers;

import java.util.Stack;

/**
 * =====================================================================
 * MASTER CLASS: ReverseStringPatterns
 * =====================================================================
 * Pattern: String Reversal (Iterative, Two-Pointer, Recursive, & Stack)
 * 
 * Core Concept: 
 * Strings in Java are immutable. Therefore, reversing a string always requires transforming it or building a new representation. 
 * Depending on constraints, you can achieve this via built-in wrappers, two pointers, recursion, or explicit data structures.
 * =====================================================================
 */
public class ReverseStringPatterns {

    public static void main(String[] args) {
        System.out.println("=== RUNNING REVERSE STRING PATTERN EXAMPLES ===");

        String original = "hello";

        System.out.println("1. StringBuilder Approach : " + reverseWithStringBuilder(original));
        System.out.println("2. Two-Pointer Approach   : " + reverseWithTwoPointers(original));
        System.out.println("3. Recursive Approach     : " + reverseWithRecursion(original));
        System.out.println("4. Stack Approach         : " + reverseWithStack(original));
        System.out.println("5. Backward Loop Approach : " + reverseWithBackwardLoop(original));
    }

    /**
     * -----------------------------------------------------------------
     * 1. THE BUILT-IN APPROACH (StringBuilder / StringBuffer)
     * -----------------------------------------------------------------
     * When to use: Day-to-day production code where speed of writing matters and interview constraints don't prohibit built-ins.
     * 
     * Time Complexity:  O(n) - Iterates through the string of length n.
     * Space Complexity: O(n) - Allocates memory for the new character array buffer.
     */
    public static String reverseWithStringBuilder(String s) {
        if (s == null) return null;

        // StringBuilder provides an optimized mutable sequence with a built-in reverse method
        return new StringBuilder(s).reverse().toString(); 
        
        // We can do above one line with below three lines for better readability
        // StringBuilder sb = new StringBuilder(s);
        // sb.reverse();
        // return sb.toString();
    }

    /**
     * -----------------------------------------------------------------
     * 2. THE TWO-POINTER APPROACH (In-Place Array Swap)
     * -----------------------------------------------------------------
     * When to use: The classic gold standard for interviews when built-ins are restricted. 
     * Logic: 
     * - Convert string to a character array.
     * - Place 'left' at index 0 and 'right' at the last index.
     * - Swap characters, then move pointers inward until they meet.
     * 
     * Time Complexity:  O(n) - Visits each character once.
     * Space Complexity: O(n) - Required because Java strings are immutable, so we must store the character array.
     */
    public static String reverseWithTwoPointers(String s) {
        if (s == null) return null;

        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            // Swap characters at left and right pointers
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;

            // Move pointers towards the center
            left++;
            right--;
        }

        return new String(chars);
    }

    /**
     * -----------------------------------------------------------------
     * 3. THE RECURSIVE APPROACH
     * -----------------------------------------------------------------
     * When to use: Demonstrating mastery of call-stack manipulation and divide-and-conquer.
     * Logic:
     * - Base Case: If the string is empty or has 1 character, return it.
     * - Recursive Step: Take the first character, send the remaining substring to be reversed, and append that first character to the end.
     * 
     * Time Complexity:  O(n) - Makes 'n' recursive calls.
     * Space Complexity: O(n) - Call stack memory proportional to string length.
     */
    public static String reverseWithRecursion(String s) {
        // Base case: empty string or single character
        if (s == null || s.length() <= 1) {
            return s;
        }

        // Recursively reverse the substring starting from index 1, then place the very first character at the very end.
        return reverseWithRecursion(s.substring(1)) + s.charAt(0);
    }

    /**
     * -----------------------------------------------------------------
     * 4. THE DATA STRUCTURE APPROACH (Using a Stack)
     * -----------------------------------------------------------------
     * When to use: Explaining the **LIFO (Last-In, First-Out)** property.
     * Logic:
     * - Push every character of the string onto a Stack.
     * - Pop characters out one by one. Because of LIFO, they automatically pop in reverse order!
     * 
     * Time Complexity:  O(n) - Pushing and popping 'n' elements.
     * Space Complexity: O(n) - Stack stores all 'n' characters.
     */
    public static String reverseWithStack(String s) {
        if (s == null) return null;

        Stack<Character> stack = new Stack<>();

        // Push all characters onto the stack
        for (int i = 0; i < s.length(); i++) {
            stack.push(s.charAt(i));
        }

        // Pop characters out and build the reversed string
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    /**
     * -----------------------------------------------------------------
     * 5. THE BACKWARD ITERATION LOOP
     * -----------------------------------------------------------------
     * When to use: A straightforward, intuitive alternative if you want to avoid managing two converging pointers.
     * Logic:
     * - Loop backwards from the last index (`length - 1`) down to `0`.
     * - Append each character to a StringBuilder.
     * 
     * Time Complexity:  O(n) - Single pass backwards.
     * Space Complexity: O(n) - Storage for the resulting StringBuilder string.
     */
    public static String reverseWithBackwardLoop(String s) {
        if (s == null) return null;

        StringBuilder sb = new StringBuilder();

        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }
}
