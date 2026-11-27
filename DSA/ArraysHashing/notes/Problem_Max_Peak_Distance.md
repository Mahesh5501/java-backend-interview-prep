# Max Peak Distance

> **Java:** `../MaxPeakDistance.java`  
> **Package:** `DSA.ArraysHashing`  
> **Pattern:** Single-pass scan + state (peaks)

---

## 1. Problem statement

An index `i` is a **peak** if:

- `1 <= i <= n-2`
- `arr[i] > arr[i-1]`
- `arr[i] > arr[i+1]`

Find the **largest distance between two consecutive peaks**.  
If fewer than two peaks exist, return `0`.

### Example 1

```text
arr = [1, 5, 2, 3, 1]
peaks at index 1 (value 5) and index 3 (value 3)
distance = 3 - 1 = 2
```

### Example 2

```text
arr = [1, 2, 1, 3, 2, 1, 4, 1]
peaks at 1, 3, 6
consecutive distances: 2 and 3 → answer 3
```

---

## 2. Mental picture

```text
index: 0 1 2 3 4 5 6 7
value: 1 2 1 3 2 1 4 1
         ^   ^     ^
       peak peak  peak
         |---|-----|
           2    3   → max = 3
```

Only **adjacent peaks in order** matter — not the first vs last peak unless they are consecutive in the peak list.

---

## 3. Algorithm

1. Scan `i` from `1` to `n-2`.  
2. When `arr[i]` is a peak:
   - if a previous peak exists → update `maxDistance = i - previousPeak`
   - set `previousPeak = i`  
3. Return `maxDistance` (0 if < 2 peaks).

---

## 4. Complexity

| | |
|---|---|
| Time | `O(n)` one pass |
| Space | `O(1)` |

---

## 5. Interview say-aloud

“I scan once and detect local peaks by comparing neighbors. I remember the previous peak index and keep the maximum gap between consecutive peaks.”

---

## 6. Practice

- [ ] Run `MaxPeakDistance`
- [ ] Trace example 2 by hand
- [ ] Explain why ends (`0` and `n-1`) cannot be peaks in this problem
