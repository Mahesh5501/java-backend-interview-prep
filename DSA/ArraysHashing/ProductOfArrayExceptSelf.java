package DSA.ArraysHashing;

import java.util.Arrays;

/*
Problem: Product of Array Except Self

output[i] = product of all nums[j] for j != i.
No division. Follow-up: O(1) extra space (output does not count).

Example:
  [1,2,3,4] → [24,12,8,6]

See notes/Problem_06_Product_of_Array_Except_Self.md for approach explanations.
*/

public class ProductOfArrayExceptSelf {

    // APPROACH 1: BRUTE FORCE
    // For each i, multiply every j != i.
    // Why mention it: correct baseline; shows the definition clearly.
    // Time: O(n^2)   Extra space: O(1) besides output
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

    // APPROACH 2: PREFIX × SUFFIX INTO OUTPUT (OPTIMAL — what we use)
    // Why: output[i] = (product left of i) * (product right of i) — no division.
    // Why not division: forbidden, and zeros need ugly special cases.
    // Pass 1: write left products into out[].
    // Pass 2: multiply right products while scanning backward.
    // Time: O(n)   Extra space: O(1) besides out[]
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];

        int prefix = 1;
        for (int i = 0; i < n; i++) {
            out[i] = prefix;   // product of everything strictly left of i
            prefix *= nums[i];
        }

        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            out[i] *= suffix;  // multiply by product of everything strictly right of i
            suffix *= nums[i];
        }
        return out;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4 };
        System.out.println("Brute:   " + Arrays.toString(productExceptSelfBrute(nums)));
        System.out.println("Optimal: " + Arrays.toString(productExceptSelf(nums)));

        int[] withZero = { -1, 1, 0, -3, 3 };
        System.out.println("Zeros:   " + Arrays.toString(productExceptSelf(withZero)));
    }
}
