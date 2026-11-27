# Problem 06 — Max Consecutive Ones III

> **Pattern:** Variable-size sliding window — **at most K** zeros inside  
> **LeetCode:** 1004  
> **Goal:** Longest contiguous subarray of 1’s if you may flip at most `k` zeros to 1’s.  
> **Java file:** `../MaxConsecutiveOnesIII.java`  

---

## 1. Problem statement

You are given a binary array `nums` (only `0` and `1`) and an integer `k`.

Return the **maximum number of consecutive 1’s** in the array if you can flip **at most** `k` `0`’s to `1`’s.

### Example

```text
Input:  nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
Output: 6

Explanation:
Flip zeros at indices 3 and 4 (or other valid pair) inside window [3..8]:
[1,1,1,0,0,0,1,1,1,1,0]
        ^--------6 ones--------^
```

### Constraints

- `1 <= nums.length <= 10^5`
- `nums[i]` is `0` or `1`
- `0 <= k <= nums.length`

---

## 2. Mental picture — one picture to keep in mind

Think of a **stretch of road** with potholes (`0`) and smooth pavement (`1`).

You are allowed to **fill at most `k` potholes** inside whatever stretch you choose.

```text
nums:  1  1  1  0  0  0  1  1  1  1  0
       |-------- window --------|
       L                    R

Inside window: count zeros
If zeros > k  →  shrink from L
If zeros ≤ k  →  window is legal; maybe record longest length
```

Two fingers:

- **right** — always moves forward (try to include the next cell)
- **left** — moves only when the window has **too many** zeros

You are **not** literally flipping bits in the array. You are asking: “What is the longest section where at most `k` zeros could be flipped?”

> **Read it as:** “Grow the window; if more than `k` zeros, kick out from the left until valid again.”

---

## 3. How to start thinking

1. **Continuous?** Yes — subarray.
2. **Constraint?** At most `k` zeros — classic **“at most K”** variable window.
3. **Want longest or shortest?** **Longest** valid window.
4. **State to track?** Count of zeros in `[left..right]`.
5. **When to shrink?** While `zeroCount > k`.

Same template as “at most 2 distinct fruits” — only the validity test changes.

---

## 4. Approach 1 — Brute force (every subarray)

### Java method

`longestOnesBruteForce`

### Idea

For every start index, extend `end` until zeros exceed `k`. Track best length.

### Why start here?

Shows you understand the definition without window tricks.

### Java (sketch)

```java
for (int start = 0; start < n; start++) {
    int zeros = 0;
    for (int end = start; end < n; end++) {
        if (nums[end] == 0) zeros++;
        if (zeros > k) break;
        best = Math.max(best, end - start + 1);
    }
}
```

### Dry run (tiny)

```text
nums = [1,0,1,1,0], k = 1

start=0: lengths 1,2,3 (window [1,0,1] has 1 zero) then break on second zero
...
best found by exhaustive scan
```

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n^2)` |
| **Space** | `O(1)` |

### Limitation

Too slow for `n = 10^5`. Need each index to enter/leave once.

---

## 5. Approach 2 — Optimal sliding window

### Java method

`longestOnes`

### Step-by-step

1. `left = 0`, `zeroCount = 0`, `best = 0`.
2. For each `right`:
   - If `nums[right] == 0`, increment `zeroCount`.
   - **While** `zeroCount > k`:
     - If `nums[left] == 0`, decrement `zeroCount`.
     - `left++`.
   - `best = max(best, right - left + 1)`.
3. Return `best`.

### Visual frame (main example)

When `right` reaches the long block of 1’s with only 2 zeros inside:

```text
... 1  1  1  1  0
    L           R     zeroCount = 2 ≤ k  →  length 6 is valid
```

### Why each pointer moves

| Pointer | When it moves |
|---|---|
| **right** | Every iteration (+1) — expands window |
| **left** | Only while `zeroCount > k` — restores validity |

Each index enters once and leaves at most once → `O(n)`.

### Java

Full runnable class: `../MaxConsecutiveOnesIII.java`

```java
public static int longestOnes(int[] nums, int k) {
    int left = 0, zeroCount = 0, best = 0;
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] == 0) zeroCount++;
        while (zeroCount > k) {
            if (nums[left] == 0) zeroCount--;
            left++;
        }
        best = Math.max(best, right - left + 1);
    }
    return best;
}
```

---

## 6. Hardest part — “flip” vs “at most k zeros”

### Before (confusing)

“I need to track which zeros I flipped and update the array.”

That leads to branching and re-flipping — messy.

### After (clean)

**Flipping at most k zeros** inside a window is equivalent to:

> The window may contain **at most k zeros**; everything else can be treated as 1’s.

You never mutate `nums`. You only **count zeros**. If count ≤ `k`, the window **could** be turned into all 1’s.

**Before / after example**

```text
Window [1,0,1,0,1], k = 1

zeroCount = 2 > 1  →  INVALID (even though you might imagine flipping one zero)

Window [1,0,1,1,1], k = 1

