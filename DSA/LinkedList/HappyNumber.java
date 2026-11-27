package DSA.LinkedList;

import java.util.HashSet;
import java.util.Set;

/*
Problem: Happy Number (LeetCode 202)

Each integer is a node; next = sum of squares of digits. Floyd / HashSet — same family as Linked List Cycle.

See notes/Problem_09_Happy_Number.md — every approach below matches the note.
*/

public class HappyNumber {

  /**
   * One "link" in the implicit chain: replace {@code n} with sum of squares of its decimal digits.
   *
   * <p>Example: {@code 19 → 1² + 9² = 82}
   */
  public static int nextHappy(int n) {
    int sum = 0;
    while (n > 0) {
      int digit = n % 10;
      sum += digit * digit;
      n /= 10;
    }
    return sum;
  }

  // -------------------------------------------------------------------------
  // APPROACH 1: BRUTE / HASH SET — remember every number we have seen
  // Time: O(log n) iterations (digit count per step), Space: O(log n) set size
  // In interview terms: O(k) steps where k = length of chain until repeat or 1
  // -------------------------------------------------------------------------
  public static boolean isHappyHashSet(int n) {
    if (n <= 0) {
      return false;
    }
    Set<Integer> seen = new HashSet<>();
    while (n != 1 && seen.add(n)) {
      n = nextHappy(n);
    }
    return n == 1;
  }

  // -------------------------------------------------------------------------
  // APPROACH 2: FLOYD — slow moves 1 step, fast moves 2 steps on the chain
  // Time: O(k), Space: O(1) — same intuition as hasCycle() in LinkedListCycle
  // -------------------------------------------------------------------------
  public static boolean isHappyFloyd(int n) {
    if (n <= 0) {
      return false;
    }
    int slow = n;
    int fast = n;
    // do-while: need at least one advance before comparing (slow == fast at start)
    do {
      slow = nextHappy(slow);
      fast = nextHappy(nextHappy(fast));
    } while (slow != fast);
    // Meeting point is 1 iff the number is happy (1 → 1 is the only fixed point on success path)
    return slow == 1;
  }

  /** Pretty-print a few steps of the chain for dry runs in main. */
  public static String chainPreview(int start, int maxSteps) {
    StringBuilder sb = new StringBuilder();
    int current = start;
    for (int step = 0; step <= maxSteps; step++) {
      if (step > 0) {
        sb.append(" → ");
      }
      sb.append(current);
      if (current == 1) {
        break;
      }
      current = nextHappy(current);
    }
    return sb.toString();
  }

  public static void demo(int n) {
    System.out.println("n = " + n);
    System.out.println("  Chain preview: " + chainPreview(n, 12));
    System.out.println("  HashSet:       " + isHappyHashSet(n));
    System.out.println("  Floyd:         " + isHappyFloyd(n));
    System.out.println();
  }

  public static void main(String[] args) {
    System.out.println("=== Happy Number (LeetCode 202) ===");
    System.out.println("Pattern: Fast & Slow — implicit linked list via sum of digit squares");
    System.out.println();

    demo(19); // true: 19→82→68→100→1
    demo(2); // false: enters unhappy cycle (e.g. ...→58→89→...)
    demo(1); // true
    demo(7); // true

    System.out.println("Interview script:");
    System.out.println(
        "Treat each number as a node; next = sum of squares of digits. Happy iff we reach 1.");
    System.out.println(
        "Brute: HashSet until repeat or 1. Optimal: Floyd slow+fast — meeting at 1 means happy.");
    System.out.println(
        "Same cycle-detection code shape as Linked List Cycle — only next() changes.");
  }
}