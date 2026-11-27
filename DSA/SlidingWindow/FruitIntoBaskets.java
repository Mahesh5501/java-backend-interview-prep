package DSA.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

/*
Problem: Fruit Into Baskets (LeetCode 904)
You have two baskets. Each basket can hold only one type of fruit.
Pick one fruit from every tree (starting from any tree), moving to the right.
You must stop when you hit a third distinct type.

Return the maximum number of fruits you can pick.

Example:
fruits = [1,2,1] → 3
fruits = [0,1,2,2] → 3

Expected Approach:
Variable sliding window — at most 2 distinct types (HashMap counts).
*/

public class FruitIntoBaskets {

    // =====================================================
    // APPROACH 1: BRUTE FORCE (every starting index)
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1) types tracked per inner loop
    // =====================================================

  public static int totalFruitBruteForce(int[] fruits) {

        int best = 0;

        for (int start = 0; start < fruits.length; start++) {
            Map<Integer, Integer> types = new HashMap<>();
            for (int end = start; end < fruits.length; end++) {
                types.put(fruits[end], types.getOrDefault(fruits[end], 0) + 1);
                if (types.size() > 2) {
                    break;
                }
                best = Math.max(best, end - start + 1);
            }
        }

        return best;
    }


    // =====================================================
    // APPROACH 2: SLIDING WINDOW + MAP (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1) — at most 3 keys in map briefly
    // =====================================================

  public static int totalFruit(int[] fruits) {

        Map<Integer, Integer> count = new HashMap<>();
        int left = 0;
        int best = 0;

        for (int right = 0; right < fruits.length; right++) {

            int type = fruits[right];
            count.put(type, count.getOrDefault(type, 0) + 1);

            while (count.size() > 2) {
                int leftType = fruits[left];
                count.put(leftType, count.get(leftType) - 1);
                if (count.get(leftType) == 0) {
                    count.remove(leftType);
                }
                left++;
            }

            best = Math.max(best, right - left + 1);
        }

        return best;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        System.out.println(totalFruit(new int[] { 1, 2, 1 }));     // 3
        System.out.println(totalFruit(new int[] { 0, 1, 2, 2 }));  // 3
        System.out.println(totalFruitBruteForce(new int[] { 1, 2, 1 })); // 3
    }
}