# Problem 10 — Sliding Window Maximum

> **Pattern:** Fixed-size window + **monotonic deque** of indices  
> **LeetCode:** 239  
> **Goal:** For every contiguous window of size `k`, output the **maximum** element.

---

## 1. Problem statement

Given an integer array `nums` and integer `k`, return an array `answer` where `answer[i]` is the **maximum** in `nums[i..i+k-1]`.

### Example

```text
Input:  nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
Output: [3, 3, 5, 5, 6, 7]

Windows:
[1,3,-1]   → 3
[3,-1,-3]  → 3
[-1,-3,5]  → 5
[-3,5,3]   → 5
[5,3,6]    → 6
[3,6,7]    → 7
```

### Constraints

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`
- `1 <= k <= nums.length`

---

## 2. Mental picture — one picture to keep in mind

Each step moves a length-`k` frame across the array. You need the **largest value** inside the frame **every time**.

```text
nums:  1   3  -1  -3   5   3   6   7
       |-------|
       k=3 max = 3

           |-------|
           max = 3
```

Brute force: scan `k` cells per window → `O(n·k)`.

**Deque idea:** Keep a line of **candidate indices** for “who could still become the max?” — drop losers early.

```text
Deque (index → value), values DECREASING left to right:

front → index of current window maximum
back  → newest interesting index
```

Store **indices**, not only values — so you know when someone **falls out** of the window.

---

## 3. How to start thinking

1. Fixed window — yes, size `k`.
2. Naive: max in each window — reuse a loop.
3. When the window slides, the max might **leave** or a **bigger newcomer** arrives.
4. Need a structure that:
   - gives max in O(1) at the front,
   - drops expired / useless indices from the back.

That structure is a **double-ended queue (deque)** kept in **decreasing value order**.

---

## 4. Approach 1 — Brute force

### Idea

For each `start`, scan `start..start+k-1` for max.

### Java

```java
for (int start = 0; start <= n - k; start++) {
    int max = Integer.MIN_VALUE;
    for (int i = start; i < start + k; i++) {
        max = Math.max(max, nums[i]);
    }
    result[start] = max;
}
```

### Complexity

| | Brute |
|---|---|
| Time | `O(n · k)` |
| Space | `O(1)` besides output |

Fails when `n` and `k` are large (~`10^10` operations).

---

## 5. Approach 2 — Monotonic deque of indices (optimal)

### What the deque stores

**Indices** `i` such that `nums[i]` values are **strictly decreasing** from front to back.

- **Front** = index of the **largest** value among viable candidates for the **current** window.
- If index `i` is in the deque and `nums[j] >= nums[i]` for some `j > i`, then `i` can **never** be the max while `j` lives in any future window that includes `j`. So pop `i` from the **back**.

### Three operations at each `right`

1. **Expire front:** If `deque.peekFirst() <= right - k`, that index left the window → `pollFirst()`.
2. **Maintain decreasing order:** While back index has value ≤ `nums[right]`, `pollLast()` (useless for future max).
3. **Add:** `offerLast(right)`.
4. **Record:** If `right >= k - 1`, window is full → `result[right - k + 1] = nums[deque.peekFirst()]`.

### Why “decreasing values”?

Suppose back of deque has value **5** and new `nums[right]` is **7**.

Any window that includes `7` will prefer **7** over **5** if both are inside. **5** can never be max again when **7** is present → drop 5 from deque.

Suppose new value is **2**. Some older big value might still be max until it expires — so **2** can sit at the back as a weak candidate for later windows.

### Java

See `../SlidingWindowMaximum.java`.

---

## 6. Hardest part — deque of **indices** + decreasing order

### Before (confusing)

“I’ll keep a max-heap of values in the window.”

Heap needs lazy deletion when elements leave — works but heavier. Deque is the classic interview solution.

### After (clear rules)

| Step | Meaning |
|---|---|
| Pop **front** when index `< right - k + 1` | Left edge left window |
| Pop **back** while `nums[back] <= nums[right]` | Back index never beats `right` |
| Push `right` | New candidate |
| Answer at front index | Current max |

**Before / after — why index `3` with value `-1` might stay**

```text
nums = [1, 3, -1, -3, ...]

When right=2 (value -1), deque might hold indices for 3, then -1 at back.
When right=3 (value -3), -3 is smaller → stays at back.
When right=4 (value 5), pop smaller backs until 5 is alone at front — max is 5.
```

**Visual — deque contents by index/value (conceptual)**

```text
right=4, k=3, window indices [2,3,4] values [-1,-3,5]

