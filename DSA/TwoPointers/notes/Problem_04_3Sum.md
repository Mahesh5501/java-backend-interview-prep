# Problem 04 — 3Sum

> **Pattern:** Sorting + Fixed Pointer + Opposite-direction Two Pointers  
> **Goal:** Understand why we sort, why we fix one index, and why we skip duplicates — not just memorise the code.

---

## 1. Problem statement

Given an integer array `nums`, return all the **unique triplets** `[nums[i], nums[j], nums[k]]` such that:

- `i`, `j`, and `k` are different indices
- `nums[i] + nums[j] + nums[k] == 0`

The solution set must not contain duplicate triplets. The order of triplets and the order of values inside a triplet do not matter for correctness, as long as every unique triplet is present.

### Example 1

```text
Input:  nums = [-1, 0, 1, 2, -1, -4]
Output: [[-1, -1, 2], [-1, 0, 1]]
```

Explanation:

- `-1 + -1 + 2 = 0`
- `-1 + 0 + 1 = 0`

The two `-1` values come from different indices. The triplet `[-1, 0, 1]` is unique even though `-1` appears more than once in the input.

### Example 2

```text
Input:  nums = [0, 1, 1]
Output: []
```

### Example 3

```text
Input:  nums = [0, 0, 0]
Output: [[0, 0, 0]]
```

### Constraints (LeetCode version)

- `3 <= nums.length <= 3000`
- `-100000 <= nums[i] <= 100000`

---

## 2. Understand the problem before coding

We are looking for three numbers that add up to zero.

That is the same as saying:

```text
nums[i] + nums[j] + nums[k] = 0
```

or equivalently:

```text
nums[j] + nums[k] = -nums[i]
```

So if we fix one number, the remaining problem becomes a Two Sum search for its negation.

### The one picture you should keep in mind

Imagine the sorted array as a line of numbers:

```text
sorted:  [ -4,  -1,  -1,   0,   1,   2 ]
           ^
           i is fixed here

Then left and right hunt for a pair that cancels nums[i]:

sorted:  [ -4,  -1,  -1,   0,   1,   2 ]
           i    L ----------------> R
```

Read it as:

> “I already chose the first number. Now find two later numbers that make the total zero.”

That is the whole algorithm at a high level.

### The most important distinction: index uniqueness vs value uniqueness

This is where most people get confused.

#### Index-unique means

Different positions in the array.

Example:

```text
nums = [-1, -1, 0, 1]
```

- indices `(0, 2, 3)` use values `-1, 0, 1`
- indices `(1, 2, 3)` use values `-1, 0, 1`

These are different index triples.

#### Value-unique means

Different combinations of numbers, ignoring which index they came from.

Both index triples above produce the **same** value-triplet:

```text
[-1, 0, 1]
```

The problem asks for **value-unique** triplets.

So the answer should contain `[-1, 0, 1]` only once.

<mark>Rule: Different indices can still create the same answer. We must remove those duplicates.</mark>

### What makes this harder than Two Sum II?

1. We need **all** valid triplets, not just one.
2. Triplets must be **unique as values**, not as indices.
3. The array is **not sorted**.
4. Duplicate values are common and must be handled carefully.

### Formula after fixing one index

```java
int needed = -nums[i];
// Find two different indices left and right such that:
// nums[left] + nums[right] == needed
```

---

## 3. How to start thinking about the solution

Before writing code, ask yourself:

1. What am I selecting? Three different indices.
2. What does a selected triple produce? A sum that must equal `0`.
3. What am I asked to return? Every unique value-triplet that works.
4. What is the simplest way to guarantee correctness? Check every possible triple.
5. Can I reduce the search? Yes — fix one value, then search for a complementary pair.
6. What makes duplicate answers appear? Repeated values and different index orders of the same numbers.

This tells us to first build a brute-force solution, then improve it by sorting and using Two Sum II style pointers.

---

## 4. Why sorting is safe and useful

Sorting changes the positions of numbers. Is that allowed?

Yes, because:

1. The answer is based on **values**, not original indices.
2. We only need three numbers that sum to zero.
3. We never need to return the original positions.

So rearranging the array does not remove any valid value-triplet.

### What sorting gives us

