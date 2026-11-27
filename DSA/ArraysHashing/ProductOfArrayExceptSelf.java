package DSA.ArraysHashing;

import java.util.Arrays;

/*
Problem: Product of Array Except Self

output[i] = product of all nums[j] for j != i.
No division in the interview answer. Follow-up: O(1) extra space (output does not count).

Example:
  [1,2,3,4] → [24,12,8,6]

See notes/Problem_06_Product_of_Array_Except_Self.md — every approach below matches the note.
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

    // APPROACH 2: DIVISION (educational — DO NOT use in interview)
    // Idea: totalProduct / nums[i]. Breaks with zeros; problem forbids division.
    // Included only so you can see why it is fragile.
    // Time: O(n)   Extra space: O(1) besides output
    public static int[] productExceptSelfDivision(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];
        int zeroCount = 0;
        int productWithoutZeros = 1;

        for (int x : nums) {
            if (x == 0) {
                zeroCount++;
            } else {
                productWithoutZeros *= x;
            }
        }

        for (int i = 0; i < n; i++) {
            if (zeroCount > 1) {
                out[i] = 0;
            } else if (zeroCount == 1) {
                out[i] = (nums[i] == 0) ? productWithoutZeros : 0;
            } else {
                out[i] = productWithoutZeros / nums[i];
            }
        }
        return out;
    }

    // APPROACH 3: LEFT[] AND RIGHT[] ARRAYS (clear learning version)
    // left[i] = product of everything strictly left of i
    // right[i] = product of everything strictly right of i
    // out[i] = left[i] * right[i]
    // Same idea as optimal; uses O(n) extra to make the picture obvious.
    // Time: O(n)   Extra space: O(n)
    public static int[] productExceptSelfLeftRight(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] out = new int[n];

        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }

        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }

        for (int i = 0; i < n; i++) {
            out[i] = left[i] * right[i];
        }
        return out;
    }

    // APPROACH 4: PREFIX × SUFFIX INTO OUTPUT (OPTIMAL — what we use)
    // Why: same as left/right but reuse out[] — O(1) extra besides output.
    // Pass 1: write left products into out[].
    // Pass 2: multiply right products while scanning backward.
    // Time: O(n)   Extra space: O(1) besides out[]
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
        System.out.println("Brute:     " + Arrays.toString(productExceptSelfBrute(nums)));
        System.out.println("Division:  " + Arrays.toString(productExceptSelfDivision(nums)));
        System.out.println("LeftRight: " + Arrays.toString(productExceptSelfLeftRight(nums)));
        System.out.println("Optimal:   " + Arrays.toString(productExceptSelf(nums)));

        int[] withZero = { -1, 1, 0, -3, 3 };
        System.out.println("Zeros opt: " + Arrays.toString(productExceptSelf(withZero)));
        System.out.println("Zeros div: " + Arrays.toString(productExceptSelfDivision(withZero)));
    }
}
