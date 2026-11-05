
package DSA.Recursion;

public class PrintNTo1 {

    /*
    Problem: Print numbers from N to 1 using recursion.
    Input: n = 5, Output: 5 4 3 2 1
    Input: n = 3, Output: 3 2 1
    Input: n = 1, Output: 1
    Hint: Base case + recursive call + print
    */

    static void printNumbers(int n) {
        if(n == 0) return;
        System.out.println(n);
        printNumbers(n-1);
    }

    public static void main(String[] args) {
        printNumbers(5);
        printNumbers(3);
        printNumbers(1);
    }
}
