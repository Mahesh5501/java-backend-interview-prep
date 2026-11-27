# Arrays & Hashing — Common Patterns Reference

A reusable revision guide for Java interviews (Java 8/17).

This file explains patterns shared by many array + hash map / set problems.
Each problem has its own notes file with statement, brute force, optimal code,
dry run, complexity, edge cases, and interview script.

Folder layout:

```text
DSA/ArraysHashing/
├── notes/          ← study notes (.md)
└── *.java          ← runnable programs (package DSA.ArraysHashing)
```

### How to read each problem (simple loop)

For every `Problem_XX_*.md` + matching `.java`:

1. Read the **problem + mental picture** in the note (no code yet).
2. Read **Approach 1 (brute)** — idea, why, complexity, when to use.
3. Read **optimal / alternatives** — why we pick this structure (Map vs Set vs sort…).
4. Open the Java file — each method is labeled `APPROACH 1 / 2 / …` with short “why” comments.
5. Run `main` and match the dry run table in the note.

**Rule:** Notes explain *why*. Java shows *how*. Method names in the note match method names in Java.

Core problems rewritten this way: **01 Two Sum → 08 Subarray Sum Equals K**.

Company drills from old `cubic` / `Wise` live **in this folder** too (`MergeIntervals`, `FrequencyMapPatterns`, …) — study them after Problems 01–08.

---

## 1. What is “Arrays & Hashing”?

It is not one trick. It is a **toolbox**:

| Tool | Typical use |
|---|---|
| **Array scan** | Read elements in order, two loops, prefix walks |
| **HashMap** | “Have I seen this value before?” Counts, complement lookup |
| **HashSet** | “Is this value duplicate / unique?” Membership only |
| **Prefix sum + map** | Subarray sum equals K, count subarrays |
| **Sort + scan** | Group anagrams key, longest consecutive after sort |
| **Bucket / counting array** | Frequencies when values are small or bounded |

**Interview rule:** Name what you store in the map (key → meaning of value).

---

## 2. How to recognise Arrays & Hashing

Clues:

- Need **O(1) lookup** by value, index, or count
- “Find pair / complement / duplicate / anagram”
- “Count frequency” or “top K frequent”
- Subarray sum / count subarrays with target sum (often **prefix + map**)
- Output can be built in one pass with extra memory
- Constraints allow `O(n)` extra space

Not always hashing:

- **Product Except Self** → prefix/suffix products, no division
- **Longest Consecutive** → hash set + only extend from sequence **starts**

---

## 3. Pattern A — Complement lookup (Two Sum)

Store `value → index` (or “seen complement”) while scanning.

```java
Map<Integer, Integer> seen = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int need = target - nums[i];
    if (seen.containsKey(need)) {
        return new int[] { seen.get(need), i };
    }
    seen.put(nums[i], i);
}
```

**When:** pair with sum / difference, “find other element”.

---

## 4. Pattern B — Frequency map

```java
Map<Integer, Integer> freq = new HashMap<>();
for (int x : nums) {
    freq.put(x, freq.getOrDefault(x, 0) + 1);
}
```

**When:**

- Contains Duplicate (any count > 1)
- Valid Anagram (compare char counts)
- Top K Frequent (sort/bucket/heap on counts)
- Group Anagrams (bucket by sorted signature or count key)

---

## 5. Pattern C — HashSet membership

```java
Set<Integer> seen = new HashSet<>();
for (int x : nums) {
    if (!seen.add(x)) return true; // duplicate
}
```

**When:** uniqueness, Longest Consecutive (store all values, O(1) contains).

---

## 6. Pattern D — Prefix sum + HashMap

For subarray sum problems:

> `prefix[j] - prefix[i] = target`  →  at index `j`, need `prefix[j] - target` in map.

```java
Map<Integer, Integer> count = new HashMap<>();
count.put(0, 1); // empty prefix
int prefix = 0, ans = 0;
for (int x : nums) {
    prefix += x;
    ans += count.getOrDefault(prefix - k, 0);
    count.put(prefix, count.getOrDefault(prefix, 0) + 1);
}
```

**When:** Subarray Sum Equals K, count of subarrays, sometimes with deque for max/min length variants.

---

## 7. Pattern E — Prefix / suffix products (no extra division)

