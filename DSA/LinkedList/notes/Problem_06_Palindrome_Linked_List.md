# Problem 06 — Palindrome Linked List

> **Java file:** `../PalindromeLinkedList.java`

> **Pattern:** Find middle + reverse half
> **Goal:** Check if list values form a palindrome.
> **LeetCode:** 234

---

## 1. Problem statement

O(n) time and O(1) extra space expected.

### Example 1

```text
1→2→2→1 → true
```

### Example 2

```text
1→2 → false
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Reverse second half; compare with first half.
```

**Pattern E — Combine patterns** from `Linked_List_Common_Patterns.md`

---

## 2b. What you are really being tested on

Can you recognise the stack/structure pattern, state invariants clearly, implement without index bugs, and justify time/space?

---

## 3. How to start thinking (simple questions)

1. What structure matches the constraint?
2. What is brute time complexity?
3. Can we process each item once?

---

## 4. Approach 1 — Brute force

### Idea

Copy values to array and two-pointer check.

### Key method

`bruteForce()` in `../PalindromeLinkedList.java`

### Dry run (summary)

```text
[1,2,2,1] palindrome
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n)` |
| **Space** | `O(n)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Middle + reverse second + compare.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`isPalindrome()` in `../PalindromeLinkedList.java`

```java
public static boolean isPalindrome(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    if (fast != null) slow = slow.next; // second middle when even length
    ListNode second = reverse(slow);
    while (second != null) {
        if (head.val != second.val) return false;
        head = head.next; second = second.next;
    }
    return true;
}
// private static ListNode reverse(ListNode head) { ... same as Problem 01 }
```

### Visual walkthrough

```text
Reverse second half; compare with first half.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Optional: restore list after check in interviews.

---

## 7. Detailed dry run

```text
Second half reversed matches first
```

---

## 7c. Paper-trace template

| Step | State / stack / pointers | Action |
|---:|---|---|
| 0 | Initial | Read input size |
| 1 | … | First push / move |
| 2 | … | Update answer |
| n | Done | Return result |

Fill this table while running the `main()` example from the Java file.

---

## 8. Why the algorithm is correct

Half reversed equals forward half.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n)` | `O(n)` |
| Optimal | `O(n)` | `O(1)` |

---

## 10. Edge cases

| Case | Behavior |
|---|---|
| Minimum size input | Check carefully |
| Empty / null | Per Java demos |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| Off-by-one in indices | Wrong distances |
| Forgetting sentinel flush | Miss max area |

---

## 11b. Debug checklist (when answer is wrong)

1. Re-run the smallest example by hand.
2. Confirm method names match `../PalindromeLinkedList.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Combine slow/fast + reverse.

### Thirty-second version

> Middle, reverse half, compare.

### Two-minute version

Brute is `bruteForce()` at `O(n)`. I use `isPalindrome()` at `O(n)` because Half reversed equals forward half.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n)`?**  Half reversed equals forward half.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## Link to Java — which method is which

| Approach | Method in `PalindromeLinkedList` |
|---|---|
| Brute force | `bruteForce` |
| Reverse half (optimal) | `isPalindrome` |

Run `main` to print all approaches on sample inputs.

---

## 14. Complete runnable Java

Full source: `../PalindromeLinkedList.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac PalindromeLinkedList.java
java -cp . DSA.LinkedList.PalindromeLinkedList
```

---

## 14b. Re-code drill (no IDE)

1. Close the notes.
2. Write method signature from memory.
3. Run one example on paper.
4. Open Java only to diff imports and edge-case guards.
5. Explain aloud using section 12 script.

---

## 15. Practice checklist

- [ ] Implement `bruteForce()` without hints.
- [ ] Implement `isPalindrome()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern E — Combine patterns.

---

---

## Depth addendum — extended workbook

### Mental picture (second angle)

Sketch input on paper and mark **what changes each loop iteration**. If the pattern uses a stack, queue, heap, or map, write its contents as a column beside the array/tree.

### Brute force reminder

State the naive approach in one sentence, its time/space, and **one sentence why** the optimal pattern beats it.

### Hardest-part drill

| Before coding | After understanding |
|---|---|
| Unsure what moves | Name the invariant pointers/stack maintain |
| Edge case worry | Check empty, single, duplicates |

### Extended dry-run columns

| iter | key variables | container snapshot | output so far |
|---:|---|---|---|

Fill five rows from the matching Java main example in the sibling .java file.

### Interview say-aloud (30s)

> I recognize the pattern, use the right structure for the invariant, avoid brute time because each element is handled once.

### Follow-up Q&A

**Q: In-place?** Only if allowed.  
**Q: Unsorted input?** State preprocessing.

## 16. Quick revision

Pattern: **Pattern E — Combine patterns**.
Brute: `O(n)` — `bruteForce()`.
Optimal: `O(n)` — `isPalindrome()`.
Say the invariant before coding.

One line:

> **Middle, reverse half, compare.**

---

## 17. Pattern used

**Pattern E — Combine patterns**

---

## 18. Next problem

**Problem 07 — Add Two Numbers**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../PalindromeLinkedList.java`.

```java
package DSA.LinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 234 — Palindrome Linked List
 * Pattern: Find middle, reverse second half, compare
 */
public class PalindromeLinkedList {

  // APPROACH 1: BRUTE — copy values to list, two indices
  public static boolean bruteForce(ListNode head) {
    List<Integer> values = ListNode.toList(head);
    int left = 0;
    int right = values.size() - 1;
    while (left < right) {
      if (!values.get(left).equals(values.get(right))) {
        return false;
      }
      left++;
      right--;
    }
    return true;
  }

  // APPROACH 2: O(1) space — slow/fast, reverse second half, compare
  public static boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) {
      return true;
    }

    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    if (fast != null) {
      slow = slow.next;
    }

    ListNode secondHalf = reverse(slow);
    ListNode left = head;
    ListNode right = secondHalf;
    while (right != null) {
      if (left.val != right.val) {
        return false;
      }
      left = left.next;
      right = right.next;
    }
    return true;
  }

  private static ListNode reverse(ListNode head) {
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

  public static void main(String[] args) {
    ListNode pal = ListNode.of(1, 2, 2, 1);
    ListNode notPal = ListNode.of(1, 2, 3);
    ListNode oddPal = ListNode.of(1, 2, 3, 2, 1);

    for (ListNode h : new ListNode[] {pal, notPal, oddPal}) {
      ListNode.print(h);
      System.out.println("  Brute:  " + bruteForce(h));
      System.out.println("  Optimal: " + isPalindrome(h));
      System.out.println("-------------------------");
    }

    System.out.println("Interview script:");
    System.out.println(
        "Brute uses array. Optimal finds mid with fast/slow, reverses second half, compares, O(1) space.");
  }
}
```