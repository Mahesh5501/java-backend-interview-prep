# Max Log Overlap (Maximum Concurrent Processes)

> **Java:** `../MaxLogOverlap.java`  
> **Package:** `DSA.ArraysHashing`  
> **Pattern:** Sweep line / timeline events (interval overlap count)

---

## 1. Problem statement

Each log is an inclusive time interval `[start, end]` (process running every minute from start through end).

Find the **maximum number of processes running at the same time**.

### Example 1

```text
Input:  [[1,3], [2,4], [3,6]]
Output: 3
```

At minute 3, all three overlap.

### Example 2

```text
Input:  [[2,6], [1,2], [3,5]]
Output: 2
```

---

## 2. Mental picture

Treat time as a line. Each process adds two events:

```text
start  → +1  (one more active process)
end+1  → -1  (process finished after 'end', so drop at next minute)
```

```text
time:  1   2   3   4   5   6   7
       +1  +1  +1  -1  -1  -1  -1   (example depends on intervals)
active count rises and falls; track the max
```

Because end is inclusive, decrement at `end + 1`.

---

## 3. Algorithm

1. Put events into a sorted map (`TreeMap`):  
   - `start → +1`  
   - `end + 1 → -1`  
2. Scan events in time order.  
3. Keep `active` running sum; track `maxOverlap`.

---

## 4. How this relates to Merge Intervals

| Problem | Question |
|---|---|
| Merge Intervals | Combine overlapping ranges |
| Max Log Overlap | How many overlap at the busiest moment? |

Same family: intervals + sorting/timeline thinking.

---

## 5. Complexity

| | |
|---|---|
| Time | `O(n log n)` for TreeMap inserts + scan |
| Space | `O(n)` for events |

---

## 6. Interview say-aloud

“I convert each interval into start and end events on a timeline. I sort by time, walk left to right, maintain an active counter, and remember the maximum. Because end is inclusive, I decrease the count at end + 1.”

---

## 7. Practice

- [ ] Run `MaxLogOverlap`
- [ ] Draw the timeline for example 1
- [ ] Explain why decrement is at `end + 1`
