# Problem 07 — Fruit Into Baskets

> **Pattern:** Variable-size sliding window — **at most 2 distinct** types  
> **LeetCode:** 904  
> **Goal:** Longest contiguous subarray containing at most **2 different** fruit types.

---

## 1. Problem statement

You are given an array `fruits` where `fruits[i]` is the type of fruit on tree `i`.

You have **two baskets**. Each basket holds **one type** only. You must pick from a **continuous** row of trees, one fruit per tree.

When you reach a **third** distinct type, you must stop (cannot pick that tree).

Return the **maximum** number of fruits you can pick.

### Examples

```text
Input:  fruits = [1,2,1]
Output: 3
Explanation: types {1,2} only → whole array.

Input:  fruits = [0,1,2,2]
Output: 3
Explanation: [1,2,2] at indices 1..3 (types 1 and 2).
```

### Constraints

- `1 <= fruits.length <= 10^5`
- `0 <= fruits[i] < fruits.length`

---

## 2. Mental picture — one picture to keep in mind

Two baskets = **two labels allowed** inside your walking window.

```text
fruits:  0  1  2  2
             |----|
             L    R   types in window: {1,2} ✓

Add tree with type 0 at start — if you include index 0, you get {0,1,2} → 3 types → illegal.
```

```text
        [ 1  2  1 ]
         ^-----^
     only types 1 and 2 → length 3
```

**right** picks the next tree. **left** walks forward when your basket “set” has **more than 2** types.

Map tracks **how many** of each type sit in the window (so you know when a type fully leaves).

---

## 3. How to start thinking

1. Story wording hides a standard rule: **longest subarray with at most 2 distinct values**.
2. Need to know **distinct count** → `HashMap<type, count>`.
3. **Longest** valid → expand always; shrink only when `map.size() > 2`.
4. When removing `fruits[left]`, decrement count; **remove key** if count hits 0.

---

## 4. Approach 1 — Brute force

### Idea

Every start index, extend end while at most 2 distinct types (use a small map or set).

### Complexity

| | Brute |
|---|---|
| Time | `O(n^2)` |
| Space | `O(1)` types (at most 3 during inner loop) |

Good for understanding; not for large `n`.

---

## 5. Approach 2 — Optimal sliding window + map

### Step-by-step

1. `left = 0`, `best = 0`, empty `HashMap<Integer,Integer> count`.
2. For each `right`:
   - `count[fruits[right]]++`.
   - **While** `count.size() > 2`:
     - Decrement `fruits[left]`; remove key if zero.
     - `left++`.
   - `best = max(best, right - left + 1)`.
3. Return `best`.

### Java

See `../FruitIntoBaskets.java`.

```java
Map<Integer, Integer> count = new HashMap<>();
int left = 0, best = 0;
for (int right = 0; right < fruits.length; right++) {
    int type = fruits[right];
    count.put(type, count.getOrDefault(type, 0) + 1);
    while (count.size() > 2) {
        int leftType = fruits[left];
        count.put(leftType, count.get(leftType) - 1);
        if (count.get(leftType) == 0) count.remove(leftType);
        left++;
    }
    best = Math.max(best, right - left + 1);
}
return best;
```

---

## 6. Hardest part — why decrement AND remove from map?

### Before (wrong)

“I’ll use a `HashSet` of types in the window and remove `fruits[left]` from the set when I shrink.”

If you have `[2,2,1]` and remove one `2`, the set might drop `2` even though another `2` is still inside the window.

### After (correct)

**Counts** tell you when a type is **completely gone** from the window. Only then remove the key → `size()` is accurate.

**Before / after**

```text
Window [2, 2, 1]  left at index 0

Remove one 2: count[2] goes from 2 → 1, key 2 stays → distinct still 2 ✓

Wrong set-only: remove 2 from set → distinct becomes {1} only — wrong state for window [2,1]
```

---

## 7. Detailed dry run

`fruits = [0, 1, 2, 2]`

| right | fruit | map (type→count) | size | action | left | best |
|---:|---|---|---|---:|---:|---:|
| 0 | 0 | {0:1} | 1 | ok | 0 | 1 |
| 1 | 1 | {0:1,1:1} | 2 | ok | 0 | 2 |
| 2 | 2 | {0,1,2} | **3** | shrink | 1 | 2 |
|  |  | after drop 0: {1:1,2:1} | 2 | ok | 1 | 2 |
| 3 | 2 | {1:1,2:2} | 2 | ok | 1 | **3** |

Answer **3** — window indices **1..3** = `[1,2,2]`.

Role diagram at end:

```text
fruits:  0  1  2  2
            L     R
         baskets: type 1 and type 2
```

---

## 8. Why the algorithm is correct

After shrinking, the map holds exactly the types in `[left..right]` with correct multiplicities → `size() ≤ 2`.

For fixed `right`, the `while` loop gives the **smallest** valid `left`, hence the **longest** valid window ending at `right`. Maximizing over `right` yields the global optimum.

---

## 9. Complexity comparison

| Method | Time | Space |
|---|---:|---:|
| Brute | `O(n^2)` | `O(1)` |
| **Window + map** | **`O(n)`** | **`O(1)`** — at most 3 keys briefly |

---

## 10. Edge cases

| Input | Output | Note |
|---|---:|---|
| `[1,2,1]` | 3 | Two types, full array |
| `[1]` | 1 | One tree |
| `[1,1,1]` | 3 | One type only |
| Long mix | varies | Single pass |

---

## 11. Common mistakes

- Removing type from map when count > 0 still in window.
- Using `if` once instead of `while` when size > 2.
- Forgetting `right - left + 1` for length.

---

## 12. How to recognise this pattern

- “At most **K distinct**” in a continuous segment.
- K = 2 in story form (baskets).

Generalisation: **Longest substring with at most K distinct characters** — same code with `K` instead of 2.

---

## 13. Interview explanation — say this aloud

> “This is the longest subarray with at most two distinct values. I slide right and add fruit counts to a hash map. If more than two keys exist, I move left forward, decrementing the leaving fruit until one type disappears from the map. I track the maximum window length. Amortized O(n) because each index enters and leaves once.”

---

## 14. Interview follow-up Q&A

**Q: At most K distinct?**  
A: Same template; shrink while `map.size() > K`.

**Q: Shortest window with exactly 2 types?**  
A: Different goal — expand/shrink pattern changes.

**Q: Without HashMap?**  
A: For K=2 only, you can track two type IDs and counts with variables — map is clearer and scales.

---

## 15. Complete runnable Java class

`FruitIntoBaskets.java` — brute + optimal + `main`.

---

## 16. Practice checklist

- [ ] Implement from memory.
- [ ] Trace `[0,1,2,2]`.
- [ ] Explain why counts beat a set alone.

---

## 17. Quick revision

> **Longest window with ≤ 2 distinct types — map counts, shrink until size ≤ 2.**

---

## 18. Next problem

**Problem 08 — Permutation in String** — **fixed** window + frequency match (anagram style).