package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Problem: Contains Duplicate

Return true if any value appears at least twice in nums.

Examples:
  [1,2,3,1]     → true
  [1,2,3,4]     → false
  [1,1,1,3,3,4] → true

Approaches: Sort adjacent compare | HashSet | Frequency map
Time: O(n) optimal   Space: O(n)
*/

public class ContainsDuplicate {

    public static boolean containsDuplicateSort(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsDuplicateSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums) {
            if (!seen.add(x)) {
                return true;
            }
        }
        return false;
    }

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
        System.out.println("Set:  " + containsDuplicateSet(a) + " / " + containsDuplicateSet(b));
        System.out.println("Sort: " + containsDuplicateSort(a) + " / " + containsDuplicateSort(b));
    }
}