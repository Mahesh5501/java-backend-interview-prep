package DSA.BinarySearch;

/*
Problem: Search in Rotated Sorted Array

Originally sorted ascending, then rotated. All values unique.
Return index of target or -1.

Example:
  [4,5,6,7,0,1,2], target=0 → 4

See notes/Problem_05_Search_Rotated_Array.md — every approach below matches the note.
*/

public class SearchRotatedArray {

    // APPROACH 1: LINEAR SCAN
    // Why: baseline; ignores rotation structure.
    // Time: O(n)   Extra space: O(1)
    public static int searchBrute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // APPROACH 2: BINARY SEARCH ON ROTATED ARRAY (OPTIMAL)
    // One of [left..mid] or [mid..right] is always sorted; check which side holds target.
    // Why: still discard half the indices per step despite rotation.
    // Time: O(log n)   Extra space: O(1)
    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = { 4, 5, 6, 7, 0, 1, 2 };
        System.out.println("Brute: " + searchBrute(nums, 0));
        System.out.println("BS:    " + search(nums, 0));
        System.out.println("Miss:  " + search(nums, 3));
        System.out.println("One:   " + search(new int[] { 1 }, 1));
    }
}