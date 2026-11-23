# Problem 08 — Trapping Rain Water

> **Pattern:** Left / right maximum heights — prefix arrays, then two pointers  
> **Goal:** See why water at each index depends on the **shorter wall** of left-max and right-max, and why two pointers can track that without extra arrays.

---

## 1. Problem statement

Given `n` non-negative integers representing an elevation map where the width of each bar is `1`, compute how much water it can trap after raining.

### Example

```text
Input:  height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
```

Picture (each `#` is one unit of height):

```text
        #
#       # #
# # # # # # #
0 1 0 2 1 0 1 3 2 1 2 1
```

Water fills the dips. The answer is **6** units total.

### More examples

```text
Input:  height = [4,2,0,3,2,5]
Output: 9

Input:  height = [1,2,3]
Output: 0
```

### Constraints

- `n == height.length`
- `1 <= n <= 2 * 10^4`
- `0 <= height[i] <= 10^5`

---

## 2. Mental picture — one picture to keep in mind

Think of each bar as a wall. Rain falls straight down.

At index `i`, water can only stack **up to the lower of these two numbers**:

1. The tallest wall anywhere to the **left** of `i`
2. The tallest wall anywhere to the **right** of `i`

```text
index i:
        rightMax -->
leftMax <--    |
               v
...  H  H  h  H  H  ...
          ^
          i  (current bar height = h)
```

Water at `i` = `min(leftMax, rightMax) - height[i]`  
(if that value is negative or zero, there is no trapped water at `i`)

> **Read it as:** “The bucket at `i` is capped by the shorter of the two tallest walls on its sides.”

---

## 3. How to start thinking about the solution

Ask yourself:

1. **What am I counting?** Total trapped water across all indices.
2. **Can I compute water at one index in isolation?** Yes — if I know left-max and right-max.
3. **What is the dumbest correct way?** For each `i`, scan all left and all right — works but slow.
4. **Can I precompute left-max and right-max?** Yes — two prefix scans.
5. **Can I avoid two full arrays?** Yes — two pointers with running `leftMax` / `rightMax`.

You do **not** need geometry or calculus. This is pure “wall height” logic.

---

## 4. Approach 1 — Brute force (per index)

### Idea

For every index `i` (except edges in practice — edges trap 0 water):

1. Scan left to find `leftMax`
2. Scan right to find `rightMax`
3. Add `max(0, min(leftMax, rightMax) - height[i])` to the answer

### Why this works

Water height at `i` is exactly the gap between the bar and the water line. The water line is `min(leftMax, rightMax)`.

### Java

```java
public int trapBruteForce(int[] height) {
    int water = 0;

    for (int i = 1; i < height.length - 1; i++) {
        int leftMax = 0;
        for (int left = 0; left < i; left++) {
            leftMax = Math.max(leftMax, height[left]);
        }

        int rightMax = 0;
        for (int right = i + 1; right < height.length; right++) {
            rightMax = Math.max(rightMax, height[right]);
        }

        int trapped = Math.min(leftMax, rightMax) - height[i];
        if (trapped > 0) {
            water += trapped;
        }
    }

    return water;
}
```

### Dry run — `height = [3, 0, 2, 0, 4]`

```text
index:     0   1   2   3   4
height:    3   0   2   0   4

i = 1:  leftMax=3  rightMax=4  trapped = min(3,4)-0 = 3
i = 2:  leftMax=3  rightMax=4  trapped = min(3,4)-2 = 1
i = 3:  leftMax=3  rightMax=4  trapped = min(3,4)-0 = 3

total = 3 + 1 + 3 = 7
```

### Complexity

| | Value |
|---|---|
| Time | `O(n²)` — each index rescans both sides |
| Space | `O(1)` |

### Limitation

Repeats the same max scans again and again. Too slow for `n = 20,000`.

---

## 5. Approach 2 — Optimal idea: prefix max + two pointers

We teach **two** optimal-speed methods: prefix arrays (easier to trust) and two pointers (best space).

---

### 5A. Prefix maximum arrays (`O(n)` time, `O(n)` space)

#### Step-by-step

