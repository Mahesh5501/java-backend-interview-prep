# Problem 02 — Maximum Average Subarray I

> **Pattern:** Fixed-size Sliding Window  
> **Goal:** Return the **maximum average** of any contiguous subarray of length **k** (LeetCode 643).  
> **Java file:** `../MaximumAverageSubarray.java`  

---

## 1. Problem statement

You are given an integer array `nums` and an integer `k`. Find the maximum average value of any contiguous subarray whose length is exactly `k`. Return a **double**.

### Example 1

```text
Input:  nums = [1, 12, -5, -6, 50, 3], k = 4
Output: 12.75
```

Subarray `[12, -5, -6, 50]` → sum `51` → `51 / 4 = 12.75`.

### Example 2

```text
Input:  nums = [5], k = 1
Output: 5.0
```

### Constraints

- `1 <= k <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

---

## 2. Mental picture — same ruler, different report card

```text
nums: [ 1 | 12 | -5 | -6 | 50 | 3 ]
      ^---------- window k=4 ----------^
      sum = 2, avg = 0.5

            slide →

      [ 1 | 12 | -5 | -6 | 50 | 3 ]
          ^---------- window ----------^
          sum = 51, avg = 12.75  ← winner
```

Average = sum ÷ k. **k is fixed**, so the window with **max sum** also has **max average**.

---

## 3. Thinking questions

1. **Is this different from Problem 01?** Same sliding mechanics; output is `sum / k`.
2. **Track max average or max sum?** Max sum is enough (divide once at the end).
3. **Negatives?** Yes — average can be negative; still use same window.
4. **Floating point?** Cast: `(double) sum / k`.

---

## 4. Approach 1 — Brute force

### Java method

`findMaxAverageBruteForce`

Sum every window of size `k`, compute `sum/k`, keep the max.

```java
for (int start = 0; start <= nums.length - k; start++) {
    int sum = 0;
    for (int i = start; i < start + k; i++) sum += nums[i];
    best = Math.max(best, (double) sum / k);
}
```

| | Brute |
|---|---|
| Time | `O(n·k)` |
| Space | `O(1)` |

---

## 5. Approach 2 — Optimal

### Java method

`findMaxAverage`

### Key insight

```text
k is the same for every window
→ argmax(average) = argmax(sum)
```

### Steps

1. Compute `windowSum` for first `k` nums.
2. Slide with `+nums[right] - nums[right-k]`.
3. Track `bestSum`.
4. Return `(double) bestSum / k`.

### Alternative (interview-friendly)

Update `bestAvg` whenever the window is full:

```java
double currentAvg = (double) sum / k;
bestAvg = Math.max(bestAvg, currentAvg);
```

Same complexity.

### Dry run — k=4 on example

| window | sum | avg |
|---|---:|---:|
| [1,12,-5,-6] | 2 | 0.5 |
| [12,-5,-6,50] | 51 | **12.75** |
| [-5,-6,50,3] | 42 | 10.5 |

---

## 5b. Approach 3 — Update max average each full window

Grow with `right`; when window length is `k`, compare `(double) sum / k`.

### Java method

`findMaxAveragePerStep`

---

## 6. Hardest part — integer division trap

### Before (wrong)

```java
return sum / k;   // integer division → truncated average
```

### After (correct)

```java
return (double) sum / k;
// or
return (double) bestSum / k;
```

---

## 7. Why correct

Maximizing sum over equal-length windows maximizes average. Sliding window visits each valid window once with correct sum.

---

## 8. Complexity

| Method | Time | Space |
|---|---:|---:|
| Brute | `O(n·k)` | `O(1)` |
| Sliding window | `O(n)` | `O(1)` |

---

## 9. Edge cases

| Case | Note |
|---|---|
| `k = 1` | Max element as double |
| All negatives | Most negative window wins |
| Large values | Use `long` for sum if overflow risk (constraints usually fit `int`) |

---

## 10. Common mistakes

| Mistake | Fix |
|---|---|
| Integer division | Cast to `double` |
| Recomputing full window sum each slide | Use enter/leave |
| Initializing `bestAvg = 0` when all avgs negative | Use `Double.NEGATIVE_INFINITY` if comparing per-step |

---

## 11. Interview — say aloud

> “For fixed k, maximum average equals maximum sum divided by k. I use the same O(n) sliding window as max sum of k elements, track the best sum, and return it divided by k as a double.”

---

## 12. Follow-ups

**Q: Maximum Average Subarray II (variable length)?**  
A: Different techniques (binary search on answer or math) — not fixed window.

**Q: Why not compare averages each step?**  
A: Valid and often clearer in interviews; dividing once at the end is slightly fewer ops.

---

## 13. Complete Java

See `../MaximumAverageSubarray.java`.

```java
package DSA.SlidingWindow;

public class MaximumAverageSubarray {

    public static double findMaxAverage(int[] nums, int k) {
        int windowSum = 0;
        for (int i = 0; i < k; i++) windowSum += nums[i];
        int bestSum = windowSum;
        for (int right = k; right < nums.length; right++) {
            windowSum += nums[right];
            windowSum -= nums[right - k];
            bestSum = Math.max(bestSum, windowSum);
        }
        return (double) bestSum / k;
    }

    public static void main(String[] args) {
        System.out.println(findMaxAverage(new int[] { 1, 12, -5, -6, 50, 3 }, 4)); // 12.75
    }
}
```

```text
nums: [1, 12, -5, -6, 50, 3], k = 4
Optimal: 12.75
```

---

## Link to Java

| Approach | Method |
|---|---|
| Brute force | `findMaxAverageBruteForce` |
| Track max sum, divide once (optimal) | `findMaxAverage` |
| Per-step average compare | `findMaxAveragePerStep` |

Run `main` in the Java class to exercise every approach.

---

## 14. Practice checklist

- [ ] Code max-sum window, then one line for return type.
- [ ] Trace example with sum and avg columns.
- [ ] Verbalize “max sum ↔ max avg when k fixed.”

---

## 15. Quick revision

> **Problem 01 + divide by k + double cast.**

---

## 16. Next problem

**Problem 03 — Minimum Size Subarray Sum**  
**Variable** window — length **changes**; find **shortest** with sum ≥ target.