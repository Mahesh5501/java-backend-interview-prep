
package DSA.Recursion;

public class CountDigits {

    /*
    Problem: Count the digits in a number using recursion.
    Input: n = 12345, Output: 5
    Input: n = 70, Output: 2
    Input: n = 0, Output: 1
    Hint: Remove the last digit using n / 10
    */

    static int countDigits(int n) {
        if (n / 10 == 0) return 1;
        return 1 + countDigits(n / 10);
    }

    public static void main(String[] args) {
        System.out.println(countDigits(12345));
        System.out.println(countDigits(70));
        System.out.println(countDigits(0));
    }
}
