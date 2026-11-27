package DSA.SlidingWindow;

/*
Problem: Longest Repeating Character Replacement (LeetCode 424)

At most k character changes — return longest substring that can become all one letter.

Examples:
  s = "AABABBA", k = 1 → 4

See notes/Problem_05_Longest_Repeating_Character_Replacement.md — every approach below matches the note.
*/

public class LongestRepeatingCharacterReplacement {

    // APPROACH 1: BRUTE FORCE (every start, extend while len - maxFreq <= k)
    // Why: direct validity check per window.
    // Time: O(n^2)   Extra space: O(1) — int[26] per inner stretch
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

    // APPROACH 2: SLIDING WINDOW + int[26] FREQUENCIES (OPTIMAL)
    // Why: invalid when windowLen - maxFreq > k → shrink with while from left.
    // Time: O(n)   Extra space: O(1)
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
        System.out.println("Window:  " + characterReplacement("AABABBA", 1));
        System.out.println();

        System.out.println("s = \"ABAB\", k = 2 -> " + characterReplacement("ABAB", 2));
        System.out.println("s = \"AAAA\", k = 0 -> " + characterReplacement("AAAA", 0));
    }
}