After sorting:

```text
before: [-1, 0, 1, 2, -1, -4]
after:  [-4, -1, -1, 0, 1, 2]
```

Two huge benefits appear:

1. **Two-pointer movement becomes valid**  
   Smaller values are on the left. Larger values are on the right.  
   If a pair sum is too small, move left right.  
   If a pair sum is too large, move right left.

2. **Duplicates sit next to each other**  
   The two `-1` values are adjacent. That makes skipping duplicates simple.

Without sorting, both of those ideas become much harder.

---

## 5. Approach 1 — Brute force (check every triple)

### Idea

Try every possible combination of three different indices:

- Choose `i`
- Choose `j` after `i`
- Choose `k` after `j`
- If the three values sum to `0`, record the triplet
- Deduplicate the results

### Why does it work?

Every valid index triple is eventually checked. If a value-triplet exists, some index combination that produces it will be found.

### The hard part: uniqueness

Brute force easily finds duplicate answers. For example:

```text
nums = [-1, -1, 0, 1]
```

Both of these index triples produce `[-1, 0, 1]`:

- indices `(0, 2, 3)`
- indices `(1, 2, 3)`

One practical way to store unique triplets is:

1. Put the three values into a temporary list.
2. Sort that temporary list.
3. Insert it into a `Set`.

Why sort each triplet before inserting?

Because:

```text
[-1, 0, 1]
[0, -1, 1]
[1, -1, 0]
```

are the same answer. Sorting them all into `[-1, 0, 1]` lets the set treat them as one item.

### Java method

`threeSumBruteForce` in `ThreeSum.java`

```java
public static List<List<Integer>> threeSumBruteForce(int[] nums) {

    // A set stores unique sorted triplets.
    Set<List<Integer>> uniqueTriplets = new HashSet<>();

    // Choose the first value.
    for (int i = 0; i < nums.length; i++) {

        // Choose the second value after i.
        for (int j = i + 1; j < nums.length; j++) {

            // Choose the third value after j.
            for (int k = j + 1; k < nums.length; k++) {

                // Check whether these three values sum to zero.
                if (nums[i] + nums[j] + nums[k] == 0) {

                    // Build one candidate triplet.
                    List<Integer> triplet = Arrays.asList(
                            nums[i], nums[j], nums[k]
                    );

                    // Sort so that [-1, 0, 1] and [0, -1, 1]
                    // become the same list for the set.
                    Collections.sort(triplet);

                    // The set discards duplicates automatically.
                    uniqueTriplets.add(triplet);
                }
            }
        }
    }

    // Convert the set into a list for the required return type.
    return new ArrayList<>(uniqueTriplets);
}
```

### Dry run

Input:

```text
nums = [-1, 0, 1, 2, -1, -4]
```

Some of the valid index triples:

| i | j | k | Values | Sum | Sorted triplet |
|---:|---:|---:|---|---:|---|
| 0 | 1 | 2 | -1, 0, 1 | 0 | [-1, 0, 1] |
| 0 | 3 | 4 | -1, 2, -1 | 0 | [-1, -1, 2] |
| 1 | 2 | 4 | 0, 1, -1 | 0 | [-1, 0, 1] |

Notice the last row:

- indices are different from the first row
- values are the same after sorting

After inserting into a set, the unique results are:

```text
[[-1, -1, 2], [-1, 0, 1]]
```

### Complexity

- **Time:** `O(n³)` for examining triples, plus sorting cost for each found triplet.
- **Auxiliary space:** `O(t)` for storing up to `t` unique triplets in the set. Ignoring the output, the loops themselves use `O(1)` extra variables.

### Limitation

With `n` up to `3000`, an `O(n³)` solution is too slow for interviews and for LeetCode. We need a better way.

---

## 6. Approach 2 — Sort + fixed value + HashSet (intermediate idea)

### Idea

1. Sort the array.
2. Fix one index `i`.
3. For the remaining subarray after `i`, solve Two Sum for target `-nums[i]` using a HashSet.
4. Skip duplicate fixed values so we do not restart with the same first number.

### Why this approach exists

It shows the reduction clearly:

> 3Sum = fix one number + Two Sum on the rest

