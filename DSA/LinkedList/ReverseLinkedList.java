package DSA.LinkedList;

import java.util.Collections;
import java.util.List;

/**
 * LeetCode 206 — Reverse Linked List
 * Pattern: Three-pointer in-place reversal
 */
public class ReverseLinkedList {

  // APPROACH 1: BRUTE — copy to list, reverse, rebuild
  // Time: O(n), Space: O(n)
  public static ListNode bruteForce(ListNode head) {
    List<Integer> values = ListNode.toList(head);
    Collections.reverse(values);
    int[] arr = new int[values.size()];
    for (int i = 0; i < values.size(); i++) {
      arr[i] = values.get(i);
    }
    return ListNode.of(arr);
  }

  // APPROACH 2: ITERATIVE three pointers
  // Time: O(n), Space: O(1)
  public static ListNode reverseList(ListNode head) {
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

  // APPROACH 3: RECURSIVE
  public static ListNode reverseRecursive(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode newHead = reverseRecursive(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
  }

  /**
   * Dry run: 1->2->3
   * prev=null, curr=1: 1->null, prev=1, curr=2
   * prev=1, curr=2: 2->1, prev=2, curr=3
   * prev=2, curr=3: 3->2, prev=3, curr=null => return 3
   */
  public static void main(String[] args) {
    ListNode head = ListNode.of(1, 2, 3, 4, 5);

    System.out.println("Original:");
    ListNode.print(head);

    ListNode brute = bruteForce(ListNode.of(1, 2, 3, 4, 5));
    System.out.print("Brute reversed: ");
    ListNode.print(brute);

    ListNode iter = reverseList(ListNode.of(1, 2, 3, 4, 5));
    System.out.print("Iterative:      ");
    ListNode.print(iter);

    ListNode rec = reverseRecursive(ListNode.of(1, 2));
    System.out.print("Recursive:      ");
    ListNode.print(rec);

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "I keep prev, curr, next. For each node I point curr.next to prev and slide all three forward.");
    System.out.println("Brute uses O(n) extra; iterative is O(1) space.");
  }
}