
package DSA.Recursion;

public class ReverseString {

    /*
    Problem: Reverse a string using recursion.
    Input: str = "java", Output: "avaj"
    Input: str = "abc", Output: "cba"
    Input: str = "a", Output: "a"
    Hint: Last character + reverse the remaining string
    */

    static String reverse(String str) {
        if(str.length() == 0) return "";
        String result = String.valueOf(str.charAt(str.length()-1));
        return result + reverse(str.substring(0,str.length()-1));
    }

    public static void main(String[] args) {
        System.out.println(reverse("java"));
        System.out.println(reverse("abc"));
        System.out.println(reverse("a"));
    }
}
