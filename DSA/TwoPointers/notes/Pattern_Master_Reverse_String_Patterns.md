# Pattern Master — Reverse String Patterns

> **Java:** `../ReverseStringPatterns.java`  
> **Package:** `DSA.TwoPointers`  
> **Role:** Same problem, many techniques (good for follow-up questions)

---

## What is inside

| Method | Technique | Extra space |
|---|---|---|
| `reverseWithStringBuilder` | Built-in | O(n) |
| `reverseWithTwoPointers` | Swap ends on a char array | O(n) for array copy in Java String |
| `reverseWithRecursion` | Recurse on substring / indices | O(n) stack |
| `reverseWithStack` | Push all, pop all | O(n) |
| `reverseWithBackwardLoop` | Build from end index | O(n) |

---

## Mental picture (two pointers)

```text
h e l l o
L       R
swap → o e l l h
  L   R
swap → o l l e h
    LR
done → o l l e h
```

---

## Interview tip

Interviewers often ask:

1. Do it with two pointers  
2. Do it without StringBuilder  
3. What is time/space?

Lead with **two pointers on a char array**, then mention alternatives.

---

## Interview say-aloud

“Java strings are immutable, so I convert to a char array, swap left and right until they meet, then build a new string. That is O(n) time. Stack or StringBuilder also work but use extra structures.”

---

## Practice

- [ ] Run `ReverseStringPatterns`
- [ ] Implement only the two-pointer version from memory
- [ ] Explain why pure in-place reverse of a Java `String` object is impossible
