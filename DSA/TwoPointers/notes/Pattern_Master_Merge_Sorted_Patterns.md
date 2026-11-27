# Pattern Master — Merge Sorted Patterns

> **Java:** `../MergeSortedPatterns.java`  
> **Package:** `DSA.TwoPointers`  
> **Role:** Compact revision of merging two sorted arrays

---

## What is inside

| Method | Idea | Related full notes |
|---|---|---|
| `mergeIntoNewArray` | Two pointers → new result array | `Problem_09_Merge_Sorted_Array.md` (simple version) |
| `mergeInPlace` | LeetCode 88 style: merge from the back into `nums1` | Optional advanced twist of Problem 09 |

---

## Mental picture

```text
Simple merge:
a: 1 3 5     i
b: 2 4 6     j
result: pick smaller of a[i], b[j] each time
```

```text
In-place from back (only if you already understand simple merge):
nums1: [1,3,5,_,_,_]   write at the end
nums2: [2,4,6]
place larger values first so you do not overwrite unread data
```

---

## Study tip

Learn **simple two-array merge first** (`Problem_09`).  
Treat `mergeInPlace` as a follow-up only when that feels easy.

---

## Interview say-aloud

“Because both arrays are sorted, I compare current heads and always take the smaller one into the result. If one array finishes, I copy the leftovers. For the in-place LeetCode version, I fill from the back using the free slots.”

---

## Practice

- [ ] Run `MergeSortedPatterns`
- [ ] Re-code `mergeIntoNewArray` from memory
- [ ] Only then try explaining `mergeInPlace`