zeroCount = 1 ≤ 1  →  VALID length 5
```

### Second common worry: shrink with `while`, not `if`

If multiple zeros leave at once, one `if` might leave `zeroCount > k`. **`while`** drains until valid.

---

## 7. Detailed dry run

`nums = [1,1,1,0,0,0,1,1,1,1,0]`, `k = 2`

| right | nums[r] | zeroCount | shrink? | left after | length | best |
|---:|---|---:|---|---:|---:|---:|
| 0 | 1 | 0 | no | 0 | 1 | 1 |
| 1 | 1 | 0 | no | 0 | 2 | 2 |
| 2 | 1 | 0 | no | 0 | 3 | 3 |
| 3 | 0 | 1 | no | 0 | 4 | 4 |
| 4 | 0 | 2 | no | 0 | 5 | 5 |
| 5 | 0 | 3 | yes | →1→2→3 | 3 | 5 |
| 6 | 1 | 2 | no | 3 | 4 | 5 |
| 7 | 1 | 2 | no | 3 | 5 | 5 |
| 8 | 1 | 2 | no | 3 | 6 | **6** |
| 9 | 1 | 2 | no | 3 | 6 | 6 |
| 10 | 0 | 3 | yes | 4 | 6 | 6 |

Best window indices **3..8** (or **3..9** before final shrink) gives length **6**.

Role diagram at `right = 8`, `left = 3`:

```text
idx:   0  1  2  3  4  5  6  7  8  9 10
val:   1  1  1  0  0  0  1  1  1  1  0
              L                 R
              |---- 2 zeros -----|
              longest valid block so far
```

---

## 8. Why the algorithm is correct

- **Valid window invariant:** After the `while` loop, `zeroCount ≤ k`, so the window can be turned into all 1’s.
- **Longest:** For each `right`, we minimize `left` (shrink as little as needed) while staying valid — so `right - left + 1` is the **longest** valid window ending at `right`.
- **Global max:** `best` stores the maximum over all `right`.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute force | `O(n^2)` | `O(1)` |
| **Sliding window** | **`O(n)`** | **`O(1)`** |

---

## 10. Edge cases

| Case | Result | Note |
|---|---|---|
| `k = 0` | Longest run of 1’s only | Same code |
| All `1`’s | `n` | Never shrink |
| All `0`’s, `k = n` | `n` | |
| All `0`’s, `k < n` | `k` | Best window has `k` zeros |
| `k >= n` | `n` | Entire array valid |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| Using `if` instead of `while` to shrink | Window stays invalid |
| Forgetting to decrement `zeroCount` when `nums[left]==0` | Wrong counts, early break |
| Literally flipping in the array | Unnecessary complexity |
| Length `right - left` without `+1` | Off-by-one |

---

## 12. How to recognise this pattern

**Signals:**

- Binary array / 1’s and 0’s
- “At most K” of something bad (zeros)
- Longest subarray/substring

**Related in this folder:**

| Problem | What is “bad” in the window? |
|---|---|
| **06 Max Consecutive Ones III** | More than `k` zeros |
| 07 Fruit Into Baskets | More than 2 types |
| 05 Longest Repeating Character Replacement | `length - maxFreq > k` |

---

## 13. Interview explanation — say this aloud

> “I want the longest contiguous subarray that can become all 1’s if I flip at most k zeros. That’s the same as a window with at most k zeros. I expand right; when zero count exceeds k, I move left forward and drop zeros from the count until it’s valid again. I track the maximum window length. Each index enters and leaves once, so O(n) time and O(1) space.”

---

## 14. Interview follow-up Q&A

**Q: What if we must flip **exactly** k zeros?**  
A: Different problem — often need extra bookkeeping or another pass.

**Q: Same for strings of ‘0’/‘1’ characters?**  
A: Identical logic on `charAt`.

**Q: Max consecutive ones **without** k?**  
A: Simple scan — no window.

**Q: What if we can flip **any** value, not only 0→1?**  
A: Not this problem; constraint is binary.

---

## Link to Java

| Approach | Method |
|---|---|
| Brute force | `longestOnesBruteForce` |
| Sliding window (optimal) | `longestOnes` |

Run `main` in the Java class to exercise every approach.

---

## 15. Complete runnable Java class

See `MaxConsecutiveOnesIII.java` in the parent folder (`package DSA.SlidingWindow`).

---

## 16. Practice checklist

- [ ] Code optimal window from memory.
- [ ] Trace main example on paper.
- [ ] Explain “at most k zeros” without saying “flip”.
- [ ] Run `main` and confirm output `6`.

---

## 17. Quick revision

1. **At most k zeros** = valid window.
2. Expand `right`, **`while`** shrink `left` when `zeroCount > k`.
3. Answer = max length `right - left + 1`.
4. **`O(n)`** time, **`O(1)`** space.

One line:

> **Longest window with ≤ k zeros — count, shrink, track length.**

---

## 18. Next problem

**Problem 07 — Fruit Into Baskets**  
Same variable window shape; validity = **at most 2 distinct** numbers in a `HashMap`.