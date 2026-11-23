# Problem 03 — Container With Most Water

> **Pattern:** Opposite-direction Two Pointers + Greedy Pointer Movement  
> **Goal:** Understand why the shorter wall is moved, not just memorise the code.

---

## 1. Problem statement

You are given an integer array `height` of length `n`.

Each element represents a vertical line drawn at that position. The value `height[i]` is the height of the line at index `i`.

Choose two different lines that, together with the x-axis, form a container. Return the **maximum amount of water** that the container can store.

### Example 1

```text
Input:  height = [1,8,6,2,5,4,8,3,7]
Output: 49
```

The lines at indices `1` and `8` have heights `8` and `7`.

- Width = `8 - 1 = 7`
- Water height = `min(8, 7) = 7`
- Area = `7 * 7 = 49`

### Example 2

```text
Input:  height = [1,1]
Output: 1
```

### Constraints (LeetCode version)

- `2 <= height.length <= 100000`
- `0 <= height[i] <= 10000`

---

## 2. Understand the problem before coding

Imagine every number in the array is a vertical wall.

For example:

```text
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
```

We choose two walls to make a container.

Three things matter:

1. **Width:** How far apart the two walls are.
2. **Water height:** How high the water can rise.
3. **Area:** Width multiplied by water height.

The shorter wall limits the water level. Water would spill over the shorter wall if we tried to fill the container above it.

### Formula

```java
int width = right - left;
int waterHeight = Math.min(height[left], height[right]);
int area = width * waterHeight;
```

Mathematically:

\[
\text{Area} = (right-left) \times \min(height[left], height[right])
\]

**Important:** We use the shorter wall's height, not the taller wall's height.

---

## 3. How to start thinking about the solution

Before writing code, ask yourself:

1. What am I selecting? Two different indices.
2. What does a selected pair produce? A width and a water height.
3. What am I asked to find? The maximum area.
4. What is the simplest way to guarantee that I find it? Check every possible pair.
5. Can I avoid checking every pair? Maybe. The left and right positions determine the width, and the shorter wall determines the water height.

This tells us to first build a brute-force solution, then look for a way to safely eliminate pairs.

---

## 4. Approach 1 — Brute force (check every pair)

### Idea

Try every possible pair of different indices:

- Choose the first wall using `i`.
- Choose the second wall using `j`, starting from `i + 1`.
- Calculate the width.
- Calculate the shorter wall's height.
- Calculate the area.
- Keep the largest area found so far.

### Why does it work?

Every possible pair is checked exactly once. Since the answer must be one of those pairs, we cannot miss the maximum.

### Java method

```java
public static int maxAreaBruteForce(int[] height) {

    // Stores the largest area found so far.
    int maxArea = 0;

    // Choose the first wall.
    for (int i = 0; i < height.length; i++) {

        // Choose a different wall after i.
        // Starting at i + 1 avoids pairing a wall with itself
        // and avoids checking the same pair in reverse order.
        for (int j = i + 1; j < height.length; j++) {

            // The distance between the two indices is the width.
            int width = j - i;

            // The shorter wall determines the maximum water height.
            int waterHeight = Math.min(height[i], height[j]);

            // Calculate the area for this pair.
            int area = width * waterHeight;

            // Keep the largest area found so far.
            maxArea = Math.max(maxArea, area);
        }
    }

    return maxArea;
}
```

### Dry run

Input:

```text
height = [1, 8, 6, 2]
```

We check every pair:

| First index (`i`) | Second index (`j`) | Heights | Width | Water height | Area |
|---:|---:|---:|---:|---:|---:|
| 0 | 1 | 1, 8 | 1 | 1 | 1 |
| 0 | 2 | 1, 6 | 2 | 1 | 2 |
| 0 | 3 | 1, 2 | 3 | 1 | 3 |
| 1 | 2 | 8, 6 | 1 | 6 | 6 |
| 1 | 3 | 8, 2 | 2 | 2 | 4 |
| 2 | 3 | 6, 2 | 1 | 2 | 2 |

The largest area is `6`.

### Complexity

- **Time:** `O(n²)` — in the worst case, we examine every pair.
- **Auxiliary space:** `O(1)` — we use only a few variables. The returned value is a primitive `int`.

