# Problem 04 — Merge Two Sorted Lists

> **Java file:** `../MergeTwoSortedLists.java`

> **Pattern:** Merge with dummy head
> **Goal:** Merge two sorted linked lists.
> **LeetCode:** 21

---

## 1. Problem statement

Return head of merged sorted list.

### Example 1

```text
1→2→4 + 1→3→4 → 1→1→2→3→4→4
```

### Example 2

```text
Empty + 0 → 0
```

### Constraints (typical)

- See problem statement / LeetCode.

---

## 2. Mental picture

```text
Dummy tail; attach smaller head each step.
```

**Pattern C — Merge sorted lists** from `Linked_List_Common_Patterns.md`

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

Copy values to array, sort, rebuild.

### Key method

`bruteForce()` in `../MergeTwoSortedLists.java`

### Dry run (summary)

```text
Merge like merge sort without arrays
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n+m)` |
| **Space** | `O(n+m)` |

### When brute still matters

Use brute on the whiteboard to validate examples before you commit to the optimal invariant.

---

## 5. Approach 2 — Optimal

### Idea

Dummy node + tail pointer.

### Steps

State the loop invariant.
Maintain auxiliary structure.
Return aggregated answer.

### Key method

`mergeTwoLists()` in `../MergeTwoSortedLists.java`

```java
public static ListNode mergeTwoLists(ListNode a, ListNode b) {
    ListNode dummy = new ListNode(0), tail = dummy;
    while (a != null && b != null) {
        if (a.val <= b.val) { tail.next = a; a = a.next; }
        else { tail.next = b; b = b.next; }
        tail = tail.next;
    }
    tail.next = (a != null) ? a : b;
    return dummy.next;
}
```

### Visual walkthrough

```text
Dummy tail; attach smaller head each step.
```

---

## 5b. Complexity intuition (optimal)

Each element is pushed and popped at most once → linear time when traversal is single-pass.

---

## 6. Hardest part

Attach remainder without extra loop if one list ends.

---

## 7. Detailed dry run

```text
Always pick min of a.val,b.val
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

Each node appended once.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | `O(n+m)` | `O(n+m)` |
| Optimal | `O(n+m)` | `O(1)` |

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
2. Confirm method names match `../MergeTwoSortedLists.java`.
3. Check null / empty guards first.
4. Verify loop bounds (`<=` vs `<`, sentinel index).
5. Print stack size / pointer positions mid-trace.

---

## 12. Interview — say this aloud

> Classic merge pointer on lists.

### Thirty-second version

> Dummy + tail merge.

### Two-minute version

Brute is `bruteForce()` at `O(n+m)`. I use `mergeTwoLists()` at `O(n+m)` because Each node appended once.

---

## 13. Follow-ups

**Q: Space optimization?** Trade stack for two pointers if possible.

---

## 13b. Complexity Q&A

**Why time `O(n+m)`?**  Each node appended once.

**Why space `O(1)`?**  Auxiliary structure size dominates (stack, heap, or recursion depth).

**Can we beat `O(n+m)`?**  Usually not for this model of the problem; follow-ups may allow linear or O(n log k) only.

---

## Link to Java — which method is which

| Approach | Method in `MergeTwoSortedLists` |
|---|---|
| Brute force | `bruteForce` |
| Two-pointer merge (optimal) | `mergeTwoLists` |

Run `main` to print all approaches on sample inputs.

---

## 14. Complete runnable Java

Full source: `../MergeTwoSortedLists.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac MergeTwoSortedLists.java
java -cp . DSA.LinkedList.MergeTwoSortedLists
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
- [ ] Implement `mergeTwoLists()` without hints.
- [ ] Trace the main example on paper with a table.
- [ ] State time/space in one sentence each.
- [ ] Name the pattern: Pattern C — Merge sorted lists.

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

Pattern: **Pattern C — Merge sorted lists**.
Brute: `O(n+m)` — `bruteForce()`.
Optimal: `O(n+m)` — `mergeTwoLists()`.
Say the invariant before coding.

One line:

> **Dummy + tail merge.**

---

## 17. Pattern used

**Pattern C — Merge sorted lists**

---

## 18. Next problem

**Problem 05 — Remove Nth Node From End**
Continue in order through the folder.

---

---

## Repository Java (full)

Align with `../MergeTwoSortedLists.java`.

```java
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
```