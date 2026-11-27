# Problem 01 — Maximum Sum of K Consecutive Elements

> **Pattern:** Fixed-size Sliding Window  
> **Goal:** Find the **largest sum** among all contiguous subarrays of **exactly** `k` elements.

---

## 1. Problem statement

Given an integer array `arr` and an integer `k`, return the maximum sum of any **contiguous** subarray whose length is exactly `k`.

### Example 1

```text
Input:  arr = [2, 1, 5, 1, 3, 2], k = 3
Output: 9
```

Explanation: `[5, 1, 3]` sums to `9`. No other length-3 window is larger.

### Example 2

```text
Input:  arr = [2, 3, 4, 1], k = 2
Output: 7
```

Explanation: `[3, 4]` → sum `7`.

### Constraints (typical)

- `1 <= k <= arr.length <= 10^5`
- Values fit in a 32-bit integer

---

## 2. Mental picture — the window is a ruler of length k

```text
arr:  [ 2 | 1 | 5 | 1 | 3 | 2 ]
       ^-------^
       window size = 3, sum = 8

       slide →

      [ 2 | 1 | 5 | 1 | 3 | 2 ]
            ^-------^
            sum = 7

            slide →

      [ 2 | 1 | 5 | 1 | 3 | 2 ]
                ^-------^
                sum = 9  ← best so far
```

Think:

> **One box enters from the right. One box leaves from the left. The window never changes width.**

That is **Pattern A — fixed window** from `Sliding_Window_Common_Patterns.md`.

---

## 3. How to start thinking (simple questions)

1. **Is the section continuous?** Yes — subarray, not subsequence.
2. **Is the size always k?** Yes — fixed window.
3. **Do I re-add all k numbers every time?** That works (brute), but we can do better.
4. **What changes when we slide?** Lose `arr[i]`, gain `arr[i+k]`. Update sum in O(1).

If you can say “sum = sum - leaving + entering,” you know the trick.

---

## 4. Approach 1 — Brute force

### Idea

For every start index `i` where a window of size `k` fits, add `k` elements.

### Why it works

You try **every** valid window. The largest sum among them is the answer.

### Java

```java
public static int maxSumBruteForce(int[] arr, int k) {
    int best = Integer.MIN_VALUE;
    for (int start = 0; start <= arr.length - k; start++) {
        int sum = 0;
        for (int i = start; i < start + k; i++) {
            sum += arr[i];
        }
        best = Math.max(best, sum);
    }
    return best;
}
```

### Dry run — `[2,1,5,1,3,2]`, k=3

| start | window | sum | best |
|---:|---|---:|---:|
| 0 | [2,1,5] | 8 | 8 |
| 1 | [1,5,1] | 7 | 8 |
| 2 | [5,1,3] | 9 | **9** |
| 3 | [1,3,2] | 6 | 9 |

### Complexity

| | Brute |
|---|---|
| **Time** | `O(n * k)` |
| **Space** | `O(1)` |

Too slow when `n` and `k` are large.

---

## 5. Approach 2 — Optimal: fixed sliding window

### Steps

1. Sum the first `k` elements → initial `windowSum`.
2. Set `best = windowSum`.
3. For `right` from `k` to `n-1`:
   - `windowSum += arr[right]`
   - `windowSum -= arr[right - k]`
   - `best = max(best, windowSum)`

### Visual — one slide step

```text
Before:  ... [ 1 | 5 | 1 ] ...   sum = 7
After:   ... [ 5 | 1 | 3 ] ...   sum = 7 - 1 + 3 = 9
              ^left   ^right
```

### Equivalent style (grow then shrink)

```text
right moves → add to sum
when length == k → update best, subtract arr[left], left++
```

Both are `O(n)`.

### Java (optimal)

```java
public static int maxSumFixedWindow(int[] arr, int k) {
    int windowSum = 0;
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    int best = windowSum;
    for (int right = k; right < arr.length; right++) {
        windowSum += arr[right];
        windowSum -= arr[right - k];
        best = Math.max(best, windowSum);
    }
    return best;
}
```

---

## 6. Hardest part — remembering to subtract the leaving element

### Before (wrong)

```text
Only add arr[right] every time → window grows past k → sum is wrong
```

### After (correct)

```text
Every slide:  newSum = oldSum + enter - leave
leave index = right - k   (when right is the new right end)
```

**Rule:** Fixed window = **always** remove what fell off the left.

---

## 7. Detailed dry run — enter / leave table

