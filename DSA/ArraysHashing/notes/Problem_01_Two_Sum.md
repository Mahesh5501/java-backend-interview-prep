# Problem 01 — Two Sum

> **Pattern:** HashMap complement (value → index)  
> **Java file:** `../TwoSum.java`  
> **Goal:** Understand every approach — what it does, why we pick it, and which Java method is which.

---

## 1. Problem statement

Given an integer array `nums` and an integer `target`, return **two indices** `i` and `j` such that:

```text
nums[i] + nums[j] == target
i != j
```

Exactly one valid answer exists (LeetCode assumption). You may return the indices in any order.

### Example 1

```text
Input:  nums = [2, 7, 11, 15], target = 9
Output: [0, 1]
```

Because `2 + 7 = 9`.

### Example 2

```text
Input:  nums = [3, 2, 4], target = 6
Output: [1, 2]
```

Because `2 + 4 = 6` (not `3 + 3` — we need **two different indices**).

### Example 3

```text
Input:  nums = [3, 3], target = 6
Output: [0, 1]
```

Same value twice is fine — different indices.

### Constraints (typical LeetCode)

- `2 <= nums.length <= 10^4`
- Exactly one solution
- Return **indices**, not values

---

## 2. Mental picture

You stand at one number and ask:

> “Have I already seen the other half of this pair?”

```text
nums = [3, 2, 4], target = 6

At index 0: value 3 → need 3.  Not seen yet. Remember 3 → 0
At index 1: value 2 → need 4.  Not seen yet. Remember 2 → 1
At index 2: value 4 → need 2.  Yes — 2 was at index 1 → answer [1, 2]
```

That “have I seen the complement?” question is why a **HashMap** fits.

### Jargon — explained once

| Term | Meaning here |
|---|---|
| **Complement** | The other number you need: `target - nums[i]` |
| **HashMap** | Fast lookup by key. We store `value → index` |
| **Brute force** | Check every possible pair with nested loops |
| **One pass** | Walk the array once while building the map |

---

## 3. How to start thinking

1. What must I return? **Indices**, not the numbers themselves.
2. Simplest correct idea? Try every pair → brute force.
3. Bottleneck? Nested loops → `O(n²)`.
4. Can I remember past numbers? Yes → map of value → index.
5. When do I check the map? **Before** inserting the current number (so I never use the same index twice).

---

## 4. Approach 1 — Brute force (check every pair)

### Idea

For every index `i`, try every later index `j` and see if `nums[i] + nums[j] == target`.

### Why mention it?

It proves you understand the problem. In interviews, say this first, then improve it.

### Java method

`twoSumBruteForce` in `TwoSum.java`

```java
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
```

### Dry run

`nums = [3, 2, 4]`, `target = 6`

| i | j | pair | sum | result |
|---|---|---|---|---|
| 0 | 1 | 3+2 | 5 | continue |
| 0 | 2 | 3+4 | 7 | continue |
| 1 | 2 | 2+4 | 6 | **return [1, 2]** |

### Complexity

| Measure | Value | Why |
|---|---|---|
| **Time** | `O(n²)` | Nested loops |
| **Extra space** | `O(1)` | No map |

### Limitation

Too slow when `n` is large (`10^4`–`10^5`). Correct, but not interview-final.

### When would you still use it?

Tiny arrays, or when interviewer asks for the simplest correct solution first.

---

## 5. Approach 2 — HashMap one pass (optimal — what we use)

### Core idea

While walking left → right:

1. Compute `complement = target - nums[i]`
2. If `complement` is already in the map → return `[map.get(complement), i]`
3. Else store `nums[i] → i` for future lookups

### Why this approach?

- We need **fast “have I seen X?”** → HashMap is average `O(1)` lookup
- We need **indices** → store value → index (not just a Set)
- One pass is enough because every earlier number is already in the map

### Why not a Set?

A Set only remembers values. Two Sum needs the **index** of the complement. Map wins.

### Why not sort + two pointers?

Sorting destroys original indices (unless you keep index pairs). Also Two Sum I usually wants the first solution in one unordered pass. (Two Sum II on a **sorted** array is a different problem — two pointers.)

### Java method

`twoSum` in `TwoSum.java`

```java
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
```

### Dry run (step by step)

`nums = [3, 2, 4]`, `target = 6`

