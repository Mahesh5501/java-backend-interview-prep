package DSA.LinkedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * LeetCode 21 — Merge Two Sorted Lists
 * Pattern: Dummy head + tail pointer
 */
public class MergeTwoSortedLists {

  // APPROACH 1: BRUTE — collect values, sort, build new list
  public static ListNode bruteForce(ListNode list1, ListNode list2) {
    List<Integer> values = new ArrayList<>();
    ListNode a = list1;
    ListNode b = list2;
    while (a != null) {
      values.add(a.val);
      a = a.next;
    }
    while (b != null) {
      values.add(b.val);
      b = b.next;
    }
    Collections.sort(values);
    int[] arr = new int[values.size()];
    for (int i = 0; i < values.size(); i++) {
      arr[i] = values.get(i);
    }
    return ListNode.of(arr);
  }

  // APPROACH 2: TWO POINTER MERGE in place
  public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    while (list1 != null && list2 != null) {
      if (list1.val <= list2.val) {
        tail.next = list1;
        list1 = list1.next;
      } else {
        tail.next = list2;
        list2 = list2.next;
      }
      tail = tail.next;
    }
    tail.next = (list1 != null) ? list1 : list2;
    return dummy.next;
  }

  public static void main(String[] args) {
    ListNode list1 = ListNode.of(1, 3, 5);
    ListNode list2 = ListNode.of(2, 4, 6);

    System.out.println("List1:");
    ListNode.print(list1);
    System.out.println("List2:");
    ListNode.print(list2);

    ListNode merged = mergeTwoLists(ListNode.of(1, 3, 5), ListNode.of(2, 4, 6));
    System.out.print("Merged: ");
    ListNode.print(merged);

    ListNode brute = bruteForce(ListNode.of(1, 2, 4), ListNode.of(1, 3, 4));
    System.out.print("Brute:  ");
    ListNode.print(brute);

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "Dummy node holds the result. I compare heads, link smaller, advance that list, attach remainder.");
  }
}