`arr = [2, 1, 5, 1, 3, 2]`, k = 3

```text
Initial window [2,1,5]: sum = 8, best = 8

right=3: +arr[3]=1  → 9,  -arr[0]=2  → 7   (window [1,5,1])
right=4: +5? wait: +arr[4]=3 → 10, -arr[1]=1 → 9  (window [5,1,3]) best=9
right=5: +arr[5]=2 → 11, -arr[2]=5 → 6  (window [1,3,2])
```

| step | enter | leave | windowSum | best |
|---|---:|---:|---:|---:|
| init | — | — | 8 | 8 |
| 3 | 1 | 2 | 7 | 8 |
| 4 | 3 | 1 | 9 | **9** |
| 5 | 2 | 5 | 6 | 9 |

Answer: **9**.

---

## 8. Why the algorithm is correct

- Every contiguous block of length `k` appears exactly once as the window slides from start to end.
- `windowSum` always equals the sum of the current `k` elements because we only subtract the element that left and add the element that entered.
- `best` stores the maximum over all those windows.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| Brute (nested loops) | `O(n·k)` | `O(1)` |
| Fixed window | `O(n)` | `O(1)` |

Each index is added once and removed at most once → linear time.

---

## 10. Edge cases

| Case | Behavior |
|---|---|
| `k == arr.length` | Only one window — return total sum |
| `k == 1` | Max single element |
| All negative | Still valid — max among length-k sums |
| `k > arr.length` | Return 0 or handle per problem spec |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| Forgetting `arr[right - k]` | Sum keeps growing — wrong answer |
| Starting loop at `right = 0` without building first window | Off-by-one |
| Using variable-window logic | Window size drifts |
| `right - left + 1` when k is fixed | OK for grow/shrink style; know both forms |

---

## 12. Interview — say this aloud

> “The window size is fixed at k. I compute the sum of the first k elements. Then I slide: add the new right element, subtract the element that left, and track the maximum sum. Each slide is O(1), so total time is O(n).”

---

## 13. Follow-ups

**Q: What if we want the start index of the best window?**  
A: When updating `best`, also store `right - k` (or `left` in grow/shrink style).

**Q: What if k is not given but we want max sum of any length?**  
A: Different problem — Kadane’s algorithm, not fixed window.

**Q: Negative numbers in the array?**  
A: Fixed sum window still works; the max sum can be negative.

---

## 14. Complete runnable Java

Full source: `../MaximumSumOfKElements.java`.

```java
package DSA.SlidingWindow;

import java.util.Arrays;

public class MaximumSumOfKElements {

    public static int maxSumBruteForce(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) return 0;
        int best = Integer.MIN_VALUE;
        for (int start = 0; start <= arr.length - k; start++) {
            int sum = 0;
            for (int i = start; i < start + k; i++) sum += arr[i];
            best = Math.max(best, sum);
        }
        return best;
    }

    public static int maxSumFixedWindow(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) return 0;
        int windowSum = 0;
        for (int i = 0; i < k; i++) windowSum += arr[i];
        int best = windowSum;
        for (int right = k; right < arr.length; right++) {
            windowSum += arr[right];
            windowSum -= arr[right - k];
            best = Math.max(best, windowSum);
        }
        return best;
    }

    public static void main(String[] args) {
        int[] nums = { 2, 1, 5, 1, 3, 2 };
        System.out.println("Fixed Window: " + maxSumFixedWindow(nums, 3)); // 9
    }
}
```

```text
Array: [2, 1, 5, 1, 3, 2], k = 3
Brute Force:     9
Fixed Window:    9
Grow / Shrink:   9
```

---

## 15. Practice checklist

- [ ] Implement brute force without hints.
- [ ] Implement enter/leave loop without hints.
- [ ] Trace `[2,1,5,1,3,2]` on paper with enter/leave columns.
- [ ] Explain why time is O(n).
- [ ] Handle `k = 1` mentally in 10 seconds.

---

## 16. Quick revision

1. **Fixed k** → subtract leaving, add entering.
2. **First** sum indices `0..k-1`.
3. **Loop** `right` from `k` to `n-1`.
4. **`O(n)` time**, **`O(1)` space**.

One line:

> **Build the first window, then slide with +enter −leave.**

---

## 17. Pattern used

**Fixed-size sliding window (Pattern A).**

---

## 18. Next problem

**Problem 02 — Maximum Average Subarray I**  
Same window — track max **sum**, then divide by `k` (or compare averages each step).