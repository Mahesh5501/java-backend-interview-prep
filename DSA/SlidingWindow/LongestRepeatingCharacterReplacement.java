package DSA.SlidingWindow;

/*
 * =============================================================================
 * Problem 05 — Longest Repeating Character Replacement (LeetCode 424)
 * Pattern:    Variable window + frequency counts
 * Valid when: windowLength - maxFrequency <= k
 * =============================================================================
 */
public class LongestRepeatingCharacterReplacement {

    // -------------------------------------------------------------------------
    // Approach 1 — Brute force: try every substring, count replacements needed
    // Time: O(n^2)   Space: O(1) for count array per window
    // -------------------------------------------------------------------------
    public static int characterReplacementBruteForce(String s, int k) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int best = 0;
        int n = s.length();

        for (int start = 0; start < n; start++) {
            int[] freq = new int[26];
            int maxFreq = 0;

            for (int end = start; end < n; end++) {
                freq[s.charAt(end) - 'A']++;
                maxFreq = Math.max(maxFreq, freq[s.charAt(end) - 'A']);
                int windowLen = end - start + 1;
                int replacements = windowLen - maxFreq;

                if (replacements <= k) {
                    best = Math.max(best, windowLen);
                } else {
                    break;
                }
            }
        }
        return best;
    }

    // -------------------------------------------------------------------------
    // Approach 2 — Optimal: sliding window with int[26] frequencies
    // Time: O(n)   Space: O(1) — fixed 26 slots
    // -------------------------------------------------------------------------
    public static int characterReplacement(String s, int k) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int[] freq = new int[26];
        int left = 0;
        int maxFreq = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            int idx = s.charAt(right) - 'A';
            freq[idx]++;
            maxFreq = Math.max(maxFreq, freq[idx]);

            int windowLen = right - left + 1;
            int replacementsNeeded = windowLen - maxFreq;

            while (replacementsNeeded > k) {
                int leaveIdx = s.charAt(left) - 'A';
                freq[leaveIdx]--;
                left++;
                windowLen = right - left + 1;
                replacementsNeeded = windowLen - maxFreq;
            }

            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("s = \"AABABBA\", k = 1");
        System.out.println("Brute:   " + characterReplacementBruteForce("AABABBA", 1));
        System.out.println("Optimal: " + characterReplacement("AABABBA", 1));
        System.out.println();

        System.out.println("s = \"ABAB\", k = 2 -> " + characterReplacement("ABAB", 2));
        System.out.println("s = \"AAAA\", k = 0 -> " + characterReplacement("AAAA", 0));
    }
}