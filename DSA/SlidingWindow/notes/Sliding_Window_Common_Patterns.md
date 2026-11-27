# Sliding Window — Common Patterns Reference

A reusable revision guide for Java interviews.

This file explains the patterns shared by many Sliding Window problems.
Each individual problem has its own notes file with full reasoning, code,
dry runs, complexity, edge cases, and follow-ups.

Folder layout:

```text
DSA/SlidingWindow/
├── notes/          ← study notes (.md)
└── *.java          ← runnable programs (package DSA.SlidingWindow)
```

---

## 1. What is Sliding Window?

Sliding Window is used when a problem asks about a **continuous** part of an
array or string (a subarray or substring).

Instead of recalculating every possible section from scratch, we keep a window
with two ends:

```java
int left = 0;

for (int right = 0; right < n; right++) {
    // expand window by including arr[right]
    // shrink window from left when needed
}
```

When the window moves:

- something **enters** from the right
- something may **leave** from the left
- we update the answer using that change

---

## 2. How to recognise Sliding Window

Look for these clues:

- continuous subarray / substring
- “exactly K consecutive”
- longest / shortest window that satisfies a condition
- at most K distinct characters / zeros / types
- every window of size K

Ask:

1. Is the section continuous?
2. Can I represent it with `left` and `right`?
3. Can I update the answer when the window expands or shrinks?

If yes, Sliding Window is a strong candidate.

Important: if the array has **negatives** and the condition is based on sum,
ordinary expand/shrink may fail. Then consider prefix sums / other patterns.

---

## 3. Pattern A — Fixed-size window

### Shape

Window size is always exactly `k`.

```java
int windowSum = 0;

for (int i = 0; i < k; i++) {
    windowSum += arr[i];
}

int best = windowSum;

for (int right = k; right < arr.length; right++) {
    windowSum += arr[right];      // enter
    windowSum -= arr[right - k];  // leave
    best = Math.max(best, windowSum);
}
```

### When to use

- maximum / minimum sum of K consecutive elements
- maximum average of length K
- count something inside every window of size K
- anagram / permutation checks of length equal to a pattern

### Key idea

```text
newAnswer = oldAnswer - leavingValue + enteringValue
```

---

## 4. Pattern B — Variable-size window

### Shape

Window grows with `right`. Shrink with `left` when the window becomes invalid
(or when you want a smaller valid window).

```java
int left = 0;

for (int right = 0; right < n; right++) {
    // add arr[right] into window state

    while (/* window is invalid or too big */) {
        // remove arr[left] from window state
        left++;
    }

    // update answer using current window [left..right]
}
```

### Two common goals

1. **Longest valid window**
   - expand as much as possible
   - shrink only when invalid
   - track maximum length

2. **Shortest valid window**
   - expand until valid
   - shrink while still valid
   - track minimum length

---

## 5. Pattern C — Window + frequency map / set

Used when validity depends on:

- unique characters
- character counts
- at most K distinct values
- matching another string’s frequency

Typical tools:

- `HashSet` for uniqueness
- `HashMap` or `int[26]` / `int[128]` for counts
- a `need` / `have` counter for “how many requirements are satisfied”

---

## 6. Decision tree (interview)

```text
Continuous section?
  NO  → not Sliding Window
  YES → fixed size K?
          YES → Fixed window
          NO  → longest / shortest / at most K?
                  YES → Variable window
                  NO  → check if frequency / anagram style still fits
```

---

## 7. Roadmap — most frequently asked

| # | Problem | Type | Main idea |
|---:|---|---|---|
| 1 | Maximum Sum of K Consecutive Elements | Fixed | enter/leave sum update |
| 2 | Maximum Average Subarray I | Fixed | same as max sum, then divide |
| 3 | Minimum Size Subarray Sum | Variable | shrink while sum >= target |
| 4 | Longest Substring Without Repeating Characters | Variable + set | shrink on duplicate |
| 5 | Longest Repeating Character Replacement | Variable + freq | window - maxFreq <= k |
| 6 | Max Consecutive Ones III | Variable | at most k zeros |
| 7 | Fruit Into Baskets | Variable + map | at most 2 types |
| 8 | Permutation in String | Fixed + freq | window equals pattern counts |
| 9 | Find All Anagrams in a String | Fixed + freq | same idea, collect start indexes |
| 10 | Sliding Window Maximum | Fixed + deque | keep candidates in decreasing deque |
| 11 | Minimum Window Substring | Variable + freq | shortest cover — need / formed |
| 12 | Longest Substring At Most K Distinct | Variable + map | longest — at most k distinct (k=2 → Fruit Into Baskets) |

Study order: 1 → 12.

---

## 8. Common mistakes

1. Forgetting the window is continuous.
2. Using fixed-window logic on a variable-size problem.
3. Forgetting to remove the leaving element from the sum/map.
4. Off-by-one in length: length is `right - left + 1`.
5. Shrinking incorrectly for “longest” vs “shortest”.
6. Assuming sum windows work with negatives.

---

## 9. Complexity reminder

Most Sliding Window solutions are:

- **Time:** `O(n)` because each index enters and leaves the window at most once
- **Extra space:** `O(1)` or `O(Σ)` for frequency maps / sets

---

## 10. How to use this folder

1. Read this common patterns file.
2. Open one problem note in `notes/`.
3. Trace the dry run on paper.
4. Run the matching `.java` file.
5. Re-code it yourself without looking.
