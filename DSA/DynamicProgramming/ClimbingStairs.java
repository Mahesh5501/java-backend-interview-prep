package DSA.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/*
 * Problem 01 — Climbing Stairs (LeetCode 70)

See notes/Problem_01_Climbing_Stairs.md — every approach below matches the note.
 */
public class ClimbingStairs {

    // APPROACH 1: BRUTE RECURSION
    // ways(n) = ways(n-1) + ways(n-2); exponential recomputation.
    // Why use it: establishes recurrence before DP.
    // Time: O(2^n)   Extra space: O(n) stack
    public static int climbStairsBrute(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairsBrute(n - 1) + climbStairsBrute(n - 2);
    }

    // APPROACH 2: TOP-DOWN MEMOIZATION
    // Cache each subproblem once — same recurrence, linear time.
    // Why use it: bridge from recursion to tabulation in interviews.
    // Time: O(n)   Extra space: O(n)
    public static int climbStairsMemo(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return memoSolve(n, memo);
    }

    private static int memoSolve(int n, Map<Integer, Integer> memo) {
        if (n <= 2) {
            return n;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        int ans = memoSolve(n - 1, memo) + memoSolve(n - 2, memo);
        memo.put(n, ans);
        return ans;
    }

    // APPROACH 3: BOTTOM-UP ROLLING (OPTIMAL — what we use)
    // Only previous two step counts needed.
    // Why use it: O(1) space after bases.
    // Time: O(n)   Extra space: O(1)
    public static int climbStairsDP(int n) {
        if (n <= 2) {
            return n;
        }
        int a = 1, b = 2;
        for (int i = 3; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println("n=5 brute: " + climbStairsBrute(n));
        System.out.println("n=5 memo:  " + climbStairsMemo(n));
        System.out.println("n=5 DP:    " + climbStairsDP(n));
        System.out.println("sanity n=45 DP: " + climbStairsDP(45));
    }
}