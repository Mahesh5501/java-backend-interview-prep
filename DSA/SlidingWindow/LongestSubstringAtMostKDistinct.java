package DSA.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

/*
Problem: Longest Substring with At Most K Distinct Characters
(Classic / LintCode 386 / LeetCode Premium 159)
Given a string s and an integer k, return the length of the longest
substring of s with at most k distinct characters.

Examples:
s = "eceba", k = 2 → 3 ("ece")
s = "aa", k = 1 → 2

Note: Fruit Into Baskets (Problem 07) is the same pattern with k = 2 on an array.

Expected Approach:
Variable sliding window + HashMap character counts.
*/

public class LongestSubstringAtMostKDistinct {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (every starting index)
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(k)
    // =====================================================

    public static int lengthAtMostKDistinctBruteForce(String s, int k) {
        if (s == null || s.isEmpty() || k <= 0) {
            return 0;
        }

        int best = 0;

        for (int start = 0; start < s.length(); start++) {
            Map<Character, Integer> count = new HashMap<>();
            for (int end = start; end < s.length(); end++) {
                char c = s.charAt(end);
                count.put(c, count.getOrDefault(c, 0) + 1);
                if (count.size() > k) {
                    break;
                }
                best = Math.max(best, end - start + 1);
            }
        }

        return best;
    }


    // =====================================================
    // APPROACH 2: SLIDING WINDOW + MAP (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(k) — at most k+1 keys briefly
    // =====================================================

    public static int lengthAtMostKDistinct(String s, int k) {
        if (s == null || s.isEmpty() || k <= 0) {
            return 0;
        }

        Map<Character, Integer> count = new HashMap<>();
        int left = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            char incoming = s.charAt(right);
            count.put(incoming, count.getOrDefault(incoming, 0) + 1);

            while (count.size() > k) {
                char outgoing = s.charAt(left);
                count.put(outgoing, count.get(outgoing) - 1);
                if (count.get(outgoing) == 0) {
                    count.remove(outgoing);
                }
                left++;
            }

            best = Math.max(best, right - left + 1);
        }

        return best;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        System.out.println(lengthAtMostKDistinct("eceba", 2)); // 3
        System.out.println(lengthAtMostKDistinct("aa", 1));    // 2
        System.out.println(lengthAtMostKDistinct("abc", 3));     // 3

        System.out.println(lengthAtMostKDistinctBruteForce("eceba", 2)); // 3
    }
}