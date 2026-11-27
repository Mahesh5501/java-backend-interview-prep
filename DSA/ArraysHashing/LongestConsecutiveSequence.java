package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
Problem: Longest Consecutive Sequence

Given unsorted nums, return length of longest consecutive elements sequence.
Run in O(n) time.

Example:
  [100,4,200,1,3,2] → 4  (sequence 1,2,3,4)

Approach: HashSet; only start walk when x-1 is not in set
Time: O(n)   Space: O(n)
*/

public class LongestConsecutiveSequence {

    public static int longestConsecutiveSort(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int best = 1;
        int streak = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i] == nums[i - 1] + 1) {
                streak++;
            } else {
                streak = 1;
            }
            best = Math.max(best, streak);
        }
        return best;
    }

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
        System.out.println("Optimal:" + longestConsecutive(nums));
        System.out.println("Empty:   " + longestConsecutive(new int[] {}));
    }
}