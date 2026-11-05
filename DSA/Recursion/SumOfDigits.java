package DSA.Recursion;

public class SumOfDigits {

    /*
    Problem: Find the sum of digits using recursion.
    Input: n = 123, Output: 6
    Input: n = 405, Output: 9
    Input: n = 7, Output: 7
    Hint: Use n % 10 and n / 10
    */

    static int sumOfDigits(int n) {
        int digit = n % 10;
        if (n == 0) return 0;
        return digit += sumOfDigits(n / 10);
    }

    public static void main(String[] args) {
        System.out.println(sumOfDigits(123));
        System.out.println(sumOfDigits(405));
        System.out.println(sumOfDigits(7));
    }
}