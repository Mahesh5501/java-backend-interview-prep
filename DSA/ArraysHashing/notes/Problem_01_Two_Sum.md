# Problem 01 — Two Sum

> **Pattern:** HashMap complement  
> **Goal:** Interview-ready: mental model, brute force, optimal approach, dry run, complexity, edge cases, and what to say aloud.

---

## 1. Problem statement

Given `nums` and `target`, return two indices whose values sum to `target`.

Work through the examples in the matching `.java` file `main` method and on LeetCode (if applicable).

### Constraints (typical)

- Input sizes up to `10^5` (sometimes `10^4`) — aim for **O(n)** or **O(n log n)** unless constraints are tiny.
- Read whether the problem wants **indices**, **values**, or **counts**.

---

## 2. Mental picture

Before coding, draw the array and ask: **what do I need to remember from earlier positions?**

- If you need “the other value that completes a pair” → **hash map / set**.
- If you need “sum of a contiguous block” with negatives allowed → **prefix sum + frequency map**, not sliding window by default.
- If the array is **sorted** and you discard half the range → **binary search**, not hashing.

Re-open **`notes/Arrays_Hashing_Common_Patterns.md`** and name the pattern letter (A–H for hashing, A–G for binary search) that matches this problem.

---

## 3. How to start thinking

1. State brute force complexity honestly (often `O(n²)`).
2. Identify the **bottleneck** (re-scanning, re-multiplying, re-walking chains).
3. Choose the structure: `Map`, `Set`, prefix map, or binary search bounds.
4. Write the **invariant** in one sentence before coding.

---

## 4. Approach 1 — Brute force

Implement the definition directly: nested loops, full rescans, or linear search on sorted data.

**Why mention it in interviews:** It proves you understand the problem and gives a correct baseline before optimizing.

**Typical complexity:** `O(n²)` or `O(n)` on sorted data — too slow when `n` is large.

---

## 5. Approach 2 — Optimal

Open the optimal method in the Java file below. While reading:

1. Name each variable (`prefix`, `seen`, `left`, `right`, …).
2. Say why each update is safe (no missed answers).
3. Trace one small example on paper (see section 7).

**Target complexity:** See section 9 — usually **O(n)** or **O(log n)** with **O(n)** or **O(1)** extra space.

---

## 6. Hardest part (slow down here)

For **Two Sum**, candidates usually slip on **boundary conditions** and **off-by-one** updates.

- Re-read the optimal method comments in Java.
- Compare your mental model to the pattern section in `Arrays_Hashing_Common_Patterns.md`.
- If you use a map, ask: what is the **key** and what does the **value** mean?

---

## 7. Detailed dry run

`nums = [3, 2, 4]`, `target = 6`

| i | nums[i] | complement (6 − x) | map after step | result |
|---:|---:|---:|---|---|
| 0 | 3 | 3 | {3→0} | continue |
| 1 | 2 | 4 | {3→0, 2→1} | continue |
| 2 | 4 | 2 | {3→0, 2→1} | **2 in map** → return [1, 2] |

Rule: **check complement before** `put`, so you never reuse the same index twice.

---

## 8. Why the optimal approach is correct

Give a **one-paragraph proof sketch**:

- Hashing: “When we are at index `i`, the structure contains exactly the information we need from indices `< i`.”
- Prefix map: “Each subarray sum equals difference of two prefix sums; we count valid earlier prefixes.”
- Binary search: “The answer always lies in `[left, right]`; each step discards a half that cannot contain it.”

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute | See your brute implementation | Usually `O(1)` |
| Optimal | Per pattern cheat sheet in `Arrays_Hashing_Common_Patterns.md` | Usually `O(n)` for hash structures |

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

> “I'll start with brute force to confirm the definition, then optimize. For this problem the right tool is **hashmap complement**. I'll maintain [map/set/prefix/left-right] so each step is one pass / logarithmic. Let me trace a small example, then I'll code the optimal solution.”

Then code the **optimal** method from memory, not brute force.

---

## 13. Follow-ups

- Ask yourself: “What if the array were sorted?” “What if we need indices not values?” “What if `k` changes?”
- Link to the **next problem** in this folder’s roadmap in `Arrays_Hashing_Common_Patterns.md`.

---

## 14. Complete runnable Java

Package: `DSA.ArraysHashing`  
File: `../TwoSum.java`

```java
package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Problem: Two Sum

Given an integer array nums and an integer target, return indices i and j
such that nums[i] + nums[j] == target and i != j. Exactly one solution exists.

Examples:
  nums = [2, 7, 11, 15], target = 9  →  [0, 1]
  nums = [3, 2, 4], target = 6       →  [1, 2]

Approach: One pass HashMap — complement lookup
Time: O(n)   Space: O(n)
*/

public class TwoSum {

    // BRUTE: check every pair
    public static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    // OPTIMAL: store value → index; look for target - nums[i]
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> valueToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (valueToIndex.containsKey(complement)) {
                return new int[] { valueToIndex.get(complement), i };
            }
            valueToIndex.put(nums[i], i);
        }
        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] a = { 2, 7, 11, 15 };
        System.out.println("Brute:  " + Arrays.toString(twoSumBruteForce(a, 9)));
        System.out.println("Optimal:" + Arrays.toString(twoSum(a, 9)));

        int[] b = { 3, 2, 4 };
        System.out.println("Optimal:" + Arrays.toString(twoSum(b, 6)));
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

One line: **Two Sum = HashMap complement + optimal method in `TwoSum.java`.**

---

## 17. Pattern used

See **`Arrays_Hashing_Common_Patterns.md`**.

---

## 18. Next problem

**Problem 02 — Contains Duplicate**

---

---

## Repository Java (full)

Align with `../TwoSum.java`.

```java
package DSA.ArraysHashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Problem: Two Sum

Given an integer array nums and an integer target, return indices i and j
such that nums[i] + nums[j] == target and i != j. Exactly one solution exists.

Examples:
  nums = [2, 7, 11, 15], target = 9  →  [0, 1]
  nums = [3, 2, 4], target = 6       →  [1, 2]

Approach: One pass HashMap — complement lookup
Time: O(n)   Space: O(n)
*/

public class TwoSum {

    // BRUTE: check every pair
    public static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    // OPTIMAL: store value → index; look for target - nums[i]
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> valueToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (valueToIndex.containsKey(complement)) {
                return new int[] { valueToIndex.get(complement), i };
            }
            valueToIndex.put(nums[i], i);
        }
        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] a = { 2, 7, 11, 15 };
        System.out.println("Brute:  " + Arrays.toString(twoSumBruteForce(a, 9)));
        System.out.println("Optimal:" + Arrays.toString(twoSum(a, 9)));

        int[] b = { 3, 2, 4 };
        System.out.println("Optimal:" + Arrays.toString(twoSum(b, 6)));
    }
}
```
