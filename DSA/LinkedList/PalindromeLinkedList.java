package DSA.LinkedList;

/*
Problem: Palindrome Linked List

Given the head of a singly linked list, determine whether the linked list is a palindrome.
A palindrome reads the same forward and backward.

Example 1:
1 → 2 → 2 → 1
Output: true

Example 2:
1 → 2 → 3
Output: false

Example 3:
1 → 2 → 3 → 2 → 1
Output: true

Expected Approach:
- Use slow and fast pointers to find the middle.
- Reverse the second half.
- Compare the first half with the reversed second half.
- If any values are different, return false.
- If all values match, return true.

Time Complexity: O(n)
Space Complexity: O(1)
*/

public class PalindromeLinkedList {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    static boolean isPalindrome(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast != null) {
            slow = slow.next;
        }
        ListNode curr = slow;
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        ListNode p1 = head;
        ListNode p2 = prev;
        while (p1 != null && p2 != null) {
            if (p1.val != p2.val) return false;
            p1 = p1.next;
            p2 = p2.next;
        }
        return true;
    }

    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(1);

        System.out.println(isPalindrome(head)); // true
    }
}
