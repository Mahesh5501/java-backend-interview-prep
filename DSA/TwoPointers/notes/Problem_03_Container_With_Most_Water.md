# Problem 03 — Container With Most Water

> **Pattern:** Opposite-direction Two Pointers + Greedy movement  
> **Goal:** Understand why we move the **shorter** wall — not the taller one — and why that still finds the maximum area.

---

## 1. Problem statement

You are given an integer array `height` of length `n`.

Each `height[i]` is the height of a vertical **line** at position `i`.

Pick **two different lines**. They form the sides of a container. The bottom is the x-axis.

Return the **maximum amount of water** the container can hold.

### Example 1

```text
Input:  height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
Output: 49
```

Best pair: index `1` (height `8`) and index `8` (height `7`).

- **Width** = `8 - 1 = 7`
- **Water level** = `min(8, 7) = 7` (shorter side limits water)
- **Area** = `7 × 7 = 49`

### Example 2

```text
Input:  height = [1, 1]
Output: 1
```

### Constraints (LeetCode version)

- `2 <= height.length <= 10^5`
- `0 <= height[i] <= 10^4`

---

## 2. Understand the problem before coding

Picture fences at each index:

```text
height = [1, 8, 6, 2]

index:   0  1  2  3
         |  |  |  |
         1  8  6  2
```

Pick two fences. Water fills between them up to the **shorter** fence. Taller fence cannot raise the water above the shorter one — it would spill.

### The one picture you should keep in mind

```text
Pick left wall at index L, right wall at index R

        |                    |
        |________water________|
        L                    R
        |<----- width ----->|

area = width × min(height[L], height[R])
```

Read it as:

> “Width shrinks when pointers move inward. The shorter wall is the bottleneck. Can I ditch one wall safely without missing a better answer?”

### Jargon — explained once

| Term | Meaning here |
|---|---|
| **Width** | `right - left` (distance between indices). |
| **Water height** | `min(height[left], height[right])` — limited by shorter wall. |
| **Area** | Width × water height. |
| **Greedy** | Each step make the locally justified move that lets you discard something safely. |

---

## 3. How to start thinking about the solution

Before writing code, ask yourself:

1. What am I choosing? Two different indices.
2. What formula governs the answer? Width × minimum of the two heights.
3. Simplest guaranteed way? Check every pair — `O(n²)`.
4. Can I avoid all pairs? Maybe — start widest, then move one pointer if we can prove a wall is useless.
5. Which wall is useless when heights differ? The **shorter** one cannot beat the current area if we only shrink width while keeping it.

---

## 4. Approach 1 — Brute force (every pair)

### Idea

For every `i < j`, compute area and track the maximum.

### Why does it work?

The answer is some pair `(i, j)`. Brute force tries all of them.

### Java method

```java
public static int maxAreaBruteForce(int[] height) {

    int maxArea = 0;

    for (int i = 0; i < height.length; i++) {
        for (int j = i + 1; j < height.length; j++) {
            int width = j - i;
            int waterHeight = Math.min(height[i], height[j]);
            int area = width * waterHeight;
            maxArea = Math.max(maxArea, area);
        }
    }

    return maxArea;
}
```

### Dry run

```text
height = [1, 8, 6, 2]
```

| i | j | Heights | Width | Water H | Area |
|---:|---:|---|---:|---:|---:|
| 0 | 1 | 1,8 | 1 | 1 | 1 |
| 0 | 2 | 1,6 | 2 | 1 | 2 |
| 0 | 3 | 1,2 | 3 | 1 | 3 |
| 1 | 2 | 8,6 | 1 | 6 | **6** |
| 1 | 3 | 8,2 | 2 | 2 | 4 |
| 2 | 3 | 6,2 | 1 | 2 | 2 |

Max = `6`.

### Complexity

- **Time:** `O(n²)`.
- **Auxiliary space:** `O(1)`.

### Limitation

Too slow for `n` up to `10^5`.

---

## 5. Is HashMap a useful middle approach?

No — not for standard interviews.

Two Sum style HashMaps look for a **fixed target complement**.

Here you **maximize** `width × min(h[left], h[right])`. There is no single complement to hunt.

Progression to mention aloud:

1. Brute force `O(n²)`.
2. Greedy two pointers `O(n)`.

---

## 6. Approach 2 — Optimal two pointers (greedy)

### Step 1 — Start at the widest container

```java
int left = 0;
int right = height.length - 1;
```

Ends give the **largest possible width**.

### Step 2 — Score the current container