### Limitation

The brute-force approach is correct, but it becomes too slow when the array contains many elements. With `n` elements, the number of pairs is `n * (n - 1) / 2`.

We need a way to avoid examining every pair.

---

## 5. Is there a useful intermediate approach?

For this problem, the standard interview progression is:

1. Brute force: check all pairs in `O(n²)`.
2. Greedy two pointers: safely discard one boundary at each step and solve it in `O(n)`.

A HashMap is not a useful standard intermediate solution here. Unlike Two Sum, we are not looking for a pair with a particular sum. We are maximising an expression involving both the distance between indices and the shorter height.

It is better in an interview to explain the two valid approaches clearly than to invent an unnecessary middle approach.

---

## 6. Approach 2 — Optimal Two Pointers (greedy movement)

### Step 1: Start with the widest possible container

Place one pointer at the beginning and one at the end:

```java
int left = 0;
int right = height.length - 1;
```

Why start at the ends?

Because this pair has the maximum possible width. Every other pair has a smaller width.

### Step 2: Calculate the current area

At every iteration:

```java
int width = right - left;
int waterHeight = Math.min(height[left], height[right]);
int currentArea = width * waterHeight;
```

Update the best answer:

```java
maxArea = Math.max(maxArea, currentArea);
```

### Step 3: Move the shorter wall

This is the most important decision in this problem.

- If `height[left] < height[right]`, move `left` forward.
- Otherwise, move `right` backward.

### Why do we move the shorter wall?

Let's prove it with an example.

Suppose:

```text
left height  = 3
right height = 8
width        = 7
```

The current area is:

```text
7 * min(3, 8) = 21
```

The shorter wall is `3`, so it limits the water height.

#### Choice A: Keep the shorter wall and move the taller wall

Imagine moving `right` one position to the left while keeping `left` fixed.

- The width becomes smaller.
- The left wall is still height `3`.
- The water height cannot be more than `3`, regardless of the new right wall's height.

Therefore, the new area must be:

```text
new smaller width * a water height no greater than 3
```

It must be smaller than the current area `7 * 3 = 21`.

So moving the taller wall while keeping the shorter wall cannot produce a better answer.

#### Choice B: Move the shorter wall

Now move `left` forward.

- The width becomes smaller.
- But we may find a new wall taller than `3`.
- If the new wall is taller, the limiting water height may increase.
- That increase might compensate for the reduced width.

This movement has a chance to produce a larger area.

<mark>Greedy rule: Always move the pointer at the shorter wall.</mark>

### Optimal Java method

```java
public static int maxAreaTwoPointers(int[] height) {

    // Start at the first wall.
    int left = 0;

    // Start at the last wall.
    int right = height.length - 1;

    // Store the largest area found so far.
    int maxArea = 0;

    // We need two different walls, so stop when pointers meet.
    while (left < right) {

        // Calculate the width between the selected walls.
        int width = right - left;

        // The shorter wall limits the water height.
        int waterHeight = Math.min(height[left], height[right]);

        // Calculate the current container's area.
        int currentArea = width * waterHeight;

        // Save this area if it is larger than our previous answer.
        maxArea = Math.max(maxArea, currentArea);

        // Move only the shorter wall.
        if (height[left] < height[right]) {

            // Left wall is shorter.
            // Try a new left wall that may be taller.
            left++;

        } else {

            // Right wall is shorter OR both walls are equal.
            // Moving right is safe in either case.
            right--;
        }
    }

    return maxArea;
}
```

### What if both heights are equal?

For example:

```text
left height  = 5
right height = 5
```

Either pointer can be moved.

Both walls limit the water to the same height. If we keep one of these walls and move the other pointer inward, the width becomes smaller and the water height cannot exceed `5`. So that kept wall cannot produce a better area with any closer opposite wall.

The code above chooses to move `right` when the heights are equal. Moving `left` would also be correct.

### Why not move both pointers?

Suppose the left wall is shorter.

We have proved that keeping the shorter left wall while moving the right pointer inward cannot improve the area. But we have **not** proved that the taller right wall is useless.

