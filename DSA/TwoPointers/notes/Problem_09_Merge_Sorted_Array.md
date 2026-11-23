# Problem 09 — Merge Two Sorted Arrays

> **Pattern:** Two pointers across two sorted arrays  
> **Goal:** Learn the simple merge first. Forget `m` and `n` for now.

---

## 1. Problem statement (simple version)

You are given **two sorted arrays**:

```text
a = [1, 2, 3]
b = [2, 5, 6]
```

Merge them into **one sorted array**.

```text
Output: [1, 2, 2, 3, 5, 6]
```

That is the whole problem.

### More examples

```text
a = [1, 4, 7]
b = [2, 3, 8]
Output: [1, 2, 3, 4, 7, 8]

a = [1, 2]
b = []
Output: [1, 2]

a = []
b = [5]
Output: [5]
```

No trailing zeros.  
No `m`.  
No `n`.  
Just two arrays.

---

## 2. Mental picture — one picture to keep in mind

Think of two sorted queues:

```text
a:  1  2  3
    ^
    i

b:  2  5  6
    ^
    j

result:  _  _  _  _  _  _
         ^
         k
```

At every step:

1. Look at `a[i]` and `b[j]`.
2. Put the **smaller** one into `result[k]`.
3. Move that pointer forward.
4. Move `k` forward.

> **Read it as:** “Always pick the smaller front value from the two arrays.”

---

## 3. How to start thinking

Ask yourself:

1. Are both arrays already sorted? Yes.
2. Can I just join them and sort again? Yes, but that ignores the sorted order.
3. Can I build the answer in one pass? Yes — compare the current values from both sides.
4. What if one array finishes early? Copy the leftovers from the other array.

---

## 4. Approach 1 — Brute force (copy + sort)

### Idea

1. Make a new array of size `a.length + b.length`.
2. Copy all values from `a`.
3. Copy all values from `b`.
4. Sort the new array.

### Why it works

Sorting always produces a sorted result. It does not use the fact that `a` and `b` were already sorted.

### Java

```java
public static int[] mergeBruteForce(int[] a, int[] b) {

    int[] result = new int[a.length + b.length];

    for (int i = 0; i < a.length; i++) {
        result[i] = a[i];
    }

    for (int i = 0; i < b.length; i++) {
        result[a.length + i] = b[i];
    }

    Arrays.sort(result);
    return result;
}
```

### Dry run

```text
a = [1, 2, 3]
b = [2, 5, 6]

after copy: [1, 2, 3, 2, 5, 6]
after sort: [1, 2, 2, 3, 5, 6]
```

### Complexity

- **Time:** `O((a + b) log(a + b))`
- **Space:** `O(a + b)` for the result

### Limitation

It works, but it is slower than needed because both inputs are already sorted.

---

## 5. Approach 2 — Two pointers (best for this problem)

### Idea

Use three indexes:

- `i` → current position in `a`
- `j` → current position in `b`
- `k` → current write position in `result`

Compare `a[i]` and `b[j]`.  
Put the smaller value into `result[k]`.  
Move that pointer and `k`.

When one array is finished, copy the rest of the other array.

### Why this works

Because both arrays are sorted:

- the next unused value in `a` is the smallest remaining value from `a`
- the next unused value in `b` is the smallest remaining value from `b`

So the smaller of those two is the next value for the merged answer.

### Java

```java
public static int[] mergeTwoPointers(int[] a, int[] b) {

    int[] result = new int[a.length + b.length];

    int i = 0; // reads a
    int j = 0; // reads b
    int k = 0; // writes result

    while (i < a.length && j < b.length) {

        if (a[i] <= b[j]) {
            result[k] = a[i];
            i++;
        } else {
            result[k] = b[j];
            j++;
        }

        k++;
    }

    while (i < a.length) {
        result[k] = a[i];
        i++;
        k++;
    }

    while (j < b.length) {
        result[k] = b[j];
        j++;
        k++;
    }

    return result;
}
```

---

## 6. The hardest part explained carefully

### What confuses beginners?

People often try to merge “in place” too early, or introduce `m` and `n` before understanding the basic idea.

For learning, keep it simple:

```text
two sorted arrays → one new sorted array
```

### Before / after of one step

```text
a: 1  2  3
   i

b: 2  5  6
   j

Compare 1 and 2.
1 is smaller.
Put 1 into result.
```

After that step:

```text
result: 1  _  _  _  _  _
        k moves here ^

a: 1  2  3
      i

b: 2  5  6
   j
```

Next compare `2` and `2`.  
They are equal. Using `<=` takes from `a` first. That is fine.

---

## 7. Detailed dry run

```text
a = [1, 2, 3]
b = [2, 5, 6]
```

| Step | i | j | a[i] | b[j] | Choose | result so far |
|---:|---:|---:|---:|---:|---|---|
| 1 | 0 | 0 | 1 | 2 | 1 from a | `[1]` |
| 2 | 1 | 0 | 2 | 2 | 2 from a | `[1, 2]` |
| 3 | 2 | 0 | 3 | 2 | 2 from b | `[1, 2, 2]` |
| 4 | 2 | 1 | 3 | 5 | 3 from a | `[1, 2, 2, 3]` |
| 5 | 3 | 1 | — | 5 | a finished | copy rest of b |
| 6 | 3 | 1 | — | 5 | 5 | `[1, 2, 2, 3, 5]` |
| 7 | 3 | 2 | — | 6 | 6 | `[1, 2, 2, 3, 5, 6]` |

Final answer:

```text
[1, 2, 2, 3, 5, 6]
```

### Visual for the first three picks