```java
int n = nums.length;
int[] out = new int[n];
int prefix = 1;
for (int i = 0; i < n; i++) { out[i] = prefix; prefix *= nums[i]; }
int suffix = 1;
for (int i = n - 1; i >= 0; i--) { out[i] *= suffix; suffix *= nums[i]; }
```

**When:** Product of Array Except Self — `O(1)` extra space if output array not counted.

---

## 8. Pattern F — Canonical key for anagram groups

| Key style | Cost |
|---|---|
| Sort characters → string | `O(k log k)` per word of length k |
| `int[26]` count → encoded string | `O(k)` |

Group into `Map<String, List<String>>`.

---

## 9. Pattern G — Top K frequent: bucket vs heap

**Bucket sort (when frequencies ≤ n):**

```text
bucket[frequency] → list of numbers with that frequency
scan buckets from high frequency down, take k
Time O(n), Space O(n)
```

**Min-heap of size k:**

```text
Time O(n log k), good when k is small
```

Both are valid in interviews — state tradeoff.

---

## 10. Pattern H — Longest consecutive sequence

1. Put all numbers in a `Set`.
2. For each `x`, if `x - 1` is **not** in set → `x` starts a chain.
3. Walk `x, x+1, x+2, …` while in set.

**Why not sort?** Sort is `O(n log n)`; set start detection is `O(n)` amortized.

---

## 11. Decision tree (interview)

```text
Need a pair / complement?
  YES → HashMap complement (Pattern A)

Need counts?
  YES → Frequency map (B) or bucket (G)

Only duplicate / unique?
  YES → HashSet (C)

Subarray sum equals K?
  YES → Prefix + map (D)

Each index needs product of all others?
  YES → Prefix/suffix products (E)

Group by anagram shape?
  YES → Canonical key (F)

Longest consecutive integers?
  YES → Set + start only (H)
```

---

## 12. Roadmap — problems in this folder

| # | Problem | Main pattern |
|---:|---|---|
| 01 | Two Sum | Complement map |
| 02 | Contains Duplicate | Set or freq map |
| 03 | Valid Anagram | Frequency (array or map) |
| 04 | Group Anagrams | Map + canonical key |
| 05 | Top K Frequent Elements | Freq map + bucket or heap |
| 06 | Product of Array Except Self | Prefix + suffix |
| 07 | Longest Consecutive Sequence | Hash set, chain from starts |
| 08 | Subarray Sum Equals K | Prefix sum + map |

Study order: 01 → 08.

---

## 13. Complexity cheat sheet

| Pattern | Time | Extra space |
|---|---:|---:|
| One pass map | O(n) | O(n) |
| Two Sum | O(n) | O(n) |
| Group Anagrams | O(n · k log k) sort key | O(n · k) |
| Top K bucket | O(n) | O(n) |
| Top K heap | O(n log k) | O(n) |
| Prefix subarray | O(n) | O(n) |
| Longest consecutive | O(n) | O(n) |
| Product except self | O(n) | O(1) excluding output |

---

## 14. Common mistakes

1. Using `==` on arrays/objects instead of `.equals()` for anagram keys.
2. Forgetting `count.put(0, 1)` in prefix-sum subarray problems.
3. Extending every element in longest consecutive (re-summing chains) → use **start only**.
4. Dividing in Product Except Self when zero exists (use prefix/suffix instead).
5. Returning indices vs values in Two Sum — read the problem.
6. Not handling **negative** numbers in prefix sum (map still works).

---

## 15. Interview phrases that sound prepared

- “I’ll trade `O(n)` space for `O(1)` lookup per element.”
- “Prefix at `j` minus prefix at `i` is the subarray sum from `i+1` to `j`.”
- “I only start a consecutive walk when `x-1` is absent so each index is visited once.”
- “For top K I can use buckets of frequency or a size-k min-heap — bucket is linear when range is n.”

---

## 16. Quick revision

1. **Map** → complement, count, prefix occurrences.  
2. **Set** → duplicate, consecutive membership.  
3. **Prefix** → subarray sums; **prefix/suffix** → products.  
4. **Canonical key** → group anagrams.  
5. **Start-only walk** → longest consecutive.

---

## 17. Next step

Open **Problem 01 — Two Sum** and trace the complement map on paper before coding from memory.