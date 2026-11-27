package DSA.BinarySearch;

/*
Problem: First Occurrence of Target

Sorted nums may contain duplicates. Return index of first occurrence of target, or -1.

Example:
  [1,2,2,2,3,4], target=2 → 1

See notes/Problem_02_First_Occurrence.md — every approach below matches the note.
*/

public class FirstOccurrence {

    // APPROACH 1: LINEAR SCAN (first hit from the left)
    // Why: simplest baseline on sorted data.
    // Time: O(n)   Extra space: O(1)
    public static int searchFirstLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // APPROACH 2: BINARY SEARCH BIASED LEFT (OPTIMAL)
    // On match, record index and search left half for an earlier occurrence.
    // Why BS: duplicates stay contiguous in sorted array — first is leftmost in range.
    // Time: O(log n)   Extra space: O(1)
    public static int searchFirst(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int ans = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                ans = mid;
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 2, 2, 3, 4 };
        System.out.println("Linear: " + searchFirstLinear(nums, 2));
        System.out.println("First:  " + searchFirst(nums, 2));
        System.out.println("Miss:   " + searchFirst(nums, 6));
        System.out.println("Dup:    " + searchFirst(new int[] { 2, 2, 2, 2 }, 2));
    }
}