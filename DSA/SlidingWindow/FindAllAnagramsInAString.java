package DSA.SlidingWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Problem: Find All Anagrams in a String (LeetCode 438)
Given strings s and p, return all start indices of p's anagrams in s.

Example:
s = "cbaebabacd", p = "abc" → [0, 6]

Expected Approach:
Same fixed window + frequency match as Permutation in String — collect indices.
*/

public class FindAllAnagramsInAString {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (sort each window)
    // Time Complexity: O(n * k log k)
    // Auxiliary Space: O(k)
    // =====================================================

  public static List<Integer> findAnagramsBruteForce(String s, String p) {

        List<Integer> answer = new ArrayList<>();
        if (p.length() > s.length()) {
            return answer;
        }

        char[] pattern = p.toCharArray();
        Arrays.sort(pattern);
        int k = p.length();

        for (int start = 0; start <= s.length() - k; start++) {
            char[] window = s.substring(start, start + k).toCharArray();
            Arrays.sort(window);
            if (Arrays.equals(pattern, window)) {
                answer.add(start);
            }
        }

        return answer;
    }


    // =====================================================
    // APPROACH 2: FIXED WINDOW + FREQUENCY (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1) — int[26]
    // =====================================================

  public static List<Integer> findAnagrams(String s, String p) {

        List<Integer> answer = new ArrayList<>();
        if (p.length() > s.length()) {
            return answer;
        }

        int[] need = new int[26];
        int[] window = new int[26];

        for (int i = 0; i < p.length(); i++) {
            need[p.charAt(i) - 'a']++;
        }

        int k = p.length();

        for (int right = 0; right < s.length(); right++) {

            window[s.charAt(right) - 'a']++;

            int left = right - k + 1;
            if (left < 0) {
                continue;
            }

            if (arraysEqual(need, window)) {
                answer.add(left);
            }

            window[s.charAt(left) - 'a']--;
        }

        return answer;
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

        String s = "cbaebabacd";
        String p = "abc";

        System.out.println("s = \"" + s + "\", p = \"" + p + "\"");
        System.out.println("Brute:  " + findAnagramsBruteForce(s, p));
        System.out.println("Window: " + findAnagrams(s, p)); // [0, 6]
    }
}