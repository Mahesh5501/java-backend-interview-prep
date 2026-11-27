package DSA.DynamicProgramming;

/*
 * Problem 02 — House Robber (LeetCode 198)
 */
public class HouseRobber {

    public static int robBrute(int[] nums) {
        return robFrom(0, nums);
    }

    private static int robFrom(int i, int[] nums) {
        if (i >= nums.length) {
            return 0;
        }
        return Math.max(nums[i] + robFrom(i + 2, nums), robFrom(i + 1, nums));
    }

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