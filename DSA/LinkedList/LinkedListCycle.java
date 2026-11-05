package DSA.LinkedList;

/*
Problem: Linked List Cycle

Given the head of a linked list, determine whether the linked list contains a cycle.
A cycle exists when some node's next pointer points back to a previous node instead of pointing to null.

Examples:
1 -> 2 -> 3 -> 4 -> null
Output: false

1 -> 2 -> 3 -> 4
     ^         |
     |_________|
Output: true

Expected Approach:
Use Fast & Slow pointers.

slow moves one step.
fast moves two steps.

If slow and fast meet, a cycle exists.
If fast reaches null, there is no cycle.
*/

public class LinkedListCycle {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);

        // Create cycle: 4 -> 2
        head.next.next.next.next = head.next;

        System.out.println(hasCycle(head)); // true
    }
}