That taller wall might form a better container with a later left wall. Moving both pointers would discard it without a reason.

Therefore, move only the shorter wall.

---

## 7. Detailed dry run — Optimal approach

Input:

```text
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
```

Initial state:

```text
left = 0
right = 8

[1, 8, 6, 2, 5, 4, 8, 3, 7]
 ^                               ^
 left                           right
```

Follow each iteration:

| Step | Left index | Right index | Heights | Width | Area | Pointer movement |
|---:|---:|---:|---:|---:|---:|---|
| 1 | 0 | 8 | 1, 7 | 8 | 8 | Move left (`1` is shorter) |
| 2 | 1 | 8 | 8, 7 | 7 | 49 | Move right (`7` is shorter) |
| 3 | 1 | 7 | 8, 3 | 6 | 18 | Move right (`3` is shorter) |
| 4 | 1 | 6 | 8, 8 | 5 | 40 | Move right (equal heights; either is safe) |
| 5 | 1 | 5 | 8, 4 | 4 | 16 | Move right (`4` is shorter) |
| 6 | 1 | 4 | 8, 5 | 3 | 15 | Move right (`5` is shorter) |
| 7 | 1 | 3 | 8, 2 | 2 | 4 | Move right (`2` is shorter) |
| 8 | 1 | 2 | 8, 6 | 1 | 6 | Move right (`6` is shorter) |

The maximum area encountered is:

```text
maxArea = 49
```

Output:

```text
49
```

Notice that we evaluated only `n - 1` pairs (eight pairs for this nine-element example), rather than checking every possible pair.

### Dry-run detail for the first three iterations

**Iteration 1**

```text
left = 0, right = 8
height[left] = 1
height[right] = 7

width = 8
waterHeight = min(1, 7) = 1
area = 8 * 1 = 8

1 is shorter, so left moves from 0 to 1.
maxArea = 8
```

**Iteration 2**

```text
left = 1, right = 8
height[left] = 8
height[right] = 7

width = 7
waterHeight = min(8, 7) = 7
area = 7 * 7 = 49

7 is shorter, so right moves from 8 to 7.
maxArea = 49
```

**Iteration 3**

```text
left = 1, right = 7
height[left] = 8
height[right] = 3

width = 6
waterHeight = min(8, 3) = 3
area = 6 * 3 = 18

3 is shorter, so right moves from 7 to 6.
maxArea remains 49.
```

---

## 8. Why does the greedy algorithm never miss a better answer?

This is the correctness reasoning to explain in an interview.

Assume the left wall is shorter than or equal to the right wall.

The left wall limits the current water height.

Consider every possible pair that keeps this same left wall and chooses a right wall closer than the current right wall:

- Its width is smaller than the current width.
- Its water height cannot be greater than the current left wall's height.
- Therefore, its area cannot be greater than the area of the current pair.

We have already calculated the current pair's area. So no future pair that keeps this left wall can improve the answer. It is safe to discard this left wall by incrementing `left`.

The same argument works symmetrically when the right wall is shorter.

At each step, we calculate the current pair and then discard a boundary that cannot produce a better answer with any remaining opposite boundary. This is why the algorithm does not need to check every pair.

---

## 9. Complexity comparison

| Approach | Time complexity | Auxiliary space | Explanation |
|---|---:|---:|---|
| Brute force | `O(n²)` | `O(1)` | Checks every pair |
| Two pointers | `O(n)` | `O(1)` | Each iteration moves one pointer inward |

**Optimal choice:** Two pointers.

Each iteration moves either `left` forward or `right` backward. Neither pointer moves backwards or moves outside the array, so there are at most `n - 1` iterations.

---

## 10. Edge cases to practise

| Input | Expected maximum area | What it checks |
|---|---:|---|
| `[1, 1]` | `1` | Minimum array size |
| `[1, 2]` | `1` | Different wall heights |
| `[2, 2, 2]` | `4` | Equal heights |
| `[1, 2, 3, 4, 5]` | `6` | Increasing heights |
| `[5, 4, 3, 2, 1]` | `6` | Decreasing heights |
| `[0, 0, 0]` | `0` | Zero-height walls |
| `[1, 8, 6, 2, 5, 4, 8, 3, 7]` | `49` | Standard example |

