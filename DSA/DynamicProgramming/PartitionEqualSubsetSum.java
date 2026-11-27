package DSA.DynamicProgramming;

/*
 * Problem 09 — Partition Equal Subset Sum (LeetCode 416)

See notes/Problem_09_Partition_Equal_Subset_Sum.md — every approach below matches the note.
 */
public class PartitionEqualSubsetSum {

    // APPROACH 1: BRUTE — try all subsets
    // Time: O(2^n)   Extra space: O(n)
    public static boolean canPartitionBrute(int[] nums) {
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        if (sum % 2 != 0) {
            return false;
        }
        return subsetBrute(0, 0, sum / 2, nums);
    }

    private static boolean subsetBrute(int idx, int current, int target, int[] nums) {
        if (current == target) {
            return true;
        }
        if (current > target || idx == nums.length) {
            return false;
        }
        return subsetBrute(idx + 1, current + nums[idx], target, nums)
                || subsetBrute(idx + 1, current, target, nums);
    }

    // APPROACH 2: 1D SUBSET DP (OPTIMAL — what we use)
    // dp[t] = can we reach sum t?
    // Time: O(n·target)   Extra space: O(target)
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
        System.out.println("Brute: " + canPartitionBrute(nums));
        System.out.println("DP:    " + canPartition(nums));
    }
}