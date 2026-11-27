# Heap / Priority Queue — Common Patterns Reference

A reusable revision guide for Java interviews (Java 8/17).

Java's `PriorityQueue` is a **binary min-heap** by default. For a **max-heap**,
pass a comparator: `new PriorityQueue<>((a, b) -> Integer.compare(b, a))`.

Runnable problems live in `Heap/` (`package DSA.Heap`). The older
`PriorityQueueHeap/` folder is legacy reference only — prefer this folder for study.

------------------------------------------------------------------------

## 1. What is a heap?

A **heap** is a complete binary tree stored in an array-like structure with the
**heap property**:

- **Min-heap:** parent ≤ children → smallest at root (`peek()`).
- **Max-heap:** parent ≥ children → largest at root.

`PriorityQueue` supports **insert** and **extract-min** in **O(log n)**; **peek**
in **O(1)**.

------------------------------------------------------------------------

## 2. How to recognise a heap problem

Clues:

- **Top K** / **Kth largest** / **Kth smallest** / **K most frequent**
- **Merge** many sorted streams (lists, arrays)
- **Running median** of a stream
- **Greedy scheduling** (task scheduler, reorganize string)
- **Repeatedly take best or worst** among dynamic candidates

------------------------------------------------------------------------

## 3. Pattern A — Min-heap of size K (Top-K largest)

Keep **K** elements; the root is the **Kth largest** among what you kept.

``` java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for (int x : nums) {
    minHeap.offer(x);
    if (minHeap.size() > k) {
        minHeap.poll(); // remove smallest in the heap
    }
}
return minHeap.peek();
```

- **Kth largest in array:** heap size `k`.
- **K closest points:** heap size `k` on distance (min-heap of size k keeps k smallest distances; compare carefully).

Time: **O(n log k)**. Space: **O(k)**.

**Kth smallest:** use **max-heap** of size `k`, or sort.

------------------------------------------------------------------------

## 4. Pattern B — Frequency map + heap

1. Count with `HashMap`.
2. Push entries into heap ordered by frequency (or count).

Used in **Top K Frequent Elements**.

------------------------------------------------------------------------

## 5. Pattern C — Merge K sorted lists

Min-heap stores **one head node per list** (smallest current value on top).

``` java
PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
```

Each `poll` appends to result and pushes that node's `next`.

Time: **O(N log k)** where N = total nodes, k = number of lists.

------------------------------------------------------------------------

## 6. Pattern D — Two heaps (median stream)

- **Max-heap** `low` → lower half (largest of lows on top).
- **Min-heap** `high` → upper half (smallest of highs on top).

Invariant: `low.size()` is equal to or one greater than `high.size()`.

Median:

- Odd count → `low.peek()`
- Even → average of `low.peek()` and `high.peek()`

Balance after every add with limited `poll`/`offer` moves.

------------------------------------------------------------------------

## 7. Pattern E — Max-heap greedy simulation

- **Last Stone Weight:** smash two largest while possible.
- **Task Scheduler:** count frequencies, sort or use max-heap + idle math.
- **Reorganize String:** max-heap on counts; place highest remaining that is not same as previous.

------------------------------------------------------------------------

## 8. Comparator tips

- Prefer `Integer.compare(a, b)` over `a - b` (overflow safe).
- Custom objects: `(a, b) -> Integer.compare(a.freq, b.freq)` or tie-break on key.
- `ListNode`: compare `val` only.

------------------------------------------------------------------------

## 9. Common mistakes

1. **Wrong heap type** — kth largest needs **min-heap** size k, not max-heap.
2. **Off-by-one** on k (largest vs smallest wording).
3. **Null** list heads in merge-k — only enqueue non-null heads.
4. **Median streams:** forgetting to rebalance sizes after insert.
5. **Reorganize string:** not checking `maxCount <= (n + 1) / 2` impossibility.
6. Assuming `PriorityQueue` iteration order is sorted — it is not.

------------------------------------------------------------------------

## 10. Complexity quick reference

  Problem shape              Typical time     Extra space
  -------------------------- ---------------- -------------
  Top-K with heap size k     O(n log k)       O(k)
  Top-K frequent             O(n log k)       O(n) map
  Merge K lists              O(N log k)       O(k)
  Median stream              O(log n) / add   O(n)
  Task scheduler (heap)      O(n) or O(n log m)

------------------------------------------------------------------------

## 11. Practice roadmap (this folder)

1. Kth Largest Element in Array — min-heap size k.
2. Top K Frequent Elements — map + heap.
3. Merge K Sorted Lists — min-heap of heads.
4. Find Median from Data Stream — two heaps.
5. K Closest Points to Origin — heap size k.
6. Task Scheduler — greedy + heap or formula.
7. Last Stone Weight — max-heap simulation.
8. Reorganize String — max-heap + placement.

------------------------------------------------------------------------

## 12. Interview script (Top-K)

1. "Brute force: sort → O(n log n)."
2. "We only need K best; maintain a heap of size K."
3. "Min-heap of size K for K **largest** — root is the answer."
4. "O(n log k) time, O(k) space — better when k ≪ n."