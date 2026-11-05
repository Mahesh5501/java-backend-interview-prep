
# DSA PATTERNS 6, 7 & 9
# BINARY SEARCH + LINKED LIST + HEAP / PRIORITYQUEUE
## Recognition → Algorithm → Variations → Interview Problems

============================================================
# PATTERN 6 — BINARY SEARCH
============================================================

## 1. What Is Binary Search?

Binary Search finds an answer by repeatedly eliminating half of the remaining search space.

Imagine searching for 23 in a sorted array:

[3, 7, 11, 15, 19, 23, 27, 31, 35]

Instead of checking every element:
1. Check the middle.
2. If the middle is too small, eliminate the left half.
3. If the middle is too large, eliminate the right half.
4. Repeat until the target is found or the search space becomes empty.

This is why Binary Search is O(log n), compared with O(n) for a linear search.

Important:
Ordinary Binary Search needs a sorted search space. Some advanced variations work on an ordered answer space rather than a sorted array.

---

## 2. How to Recognize Binary Search

| Problem clue | Possible pattern |
|---|---|
| Find a target in a sorted array | Classic Binary Search |
| Find the first or last occurrence | Boundary Binary Search |
| Find the first value greater than or equal to target | Lower Bound |
| Find the first value greater than target | Upper Bound |
| Find a value in a rotated sorted array | Modified Binary Search |
| Find the minimum in a rotated sorted array | Modified Binary Search |
| Find a minimum feasible capacity or speed | Binary Search on Answer |
| Find a peak element | Binary Search using a directional property |
| Search a sorted matrix | Binary Search or matrix-specific elimination |
| Find the square root of a number | Binary Search on Answer |

The key question:

**Can I eliminate half of the remaining candidates using the result of one comparison?**

If yes, Binary Search may fit.

---

## 3. Binary Search Decision Tree

### Question 1: Is the search space sorted or does it have a useful monotonic property?

- YES → Continue.
- NO → Binary Search may not apply directly.

### Question 2: Do I need to find an exact target?

- YES → Classic Binary Search.
- NO → Continue.

### Question 3: Do I need the first or last position satisfying a condition?

- YES → Boundary Binary Search.

### Question 4: Am I looking for the smallest or largest answer that satisfies a condition?

- YES → Consider Binary Search on Answer.

### Question 5: Is the array rotated or partially ordered?

- YES → Use a modified Binary Search that identifies which part has the required order.

---

# FAMILY A — CLASSIC BINARY SEARCH

## 4. Recognition

Look for:
- Search for a target in a sorted array.
- Return its index.
- Return -1 if it does not exist.

## 5. Algorithm

1. Set left to the first index.
2. Set right to the last index.
3. Calculate the middle index.
4. If the middle value equals the target, return it.
5. If the middle value is too small, search the right half.
6. If the middle value is too large, search the left half.
7. Continue while left <= right.

## 6. Java Template

    static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

Why calculate the middle this way?

    int mid = left + (right - left) / 2;

This avoids the potential integer overflow of `(left + right) / 2`.

Complexity:
- Time: O(log n).
- Extra space: O(1).

## 7. Dry Run

Array: [3, 7, 11, 15, 19, 23, 27]
Target = 23

First:
- left = 0
- right = 6
- mid = 3
- arr[3] = 15

15 < 23, so discard indices 0 through 3.

Next:
- left = 4
- right = 6
- mid = 5
- arr[5] = 23

Found at index 5.

---

# FAMILY B — FIRST AND LAST OCCURRENCE

## 8. How to Recognize It

Look for:
- Find the first occurrence of a duplicate value.
- Find the last occurrence.
- Count occurrences in a sorted array.
- Find the range of a target.

Example:

[1, 2, 2, 2, 2, 5, 7]

Target = 2

First occurrence = index 1.
Last occurrence = index 4.
Count = 4.

A normal Binary Search can find any matching index, but that is not enough when the question asks for the first or last occurrence.

