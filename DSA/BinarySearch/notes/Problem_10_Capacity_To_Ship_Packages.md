# Problem 10 — Capacity To Ship

> **Pattern:** BS on answer  
> **Java file:** `../CapacityToShipPackages.java`  
> **Goal:** Understand every approach — what it does, why we pick it, and which Java method is which.

---

## 1. Problem statement

Ship all packages in order within `days` days. Return minimum ship capacity.

### Example

```text
weights 1..10, days=5 → capacity 15
```

### Constraints (typical)

- `n` up to `10^5` → prefer **O(log n)** when the array is sorted or the answer space is monotone.
- Clarify return type: index, value, or count.

---

## 2. Mental picture

Greedy day count for a fixed capacity is monotone: larger cap → ≤ days needed.

Re-open **`notes/Binary_Search_Common_Patterns.md`** and name the pattern that matches this problem.

---

## 3. How to start thinking

1. State brute force honestly (usually linear scan or linear search on answer).
2. Ask: can I **discard half** of candidates each step? → binary search.
3. For biased BS: on equal `target`, which direction keeps searching?
4. For answer-space BS: write a monotone `feasible(x)` first, then search `x`.

---

## 4. Approach 1 — Brute force

### Idea

Try capacities from max weight to total weight.

### Why mention it?

Correct baseline before you exploit sorted structure or monotonic feasibility.

### Java method

`shipWithinDaysBrute` in `CapacityToShipPackages.java`

### Complexity

| Time | Extra space |
|---|---|
| `O(n·sum)` | `O(1)` |

---

## 5. Approach 2 — Optimal

### Idea

Binary search minimum capacity with `daysNeeded(cap) <= days`.

### Why this approach?

Fits the **Answer-space BS + greedy feasibility.** pattern — logarithmic search instead of rescanning.

### Java method

`shipWithinDays` in `CapacityToShipPackages.java`

### Complexity

| Time | Extra space |
|---|---|
| `O(n log sum)` | `O(1)` |

---


## 6. Hardest part (slow down here)

For **Capacity To Ship Packages**, candidates usually slip on **boundary conditions** and **off-by-one** updates.

- Re-read the optimal method comments in Java.
- Compare your mental model to the pattern section in `Binary_Search_Common_Patterns.md`.
- If you use a map, ask: what is the **key** and what does the **value** mean?

---

## 7. Detailed dry run

Pick the **smallest interesting example** from `main` in the Java file.

| Step | What to write on paper |
|---|---|
| Initialization | Empty map/set, `left/right`, or `prefix=0` + `{0:1}` if prefix problem |
| Each index | Show map/set after the step; or show which half of the array remains in BS |
| Answer | Final return value |

Do this once with the video off. Interviewers care that you can trace without running the IDE.

---

## 8. Why the optimal approach is correct

Give a **one-paragraph proof sketch**:

- Hashing: “When we are at index `i`, the structure contains exactly the information we need from indices `< i`.”
- Prefix map: “Each subarray sum equals difference of two prefix sums; we count valid earlier prefixes.”
- Binary search: “The answer always lies in `[left, right]`; each step discards a half that cannot contain it.”

---

## 9. Complexity comparison

| Approach | Java method | Time | Extra space | Interview? |
|---|---|---|---|---|
| Brute force | `shipWithinDaysBrute` | `O(n·sum)` | `O(1)` | Start verbally |
| Optimal | `shipWithinDays` | `O(n log sum)` | `O(1)` | **Final answer** |


---

## 10. Edge cases

Build a tiny table before you submit:

| Case | Expected behavior |
|---|---|
| Minimum length input | Single element / pair |
| All equal values | Duplicates / freq / first-last BS |
| Negatives (if allowed) | Prefix map still works; sliding sum may not |
| Empty / null | Return `0`, `false`, or `-1` per spec |

---

## 11. Common mistakes

- Using the wrong loop condition (`left < right` vs `left <= right`).
- Forgetting `prefixCount.put(0, 1)` on subarray-sum problems.
- Starting a consecutive walk from **every** element (re-sums chains).
- Returning immediately on first binary-search hit when the problem asks for **first/last** occurrence.

---

## 12. Interview — say this aloud

> “I'll start with brute force to confirm the definition, then optimize. For this problem the right tool is **bs on answer**. I'll maintain [map/set/prefix/left-right] so each step is one pass / logarithmic. Let me trace a small example, then I'll code the optimal solution.”

Then code the **optimal** method from memory, not brute force.