You already know Two Sum with a HashSet from earlier study. This is that idea reused inside an outer loop.

### Mental model

```text
sorted: [-4, -1, -1, 0, 1, 2]
          i

needed = 4

Scan the rest with a set:
seen = {}
visit -1 → need 5? no → seen={-1}
visit -1 → need 5? no → seen={-1}
visit  0 → need 4? no → seen={-1,0}
visit  1 → need 3? no → seen={-1,0,1}
visit  2 → need 2? no
```

No pair found for `-4`. Then move `i` to the next useful value and repeat.

### Java method

`threeSumHashSet` in `ThreeSum.java`

```java
public static List<List<Integer>> threeSumHashSet(int[] nums) {

    // Sorting groups duplicates and makes skip logic easy.
    Arrays.sort(nums);

    // A set of sorted triplets avoids duplicate answers cleanly.
    Set<List<Integer>> uniqueTriplets = new HashSet<>();

    // Fix the first number of the triplet.
    for (int i = 0; i < nums.length; i++) {

        // Skip the same fixed value we already processed.
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }

        // We need two numbers that sum to -nums[i].
        int needed = -nums[i];

        // Store values seen in the remaining subarray.
        Set<Integer> seen = new HashSet<>();

        // Scan the remaining numbers after i.
        for (int j = i + 1; j < nums.length; j++) {

            int complement = needed - nums[j];

            // If the complement was already seen, we found a pair.
            if (seen.contains(complement)) {
                List<Integer> triplet = Arrays.asList(
                        nums[i], complement, nums[j]
                );
                Collections.sort(triplet);
                uniqueTriplets.add(triplet);
            }

            // Remember the current value for later complements.
            seen.add(nums[j]);
        }
    }

    return new ArrayList<>(uniqueTriplets);
}
```

### Why this is useful

It proves the reduction works:

- Outer loop chooses the first number.
- Inner search finds a complementary pair.

### Complexity

- **Time:** `O(n²)` after `O(n log n)` sorting.
- **Auxiliary space:** `O(n)` for the HashSet in the worst case, plus space for the output.

### Limitation

This works, but the classic interview solution avoids the HashSet and uses two pointers instead. That keeps the auxiliary space lower and reuses the Two Sum II pattern you already know.

Treat HashSet as a bridge idea. Make two pointers your main solution.

---

## 7. Approach 3 — Optimal: Sort + fixed pointer + two pointers

### Core idea

1. Sort the array.
2. Fix index `i` as the first number.
3. Place `left = i + 1` and `right = n - 1`.
4. Search for a pair whose sum equals `-nums[i]`.
5. Move pointers using the same logic as Two Sum II.
6. Skip duplicates at `i`, `left`, and `right`.

This is exactly:

> Two Sum II, repeated for every valid fixed first value.

### One-step visual frame

Take:

```text
sorted = [-4, -1, -1, 0, 1, 2]
```

Suppose `i` is at the first `-1`:

```text
values:  -4   -1   -1    0    1    2
roles:        i    L -------------- R
needed:            (-1) + L + R = 0
so L + R must equal 1
```

Now the inner problem is identical to Two Sum II on the subarray `[-1, 0, 1, 2]` with target `1`.

### Step 1: Sort

```java
Arrays.sort(nums);
```

Why?

- Sorted order lets us decide pointer movement from the sum.
- Equal values sit next to each other, so duplicates are easy to skip.

### Step 2: Fix the first number

```java
for (int i = 0; i < nums.length; i++) {
    // ...
}
```

Optional early break:

```java
if (nums[i] > 0) {
    break;
}
```

Why? After sorting, if the first number is already positive, every later number is also positive, so their sum cannot be zero.

Example:

```text
sorted = [1, 2, 3]
i at 1 → remaining values are also positive → impossible
```

### Step 3: Skip duplicate fixed values

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

If we already used `-1` as the first number, we must not start another search with the same first number. That would recreate the same triplets.

### Step 4: Run Two Sum II on the remaining range

```java
int left = i + 1;
int right = nums.length - 1;
int needed = -nums[i];

while (left < right) {
    int sum = nums[left] + nums[right];

    if (sum == needed) {
        // Found a triplet.
    } else if (sum < needed) {
        left++;
    } else {
        right--;
    }
}
```

