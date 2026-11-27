package DSA.DynamicProgramming;

/*
 * Problem 01 — Climbing Stairs (LeetCode 70)
 */
public class ClimbingStairs {

    public static int climbStairsBrute(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairsBrute(n - 1) + climbStairsBrute(n - 2);
    }

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
        System.out.println("n=5 DP: " + climbStairsDP(n));
        System.out.println("n=5 brute: " + climbStairsBrute(n));
    }
}