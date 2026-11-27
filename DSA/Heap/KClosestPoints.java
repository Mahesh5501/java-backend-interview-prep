package DSA.Heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
Problem: K Closest Points to Origin (LeetCode 973)

Each point is [x, y]. Return the k points with smallest distance squared to (0, 0).

Example:
    points = [[1,1],[3,-2],[-2,2]], k = 2 -> two closest among them

See notes/Problem_05_K_Closest_Points_to_Origin.md — every approach below matches the note.
*/

public class KClosestPoints {

  private static int distanceSquared(int[] point) {
    return point[0] * point[0] + point[1] * point[1];
  }

  // APPROACH 1: SORT BY DISTANCE
  // Compute dist² for all points, sort, return first k.
  // Why use it: simplest; fine when n is modest or k ≈ n.
  // Time: O(n log n)   Extra space: O(n) for copy/sort
  public static int[][] kClosestSort(int[][] points, int k) {
    List<int[]> list = new ArrayList<>(Arrays.asList(points));
    list.sort(Comparator.comparingInt(KClosestPoints::distanceSquared));
    int[][] result = new int[k][2];
    for (int i = 0; i < k; i++) {
      result[i] = list.get(i);
    }
    return result;
  }

  // APPROACH 2: SIZE-K MAX-HEAP (OPTIMAL — what we use)
  // Keep k closest so far using max-heap on distance — evict farthest among survivors.
  // Why max-heap for "k smallest": root is the worst of the kept set.
  // Time: O(n log k)   Extra space: O(k)
  public static int[][] kClosest(int[][] points, int k) {
    PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
        (a, b) -> Integer.compare(distanceSquared(b), distanceSquared(a)));

    for (int[] point : points) {
      maxHeap.offer(point);
      if (maxHeap.size() > k) {
        maxHeap.poll();
      }
    }

    int[][] result = new int[maxHeap.size()][2];
    int index = result.length - 1;
    while (!maxHeap.isEmpty()) {
      result[index--] = maxHeap.poll();
    }
    return result;
  }

  public static void main(String[] args) {
    int[][] points = { { 1, 1 }, { 3, -2 }, { -2, 2 } };
    System.out.println("Sort: " + Arrays.deepToString(kClosestSort(points, 2)));
    System.out.println("Heap: " + Arrays.deepToString(kClosest(points, 2)));
  }
}