### Why does the pointer movement work?

This is the same proof as Two Sum II.

Suppose the current pair sum is too small:

```text
needed = 1
left value = -1
right value = 0
sum = -1
```

Because the array is sorted:

- every value left of `right` is `<= 0`
- keeping `left` fixed and moving `right` left can only make the sum smaller or equal

So the only useful move is `left++`, hoping for a larger left value.

If the sum is too large, the only useful move is `right--`.

### Step 5: Skip duplicates after a successful find

When a valid pair is found:

```java
result.add(Arrays.asList(nums[i], nums[left], nums[right]));
left++;
right--;

while (left < right && nums[left] == nums[left - 1]) {
    left++;
}

while (left < right && nums[right] == nums[right + 1]) {
    right--;
}
```

Why move both after a match?

Because the current pair is already used. To find another pair for the same fixed `i`, both ends must change.

---

## 8. The hardest part explained carefully — duplicate skipping

This section is more important than memorising the loops.

### Case A — Duplicate fixed `i`

Sorted array:

```text
[-1, -1, 0, 1]
  0   1  2  3
```

#### Without skipping duplicate `i`

1. `i = 0`, value `-1`  
   Find pair summing to `1` → find `(0, 1)` wait no: remaining is `[-1, 0, 1]`, pair `(0, 1)` values give `[-1, 0, 1]`.

2. `i = 1`, value `-1` again  
   Remaining is `[0, 1]`, pair gives again `[-1, 0, 1]`.

Result becomes:

```text
[[-1, 0, 1], [-1, 0, 1]]   ← wrong, duplicate answer
```

#### With skipping duplicate `i`

1. Process `i = 0` with value `-1` → add `[-1, 0, 1]`
2. Skip `i = 1` because `nums[1] == nums[0]`

Result becomes:

```text
[[-1, 0, 1]]   ← correct
```

<mark>Skip duplicate `i` so the same first number is not restarted.</mark>

### Case B — Duplicate `left` / `right` after a match

Sorted array:

```text
[-2, 0, 0, 2, 2]
```

Fix `i = 0`, value `-2`.  
Needed pair sum = `2`.

```text
values: -2   0   0   2   2
roles:   i   L ----------- R
```

#### Without skipping duplicate left/right

Possible valid pairs for needed `2`:

- `(0, 2)`
- `(0, 2)` again from the second `0` and second `2`

If we do not skip equals after a match, we can report:

```text
[-2, 0, 2]
[-2, 0, 2]   ← duplicate
```

#### With skipping duplicate left/right

1. Find first pair `(0, 2)` → add `[-2, 0, 2]`
2. Move both pointers
3. Skip the next `0` because it equals the previous left value
4. Skip the next `2` because it equals the previous right value
5. Stop

Result becomes:

```text
[[-2, 0, 2]]   ← correct
```

<mark>After a successful match, move both pointers, then skip equal neighbours.</mark>

### Case C — Why `[-1, -1, 2]` is still allowed

This looks contradictory at first, but it is not.

```text
sorted = [-1, -1, 2]
```

Here the two `-1` values are different positions, and both are needed in the same triplet.

That is valid because:

- the triplet values are `-1, -1, 2`
- those three numbers really do sum to zero
- there is only one unique value-triplet of that form

Duplicate skipping does **not** mean “never use a repeated number”.  
It means “do not report the same finished triplet more than once”.

---

## 9. Java method (optimal)

`threeSumTwoPointers` in `ThreeSum.java`

