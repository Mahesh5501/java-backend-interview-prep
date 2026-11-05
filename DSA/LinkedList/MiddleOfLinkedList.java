package DSA.LinkedList;

/*
Problem: Find Middle of Linked List

Given the head of a singly linked list, return the middle node.
If the list contains an even number of nodes, return the second middle node.

Examples:
1 -> 2 -> 3 -> 4 -> 5
Output: 3

1 -> 2 -> 3 -> 4 -> 5 -> 6
Output: 4

Expected Approach:
Use two pointers:
slow moves one step at a time.
fast moves two steps at a time.

When fast reaches the end, slow is at the middle.
*/

public class MiddleOfLinkedList {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    static ListNode findMiddle(ListNode head) {

       ListNode slow = head;
       ListNode fast = head;

       while(fast != null && fast.next != null){
           slow = slow.next;
           fast = fast.next.next;
       }
        return slow;
    }

    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.println(findMiddle(head).val); // 3
    }
}
