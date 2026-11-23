# Problem 06 — Remove Duplicates from Sorted Array

> **Pattern:** Read / Write Two Pointers  
> **Goal:** Delete duplicates **in-place** from a **sorted** array and return how many **unique** values remain at the front.

---

## 1. Problem statement

Given an integer array `nums` sorted in **non-decreasing order**, remove duplicates **in-place**.

The relative order of elements must stay the same — but for a sorted array, “order” really means “each distinct value appears once, from small to large.”

Return `k`, the number of unique elements.

The first `k` slots of `nums` must hold those unique values in order. Anything beyond index `k - 1` does not matter.

### Example

```text
Input:  nums = [0,0,1,1,1,2,2,3,3,4]
Output: k = 5
        nums[0..4] = [0,1,2,3,4]   (rest ignored)
```

### Example 2

```text
Input:  nums = [1,1,2]
Output: k = 2
        nums[0..1] = [1,2]
```

### Constraints

- `1 <= nums.length <= 3 * 10^4`
- `-100 <= nums[i] <= 100`
- `nums` is sorted in **non-decreasing** order

---

## 2. Mental picture — one picture to keep in mind

Sorted array = equal numbers sit **next to each other** in a group.

```text
[ 0  0 | 1  1  1 | 2  2 | 3  3 | 4 ]
  ^^^^   ^^^^^^^^^   ^^^   ^^^   ^
 group  group       ...   ...  last unique grows left to right
```

Imagine two fingers:

```text
READ  ------>  scans every cell
WRITE ----->  marks end of "unique prefix"
```

```text
nums:  [ 0  0  1  1  1  2  2  3  3  4 ]
        W  R
```

**Write** = “I trust everything from `0..writeIndex` — no duplicates inside.”  
**Read** = “Show me the next candidate.”

---

## 3. How to start thinking (simple questions)

1. **Why is the array sorted important?** Duplicates are **adjacent**. You only compare with the **last kept** value.
2. **What do I return?** A **count** `k`, not a new array.
3. **What must `nums` look like?** First `k` cells = one copy of each distinct value, smallest to largest.
4. **Brute idea?** Copy values into a set/list, write back.
5. **Better idea?** One pass: if read sees a **new** value, extend the unique prefix.

---

## 4. Approach 1 — Brute force (extra set or list)

### Idea

1. Walk through `nums`.
2. Add each value to a `LinkedHashSet` (keeps order, removes dupes).
3. Copy set back into `nums` from index `0`.
4. Return set size.

### Why does it work?

A set never stores the same value twice. LinkedHashSet keeps **first-seen order**, which for a sorted input is still `0,1,2,3,4`.

### Java

```java
public static int removeDuplicatesBruteForce(int[] nums) {

    Set<Integer> uniqueValues = new LinkedHashSet<>();

    for (int value : nums) {
        uniqueValues.add(value);
    }

    int index = 0;
    for (int value : uniqueValues) {
        nums[index] = value;
        index++;
    }

    return uniqueValues.size();
}
```

### Dry run

```text
Input: [0,0,1,1,1,2,2,3,3,4]

Set after scan (insertion order): [0, 1, 2, 3, 4]
Copy to nums front → [0,1,2,3,4,...]
Return 5
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n)` |
| **Extra space** | `O(n)` for the set |

### Limitation

Uses extra memory. We want **`O(1)`** extra space using the fact that input is **sorted**.

---

## 5. Approach 2 — Optimal: read / write pointers

### Step-by-step

1. If `nums.length == 0`, return `0`.
2. `writeIndex = 0` — first element is always unique.
3. `readIndex` runs from `1` to `n - 1`.
4. If `nums[readIndex] != nums[writeIndex]`, we found a **new** value:
   - `writeIndex++`
   - `nums[writeIndex] = nums[readIndex]`
5. Return `writeIndex + 1`.

### Visual frames

**Start** — first `0` is kept

```text
[ 0  0  1  1  1  2  2  3  3  4 ]
  W     R
```

**Read sees another `0`** — same as `nums[W]` → skip

```text
[ 0  0  1  1  1  2  2  3  3  4 ]
  W        R
```

**Read sees `1`** — different → W++, write `1`

