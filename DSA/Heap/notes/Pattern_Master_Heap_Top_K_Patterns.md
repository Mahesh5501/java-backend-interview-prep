# Pattern Master — Heap Top-K Patterns

> **Java:** `../HeapTopKPatterns.java`  
> **Package:** `DSA.Heap`  
> **Role:** Bounded heap of size K (Top-K template)

---

## What is inside

| Method | Heap choice | Related full notes |
|---|---|---|
| `findKthSmallest` | Max-heap size K | Similar to Kth Largest family |
| `findKthLargest` | Min-heap size K | `Problem_01_Kth_Largest_Element.md` |
| `findKSmallestElements` | Max-heap size K, then collect | Top-K list variant |

---

## Mental picture (the rule)

```text
Want K largest?   keep a MIN-heap of size K
                  (throw away numbers that are too small)

Want K smallest?  keep a MAX-heap of size K
                  (throw away numbers that are too large)
```

Example:

```text
arr = [7,10,4,3,20,15], k=3
3rd smallest = 7
3rd largest  = 10
```

---

## Complexity

- Time: `O(n log k)`  
- Space: `O(k)`  

Better than full sort `O(n log n)` when `k << n`.

---

## Interview say-aloud

“For the Kth largest I keep a min-heap of size K. Each number is offered to the heap; if size exceeds K I poll the smallest. At the end the heap root is the Kth largest.”

---

## Practice

- [ ] Run `HeapTopKPatterns`
- [ ] Draw the min-heap for Kth largest on the sample array
- [ ] Link this to `TopKFrequent` / `KthLargestElement` problem notes
