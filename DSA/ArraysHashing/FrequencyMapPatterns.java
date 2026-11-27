package DSA.ArraysHashing;

import java.util.HashMap;
import java.util.Map;

/**
 * =====================================================================
 * MASTER CLASS: FrequencyMapPatterns
 * =====================================================================
 * Pattern: Frequency & Count Tracking (The HashMap Pattern)
 * 
 * Core Concept: 
 * When problems ask about frequencies, counts, duplicates, or unique elements, 
 * use a HashMap to map each element to its occurrence count. 
 * - Pass 1: Build the frequency map.
 * - Pass 2: Evaluate the map or original data to find the answer.
 * =====================================================================
 */
public class FrequencyMapPatterns {

    public static void main(String[] args) {
        System.out.println("=== RUNNING FREQUENCY MAP PATTERN EXAMPLES ===");

        // 1. First Non-Repeating Character Example
        String s1 = "swiss";
        System.out.println("1. First Non-Repeating in \"" + s1 + "\": " + firstNonRepeating(s1));

        // 2. First Repeating Character Example
        String s2 = "programming";
        System.out.println("2. First Repeating in \"" + s2 + "\": " + firstRepeating(s2));

        // 3. Most Frequent Element Example
        int[] arr = {1, 3, 2, 1, 4, 1, 3, 3, 3};
        System.out.println("3. Most Frequent Element: " + mostFrequentElement(arr));

        // 4. Valid Anagram Example
        String t1 = "listen", t2 = "silent";
        System.out.println("4. Are \"" + t1 + "\" and \"" + t2 + "\" anagrams? " + areAnagrams(t1, t2));
    }

    /**
     * -----------------------------------------------------------------
     * 1. FIRST NON-REPEATING CHARACTER
     * -----------------------------------------------------------------
     * Time Complexity:  O(n) - Two passes over the string of length n.
     * Space Complexity: O(k) - Where k is the number of distinct characters.
     */
    public static char firstNonRepeating(String s) {
        if (s == null || s.isEmpty()) {
            return '\0';
        }

        Map<Character, Integer> frequencyMap = new HashMap<>();

        // Pass 1: Count frequency of each character
        for (char ch : s.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }

        // Pass 2: Traverse original string to find the first character with count == 1
        for (char ch : s.toCharArray()) {
            if (frequencyMap.get(ch) == 1) {
                return ch;
            }
        }

        return '\0'; // Return null char if none found
    }

    /**
     * -----------------------------------------------------------------
     * 2. FIRST REPEATING CHARACTER
     * -----------------------------------------------------------------
     * Time Complexity:  O(n) - Single pass (we check and build simultaneously).
     * Space Complexity: O(k) - Distinct characters stored in map.
     */
    public static char firstRepeating(String s) {
        if (s == null || s.isEmpty()) {
            return '\0';
        }

        Map<Character, Integer> frequencyMap = new HashMap<>();

        // Traverse and check immediately: the first element we see that already exists in our map (or has a count > 0) is our answer!
        for (char ch : s.toCharArray()) {
            if (frequencyMap.containsKey(ch)) {
                return ch; // Found the first character that repeats
            }
            frequencyMap.put(ch, 1);
        }

        return '\0';
    }

    /**
     * -----------------------------------------------------------------
     * 3. MOST FREQUENT ELEMENT (Element with Maximum Count)
     * -----------------------------------------------------------------
     * Time Complexity:  O(n) - One pass to build map, one pass to find max.
     * Space Complexity: O(k) - Distinct elements stored in map.
     */
    public static int mostFrequentElement(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int mostFrequent = arr[0];
        int maxCount = 0;

        // Pass 1: Build frequency map
        for (int num : arr) {
            int count = frequencyMap.getOrDefault(num, 0) + 1;
            frequencyMap.put(num, count);

            // Pass 2 (Optimized inside loop): Keep track of the highest frequency so far
            if (count > maxCount) {
                maxCount = count;
                mostFrequent = num;
            }
        }

        return mostFrequent;
    }

    /**
     * -----------------------------------------------------------------
     * 4. VALID ANAGRAM (Two strings have identical character frequencies)
     * -----------------------------------------------------------------
     * Time Complexity:  O(n) - Length of strings.
     * Space Complexity: O(k) - Distinct characters in alphabet.
     */
    public static boolean areAnagrams(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        Map<Character, Integer> frequencyMap = new HashMap<>();

        // Count characters from the first string (increment)
        for (char ch : s1.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }

        // Subtract characters using the second string
        for (char ch : s2.toCharArray()) {
            if (!frequencyMap.containsKey(ch)) {
                return false; // Character in s2 doesn't exist in s1
            }
            
            int count = frequencyMap.get(ch) - 1;
            if (count == 0) {
                frequencyMap.remove(ch); // Clean up if frequency hits zero
            } else {
                frequencyMap.put(ch, count);
            }
        }

        return frequencyMap.isEmpty(); // If map is completely empty, counts matched perfectly!
    }
}
