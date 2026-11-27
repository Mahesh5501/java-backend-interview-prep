package DSA.LinkedList;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Palindrome Linked List (LeetCode 234)

See notes/Problem_06_Palindrome_Linked_List.md — every approach below matches the note.
*/

public class PalindromeLinkedList {

  // APPROACH 1: BRUTE — copy values to list, two indices
  public static boolean bruteForce(ListNode head) {
    List<Integer> values = ListNode.toList(head);
    int left = 0;
    int right = values.size() - 1;
    while (left < right) {
      if (!values.get(left).equals(values.get(right))) {
        return false;
      }
      left++;
      right--;
    }
    return true;
  }

  // APPROACH 2: O(1) space — slow/fast, reverse second half, compare
  public static boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) {
      return true;
    }

    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    if (fast != null) {
      slow = slow.next;
    }

    ListNode secondHalf = reverse(slow);
    ListNode left = head;
    ListNode right = secondHalf;
    while (right != null) {
      if (left.val != right.val) {
        return false;
      }
      left = left.next;
      right = right.next;
    }
    return true;
  }

  private static ListNode reverse(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
      ListNode next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    return prev;
  }

  public static void main(String[] args) {
    ListNode pal = ListNode.of(1, 2, 2, 1);
    ListNode notPal = ListNode.of(1, 2, 3);
    ListNode oddPal = ListNode.of(1, 2, 3, 2, 1);

    for (ListNode h : new ListNode[] {pal, notPal, oddPal}) {
      ListNode.print(h);
      System.out.println("  Brute:  " + bruteForce(h));
      System.out.println("  Optimal: " + isPalindrome(h));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Brute uses array. Optimal finds mid with fast/slow, reverses second half, compares, O(1) space.");
  }
}