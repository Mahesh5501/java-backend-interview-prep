package DSA.Heap;

import java.util.PriorityQueue;

/*
Problem: Merge k Sorted Lists (LeetCode 23)

Each list is sorted. Return one sorted linked list.

Example:
    [[1,4,5],[1,3,4],[2,6]] -> 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6

See notes/Problem_03_Merge_K_Sorted_Lists.md — every approach below matches the note.
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

    // APPROACH 1: MERGE PAIRS REPEATEDLY
    // Merge lists two at a time until one remains — classic divide-and-conquer without heap.
    // Why use it: shows reduce structure; worse constant factors than heap for large k.
    // Time: O(N log k)   Extra space: O(1) pointer moves (ignoring output)
    public static ListNode mergeKListsBrute(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        while (lists.length > 1) {
            ListNode[] next = new ListNode[(lists.length + 1) / 2];
            for (int i = 0; i < lists.length; i += 2) {
                next[i / 2] = mergeTwo(lists[i], i + 1 < lists.length ? lists[i + 1] : null);
            }
            lists = next;
        }
        return lists[0];
    }

    private static ListNode mergeTwo(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = a != null ? a : b;
        return dummy.next;
    }

    // APPROACH 2: MIN-HEAP OF LIST HEADS (OPTIMAL — what we use)
    // Always attach the smallest current node among k active lists.
    // Why heap: each of N nodes does O(log k) heap ops.
    // Time: O(N log k)   Extra space: O(k)
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
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        ListNode[] bruteCopy = { l1, l2, l3 };
        System.out.print("Brute merged: ");
        printList(mergeKListsBrute(bruteCopy));

        ListNode[] heapCopy = {
                new ListNode(1, new ListNode(4, new ListNode(5))),
                new ListNode(1, new ListNode(3, new ListNode(4))),
                new ListNode(2, new ListNode(6)),
        };
        System.out.print("Heap merged: ");
        printList(mergeKLists(heapCopy));
    }
}