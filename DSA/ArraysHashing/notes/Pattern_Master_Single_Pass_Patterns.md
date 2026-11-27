# Pattern Master — Single Pass Patterns

> **Java:** `../SinglePassPatterns.java`  
> **Package:** `DSA.ArraysHashing`  
> **Role:** One scan, keep state variables (greedy / running state)

---

## What is inside

| Method | Idea |
|---|---|
| `findSecondLargest` | Track largest + second largest while scanning |
| `findSecondSmallest` | Track smallest + second smallest |
| `maxProfit` | Best stock profit: track min price so far, max profit so far |

---

## Mental picture

```text
Second largest:
nums: 10, 5, 20, 8, 20
keep: largest, secondLargest
update carefully when equals appear
```

```text
Max profit (Buy/Sell once):
prices: 7,1,5,3,6,4
minPrice starts high
at each day: profit = price - minPrice; update best
also update minPrice
```

---

## Why this pattern matters

Avoid:

- sorting just to find 2nd largest (`O(n log n)`)
- nested loops for max profit (`O(n²)`)

Prefer:

- one pass  
- a few state variables  
- `O(n)` time, `O(1)` space  

---

## Interview say-aloud

“I maintain the extreme values I need while scanning once. For stock profit, I track the lowest buy price seen so far and the best profit if I sell today.”

---

## Practice

- [ ] Run `SinglePassPatterns`
- [ ] Code `maxProfit` from memory
- [ ] Explain how duplicates are handled in second largest
