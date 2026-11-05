
package DSA.Recursion;

public class ArraySum {

    /*
    Problem: Find the sum of array elements using recursion.
    Input: arr = {1, 2, 3, 4}, Output: 10
    Input: arr = {5, 10}, Output: 15
    Input: arr = {}, Output: 0
    Hint: Add one element and recursively process the remaining array.
    */

    static int sum(int[] arr, int index) {
        if(index == arr.length) return 0;
        return arr[index] + sum(arr, index+1);
    }

    public static void main(String[] args) {
        System.out.println(sum(new int[]{1, 2, 3, 4}, 0));
        System.out.println(sum(new int[]{5, 10}, 0));
        System.out.println(sum(new int[]{}, 0));
    }
}
