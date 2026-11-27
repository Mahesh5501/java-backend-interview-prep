# Problem 04 — Group Anagrams

> **Pattern:** Canonical key → HashMap of groups  
> **Java file:** `../GroupAnagrams.java`  
> **Goal:** Know why we hash a “fingerprint” of each word, and which fingerprints to use.

---

## 1. Problem statement

Given an array of strings, group the anagrams together.

Order of groups (and order inside a group) can vary.

### Example

```text
Input:  ["eat","tea","tan","ate","nat","bat"]

Output (one valid grouping):
  [["bat"],["nat","tan"],["ate","eat","tea"]]
```

`eat`, `tea`, `ate` share letters → one group.  
`tan`, `nat` → another.  
`bat` alone.

---

## 2. Mental picture

Anagrams share the same character bag. Give every word a **key** that is the same for anagrams and different otherwise. Put words with the same key in the same list.

```text
eat → key "aet"
tea → key "aet"   } same bucket
ate → key "aet"

tan → key "ant"
nat → key "ant"

bat → key "abt"
```

### Jargon

| Term | Meaning |
|---|---|
| **Canonical key** | Fingerprint of a word (sorted chars, or count signature) |
| **Group map** | `Map<key, List<word>>` |

---

## 3. How to start thinking

1. Brute force? For each word, compare to every other with an anagram check → slow.
2. Better? One pass: compute key, append word to that key’s list.
3. What key? Sorted string (simple) or count string (faster for long words).

---

## 4. Approach 1 — Brute force (pairwise anagram checks)

### Idea

For each ungrouped word, scan remaining words and pull anagrams into a group.

### Complexity

Roughly `O(n² * k)` where `k` = word length — too slow for interviews as final answer.

### When to mention

Only as the “naive” starting point verbally.

---

## 5. Approach 2 — Sorted string as key (what we use in the Java file)

### Idea

```text
key = sort characters of word
map.computeIfAbsent(key, ...).add(word)
return all map values
```

### Why this approach?

- Anagrams sort to the **same** string
- Easy to code and explain
- One pass over the word list

### Java method

`groupAnagrams` (+ helper `sortedKey`)

```java
private static String sortedKey(String word) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    return new String(chars);
}

public static List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();
    for (String word : strs) {
        String key = sortedKey(word);
        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
    }
    return new ArrayList<>(groups.values());
}
```

### Dry run

| word | key | map buckets |
|---|---|---|
| eat | aet | aet→[eat] |
| tea | aet | aet→[eat,tea] |
| tan | ant | aet→…, ant→[tan] |
| ate | aet | aet→[eat,tea,ate] |
| nat | ant | ant→[tan,nat] |
| bat | abt | abt→[bat] |

### Complexity

| Measure | Value | Why |
|---|---|---|
| **Time** | `O(n * k log k)` | Sort each word of length `k` |
| **Extra space** | `O(n * k)` | Store all words + keys |

---

## 6. Approach 3 — Count signature as key (faster alternative)

### Idea

Build a key from letter counts, e.g. `"a1e1t1"` or a fixed `#1#0#0#...` of 26 counts.

Same counts → same key → same group. **No sorting** per word → `O(n * k)` time.

### Why use it?

Long words (`k` large): counting beats sorting.

### Why we still teach sorted key first

Simpler to write under pressure. Mention count-key as the follow-up optimization.

*(Optional: add a second method later; core file keeps sorted key for clarity.)*

---

## 7. What we use and why

| Approach | Time | Use when |
|---|---|---|
| Pairwise grouping | `O(n²·k)` | Verbal only |
| **Sorted key + map** | `O(n·k log k)` | **Default in our Java** — clear |
| Count signature + map | `O(n·k)` | Follow-up / long strings |

---

## 8. Hardest part

### Mistake — Using the word itself as key

Different anagrams are different strings — they would not group.

### Mistake — Mutating the same char array for every word

Create a **new** char array (or copy) per word before sorting.

### Mistake — Forgetting empty string / single letter

They are valid groups — empty key `""` is fine.

---

## 9. Interview explanation — say this aloud

“I group by a canonical key. For each word I sort its characters; that sorted string is the map key. Anagrams share the key, so I append the word to that list. At the end I return the map values. Time `O(n k log k)`. If needed I can switch the key to a 26-count signature for `O(n k)`.”

---

## 10. Link to Java

| Approach | Method |
|---|---|
| Sorted key grouping | `groupAnagrams` / `sortedKey` |
