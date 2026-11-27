package DSA.BinarySearch;

/*
Problem: Search Insert Position

Sorted distinct nums, return index if target found else index where target would be inserted.

Example:
  [1,3,5,6], target=5 → 2
  [1,3,5,6], target=2 → 1

Approach: Classic binary search; when absent, left is insert position
Time: O(log n)   Space: O(1)
*/

public class SearchInsertPosition {

    public static int searchInsertLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }

    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 3, 5, 6 };
        System.out.println("Found:    " + searchInsert(nums, 5));
        System.out.println("Insert:   " + searchInsert(nums, 2));
        System.out.println("After:    " + searchInsert(nums, 7));
        System.out.println("Before:   " + searchInsert(nums, 0));
    }
}