```text
[ 0  1  1  1  1  2  2  3  3  4 ]
     W        R
```

**Eventually**

```text
[ 0  1  2  3  4  2  2  3  3  4 ]
              W
```

Return `W + 1 = 5`.

### Why each pointer moves

| Pointer | Moves when |
|---|---|
| **readIndex** | Every loop step (+1) |
| **writeIndex** | Only when you commit a **new** distinct value |

Duplicate values only move **read**. They never move **write**.

### Java

```java
public static int removeDuplicatesTwoPointers(int[] nums) {

    if (nums.length == 0) {
        return 0;
    }

    int writeIndex = 0;

    for (int readIndex = 1; readIndex < nums.length; readIndex++) {
        if (nums[readIndex] != nums[writeIndex]) {
            writeIndex++;
            nums[writeIndex] = nums[readIndex];
        }
    }

    return writeIndex + 1;
}
```

---

## 6. Hardest part — why compare only to `nums[writeIndex]`?

### The worry

“Should I compare `read` to `read - 1`? Or scan the whole prefix?”

### Because array is sorted

All duplicates of the current value form **one contiguous block**.

```text
... 1  1  1  2 ...
        ^^^^^
     one block of 1s
```

If `nums[read] == nums[writeIndex]`, it is still the **same** value we already stored at the end of the unique prefix.

If `nums[read] != nums[writeIndex]`, sorted order guarantees it is a **new** larger value — never a duplicate of something earlier in the array.

### Before / after

**Wrong idea** — compare only neighbours:

```text
[ 0  1  0 ]   // not sorted — neighbour compare fails
```

**This problem** — input is sorted, so **compare to last kept** is enough:

```text
[ 0  0  1 ]   compare read to writeIndex value ✓
```

---

## 7. Detailed dry run with role diagram

Input: `[0,0,1,1,1,2,2,3,3,4]`

| read | nums[read] | nums[write] | Action | write after |
|---:|---|---|---|---:|
| 1 | 0 | 0 | equal → skip | 0 |
| 2 | 1 | 0 | new → W=1, store 1 | 1 |
| 3 | 1 | 1 | equal | 1 |
| 4 | 1 | 1 | equal | 1 |
| 5 | 2 | 1 | new → W=2, store 2 | 2 |
| 6 | 2 | 2 | equal | 2 |
| 7 | 3 | 2 | new → W=3 | 3 |
| 8 | 3 | 3 | equal | 3 |
| 9 | 4 | 3 | new → W=4 | 4 |

Return `5`. Prefix: `[0,1,2,3,4]`.

Role diagram at `read = 5`:

```text
[ 0  1  2  1  1  2  ... ]
        W           R
        |           |
   last unique   seeing 2 again
   value is 1    (will skip until
                  2 at W)
```

(Intermediate array may show old duplicates in the tail — that is OK.)

---

## 8. Why the algorithm is correct

**No duplicates in prefix:** `writeIndex` advances only when the read value differs from the last kept value. Equal values never get a second slot.

**No missing uniques:** `read` visits every index. Every distinct value appears as the **first** cell of its sorted block; that moment triggers a write.

**Sorted order:** We only append **larger** new values at the end of the prefix. So the prefix stays sorted.

---

## 9. Complexity comparison

| Method | Time | Extra space | Uses sorted property? |
|---|---:|---:|---|
| HashSet / list | `O(n)` | `O(n)` | No |
| Read / write | `O(n)` | `O(1)` | **Yes** |

---

## 10. Edge cases

| Input | Output k | Prefix |
|---|---:|---|
| `[1]` | 1 | `[1]` |
| `[1,1,1]` | 1 | `[1]` |
| `[1,2,3]` | 3 | `[1,2,3]` |
| `[-100,-100,0,0,100]` | 3 | `[-100,0,100]` |
| length `30000` | varies | single pass still OK |

---

## 11. Common mistakes

| Mistake | Result |
|---|---|
| Starting `writeIndex` at `1` without special case | Off-by-one on `[1]` |
| Comparing `read` with `read-1` only | Works on sorted input, but **writeIndex** comparison is the standard pattern |
| `writeIndex++` **before** assigning | Skips a slot or drops a value |
| Returning `writeIndex` instead of `writeIndex + 1` | Wrong length |
| Expecting tail of array to be cleaned | LeetCode ignores cells after `k` |

