
package DSA.Recursion;

public class SumOfN {

    /*
    Problem: Find the sum of numbers from 1 to N.
    Input: n = 5, Output: 15
    Input: n = 3, Output: 6
    Input: n = 0, Output: 0
    Hint: Base case + n + sum(n - 1)
    */

    static int sum(int n) {
        if(n == 0) return 0;
        return n + sum(n - 1);
    }

    public static void main(String[] args) {
        System.out.println(sum(5));
        System.out.println(sum(3));
        System.out.println(sum(0));
    }
}
