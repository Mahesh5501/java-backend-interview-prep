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
            String answer = reorganizeString(sample);
            System.out.println("Input: " + sample + " -> " + answer
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