After cleanup, front index points to 5 → answer 5
```

### Not a sliding window on `left` variable

Unlike Problems 03–07, you often implement only `right` and use the deque to drop expired indices. The window is **implicit**: `[right-k+1 .. right]`.

---

## 7. Detailed dry run

`nums = [1, 3, -1, -3, 5, 3, 6, 7]`, `k = 3`

Track: deque as list of `(index,value)`, then output.

| right | nums[r] | expire front? | pop back (≤ r val)? | deque after add | output |
|---:|---|---|---|---|---|
| 0 | 1 | — | — | [(0,1)] | — |
| 1 | 3 | — | pop 0 (1≤3) | [(1,3)] | — |
| 2 | -1 | — | add -1 | [(1,3),(2,-1)] | **3** |
| 3 | -3 | — | add | [(1,3),(2,-1),(3,-3)] | **3** |
| 4 | 5 | pop 1 if ≤4-3=1? front idx 1 ≤1? 1≤1 yes | pop until 5 wins | [(4,5)] | **5** |
| 5 | 3 | pop while front ≤5-3=2? idx 4 ok | pop 3? 3≤3 | [(4,5),(5,3)] | **5** |
| 6 | 6 | expire ≤3 → drop 4? 4≤3 no; check: front 4 ≤ 3? no | pop 5,3 vs 6 | [(6,6)] | **6** |
| 7 | 7 | front ≤4? | pop 6 | [(7,7)] | **7** |

(Trace exact expirations on paper — expire rule: `peekFirst() <= right - k`.)

Output: `[3,3,5,5,6,7]` ✓

Role diagram at `right = 2` (first full window):

```text
window: [1, 3, -1]
deque front → index 1 (value 3)
```

---

## 8. Why the algorithm is correct

**Lemma:** For current window, the front index has the maximum value among all indices still in the deque, and all indices in the window that could be maximum are represented in the deque.

**Drop rule:** Removing smaller or equal values from the back cannot remove the true max of any future window.

**Expire rule:** Ensures no index outside `[right-k+1, right]` affects the answer.

Each index is pushed once and popped at most once → **O(n)** time.

---

## 9. Complexity comparison

| Method | Time | Space |
|---|---:|---:|
| Brute scan each window | `O(n · k)` | `O(1)` |
| **Monotonic deque** | **`O(n)`** | **`O(k)`** deque size |

---

## 10. Edge cases

| Case | Note |
|---|---|
| `k = 1` | Each element is its own max |
| `k = n` | One window, one max |
| Strictly increasing `nums` | Deque keeps dropping old — front is latest large |
| Strictly decreasing | Front stays largest until it expires |

---

## 11. Common mistakes

- Storing values only — cannot detect expiration.
- Expiring with `right - k` vs `right - k + 1` — use **`peekFirst() <= right - k`** (index invalid as **start** of window ending at `right` when too far left).
- Using increasing deque instead of **decreasing**.
- Forgetting to record answer only when `right >= k - 1`.

---

## 12. How to recognise this pattern

- “Max / min in every window of size k”
- Fixed window + need **order statistics** fast
- **Deque** appears in advanced sliding-window templates

---

## 13. Interview explanation — say this aloud

> “Brute force scans each window in O(k), so O(nk) overall. I maintain a deque of indices whose values are decreasing. The front is the max for the current window. When I advance right, I remove indices that left the window from the front, and from the back I remove indices whose values are less than or equal to nums[right] because they can never be the maximum again. I push right and when the window is full I read nums at the front index. Each index is pushed and popped once, so O(n) time.”

---

## 14. Interview follow-up Q&A

**Q: Sliding window minimum?**  
A: Same code with `>=` flipped to pop **larger** values from back (increasing deque for min).

**Q: Multiset — k largest each window?**  
A: Harder — heap or balanced BST; deque is specifically for **max of size k**.

**Q: Why not segment tree?**  
A: Works `O(n log n)` — deque is `O(n)` and simpler here.

---

## 15. Complete runnable Java class

`SlidingWindowMaximum.java` — brute + deque + `main`.

---

## 16. Practice checklist

- [ ] Implement deque version from memory.
- [ ] Explain why smaller backs get popped.
- [ ] Run main on `[1,3,-1,-3,5,3,6,7]`, k=3.
- [ ] Try k=1 and k=n.

---

## 17. Quick revision

1. Deque stores **indices**, values **decrease** front → back.
2. Pop front if index **out of window**.
3. Pop back while value **≤ new** `nums[right]`.
4. Front index = current max when window full.
5. **`O(n)`** time.

One line:

> **Candidates in decreasing order; front is max; drop expired left and never-win backs.**

---

## 18. Continue to Problems 11–12

You finished **Problems 01–10** in this folder. Next in the track:

| # | Problem | Core idea |
|---:|---|---|
| 11 | **Minimum Window Substring** | Shortest substring covering all of `t` — `need` / `formed` |
| 12 | **Longest Substring At Most K Distinct** | Longest with ≤ k distinct chars — generalises Problem 07 |

Review **`Sliding_Window_Common_Patterns.md`** and practise re-coding from memory. **Problem 12** marks the end of the core interview set (01–12).

---

## 19. Independent practice challenge

Without code, for `nums = [9,7,5,3,1]`, `k = 2`, write the output array of maxes.

Then trace the deque `(index,value)` after each `right` for `nums = [1,3,-1,-3,5]`, `k = 3`.