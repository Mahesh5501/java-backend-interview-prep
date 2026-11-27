package DSA.Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Problem: K Closest Points to Origin (LeetCode 973)

Each point is [x, y]. Return the k points with smallest distance squared to (0, 0).

Example:
    points = [[1,1],[3,-2],[-2,2]], k = 2 -> two closest among them
*/

public class KClosestPoints {

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

    private static int distanceSquared(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    public static void main(String[] args) {
        int[][] points = { { 1, 1 }, { 3, -2 }, { -2, 2 } };
        int[][] closest = kClosest(points, 2);
        System.out.println("Two closest: " + Arrays.deepToString(closest));

        int[][] points2 = { { 3, 3 }, { 5, -1 }, { -2, 4 } };
        System.out.println("k=1: " + Arrays.deepToString(kClosest(points2, 1)));
    }
}