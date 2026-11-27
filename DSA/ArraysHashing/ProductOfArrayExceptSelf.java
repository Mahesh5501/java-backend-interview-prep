package DSA.ArraysHashing;

import java.util.Arrays;

/*
Problem: Product of Array Except Self

Return array output where output[i] = product of all nums[j] where j != i.
Solve without division. Follow-up: O(1) extra space (output does not count).

Example:
  [1,2,3,4] → [24,12,8,6]

Approach: Prefix products left-to-right, suffix right-to-left into output
Time: O(n)   Space: O(1) excluding output array
*/

public class ProductOfArrayExceptSelf {

    public static int[] productExceptSelfBrute(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];
        for (int i = 0; i < n; i++) {
            int prod = 1;
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    prod *= nums[j];
                }
            }
            out[i] = prod;
        }
        return out;
    }

    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];

        int prefix = 1;
        for (int i = 0; i < n; i++) {
            out[i] = prefix;
            prefix *= nums[i];
        }

        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            out[i] *= suffix;
            suffix *= nums[i];
        }
        return out;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4 };
        System.out.println("Brute:   " + Arrays.toString(productExceptSelfBrute(nums)));
        System.out.println("Optimal:" + Arrays.toString(productExceptSelf(nums)));
        int[] withZero = { -1, 1, 0, -3, 3 };
        System.out.println("Zeros:   " + Arrays.toString(productExceptSelf(withZero)));
    }
}