| i | nums[i] | complement | map before check | action |
|---|---|---|---|---|
| 0 | 3 | 3 | `{}` | 3 not in map → put `{3→0}` |
| 1 | 2 | 4 | `{3→0}` | 4 not in map → put `{3→0, 2→1}` |
| 2 | 4 | 2 | `{3→0, 2→1}` | **2 is in map** → return `[1, 2]` |

### Critical rule

**Check before put.** If you put first, then for `[3, 3]` with target `6` you might wrongly reuse the same index.

### Complexity

| Measure | Value | Why |
|---|---|---|
| **Time** | `O(n)` | One pass; map ops average `O(1)` |
| **Extra space** | `O(n)` | Map can hold every element |

---

## 6. Approach 3 — Sort + two pointers (keeps indices via pairs)

### Idea

Build `(value, originalIndex)` pairs, sort by value, then move `left` / `right` until the sum equals `target`.

### Java method

`twoSumSortTwoPointers` in `TwoSum.java`

### Complexity

| Time | Extra space |
|---|---|
| `O(n log n)` | `O(n)` for pairs |

### When to use

Interviewer wants two pointers / lower hash memory; or the array is already sorted (Two Sum II — then no pairs needed).

**Interview default for Two Sum I:** still HashMap one pass (`twoSum`).

---

## 7. Hardest part (slow down here)

### Mistake A — Put before check

```java
// WRONG order
valueToIndex.put(nums[i], i);
if (valueToIndex.containsKey(complement)) { ... }
```

For `nums = [3, 3], target = 6`, after putting first `3`, complement `3` is already “seen” as the same index.

**Fix:** check first, then put.

### Mistake B — Returning values instead of indices

Problem asks for indices. Map value must be the **index**.

### Mistake C — Thinking you need two passes

One pass is enough: earlier numbers are already stored when you look for the complement.

---

## 8. Why the optimal approach never misses the answer

When we are at index `i`, the map contains **exactly** the values from indices `0 .. i-1` with their indices.

If any earlier index `j` forms a pair with `i`, then `nums[j]` equals `target - nums[i]`, so it is in the map. We find it.

If no earlier index works, we store `nums[i]` and continue. The unique solution (guaranteed) will be found when its second index is visited.

---

## 9. Complexity comparison

| Approach | Java method | Time | Extra space | Interview? |
|---|---|---|---|---|
| Brute force | `twoSumBruteForce` | `O(n²)` | `O(1)` | Start here verbally |
| HashMap one pass | `twoSum` | `O(n)` | `O(n)` | **Final answer** |
| Sort + two pointers | `twoSumSortTwoPointers` | `O(n log n)` | `O(n)` | Alternative |

---

## 10. Edge cases

| Input | Expected | What it checks |
|---|---|---|
| `[2,7,11,15], 9` | `[0,1]` | Classic |
| `[3,2,4], 6` | `[1,2]` | Not the first element pairing with itself |
| `[3,3], 6` | `[0,1]` | Duplicate values, different indices |
| Negative numbers | works | Complement can be negative |

---

## 11. Common mistakes

1. Put into map before checking complement.
2. Return values instead of indices.
3. Using a Set when you need indices.
4. Forgetting `i != j` (same-index reuse).
5. Sorting without tracking original indices.

---

## 12. How to recognise this pattern

Look for:

- “Find two numbers that sum to target”
- Need **indices** from an unordered array
- One solution guaranteed (or first solution)

Ask:

> Can I remember past values in a map and look up `target - current`?

If yes → HashMap complement.

---

## 13. Interview explanation — say this aloud

“Brute force checks every pair in `O(n²)`. I can do one pass with a HashMap from value to index. At each index `i`, I look for `target - nums[i]` in the map. If it exists, I return that stored index and `i`. If not, I store `nums[i] → i`. I check before inserting so I never reuse the same index. Time `O(n)`, space `O(n)`.”

---

## 14. Interview follow-ups

### Q1. What if the array is already sorted?

Use two pointers (Two Sum II) — `O(n)` time, `O(1)` space — no map needed.

### Q2. What if there are multiple answers?

Clarify: return any? all? Then adjust (don’t return early; collect pairs carefully with duplicates).

### Q3. What if I must use `O(1)` extra space?

Sort a copy of (value, index) pairs, then two pointers — `O(n log n)` time.

---

## 15. Link to Java — which method is which

| Approach | Method in `TwoSum.java` |
|---|---|
| Brute force | `twoSumBruteForce` |
| Optimal HashMap | `twoSum` |
| Sort + two pointers | `twoSumSortTwoPointers` |

Run `main` to print all approaches on sample inputs.
