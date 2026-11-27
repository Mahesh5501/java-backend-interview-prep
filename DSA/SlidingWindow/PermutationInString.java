package DSA.SlidingWindow;

import java.util.Arrays;

/*
Problem: Permutation in String (LeetCode 567)

Return true if s2 contains a permutation of s1 (same character counts).

Examples:
  s1 = "ab", s2 = "eidbaooo" → true

See notes/Problem_08_Permutation_in_String.md — every approach below matches the note.
*/

public class PermutationInString {

    // APPROACH 1: BRUTE FORCE (sort s1 and each window of s2)
    // Why: sorted window equals sorted pattern ⇔ permutation.
    // Time: O(n * k log k)   Extra space: O(k)
    public static boolean checkInclusionBruteForce(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        char[] pattern = s1.toCharArray();
        Arrays.sort(pattern);
        int k = s1.length();

        for (int start = 0; start <= s2.length() - k; start++) {
            char[] window = s2.substring(start, start + k).toCharArray();
            Arrays.sort(window);
            if (Arrays.equals(pattern, window)) {
                return true;
            }
        }

        return false;
    }

    // APPROACH 2: FIXED WINDOW + int[26] FREQUENCY (OPTIMAL)
    // Why: slide window of |s1|; compare need vs window counts in O(26).
    // Time: O(n)   Extra space: O(1)
    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        int[] need = new int[26];
        int[] window = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            need[s1.charAt(i) - 'a']++;
        }

        int k = s1.length();

        for (int right = 0; right < s2.length(); right++) {
            window[s2.charAt(right) - 'a']++;

            int left = right - k + 1;
            if (left < 0) {
                continue;
            }

            if (arraysEqual(need, window)) {
                return true;
            }

            window[s2.charAt(left) - 'a']--;
        }

        return false;
    }

    private static boolean arraysEqual(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "eidbaooo";

        System.out.println("s1 = \"" + s1 + "\", s2 = \"" + s2 + "\"");
        System.out.println("Brute:  " + checkInclusionBruteForce(s1, s2));
        System.out.println("Window: " + checkInclusion(s1, s2));
        System.out.println("Window: " + checkInclusion("ab", "eidboaoo"));
    }
}