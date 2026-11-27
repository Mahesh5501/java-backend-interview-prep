package DSA.BinarySearch;

/*
Problem: Find Peak Element

A peak is nums[i] > nums[i-1] and nums[i] > nums[i+1] (ends compare with one neighbor).
Return any peak index.

Example:
  [1,2,3,1] → 2 (value 3)

See notes/Problem_09_Find_Peak_Element.md — every approach below matches the note.
*/

public class FindPeakElement {

    // APPROACH 1: LINEAR SCAN FOR ANY PEAK
    // Why: baseline; checks local neighbors for every index.
    // Time: O(n)   Extra space: O(1)
    public static int findPeakLinear(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int left = (i == 0) ? Integer.MIN_VALUE : nums[i - 1];
            int right = (i == nums.length - 1) ? Integer.MIN_VALUE : nums[i + 1];
            if (nums[i] > left && nums[i] > right) {
                return i;
            }
        }
        return -1;
    }

    // APPROACH 2: BINARY SEARCH ON ASCENT (OPTIMAL)
    // If nums[mid] < nums[mid+1], peak lies right; else left (including mid).
    // Why BS: guarantees a peak exists; monotonic "uphill" directs search.
    // Time: O(log n)   Extra space: O(1)
    public static int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 1 };
        System.out.println("Linear: " + findPeakLinear(a));
        System.out.println("BS:     " + findPeakElement(a));
        int[] b = { 1, 2, 1, 3, 5, 6, 4 };
        System.out.println("Linear: " + findPeakLinear(b));
        System.out.println("BS:     " + findPeakElement(b));
    }
}