```java
public static List<List<Integer>> threeSumTwoPointers(int[] nums) {

    // Sorting enables two-pointer search and duplicate skipping.
    Arrays.sort(nums);

    List<List<Integer>> result = new ArrayList<>();

    // Fix the first number of every candidate triplet.
    for (int i = 0; i < nums.length; i++) {

        // After sorting, a positive first number cannot make sum 0.
        if (nums[i] > 0) {
            break;
        }

        // Skip duplicate first numbers.
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }

        // Search for two numbers that sum to -nums[i].
        int left = i + 1;
        int right = nums.length - 1;
        int needed = -nums[i];

        while (left < right) {

            int sum = nums[left] + nums[right];

            if (sum == needed) {

                // Record one unique triplet.
                result.add(Arrays.asList(
                        nums[i], nums[left], nums[right]
                ));

                // Move both pointers inward after a successful match.
                left++;
                right--;

                // Skip duplicate left values.
                while (left < right && nums[left] == nums[left - 1]) {
                    left++;
                }

                // Skip duplicate right values.
                while (left < right && nums[right] == nums[right + 1]) {
                    right--;
                }

            } else if (sum < needed) {

                // Current pair is too small, so increase it.
                left++;

            } else {

                // Current pair is too large, so decrease it.
                right--;
            }
        }
    }

    return result;
}
```

---

## 10. Detailed dry run — Optimal approach

Input:

```text
nums = [-1, 0, 1, 2, -1, -4]
```

### After sorting

```text
values:  -4   -1   -1    0    1    2
index:    0    1    2    3    4    5
```

### Iteration when `i = 0`, `nums[i] = -4`

```text
needed = 4

values:  -4   -1   -1    0    1    2
roles:    i    L ------------------ R
```

| left | right | nums[left] | nums[right] | sum | Action |
|---:|---:|---:|---:|---:|---|
| 1 | 5 | -1 | 2 | 1 | too small → left++ |
| 2 | 5 | -1 | 2 | 1 | too small → left++ |
| 3 | 5 | 0 | 2 | 2 | too small → left++ |
| 4 | 5 | 1 | 2 | 3 | too small → left++ |
| 5 | 5 | — | — | — | stop (`left == right`) |

No triplet with first value `-4`.

### Iteration when `i = 1`, `nums[i] = -1`

```text
needed = 1

values:  -4   -1   -1    0    1    2
roles:         i    L -------------- R
```

| left | right | nums[left] | nums[right] | sum | Action |
|---:|---:|---:|---:|---:|---|
| 2 | 5 | -1 | 2 | 1 | found `[-1, -1, 2]` → move both |
| 3 | 4 | 0 | 1 | 1 | found `[-1, 0, 1]` → move both |
| 4 | 3 | — | — | — | stop |

Result so far:

```text
[[-1, -1, 2], [-1, 0, 1]]
```

### Iteration when `i = 2`, `nums[i] = -1`

```text
values:  -4   -1   -1    0    1    2
roles:              i
```

Skip, because `nums[2] == nums[1]`.

This is Case A from the duplicate section. Without this skip, we would search again with first value `-1` and risk repeating `[-1, 0, 1]`.

### Later iterations

#### `i = 3`, value `0`

```text
needed = 0
remaining = [1, 2]
```

`1 + 2 = 3`, too large, and no valid pair remains.

#### `i = 4`, value `1`

Break early because the first number is already positive.

Final answer:

```text
[[-1, -1, 2], [-1, 0, 1]]
```

### Extra dry run — the duplicate-pair case

Input:

```text
nums = [-2, 0, 0, 2, 2]
```

Already sorted.

#### `i = 0`, value `-2`, needed `2`

```text
values: -2   0   0   2   2
roles:   i   L ----------- R
```

1. `0 + 2 = 2` → found `[-2, 0, 2]`
2. Move to next candidates
3. Skip the duplicate `0`
4. Skip the duplicate `2`
5. Stop

Only one unique triplet is added.

#### Later `i` values

- next `0` values cannot form a new unique zero-sum triplet that we have not already covered usefully for this input pattern
- final answer remains `[[-2, 0, 2]]`

---

## 11. Why sorting + skipping never misses a unique answer

Two separate guarantees matter.

### Guarantee 1: Completeness of pairs

For a fixed `i`, the remaining subarray is sorted. The two-pointer search examines every pair that could possibly sum to `-nums[i]`, using the same safe movement rules as Two Sum II. Therefore no complementary pair is missed for that fixed first value.

### Guarantee 2: Uniqueness of triplets

- Skipping duplicate `i` values prevents restarting the same first number.
- Skipping duplicate `left` / `right` values after a match prevents reporting the same pair again.
- Because we always take indices in increasing order (`i < left < right`), we never report the same three values in a different index order as a new answer.

### Why skipping duplicate `i` does not lose a needed answer