---

## 12. How to recognise this pattern

**Signals:**

- Sorted array + remove duplicates / in-place compression
- Return **length** or new size, not full array
- “Unique prefix” language

**Compare to Problem 05 (Move Zeroes):**

| | Move Zeroes | Remove Duplicates |
|---|---|---|
| Condition | `!= 0` | `!= nums[writeIndex]` |
| After scan | zero-fill tail | return `write+1` |
| Needs sorted? | No | **Yes** |

Both are **read / write** in the same direction.

---

## 13. Interview explanation — say this aloud

> “Because the array is sorted, duplicates are adjacent. I keep a write index at the end of the unique prefix, starting at zero. The read index scans from one. When nums[read] differs from nums[write], I found a new value — I advance write and copy it there. Duplicates only advance read. At the end I return write plus one. One pass, O(n) time, O(1) extra space.”

---

## 14. Interview follow-up Q&A

**Q: What if the array is not sorted?**  
A: Neighbour compare fails. Use a set (`O(n)` space) or sort first (`O(n log n)`).

**Q: Remove duplicates and return count **with at most two** of each?**  
A: LeetCode 80 — same idea, but allow write only when count rule passes.

**Q: Can write stay still while read runs?**  
A: Yes — during a duplicate block, write does not move.

**Q: In-place deletion in unsorted array?**  
A: Different problem — often hash set or two passes.

---

## 15. Complete runnable Java class

Full source: `../RemoveDuplicatesFromSortedArray.java`.

```java


import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class RemoveDuplicatesFromSortedArray {

    public static int removeDuplicatesBruteForce(int[] nums) {
        Set<Integer> uniqueValues = new LinkedHashSet<>();
        for (int value : nums) {
            uniqueValues.add(value);
        }
        int index = 0;
        for (int value : uniqueValues) {
            nums[index++] = value;
        }
        return uniqueValues.size();
    }

    public static int removeDuplicatesTwoPointers(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int writeIndex = 0;
        for (int readIndex = 1; readIndex < nums.length; readIndex++) {
            if (nums[readIndex] != nums[writeIndex]) {
                nums[++writeIndex] = nums[readIndex];
            }
        }
        return writeIndex + 1;
    }

    public static void main(String[] args) {
        int[] example = { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };
        int[] optimalInput = example.clone();
        int k = removeDuplicatesTwoPointers(optimalInput);
        System.out.println("Input: " + Arrays.toString(example));
        System.out.println("k = " + k + ", prefix = "
                + Arrays.toString(Arrays.copyOf(optimalInput, k)));
    }
}
```

```text
Input: [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
k = 5, prefix = [0, 1, 2, 3, 4]
```

---

## 16. Independent IDE practice checklist

- [ ] Brute force with `LinkedHashSet`.
- [ ] Optimal version from memory.
- [ ] Trace `[0,0,1,1,1,2,2,3,3,4]` on paper.
- [ ] Explain why sorted input lets you compare only to `nums[writeIndex]`.
- [ ] Handle `[1]`, `[1,1,1]`, `[1,2,3]`.
- [ ] Say time/space out loud.

### Small challenge

Without code, trace:

```text
nums = [1, 1, 2, 2, 2, 3]
```

List every step where `writeIndex` changes and the array prefix after each change.

---

## 17. Quick revision

1. **Sorted** → dupes are **groups**.
2. **write** = last unique index in prefix.
3. New value → `write++`, assign, else only `read++`.
4. Return **`writeIndex + 1`**.
5. **`O(n)`** time, **`O(1)`** space.

One line:

> **Walk read; whenever the value beats the last kept, extend write.**

---

## 18. Pattern used in this problem

**Same-direction read / write** — compress array to unique prefix.

Related pattern name: **two pointers for in-place filtering** (also used in Move Zeroes).

---

## 19. Next problem

**Problem 07 — Valid Palindrome II**  
Main concept: opposite pointers again, plus **one allowed skip** when characters mismatch (branching).