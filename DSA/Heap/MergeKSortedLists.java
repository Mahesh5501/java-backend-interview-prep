package DSA.Heap;

import java.util.PriorityQueue;

/*
Problem: Merge k Sorted Lists (LeetCode 23)

Each list is sorted. Return one sorted linked list.

Example:
    [[1,4,5],[1,3,4],[2,6]] -> 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
*/

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

    public static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.val, b.val));

        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            tail.next = node;
            tail = tail.next;

            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }

        return dummy.next;
    }

    static void printList(ListNode head) {
        StringBuilder builder = new StringBuilder();
        while (head != null) {
            builder.append(head.val);
            if (head.next != null) {
                builder.append(" -> ");
            }
            head = head.next;
        }
        System.out.println(builder.toString());
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));

        ListNode merged = mergeKLists(new ListNode[] { l1, l2, l3 });
        System.out.print("Merged: ");
        printList(merged);

        System.out.print("Empty input: ");
        printList(mergeKLists(new ListNode[0]));
    }
}