---

## 11. Common mistakes

1. **Using the taller wall's height:** Use `Math.min(height[left], height[right])`, because the shorter wall limits the water level.
2. **Using the wrong width:** The width is `right - left`, not `right - left + 1`.
3. **Moving the taller wall:** Move the shorter wall. Keeping the shorter wall while reducing width cannot improve the area.
4. **Moving both pointers:** This discards a wall without proving that it cannot help.
5. **Updating the maximum after moving a pointer:** Calculate and record the current pair's area before moving either pointer.
6. **Using nested loops in the final solution:** Nested loops are the brute-force approach. The optimal approach is linear.
7. **Confusing maximum height with maximum area:** Tall walls do not automatically create the largest area. Width also matters.

---

## 12. How to recognise this pattern in an interview

Look for these clues:

- You must choose two positions.
- The distance between the positions affects the result.
- The smaller of two values limits the result.
- You need to maximise or minimise an expression involving both positions.
- Starting at the two ends gives the widest possible pair.
- You can prove that one boundary can be safely discarded at each step.

Ask yourself:

> If I keep one boundary fixed and move the other inward, can I prove that keeping the shorter boundary cannot improve the answer?

If the answer is yes, a greedy two-pointer strategy may be useful.

### How this differs from Problem 2 — Two Sum II

| Problem | What decides which pointer moves? |
|---|---|
| Two Sum II | Whether the current sum is too small or too large |
| Container With Most Water | Which wall is shorter and limits the area |

Both use opposite-direction pointers, but their pointer-movement reasoning is different.

---

## 13. Interview explanation — Say this aloud

“I would first consider checking every pair, which takes `O(n²)` time. To optimise it, I place one pointer at the beginning and the other at the end, giving the maximum possible width. I calculate the area using the width multiplied by the shorter wall's height. Then I move only the pointer at the shorter wall. Keeping that shorter wall while reducing the width cannot produce a better area, so it is safe to discard it. I continue until the pointers meet. This gives `O(n)` time and `O(1)` auxiliary space.”

---

## 14. Interview follow-up questions

### Q1. Why do we use the minimum of the two heights?

Water spills over the shorter wall. The taller wall cannot increase the water level beyond the shorter wall.

### Q2. Why do we start with the two ends?

The two ends give the maximum possible width. Every other pair has a smaller width. We start with the widest pair and then try to improve the limiting height.

### Q3. Why move the shorter wall?

The shorter wall limits the current area. Keeping it while reducing width cannot produce a larger area. Moving it is the only way to potentially find a taller limiting wall.

### Q4. What if both walls have the same height?

Either pointer can be moved. Keeping either wall while moving the other inward reduces width and cannot increase the limiting height beyond the kept wall's height.

### Q5. Can we use a HashMap?

A HashMap does not naturally help. We are not searching for a pair with a particular sum. We are maximising an area that depends on both the distance and the shorter height.

### Q6. What is the time complexity?

`O(n)`. Each iteration moves either `left` forward or `right` backward. There are at most `n - 1` iterations.

### Q7. What is the auxiliary space complexity?

`O(1)`. We use only pointer variables and a few values to calculate and store the maximum area.

### Q8. Why do we calculate the area before moving a pointer?

The current pair is a valid candidate. We must consider its area before discarding either boundary.

### Q9. Does the array need to be sorted by height?

No. The array represents wall heights at fixed positions. The two-pointer proof depends on the fact that moving inward reduces the width and that the shorter wall limits the area. It does not require the heights to be sorted.

---

## 15. Complete runnable Java class — All applicable approaches

This problem has two standard approaches worth implementing:

1. Brute force — `O(n²)` time and `O(1)` auxiliary space.
2. Two pointers — `O(n)` time and `O(1)` auxiliary space.

There is no standard HashMap approach that improves this problem. A HashMap is useful for complement-search problems such as Two Sum, but this problem asks us to maximise an area based on width and the shorter wall.

