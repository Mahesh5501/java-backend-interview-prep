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

See notes/Problem_04_Group_Anagrams.md — every approach below matches the note.
*/

public class GroupAnagrams {

    // Shared helper: are two words anagrams? (used by brute force)
    private static boolean isAnagramPair(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int[] count = new int[26];
        for (int i = 0; i < a.length(); i++) {
            count[a.charAt(i) - 'a']++;
            count[b.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) {
                return false;
            }
        }
        return true;
    }

    // APPROACH 1: BRUTE FORCE — pairwise anagram checks
    // Why mention it: shows the definition; too slow as final answer.
    // For each ungrouped word, scan the rest and pull matching anagrams.
    // Time: O(n^2 * k)   Extra space: O(n * k) for output
    public static List<List<String>> groupAnagramsBrute(String[] strs) {
        boolean[] used = new boolean[strs.length];
        List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < strs.length; i++) {
            if (used[i]) {
                continue;
            }
            List<String> group = new ArrayList<>();
            group.add(strs[i]);
            used[i] = true;
            for (int j = i + 1; j < strs.length; j++) {
                if (!used[j] && isAnagramPair(strs[i], strs[j])) {
                    group.add(strs[j]);
                    used[j] = true;
                }
            }
            result.add(group);
        }
        return result;
    }

    // Helper for APPROACH 2: sorted characters as canonical key
    private static String sortedKey(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // APPROACH 2: SORTED KEY → MAP OF LISTS (clear interview default)
    // Why map: one pass groups words that share a fingerprint.
    // Time: O(n * k log k)   Extra space: O(n * k)
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : strs) {
            String key = sortedKey(word);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    // APPROACH 3: COUNT SIGNATURE KEY (faster for long words)
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

    private static void printGroups(String label, List<List<String>> groups) {
        System.out.println(label);
        for (List<String> group : groups) {
            System.out.println("  " + group);
        }
    }

    public static void main(String[] args) {
        String[] input = { "eat", "tea", "tan", "ate", "nat", "bat" };
        printGroups("Brute:", groupAnagramsBrute(input));
        printGroups("Sorted key:", groupAnagrams(input));
        printGroups("Count key:", groupAnagramsCountKey(input));
    }
}