1. Build `leftMax[i]` = max height from `0` to `i`
2. Build `rightMax[i]` = max height from `n-1` down to `i`
3. For each `i`: `water += max(0, min(leftMax[i], rightMax[i]) - height[i])`

#### Visual frame

```text
height:   [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]

leftMax:  [0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3]
rightMax: [3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 1]
```

At index 5 (`height[5]=0`): `min(2,3)-0 = 2` units trapped.

#### Why pointer / index moves here

There are no sliding pointers yet — only **forward** and **backward** sweeps to fill arrays. Think of it as two scanners:

- Left scanner grows `leftMax` as it walks right
- Right scanner grows `rightMax` as it walks left

---

### 5B. Two pointers (`O(n)` time, `O(1)` space)

#### Step-by-step

1. `left = 0`, `right = n - 1`
2. Track `leftMax` and `rightMax` (best height seen so far on each side)
3. Compare `height[left]` and `height[right]`
4. Process the **smaller** side:
   - If current height ≥ running max → update max
   - Else → add `runningMax - height` to answer
5. Move that pointer inward
6. Stop when `left >= right`

#### Visual frames

```text
Step 0:  L and R at ends
         L                       R
         v                       v
[ 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 ]

height[left]=0 < height[right]=1  → work on LEFT side
leftMax becomes 0, water += 0, L++

Step 1:  L at 1
               L                 R
[ 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 ]

height[1]=1, leftMax=1, no water, L++

... continue until pointers meet ...
```

#### Why we move the smaller-height pointer

Suppose `height[left] < height[right]`.

- We know there is **some** wall on the right at least as tall as `height[right]`
- So the **bottleneck** for index `left` is on the left side — whatever `leftMax` we have tracked
- We can count water at `left` **now** without knowing the exact right max beyond `height[right]`

The smaller side is the “weaker” wall — that is the side we can settle.

---

## 6. The hardest part — why moving the shorter pointer is safe

### Before (confusion)

```text
left at height 1, right at height 3
"I do not know the full rightMax yet — can I trust leftMax?"
```

### After (clear rule)

When `height[left] < height[right]`:

```text
right side already has a wall of height 3 (at index right)
so rightMax >= 3 > height[left]

water at left is limited by leftMax, not by an unknown tiny right wall
```

**Before / after example**

```text
height = [5, 0, 0, 0, 2]

Early step:
 L at 0 (5), R at 4 (2)  → height[R] < height[L] → process RIGHT

At R=4: rightMax=2, water += 0, R moves to 3 (0)
        trapped = rightMax - 0 = 2   (the dip near the right)

You never need to process left index 0 for water — it is a peak.
```

Another trap students hit:

```text
height = [2, 0, 2]

i=1: leftMax=2, rightMax=2, water = 2-0 = 2  ✓
```

Two pointers reach the same count by always processing the side that is currently “capped” by its own running max.

---

## 7. Detailed dry run — two pointers + roles

Input: `[0,1,0,2,1,0,1,3,2,1,2,1]` → expected **6**

```text
Roles:
  L = left pointer
  R = right pointer
  lm = leftMax so far
  rm = rightMax so far
  ans = running total
```

| L | R | height[L] | height[R] | action | lm | rm | +water | ans |
|---|---:|---:|---:|---|---:|---:|---:|---:|
| 0 | 11 | 0 | 1 | left smaller: lm=0, w+=0, L++ | 0 | 0 | 0 | 0 |
| 1 | 11 | 1 | 1 | right smaller/equal: process R→ lm still 1 side later... | | | | |

Trace shorthand on the main dip at index 5 (`height=0`, `leftMax=2`):

```text
When L reaches 5:
  leftMax = 2  (from bar at index 3)
  height[5] = 0
  water += 2 - 0 = 2
```

Full simulation is easier with code — run `../TrappingRainWater.java` and compare all three approaches.

ASCII role diagram at a trapped cell:

```text
        rm (known tall wall on right)
         /
... 2  0  1  3 ...
    ^
    lm

cell 0 between lm and rm → water up to min(lm, rm)
```

---

## 8. Why the algorithm is correct

