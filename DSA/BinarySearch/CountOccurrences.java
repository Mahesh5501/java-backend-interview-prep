package DSA.BinarySearch;

/*
Problem: Count Occurrences of Target

Return how many times target appears in sorted nums.

Example:
  [1,2,2,2,3,4], target=2 → 3

Approach: first + last index, count = last - first + 1
Time: O(log n)   Space: O(1)
*/

public class CountOccurrences {

    public static int countOccurrencesBrute(int[] nums, int target) {
        int count = 0;
        for (int x : nums) {
            if (x == target) {
                count++;
            }
        }
        return count;
    }

    public static int countOccurrences(int[] nums, int target) {
        int first = FirstOccurrence.searchFirst(nums, target);
        if (first == -1) {
            return 0;
        }
        int last = LastOccurrence.searchLast(nums, target);
        return last - first + 1;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 2, 2, 3, 4 };
        System.out.println("Brute: " + countOccurrencesBrute(nums, 2));
        System.out.println("BS:    " + countOccurrences(nums, 2));
        System.out.println("Zero:  " + countOccurrences(nums, 6));
    }
}