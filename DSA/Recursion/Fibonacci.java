
package DSA.Recursion;

public class Fibonacci {

    /*
    Problem: Find the Nth Fibonacci number.
    Input: n = 5, Output: 5
    Input: n = 6, Output: 8
    Input: n = 0, Output: 0
    Hint: fib(n) = fib(n-1) + fib(n-2)
    */

    static int fib(int n) {
        if(n==0) return 0;
        if(n==1) return 1;
        return fib(n-1) + fib(n-2);
    }

    public static void main(String[] args) {
        System.out.println(fib(5));
        System.out.println(fib(6));
        System.out.println(fib(0));
    }
}
