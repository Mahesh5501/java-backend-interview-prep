package cubic;

/**
 * Program: Find the Second Largest Distinct Element in an Array
 * Description: Demonstrates an efficient O(n) single-pass approach to find the second largest distinct number, handling duplicates safely.
 */
public class SecondLargest {

    public static void main(String[] args) {
        // Test array containing positive numbers and duplicate maximums
        int[] arr = {10, 5, 20, 8, 20};

        // Call the method to find the second largest element
        int result = findSecondLargest(arr);

        // Output the result
        if (result != Integer.MIN_VALUE) {
            System.out.println("Second largest = " + result);
        } else {
            System.out.println("No second largest element found (array may be too small or all elements are equal).");
        }
    }

    /**
     * Finds the second largest distinct element in an integer array.
     * 
     * @param arr The input array of integers
     * @return The second largest distinct integer, or Integer.MIN_VALUE if none exists
     */
    public static int findSecondLargest(int[] arr) {
        // Edge case: if array has fewer than 2 elements, a second largest doesn't exist
        if (arr == null || arr.length < 2) {
            return Integer.MIN_VALUE;
        }

        // Initialize tracking variables to the smallest possible integer value
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        // Traverse through each number in the array (Single Pass)
        for (int num : arr) {
            
            // Case 1: Found a new number strictly greater than 'largest'
            if (num > largest) {
                secondLargest = largest; // The old largest gets demoted to second largest
                largest = num; // Update largest to the new higher number
            }
            // Case 2: num is smaller than 'largest', but check if it can be 'secondLargest'
            // num > secondLargest: ensures it's bigger than our current runner-up
            // num != largest: prevents duplicates from being treated as second largest
            else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }

        return secondLargest;
    }
}

/**
 * =====================================================================
 * COMPLEXITY ANALYSIS:
 * =====================================================================
 * 
 * 1. Time Complexity: O(n)
 *    - The algorithm loops through the array of length 'n' exactly once.
 *    - Inside the loop, all comparison operations run in constant time O(1).
 *    - Thus, the total time complexity is linear, making it optimal.
 * 
 * 2. Space Complexity: O(1)
 *    - The algorithm only uses a few primitive variables ('largest', 'secondLargest') regardless of the size of the input array.
 *    - No extra data structures or recursion stacks are used, resulting in constant auxiliary space.
 * =====================================================================
 */
