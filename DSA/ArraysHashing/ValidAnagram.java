package DSA.ArraysHashing;

import java.util.Arrays;

/*
Problem: Valid Anagram

Return true if t is an anagram of s (same characters, same frequencies).

Examples:
  s = "anagram", t = "nagaram" → true
  s = "rat", t = "car"         → false

See notes/Problem_03_Valid_Anagram.md for full approach explanations.
Assumption for count approach: lowercase English letters a–z.
*/

public class ValidAnagram {

    // APPROACH 1: SORT BOTH AND COMPARE
    // Why: same multiset of chars → same sorted string.
    // Use when: quick to write, or you blank on counting.
    // Time: O(n log n)   Extra space: O(n) for char arrays
    public static boolean isAnagramSort(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    // APPROACH 2: FREQUENCY COUNT int[26] (OPTIMAL — what we use)
    // Why int[26]: alphabet is fixed lowercase a–z → O(1) extra space.
    // Why ++ on s and -- on t: leftovers cancel; any non-zero means mismatch.
    // Why not HashMap here: Map is for Unicode / unknown alphabet follow-ups.
    // Time: O(n)   Extra space: O(1)
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Count: " + isAnagram("anagram", "nagaram"));
        System.out.println("Count: " + isAnagram("rat", "car"));
        System.out.println("Sort:  " + isAnagramSort("listen", "silent"));
        System.out.println("Sort:  " + isAnagramSort("hello", "bello"));
    }
}
