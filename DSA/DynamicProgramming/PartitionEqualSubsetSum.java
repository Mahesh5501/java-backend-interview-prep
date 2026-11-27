package DSA.DynamicProgramming;

/*
 * Problem 09 — Partition Equal Subset Sum (LeetCode 416)
 */
public class PartitionEqualSubsetSum {

    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int x : nums) {
            for (int t = target; t >= x; t--) {
                dp[t] = dp[t] || dp[t - x];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] nums = { 1, 5, 11, 5 };
        System.out.println("Can partition: " + canPartition(nums));
    }
}