# Problem 07 — Longest Consecutive Sequence

> **Pattern:** HashSet + only start a run at sequence beginnings  
> **Java file:** `../LongestConsecutiveSequence.java`  
> **Goal:** See why sorting works, and why the Set trick is truly `O(n)`.

---

## 1. Problem statement

Given an **unsorted** array of integers, return the length of the longest sequence of **consecutive** values.

Numbers can appear in any order. Extra duplicates do not extend the sequence.

### Example

```text
Input:  [100, 4, 200, 1, 3, 2]
Output: 4
```

Longest run: `1, 2, 3, 4`.

---

## 2. Mental picture

A consecutive sequence looks like a chain:

```text
1 → 2 → 3 → 4
```

You only want to **start walking** at the beginning of a chain (when `x-1` is missing). Otherwise you re-count the same chain many times.

```text
set = {100, 4, 200, 1, 3, 2}

1 has no 0  → start; walk 1,2,3,4 → length 4
2 has 1     → skip (not a start)
3 has 2     → skip
4 has 3     → skip
100 has no 99 → start; length 1
200 has no 199 → start; length 1
```

---

## 3. How to start thinking

1. Brute: for each num, while num+1 exists (linear search) → very slow.
2. Sort: consecutive values become neighbors → easy scan.
3. Optimal: HashSet for `O(1)` contains; only start at chain heads.

---

## 4. Approach 1 — Sort then scan

### Idea

Sort, skip duplicates, count how long `nums[i] == nums[i-1] + 1` streaks last.

### Java method

`longestConsecutiveSort`

### Complexity

`O(n log n)` time.

### When to use

Simple and correct. Good if `O(n)` is not required. LeetCode asks for `O(n)` → prefer Set.

---

## 5. Approach 2 — HashSet + start only when `x-1` missing (optimal — what we use)

### Idea

1. Put all numbers in a HashSet (dedupes automatically).
2. For each `x` in the set:
   - If `x-1` exists → skip (middle of a chain).
   - Else walk `x, x+1, x+2, ...` while present; track best length.

### Why this is O(n)

Each number is the start of a walk **at most once**. Across all walks, each number is visited about once as you step `x+len`. Total work proportional to `n`.

### Why the `x-1` check matters

Without it, from every number you walk forward → can become `O(n²)`.

### Java method

`longestConsecutive`

```java
public static int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int x : nums) set.add(x);

    int best = 0;
    for (int x : set) {
        if (set.contains(x - 1)) continue; // not a sequence start
        int len = 1;
        while (set.contains(x + len)) len++;
        best = Math.max(best, len);
    }
    return best;
}
```

### Complexity

| Time | Extra space |
|---|---|
| `O(n)` average | `O(n)` |

---

## 6. What we use and why

| Approach | Java method | Time | Use |
|---|---|---|---|
| Sort + streak | `longestConsecutiveSort` | `O(n log n)` | Simple backup |
| **Set + start heads** | `longestConsecutive` | `O(n)` | **Required interview answer** |

---

## 7. Hardest part

### Mistake — Walking forward from every number

Destroys the `O(n)` guarantee.

### Mistake — Using the array instead of the set in the for-loop

Duplicates in `nums` can re-trigger work. Iterate the **set**.

### Empty array

Return `0`.

---

## 8. Interview explanation — say this aloud

“Sorting then scanning streaks is `O(n log n)`. For `O(n)`, I put numbers in a HashSet. I only start a forward walk when `x-1` is not in the set — that means `x` is the start of a consecutive run. I walk with `contains(x+len)` and track the max length. Each number is visited constantly many times overall, so time is `O(n)`.”

---

## 9. Link to Java

| Approach | Method |
|---|---|
| Sort | `longestConsecutiveSort` |
| HashSet optimal | `longestConsecutive` |
