# Problem 02 — Contains Duplicate

> **Pattern:** Seen-before check (HashSet / sort / frequency)  
> **Java file:** `../ContainsDuplicate.java`  
> **Goal:** Know every approach, why we pick HashSet in interviews, and which Java method is which.

---

## 1. Problem statement

Given an integer array `nums`, return `true` if any value appears **at least twice**. Return `false` if every element is distinct.

### Example 1

```text
Input:  [1, 2, 3, 1]
Output: true
```

`1` appears twice.

### Example 2

```text
Input:  [1, 2, 3, 4]
Output: false
```

All unique.

### Example 3

```text
Input:  [1, 1, 1, 3, 3, 4, 3, 2, 4, 2]
Output: true
```

### Constraints (typical)

- `1 <= nums.length <= 10^5`
- Aim for **O(n)** time if possible

---

## 2. Mental picture

As you walk the array, ask:

> “Have I already seen this number?”

```text
nums = [1, 2, 3, 1]

seen: {}
1 → not seen → add {1}
2 → not seen → add {1,2}
3 → not seen → add {1,2,3}
1 → already in seen → true
```

That question is exactly what a **HashSet** answers in average `O(1)`.

### Jargon

| Term | Meaning here |
|---|---|
| **HashSet** | Collection that stores unique values; `add` returns false if already present |
| **Frequency map** | Map from value → how many times it appears |
| **Adjacent after sort** | Duplicates sit next to each other after sorting |

---

## 3. How to start thinking

1. What am I detecting? A value that appears more than once.
2. Brute force? For each index, scan the rest of the array.
3. Can sorting help? Yes — duplicates become neighbors.
4. Can a Set help? Yes — “seen before?” in one pass.
5. Do I need counts? Only if the problem asked “how many times” — here a Set is enough.

---

## 4. Approach 1 — Brute force (nested scan)

### Idea

For each `i`, scan `j = i+1 .. n-1`. If `nums[i] == nums[j]`, return true.

### Why mention it?

Shows you understand “duplicate = same value at two indices”.

### Complexity

| Time | Extra space |
|---|---|
| `O(n²)` | `O(1)` |

### Limitation

Too slow for `n = 10^5`.

### When to use

Tiny arrays only. We do **not** keep this as a separate method in Java for interview practice — focus on the three practical approaches below.

---

## 5. Approach 2 — Sort, then compare neighbors

### Idea

1. Sort the array.
2. Walk once; if `nums[i] == nums[i-1]`, duplicate found.

```text
Before: [3, 1, 2, 1]
After:  [1, 1, 2, 3]
              ^  ^
         equal → true
```

### Why does it work?

Sorting groups equal values together. Any duplicate must sit next to its twin.

### Java method

`containsDuplicateSort` in `ContainsDuplicate.java`

### Trade-offs

| Pros | Cons |
|---|---|
| Extra space `O(1)` if in-place sort allowed | Destroys original order |
| Easy to explain | Time `O(n log n)` |

### When to use

Interviewer wants **low extra memory** and allows modifying the array (or you sort a copy).

---

## 6. Approach 3 — HashSet (optimal for interviews — what we use)

### Core idea

```text
seen = empty set
for each x in nums:
    if x already in seen → return true
    else add x
return false
```

In Java, `seen.add(x)` returns `false` when `x` was already present — one clean check.

### Why this approach?

- One pass, average `O(1)` membership test
- Does not need counts — presence is enough
- Does not destroy the array

### Why Set instead of Map?

We only care “seen or not”, not “how many times”. A Map frequency works but stores more than we need.

### Java method

`containsDuplicateSet` in `ContainsDuplicate.java`

```java
public static boolean containsDuplicateSet(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int x : nums) {
        if (!seen.add(x)) {
            return true;  // add failed → already present
        }
    }
    return false;
}
```

### Dry run

`[1, 2, 3, 1]`

| x | seen before | add result | action |
|---|---|---|---|
| 1 | `{}` | true | continue, `{1}` |
| 2 | `{1}` | true | continue, `{1,2}` |
| 3 | `{1,2}` | true | continue, `{1,2,3}` |
| 1 | `{1,2,3}` | **false** | return true |

### Complexity

| Measure | Value |
|---|---|
| **Time** | `O(n)` average |
| **Extra space** | `O(n)` worst case (all unique) |

---

## 7. Approach 4 — Frequency map (works, usually overkill)

### Idea

Count occurrences; if any count becomes `> 1`, return true.

### Java method

`containsDuplicateFreq`

### Why mention it?

Same family as anagram / top-k frequency problems. Good to know — but for **this** problem a Set is cleaner.

### When to use

If the problem changes to “return counts” or “elements appearing more than k times”.

---

## 8. What we use and why

| Approach | Java method | Time | Space | Use when |
|---|---|---|---|---|
| Nested scan | *(verbal only)* | `O(n²)` | `O(1)` | Explain first |
| Sort + adjacent | `containsDuplicateSort` | `O(n log n)` | `O(1)` / `O(n)` copy | Memory tight |
| **HashSet** | `containsDuplicateSet` | `O(n)` | `O(n)` | **Default interview answer** |
| Freq map | `containsDuplicateFreq` | `O(n)` | `O(n)` | Need counts later |

**Interview default:** HashSet one pass.

---

## 9. Hardest part

### Mistake — Using List.contains

`List.contains` is `O(n)` each time → overall `O(n²)`. Use `HashSet`.

### Mistake — Sorting then forgetting you mutated input

If caller needs original order, sort a **copy**.

### Mistake — Thinking early exit is wrong

Returning as soon as a duplicate appears is correct and faster in practice.

---

## 10. Complexity comparison

| Approach | Time | Extra space |
|---|---|---|
| Brute nested | `O(n²)` | `O(1)` |
| Sort + scan | `O(n log n)` | `O(1)` in-place |
| HashSet | `O(n)` | `O(n)` |
| Freq map | `O(n)` | `O(n)` |

---

## 11. Edge cases

| Input | Expected | Checks |
|---|---|---|
| `[1]` | false | Single element |
| `[1,1]` | true | Immediate duplicate |
| `[1,2,3,4]` | false | All unique |
| Large n, one late duplicate | true | Must scan far |

---

## 12. Interview explanation — say this aloud

“Brute force is nested loops — `O(n²)`. I can sort and compare neighbors — `O(n log n)`. Best for interviews: a HashSet. Walk once; if `add` fails, the value was already seen, return true. Time `O(n)`, space `O(n)`. I use a Set instead of a Map because I only need presence, not counts.”

---

## 13. Link to Java

| Approach | Method |
|---|---|
| Sort + adjacent | `containsDuplicateSort` |
| HashSet (preferred) | `containsDuplicateSet` |
| Frequency map | `containsDuplicateFreq` |

Run `main` to compare Set vs Sort on sample arrays.