**Lemma 1:** At any index `i`, trapped water depends only on `leftMax(i)` and `rightMax(i)` as defined.

**Lemma 2 (prefix arrays):** `leftMax[i]` and `rightMax[i]` store exactly those values, so the per-index formula is correct.

**Lemma 3 (two pointers):** When `height[left] < height[right]`, any bar to the right of `left` that could be `rightMax` is at least `height[right]`, which is already taller than `height[left]`. So the water level at `left` is `leftMax - height[left]` (if positive). Symmetric argument when processing the right side.

**Theorem:** Both optimal methods sum the same per-index trapped amounts, so they equal the brute force answer.

---

## 9. Complexity comparison

| Approach | Time | Extra space | When to use |
|---|---:|---:|---|
| Brute force per index | `O(n²)` | `O(1)` | Learning only |
| Prefix max arrays | `O(n)` | `O(n)` | Easiest optimal to code in an interview |
| Two pointers | `O(n)` | `O(1)` | Best follow-up / production style |

---

## 10. Edge cases

| Input | Output | What it checks |
|---|---|---|
| `[0,1,0,2,1,0,1,3,2,1,2,1]` | 6 | Classic LeetCode example |
| `[4,2,0,3,2,5]` | 9 | Deep valley in the middle |
| `[1,2,3]` | 0 | Strictly increasing — no dips |
| `[3,2,1]` | 0 | Strictly decreasing |
| `[5,5,5]` | 0 | Flat plateau |
| `[1]` | 0 | Single bar |
| `[2,0,2]` | 2 | Symmetric valley |
| `[0,0,0]` | 0 | All zeros |

---

## 11. Common mistakes

1. **Forgetting `min(leftMax, rightMax)`** — using only one side over-counts water.
2. **Subtracting in wrong order** — formula is `min(...) - height[i]`, not `height[i] - min(...)`.
3. **Counting water on the first/last index as non-zero** — edges usually have no enclosed pocket (prefix method still gives 0).
4. **Moving the taller pointer in two-pointer version** — breaks the safety proof; always process the shorter side (when equal, either side works if consistent).
5. **Not updating `leftMax` / `rightMax` when bar is taller** — must raise the running max before comparing water.
6. **Confusing this with Container With Most Water** — container picks two walls and multiplies width; trapping sums many cells.

---

## 12. How to recognise the pattern

**Clues:**

- Elevation / histogram / “how much volume” between bars
- Each position needs **best seen so far** from both directions
- Opposite ends moving inward after tracking maxima

**Compared to earlier problems**

| Problem | What the pointers track |
|---|---|
| Valid Palindrome | Characters at both ends |
| Two Sum II | Pair sum vs target |
| Container With Most Water | Area; move shorter wall |
| 3Sum | Fixed + pair after sort |
| **Trapping Rain Water** | Running max on both sides; move shorter height |

Reusable sentence:

> **Know left-max and right-max; water uses the minimum; two pointers update the weaker side.**

---

## 13. Interview explanation — say this aloud

“I would start with brute force: for each index, scan left and right for maximum heights and add trapped water. That is `O(n²)`. To improve, I precompute `leftMax` and `rightMax` with two linear passes, then sum `min(leftMax, rightMax) - height[i]` — `O(n)` time and `O(n)` space. For `O(1)` space, I use two pointers at both ends with running `leftMax` and `rightMax`. I always process the side with smaller current height because the other side already has a wall at least that tall, so the water at this index is determined by the running max on the weaker side. Each pointer moves at most `n` times, so time is `O(n)`.”

---

## 14. Interview follow-up Q&A

**Q1. Why is water at `i` equal to `min(leftMax, rightMax) - height[i]`?**  
The surface cannot rise above the shorter enclosing wall on either side.

**Q2. Why two pointers instead of only prefix arrays?**  
Same time, but constant extra space — good follow-up optimisation.

**Q3. Can water be negative?**  
We only add when `min(...) > height[i]`.

**Q4. Does the array need to be sorted?**  
No. Order of bars is the whole problem.

**Q5. What if heights are huge?**  
Use `long` for the total if constraints grow; LeetCode `int` is fine here.

