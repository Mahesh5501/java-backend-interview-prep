
package DSA.Recursion;

public class Palindrome {

    /*
    Problem: Check whether a string is a palindrome using recursion.
    Input: str = "madam", Output: true
    Input: str = "java", Output: false
    Input: str = "a", Output: true
    Hint: Compare first and last characters, then check the middle.
    */

    static boolean isPalindrome(String str) {
        if(str.length() <= 1) return true;
        if(str.charAt(0) == str.charAt(str.length()-1))
            return isPalindrome(str.substring(1,str.length()-1));
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("madam"));
        System.out.println(isPalindrome("java"));
        System.out.println(isPalindrome("a"));
    }
}
