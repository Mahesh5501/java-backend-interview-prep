package DSA.LinkedList;

/**
 * LeetCode 876 — Middle of the Linked List
 * Pattern: Fast & slow pointers (slow moves 1, fast moves 2)
 */
public class MiddleOfLinkedList {

  // APPROACH 1: BRUTE — two passes: count length, walk to mid
  public static ListNode bruteForce(ListNode head) {
    int len = ListNode.length(head);
    int steps = len / 2;
    ListNode current = head;
    for (int i = 0; i < steps; i++) {
      current = current.next;
    }
    return current;
  }

  // APPROACH 2: FAST & SLOW — when fast cannot advance, slow is middle
  // For even length, returns second middle (LeetCode 876).
  public static ListNode middleNode(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  /**
   * Dry run odd: 1-2-3-4-5
   * slow/fast start at 1; end with slow at 3
   * Even: 1-2-3-4 -> slow ends at 3 (second middle)
   */
  public static void main(String[] args) {
    ListNode odd = ListNode.of(1, 2, 3, 4, 5);
    ListNode even = ListNode.of(1, 2, 3, 4, 5, 6);

    System.out.println("Odd list:");
    ListNode.print(odd);
    System.out.println("Brute middle val: " + bruteForce(odd).val);
    System.out.println("Fast/slow val:    " + middleNode(odd).val);

    System.out.println("\nEven list:");
    ListNode.print(even);
    System.out.println("Brute middle val: " + bruteForce(even).val);
    System.out.println("Fast/slow val:    " + middleNode(even).val);

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "Brute counts then walks. Fast/slow finds middle in one pass: when fast hits end, slow is mid.");
  }
}