package DSA.LinkedList;

/*
Problem: Intersection of Two Linked Lists (LeetCode 160)

See notes/Problem_08_Intersection_of_Two_Linked_Lists.md — every approach below matches the note.
*/

public class IntersectionOfTwoLinkedLists {

  // APPROACH 1: BRUTE — nested traversal (compare references)
  public static ListNode bruteForce(ListNode headA, ListNode headB) {
    ListNode a = headA;
    while (a != null) {
      ListNode b = headB;
      while (b != null) {
        if (a == b) {
          return a;
        }
        b = b.next;
      }
      a = a.next;
    }
    return null;
  }

  // APPROACH 2: TWO POINTERS — switch heads on null (OPTIMAL)
  public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) {
      return null;
    }
    ListNode a = headA;
    ListNode b = headB;
    while (a != b) {
      a = (a == null) ? headB : a.next;
      b = (b == null) ? headA : b.next;
    }
    return a;
  }

  // APPROACH 3: LENGTH ALIGNMENT — skip leading nodes on longer list
  public static ListNode getIntersectionNodeByLength(ListNode headA, ListNode headB) {
    int lenA = ListNode.length(headA);
    int lenB = ListNode.length(headB);
    ListNode a = headA;
    ListNode b = headB;

    if (lenA > lenB) {
      for (int i = 0; i < lenA - lenB; i++) {
        a = a.next;
      }
    } else {
      for (int i = 0; i < lenB - lenA; i++) {
        b = b.next;
      }
    }
    while (a != null && b != null) {
      if (a == b) {
        return a;
      }
      a = a.next;
      b = b.next;
    }
    return null;
  }

  /** Build classic Y-shaped lists sharing tail from shared node. */
  public static ListNode[] buildIntersectingLists() {
    ListNode shared = ListNode.of(8, 4, 5);
    ListNode headA = new ListNode(4);
    headA.next = new ListNode(1);
    headA.next.next = shared;

    ListNode headB = new ListNode(5);
    headB.next = new ListNode(6);
    headB.next.next = new ListNode(1);
    headB.next.next.next = shared;

    return new ListNode[] {headA, headB, shared};
  }

  public static void main(String[] args) {
    ListNode[] lists = buildIntersectingLists();
    ListNode headA = lists[0];
    ListNode headB = lists[1];

    System.out.println("List A:");
    ListNode.print(headA);
    System.out.println("List B:");
    ListNode.print(headB);

    ListNode brute = bruteForce(headA, headB);
    ListNode byLen = getIntersectionNodeByLength(headA, headB);
    ListNode optimal = getIntersectionNode(headA, headB);

    System.out.println(
        "Intersection node value (same reference expected): "
            + (brute != null ? brute.val : "null"));
    System.out.println("Length method matches: " + (byLen == optimal));
    System.out.println("Two-pointer method val: " + (optimal != null ? optimal.val : "null"));

    ListNode noA = ListNode.of(2, 6);
    ListNode noB = ListNode.of(1, 5);
    System.out.println("\nNo intersection: " + getIntersectionNode(noA, noB));

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "Brute is O(n*m). Align longer list by length diff, or swap heads on null — same node reference.");
  }
}