## 9. First Occurrence Algorithm

When arr[mid] equals the target:
- Save mid as a possible answer.
- Continue searching the left half.

Java:

    static int firstOccurrence(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                answer = mid;
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

## 10. Last Occurrence Algorithm

When arr[mid] equals the target:
- Save mid as a possible answer.
- Continue searching the right half.

Java:

    static int lastOccurrence(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                answer = mid;
                left = mid + 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

Complexity for each:
- Time: O(log n).
- Extra space: O(1).

### Count occurrences

If the target exists:

count = lastOccurrence - firstOccurrence + 1

If the target does not exist, the count is zero.

---

# FAMILY C — LOWER BOUND AND UPPER BOUND

## 11. How to Recognize It

These are common when a problem asks for:
- The first position where a value can be inserted.
- The first element greater than or equal to a target.
- The first element strictly greater than a target.
- The number of elements smaller than a target.
- The number of elements within a range.

### Lower Bound

Find the first index i where:

arr[i] >= target

If no such index exists, return arr.length.

### Upper Bound

Find the first index i where:

arr[i] > target

If no such index exists, return arr.length.

## 12. Java — Lower Bound

    static int lowerBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

Notice the difference:
- right starts at arr.length.
- The loop uses left < right.
- The search interval is treated as [left, right).

## 13. Java — Upper Bound

    static int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

Why are these useful?

For a sorted array, the number of elements equal to target is:

upperBound(arr, target) - lowerBound(arr, target)

This works even when the target does not exist; the difference is zero.

---

# FAMILY D — SEARCH IN A ROTATED SORTED ARRAY

## 14. How to Recognize It

Look for:
- An originally sorted array rotated at some pivot.
- Search for a target in the rotated array.
- Find a value without sorting the array again.

Example:

Original:
[1, 3, 5, 7, 9, 11]

Rotated:
[7, 9, 11, 1, 3, 5]

The entire array is not sorted, but at least one side of the midpoint is sorted when all values are distinct.

## 15. Algorithm

1. Find the middle.
2. If the middle is the target, return it.
3. Determine which half is sorted.
4. Check whether the target lies inside that sorted half.
5. Keep that half if it does; otherwise, search the other half.
6. Repeat.

Java:

    static int searchRotated(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            if (arr[left] <= arr[mid]) {
                // Left half is sorted.
                if (arr[left] <= target && target < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // Right half is sorted.
                if (arr[mid] < target && target <= arr[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

Complexity:
- Time: O(log n) when the usual distinct-value assumptions apply.
- Extra space: O(1).

Important:
Duplicates can make it impossible to determine the sorted half from endpoint comparisons alone. Some duplicate-heavy variations require shrinking the search interval and can degrade to O(n).

---

# FAMILY E — BINARY SEARCH ON ANSWER

## 16. How to Recognize It

Look for:
- Minimum capacity that can complete a task.
- Minimum speed needed to finish within a deadline.
- Minimum days required.
- Smallest feasible maximum workload.
- Maximum minimum distance between placements.
- Minimize the largest partition sum.

Sometimes you are not searching an array. You are searching a range of possible answers.

The key question:

**If an answer is feasible, can I determine whether a smaller or larger answer is also feasible?**

If the feasibility result is monotonic, Binary Search may apply.

Example:

If a shipping capacity of 10 is sufficient, capacity 11 or 12 is also sufficient.

So:
- Capacity below the minimum feasible value → false.
- Capacity at or above it → true.

This creates a boundary that Binary Search can find.

## 17. General Template — Find Minimum Feasible Answer

    int left = minimumPossibleAnswer;
    int right = maximumPossibleAnswer;

    while (left < right) {
        int mid = left + (right - left) / 2;

        if (canComplete(mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }

    return left;

The `canComplete(mid)` method is problem-specific.

For minimum feasible answers:
- If mid is feasible, try a smaller answer.
- If mid is infeasible, try a larger answer.

For maximum feasible answers, the direction and midpoint formula may differ.

## 18. Example — Minimum Capacity to Ship Packages

Packages:

[1, 2, 3, 1, 1]

Days = 4

A capacity of 3 can ship:
- Day 1: 1 + 2
- Day 2: 3
- Day 3: 1 + 1

This uses 3 days, so it is feasible.

A capacity of 2 requires more than 4 days, so it is not feasible.

The answer is the smallest capacity that allows all packages to be shipped within 4 days.

Java:

    static int shipWithinDays(int[] weights, int days) {
        int left = 0;
        int right = 0;

        for (int weight : weights) {
            left = Math.max(left, weight);
            right += weight;
        }

        while (left < right) {
            int capacity = left + (right - left) / 2;

            if (canShip(weights, days, capacity)) {
                right = capacity;
            } else {
                left = capacity + 1;
            }
        }

        return left;
    }

    static boolean canShip(int[] weights, int days, int capacity) {
        int usedDays = 1;
        int currentLoad = 0;

        for (int weight : weights) {
            if (currentLoad + weight > capacity) {
                usedDays++;
                currentLoad = 0;
            }

            currentLoad += weight;
        }

        return usedDays <= days;
    }

Why does the search start at the maximum individual weight?

A package cannot be split and must fit on a single day.

Why does the search end at the total weight?

That capacity can ship every package in one day.

Complexity:
- Feasibility check: O(n).
- Binary Search iterations: O(log S), where S is the searched capacity range.
- Total: O(n log S).

Use `long` for bounds and sums if the input constraints can exceed the `int` range.

---

## 19. Binary Search Mistakes

1. Using Binary Search on unsorted data without another monotonic property.
2. Confusing `left <= right` with `left < right`.
3. Updating the boundary incorrectly and causing an infinite loop.
4. Forgetting to save a candidate answer for first/last occurrence.
5. Searching for an exact match when the question asks for a boundary.
6. Assuming every Binary Search variation takes O(log n); Binary Search on Answer also includes the cost of the feasibility check.
7. Ignoring duplicates in rotated arrays.
8. Forgetting that a lower bound may return `arr.length`.
9. Using `left + right` for the midpoint when overflow is possible.
10. Failing to prove that the feasibility condition is monotonic.

---

## 20. Binary Search Recognition Summary

| Problem | Pattern |
|---|---|
| Find target in sorted array | Classic Binary Search |
| First/last occurrence | Boundary Binary Search |
| First element >= target | Lower Bound |
| First element > target | Upper Bound |
| Search rotated sorted array | Modified Binary Search |
| Minimum capacity or speed | Binary Search on Answer |
| Find peak | Directional Binary Search |
| Square root | Binary Search on Answer |

---

============================================================
# PATTERN 7 — LINKED LIST
============================================================

## 21. What Is a Linked List?

A Linked List stores values in nodes. Each node holds a value and a reference to the next node.

Unlike an array, its nodes are not required to be stored next to each other in memory.

Example:

10 → 20 → 30 → 40 → null

A typical Java node:

    class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

`head` points to the first node.

`next` connects one node to the next.

The last node points to null in a normal singly linked list.

Important:
A Linked List is a data structure, while patterns such as Two Pointers and Fast & Slow Pointers are techniques commonly used to solve Linked List problems.

---

## 22. How to Recognize Linked List Techniques

| Problem clue | Likely technique |
|---|---|
| Reverse the list | Pointer reversal |
| Find the middle node | Fast & Slow Pointers |
| Detect a cycle | Fast & Slow Pointers |
| Find where a cycle begins | Floyd's cycle algorithm |
| Find the kth node from the end | Two pointers with a gap |
| Merge two sorted linked lists | Two pointers |
| Remove the nth node from the end | Gap pointers + dummy node |
| Check if the list is a palindrome | Middle + reverse second half |
| Add two numbers represented by lists | Carry + traversal |
| Reorder a list | Middle + reverse + merge |
| Find the intersection node | Two-pointer switching |
| Reverse nodes in groups | Pointer rewiring |

The key question:

**Does the problem involve changing links, locating a node, or traversing the list without direct index access?**

If yes, consider Linked List techniques.

---

# FAMILY A — REVERSING A LINKED LIST

## 23. How to Recognize It

Look for:
- Reverse a singly linked list.
- Reverse a list in place.
- Reverse part of a linked list.
- Reverse nodes in groups.

The central challenge is changing each node's `next` reference without losing access to the remaining nodes.

## 24. Algorithm

Use three references:
- `prev`: the previous node.
- `current`: the node being processed.
- `next`: the next node, saved before changing the link.

At each step:
1. Save `current.next`.
2. Point `current.next` to `prev`.
3. Move `prev` to current.
4. Move current to the saved next node.

## 25. Java Solution

    static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode next = current.next;

            current.next = prev;

            prev = current;
            current = next;
        }

        return prev;
    }

Example:

Before:

10 → 20 → 30 → null

After:

30 → 20 → 10 → null

Why save `next` first?

If we overwrite `current.next` before saving it, we lose the reference to the remaining nodes.

Complexity:
- Time: O(n).
- Extra space: O(1).

---

# FAMILY B — FIND THE MIDDLE

## 26. How to Recognize It

Look for:
- Find the middle node.
- Split a linked list into halves.
- Prepare a list for palindrome checking.
- Merge sort a linked list.

Use Fast & Slow Pointers:
- Slow moves one step.
- Fast moves two steps.

When fast reaches the end, slow is around the middle.

Java:

    static ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

For an even-length list, this version returns the second middle node.

Complexity:
- Time: O(n).
- Extra space: O(1).

---

# FAMILY C — DETECT A CYCLE

## 27. How to Recognize It

Look for:
- Determine whether a linked list loops back to an earlier node.
- Detect an infinite traversal.
- Find whether a linked list contains a cycle.

A cycle looks like:

10 → 20 → 30 → 40
          ↑         |
          |_________|

Fast & Slow Pointer approach:

1. Both begin at head.
2. Slow moves one step.
3. Fast moves two steps.
4. If they meet, a cycle exists.
5. If fast or fast.next becomes null, no cycle exists.

Java:

    static boolean hasCycle(ListNode head) {
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

Important:
Compare node references using `==`, not node values. Two different nodes can contain the same value.

Complexity:
- Time: O(n).
- Extra space: O(1).

---

# FAMILY D — REMOVE THE NTH NODE FROM THE END

## 28. How to Recognize It

Look for:
- Remove the last node.
- Remove the nth node from the end.
- Find a node a fixed distance from the end.

The trick is to maintain a gap between two pointers.

If the pointers are n nodes apart, moving them together allows the second pointer to reach the end while the first reaches the target position.

A dummy node simplifies removing the head.

## 29. Java Solution

    static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        for (int i = 0; i < n; i++) {
            if (fast.next == null) {
                throw new IllegalArgumentException("Invalid n");
            }

            fast = fast.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }

The dummy node gives us a node before the original head, so deleting the head uses the same link-update operation as deleting another node.

Complexity:
- Time: O(n).
- Extra space: O(1).

---

# FAMILY E — MERGE TWO SORTED LINKED LISTS

## 30. How to Recognize It

Look for:
- Combine two sorted linked lists into one sorted list.
- Merge lists without creating new nodes for each value.
- Merge sort on linked lists.

Use two pointers, one for each input, and a dummy node for the output.

## 31. Java Solution

    static ListNode mergeTwoLists(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }

            tail = tail.next;
        }

        tail.next = (a != null) ? a : b;

        return dummy.next;
    }

Why use a dummy node?

It avoids separate logic for initializing the first node of the merged list.

Complexity:
- Time: O(n + m).
- Extra space: O(1) auxiliary space when reusing existing nodes.

---

# FAMILY F — LINKED LIST PALINDROME

## 32. How to Recognize It

Look for:
- Read a linked list the same forward and backward.
- Check whether the values form a palindrome.
- Do it in O(1) extra space if possible.

A common approach:
1. Find the middle with Fast & Slow Pointers.
2. Reverse the second half.
3. Compare the first half with the reversed second half.
4. Optionally restore the original list if the problem requires it.

This combines several patterns:
- Fast & Slow Pointers.
- Pointer reversal.
- Two-pointer comparison.

Be careful with odd-length lists: the middle element does not need to match another element.

---

# FAMILY G — INTERSECTION OF TWO LINKED LISTS

## 33. How to Recognize It

Look for:
- Two lists eventually share the same node.
- Find the first common node.
- Do not confuse equal values with identical nodes.

A useful two-pointer approach:
- Pointer A traverses list A, then list B.
- Pointer B traverses list B, then list A.
- If the lists intersect, the pointers meet at the intersection.
- If there is no intersection, both eventually reach null.

Java:

    static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b = headB;

        while (a != b) {
            a = (a == null) ? headB : a.next;
            b = (b == null) ? headA : b.next;
        }

        return a;
    }

Why does switching heads work?

Each pointer traverses the combined lengths of both lists. This balances out any difference in their initial lengths.

Complexity:
- Time: O(n + m).
- Extra space: O(1).

---

## 34. Linked List Mistakes

1. Overwriting `current.next` before saving the next node.
2. Losing the head reference.
3. Comparing node values instead of node identity when detecting cycles or intersections.
4. Forgetting to handle null and one-node lists.
5. Forgetting a dummy node when deleting the head is possible.
6. Returning the old head after reversing the list.
7. Creating an accidental cycle when reconnecting list sections.
8. Forgetting to reconnect the reversed half in a palindrome or reorder problem.
9. Using `fast.next.next` without checking that `fast` and `fast.next` are non-null.
10. Confusing an index-based array operation with pointer traversal.

---

## 35. Linked List Recognition Summary

| Problem | Pattern |
|---|---|
| Reverse a list | Pointer reversal |
| Find the middle | Fast & Slow |
| Detect a cycle | Floyd's algorithm |
| Remove nth node from end | Gap pointers + dummy |
| Merge sorted lists | Two pointers + dummy |
| Palindrome list | Middle + reverse + compare |
| Intersection of lists | Two pointers switching heads |
| Reorder list | Middle + reverse + merge |
| Reverse in groups | Pointer rewiring |

---

============================================================
# PATTERN 9 — HEAP / PRIORITYQUEUE
============================================================

## 36. What Is a Heap?

A Heap is a data structure that lets you retrieve the smallest or largest element efficiently.

In Java, `PriorityQueue` is the standard library implementation of a priority queue using a heap.

A normal queue follows arrival order.

A PriorityQueue follows priority order.

For a min-heap:
- The smallest element is at the head.

For a max-heap:
- The largest element is at the head.

Java:

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    PriorityQueue<Integer> maxHeap =
        new PriorityQueue<>(Comparator.reverseOrder());

Important:
Java's `PriorityQueue` does not keep all elements fully sorted. It guarantees access to the highest-priority element at the head. Iterating through it does not guarantee sorted order.

---

## 37. How to Recognize Heap / PriorityQueue

| Problem clue | Likely approach |
|---|---|
| Find the K largest elements | Min-heap of size K |
| Find the K smallest elements | Max-heap of size K |
| Find the kth largest element | Min-heap of size K or Quickselect |
| Find the kth smallest element | Max-heap of size K or Quickselect |
| Merge K sorted lists | Min-heap |
| Find the top K frequent elements | Heap + frequency map |
| Repeatedly process the smallest or largest item | PriorityQueue |
| Find the median of a data stream | Two heaps |
| Schedule tasks by priority | PriorityQueue |
| Find closest K points | Heap or Quickselect |
| Combine smallest items repeatedly | Min-heap |

The key question:

**Do I repeatedly need the smallest or largest item, or only the top K items, rather than a fully sorted collection?**

If yes, consider a heap.

---

## 38. Heap Decision Tree

### Question 1: Do I need repeated access to the smallest or largest element?

- YES → PriorityQueue may fit.
- NO → Sorting or another structure may be simpler.

### Question 2: Do I need all elements sorted?

- YES → Sorting may be simpler if repeated priority extraction is unnecessary.
- NO → A heap may be more efficient.

### Question 3: Do I only need the top K elements?

- YES → Maintain a heap of size K when appropriate.
- NO → Continue.

### Question 4: Do I need both a lower half and an upper half?

- YES → Consider two heaps, as in finding a running median.

### Question 5: Do I need to merge K sorted sources?

- YES → Consider a min-heap containing the next candidate from each source.

---

# FAMILY A — KTH LARGEST ELEMENT

## 39. How to Recognize It

Look for:
- Find the kth largest value.
- Find the top K largest values.
- Maintain the largest K values in a stream.

If K is small compared with n, a heap of size K is often useful.

## 40. Algorithm — Min-Heap of Size K

1. Create a min-heap.
2. Add each element.
3. If the heap grows larger than K, remove its smallest element.
4. At the end, the heap contains the K largest elements.
5. The smallest among those K elements is the kth largest.

Why a min-heap?

If we have more than K candidates, we remove the smallest. That keeps only the largest K seen so far.

## 41. Java Solution

    static int findKthLargest(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Invalid k");
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int value : arr) {
            minHeap.offer(value);

            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return minHeap.peek();
    }

Example:

Array = [3, 2, 1, 5, 6, 4]
K = 2

The two largest elements are 5 and 6.

The heap keeps these candidates.

The smallest among them is 5.

Answer = 5.

Complexity:
- Time: O(n log k).
- Extra space: O(k).

Variation:
For kth smallest, maintain a max-heap of size K and remove the largest when the heap exceeds K.

---

# FAMILY B — TOP K FREQUENT ELEMENTS

## 42. How to Recognize It

Look for:
- Most frequent K values.
- Top K words.
- Most common events.
- Most frequent products or IDs.

This combines two techniques:
1. HashMap to count frequencies.
2. Heap to retain the top candidates.

## 43. Algorithm

1. Count the frequency of each value.
2. Create a min-heap ordered by frequency.
3. Add each distinct value with its frequency.
4. If the heap size exceeds K, remove the least frequent entry.
5. The remaining entries are the top K frequent values.

Important:
If the problem specifies a tie-breaking rule, the comparator must handle it explicitly.

## 44. Java Solution

    static int[] topKFrequent(int[] arr, int k) {
        Map<Integer, Integer> frequency = new HashMap<>();

        for (int value : arr) {
            frequency.put(
                value,
                frequency.getOrDefault(value, 0) + 1
            );
        }

        PriorityQueue<Integer> minHeap =
            new PriorityQueue<>(
                Comparator.comparingInt(frequency::get)
            );

        for (int value : frequency.keySet()) {
            minHeap.offer(value);

            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[minHeap.size()];

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }

        return result;
    }

Complexity:
- Counting: O(n) expected.
- Heap processing: O(u log k), where u is the number of distinct values.
- Extra space: O(u + k).

Tie handling:
The comparator above considers frequency only. If the problem requires a particular order for equal frequencies, add a secondary comparison.

---

# FAMILY C — MERGE K SORTED LISTS

## 45. How to Recognize It

Look for:
- Merge K sorted arrays or lists.
- Find the smallest current element across several sorted sources.
- Process multiple sorted streams efficiently.

The idea:

At any moment, the next candidate from each list is its current element. The smallest of these candidates must be the next value in the merged output.

A min-heap efficiently chooses that candidate.

## 46. Algorithm

1. Insert the first node of every non-empty list into a min-heap.
2. Remove the smallest node.
3. Append it to the merged output.
4. If the removed node has a next node, add that next node to the heap.
5. Repeat until the heap is empty.

Java:

    static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap =
            new PriorityQueue<>(
                Comparator.comparingInt(node -> node.val)
            );

        for (ListNode node : lists) {
            if (node != null) {
                minHeap.offer(node);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {
            ListNode smallest = minHeap.poll();

            tail.next = smallest;
            tail = tail.next;

            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }

        tail.next = null;

        return dummy.next;
    }

Complexity:
- Let N be the total number of nodes and K the number of lists.
- Time: O(N log K).
- Heap space: O(K).

Important:
The comparator compares node values, not node identities. Equal-valued nodes can appear in any order unless the problem specifies a tie-breaker.

---

# FAMILY D — TWO HEAPS FOR MEDIAN

## 47. How to Recognize It

Look for:
- Median of a stream.
- Continuously add numbers and report the median.
- Find the middle value after every insertion.

Why two heaps?

Split the values into two halves:
- Max-heap: lower half.
- Min-heap: upper half.

Maintain these rules:
1. Every value in the lower half is less than or equal to every value in the upper half.
2. The heaps' sizes differ by at most one.

If the total number of values is odd, the larger heap contains the median.

If the count is even, the median is the average of the two heap tops.

## 48. Java Implementation

    class MedianFinder {
        private final PriorityQueue<Integer> lower =
            new PriorityQueue<>(Comparator.reverseOrder());

        private final PriorityQueue<Integer> upper =
            new PriorityQueue<>();

        public void addNum(int num) {
            lower.offer(num);
            upper.offer(lower.poll());

            if (upper.size() > lower.size()) {
                lower.offer(upper.poll());
            }
        }

        public double findMedian() {
            if (lower.size() > upper.size()) {
                return lower.peek();
            }

            return ((long) lower.peek() + upper.peek()) / 2.0;
        }
    }

Why cast to `long` when adding the heap tops?

It prevents integer overflow when the two middle values are large.

Complexity:
- Insertion: O(log n).
- Find median: O(1).
- Space: O(n).

Note:
Call `findMedian()` only after at least one number has been added. The implementation assumes non-empty input for that operation.

---

## 49. Java PriorityQueue Operations

| Operation | Java method | Typical time |
|---|---|---|
| Insert an element | `offer()` | O(log n) |
| Remove the head | `poll()` | O(log n) |
| Read the head | `peek()` | O(1) |
| Check size | `size()` | O(1) |
| Check whether empty | `isEmpty()` | O(1) |
| Search for an arbitrary element | `contains()` | O(n) |
| Remove an arbitrary element by value | `remove(value)` | O(n) |

Important:
Do not assume arbitrary removal from a Java PriorityQueue is O(log n). Finding the value is generally O(n).

---

## 50. Min-Heap vs Max-Heap

| Problem | Heap choice |
|---|---|
| Kth largest | Min-heap of size K |
| Kth smallest | Max-heap of size K |
| Top K largest | Min-heap of size K |
| Top K smallest | Max-heap of size K |
| Merge K sorted lists | Min-heap |
| Repeatedly combine smallest values | Min-heap |
| Median of a stream | Max-heap + min-heap |
| Always process highest priority | Heap ordered by the required priority |

Why does kth largest use a min-heap?

Because we only keep the largest K elements. The smallest among them is the kth largest overall.

---

## 51. Binary Search vs Heap

| Requirement | Likely pattern |
|---|---|
| Find a target in sorted data | Binary Search |
| Find the first valid position | Boundary Binary Search |
| Find the minimum feasible capacity | Binary Search on Answer |
| Retrieve the smallest repeatedly | Min-heap |
| Maintain the largest K values | Min-heap of size K |
| Find the kth largest once | Heap or Quickselect |
| Keep a running median | Two heaps |
| Merge K sorted lists | Min-heap |

---

## 52. Common Heap / PriorityQueue Mistakes

1. Using a min-heap when the problem needs the opposite ordering.
2. Forgetting to limit the heap to K elements in a top-K solution.
3. Assuming PriorityQueue iteration returns elements in sorted order.
4. Forgetting to define tie-breaking behavior when required.
5. Comparing subtraction in a comparator, which can overflow for extreme integers.
6. Calling `peek()` on an empty queue and assuming a value exists.
7. Assuming arbitrary removal is O(log n).
8. Sorting all values when only a small top K is needed.
9. Forgetting that duplicates count separately unless the problem says distinct values.
10. Forgetting to balance both heaps in the running-median problem.

Prefer:

    Comparator.comparingInt(MyObject::getPriority)

or:

    Integer.compare(a, b)

instead of:

    a - b

when implementing numeric comparisons.

---

# 53. How to Solve an Unfamiliar Problem

## Binary Search

1. Is the data sorted, or is there a monotonic feasibility condition?
2. Am I searching for a target, a boundary, or an optimal answer?
3. What does left represent?
4. What does right represent?
5. When a candidate is feasible, should I keep it and search smaller, or search larger?
6. Does the loop always make progress?
7. What is the complexity of checking one candidate?

## Linked List

1. What does each pointer represent?
2. Can I lose access to the remaining list when changing a link?
3. Would a dummy node simplify head removal or list construction?
4. Do I need Fast & Slow Pointers or a fixed gap?
5. Must I compare node values or node identities?
6. What happens for null, one node, or two nodes?
7. Must I restore the original links afterward?

## Heap / PriorityQueue

1. Do I need the smallest or largest item repeatedly?
2. Do I need all values sorted or only the top K?
3. Should the heap be a min-heap or max-heap?
4. What does each heap entry represent?
5. When should an item be removed?
6. What are the tie-breaking rules?
7. Can I bound the heap size to improve complexity?

---

# 54. Five-Second Revision Table

| If the question says... | Think... |
|---|---|
| Search sorted array | Binary Search |
| First or last occurrence | Boundary Binary Search |
| Minimum feasible speed or capacity | Binary Search on Answer |
| Reverse linked list | Pointer reversal |
| Find middle of list | Fast & Slow |
| Detect cycle | Floyd's algorithm |
| Remove nth from end | Gap pointers + dummy |
| Merge sorted lists | Two pointers |
| Kth largest | Min-heap of size K |
| Kth smallest | Max-heap of size K |
| Top K frequent | HashMap + heap |
| Merge K sorted lists | Min-heap |
| Running median | Two heaps |

---

# 55. Practice Order

## Binary Search

1. Binary Search.
2. First and Last Position of Element.
3. Search Insert Position.
4. Lower Bound and Upper Bound.
5. Search in Rotated Sorted Array.
6. Find Minimum in Rotated Sorted Array.
7. Find Peak Element.
8. Koko Eating Bananas.
9. Capacity to Ship Packages Within D Days.
10. Split Array Largest Sum.

## Linked List

1. Reverse Linked List.
2. Middle of the Linked List.
3. Linked List Cycle.
4. Linked List Cycle II.
5. Remove Nth Node From End.
6. Merge Two Sorted Lists.
7. Linked List Palindrome.
8. Intersection of Two Linked Lists.
9. Reorder List.
10. Reverse Nodes in K-Group.

## Heap / PriorityQueue

1. Kth Largest Element.
2. Kth Smallest Element.
3. Top K Frequent Elements.
4. Last Stone Weight.
5. K Closest Points to Origin.
6. Merge K Sorted Lists.
7. Find Median from Data Stream.
8. Task Scheduler.
9. Reorganize String.
10. Smallest Range Covering Elements from K Lists.

---

# FINAL MEMORY RULE

Binary Search:

**Use a comparison or monotonic condition to eliminate half of the remaining search space.**

Linked List:

**Protect your references, define each pointers role, and change links in the correct order.**

Heap / PriorityQueue:

**Keep the candidate with the highest required priority accessible, and avoid sorting everything when you only need the top K or repeated minimum/maximum values.**
