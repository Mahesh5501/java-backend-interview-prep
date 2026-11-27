package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Problem: Valid Anagram

Return true if t is an anagram of s (same characters, same frequencies).

Examples:
  s = "anagram", t = "nagaram" → true
  s = "rat", t = "car"         → false

See notes/Problem_03_Valid_Anagram.md — every approach below matches the note.
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

    // APPROACH 2: FREQUENCY COUNT int[26] (OPTIMAL — what we use for a–z)
    // Why int[26]: alphabet is fixed lowercase a–z → O(1) extra space.
    // Why ++ on s and -- on t: leftovers cancel; any non-zero means mismatch.
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

    // APPROACH 3: HASHMAP FREQUENCY (Unicode / unknown alphabet)
    // Why Map: works when characters are not limited to a–z.
    // Count chars in s, then decrement with t; any leftover or missing → false.
    // Time: O(n)   Extra space: O(k) where k = distinct characters
    public static boolean isAnagramMap(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> freq = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            int remaining = freq.getOrDefault(c, 0) - 1;
            if (remaining < 0) {
                return false;
            }
            if (remaining == 0) {
                freq.remove(c);
            } else {
                freq.put(c, remaining);
            }
        }
        return freq.isEmpty();
    }

    public static void main(String[] args) {
        String[][] cases = {
            { "anagram", "nagaram" },
            { "rat", "car" },
            { "listen", "silent" },
            { "hello", "bello" }
        };
        for (String[] c : cases) {
            System.out.println(c[0] + " / " + c[1]
                + "  sort=" + isAnagramSort(c[0], c[1])
                + "  count26=" + isAnagram(c[0], c[1])
                + "  map=" + isAnagramMap(c[0], c[1]));
        }
    }
}
