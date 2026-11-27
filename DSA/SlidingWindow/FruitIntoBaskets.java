package DSA.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

/*
Problem: Fruit Into Baskets (LeetCode 904)

At most 2 distinct fruit types in the window — max trees you can pick.

Examples:
  fruits = [1,2,1] → 3

See notes/Problem_07_Fruit_Into_Baskets.md — every approach below matches the note.
*/

public class FruitIntoBaskets {

    // APPROACH 1: BRUTE FORCE (every starting index)
    // Why: extend end until more than 2 distinct types.
    // Time: O(n^2)   Extra space: O(1) — map size ≤ 3 briefly
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

    // APPROACH 2: SLIDING WINDOW + MAP COUNTS (OPTIMAL)
    // Why: shrink while distinct types > 2; remove key when count hits 0.
    // Time: O(n)   Extra space: O(1) — at most 3 keys briefly
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

    public static void main(String[] args) {
        System.out.println("Brute:  " + totalFruitBruteForce(new int[] { 1, 2, 1 }));
        System.out.println("Window: " + totalFruit(new int[] { 1, 2, 1 }));
        System.out.println("Window: " + totalFruit(new int[] { 0, 1, 2, 2 }));
    }
}