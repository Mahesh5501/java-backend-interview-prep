package DSA.BinarySearch;

/*
Problem: Capacity To Ship Packages Within D Days (binary search on answer)

weights[i] = weight of package on day-order. Ship in order; each day total load <= capacity.
Return minimum capacity to ship all within days days.

Example:
  weights = [1,2,3,4,5,6,7,8,9,10], days = 5  →  15

See notes/Problem_10_Capacity_To_Ship_Packages.md — every approach below matches the note.
*/

public class CapacityToShipPackages {

    private static int daysNeeded(int[] weights, int capacity) {
        int days = 1;
        int current = 0;
        for (int w : weights) {
            if (current + w > capacity) {
                days++;
                current = 0;
            }
            current += w;
        }
        return days;
    }

    // APPROACH 1: LINEAR SEARCH ON CAPACITY
    // Try every cap from max(weights) to sum(weights).
    // Why: validates greedy day-count predicate before binary search.
    // Time: O(n * sum(weights))   Extra space: O(1)
    public static int shipWithinDaysBrute(int[] weights, int days) {
        int low = 0;
        int high = 0;
        for (int w : weights) {
            low = Math.max(low, w);
            high += w;
        }
        for (int cap = low; cap <= high; cap++) {
            if (daysNeeded(weights, cap) <= days) {
                return cap;
            }
        }
        return high;
    }

    // APPROACH 2: BINARY SEARCH ON CAPACITY (OPTIMAL)
    // Monotonic: larger capacity never needs more days than smaller capacity.
    // Why BS: find minimum feasible capacity in log(sum) steps.
    // Time: O(n log sum(weights))   Extra space: O(1)
    public static int shipWithinDays(int[] weights, int days) {
        int low = 0;
        int high = 0;
        for (int w : weights) {
            low = Math.max(low, w);
            high += w;
        }
        int best = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (daysNeeded(weights, mid) <= days) {
                best = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] w = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int days = 5;
        System.out.println("Brute: " + shipWithinDaysBrute(w, days));
        System.out.println("BS:    " + shipWithinDays(w, days));

        int[] w2 = { 3, 2, 2, 4, 1, 4 };
        System.out.println("BS:    " + shipWithinDays(w2, 3));
    }
}