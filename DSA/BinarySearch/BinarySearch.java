package DSA.BinarySearch;

/*
Problem: Classic Binary Search

Given sorted array nums and target, return index of target or -1.

Examples:
  [1,3,5,7,9], target=7 → 3
  [1,3,5,7,9], target=4 → -1

See notes/Problem_01_Classic_Binary_Search.md — every approach below matches the note.
*/

public class BinarySearch {

    // APPROACH 1: LINEAR SCAN
    // Walk every index until target found.
    // Why use it: baseline when array is tiny or unsorted (violates sorted assumption).
    // Time: O(n)   Extra space: O(1)
    public static int searchLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // APPROACH 2: CLASSIC BINARY SEARCH (OPTIMAL on sorted data)
    // Halve search range using nums[mid] vs target; left <= right while invariant holds.
    // Why BS: sorted order lets us discard half the indices each step.
    // Time: O(log n)   Extra space: O(1)
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