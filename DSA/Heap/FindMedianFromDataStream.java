package DSA.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/*
Problem: Find Median from Data Stream (LeetCode 295)

Support addNum(int) and findMedian() on a growing stream.

Example:
    add 1, add 2 -> median 1.5
    add 3 -> median 2

See notes/Problem_04_Find_Median_from_Data_Stream.md — every approach below matches the note.
*/

public class FindMedianFromDataStream {

  // APPROACH 1: SORTED LIST (BRUTE — educational)
  // Insert each num in sorted order; median from middle index(s).
  // Why use it: shows definition before two-heap invariant.
  // addNum: O(n)   findMedian: O(1)   Extra space: O(n)
  public static class FindMedianBrute {
    private final List<Integer> values = new ArrayList<>();

    public void addNum(int num) {
      int pos = Collections.binarySearch(values, num);
      if (pos < 0) {
        pos = -pos - 1;
      }
      values.add(pos, num);
    }

    public double findMedian() {
      int n = values.size();
      if (n % 2 == 1) {
        return values.get(n / 2);
      }
      return (values.get(n / 2 - 1) + values.get(n / 2)) / 2.0;
    }
  }

  // APPROACH 2: TWO HEAPS (OPTIMAL — what we use)
  // Max-heap for lower half, min-heap for upper half; balance sizes then median from tops.
  // Why two heaps: O(log n) add, O(1) median query.
  // addNum: O(log n)   findMedian: O(1)   Extra space: O(n)
  /** Lower half — max-heap (largest of the lows on top). */
  private final PriorityQueue<Integer> lowHalf;

  /** Upper half — min-heap (smallest of the highs on top). */
  private final PriorityQueue<Integer> highHalf;

  public FindMedianFromDataStream() {
    lowHalf = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
    highHalf = new PriorityQueue<>();
  }

  public void addNum(int num) {
    if (lowHalf.isEmpty() || num <= lowHalf.peek()) {
      lowHalf.offer(num);
    } else {
      highHalf.offer(num);
    }

    if (lowHalf.size() > highHalf.size() + 1) {
      highHalf.offer(lowHalf.poll());
    } else if (highHalf.size() > lowHalf.size()) {
      lowHalf.offer(highHalf.poll());
    }
  }

  public double findMedian() {
    if (lowHalf.size() > highHalf.size()) {
      return lowHalf.peek();
    }
    return (lowHalf.peek() + highHalf.peek()) / 2.0;
  }

  public static void main(String[] args) {
    FindMedianBrute brute = new FindMedianBrute();
    brute.addNum(1);
    brute.addNum(2);
    System.out.println("Brute median after 1,2: " + brute.findMedian());
    brute.addNum(3);
    System.out.println("Brute median after 1,2,3: " + brute.findMedian());

    FindMedianFromDataStream optimal = new FindMedianFromDataStream();
    optimal.addNum(1);
    optimal.addNum(2);
    System.out.println("Heap median after 1,2: " + optimal.findMedian());
    optimal.addNum(3);
    System.out.println("Heap median after 1,2,3: " + optimal.findMedian());
  }
}