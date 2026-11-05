
package DSA.Recursion;

public class PowerofNumber {

    /*
    Problem: Calculate x raised to power n using recursion.
    Input: x = 2, n = 3, Output: 8
    Input: x = 5, n = 2, Output: 25
    Input: x = 7, n = 0, Output: 1
    Hint: power(x, n) = x * power(x, n - 1)
    */

    static int power(int x, int n) {
        if(n==1) return x;
        if(n==0) return 1;
        return x*power(x, n-1);
    }

    public static void main(String[] args) {
        System.out.println(power(2, 3));
        System.out.println(power(5, 2));
        System.out.println(power(7, 0));
    }
}
