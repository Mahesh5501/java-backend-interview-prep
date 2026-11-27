# Binary Search — Common Patterns Reference

A reusable revision guide for Java interviews (Java 8/17).

Binary Search is not only “find target in sorted array.” It is a way to **halve**
the search space whenever you can prove which half still contains the answer.

Folder layout:

```text
DSA/BinarySearch/
├── notes/          ← study notes (.md)
└── *.java          ← runnable programs (package DSA.BinarySearch)
```

---

## 1. Classic binary search

**Precondition:** array sorted ascending (or descending with flipped logic).

```java
int left = 0, right = nums.length - 1;
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) return mid;
    if (nums[mid] < target) left = mid + 1;
    else right = mid - 1;
}
return -1;
```

| Loop | When |
|---|---|
| `left <= right` | exact index or “not found” |
| `left < right` | find boundary / minimum (careful with `right = mid`) |

**Overflow-safe mid:** `left + (right - left) / 2`, not `(left + right) / 2`.

---

## 2. Pattern A — First occurrence (lower bound)

On hit, record answer and search **left** half:

```java
int ans = -1;
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) {
        ans = mid;
        right = mid - 1;
    } else if (nums[mid] < target) left = mid + 1;
    else right = mid - 1;
}
```

---

## 3. Pattern B — Last occurrence (upper bound style)

On hit, record answer and search **right** half:

```java
if (nums[mid] == target) {
    ans = mid;
    left = mid + 1;
}
```

**Count occurrences:** `last - first + 1` (if first != -1).

---

## 4. Pattern C — Search insert position

Same as classic; when loop ends, `left` is the insertion index.

Or use lower bound: first index where `nums[i] >= target`.

---

## 5. Pattern D — Rotated sorted array

Half `[left..mid]` or `[mid..right]` is always sorted.

1. Compare `nums[mid]` with `nums[left]` (or `nums[right]`) to know which side is sorted.
2. Check if `target` lies in the sorted half’s value range.
3. Discard the other half.

**Unique elements** simplify the logic (LeetCode 33 style).

---

## 6. Pattern E — Minimum in rotated array

```java
while (left < right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] > nums[right]) left = mid + 1;  // min in right half
    else right = mid;                             // mid may be min
}
return nums[left];
```

Compare **mid with right**, not mid with left, for the standard unique-values version.

---

## 7. Pattern F — Binary search on answer (answer space)

When the question asks for **minimum X** such that `feasible(X)` is true and
`feasible` is monotonic (false, false, …, true, true).

```java
int low = minPossible, high = maxPossible, best = high;
while (low <= high) {
    int mid = low + (high - low) / 2;
    if (feasible(mid)) {
        best = mid;
        high = mid - 1;  // minimize: shrink high
    } else {
        low = mid + 1;
    }
}
```

| Goal | On success move |
|---|---|
| Minimize answer | `high = mid - 1` |
| Maximize answer | `low = mid + 1` |

**Examples:** Koko Eating Bananas, Capacity To Ship Packages, split array, painting boards.

---

## 8. Pattern G — Peak element

`nums[mid] < nums[mid+1]` → peak on right; else on left.

```java
while (left < right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] < nums[mid + 1]) left = mid + 1;
    else right = mid;
}
```

`nums[-1]` and `nums[n]` treated as −∞ (implicit).

---

## 9. Decision tree

```text
Sorted array, one target?
  → Classic BS

Duplicates, need first/last/count?
  → Pattern A / B

Rotated?
  → Which half sorted? (D or E)

Insert if missing?
  → Classic ends with left = insert index (C)

"Minimum speed / capacity / days" with check(mid)?
  → BS on answer (F)

Peak / local maximum?
  → Pattern G
```

---

## 10. Roadmap — problems in this folder

| # | Problem | Pattern |
|---:|---|---|
| 01 | Classic Binary Search | Classic |
| 02 | First Occurrence | A |
| 03 | Last Occurrence | B |
| 04 | Count Occurrences | A + B |
| 05 | Search Rotated Array | D |
| 06 | Find Minimum Rotated Array | E |
| 07 | Search Insert Position | C |
| 08 | Koko Eating Bananas | F |
| 09 | Find Peak Element | G |
| 10 | Capacity To Ship Packages | F |

Study order: 01 → 10.

---

## 11. Common mistakes

1. Infinite loop: `left = mid` without `right = mid` when appropriate (or wrong loop condition).
2. Using `(left + right) / 2` on large indices (overflow).
3. Wrong half discard in rotated search — always justify with **sorted half + range**.
4. `right = mid - 1` when finding minimum in rotated array (should often be `right = mid`).
5. Confusing `lower bound` with “first occurrence” on duplicates — same idea.
6. BS on answer without proving **monotonic** feasibility.

---

## 12. Interview script (template)

> “The array is sorted, so I maintain `left` and `right` and compute `mid`. If `nums[mid]` equals the target I return it; otherwise I discard the half that cannot contain the target because all values there are too small or too large. That leaves `O(log n)` comparisons and `O(1)` space.”

For **BS on answer:**

> “I binary search the answer value. For a candidate `mid`, I run a greedy feasibility check. If it works, I try smaller (or larger) and keep the best valid answer.”

---

## 13. Complexity

| Variant | Time | Space |
|---|---:|---:|
| Standard | O(log n) | O(1) |
| Count (first+last) | O(log n) | O(1) |
| BS on answer + O(n) check | O(n log R) | O(1) |

`R` = range of answer space (e.g. max pile, total sum).

---

## 14. Quick revision

1. **Sorted** → halve by compare at `mid`.  
2. **First/last** → save `ans`, bias left or right on tie.  
3. **Rotated** → find sorted half, then range test.  
4. **Min rotated** → compare `mid` with `right`, `right = mid`.  
5. **Answer space** → monotonic `feasible(mid)`.  
6. **Peak** → climb toward larger neighbor.

---

## 15. Next step

Implement **Problem 01** from memory, then **Problem 02** and **03** as a trio before moving to rotated arrays.