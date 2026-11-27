# Problem 05 — Move Zeroes

> **Pattern:** Slow / Fast (Read / Write) Two Pointers  
> **Goal:** Move every `0` to the end **in-place**, while keeping the **same order** for all non-zero numbers.

---

## 1. Problem statement

Given an integer array `nums`, move all `0`s to the end of the array.

You must do this **in-place** (modify the same array; do not return a new array).

The **relative order** of the non-zero elements must stay the same.

### Example 1

```text
Input:  nums = [0, 1, 0, 3, 12]
Output:        [1, 3, 12, 0, 0]
```

### Example 2

```text
Input:  nums = [0]
Output:        [0]
```

### Constraints

- `1 <= nums.length <= 10^4`
- `-2^31 <= nums[i] <= 2^31 - 1`

**Follow-up:** Could you minimise the total number of operations? (We aim for a single pass.)

---

## 2. Mental picture — one picture to keep in mind

Think of the array as a line of boxes.

```text
[ 0 | 1 | 0 | 3 | 12 ]
  ^
  start
```

You want:

```text
[ 1 | 3 | 12 | 0 | 0 ]
   ^^^^^^^^^  non-zeros kept in order
              ^^^^^  zeros pushed to the tail
```

Read it as:

> **One finger reads every value. Another finger marks where the next non-zero should be written.**

That is the read/write (slow/fast) pattern.

```text
         READ  ------>
nums:   [ 0   1   0   3  12 ]
         ^
       WRITE starts here
```

---

## 3. How to start thinking (simple questions)

Before coding, answer these in plain English:

1. **What must stay the same?** The order of `1, 3, 12` — not swap `3` and `12`.
2. **What may change?** Position of zeros only.
3. **Can I use another array?** Yes for learning — but LeetCode wants in-place.
4. **What is the simplest correct idea?** Collect non-zeros, then pad with zeros.
5. **Can I do that in one array?** Yes — use one index to **write** non-zeros, then fill the rest.

If you can explain “read every cell, write non-zeros to the front, zero the tail,” you understand the problem.

---

## 4. Approach 1 — Brute force (extra array)

### Idea

1. Scan the array and copy every non-zero into a **new** temporary array.
2. Put `0` in all remaining slots of the temp array.
3. Copy the temp array back into `nums`.

### Why does it work?

You visit values in left-to-right order. Non-zeros land in the temp array in that same order. Zeros fill the end. So the output rules are satisfied.

### Java method

`moveZeroesBruteForce` in `MoveZeroes.java`

```java
public static void moveZeroesBruteForce(int[] nums) {

    int[] temp = new int[nums.length];
    int tempIndex = 0;

    for (int value : nums) {
        if (value != 0) {
            temp[tempIndex] = value;
            tempIndex++;
        }
    }
    // Remaining temp slots are already 0 (default for new int[]).

    for (int i = 0; i < nums.length; i++) {
        nums[i] = temp[i];
    }
}
```

### Dry run

```text
Input: [0, 1, 0, 3, 12]

Scan → copy non-zeros in order → temp = [1, 3, 12, 0, 0]
Copy back → nums = [1, 3, 12, 0, 0]
```

| Step | `value` | `temp` after |
|---:|---|---|
| see `0` | skip | `[_, _, _, _, _]` |
| see `1` | write | `[1, _, _, _, _]` |
| see `0` | skip | same |
| see `3` | write | `[1, 3, _, _, _]` |
| see `12` | write | `[1, 3, 12, _, _]` |

After padding: `[1, 3, 12, 0, 0]`.

### Complexity

| | Brute (extra array) |
|---|---|
| **Time** | `O(n)` — one scan to fill temp, one scan to copy back |
| **Extra space** | `O(n)` — the temporary array |

### Limitation

Correct, but uses `O(n)` extra memory. Interviews and follow-ups want **in-place** with `O(1)` extra space.

---

## 5. Approach 2 — Optimal: read / write two pointers

### Step-by-step

1. Set `writeIndex = 0`. This is the slow pointer — “next slot for a non-zero.”
2. Loop `fastIndex` from `0` to `n - 1` (the read pointer).
3. If `nums[fastIndex] != 0`, assign `nums[writeIndex] = nums[fastIndex]`, then `writeIndex++`.
4. After the loop, set `nums[writeIndex..n-1] = 0`.

### Visual frames

**Start**

```text
index:  0  1  2  3   4
nums:   0  1  0  3  12
        W  R
```

**After reading `1`**

```text
nums:   1  1  0  3  12   (write 1 at index 0; W → 1)
           W  R
```

**After reading `3` and `12`**

