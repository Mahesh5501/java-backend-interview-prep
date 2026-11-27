# PreparationGuide — clean structure

```text
PreparationGuide/
├── DSA/                         ← coding practice (patterns + Java)
│   ├── ArraysHashing/ … TrieUnionFind/
│   └── DSA_Interview_Pattern_Roadmap.md
├── Notes/                       ← theory / revision
│   ├── Interview/
│   ├── DSA_CheatSheets/
│   └── SystemDesign/            ← platform layers + Interview_Problems/
├── MultithreadingConcurrency/   ← ONLY concurrency track (demos + notes)
├── ResiliencePatterns/          ← Retry, CircuitBreaker, RateLimiter, …
├── DesignPatterns/
├── SolidPrinciples/
└── StreamsAndFunctionalInterfaces/
```

## Where old company prep went (inside patterns — not separate folders)

| Old folder | Now lives in |
|---|---|
| `cubic/` frequency / single-pass drills | `DSA/ArraysHashing/` |
| `cubic/` two-pointer / merge / reverse drills | `DSA/TwoPointers/` |
| `cubic/` heap top-k drills | `DSA/Heap/` |
| `Wise/` intervals / overlap / peak | `DSA/ArraysHashing/` |

Study **Problem_01 … Problem_08** first in each pattern folder; company drills (`Pattern_Master_*`, Merge Intervals, …) after.

## Do NOT recreate these (duplicates)

| Folder | Why removed | Use instead |
|---|---|---|
| `cubic/`, `Wise/`, `DSA/Extras/` | Not separate homes | respective pattern folders above |
| `JavaConcurrency/` | Duplicate track | `MultithreadingConcurrency/` |
| `SystemDesign/` (root) | Duplicate home | `Notes/SystemDesign/` |
| `DSA/FastAndSlowPointers/` | Covered | `DSA/LinkedList/` (Happy Number) |
| `DSA/PriorityQueueHeap/` | Covered | `DSA/Heap/` |
| `DSA/StackExamples/` | Covered | `DSA/Stack/` |
| `DSA/Recursion/` | Not in main interview track | skip / add later intentionally |

If OneDrive restores an old folder, delete it again.
