package DSA.SlidingWindow;

/*
Problem: Permutation in String (LeetCode 567)
Given strings s1 and s2, return true if s2 contains a permutation of s1.

A permutation has the same character counts as s1 (same letters, any order).

Example:
s1 = "ab", s2 = "eidbaooo" → true

Expected Approach:
Fixed window of length s1.length() + frequency counts.
*/

public class PermutationInString {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (every window, sort/compare)
    // Time Complexity: O(n * k log k) where k = |s1|
    // Auxiliary Space: O(k)
    // =====================================================

  public static boolean checkInclusionBruteForce(String s1, String s2) {

        if (s1.length() > s2.length()) {
            return false;
        }

        char[] pattern = s1.toCharArray();
        java.util.Arrays.sort(pattern);
        int k = s1.length();

        for (int start = 0; start <= s2.length() - k; start++) {
            char[] window = s2.substring(start, start + k).toCharArray();
            java.util.Arrays.sort(window);
            if (java.util.Arrays.equals(pattern, window)) {
                return true;
            }
        }

        return false;
    }


    // =====================================================
    // APPROACH 2: FIXED WINDOW + FREQUENCY (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1) — int[26]
    // =====================================================

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


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        String s1 = "ab";
        String s2 = "eidbaooo";

        System.out.println("s1 = \"" + s1 + "\", s2 = \"" + s2 + "\"");
        System.out.println("Brute:  " + checkInclusionBruteForce(s1, s2));
        System.out.println("Window: " + checkInclusion(s1, s2)); // true

        System.out.println(checkInclusion("ab", "eidboaoo")); // false
    }
}