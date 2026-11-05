package DSA.PriorityQueueHeap;

import java.util.*;

public class MergeKSortedLists {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /*
    Problem:
    You are given an array of k linked lists, where each linked list is sorted in ascending order.
    Merge all the linked lists into one sorted linked list.

    Example:
    Input:
    [1 -> 4 -> 5]
    [1 -> 3 -> 4]
    [2 -> 6]

    Output: 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
    */

    static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        for (ListNode node : lists) {
            pq.add(node);
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            current.next = node;
            current = current.next;

            if (node.next != null) {
                pq.add(node.next);
            }
        }
        return dummy.next;
    }

    public static void main(String[] args) {

        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));
        ListNode[] lists = new ListNode[]{l1, l2, l3};
        ListNode result = mergeKLists(lists);

        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
