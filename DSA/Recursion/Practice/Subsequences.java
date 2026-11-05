package DSA.Recursion.Practice;

public class Subsequences {

    /*
    Problem: Generate all subsequences of a string.
    Input: "abc"
    Output: "", "a", "b", "c", "ab", "ac", "bc", "abc"

    Input: "ab"
    Output: "", "a", "b", "ab"

    Input: "a"
    Output: "", "a"

    Hint: For each character, make two choices:
    include it or exclude it.
    */

    static void generate(String str, int index, String current) {

        if (index == str.length()) {
            System.out.println(current);
            return;
        }
        // Include current character
        generate(str, index + 1, current + str.charAt(index));
        // Exclude current character
        generate(str, index + 1, current);
    }

    public static void main(String[] args) {
        generate("abc", 0, "");
    }
}