---

## 13. Follow-ups

- Ask yourself: “What if the array were sorted?” “What if we need indices not values?” “What if `k` changes?”
- Link to the **next problem** in this folder’s roadmap in `Binary_Search_Common_Patterns.md`.

---

## 14. Complete runnable Java

Package: `DSA.BinarySearch`  
File: `../CapacityToShipPackages.java`

```java
package DSA.BinarySearch;

/*
Problem: Capacity To Ship Packages Within D Days (binary search on answer)

weights[i] = weight of package on day-order. Ship in order; each day total load <= capacity.
Return minimum capacity to ship all within days days.

Example:
  weights = [1,2,3,4,5,6,7,8,9,10], days = 5  →  15

Approach: BS on capacity between max(weights) and sum(weights);
feasible(cap) = greedy days to ship <= days
Time: O(n log sum)   Space: O(1)
*/

public class CapacityToShipPackages {

    private static int daysNeeded(int[] weights, int capacity) {
        int days = 1;
        int current = 0;
        for (int w : weights) {
            if (current + w > capacity) {
                days++;
                current = 0;
            }
            current += w;
        }
        return days;
    }

    public static int shipWithinDaysBrute(int[] weights, int days) {
        int low = 0;
        int high = 0;
        for (int w : weights) {
            low = Math.max(low, w);
            high += w;
        }
        for (int cap = low; cap <= high; cap++) {
            if (daysNeeded(weights, cap) <= days) {
                return cap;
            }
        }
        return high;
    }

    public static int shipWithinDays(int[] weights, int days) {
        int low = 0;
        int high = 0;
        for (int w : weights) {
            low = Math.max(low, w);
            high += w;
        }
        int best = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (daysNeeded(weights, mid) <= days) {
                best = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] w = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int days = 5;
        System.out.println("Brute: " + shipWithinDaysBrute(w, days));
        System.out.println("BS:    " + shipWithinDays(w, days));

        int[] w2 = { 3, 2, 2, 4, 1, 4 };
        System.out.println("BS:    " + shipWithinDays(w2, 3));
    }
}
```

Run `main` after implementing from memory.

---

## 15. Practice checklist

- [ ] Implement brute force without looking.
- [ ] Implement optimal without looking.
- [ ] Trace one example on paper.
- [ ] State time and space complexity.
- [ ] List three edge cases from section 10.

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

One line: **Capacity To Ship Packages = BS on answer + optimal method in `CapacityToShipPackages.java`.**

---

## 17. Pattern used

See **`Binary_Search_Common_Patterns.md`**.

---

## 18. Next problem

**Next topic in DSA_Interview_Pattern_Roadmap.md**

---

---

## Repository Java (full)

Align with `../CapacityToShipPackages.java`.

```java
package DSA.BinarySearch;

/*
Problem: Capacity To Ship Packages Within D Days (binary search on answer)

weights[i] = weight of package on day-order. Ship in order; each day total load <= capacity.
Return minimum capacity to ship all within days days.

Example:
  weights = [1,2,3,4,5,6,7,8,9,10], days = 5  →  15

Approach: BS on capacity between max(weights) and sum(weights);
feasible(cap) = greedy days to ship <= days
Time: O(n log sum)   Space: O(1)
*/

public class CapacityToShipPackages {

    private static int daysNeeded(int[] weights, int capacity) {
        int days = 1;
        int current = 0;
        for (int w : weights) {
            if (current + w > capacity) {
                days++;
                current = 0;
            }
            current += w;
        }
        return days;
    }

    public static int shipWithinDaysBrute(int[] weights, int days) {
        int low = 0;
        int high = 0;
        for (int w : weights) {
            low = Math.max(low, w);
            high += w;
        }
        for (int cap = low; cap <= high; cap++) {
            if (daysNeeded(weights, cap) <= days) {
                return cap;
            }
        }
        return high;
    }

    public static int shipWithinDays(int[] weights, int days) {
        int low = 0;
        int high = 0;
        for (int w : weights) {
            low = Math.max(low, w);
            high += w;
        }
        int best = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (daysNeeded(weights, mid) <= days) {
                best = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] w = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int days = 5;
        System.out.println("Brute: " + shipWithinDaysBrute(w, days));
        System.out.println("BS:    " + shipWithinDays(w, days));

        int[] w2 = { 3, 2, 2, 4, 1, 4 };
        System.out.println("BS:    " + shipWithinDays(w2, 3));
    }
}
```