```java
int width = right - left;
int waterHeight = Math.min(height[left], height[right]);
int currentArea = width * waterHeight;
maxArea = Math.max(maxArea, currentArea);
```

### Step 3 — Move the shorter wall

```java
if (height[left] < height[right]) {
    left++;
} else {
    right--;  // also when equal — either side OK
}
```

### Visual

```text
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
          L                           R
          1                           7

water limited by 1 → area = 8×1 = 8
1 is shorter → move L
```

---

## 7. The hardest part explained carefully — why move the shorter wall?

### Setup

```text
left height  = 3
right height = 8
width        = 7
area         = 7 × min(3,8) = 21
```

Shorter wall is **left (3)**. It caps the water level.

### Case A — Keep shorter left, move right inward (wrong for improvement)

```text
Before:
  3 -------- 8     width 7, cap 3 → area 21

After right-- (keep left at 3):
  3 ---- ?         width smaller, cap still 3 → area <= 21 always
```

Any new right is closer, so width drops. Left is still 3, so water height still at most 3.

**No future pair keeping this left wall can beat 21.** Safe to abandon left → `left++`.

#### Before / after summary

| Choice | Width | Max water height with fixed short wall | Can beat old area? |
|---|---:|---:|---|
| Keep short left, move R in | Smaller | Still ≤ 3 | **No** |
| Move short left | Smaller maybe | Could rise above 3 | **Maybe yes** |

### Case B — Move the taller wall instead (also wrong)

Keeping the tall `8` while sliding `right` does not fix the `3` cap. You still never exceed `3 × smaller width`.

Moving the short side is the only move that might **raise the limiting height**.

### Case C — Equal heights

```text
left = 5, right = 5
```

If you keep left fixed and move right in, width shrinks and cap stays 5. Same proof. Code moves `right--` when `height[left] >= height[right]`; moving `left++` when equal is also accepted.

### Case D — Why not move both pointers?

You might discard a tall wall that could pair with a future left index for a record area. Move **one** pointer per iteration.

---

## 8. Detailed dry run — Optimal approach

Input:

```text
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
```

| Step | L | R | h[L], h[R] | Width | Area | Move why |
|---:|---:|---:|---|---:|---:|---|
| 1 | 0 | 8 | 1,7 | 8 | 8 | 1 shorter → `L++` |
| 2 | 1 | 8 | 8,7 | 7 | **49** | 7 shorter → `R--` |
| 3 | 1 | 7 | 8,3 | 6 | 18 | 3 shorter → `R--` |
| 4 | 1 | 6 | 8,8 | 5 | 40 | equal → `R--` |
| 5 | 1 | 5 | 8,4 | 4 | 16 | 4 shorter → `R--` |
| 6 | 1 | 4 | 8,5 | 3 | 15 | 5 shorter → `R--` |
| 7 | 1 | 3 | 8,2 | 2 | 4 | 2 shorter → `R--` |
| 8 | 1 | 2 | 8,6 | 1 | 6 | 6 shorter → `R--` |

`maxArea = 49`.

### Role diagram — step 2 (best area found)

```text
index:   0  1  2  3  4  5  6  7  8
         1  8  6  2  5  4  8  3  7
            L                       R
            8                       7
            |<------- width 7 ------>|
water to height 7 → area 49
```

---

## 9. Why the greedy scan never misses the maximum

When `height[left] < height[right]`:

Every container that **keeps this left index** and uses a right index closer than current `right` has:

- smaller width, and
- water height still ≤ `height[left]`.

So none beat the **current** area for that fixed left. We already recorded the current area. Discarding `left` loses nothing globally.

Symmetric when the right wall is shorter.

We examine `n - 1` containers (one per pointer step) and never eliminate a pair that could still set a new global maximum.

---

## 10. Complexity comparison

| Approach | Time | Auxiliary space | Notes |
|---|---:|---:|---|
| Brute force | `O(n²)` | `O(1)` | Baseline |
| Two pointers | `O(n)` | `O(1)` | Each step moves one pointer |

At most `n - 1` iterations while `left < right`.

---

## 11. Edge cases to practise

| Input | Expected | What it checks |
|---|---:|---|
| `[1, 1]` | 1 | Minimum size |
| `[1, 2]` | 1 | Different heights |
| `[2, 2, 2]` | 4 | All equal |
| `[1, 2, 3, 4, 5]` | 6 | Rising |
| `[5, 4, 3, 2, 1]` | 6 | Falling |
| `[0, 0, 0]` | 0 | Zero walls |
| `[1, 8, 6, 2, 5, 4, 8, 3, 7]` | 49 | Classic |

---

## 12. Common mistakes

