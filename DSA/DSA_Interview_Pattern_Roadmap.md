# DSA Interview Pattern Roadmap (30+ LPA prep)

Same study style as Two Pointers / Sliding Window:

```text
DSA/<Pattern>/
├── notes/                 ← Common_Patterns.md + Problem_XX_*.md
└── *.java                 ← package DSA.<Pattern>
```

## Status

| Priority | Pattern | Folder | Problems | Status |
|---|---|---|---:|---|
| 1 | Arrays / Hashing | `ArraysHashing/` | 8 | Done |
| 2 | Two Pointers | `TwoPointers/` | 10 | Done |
| 2 | Sliding Window | `SlidingWindow/` | 12 | Done |
| 3 | Binary Search | `BinarySearch/` | 10 | Done |
| 4 | Stack / Monotonic Stack | `Stack/` | 8 | Done |
| 5 | Linked List | `LinkedList/` | 8 | Done |
| 6 | Trees / BST | `Trees/` | 10 | Done |
| 7 | Heap / Top-K | `Heap/` | 8 | Done |
| 8 | Graphs | `Graphs/` | 10 | Done |
| 9 | Dynamic Programming | `DynamicProgramming/` | 10 | Done |
| 10 | Trie / Union-Find | `TrieUnionFind/` | 6 | Done |

**Total problem notes:** 100 (+ shared helpers: `ListNode.java`, `TreeNode.java`, `UnionFind.java`)

## How to study each pattern

1. Read `notes/*_Common_Patterns.md`
2. Do problems in order (`Problem_01` → …)
3. Run matching `.java`
4. Re-code without looking

## Already complete

- **ArraysHashing** — 8 problems + common patterns
- **TwoPointers** — 10 problems + common patterns
- **SlidingWindow** — 12 problems + common patterns
- **BinarySearch** — 10 problems + common patterns
- **Stack** — 8 problems + common patterns
- **LinkedList** — 8 problems + `ListNode.java` + common patterns
- **Trees** — 10 problems + `TreeNode.java` + common patterns
- **Heap** — 8 problems + common patterns
- **Graphs** — 10 problems + common patterns
- **DynamicProgramming** — 10 problems + common patterns
- **TrieUnionFind** — 6 problems + `UnionFind.java` + common patterns

## Cleanup done

- Deleted compiled `*.class` files under PreparationGuide
- Removed legacy folders with no notes / already covered:
  - `StackExamples/` → use `Stack/`
  - `PriorityQueueHeap/` → use `Heap/`
  - `Recursion/` → covered enough via DP / trees study path
  - `FastAndSlowPointers/` → covered by `LinkedList/` cycle problems
- `.gitignore` already ignores `*.class` and `out/`

DSA now contains only the main interview track folders + this roadmap.