Suppose two equal values exist:

```text
[..., a, a, ...]
      i  i+1
```

Any triplet that uses the second `a` as its first number could also have used the first `a` as its first number, with the same later pair values still available to the right, or an equivalent value combination already generated.

In the interview explanation, the practical point is:

> Once we have finished all pair searches for a fixed first value `a`, restarting with another identical first value `a` can only recreate the same value-triplets.

That is why the skip is safe.

Together, these rules give every unique value-triplet exactly once.

---

## 12. Complexity comparison

| Approach | Time complexity | Auxiliary space | Explanation |
|---|---:|---:|---|
| Brute force | `O(n³)` | `O(t)` for the set of triplets | Checks every index triple |
| Sort + HashSet | `O(n²)` | `O(n)` for the set + output | Fix one value, Two Sum with a set |
| Sort + two pointers | `O(n²)` | `O(1)` extra besides output | Fix one value, Two Sum II on the rest |

**Optimal interview choice:** Sort + fixed pointer + two pointers.

Sorting costs `O(n log n)`, but the nested two-pointer search dominates and makes the total `O(n²)`.

Why `O(n²)`?

- Outer loop runs about `n` times.
- For each fixed `i`, `left` and `right` together move at most across the remaining part of the array.
- That inner scan is `O(n)`.
- Total: `O(n) * O(n) = O(n²)`.

---

## 13. Edge cases to practise

| Input | Expected output | What it checks |
|---|---|---|
| `[-1, 0, 1, 2, -1, -4]` | `[[-1,-1,2],[-1,0,1]]` | Standard example |
| `[0, 0, 0]` | `[[0,0,0]]` | All zeros |
| `[0, 0, 0, 0]` | `[[0,0,0]]` | Many zeros, still one unique triplet |
| `[0, 1, 1]` | `[]` | No valid triplet |
| `[1, 2, 3]` | `[]` | All positive |
| `[-3, -2, -1]` | `[]` | All negative |
| `[-2, 0, 0, 2, 2]` | `[[-2,0,2]]` | Duplicate pairs must be skipped |
| `[3, 0, -3]` | `[[-3,0,3]]` | Single valid triplet |
| `[-1, -1, -1, 2, 2]` | `[[-1,-1,2]]` | Repeated values inside one valid triplet |

---

## 14. Common mistakes

1. **Forgetting to sort:** Without sorting, two-pointer movement is not justified.
2. **Not skipping duplicate `i` values:** This is the most common source of duplicate triplets.
3. **Not skipping duplicate `left` / `right` after a match:** Another common source of duplicates.
4. **Thinking repeated values are always illegal:** `[-1, -1, 2]` is valid. Duplicate skipping removes duplicate answers, not repeated numbers inside one answer.
5. **Using the same index twice:** Always keep `i < left < right`.
6. **Stopping after the first triplet:** 3Sum asks for all unique triplets.
7. **Moving only one pointer after a match:** After finding a pair, move both, then skip duplicates.
8. **Confusing index uniqueness with value uniqueness:** Different indices can still produce the same value-triplet.
9. **Treating HashSet as the final interview answer:** Useful as a bridge, but two pointers is the preferred pattern here.

---

## 15. How to recognise this pattern in an interview

Look for these clues:

- You must find combinations of 3 values (or k values).
- The target is a fixed sum, often `0`.
- Duplicate answers must be removed.
- Sorting is allowed.
- After fixing one value, the remaining problem looks like Two Sum.

Ask yourself:

> If I sort and fix one number, can I solve the rest with the Two Sum II two-pointer pattern?

If yes, this is the 3Sum family of problems.

### How this differs from earlier problems

| Problem | Pattern focus |
|---|---|
| Valid Palindrome | Compare both ends |
| Two Sum II | One pair, sorted sum steering |
| Container With Most Water | Greedy move of the shorter wall |
| 3Sum | Fix one index, then reuse Two Sum II, plus duplicate skipping |

3Sum is the first problem in this roadmap that combines **sorting**, a **fixed outer pointer**, and an **inner opposite-direction search**.

### The reusable sentence

> Sort → fix one → two pointers for the rest → skip duplicates.

---

