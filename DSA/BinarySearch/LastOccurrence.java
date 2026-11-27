package DSA.BinarySearch;

/*
Problem: Last Occurrence of Target

Sorted nums may contain duplicates. Return index of last occurrence of target, or -1.

Example:
  [1,2,2,2,3,4], target=2 → 3

Approach: Binary search — on match, save index and go right
Time: O(log n)   Space: O(1)
*/

public class LastOccurrence {

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
        System.out.println("Last: " + searchLast(nums, 2));
        System.out.println("Miss: " + searchLast(nums, 6));
        System.out.println("All2: " + searchLast(new int[] { 2, 2, 2, 2 }, 2));
    }
}