1. **Using max height instead of min** for water level.
2. **Width off-by-one:** use `right - left`, not `+ 1`.
3. **Moving the taller wall** when heights differ.
4. **Moving both pointers** without proof.
5. **Updating max after moving** — measure **before** you discard a wall.
6. **Assuming array must be sorted** — it does not; positions are fixed.

---

## 13. How to recognise this pattern in an interview

Clues:

- Choose two indices.
- Result uses **distance × min of two values**.
- Wider start may help; one side limits the outcome.
- You can prove one index is dead after scoring current pair.

Ask:

> If I keep the shorter fence and slide the other inward, can area ever increase?

If no → move the shorter side.

### vs Two Sum II

| Problem | Pointer rule |
|---|---|
| Two Sum II | Sum vs target |
| Container | Move shorter wall |

Same skeleton (`left` / `right`), different reasoning.

---

## 14. Interview explanation — Say this aloud

“I’d check all pairs in `O(n²)`. To optimise, I put `left` and `right` at the ends for maximum width, compute `width × min(height[left], height[right])`, and track the max. The shorter wall limits the water. Any container that keeps that shorter wall while moving the other end inward only loses width without raising the water height, so that wall cannot improve the answer — I move the shorter pointer. I repeat until the pointers meet. That’s `O(n)` time and `O(1)` space.”

---

## 15. Interview follow-up questions

### Q1. Why `min` for height?

Water spills over the shorter side.

### Q2. Why start at the ends?

Maximum width first; then trade width for possibly taller limiting walls.

### Q3. Equal heights?

Either pointer may move; keeping one while shrinking width cannot beat current cap.

### Q4. HashMap?

Not a natural fit — no complement target.

### Q5. `int` enough?

Under LeetCode bounds, yes. Use `long` if you scale heights/indices up in variants.

### Q6. Calculate area before or after move?

**Before** — current pair is a valid candidate.

### Q7. Relation to Trapping Rain Water?

Different problem (Problem 08) — fills between many bars; do not mix proofs.

---

## 16. Complete runnable Java class — All applicable approaches

Matches **`../ContainerWithMostWater.java`**.

### Complete Java code

```java


import java.util.Arrays;

public class ContainerWithMostWater {

    // =====================================================
    // APPROACH 1: BRUTE FORCE
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int maxAreaBruteForce(int[] height) {

        int maxArea = 0;

        for (int i = 0; i < height.length; i++) {

            for (int j = i + 1; j < height.length; j++) {
                int width = j - i;
                int waterHeight = Math.min(height[i], height[j]);
                int area = width * waterHeight;
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }


    // =====================================================
    // APPROACH 2: TWO POINTERS (OPTIMAL)
    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    // =====================================================

    public static int maxAreaTwoPointers(int[] height) {

        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int width = right - left;
            int waterHeight = Math.min(height[left], height[right]);
            int currentArea = width * waterHeight;
            maxArea = Math.max(maxArea, currentArea);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }


    public static void main(String[] args) {

        int[] height = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        System.out.println("Heights: " + Arrays.toString(height));

        System.out.println(
                "Brute Force Maximum Area: " + maxAreaBruteForce(height)
        );
        System.out.println(
                "Two Pointers Maximum Area: " + maxAreaTwoPointers(height)
        );
    }
}
```

### Expected output

```text
Heights: [1, 8, 6, 2, 5, 4, 8, 3, 7]
Brute Force Maximum Area: 49
Two Pointers Maximum Area: 49
```

---

## 17. Independent IDE practice

Before Problem 04:

- [ ] Implement brute force and two pointers.
- [ ] Draw Case A before/after for a short-left example.
- [ ] Trace `[4, 3, 2, 1, 4]` by hand (good greedy drill).
- [ ] Explain why moving both pointers is unsafe.
- [ ] Match brute and optimal on the classic example.

### Small challenge

Trace without code:

```text
height = [4, 3, 2, 1, 4]
```

For each step: `L`, `R`, heights, width, area, who moves, running max.

---

## 18. Quick revision

1. **Area:** `(right - left) × min(h[left], h[right])`.
2. **Move:** Shorter wall (or either if equal).
3. **Complexity:** `O(n)` time, `O(1)` space.

Reusable sentence:

> **Container = widest start + score pair + discard shorter wall.**

---

## 19. Pattern used in this problem

**Opposite-direction Two Pointers + Greedy movement.**

Template details: **`Two_Pointers_Common_Patterns.md`**.

---

## 20. Next problem

**Problem 04 — 3Sum**  
Main concept: Sort first, fix one index, run Two Sum II inside, skip duplicates.