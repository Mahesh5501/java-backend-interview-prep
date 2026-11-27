package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Problem: Contains Duplicate

Return true if any value appears at least twice; otherwise false.

Examples:
  [1, 2, 3, 1]     → true
  [1, 2, 3, 4]     → false

See notes/Problem_02_Contains_Duplicate.md — every approach below matches the note.
*/

public class ContainsDuplicate {

    // APPROACH 1: BRUTE FORCE — nested scan
    // Why: for each i, scan later j looking for nums[i] == nums[j].
    // Use when: tiny arrays / explain first in interview.
    // Time: O(n^2)   Extra space: O(1)
    public static boolean containsDuplicateBrute(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // APPROACH 2: SORT + COMPARE NEIGHBORS
    // Why: after sorting, duplicates sit next to each other.
    // Use when: interviewer wants less memory and allows mutating / copying.
    // Time: O(n log n)   Extra space: O(n) here (we copy to keep original intact)
    public static boolean containsDuplicateSort(int[] nums) {
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i] == copy[i - 1]) {
                return true;
            }
        }
        return false;
    }

    // APPROACH 3: HASHSET (OPTIMAL — what we use in interviews)
    // Why Set: we only ask "have I seen this value before?" — no counts needed.
    // seen.add(x) returns false when x was already present.
    // Time: O(n) average   Extra space: O(n)
    public static boolean containsDuplicateSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums) {
            if (!seen.add(x)) {
                return true;
            }
        }
        return false;
    }

    // APPROACH 4: FREQUENCY MAP
    // Why it works: count++ and stop when count > 1.
    // Why we usually skip it here: Set already answers presence with less noise.
    // Use when: problem later asks for counts / top frequent / etc.
    // Time: O(n)   Extra space: O(n)
    public static boolean containsDuplicateFreq(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            int count = freq.getOrDefault(x, 0) + 1;
            if (count > 1) {
                return true;
            }
            freq.put(x, count);
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 1 };
        int[] b = { 1, 2, 3, 4 };

        System.out.println("Brute: " + containsDuplicateBrute(a) + " / " + containsDuplicateBrute(b));
        System.out.println("Sort:  " + containsDuplicateSort(a) + " / " + containsDuplicateSort(b));
        System.out.println("Set:   " + containsDuplicateSet(a) + " / " + containsDuplicateSet(b));
        System.out.println("Freq:  " + containsDuplicateFreq(a) + " / " + containsDuplicateFreq(b));
    }
}
