# Problem 06 — Product of Array Except Self

> **Pattern:** Prefix / suffix products (no division)  
> **Java file:** `../ProductOfArrayExceptSelf.java`  
> **Goal:** See why left×right products replace division, and how we reuse the output array.

---

## 1. Problem statement

Given `nums`, return `output` where:

```text
output[i] = product of every nums[j] where j != i
```

Rules:

- **Do not use division**
- Follow-up: `O(1)` extra space (the output array does not count)

### Example

```text
Input:  [1, 2, 3, 4]
Output: [24, 12, 8, 6]

Because:
  index 0: 2*3*4 = 24
  index 1: 1*3*4 = 12
  index 2: 1*2*4 = 8
  index 3: 1*2*3 = 6
```

---

## 2. Mental picture

For index `i`:

```text
output[i] = (product of everything LEFT of i)
          × (product of everything RIGHT of i)
```

```text
nums:     1   2   3   4
left:     1   1   2   6     (running product before i)
right:   24  12   4   1     (running product after i)
output:  24  12   8   6
```

---

## 3. How to start thinking

1. Brute: for each i, multiply all other j → `O(n²)`.
2. Division trick: totalProduct / nums[i] → forbidden (and breaks on zeros).
3. Optimal: precompute left products and right products.

---

## 4. Approach 1 — Brute force

### Idea

Nested loops: for each `i`, multiply all `j != i`.

### Java method

`productExceptSelfBrute`

### Complexity

`O(n²)` time, `O(1)` extra besides output.

### When

Explain first only.

---

## 5. Approach 2 — Why division fails (know this)

```text
output[i] = (product of all) / nums[i]
```

Problems:

1. Problem forbids division.
2. Zeros: division by zero / wrong counts of zeros need special cases.

Do **not** use this in the final answer.

---

## 6. Approach 3 — Prefix then suffix into output (optimal — what we use)

### Idea

**Pass 1 (left → right):**  
`out[i] = product of all left of i` (running `prefix`).

**Pass 2 (right → left):**  
Multiply `out[i]` by product of all right of i (running `suffix`).

### Why this approach?

- No division
- Handles zeros naturally
- Extra space `O(1)` besides `out` (only two ints: prefix & suffix)

### Java method

`productExceptSelf`

```java
public static int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] out = new int[n];

    int prefix = 1;
    for (int i = 0; i < n; i++) {
        out[i] = prefix;
        prefix *= nums[i];
    }

    int suffix = 1;
    for (int i = n - 1; i >= 0; i--) {
        out[i] *= suffix;
        suffix *= nums[i];
    }
    return out;
}
```

### Dry run

`[1,2,3,4]`

After pass 1 (`out` = left products):

```text
out = [1, 1, 2, 6]
```

Pass 2:

| i | out[i] before | suffix | out[i] after | new suffix |
|---|---|---|---|---|
| 3 | 6 | 1 | 6 | 4 |
| 2 | 2 | 4 | 8 | 12 |
| 1 | 1 | 12 | 12 | 24 |
| 0 | 1 | 24 | 24 | 24 |

Result `[24,12,8,6]`.

### Complexity

| Time | Extra space |
|---|---|
| `O(n)` | `O(1)` besides output |

---

## 7. What we use and why

| Approach | Java method | Time | Space | Use |
|---|---|---|---|---|
| Brute multiply | `productExceptSelfBrute` | `O(n²)` | `O(1)` | Verbal |
| Division | — | `O(n)` | `O(1)` | **Forbidden / fragile** |
| **Prefix×suffix** | `productExceptSelf` | `O(n)` | `O(1)` | **Interview answer** |

Alternative: two arrays `left[]` and `right[]` — clearer to learn, same idea, uses `O(n)` extra. Then compress into one `out[]` for the follow-up.

---

## 8. Hardest part

### Zeros

One zero: every index except the zero’s index becomes 0; the zero’s index gets product of others.  
Two zeros: entire output is zeros.  
Prefix/suffix handles this without special cases.

### Mistake — Including nums[i] in prefix before writing out[i]

Write `out[i] = prefix` **first**, then `prefix *= nums[i]`.

---

## 9. Interview explanation — say this aloud

“Brute force is `O(n²)`. Division is not allowed and breaks with zeros. Instead, `output[i]` is left product times right product. I fill the output with prefix products left to right, then multiply suffix products right to left. Time `O(n)`, extra space `O(1)` besides the output.”

---

## 10. Link to Java

| Approach | Method |
|---|---|
| Brute | `productExceptSelfBrute` |
| Prefix × suffix | `productExceptSelf` |
