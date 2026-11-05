package DSA.SlidingWindow;

import java.util.HashSet;
import java.util.Set;

public class LongestUniqueSubString {

   /*
Problem: Longest Substring Without Repeating Characters
Given a string s, find the length of the longest substring that contains no repeating characters.
A substring must contain consecutive characters.

Examples:
Input:  "abcabcbb"
Output: 3

Input:  "bbbbb"
Output: 1

Input:  "pwwkew"
Output: 3

Input:  "abc"
Output: 3

Input:  ""
Output: 0

Expected Approach:
Sliding Window + HashSet
*/

    public static void main(String[] args) {

        System.out.println(longestUnique("abcabcbb")); // 3
        System.out.println(longestUnique("bbbbb"));    // 1
        System.out.println(longestUnique("pwwkew"));   // 3
        System.out.println(longestUnique("abc"));      // 3
        System.out.println(longestUnique(""));         // 0
    }

    static int longestUnique(String s) {
        int left =0;
        int length=0;
        Set<Character> set = new HashSet<>();
        for(int right =0; right < s.length(); right++){
            while(set.contains(s.charAt(right))){
                set.remove(s.charAt(left));
                left++;
            }
            set.add(s.charAt(right));
            int currentLength = right - left +1;
            length = Math.max(currentLength, length);
        }
        return length;
    }
}