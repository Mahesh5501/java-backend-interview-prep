package DSA.SlidingWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Problem: Find All Anagrams in a String (LeetCode 438)

Return all start indices where a window of s is an anagram of p.

Examples:
  s = "cbaebabacd", p = "abc" → [0, 6]

See notes/Problem_09_Find_All_Anagrams.md — every approach below matches the note.
*/

public class FindAllAnagramsInAString {

    // APPROACH 1: BRUTE FORCE (sort each window)
    // Why: same as Permutation in String — collect all matching starts.
    // Time: O(n * k log k)   Extra space: O(k)
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

    // APPROACH 2: FIXED WINDOW + int[26] FREQUENCY (OPTIMAL)
    // Why: on match, record window left index; slide counts in O(1).
    // Time: O(n)   Extra space: O(1)
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

    public static void main(String[] args) {
        String s = "cbaebabacd";
        String p = "abc";

        System.out.println("s = \"" + s + "\", p = \"" + p + "\"");
        System.out.println("Brute:  " + findAnagramsBruteForce(s, p));
        System.out.println("Window: " + findAnagrams(s, p));
    }
}