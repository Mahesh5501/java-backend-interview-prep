package DSA.Heap;

import java.util.PriorityQueue;

/*
Problem: Find Median from Data Stream (LeetCode 295)

Support addNum(int) and findMedian() on a growing stream.

Example:
    add 1, add 2 -> median 1.5
    add 3 -> median 2
*/

public class FindMedianFromDataStream {

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
        FindMedianFromDataStream medianFinder = new FindMedianFromDataStream();

        medianFinder.addNum(1);
        medianFinder.addNum(2);
        System.out.println("Median after 1, 2: " + medianFinder.findMedian()); // 1.5

        medianFinder.addNum(3);
        System.out.println("Median after 1, 2, 3: " + medianFinder.findMedian()); // 2.0

        medianFinder.addNum(4);
        medianFinder.addNum(5);
        System.out.println("Median after 1..5: " + medianFinder.findMedian()); // 3.0
    }
}