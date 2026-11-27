package DSA.BinarySearch;

/*
Problem: Count Occurrences of Target

Return how many times target appears in sorted nums.

Example:
  [1,2,2,2,3,4], target=2 → 3

See notes/Problem_04_Count_Occurrences.md — every approach below matches the note.
*/

public class CountOccurrences {

    // APPROACH 1: LINEAR COUNT
    // Why: direct application of definition.
    // Time: O(n)   Extra space: O(1)
    public static int countOccurrencesBrute(int[] nums, int target) {
        int count = 0;
        for (int x : nums) {
            if (x == target) {
                count++;
            }
        }
        return count;
    }

    // APPROACH 2: FIRST + LAST BINARY SEARCH (OPTIMAL)
    // count = lastIndex - firstIndex + 1 when first != -1.
    // Why: reuses O(log n) boundary search instead of scanning the duplicate run.
    // Time: O(log n)   Extra space: O(1)
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