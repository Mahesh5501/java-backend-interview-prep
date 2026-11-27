# Linked List --- Common Patterns Reference

A reusable revision guide for Java interviews (Java 8/17).

Runnable classes in `../` use package `DSA.LinkedList`, shared `ListNode` helpers,
brute-force where instructive, optimal patterns, and `main()` demos.

------------------------------------------------------------------------

## 1. What is a linked list node?

```java
public class ListNode {
    public int val;
    public ListNode next;
}
```

Unlike an array, there is **no random access**. To reach the k-th node you walk
`k` `next` links --- O(k) time.

**Head:** first node. **Tail:** last node (`next == null`).

**Interview habit:** Draw boxes and arrows. Name pointers: `prev`, `curr`,
`slow`, `fast`, `dummy`.

------------------------------------------------------------------------

## 2. Helper mindset (`ListNode.java`)

- `ListNode.of(1, 2, 3)` --- build a list for tests.
- `ListNode.print(head)` --- debug output.
- `ListNode.length(head)` --- count nodes.

Use helpers in `main()`; keep solution methods focused.

------------------------------------------------------------------------

## 3. How to recognise linked-list problems

- Input is `ListNode head` (singly or doubly linked).
- No index access; must traverse.
- In-place reversal, merge, cycle, middle, nth from end.
- Two lists: add numbers, find intersection, merge sorted.
- Often needs **O(1) extra space** (not counting output).

------------------------------------------------------------------------

## 4. Pattern A --- In-place reversal

Three pointers: `prev`, `curr`, `next`.

```java
ListNode prev = null;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return prev; // new head
```

**Uses:** Reverse List, reverse half for palindrome, reverse k-group (advanced).

**Brute force:** Copy values to array, reverse array, rebuild list --- O(n) space.

------------------------------------------------------------------------

## 5. Pattern B --- Fast & slow pointers

`slow` moves 1 step; `fast` moves 2 steps (when `fast` and `fast.next` allow).

| Problem            | Stop condition              | Result                          |
|--------------------|-----------------------------|---------------------------------|
| Middle             | `fast == null` or no `next` | `slow` at middle                |
| Cycle detection    | `fast` hits null or meet    | meet ⇒ cycle                    |
| Palindrome         | find middle, then reverse   | compare halves                  |
| Happy Number (202) | `slow == fast`              | meet at `1` ⇒ happy; else cycle |

**Why cycle works:** inside a cycle, fast gains 1 node per step on slow ⇒ eventual meet.

**Happy Number (not a `ListNode`, same pattern):** each integer is a node; `next` =
`sumSquares(digits)`. Unhappy numbers fall into a fixed cycle; Floyd detects it with `O(1)` space.
See `HappyNumber.java` and Problem 09 notes.

------------------------------------------------------------------------

## 6. Pattern C --- Dummy head node

`ListNode dummy = new ListNode(0); dummy.next = head;`

Simplifies:

- Removing head (nth from end, delete val).
- Merge result construction (`current = dummy`).

Return `dummy.next` as the real head.

------------------------------------------------------------------------

## 7. Pattern D --- Two-pointer gap (nth from end)

1. `dummy.next = head`.
2. Move `fast` `n` steps ahead from `dummy`.
3. Move `slow` and `fast` together until `fast.next == null`.
4. `slow` is **before** the node to delete: `slow.next = slow.next.next`.

**Brute force:** Count length `L`, remove node at index `L - n` from start.

------------------------------------------------------------------------

## 8. Pattern E --- Merge two sorted lists

Dummy tail pointer. Compare heads of both lists; attach smaller; advance that list.
Attach remainder when one list ends.

**Brute force:** Copy all values, sort array, build new list --- O(n log n), extra space.

Time O(n + m), extra O(1) if reusing nodes.

------------------------------------------------------------------------

## 9. Pattern F --- Add two numbers (LeetCode 2)

Reverse digit order at each node. Simulate addition with carry.

```java
int sum = a.val + b.val + carry;
carry = sum / 10;
digit = sum % 10;
```

Walk while either list or carry remains. Dummy head for result.

------------------------------------------------------------------------

## 10. Pattern G --- Intersection of two linked lists

**Brute force:** For each node in A, scan B --- O(n·m).

**Optimal (length trim):**

1. Count lengths `lenA`, `lenB`.
2. Advance longer head by `|lenA - lenB|`.
3. Walk both in parallel until same reference (intersection) or null.

**Alternative:** Two pointers `a`, `b` starting at heads; when one hits null,
jump to other head; they meet at intersection or both null after at most `lenA+lenB` steps.

------------------------------------------------------------------------

## 11. Palindrome linked list (O(1) space)

1. Fast/slow to middle.
2. For even length, adjust `slow` (problem-dependent).
3. Reverse second half.
4. Compare first half with reversed second half.
5. (Optional) restore list for follow-ups.

**Brute force:** Copy to `ArrayList`, two indices --- O(n) space.

------------------------------------------------------------------------

## 12. Common mistakes

1. Losing `head` reference --- use `dummy` or save `newHead`.
2. Not checking `fast.next` before `fast.next.next`.
3. Off-by-one on middle (odd vs even length).
4. Comparing intersection by **value** instead of **node reference**.
5. Forgetting carry in Add Two Numbers.
6. Null `head` / empty list.

------------------------------------------------------------------------

## 13. Complexity reference

| Pattern              | Time     | Extra space |
|----------------------|----------|-------------|
| Single traversal     | O(n)     | O(1)        |
| Reverse in place     | O(n)     | O(1)        |
| Merge two lists      | O(n+m)   | O(1)        |
| Hash set cycle (alt) | O(n)     | O(n)        |
| Brute intersection   | O(n·m)   | O(1)        |

------------------------------------------------------------------------

## 14. Interview script order

1. Restate examples (including edge: empty, single node).
2. Brute force for correctness baseline.
3. Pointer meaning (`slow`, `fast`, `dummy`).
4. Invariant in one sentence.
5. Short dry run on 4--5 nodes.
6. Time and space.

------------------------------------------------------------------------

## 15. Practice roadmap (this folder)

1. Reverse Linked List --- three pointers.
2. Middle of Linked List --- fast/slow.
3. Linked List Cycle --- fast/slow meet.
4. Merge Two Sorted Lists --- dummy tail.
5. Remove Nth Node From End --- gap pointers + dummy.
6. Palindrome Linked List --- middle + reverse + compare.
7. Add Two Numbers --- digit carry walk.
8. Intersection of Two Linked Lists --- align lengths or two-pointer swap.
9. Happy Number --- Floyd on the digit-square chain (implicit linked list).

Study one class at a time; close the file and explain from memory.