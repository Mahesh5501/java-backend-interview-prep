# Problem 07 — Add Two Numbers

> **Pattern:** Digit lists + carry
> **Goal:** Add numbers stored as reversed digit lists.
> **LeetCode:** 2

---

## 1. Problem statement

Each node is a digit; add with carry.

### Example 1

```text
2→4→3 + 5→6→4 = 7→0→8 (342+465)
```

### Example 2

```text
0 + 0 → 0
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Walk both lists; carry to next digit.
```

**Pattern F — Simulate arithmetic** from `Linked_List_Common_Patterns.md`

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

Convert to int (small inputs demo only).

### Key method

`bruteForceSmall()` in `../AddTwoNumbers.java`

### Dry run (summary)

```text
carry 1 when 7+8
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

Dummy result; while l1||l2||carry.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`addTwoNumbers()` in `../AddTwoNumbers.java`

```java
public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0), tail = dummy;
    int carry = 0;
    while (l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if (l1 != null) { sum += l1.val; l1 = l1.next; }
        if (l2 != null) { sum += l2.val; l2 = l2.next; }
        tail.next = new ListNode(sum % 10);
        tail = tail.next;
        carry = sum / 10;
    }
    return dummy.next;
}
```

### Visual walkthrough

```text
Walk both lists; carry to next digit.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Lengths differ — null checks.

---

## 7. Detailed dry run

```text
sum 17 → digit 7 carry 1
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

Mimics grade-school add.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n)` | `O(n)` |
| Optimal | `O(max(n,m))` | `O(max(n,m))` |

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
2. Confirm method names match `../AddTwoNumbers.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Dummy tail build; carry propagation.

### Thirty-second version

> Digit walk with carry.

### Two-minute version

Brute is `bruteForceSmall()` at `O(n)`. I use `addTwoNumbers()` at `O(max(n,m))` because Mimics grade-school add.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(max(n,m))`?**  Mimics grade-school add.

**Why space `O(max(n,m))`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(max(n,m))`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## 14. Complete runnable Java

Full source: `../AddTwoNumbers.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac AddTwoNumbers.java
java -cp . DSA.LinkedList.AddTwoNumbers
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

- [ ] Implement `bruteForceSmall()` without hints.
- [ ] Implement `addTwoNumbers()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern F — Simulate arithmetic.

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

Pattern: **Pattern F — Simulate arithmetic**.
Brute: `O(n)` — `bruteForceSmall()`.
Optimal: `O(max(n,m))` — `addTwoNumbers()`.
Say the invariant before coding.

One line:

> **Digit walk with carry.**

---

## 17. Pattern used

**Pattern F — Simulate arithmetic**

---

## 18. Next problem

**Problem 08 — Intersection of Two Linked Lists**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../AddTwoNumbers.java`.

```java
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
```
