package DSA.TwoPointers;

public class ValidPalindromeII {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (TRY DELETE EACH CHARACTER)
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(n) for temporary strings
    // =====================================================

    public static boolean validPalindromeBruteForce(String s) {

        // An empty string or one character is already a palindrome.
        if (s.length() <= 1) {
            return true;
        }

        // Try removing exactly one character at every position.
        for (int skipIndex = 0; skipIndex < s.length(); skipIndex++) {

            StringBuilder candidate = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (i != skipIndex) {
                    candidate.append(s.charAt(i));
                }
            }

            if (isPlainPalindrome(candidate.toString())) {
                return true;
            }
        }

        return false;
    }

    // Helper for brute force: checks a plain string with no skip budget.
    private static boolean isPlainPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }


    // =====================================================
    // APPROACH 2: TWO POINTERS + ONE SKIP BRANCH (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static boolean validPalindromeTwoPointers(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            // Happy path: characters match, keep moving inward.
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
                continue;
            }

            // We are allowed one delete. Try skipping left OR skipping right.
            return isPalindromeRange(s, left + 1, right)
                    || isPalindromeRange(s, left, right - 1);
        }

        // Never found a mismatch that could not be fixed with one delete.
        return true;
    }

    // Checks whether s[left..right] is a plain palindrome.
    private static boolean isPalindromeRange(String s, int left, int right) {

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        String[] testCases = {
                "abca",
                "aba",
                "ab",
                "eccentric",
                "deeee"
        };

        for (String test : testCases) {
            boolean brute = validPalindromeBruteForce(test);
            boolean optimal = validPalindromeTwoPointers(test);

            System.out.println("Input:  \"" + test + "\"");
            System.out.println("Brute:   " + brute);
            System.out.println("Optimal: " + optimal);
            System.out.println();
        }
    }
}