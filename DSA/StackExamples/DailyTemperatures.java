package DSA.StackExamples;

/*
Problem: Daily Temperatures

Given an array of daily temperatures, return an array where answer[i] is the number of days you have to wait after day i to get a warmer temperature.
If there is no future day with a warmer temperature, answer[i] = 0.
Examples:
Input:  [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]

Input:  [30,40,50,60]
Output: [1,1,1,0]

Expected Approach:
Use a monotonic decreasing stack of indices.
*/

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DailyTemperatures {
    static int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[temperatures.length];

        for(int right = temperatures.length-1; right>=0;right--){
            while(!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[right]){
                stack.pop();
            }

            if(stack.isEmpty()){
                result[right] = 0;
            }else{
                result[right] = stack.peek() - right;
            }
            stack.push(right);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{73,74,75,71,69,72,76,73}))); // [1,1,4,2,1,1,0,0]
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{30,40,50,60}))); // [1,1,1,0]
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{30,20,10}))); // [0,0,0]
    }
}
