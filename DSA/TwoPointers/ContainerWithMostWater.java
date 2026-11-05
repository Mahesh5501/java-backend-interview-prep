package DSA.TwoPointers;

/*
Problem: Container With Most Water

Given an integer array height where height[i] represents the height of a vertical line at index i.
Choose two lines that together with the x-axis form a container. Return the maximum amount of water the container can store.
The amount of water is: width * minimum(leftHeight, rightHeight)

Examples:
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49

Explanation:
Using heights 8 and 7:
width = 7
minimum height = 7
water = 7 * 7 = 49

Input: height = [1,1]
Output: 1

Expected Approach:
Two Pointers
Time: O(n)
Space: O(1)
*/

public class ContainerWithMostWater {

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7})); // 49
        System.out.println(maxArea(new int[]{1, 1})); // 1
    }

    static int maxArea(int[] height) {

        int left =0;
        int right = height.length -1;
        int maxWater = 0;

        while(left < right){
            int currentWater = (right - left) * Math.min(height[left], height[right]);
            maxWater = Math.max(currentWater, maxWater);
            if(height[left] > height[right]){
                right--;
            } else{
                left++;
            }
        }
        return maxWater;
    }
}