## 16. Interview explanation — Say this aloud

“I would first mention the brute-force `O(n³)` solution of checking every triple. The hard part is that we need unique value-triplets, not unique index-triplets. To optimise, I sort the array. Sorting is safe because the answer depends on values, not original positions. Then I fix one number at index `i` and search for two later numbers that sum to `-nums[i]`. Because the remaining part is sorted, I use left and right pointers exactly like Two Sum II. If the pair sum is too small I move left; if it is too large I move right. When I find a valid pair I record it, move both pointers, and skip duplicate neighbouring values. I also skip duplicate fixed values of `i`, so the same first number is not processed twice. Sorting costs `O(n log n)`, and the nested two-pointer search is `O(n²)`, so the total time is `O(n²)`. Extra space is constant besides the output list.”

---

## 17. Interview follow-up questions

### Q1. Why do we sort the array?

Sorting makes two-pointer movement correct and makes duplicate values adjacent, so skipping them is easy. It is safe because we return values, not original indices.

### Q2. Why fix one number instead of using three moving pointers from the start?

Fixing one number reduces 3Sum to many Two Sum searches. That is simpler to reason about and reuses a pattern you already trust.

### Q3. Why skip duplicates?

Because the problem asks for unique value-triplets, not unique index-triplets. Without skipping, the same numbers would be reported multiple times.

### Q4. Does skipping duplicates mean we cannot use repeated numbers?

No. `[-1, -1, 2]` is allowed. We only avoid reporting the same finished triplet more than once.

### Q5. What if the target was not zero?

The same algorithm works. Replace `needed = -nums[i]` with `needed = target - nums[i]`.

### Q6. What is the time complexity?

`O(n²)`. Sorting is `O(n log n)`, but the dominant cost is the outer loop plus the inner two-pointer scan.

### Q7. What is the auxiliary space complexity?

`O(1)` extra besides the output, if we sort in place. The result list itself needs space proportional to the number of triplets.

### Q8. Can we do better than `O(n²)`?

For the standard interview model of 3Sum, `O(n²)` is the expected optimal class.

### Q9. How does this connect to kSum?

The general pattern is: sort once, then recursively fix one value and reduce kSum to (k-1)Sum, until you reach Two Sum solved by two pointers.

### Q10. Why can we break when `nums[i] > 0`?

After sorting, every later number is also positive. Three positive numbers cannot sum to zero.

---

## 18. Complete runnable Java class — All applicable approaches

This problem has three useful approaches to practise:

1. Brute force — `O(n³)` time.
2. Sort + HashSet — `O(n²)` time, `O(n)` extra space for the set.
3. Sort + two pointers — `O(n²)` time, `O(1)` extra space besides output.

Copy the entire class into your IDE and save it as **`ThreeSum.java`** (lives in `java/`).

