package DSA.BinarySearch;

/*
Problem: Find Minimum in Rotated Sorted Array

Sorted ascending, then rotated. All elements unique.
Return the minimum element.

Examples:
  [3,4,5,1,2] → 1
  [11,13,15,17] (not rotated) → 11

See notes/Problem_06_Find_Minimum_Rotated_Array.md — every approach below matches the note.
*/

public class FindMinimumRotatedArray {

    // APPROACH 1: LINEAR MINIMUM
    // Why: baseline before exploiting rotation.
    // Time: O(n)   Extra space: O(1)
    public static int findMinLinear(int[] nums) {
        int min = nums[0];
        for (int x : nums) {
            min = Math.min(min, x);
        }
        return min;
    }

    // APPROACH 2: BINARY SEARCH ON ROTATION PIVOT (OPTIMAL)
    // Compare nums[mid] with nums[right]; shrink toward the smaller side.
    // Why: minimum is the pivot where sorted tail wraps; BS finds it in log n.
    // Time: O(log n)   Extra space: O(1)
    public static int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {
        System.out.println("Linear: " + findMinLinear(new int[] { 3, 4, 5, 1, 2 }));
        System.out.println("BS:     " + findMin(new int[] { 3, 4, 5, 1, 2 }));
        System.out.println("BS:     " + findMin(new int[] { 4, 5, 6, 7, 0, 1, 2 }));
        System.out.println("Sorted: " + findMin(new int[] { 11, 13, 15, 17 }));
    }
}