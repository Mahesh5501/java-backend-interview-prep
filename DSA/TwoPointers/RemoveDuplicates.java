package DSA.TwoPointers;

/*
Problem: Remove Duplicates from Sorted Array

Given a sorted integer array nums, remove the duplicates in-place so that each unique element appears only once.
Return the number of unique elements.
The order of the unique elements must be preserved.

Examples:

Input:
nums = [1, 1, 2]

Output: 2

After removing duplicates:
[1, 2, ...]

Input:
nums = [0, 0, 1, 1, 1, 2, 2, 3]

Output: 4

After removing duplicates:
[0, 1, 2, 3, ...]

Constraints:
- The array is sorted.
- Modify the array in-place.
- Do not create another array.

Expected Approach:
Think about Two Pointers.
*/


public class RemoveDuplicates {

    public static void main(String[] args) {

        int[] nums1 = {1, 1, 2};
        System.out.println(removeDuplicates(nums1)); // 2

        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3};
        System.out.println(removeDuplicates(nums2)); // 4
    }

    static int removeDuplicates(int[] nums) {
        int left =0;
        for(int right =0; right < nums.length; right++){
           if(nums[left] != nums[right]){
                left++;
                nums[left] = nums[right];
           }
        }
        return left + 1;
    }
}
