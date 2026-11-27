package DSA.BinarySearch;

/*
Problem: Last Occurrence of Target

Sorted nums may contain duplicates. Return index of last occurrence of target, or -1.

Example:
  [1,2,2,2,3,4], target=2 → 3

See notes/Problem_03_Last_Occurrence.md — every approach below matches the note.
*/

public class LastOccurrence {

    // APPROACH 1: LINEAR SCAN (last hit from the right)
    // Why: baseline before biased binary search.
    // Time: O(n)   Extra space: O(1)
    public static int searchLastLinear(int[] nums, int target) {
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // APPROACH 2: BINARY SEARCH BIASED RIGHT (OPTIMAL)
    // On match, record index and search right half for a later occurrence.
    // Why BS: last duplicate is the rightmost index where nums[mid] == target.
    // Time: O(log n)   Extra space: O(1)
    public static int searchLast(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int ans = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                ans = mid;
                left = mid + 1;
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
        System.out.println("Linear: " + searchLastLinear(nums, 2));
        System.out.println("Last:   " + searchLast(nums, 2));
        System.out.println("Miss:   " + searchLast(nums, 6));
        System.out.println("All2:   " + searchLast(new int[] { 2, 2, 2, 2 }, 2));
    }
}