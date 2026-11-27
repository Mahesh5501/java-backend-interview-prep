package DSA.LinkedList;

/**
 * LeetCode 19 — Remove Nth Node From End of List
 * Pattern: Dummy + fast ahead by n, then move together
 */
public class RemoveNthNode {

  // APPROACH 1: BRUTE — count length L, remove node at index L - n
  public static ListNode bruteForce(ListNode head, int n) {
    int len = ListNode.length(head);
    if (n == len) {
      return head.next;
    }
    ListNode current = head;
    for (int i = 0; i < len - n - 1; i++) {
      current = current.next;
    }
    current.next = current.next.next;
    return head;
  }

  // APPROACH 2: TWO POINTERS with dummy
  public static ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0, head);
    ListNode slow = dummy;
    ListNode fast = dummy;

    for (int i = 0; i < n; i++) {
      fast = fast.next;
    }
    while (fast.next != null) {
      slow = slow.next;
      fast = fast.next;
    }
    slow.next = slow.next.next;
    return dummy.next;
  }

  /**
   * Dry run: 1-2-3-4-5, n=2
   * fast moves to node 2; then both advance until fast at tail
   * slow before 4 -> skip 4 -> 1-2-3-5
   */
  public static void main(String[] args) {
    System.out.println("Remove 2nd from end:");
    ListNode head = ListNode.of(1, 2, 3, 4, 5);
    ListNode.print(head);
    ListNode result = removeNthFromEnd(ListNode.of(1, 2, 3, 4, 5), 2);
    System.out.print("Two pointers: ");
    ListNode.print(result);

    ListNode brute = bruteForce(ListNode.of(1, 2, 3), 3);
    System.out.print("Brute (remove 3rd from 3 nodes): ");
    ListNode.print(brute);

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "Dummy handles deleting head. Move fast n steps, then lock-step; slow.next is target.");
  }
}