```text
nums:   1  3  12  3  12   (non-zeros packed at front; W = 3)
                 W     R (done)
```

**Zero the tail**

```text
nums:   1  3  12  0  0
```

### Why each pointer moves

| Pointer | Role | When it moves |
|---|---|---|
| **fast (read)** | Looks at every element | Every iteration: `fastIndex++` |
| **slow (write)** | Where the next kept value goes | Only when you find a non-zero |

Zeros are **not** written during the scan. They are “left behind” or overwritten later. The final loop paints the tail with `0`.

### Java method

`moveZeroesTwoPointers` in `MoveZeroes.java` (optimal)

```java
public static void moveZeroesTwoPointers(int[] nums) {

    int writeIndex = 0;

    for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {
        if (nums[fastIndex] != 0) {
            nums[writeIndex] = nums[fastIndex];
            writeIndex++;
        }
    }

    while (writeIndex < nums.length) {
        nums[writeIndex] = 0;
        writeIndex++;
    }
}
```

---

## 6. Hardest part — overwriting before you finish reading

### The worry

“When I write `nums[writeIndex] = nums[fastIndex]`, don’t I destroy a value I still need?”

### Before / after example

```text
nums = [0, 1, 0, 3, 12]
```

When `fastIndex = 1`, we write `1` at index `0`. Index `0` was `0` — we did not lose a non-zero.

When `fastIndex = 3`, `writeIndex = 2`. We write `3` at index `2`. Index `2` was `0` — safe.

**Rule:** `writeIndex` is always **at or behind** `fastIndex`. Any cell we overwrite has already been **read** by `fastIndex`, or was a zero we do not need to keep.

### When write equals read

```text
nums = [1, 2, 3]   (no zeros)
```

`writeIndex` and `fastIndex` move together. Assignment `nums[i] = nums[i]` is harmless.

---

## 7. Detailed dry run with role diagram

Input: `[0, 1, 0, 3, 12]`

```text
Legend:  W = writeIndex    R = fastIndex
```

| R | nums (conceptual) | Action | W after |
|---:|---|---|---:|
| 0 | `[0,1,0,3,12]` | `0` → skip | 0 |
| 1 | same | write `1` at W=0 | 1 |
| 2 | `[1,1,0,3,12]` | `0` → skip | 1 |
| 3 | same | write `3` at W=1 | 2 |
| 4 | `[1,3,0,3,12]` | write `12` at W=2 | 3 |

Zero-fill from W=3:

```text
[1, 3, 12, 0, 0]  ✓
```

Role diagram at `R = 3`:

```text
[ 1   3   0   3  12 ]
      W       R
      |       |
   "next      "currently
    hole"     reading"
```

---

## 8. Why the algorithm is correct

**Order preserved:** We visit indices in increasing order. Each non-zero is written to the next available slot. So non-zeros appear in the same relative order as in the input.

**All zeros at end:** After packing, `writeIndex` is the count of non-zeros. Everything from that index onward is set to `0`. So no non-zero sits after a zero in the final array.

**In-place:** Only a few index variables plus overwriting within `nums`.

---

## 9. Complexity comparison

| Method | Time | Extra space | In-place? |
|---|---:|---:|---|
| Extra temp array | `O(n)` | `O(n)` | Modifies `nums`, but uses helper array |
| Read / write pointers | `O(n)` | `O(1)` | Yes |

Both are linear time. The two-pointer version wins on **space**.

---

## 10. Edge cases

| Input | Expected | Note |
|---|---|---|
| `[0]` | `[0]` | One zero — already valid |
| `[1]` | `[1]` | No zeros |
| `[0,0,0]` | `[0,0,0]` | All zeros |
| `[1,2,3]` | `[1,2,3]` | Write chases read |
| `[0,0,1]` | `[1,0,0]` | Single non-zero at end |
| negative values | order kept | Only `== 0` matters |

---

## 11. Common mistakes

| Mistake | What goes wrong |
|---|---|
| Using **swap** for every non-zero | Can break relative order (e.g. swapping distant zeros) |
| Forgetting the **final zero-fill** | Zeros stay in the middle |
| Incrementing `writeIndex` on zero | Holes or wrong count |
| Returning a **new array** | Violates in-place requirement |
| Nested loops to “bubble” zeros | `O(n²)` — too slow |

### Optional swap variant (minimise writes)

Some solutions swap when `fastIndex != writeIndex`. Still correct for this problem, but **assign + zero-fill** is easier for beginners.

---

## 12. How to recognise this pattern

**Signals in the problem text:**