```text
Start:
a: [1, 2, 3]     b: [2, 5, 6]     result: []
    i                 j

Pick 1:
a: [1, 2, 3]     b: [2, 5, 6]     result: [1]
       i              j

Pick 2:
a: [1, 2, 3]     b: [2, 5, 6]     result: [1, 2]
          i           j

Pick 2 from b:
a: [1, 2, 3]     b: [2, 5, 6]     result: [1, 2, 2]
          i              j
```

---

## 8. Why this never misses a value

- Every value from `a` is written exactly once.
- Every value from `b` is written exactly once.
- At each step we always take the next smallest available value.
- Leftover values are copied at the end.

So the result contains all values, and they stay sorted.

---

## 9. Complexity comparison

| Approach | Time | Extra space | Notes |
|---|---:|---:|---|
| Copy + sort | `O((a+b) log(a+b))` | `O(a+b)` | Easy, but slower |
| Two pointers | `O(a + b)` | `O(a+b)` for result | Best for two sorted arrays |

**Interview choice for this version:** two pointers.

---

## 10. Edge cases

| a | b | Output | What it checks |
|---|---|---|---|
| `[1, 2, 3]` | `[2, 5, 6]` | `[1, 2, 2, 3, 5, 6]` | Normal merge |
| `[1, 2]` | `[]` | `[1, 2]` | Empty second array |
| `[]` | `[5]` | `[5]` | Empty first array |
| `[1, 1]` | `[1, 1]` | `[1, 1, 1, 1]` | Duplicates |
| `[4, 5]` | `[1, 2]` | `[1, 2, 4, 5]` | All of b smaller than a |

---

## 11. Common mistakes

1. **Forgetting leftovers:** after one array ends, still copy the other array.
2. **Using `a.length` wrong:** result size must be `a.length + b.length`.
3. **Sorting again in the optimal solution:** not needed if two pointers are used correctly.
4. **Jumping to LeetCode `m` and `n` too early:** learn the simple two-array merge first.

---

## 12. How to recognise this pattern

Clues:

- You have two sequences.
- Both are already sorted.
- You need one combined sorted sequence.

Reusable sentence:

> Compare the current values of both arrays and always take the smaller one.

---

## 13. Interview explanation — say this aloud

“I have two sorted arrays. I create a result array of size `a.length + b.length`. I keep one pointer in each input array. At every step I compare the two current values and write the smaller one into the result. When one array is finished, I copy the remaining values from the other array. This takes linear time because each element is visited once.”

---

## 14. Interview follow-up questions

### Q1. Why not just concatenate and sort?

That works, but it is slower: `O((a+b) log(a+b))`. Two pointers use the sorted order and run in `O(a+b)`.

### Q2. What if one array is empty?

The first while loop never runs, and the leftover loop copies the non-empty array.

### Q3. Does `<=` or `<` matter?

Both can work. With `<=`, equal values from `a` are taken first. The final array is still sorted.

### Q4. Is extra space required?

For this simple version, yes — we return a new array. That is normal and clear.

---

## 15. Complete runnable Java class

Save as **`MergeSortedArray.java`**.

```java
package DSA.TwoPointers;

import java.util.Arrays;

public class MergeSortedArray {

    public static int[] mergeBruteForce(int[] a, int[] b) {

        int[] result = new int[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }

        for (int i = 0; i < b.length; i++) {
            result[a.length + i] = b[i];
        }

        Arrays.sort(result);
        return result;
    }

    public static int[] mergeTwoPointers(int[] a, int[] b) {

        int[] result = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[k] = a[i];
                i++;
            } else {
                result[k] = b[j];
                j++;
            }
            k++;
        }

        while (i < a.length) {
            result[k] = a[i];
            i++;
            k++;
        }

        while (j < b.length) {
            result[k] = b[j];
            j++;
            k++;
        }

        return result;
    }

    public static void main(String[] args) {

        int[] a = { 1, 2, 3 };
        int[] b = { 2, 5, 6 };

        System.out.println("Array a: " + Arrays.toString(a));
        System.out.println("Array b: " + Arrays.toString(b));
        System.out.println("Brute Force:  " + Arrays.toString(mergeBruteForce(a, b)));
        System.out.println("Two Pointers: " + Arrays.toString(mergeTwoPointers(a, b)));
    }
}
```

### Expected output

```text
Array a: [1, 2, 3]
Array b: [2, 5, 6]
Brute Force:  [1, 2, 2, 3, 5, 6]
Two Pointers: [1, 2, 2, 3, 5, 6]
```

---

## 16. Independent IDE practice

- [ ] Implement copy + sort.
- [ ] Implement two-pointer merge.
- [ ] Trace `[1, 2, 3]` and `[2, 5, 6]` by hand.
- [ ] Test one empty array.
- [ ] Explain why leftovers must be copied.

### Small challenge

Trace:

```text
a = [1, 4, 7]
b = [2, 3, 8]
```

Write each chosen value in order.

---

## 17. Quick revision

Remember only this:

1. `i` reads first array.
2. `j` reads second array.
3. Always take the smaller current value.
4. Copy leftovers at the end.

Reusable sentence:

> **Merge two sorted arrays by always picking the smaller front value.**

---

## 18. Optional later topic (do this only after the simple version is clear)

LeetCode 88 adds a twist:

- `nums1` already has extra empty space at the end
- you must merge **into `nums1`**
- that is where `m` and `n` appear

Ignore that until the simple two-array merge feels easy.

---

## 19. Next problem

**Problem 10 — Squares of a Sorted Array**  
Main concept: compare absolute values at both ends
