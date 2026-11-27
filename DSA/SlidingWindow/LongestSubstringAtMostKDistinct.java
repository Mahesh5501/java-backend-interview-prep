package DSA.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

/*
Problem: Longest Substring with At Most K Distinct Characters

Return length of longest substring of s with at most k distinct characters.

Examples:
  s = "eceba", k = 2 → 3

See notes/Problem_12_Longest_Substring_At_Most_K_Distinct.md — every approach below matches the note.
*/

public class LongestSubstringAtMostKDistinct {

    // APPROACH 1: BRUTE FORCE (every starting index)
    // Why: extend until distinct count > k.
    // Time: O(n^2)   Extra space: O(k)
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

    // APPROACH 2: SLIDING WINDOW + MAP COUNTS (OPTIMAL)
    // Why: shrink while map.size() > k; remove key at count 0.
    // Time: O(n)   Extra space: O(k)
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

    public static void main(String[] args) {
        System.out.println("Brute:  " + lengthAtMostKDistinctBruteForce("eceba", 2));
        System.out.println("Window: " + lengthAtMostKDistinct("eceba", 2));
        System.out.println("Window: " + lengthAtMostKDistinct("aa", 1));
        System.out.println("Window: " + lengthAtMostKDistinct("abc", 3));
    }
}