### Complete Java code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThreeSum {

    // =====================================================
    // APPROACH 1: BRUTE FORCE
    // Time Complexity: O(n^3)
    // Auxiliary Space: O(t) for the set of unique triplets
    // =====================================================

    public static List<List<Integer>> threeSumBruteForce(int[] nums) {

        Set<List<Integer>> uniqueTriplets = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {

                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(
                                nums[i], nums[j], nums[k]
                        );
                        Collections.sort(triplet);
                        uniqueTriplets.add(triplet);
                    }
                }
            }
        }

        return new ArrayList<>(uniqueTriplets);
    }


    // =====================================================
    // APPROACH 2: SORT + HASHSET
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(n) for the HashSet + output
    // =====================================================

    public static List<List<Integer>> threeSumHashSet(int[] nums) {

        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int needed = -nums[i];
            Set<Integer> seen = new HashSet<>();

            for (int j = i + 1; j < nums.length; j++) {
                int complement = needed - nums[j];

                if (seen.contains(complement)) {
                    List<Integer> triplet = Arrays.asList(
                            nums[i], complement, nums[j]
                    );
                    Collections.sort(triplet);
                    uniqueTriplets.add(triplet);
                }

                seen.add(nums[j]);
            }
        }

        return new ArrayList<>(uniqueTriplets);
    }


    // =====================================================
    // APPROACH 3: SORT + TWO POINTERS (OPTIMAL)
    // Time Complexity: O(n^2)
    // Auxiliary Space: O(1) extra besides the output
    // =====================================================

    public static List<List<Integer>> threeSumTwoPointers(int[] nums) {

        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] > 0) {
                break;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            int needed = -nums[i];

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == needed) {
                    result.add(Arrays.asList(
                            nums[i], nums[left], nums[right]
                    ));

                    left++;
                    right--;

                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }

                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }

                } else if (sum < needed) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        int[] nums = { -1, 0, 1, 2, -1, -4 };

        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Brute Force:  " + threeSumBruteForce(nums.clone()));
        System.out.println("HashSet:      " + threeSumHashSet(nums.clone()));
        System.out.println("Two Pointers: " + threeSumTwoPointers(nums.clone()));
    }
}
```

### Expected output

```text
Input: [-1, 0, 1, 2, -1, -4]
Brute Force:  [[-1, -1, 2], [-1, 0, 1]]
HashSet:      [[-1, -1, 2], [-1, 0, 1]]
Two Pointers: [[-1, -1, 2], [-1, 0, 1]]
```

Exact list order may differ for set-based conversion, but the unique triplets must match.

### Approach comparison

| Method | Time | Auxiliary space | Use |
|---|---:|---:|---|
| `threeSumBruteForce()` | `O(n³)` | output / set | Understand every triple |
| `threeSumHashSet()` | `O(n²)` | `O(n)` + output | Intermediate reduction to Two Sum |
| `threeSumTwoPointers()` | `O(n²)` | `O(1)` + output | Optimal interview solution |

---

## 19. Independent IDE practice

Before moving to Problem 5, write the solutions yourself without looking at the code.

- [ ] Implement the brute-force approach.
- [ ] Implement the sort + HashSet approach.
- [ ] Implement the sort + two-pointer approach.
- [ ] Explain why sorting is safe.
- [ ] Explain index uniqueness vs value uniqueness with one example.
- [ ] Explain why we skip duplicate `i` values, with a before/after example.
- [ ] Explain why we skip duplicate `left` / `right` values after a match.
- [ ] Explain why `[-1, -1, 2]` is still allowed.
- [ ] Trace `[-1, 0, 1, 2, -1, -4]` by hand.
- [ ] Trace `[-2, 0, 0, 2, 2]` by hand.
- [ ] State time and auxiliary-space complexity.
- [ ] Explain how 3Sum reuses Two Sum II.

### Small challenge

Trace this input without running the code:

```text
nums = [-2, 0, 0, 2, 2]
```

Write down:

1. The sorted array.
2. Every `i` value that is actually processed.
3. Every `left` / `right` movement.
4. Which duplicates are skipped.
5. The final unique triplets.

---

## 20. Quick revision

Remember these five things:

1. **Reduce the problem:** Fix one number, then solve Two Sum for `-nums[i]`.
2. **Sort first:** Safe because answers are values, not indices.
3. **Value uniqueness ≠ index uniqueness:** Different indices can still make the same answer.
4. **Skip duplicates:** At `i`, and after each successful `left` / `right` match.
5. **Complexity:** `O(n²)` time and `O(1)` extra space besides the output.

Reusable sentence:

> **3Sum = sort + fixed value + Two Sum II + skip duplicates.**

---

## 21. Pattern used in this problem

**Sorting + Fixed Pointer + Opposite-direction Two Pointers.**

This problem-specific explanation belongs in this problem's file. The reusable explanation of opposite-direction pointers belongs in the separate **`Two_Pointers_Common_Patterns.md`** file.

We maintain only:
- One common file for all reusable Two Pointers patterns.
- One separate detailed file for each problem.

There is no additional pattern file for this problem.

---

## 22. Link to Java — which method is which

| Approach | Method in `ThreeSum.java` |
|---|---|
| Brute force (every triple) | `threeSumBruteForce` |
| Sort + HashSet | `threeSumHashSet` |
| Sort + two pointers (optimal) | `threeSumTwoPointers` |

Run `main` to print all approaches on sample input.

---

## 23. Next problem

When you are ready, the next roadmap item is:

**Problem 05 — Move Zeroes**  
Main concept: Slow/fast pointers
