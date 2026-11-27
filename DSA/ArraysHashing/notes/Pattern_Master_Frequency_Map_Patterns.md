# Pattern Master — Frequency Map Patterns

> **Java:** `../FrequencyMapPatterns.java`  
> **Package:** `DSA.ArraysHashing`  
> **Role:** HashMap counting / frequency template

---

## What is inside

| Method | Idea | Related full notes |
|---|---|---|
| `firstNonRepeating` | First char with count 1 | Hashing family |
| `firstRepeating` | First char seen twice | Hashing family |
| `mostFrequentElement` | Max frequency in array | Similar to Top K Frequent |
| `areAnagrams` | Same char counts | `Problem_03_Valid_Anagram.md` |

---

## Mental picture

```text
Pass 1: build counts
s = "swiss"
s→2, w→1, i→1

Pass 2: scan original order
s (2) skip, w (1) → answer 'w'
```

Reusable template:

1. Count with `HashMap` (or `int[26]` for lowercase letters)  
2. Scan again to answer the question  

---

## Interview say-aloud

“I use a frequency map. First pass counts occurrences. Second pass walks the original order and applies the rule — first unique, first duplicate, or compare two maps for anagrams.”

---

## Practice

- [ ] Run `FrequencyMapPatterns`
- [ ] Re-code `areAnagrams` and `firstNonRepeating`
- [ ] Explain when `int[26]` is better than `HashMap`
