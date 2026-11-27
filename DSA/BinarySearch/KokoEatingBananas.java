package DSA.BinarySearch;

/*
Problem: Koko Eating Bananas (binary search on answer)

piles[i] = bananas in pile i. h = hours to finish. Each hour eat at most k bananas from one pile.
Return minimum integer k so Koko can finish within h hours.

Example:
  piles = [3,6,7,11], h = 8  →  k = 4

Approach: BS on k from 1 to max(piles); feasible(k) = sum of ceil(pile/k) <= h
Time: O(n log M), M = max pile   Space: O(1)
*/

public class KokoEatingBananas {

    private static long hoursNeeded(int[] piles, int speed) {
        long hours = 0;
        for (int pile : piles) {
            hours += (pile + speed - 1) / speed;
        }
        return hours;
    }

    public static int minEatingSpeedBrute(int[] piles, int h) {
        int maxPile = 0;
        for (int p : piles) {
            maxPile = Math.max(maxPile, p);
        }
        for (int k = 1; k <= maxPile; k++) {
            if (hoursNeeded(piles, k) <= h) {
                return k;
            }
        }
        return maxPile;
    }

    public static int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = 0;
        for (int p : piles) {
            high = Math.max(high, p);
        }
        int best = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hoursNeeded(piles, mid) <= h) {
                best = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] piles = { 3, 6, 7, 11 };
        int h = 8;
        System.out.println("Brute: " + minEatingSpeedBrute(piles, h));
        System.out.println("BS:    " + minEatingSpeed(piles, h));

        int[] p2 = { 30, 11, 23, 4, 20 };
        System.out.println("BS:    " + minEatingSpeed(p2, 5));
    }
}