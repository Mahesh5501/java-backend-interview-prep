package DSA.BinarySearch;

/*
Problem: Search in Rotated Sorted Array

Originally sorted ascending, then rotated. All values unique.
Return index of target or -1.

Example:
  [4,5,6,7,0,1,2], target=0 → 4

Approach: One half [left..mid] or [mid..right] is always sorted
Time: O(log n)   Space: O(1)
*/

public class SearchRotatedArray {

    public static int searchBrute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // Left half [left..mid] is sorted
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // Right half [mid..right] is sorted
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
        System.out.println("Found: " + search(nums, 0));
        System.out.println("Miss:  " + search(nums, 3));
        System.out.println("One:   " + search(new int[] { 1 }, 1));
    }
}