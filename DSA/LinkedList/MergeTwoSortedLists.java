package DSA.LinkedList;

/*
Problem: Merge Two Sorted Linked Lists

Given two sorted linked lists, merge them into one sorted linked list.

Example 1:
List 1: 1 → 3 → 5
List 2: 2 → 4 → 6

Output: 1 → 2 → 3 → 4 → 5 → 6

Example 2:
List 1: 1 → 2 → 4
List 2: 1 → 3 → 4

Output: 1 → 1 → 2 → 3 → 4 → 4

Expected Approach:
- Use two pointers, one for each list.
- Compare the current nodes.
- Attach the smaller node to the result.
- Move the pointer of the list from which the node was taken.
- Continue while both lists have nodes.
- Attach the remaining list after one list becomes empty.

Time Complexity: O(n + m)
Space Complexity: O(1) extra space
*/

public class MergeTwoSortedLists {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while(list1 != null && list2!= null){
            if(list1.val < list2.val){
                current.next = list1;
                list1 = list1.next;
            }else{
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        current.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }

    public static void main(String[] args) {

        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(3);
        list1.next.next = new ListNode(5);

        ListNode list2 = new ListNode(2);
        list2.next = new ListNode(4);
        list2.next.next = new ListNode(6);

        ListNode result = mergeTwoLists(list1, list2);

        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
