package DSA.BinarySearch;

/*
Problem: Koko Eating Bananas (binary search on answer)

piles[i] = bananas in pile i. h = hours to finish. Each hour eat at most k bananas from one pile.
Return minimum integer k so Koko can finish within h hours.

Example:
  piles = [3,6,7,11], h = 8  →  k = 4

See notes/Problem_08_Koko_Eating_Bananas.md — every approach below matches the note.
*/

public class KokoEatingBananas {

    private static long hoursNeeded(int[] piles, int speed) {
        long hours = 0;
        for (int pile : piles) {
            hours += (pile + speed - 1) / speed;
        }
        return hours;
    }

    // APPROACH 1: LINEAR SEARCH ON SPEED k
    // Try k = 1, 2, … until hoursNeeded(k) <= h.
    // Why: proves the feasibility predicate before speeding up with BS.
    // Time: O(n * M) where M = max pile   Extra space: O(1)
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

    // APPROACH 2: BINARY SEARCH ON ANSWER k (OPTIMAL)
    // Monotonic: if k works, any larger k works. Search smallest feasible k.
    // Why BS: predicate "can finish in h hours" is monotone over speed.
    // Time: O(n log M)   Extra space: O(1)
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