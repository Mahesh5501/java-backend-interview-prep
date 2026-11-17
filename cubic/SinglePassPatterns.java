package cubic;

/**
 * =====================================================================
 * MASTER CLASS: SinglePassPatterns
 * =====================================================================
 * Pattern: Single-Pass State Tracking (Greedy State Maintenance)
 * 
 * Core Concept: 
 * Whenever a problem asks for extremes (second largest, second smallest, max profit), avoid sorting or brute-force O(n^2) loops. 
 * Instead, maintain state variables and update them dynamically in O(n) time.
 * =====================================================================
 */
public class SinglePassPatterns {

    public static void main(String[] args) {
        System.out.println("=== RUNNING SINGLE-PASS PATTERN EXAMPLES ===");

        // 1. Second Largest Example
        int[] arr1 = {10, 5, 20, 8, 20};
        System.out.println("1. Second Largest = " + findSecondLargest(arr1));

        // 2. Second Smallest Example
        int[] arr2 = {10, 5, 20, 5, 8};
        System.out.println("2. Second Smallest = " + findSecondSmallest(arr2));

        // 3. Maximum Profit Example
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("3. Max Profit      = " + maxProfit(prices));

    }

    /**
     * -----------------------------------------------------------------
     * 1. FIND SECOND LARGEST DISTINCT ELEMENT
     * -----------------------------------------------------------------
     * Time Complexity:  O(n) - Single pass through the array.
     * Space Complexity: O(1) - Only uses two primitive variables.
     */
    public static int findSecondLargest(int[] arr) {
        if (arr == null || arr.length < 2) {
            return Integer.MIN_VALUE;
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int num : arr) {
            // Case 1: Found a new number strictly greater than 'largest'
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            }
            // Case 2: num is smaller than 'largest', but check if it's new second largest
            else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }

        return secondLargest;
    }

    /**
     * -----------------------------------------------------------------
     * 2. FIND SECOND SMALLEST DISTINCT ELEMENT
     * -----------------------------------------------------------------
     * Time Complexity:  O(n) - Single pass through the array.
     * Space Complexity: O(1) - Only uses two primitive variables.
     */
    public static int findSecondSmallest(int[] arr) {
        if (arr == null || arr.length < 2) {
            return Integer.MAX_VALUE;
        }

        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;

        for (int num : arr) {
            // Case 1: Found a new number strictly smaller than 'smallest'
            if (num < smallest) {
                secondSmallest = smallest;
                smallest = num;
            }
            // Case 2: Between smallest and secondSmallest, and not a duplicate
            else if (num < secondSmallest && num != smallest) {
                secondSmallest = num;
            }
        }

        return secondSmallest;
    }

    /**
     * -----------------------------------------------------------------
     * 3. MAXIMUM PROFIT (Buy and Sell Stock Once)
     * -----------------------------------------------------------------
     * Time Complexity:  O(n) - Single traversal of the price array.
     * Space Complexity: O(1) - Tracks minPrice and maxProfit constants.
     */
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            // Track the lowest price encountered so far on the left
            if (price < minPrice) {
                minPrice = price;
            } 
            // Calculate potential profit if we sold today, and keep the maximum
            else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }

        return maxProfit;
    }

}
