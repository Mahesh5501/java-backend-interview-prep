package DSA.Heap;

import java.util.PriorityQueue;

/*
Problem: Last Stone Weight (LeetCode 1046)

Each turn: take two heaviest stones; if equal they smash to nothing,
else the difference remains. Return weight of the last stone, or 0.

Example:
    [2, 7, 4, 1, 8, 1] -> 1
*/

public class LastStoneWeight {

    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        for (int stone : stones) {
            maxHeap.offer(stone);
        }

        while (maxHeap.size() > 1) {
            int first = maxHeap.poll();
            int second = maxHeap.poll();
            if (first != second) {
                maxHeap.offer(first - second);
            }
        }

        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    public static void main(String[] args) {
        int[] stones = { 2, 7, 4, 1, 8, 1 };
        System.out.println("Last stone weight: " + lastStoneWeight(stones)); // 1

        int[] stones2 = { 1 };
        System.out.println("Single stone: " + lastStoneWeight(stones2)); // 1

        int[] stones3 = { 2, 2 };
        System.out.println("Both equal: " + lastStoneWeight(stones3)); // 0
    }
}