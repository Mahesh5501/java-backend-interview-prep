package DSA.SlidingWindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * =============================================================================
 * Problem 04 — Longest Substring Without Repeating Characters (LeetCode 3)
 * Pattern:    Variable window + HashSet (or HashMap for jump-left)
 * =============================================================================
 */
public class LongestSubstringWithoutRepeating {

    // -------------------------------------------------------------------------
    // Approach 1 — Brute force: test every substring
    // Time: O(n^2) or O(n^3) depending on duplicate check   Space: O(min(n,Σ))
    // -------------------------------------------------------------------------
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

    // -------------------------------------------------------------------------
    // Approach 2 — Optimal: sliding window + HashSet
    // Shrink from left while current char is already in the set
    // Time: O(n)   Space: O(min(n, alphabet size))
    // -------------------------------------------------------------------------
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

    // -------------------------------------------------------------------------
    // Approach 3 — HashMap: store last index; jump left past duplicate
    // Same O(n) time; fewer shrink steps in practice
    // -------------------------------------------------------------------------
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
        System.out.println("Brute:  " + lengthOfLongestSubstringBruteForce("abcabcbb"));
        System.out.println("Set:    " + lengthOfLongestSubstring("abcabcbb"));
        System.out.println("Map:    " + lengthOfLongestSubstringMap("abcabcbb"));
        System.out.println();

        System.out.println("\"bbbbb\"  -> " + lengthOfLongestSubstring("bbbbb"));
        System.out.println("\"pwwkew\" -> " + lengthOfLongestSubstring("pwwkew"));
        System.out.println("\"\"       -> " + lengthOfLongestSubstring(""));
    }
}