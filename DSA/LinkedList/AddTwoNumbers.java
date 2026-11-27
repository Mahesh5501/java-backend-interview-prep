package DSA.LinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 2 — Add Two Numbers
 * Pattern: Walk both lists with carry; dummy head for result
 */
public class AddTwoNumbers {

  // APPROACH 1: BRUTE — convert to integers (only safe for small lists in theory)
  public static ListNode bruteForceSmall(ListNode l1, ListNode l2) {
    long n1 = toNumber(l1);
    long n2 = toNumber(l2);
    long sum = n1 + n2;
    if (sum == 0) {
      return new ListNode(0);
    }
    List<Integer> digits = new ArrayList<>();
    while (sum > 0) {
      digits.add((int) (sum % 10));
      sum /= 10;
    }
    int[] arr = new int[digits.size()];
    for (int i = 0; i < digits.size(); i++) {
      arr[i] = digits.get(i);
    }
    return ListNode.of(arr);
  }

  private static long toNumber(ListNode head) {
    long num = 0;
    long place = 1;
    while (head != null) {
      num += (long) head.val * place;
      place *= 10;
      head = head.next;
    }
    return num;
  }

  // APPROACH 2: OPTIMAL — digit-by-digit with carry
  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    int carry = 0;

    while (l1 != null || l2 != null || carry != 0) {
      int a = (l1 != null) ? l1.val : 0;
      int b = (l2 != null) ? l2.val : 0;
      int sum = a + b + carry;
      carry = sum / 10;
      tail.next = new ListNode(sum % 10);
      tail = tail.next;
      if (l1 != null) {
        l1 = l1.next;
      }
      if (l2 != null) {
        l2 = l2.next;
      }
    }
    return dummy.next;
  }

  // APPROACH 3: SAFE brute — collect digits then add with grade-school carry on lists
  public static ListNode bruteForceListAdd(ListNode l1, ListNode l2) {
    List<Integer> d1 = ListNode.toList(l1);
    List<Integer> d2 = ListNode.toList(l2);
    List<Integer> result = new ArrayList<>();
    int carry = 0;
    int i = 0;
    int j = 0;
    while (i < d1.size() || j < d2.size() || carry != 0) {
      int a = i < d1.size() ? d1.get(i) : 0;
      int b = j < d2.size() ? d2.get(j) : 0;
      int sum = a + b + carry;
      result.add(sum % 10);
      carry = sum / 10;
      i++;
      j++;
    }
    int[] arr = new int[result.size()];
    for (int k = 0; k < result.size(); k++) {
      arr[k] = result.get(k);
    }
    return ListNode.of(arr);
  }

  /**
   * Dry run: 2->4->3 + 5->6->4  (342 + 465 = 807 -> 7->0->8)
   */
  public static void main(String[] args) {
    ListNode l1 = ListNode.of(2, 4, 3);
    ListNode l2 = ListNode.of(5, 6, 4);

    System.out.println("l1:");
    ListNode.print(l1);
    System.out.println("l2:");
    ListNode.print(l2);

    ListNode sum = addTwoNumbers(ListNode.of(2, 4, 3), ListNode.of(5, 6, 4));
    System.out.print("Sum (optimal): ");
    ListNode.print(sum);

    ListNode brute = bruteForceListAdd(ListNode.of(9, 9, 9, 9, 9, 9, 9), ListNode.of(9, 9, 9, 9));
    System.out.print("Long lists (list add): ");
    ListNode.print(brute);

    System.out.println();
    System.out.println("Interview script:");
    System.out.println(
        "Do not convert huge lists to int — overflow. Add digits with carry, new result nodes, O(max(n,m)).");
  }
}