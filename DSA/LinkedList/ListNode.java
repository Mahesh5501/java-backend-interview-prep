package DSA.LinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared node type and small helpers for interview practice in this package.
 */
public class ListNode {

  public int val;
  public ListNode next;

  public ListNode(int val) {
    this.val = val;
  }

  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }

  /** Build a list from values; last node's next is null. */
  public static ListNode of(int... values) {
    if (values == null || values.length == 0) {
      return null;
    }
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    for (int v : values) {
      tail.next = new ListNode(v);
      tail = tail.next;
    }
    return dummy.next;
  }

  public static void print(ListNode head) {
    ListNode current = head;
    while (current != null) {
      System.out.print(current.val);
      if (current.next != null) {
        System.out.print(" -> ");
      }
      current = current.next;
    }
    System.out.println(" -> null");
  }

  public static int length(ListNode head) {
    int count = 0;
    while (head != null) {
      count++;
      head = head.next;
    }
    return count;
  }

  public static List<Integer> toList(ListNode head) {
    List<Integer> out = new ArrayList<>();
    while (head != null) {
      out.add(head.val);
      head = head.next;
    }
    return out;
  }

  /** Create cycle: tail connects to node at cycleIndex (0-based). */
  public static ListNode withCycle(int[] values, int cycleIndex) {
    if (values == null || values.length == 0) {
      return null;
    }
    ListNode head = of(values);
    if (cycleIndex < 0) {
      return head;
    }
    ListNode tail = head;
    ListNode cycleNode = null;
    int i = 0;
    while (tail.next != null) {
      if (i == cycleIndex) {
        cycleNode = tail;
      }
      tail = tail.next;
      i++;
    }
    if (cycleIndex == values.length - 1) {
      cycleNode = tail;
    }
    if (cycleNode != null) {
      tail.next = cycleNode;
    }
    return head;
  }
}