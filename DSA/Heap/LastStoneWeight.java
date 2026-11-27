package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: Last Stone Weight (LeetCode 1046)

Each turn: take two heaviest stones; if equal they smash to nothing,
else the difference remains. Return weight of the last stone, or 0.

Example:
    [2, 7, 4, 1, 8, 1] -> 1

See notes/Problem_07_Last_Stone_Weight.md — every approach below matches the note.
*/

public class LastStoneWeight {

  // APPROACH 1: RESORT EVERY ROUND
  // Sort descending, smash top two, push difference, repeat — mirrors rules literally.
  // Why use it: interview baseline before you justify a heap.
  // Time: O(n² log n) in worst case   Extra space: O(n)
  public static int lastStoneWeightBrute(int[] stones) {
    int[] arr = stones.clone();
    while (arr.length > 1) {
      Arrays.sort(arr);
      int a = arr[arr.length - 1];
      int b = arr[arr.length - 2];
      int diff = Math.abs(a - b);
      arr = Arrays.copyOf(arr, arr.length - 2);
      if (diff != 0) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = diff;
      }
    }
    return arr.length == 0 ? 0 : arr[0];
  }

  // APPROACH 2: MAX-HEAP (OPTIMAL — what we use)
  // Poll two largest each round; push difference if any.
  // Why heap: O(log n) per smash vs resorting entire array.
  // Time: O(n log n)   Extra space: O(n)
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
    System.out.println("Brute: " + lastStoneWeightBrute(stones));
    System.out.println("Heap:  " + lastStoneWeight(stones));

    int[] equal = { 2, 2 };
    System.out.println("Equal brute: " + lastStoneWeightBrute(equal));
    System.out.println("Equal heap:  " + lastStoneWeight(equal));
  }
}