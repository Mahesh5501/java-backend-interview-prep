package DSA.DynamicProgramming;

/*
 * Problem 02 — House Robber (LeetCode 198)

See notes/Problem_02_House_Robber.md — every approach below matches the note.
 */
public class HouseRobber {

    // APPROACH 1: BRUTE RECURSION
    // At each house: rob it (skip next) or skip — try both branches.
    // Time: O(2^n)   Extra space: O(n) stack
    public static int robBrute(int[] nums) {
        return robFrom(0, nums);
    }

    private static int robFrom(int i, int[] nums) {
        if (i >= nums.length) {
            return 0;
        }
        return Math.max(nums[i] + robFrom(i + 2, nums), robFrom(i + 1, nums));
    }

    // APPROACH 2: ROLLING DP (OPTIMAL — what we use)
    // Track best loot if previous house was skipped vs taken.
    // Time: O(n)   Extra space: O(1)
    public static int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int x : nums) {
            int cur = Math.max(prev1, prev2 + x);
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }

    public static void main(String[] args) {
        int[] nums = { 2, 7, 9, 3, 1 };
        System.out.println("Optimal: " + rob(nums));
        System.out.println("Brute: " + robBrute(nums));
    }
}