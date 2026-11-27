package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
Problem: Longest Consecutive Sequence

Return length of the longest run of consecutive values in an unsorted array.
Target: O(n) time.

Example:
  [100, 4, 200, 1, 3, 2] → 4   (sequence 1,2,3,4)

See notes/Problem_07_Longest_Consecutive_Sequence.md for approach explanations.
*/

public class LongestConsecutiveSequence {

    // APPROACH 1: SORT + SCAN STREAKS
    // Why: after sorting, consecutive values become neighbors.
    // Use when: O(n) not required; easy to explain.
    // Time: O(n log n)   Extra space: O(1) / O(n) depending on sort
    public static int longestConsecutiveSort(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);

        int best = 1;
        int streak = 1;
        for (int i = 1; i < copy.length; i++) {
            if (copy[i] == copy[i - 1]) {
                continue; // duplicates do not extend the streak
            }
            if (copy[i] == copy[i - 1] + 1) {
                streak++;
            } else {
                streak = 1;
            }
            best = Math.max(best, streak);
        }
        return best;
    }

    // APPROACH 2: HASHSET + ONLY START AT SEQUENCE HEADS (OPTIMAL — what we use)
    // Why Set: O(1) average contains checks.
    // Why skip when (x-1) exists: only begin a walk at the start of a chain.
    // Why that makes O(n): each number is walked over a constant number of times total.
    // Without the (x-1) guard, forward walks from every x can become O(n^2).
    // Time: O(n) average   Extra space: O(n)
    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) {
            set.add(x);
        }

        int best = 0;
        for (int x : set) {
            if (set.contains(x - 1)) {
                continue;
            }
            int len = 1;
            while (set.contains(x + len)) {
                len++;
            }
            best = Math.max(best, len);
        }
        return best;
    }

    public static void main(String[] args) {
        int[] nums = { 100, 4, 200, 1, 3, 2 };
        System.out.println("Sort:    " + longestConsecutiveSort(nums));
        System.out.println("Optimal: " + longestConsecutive(nums));
        System.out.println("Empty:   " + longestConsecutive(new int[] {}));
    }
}
