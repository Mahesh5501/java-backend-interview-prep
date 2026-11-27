package DSA.BinarySearch;

/*
Problem: Classic Binary Search

Given sorted array nums and target, return index of target or -1.

Examples:
  [1,3,5,7,9], target=7 → 3
  [1,3,5,7,9], target=4 → -1

Time: O(log n)   Space: O(1)
*/

public class BinarySearch {

    // BRUTE: linear scan
    public static int searchLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // OPTIMAL: binary search on sorted array
    public static int search(int[] nums, int target) {
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
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 3, 5, 7, 9 };
        System.out.println("Linear:  " + searchLinear(nums, 7));
        System.out.println("Binary:  " + search(nums, 7));
        System.out.println("Missing: " + search(nums, 4));
        System.out.println("First:   " + search(nums, 1));
    }
}