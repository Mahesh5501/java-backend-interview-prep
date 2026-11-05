package DSA.LinkedList;

/*
Problem: Remove Nth Node From End of List

Given the head of a singly linked list and an integer n, remove the nth node from the end of the list and return the new head.

Examples:
Input:
1 -> 2 -> 3 -> 4 -> 5
n = 2

Output:
1 -> 2 -> 3 -> 5

Input:
1 -> 2 -> 3
n = 3

Output:
2 -> 3

Expected Approach: Use two pointers with a dummy node.

Move fast n steps ahead. Then move slow and fast together until fast reaches the end. The slow pointer will be positioned just before the node that needs to be removed.
*/

public class RemoveNthNode {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    static ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = dummy;

        for(int i=0; i< n;i++){
            fast = fast.next;
        }
        while(fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        head = removeNthFromEnd(head, 2);

        printList(head); // 1 -> 2 -> 3 -> 5 -> null
    }
}
