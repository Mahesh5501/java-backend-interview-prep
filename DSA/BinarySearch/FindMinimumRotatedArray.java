package DSA.BinarySearch;

/*
Problem: Find Minimum in Rotated Sorted Array

Sorted ascending, then rotated. All elements unique.
Return the minimum element.

Examples:
  [3,4,5,1,2] → 1
  [11,13,15,17] (not rotated) → 11

Approach: Compare nums[mid] with nums[right]
Time: O(log n)   Space: O(1)
*/

public class FindMinimumRotatedArray {

    public static int findMinLinear(int[] nums) {
        int min = nums[0];
        for (int x : nums) {
            min = Math.min(min, x);
        }
        return min;
    }

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
        System.out.println(findMin(new int[] { 3, 4, 5, 1, 2 }));
        System.out.println(findMin(new int[] { 4, 5, 6, 7, 0, 1, 2 }));
        System.out.println(findMin(new int[] { 11, 13, 15, 17 }));
        System.out.println(findMin(new int[] { 5, 1, 2, 3, 4 }));
    }
}