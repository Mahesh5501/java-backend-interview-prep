package DSA.Recursion;

public class Print1ToN {

    /*
    Problem: Print numbers from 1 to N using recursion.
    Input: n = 5, Output: 1 2 3 4 5
    Input: n = 3, Output: 1 2 3
    Input: n = 1, Output: 1
    Hint: Base case + recursive call + print
    */

    static void printNumbers(int n) {
        if (n == 0) return;
        printNumbers(n - 1);
        System.out.println(n);
    }

    public static void main(String[] args) {
        printNumbers(5);
        System.out.println();

        printNumbers(3);
        System.out.println();

        printNumbers(1);
    }
}