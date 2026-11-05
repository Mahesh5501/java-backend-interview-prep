package DSA.BinarySearch;

/*
Problem: Count Occurrences of Target

Given a sorted integer array nums that may contain duplicates, return the number of times target appears in the array.
Examples:

Input:
nums = [1, 2, 2, 2, 3, 4]
target = 2

Output: 3

Input:
nums = [1, 2, 3, 4, 5]
target = 4

Output: 1

Input:
nums = [1, 2, 3, 4, 5]
target = 6

Output: 0

Expected Approach:
Binary Search

Hint: Use First Occurrence + Last Occurrence.

Time: O(log n)
Space: O(1)
*/

public class CountOccurrences {
    public static void main(String[] args) {
        System.out.println(countOccurrences(new int[]{1, 2, 2, 2, 3, 4}, 2)); // 3
        System.out.println(countOccurrences(new int[]{1, 2, 3, 4, 5}, 4));     // 1
        System.out.println(countOccurrences(new int[]{1, 2, 3, 4, 5}, 6));     // 0
    }

    static int countOccurrences(int[] nums, int target) {
        int first = findFirst(nums, target);
        int last = findLast(nums, target);
        if(first == -1) return 0;
        return last - first +1;
    }

    static int findFirst(int[] nums, int target) {
        int left =0;
        int right = nums.length-1;
        int ans = -1;
        while(left<= right){
            int mid = left + (right - left)/2;
            if(nums[mid] == target){
                ans = mid;
                right = mid-1;
            } else if(nums[mid] > target){
                right = mid -1;
            } else{
                left = mid+1;
            }
        }
        return ans;
    }

    static int findLast(int[] nums, int target) {
        int left =0;
        int right = nums.length-1;
        int ans = -1;

        while(left<= right){
            int mid = left + (right - left)/2;
            if(nums[mid] == target){
                ans = mid;
                left = mid+1;
            } else if(nums[mid] > target){
                right = mid -1;
            } else{
                left = mid+1;
            }
        }
        return ans;
    }
}
