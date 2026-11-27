# Pattern Master — Two Pointer Patterns

> **Java:** `../TwoPointerPatterns.java`  
> **Package:** `DSA.TwoPointers`  
> **Role:** Quick revision master class (several two-pointer tricks in one file)

---

## What is inside

| Method | Problem idea | Related full notes |
|---|---|---|
| `moveZeroes` | Push zeros to end, keep order | `Problem_05_Move_Zeroes.md` |
| `removeElement` | Remove a value in-place, return new length | Read/write pointers (same family as Move Zeroes) |
| `sortedSquares` | Squares of sorted array, sorted result | `Problem_10_Squares_of_a_Sorted_Array.md` |

---

## Mental picture

```text
read/write style:
[ 0, 1, 0, 3, 12 ]
  write stays on next free non-zero slot
  read scans everything
```

```text
both-ends style (sorted squares):
[-4, -1, 0, 3, 10]
  L -------------- R
compare |L| vs |R|, place larger square from the back
```

---

## When to open this file

- Before interview: run `main` and re-explain each method aloud
- After studying Problem 05 and Problem 10: use this as a compact cheat sheet

---

## Interview say-aloud

“For in-place compaction I use a write pointer for kept values and a read pointer to scan. For sorted squares, because negatives square large, I compare absolute values from both ends and fill the answer from the right.”

---

## Practice

- [ ] Run `TwoPointerPatterns`
- [ ] Re-code `moveZeroes` and `sortedSquares` without looking
- [ ] Explain why write pointer never needs a second array
