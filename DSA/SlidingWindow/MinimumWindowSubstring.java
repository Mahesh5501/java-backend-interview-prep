package DSA.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

/*
Problem: Minimum Window Substring (LeetCode 76)

Return smallest substring of s containing every char of t (with counts).

Examples:
  s = "ADOBECODEBANC", t = "ABC" → "BANC"

See notes/Problem_11_Minimum_Window_Substring.md — every approach below matches the note.
*/

public class MinimumWindowSubstring {

    // APPROACH 1: BRUTE FORCE (every start, grow until cover t)
    // Why: baseline — checks coverage per window.
    // Time: O(n^2 * |t|) typical   Extra space: O(|t|)
    public static String minWindowBruteForce(String s, String t) {
        if (s == null || t == null || s.isEmpty() || t.isEmpty()) {
            return "";
        }
        if (t.length() > s.length()) {
            return "";
        }

        Map<Character, Integer> need = buildNeed(t);
        int bestLen = Integer.MAX_VALUE;
        int bestStart = 0;
        boolean found = false;

        for (int start = 0; start < s.length(); start++) {
            Map<Character, Integer> window = new HashMap<>();
            int matchedTypes = 0;

            for (int end = start; end < s.length(); end++) {
                char c = s.charAt(end);
                window.put(c, window.getOrDefault(c, 0) + 1);

                if (need.containsKey(c)
                        && window.get(c).intValue() == need.get(c).intValue()) {
                    matchedTypes++;
                }

                if (matchedTypes == need.size() && covers(window, need)) {
                    int len = end - start + 1;
                    if (len < bestLen) {
                        bestLen = len;
                        bestStart = start;
                        found = true;
                    }
                    break;
                }
            }
        }

        return found ? s.substring(bestStart, bestStart + bestLen) : "";
    }

    private static boolean covers(Map<Character, Integer> window, Map<Character, Integer> need) {
        for (Map.Entry<Character, Integer> entry : need.entrySet()) {
            if (window.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    private static Map<Character, Integer> buildNeed(String t) {
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        return need;
    }

    // APPROACH 2: SLIDING WINDOW + NEED / FORMED (OPTIMAL)
    // Why: formed tracks satisfied distinct chars; shrink while valid for min length.
    // Time: O(|s| + |t|)   Extra space: O(|t|)
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.isEmpty() || t.isEmpty()) {
            return "";
        }
        if (t.length() > s.length()) {
            return "";
        }

        Map<Character, Integer> need = buildNeed(t);
        Map<Character, Integer> window = new HashMap<>();

        int required = need.size();
        int formed = 0;

        int left = 0;
        int bestLen = Integer.MAX_VALUE;
        int bestLeft = 0;

        for (int right = 0; right < s.length(); right++) {
            char incoming = s.charAt(right);
            window.put(incoming, window.getOrDefault(incoming, 0) + 1);

            if (need.containsKey(incoming)
                    && window.get(incoming).intValue() == need.get(incoming).intValue()) {
                formed++;
            }

            while (formed == required) {
                int windowLen = right - left + 1;
                if (windowLen < bestLen) {
                    bestLen = windowLen;
                    bestLeft = left;
                }

                char outgoing = s.charAt(left);
                window.put(outgoing, window.get(outgoing) - 1);
                if (need.containsKey(outgoing)
                        && window.get(outgoing).intValue() < need.get(outgoing).intValue()) {
                    formed--;
                }
                left++;
            }
        }

        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestLeft, bestLeft + bestLen);
    }

    public static void main(String[] args) {
        System.out.println("Brute:  " + minWindowBruteForce("ADOBECODEBANC", "ABC"));
        System.out.println("Window: " + minWindow("ADOBECODEBANC", "ABC"));
        System.out.println("Window: " + minWindow("a", "a"));
        System.out.println("Window: " + minWindow("a", "aa"));
    }
}