- “Move X to the end” or “partition” the array
- **In-place** with stable order for the other elements
- One pass preferred

**Compare to earlier problems:**

| Problem | Pointer style | What moves |
|---|---|---|
| Valid Palindrome | left / right opposite | Inward compare |
| Two Sum II | left / right opposite | Shrink by sum |
| 3Sum | fixed + left / right | Search pairs |
| **Move Zeroes** | write + read same direction | Pack non-zeros |

Same family (two pointers), new job: **one pointer writes, one reads**.

---

## 13. Interview explanation — say this aloud

> “I use a write index for the next non-zero and a read index that scans the whole array. Whenever I see a non-zero, I place it at the write index and advance write. Zeros are skipped during the scan. After the scan, I fill from the write index to the end with zero. One pass, O(n) time, O(1) extra space, and order of non-zeros is preserved.”

---

## 14. Interview follow-up Q&A

**Q: Can you do it with swaps only?**  
A: Yes — swap non-zero to write index when they differ, then zero-fill or swap zeros forward. Must still preserve order.

**Q: What counts as an operation?**  
A: Assignments or swaps; follow-up often wants **minimal writes**. Swap version can reduce assignments when few zeros.

**Q: Does this work for “move all X to end” for any value X?**  
A: Same pattern — treat `value == X` as “skip,” pack others.

**Q: Stable vs unstable?**  
A: This approach is **stable** for non-zeros — order unchanged.

---

## 15. Complete runnable Java class

Full source: `../MoveZeroes.java`. Copy below next to your other Two Pointers classes.

```java


import java.util.Arrays;

public class MoveZeroes {

    public static void moveZeroesBruteForce(int[] nums) {
        int[] temp = new int[nums.length];
        int tempIndex = 0;
        for (int value : nums) {
            if (value != 0) {
                temp[tempIndex++] = value;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = temp[i];
        }
    }

    public static void moveZeroesTwoPointers(int[] nums) {
        int writeIndex = 0;
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++) {
            if (nums[fastIndex] != 0) {
                nums[writeIndex++] = nums[fastIndex];
            }
        }
        while (writeIndex < nums.length) {
            nums[writeIndex++] = 0;
        }
    }

    public static void main(String[] args) {
        int[] example = { 0, 1, 0, 3, 12 };
        int[] bruteInput = example.clone();
        int[] optimalInput = example.clone();
        System.out.println("Input:              " + Arrays.toString(example));
        moveZeroesBruteForce(bruteInput);
        System.out.println("Brute Force (extra): " + Arrays.toString(bruteInput));
        moveZeroesTwoPointers(optimalInput);
        System.out.println("Two Pointers:        " + Arrays.toString(optimalInput));
    }
}
```

```text
Input:              [0, 1, 0, 3, 12]
Brute Force (extra): [1, 3, 12, 0, 0]
Two Pointers:        [1, 3, 12, 0, 0]
```

---

## 16. Independent IDE practice checklist

- [ ] Implement brute force with a temp array.
- [ ] Implement read / write pointers without looking at notes.
- [ ] Trace `[0, 1, 0, 3, 12]` by hand with W and R on paper.
- [ ] Explain why overwriting at `writeIndex` is safe.
- [ ] Handle `[0]`, `[1,2,3]`, `[0,0,1]` without bugs.
- [ ] State time and space complexity aloud.

### Small challenge

Trace **without running code**:

```text
nums = [0, 0, 0, 1, 2, 0, 3]
```

Write down:

1. `nums` after the read loop (before zero-fill).
2. Final array.
3. Final `writeIndex` before zero-fill.

---

## 17. Quick revision

1. **Read** scans all indices once.
2. **Write** only advances on non-zero.
3. **Zero-fill** the tail after packing.
4. **`O(n)` time**, **`O(1)` space**.
5. **Do not swap** if you care about stable order (beginner-safe rule).

One line:

> **Pack non-zeros with write, then paint the rest with zero.**

---

## 18. Pattern used in this problem

**Same-direction slow / fast (read / write) pointers.**

Reusable rule from `Two_Pointers_Common_Patterns.md`:

> When you must **filter** or **compact** in-place while keeping order, let **read** explore and **write** commit.

---

## 19. Link to Java — which method is which

| Approach | Method in `MoveZeroes.java` |
|---|---|
| Extra array copy | `moveZeroesBruteForce` |
| Read / write pointers (optimal) | `moveZeroesTwoPointers` |

Run `main` to print both approaches on sample input.

---

## 20. Next problem

**Problem 06 — Remove Duplicates from Sorted Array**  
Main concept: read / write again — but now you return the **new length** and the array is **sorted**.