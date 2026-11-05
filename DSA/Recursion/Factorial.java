
package DSA.Recursion;

public class Factorial {

    /*
    Problem: Find factorial using recursion.

    Input: n = 4, Output: 24
    Input: n = 1, Output: 1
    Input: n = 0, Output: 1

    Hint: Base case + factorial(n - 1)
    */

    static int factorial(int n) {
        if(n == 0) return 1;
        return  n* (factorial(n-1));
    }

    public static void main(String[] args) {

        System.out.println("Factorial of 4: " + factorial(4));
        System.out.println("Factorial of 5: " + factorial(5));
        System.out.println("Factorial of 1: " + factorial(1));
        System.out.println("Factorial of 0: " + factorial(0));
    }
}