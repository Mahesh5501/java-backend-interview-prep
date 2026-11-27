package DSA.SlidingWindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Problem: Longest Substring Without Repeating Characters (LeetCode 3)

Return the length of the longest substring with all distinct characters.

Examples:
  s = "abcabcbb" → 3

See notes/Problem_04_Longest_Substring_Without_Repeating.md — every approach below matches the note.
*/

public class LongestSubstringWithoutRepeating {

    // APPROACH 1: BRUTE FORCE (every substring from each start)
    // Why: baseline — break inner loop on first duplicate.
    // Time: O(n^2)   Extra space: O(min(n, Σ))
    public static int lengthOfLongestSubstringBruteForce(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int best = 0;
        int n = s.length();

        for (int start = 0; start < n; start++) {
            Set<Character> seen = new HashSet<>();
            for (int end = start; end < n; end++) {
                char c = s.charAt(end);
                if (seen.contains(c)) {
                    break;
                }
                seen.add(c);
                best = Math.max(best, end - start + 1);
            }
        }
        return best;
    }

    // APPROACH 2: SLIDING WINDOW + HASHSET (OPTIMAL — DEFAULT INTERVIEW)
    // Why: shrink left until duplicate is gone; each char enters/leaves once.
    // Time: O(n)   Extra space: O(min(n, alphabet))
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int left = 0;
        int best = 0;
        Set<Character> window = new HashSet<>();

        for (int right = 0; right < s.length(); right++) {
            char incoming = s.charAt(right);

            while (window.contains(incoming)) {
                window.remove(s.charAt(left));
                left++;
            }

            window.add(incoming);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    // APPROACH 3: HASHMAP LAST INDEX — JUMP LEFT PAST DUPLICATE
    // Why: skip one-by-one shrinks — still O(n), fewer steps in practice.
    // Time: O(n)   Extra space: O(Σ)
    public static int lengthOfLongestSubstringMap(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        Map<Character, Integer> lastIndex = new HashMap<>();
        int left = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
                left = lastIndex.get(c) + 1;
            }
            lastIndex.put(c, right);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("s = \"abcabcbb\"");
        System.out.println("Brute: " + lengthOfLongestSubstringBruteForce("abcabcbb"));
        System.out.println("Set:   " + lengthOfLongestSubstring("abcabcbb"));
        System.out.println("Map:   " + lengthOfLongestSubstringMap("abcabcbb"));
        System.out.println();

        System.out.println("\"bbbbb\"  -> " + lengthOfLongestSubstring("bbbbb"));
        System.out.println("\"pwwkew\" -> " + lengthOfLongestSubstring("pwwkew"));
        System.out.println("\"\"       -> " + lengthOfLongestSubstring(""));
    }
}