**Q6. Relation to Container With Most Water?**  
Both use two ends, but container **maximises one rectangle**; trapping **sums many unit cells**.

**Q7. Can I use a stack?**  
Yes — monotonic stack is another `O(n)` family. Two pointers are enough for this roadmap.

**Q8. What moves when heights are equal at both ends?**  
Pick one side consistently (e.g. process left); proofs still hold.

---

## 15. Complete runnable Java class

Copy the entire class into your IDE and save it as **`TrappingRainWater.java`** (lives in `java/`).

```java


import java.util.Arrays;

public class TrappingRainWater {

  public static int trapBruteForce(int[] height) {
    int water = 0;
    for (int i = 1; i < height.length - 1; i++) {
      int leftMax = 0;
      for (int left = 0; left < i; left++) {
        leftMax = Math.max(leftMax, height[left]);
      }
      int rightMax = 0;
      for (int right = i + 1; right < height.length; right++) {
        rightMax = Math.max(rightMax, height[right]);
      }
      int trapped = Math.min(leftMax, rightMax) - height[i];
      if (trapped > 0) water += trapped;
    }
    return water;
  }

  public static int trapPrefixMax(int[] height) {
    int n = height.length;
    if (n <= 2) return 0;
    int[] leftMax = new int[n];
    leftMax[0] = height[0];
    for (int i = 1; i < n; i++) {
      leftMax[i] = Math.max(leftMax[i - 1], height[i]);
    }
    int[] rightMax = new int[n];
    rightMax[n - 1] = height[n - 1];
    for (int i = n - 2; i >= 0; i--) {
      rightMax[i] = Math.max(rightMax[i + 1], height[i]);
    }
    int water = 0;
    for (int i = 0; i < n; i++) {
      int trapped = Math.min(leftMax[i], rightMax[i]) - height[i];
      if (trapped > 0) water += trapped;
    }
    return water;
  }

  public static int trapTwoPointers(int[] height) {
    int left = 0, right = height.length - 1;
    int leftMax = 0, rightMax = 0, water = 0;
    while (left < right) {
      if (height[left] < height[right]) {
        if (height[left] >= leftMax) leftMax = height[left];
        else water += leftMax - height[left];
        left++;
      } else {
        if (height[right] >= rightMax) rightMax = height[right];
        else water += rightMax - height[right];
        right--;
      }
    }
    return water;
  }

  public static void main(String[] args) {
    int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
    System.out.println("Heights: " + Arrays.toString(height));
    System.out.println("Brute Force:   " + trapBruteForce(height));
    System.out.println("Prefix Max:    " + trapPrefixMax(height));
    System.out.println("Two Pointers:  " + trapTwoPointers(height));
  }
}
```

Expected output:

```text
Heights: [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
Brute Force:   6
Prefix Max:    6
Two Pointers:  6
```

---

## 16. Independent IDE practice

- [ ] Implement brute force and trace `[3,0,2,0,4]`
- [ ] Implement prefix max and print `leftMax` / `rightMax` arrays
- [ ] Implement two pointers and log each move
- [ ] Explain why you process the shorter side
- [ ] Hand-trace `[2,0,2]` for all three methods
- [ ] State time and space for each approach

### Small challenge

Without code, compute trapped water for:

```text
height = [4,2,0,3,2,5]
```

Hints: draw the histogram; mark three main dips; sum unit squares.

---

## 17. Quick revision

1. Per index: `water = min(leftMax, rightMax) - height[i]` (if positive).
2. Prefix method: two arrays, one forward pass, one backward pass.
3. Two pointers: running maxes, move from the shorter end.
4. Time `O(n)` for optimal methods; space `O(n)` or `O(1)`.
5. Not the same problem as Container With Most Water.

---

## 18. Pattern used in this problem + next problem

**Opposite-direction two pointers with running left / right maximums** (after understanding the prefix-max version).

Reusable line:

> **Trapping Rain Water = min of two walls minus bar height; pointers chase the weaker side.**

**Next:** **Problem 09 — Merge Sorted Array** (two pointers from the **end** for in-place merge).