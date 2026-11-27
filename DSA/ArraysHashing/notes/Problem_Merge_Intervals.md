# Merge Intervals

> **Java:** `../MergeIntervals.java`  
> **Package:** `DSA.ArraysHashing`  
> **Pattern:** Sort + linear scan (interval merging)

---

## 1. Problem statement

Given a list of intervals `[start, end]`, merge all overlapping ones.

### Example

```text
Input:  [[1,3], [2,6], [8,10], [9,12]]
Output: [[1,6], [8,12]]
```

---

## 2. Mental picture

```text
[1----3]
   [2-------6]   → merge → [1----------6]

              [8----10]
                 [9------12] → merge → [8-----------12]
```

Two intervals overlap when:

```text
current.start <= last.end
```

---

## 3. Algorithm

1. Sort intervals by start time.  
2. Keep a `merged` list.  
3. For each interval:
   - if it overlaps the last merged interval → extend `last.end`
   - else append it as a new interval  

---

## 4. Why sorting is required

Without sorting, “previous interval” is meaningless.  
Sorting by start makes overlaps local and easy to detect in one pass.

---

## 5. Complexity

| | |
|---|---|
| Time | `O(n log n)` (sort) + `O(n)` scan |
| Space | `O(n)` for the answer list |

---

## 6. Interview say-aloud

“I sort by start time, then scan. If the current interval starts before or at the end of the last merged interval, I merge by taking the max end. Otherwise I push a new interval.”

---

## 7. Practice

- [ ] Run `MergeIntervals`
- [ ] Trace `[[1,4],[0,2],[3,5]]` by hand after sorting
- [ ] Explain the overlap condition clearly