Copy the entire class into your IDE and save it as **`ContainerWithMostWater.java`**.

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

        // Stores the largest area found so far.
        int maxArea = 0;

        // Choose the first wall.
        for (int i = 0; i < height.length; i++) {

            // Choose a different wall after i.
            // Starting at i + 1 avoids using the same wall twice
            // and avoids checking the same pair in reverse order.
            for (int j = i + 1; j < height.length; j++) {

                // The distance between the indices is the width.
                int width = j - i;

                // The shorter wall limits the water height.
                int waterHeight = Math.min(height[i], height[j]);

                // Calculate the area formed by this pair.
                int area = width * waterHeight;

                // Keep the largest area found so far.
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

        // Start at the first wall.
        int left = 0;

        // Start at the last wall.
        int right = height.length - 1;

        // Stores the largest area found so far.
        int maxArea = 0;

        // Continue while two different walls are available.
        while (left < right) {

            // Calculate the distance between the selected walls.
            int width = right - left;

            // The shorter wall determines the water height.
            int waterHeight = Math.min(height[left], height[right]);

            // Calculate the current container's area.
            int currentArea = width * waterHeight;

            // Update the answer if this pair is better.
            maxArea = Math.max(maxArea, currentArea);

            // Move only the shorter wall.
            if (height[left] < height[right]) {

                // Left wall is shorter, so try a new left wall.
                left++;

            } else {

                // Right wall is shorter OR both are equal.
                // Moving right is safe in either case.
                right--;
            }
        }

        return maxArea;
    }


    // =====================================================
    // MAIN METHOD
    // Run both approaches using the same input.
    // =====================================================

    public static void main(String[] args) {

        int[] height = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };

        System.out.println("Heights: " + Arrays.toString(height));

        // Run the brute-force approach.
        int bruteForceAnswer = maxAreaBruteForce(height);

        System.out.println(
                "Brute Force Maximum Area: " + bruteForceAnswer
        );

        // Run the optimal two-pointer approach.
        int twoPointerAnswer = maxAreaTwoPointers(height);

        System.out.println(
                "Two Pointers Maximum Area: " + twoPointerAnswer
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

### Approach comparison

| Method | Time | Auxiliary space | Use |
|---|---:|---:|---|
| `maxAreaBruteForce()` | `O(n²)` | `O(1)` | Understand and verify every pair |
| `maxAreaTwoPointers()` | `O(n)` | `O(1)` | Optimal solution |

**Integer note:** Under the given LeetCode constraints, the maximum area fits in a Java `int`. If adapting the solution to much larger height and index values, consider using `long` for the area and return type.

---

## 16. Independent IDE practice

Before moving to Problem 4, write the solutions yourself without looking at the code.

- [ ] Implement the brute-force approach.
- [ ] Implement the two-pointer approach.
- [ ] Explain why the shorter wall limits the water height.
- [ ] Explain why moving the taller wall cannot improve the current area.
- [ ] Explain why moving both pointers is not justified.
- [ ] Trace the standard example by hand.
- [ ] Test equal heights, zero heights, increasing and decreasing arrays.
- [ ] State time and auxiliary-space complexity.
- [ ] Run both methods and confirm that they return the same answer.

### Small challenge

Trace this input without running the code:

```text
height = [4, 3, 2, 1, 4]
```

Write down:

1. The `left` and `right` indices at every step.
2. The two heights.
3. The width.
4. The area.
5. Which pointer moves and why.
6. The final maximum area.

---

## 17. Quick revision

Remember these three things:

1. **Area formula:** `(right - left) * min(height[left], height[right])`.
2. **Pointer movement:** Move the pointer at the shorter wall.
3. **Complexity:** `O(n)` time and `O(1)` auxiliary space.

Do not memorise only the rule. Understand the proof: the shorter wall limits the current area, and keeping it while reducing the width cannot produce a better area.

---

## 18. Pattern used in this problem

**Opposite-direction Two Pointers + Greedy Pointer Movement.**

This problem-specific explanation belongs in this problem's file. The reusable explanation of opposite-direction pointers and greedy movement belongs in the separate **`Two_Pointers_Common_Patterns.md`** file.

We maintain only:
- One common file for all reusable Two Pointers patterns.
- One separate detailed file for each problem.

There is no additional pattern file for this problem.
