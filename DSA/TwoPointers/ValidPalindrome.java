package DSA.TwoPointers;

/*
Problem: Valid Palindrome (LeetCode 125)

See notes/Problem_01_Valid_Palindrome.md — every approach below matches the note.
*/

public class ValidPalindrome {

    // APPROACH 1: CLEAN, REVERSE, COMPARE
    // Build a cleaned lowercase string, reverse it, compare.
    // Why use it: easiest brute force that respects LeetCode filtering rules.
    // Time: O(n)   Extra space: O(n)
    public static boolean validPalindromeBruteForce(String s) {

        StringBuilder cleaned = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            if (Character.isLetterOrDigit(current)) {
                cleaned.append(Character.toLowerCase(current));
            }
        }

        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.toString().equals(reversed);
    }

    // APPROACH 2: BRUTE FORCE (simple strings — reverse whole input)
    // Reverse the string and compare it with the original.
    // Time: O(n), Auxiliary space: O(n).
    public static boolean bruteForce(String s) {

        StringBuilder reversed = new StringBuilder(s);
        reversed.reverse();

        return s.equals(reversed.toString());  // equals() compares contents, unlike ==.
    }

    // APPROACH 3: TWO POINTERS
    // Compare characters at opposite ends and move inward.
    // Time: O(n), Auxiliary space: O(1).
    // Assumption: lowercase English letters only.
    public static boolean twoPointers(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            // A mismatch proves the string is not a palindrome.
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            // Both positions matched, so move to the next pair.
            left++;
            right--;
        }

        return true;
    }

    // APPROACH 4: FULL LEETCODE VERSION (OPTIMAL)
    // Ignore punctuation, spaces, and letter case.
    // Time: O(n), Auxiliary space: O(1).
    public static boolean validPalindrome(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            // Skip non-alphanumeric characters from the left.
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            // Skip non-alphanumeric characters from the right.
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare letters without case sensitivity.
            char leftChar = Character.toLowerCase(s.charAt(left));
            char rightChar = Character.toLowerCase(s.charAt(right));

            if (leftChar != rightChar) {
                return false;
            }

            // Matching pair: move both pointers inward.
            left++;
            right--;
        }

        return true;
    }

    public static void main(String[] args) {

        String[] testCases = {
            "madam",
            "hello",
            "",
            "a",
            "aa",
            "ab",
            "racecar",
            "A man, a plan, a canal: Panama",
            "race a car",
            "!!!"
        };

        for (String s : testCases) {

            System.out.println("Input: " + s);

            // These first two methods assume the simple version.
            System.out.println("Clean + reverse: " + validPalindromeBruteForce(s));
            System.out.println("Brute force: " + bruteForce(s));
            System.out.println("Two pointers: " + twoPointers(s));
            System.out.println("Full version: " + validPalindrome(s));

            System.out.println("-------------------------");
        }
    }
}
