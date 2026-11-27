package DSA.LinkedList;

import java.util.HashSet;
import java.util.Set;

/*
Problem: Linked List Cycle (LeetCode 141)

See notes/Problem_03_Linked_List_Cycle.md — every approach below matches the note.
*/

public class LinkedListCycle {

  // APPROACH 1: BRUTE / HASH SET — track visited nodes
  // Time: O(n), Space: O(n)
  public static boolean hasCycleHashSet(ListNode head) {
    Set<ListNode> seen = new HashSet<>();
    ListNode current = head;
    while (current != null) {
      if (!seen.add(current)) {
        return true;
      }
      current = current.next;
    }
    return false;
  }

  // APPROACH 2: FLOYD — fast and slow meet if cycle exists
  // Time: O(n), Space: O(1)
  public static boolean hasCycle(ListNode head) {
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
    ListNode noCycle = ListNode.of(1, 2, 3, 4);
    ListNode withCycle = ListNode.withCycle(new int[] {1, 2, 3, 4}, 1);

    System.out.println("No cycle:");
    ListNode.print(noCycle);
    System.out.println("  HashSet: " + hasCycleHashSet(noCycle));
    System.out.println("  Floyd:   " + hasCycle(noCycle));

    System.out.println("\nCycle (tail -> node at index 1):");
    System.out.println("  HashSet: " + hasCycleHashSet(withCycle));
    System.out.println("  Floyd:   " + hasCycle(withCycle));

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "HashSet stores nodes O(n) space. Floyd moves slow 1 and fast 2; meeting inside cycle => true.");
  }
}