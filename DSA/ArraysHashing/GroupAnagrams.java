package DSA.ArraysHashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Problem: Group Anagrams

Group strings that are anagrams of each other.

Example:
  ["eat","tea","tan","ate","nat","bat"]
  → groups like [["bat"],["nat","tan"],["ate","eat","tea"]]

See notes/Problem_04_Group_Anagrams.md for approach explanations.
*/

public class GroupAnagrams {

    // Helper for APPROACH 1: sorted characters as canonical key
    // Why sorted key: anagrams become the identical string after sort.
    private static String sortedKey(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // APPROACH 1: SORTED KEY → MAP OF LISTS (what we use)
    // Why map: one pass groups words that share a fingerprint.
    // Why not pairwise anagram checks: that is ~O(n^2 * k).
    // Alternative (follow-up): count-signature key for O(n * k) instead of O(n * k log k).
    // Time: O(n * k log k)   Extra space: O(n * k)
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : strs) {
            String key = sortedKey(word);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    // APPROACH 2: COUNT SIGNATURE KEY (faster for long words)
    // Key example for "eat": "#1#0#0#0#1#0...#1..." (26 counts)
    // Why use it: avoids sorting each word → O(n * k).
    // Time: O(n * k)   Extra space: O(n * k)
    public static List<List<String>> groupAnagramsCountKey(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : strs) {
            int[] count = new int[26];
            for (int i = 0; i < word.length(); i++) {
                count[word.charAt(i) - 'a']++;
            }
            StringBuilder key = new StringBuilder();
            for (int c : count) {
                key.append('#').append(c);
            }
            groups.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    public static void main(String[] args) {
        String[] input = { "eat", "tea", "tan", "ate", "nat", "bat" };

        System.out.println("Sorted key:");
        for (List<String> group : groupAnagrams(input)) {
            System.out.println("  " + group);
        }

        System.out.println("Count key:");
        for (List<String> group : groupAnagramsCountKey(input)) {
            System.out.println("  " + group);
        }
    }
}
