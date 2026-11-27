package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: Reorganize String (LeetCode 767)

Rearrange so no two adjacent letters are the same.
Return any valid string or "" if impossible.

Example:
    "aab" -> "aba"
    "aaab" -> ""

See notes/Problem_08_Reorganize_String.md — every approach below matches the note.
*/

public class ReorganizeString {

    static class LetterCount {
        char letter;
        int count;

        LetterCount(char letter, int count) {
            this.letter = letter;
            this.count = count;
        }
    }

    // APPROACH 1: GREEDY WITH SORTED FREQUENCIES (BRUTE)
    // Each step picks two largest remaining counts via array sort — no heap.
    // Why use it: same greedy as heap, easier to trace on paper.
    // Time: O(n · 26 log 26)   Extra space: O(26)
    public static String reorganizeStringBrute(String s) {
        int[] frequency = new int[26];
        for (char ch : s.toCharArray()) {
            frequency[ch - 'a']++;
        }
        int maxCount = 0;
        for (int count : frequency) {
            maxCount = Math.max(maxCount, count);
        }
        if (maxCount > (s.length() + 1) / 2) {
            return "";
        }
        StringBuilder result = new StringBuilder(s.length());
        while (result.length() < s.length()) {
            int[] order = new int[26];
            for (int i = 0; i < 26; i++) {
                order[i] = i;
            }
            Arrays.sort(order, (a, b) -> Integer.compare(frequency[b], frequency[a]));
            int picked = 0;
            for (int i = 0; i < 26 && picked < 2; i++) {
                int idx = order[i];
                if (frequency[idx] == 0) {
                    continue;
                }
                if (picked == 1 && result.charAt(result.length() - 1) == ('a' + idx)) {
                    continue;
                }
                result.append((char) ('a' + idx));
                frequency[idx]--;
                picked++;
            }
            if (picked == 0) {
                return "";
            }
        }
        return result.toString();
    }

    // APPROACH 2: MAX-HEAP (OPTIMAL — what we use)
    // Always place two largest frequencies alternately.
    // Why heap: O(n log 26) without repeated full sorts.
    // Time: O(n log 26)   Extra space: O(26)
    public static String reorganizeString(String s) {
        int[] frequency = new int[26];
        for (char ch : s.toCharArray()) {
            frequency[ch - 'a']++;
        }

        int maxCount = 0;
        for (int count : frequency) {
            maxCount = Math.max(maxCount, count);
        }
        if (maxCount > (s.length() + 1) / 2) {
            return "";
        }

        PriorityQueue<LetterCount> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.count, a.count));

        for (int i = 0; i < 26; i++) {
            if (frequency[i] > 0) {
                maxHeap.offer(new LetterCount((char) ('a' + i), frequency[i]));
            }
        }

        StringBuilder result = new StringBuilder();

        while (!maxHeap.isEmpty()) {
            LetterCount first = maxHeap.poll();
            result.append(first.letter);

            if (!maxHeap.isEmpty()) {
                LetterCount second = maxHeap.poll();
                result.append(second.letter);
                second.count--;
                if (second.count > 0) {
                    maxHeap.offer(second);
                }
            }

            first.count--;
            if (first.count > 0) {
                maxHeap.offer(first);
            }
        }

        return result.length() == s.length() ? result.toString() : "";
    }

    public static void main(String[] args) {
        String[] samples = { "aab", "aaab", "vvvlo", "aabbcc" };
        for (String sample : samples) {
            String brute = reorganizeStringBrute(sample);
            String answer = reorganizeString(sample);
            System.out.println("Brute " + sample + " -> " + brute);
            System.out.println("Heap  " + sample + " -> " + answer
                    + " (valid: " + isValidReorganization(sample, answer) + ")");
        }
    }

    private static boolean isValidReorganization(String original, String result) {
        if (result.isEmpty() && !original.isEmpty() && original.chars().distinct().count() == 1) {
            return true;
        }
        if (result.length() != original.length()) {
            return result.isEmpty();
        }
        for (int i = 1; i < result.length(); i++) {
            if (result.charAt(i) == result.charAt(i - 1)) {
                return false;
            }
        }
        char[] a = original.toCharArray();
        char[] b = result.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }
}