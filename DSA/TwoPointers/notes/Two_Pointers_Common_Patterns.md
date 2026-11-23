# Two Pointers --- Common Patterns Reference

A reusable revision guide for Java interviews (Java 8/17).

This file explains the patterns shared by many Two Pointers problems.
Each individual problem should still have its own separate notes file
with its complete problem statement, reasoning, approaches, code, dry
run, complexity, edge cases, and follow-ups.

------------------------------------------------------------------------

## 1. What is the Two Pointers technique?

Two Pointers is a problem-solving technique in which we maintain two
index variables (pointers) to inspect or process elements in a data
structure, commonly an array or string.

Instead of repeatedly checking every possible pair with nested loops, we
move pointers according to a rule. When that rule safely eliminates
possibilities, we can often reduce an O(n²) solution to O(n).

A pointer is usually just an integer index:

``` java
int left = 0;
int right = arr.length - 1;
```

The important part is not having two variables. The important part is
knowing:

1.  What does each pointer represent?
2.  Where should each pointer start?
3.  What condition keeps the algorithm running?
4.  What evidence tells us which pointer to move?
5.  Why is it safe to discard the possibilities we are skipping?

**Interview rule:** Never move a pointer randomly. Be able to explain
why that movement cannot discard a valid answer.

------------------------------------------------------------------------

## 2. How to recognise a Two Pointers problem

Look for these clues:

-   The input is an array or string.
-   The problem asks about pairs, matching values, reversing,
    palindromes, duplicates, or combining sorted data.
-   The array is already sorted, or sorting is allowed.
-   A brute-force solution checks many pairs or repeatedly scans the
    same data.
-   The problem asks for an in-place transformation or O(1) extra space.
-   The problem naturally involves comparing values from the beginning
    and end.
-   You can make a safe decision to move one pointer based on the
    current values.

These clues suggest Two Pointers, but they do not prove it. First
understand the problem and constraints.

------------------------------------------------------------------------

## 3. Pattern A --- Opposite-direction pointers

### Shape

One pointer starts at the beginning and the other starts at the end.

``` java
int left = 0;
int right = arr.length - 1;

while (left < right) {
    // Compare arr[left] and arr[right].

    left++;
    right--;
}
```

### When to use it

Use this pattern when the solution compares or processes the two ends of
the same array or string.

Common examples:

-   Valid Palindrome
-   Two Sum II --- Input Array Is Sorted
-   Container With Most Water
-   Reverse an Array
-   Trapping Rain Water (one common optimal approach)

### How to decide pointer movement

There is no universal movement rule. The problem's logic determines
which pointer moves.

Examples:

-   **Palindrome:** If both characters match, move both inward. If they
    do not match, the answer may immediately be false.
-   **Sorted Two Sum:** If the sum is too small, move the left pointer
    right. If it is too large, move the right pointer left.
-   **Container With Most Water:** Move the pointer at the shorter
    height because keeping that shorter boundary cannot produce a larger
    area with a narrower width.

### Why is the loop often `left < right`?

The pointers have not crossed while they are still examining a pair of
distinct positions. When `left == right`, both point to the same middle
element, so there is no pair of different ends left to compare.

Some problems use `left <= right`; choose the condition based on whether
the middle element must be processed.

### Common mistake

Do not automatically move both pointers after every comparison. In Two
Sum II, moving both can skip a possible answer. In other problems, the
correct movement is different.

------------------------------------------------------------------------

## 4. Pattern B --- Same-direction / Slow-Fast pointers

### Shape

Both pointers move from left to right, but at different speeds or for
different purposes.

``` java
int slow = 0;

for (int fast = 0; fast < arr.length; fast++) {
    // Decide whether arr[fast] should be kept.

    if (/* keep this value */) {
        arr[slow] = arr[fast];
        slow++;
    }
}
```

### What do the pointers mean?

-   `fast` explores the input.
-   `slow` marks where the next accepted value should be placed, or
    tracks a boundary.

The exact meaning of `slow` must be defined for each problem.

### When to use it

Use it when you need to scan all elements while maintaining a compacted
or processed region.

Common examples:

-   Move Zeroes
-   Remove Duplicates from Sorted Array
-   Remove Element
-   Partition an array by a condition

### The key invariant

An invariant is a statement that remains true throughout the loop.

For example, in an in-place removal problem:

> Everything before `slow` contains the values that should remain, in
> the required order.

The `fast` pointer scans the unprocessed part. When a value should
remain, write it at `slow` and advance `slow`.

### Common mistake

Confusing the meaning of `slow`. Sometimes it points to the next write
position; sometimes it points to the last valid element. State which
meaning you are using before writing code.

------------------------------------------------------------------------

## 5. Pattern C --- Read / Write pointers

This is closely related to slow-fast pointers, but it is useful to think
of the pointers by their jobs.

-   `read` scans every input element.
-   `write` identifies where the next retained or transformed element
    belongs.

``` java
int write = 0;

for (int read = 0; read < arr.length; read++) {
    if (/* keep arr[read] */) {
        arr[write] = arr[read];
        write++;
    }
}
```

### When to use it

-   Remove unwanted values in-place.
-   Remove duplicates from sorted data.
-   Move or compact selected values.
-   Preserve the relative order of retained values.

### What does the returned length mean?

In many array-compaction problems, the first `write` elements are the
valid result. Values after that position may be stale and should be
ignored.

For example, if the original array is:

``` text
[0, 1, 0, 3, 12]
```

After moving non-zero values forward, the meaningful prefix may be:

``` text
[1, 3, 12, ...]
```

The array's physical length has not changed. The algorithm returns or
tracks the logical length of the valid prefix.

------------------------------------------------------------------------

## 6. Pattern D --- Two pointers across two arrays

### Shape

Each pointer scans a different array.

``` java
int i = 0;
int j = 0;

while (i < first.length && j < second.length) {
    // Compare first[i] and second[j].
}
```

### When to use it

Especially useful when both arrays are sorted.

Common examples:

-   Merge Two Sorted Arrays
-   Intersection of Two Sorted Arrays
-   Compare two sequences
-   Find common values in sorted lists

### Typical movement rule for sorted arrays

Suppose you want to find common values:

-   If `first[i] == second[j]`, you found a match; process it and move
    both.
-   If `first[i] < second[j]`, move `i`, because the smaller value
    cannot match the current or any later value in the second sorted
    array.
-   If `first[i] > second[j]`, move `j` for the symmetric reason.

After one array is exhausted, there are no more pairs to compare.

### Common mistake

Using `&&` versus `||` incorrectly. If both pointers must be valid to
compare current elements, use:

``` java
while (i < first.length && j < second.length)
```

------------------------------------------------------------------------

## 7. Pattern E --- Sorting + Two Pointers

Sometimes the original input is not sorted. Sorting can make a
two-pointer strategy possible.

### General workflow

1.  Sort the array.
2.  Fix one element if the problem requires it.
3.  Use two pointers on the remaining range.
4.  Move pointers based on the sorted order.
5.  Handle duplicate values when the problem requires unique results.

### Example family

-   3Sum
-   4Sum (often sorting plus nested fixed choices and two pointers)
-   Pair-sum variants where original order does not matter

### Important trade-off

Sorting usually costs O(n log n). It may also change the original order.

Before sorting, ask:

-   Does the problem require original indices?
-   Does it require preserving input order?
-   Is modifying the input allowed?
-   Can I sort a copy instead?
-   Does the output need unique combinations?

For ordinary Two Sum on an unsorted array where original indices are
required, a HashMap is usually more appropriate than sorting the input
and using two pointers.

------------------------------------------------------------------------

## 8. Pattern F --- Two Pointers with a greedy decision

In some problems, the pointers represent boundaries. At every step, a
local decision safely eliminates one side.

### Example: Container With Most Water

The area is:

`width * min(leftHeight, rightHeight)`

The shorter wall limits the water height. Moving the taller wall inward
reduces width but does not remove the shorter limiting wall. Moving the
shorter wall is the only move that could find a taller limiting wall and
possibly improve the area.

This is a problem-specific proof, not a rule that the shorter pointer
always moves in every Two Pointers problem.

### Interview habit

When using a greedy pointer movement, explain:

1.  What limits the current answer?
2.  Which pointer represents that limitation?
3.  Why can keeping that pointer not improve the answer?
4.  Why is moving the other pointer the useful choice?

------------------------------------------------------------------------

## 9. Pattern G --- Mismatch and branching

Sometimes two pointers move inward until they find a mismatch. At that
point, the algorithm branches into a small number of possibilities.

### Example family

-   Valid Palindrome II: determine whether a string can become a
    palindrome after deleting at most one character.

### General idea

1.  Start with opposite-direction pointers.
2.  Move inward while values match.
3.  At the first mismatch, test the allowed alternatives.
4.  Return whether at least one permitted alternative works.

### Common mistake

Branching at every character. Usually, the first mismatch is the
important decision point. Also be clear whether the problem permits one
deletion, one replacement, or some other operation.

------------------------------------------------------------------------

## 10. A repeatable method for starting any Two Pointers problem

When you see a new problem, do not immediately write code. Use this
checklist.

### Step 1 --- Understand the contract

Write down:

-   What is the input?
-   What exactly must be returned?
-   Are values sorted?
-   Can values be negative or duplicated?
-   Must original indices be returned?
-   Can the input be modified?
-   What are the constraints?

### Step 2 --- Build a brute-force solution mentally

Ask:

-   Would I check every pair?
-   Would I reverse or copy the string?
-   Would I use nested loops?
-   What is the time and extra-space cost?

A brute-force solution gives you a correct baseline.

### Step 3 --- Look for repeated work

Ask:

-   Am I checking the same candidates repeatedly?
-   Does sorted order let me discard many candidates at once?
-   Can I process an element once using a read pointer?
-   Can the ends be compared without copying the input?

### Step 4 --- Define the pointers in plain English

For example:

-   `left`: current smallest candidate.
-   `right`: current largest candidate.
-   `fast`: next element to inspect.
-   `slow`: next position to write a valid element.
-   `i`: current element in the first sorted array.
-   `j`: current element in the second sorted array.

If you cannot explain what each pointer means, pause before coding.

### Step 5 --- Write the movement rules

Make a small decision table before coding.

  -----------------------------------------------------------------------
  Situation               Action                  Reason
  ----------------------- ----------------------- -----------------------
  Sum too small in sorted Move left rightward     Need a larger value
  pair-sum                                        

  Sum too large in sorted Move right leftward     Need a smaller value
  pair-sum                                        

  Values match in         Move both inward        Both ends are valid
  palindrome                                      

  Read value should be    Write at slow/write,    Extend valid prefix
  kept                    advance it              

  First sorted-array      Advance first-array     It cannot match the
  value is smaller        pointer                 larger current value
  -----------------------------------------------------------------------

These are examples for their specific patterns, not universal rules.

### Step 6 --- State the invariant

An invariant describes what is already known to be correct.

Examples:

-   The region before `slow` contains all accepted elements.
-   All pairs outside the current `[left, right]` range have already
    been ruled out.
-   The processed prefix of each array has been merged correctly.

### Step 7 --- Dry-run before trusting the code

Track:

-   Pointer positions.
-   Values at those positions.
-   Current calculation or comparison.
-   Which pointer moves.
-   Why it moves.
-   What remains unprocessed.

### Step 8 --- Check boundaries

Always consider:

-   Empty input.
-   One element.
-   Two elements.
-   Duplicates.
-   Negative values.
-   Already sorted or reverse-sorted input.
-   No valid answer.
-   Multiple valid answers.
-   Integer overflow in arithmetic.

------------------------------------------------------------------------

## 11. Java templates

These are starting shapes, not copy-paste solutions for every problem.

### Opposite-direction

``` java
int left = 0;
int right = arr.length - 1;

while (left < right) {
    // Compare or calculate using arr[left] and arr[right].
    // Move the pointer(s) justified by the problem.
}
```

### Slow-fast / read-write

``` java
int write = 0;

for (int read = 0; read < arr.length; read++) {
    if (/* current value should be kept */) {
        arr[write] = arr[read];
        write++;
    }
}

// Usually, write is the logical length of the valid prefix.
```

### Two sorted arrays

``` java
int i = 0;
int j = 0;

while (i < first.length && j < second.length) {
    if (first[i] == second[j]) {
        // Process the match.
        i++;
        j++;
    } else if (first[i] < second[j]) {
        i++;
    } else {
        j++;
    }
}
```

### Fixed pointer + two pointers

``` java
for (int fixed = 0; fixed < arr.length - 2; fixed++) {
    int left = fixed + 1;
    int right = arr.length - 1;

    while (left < right) {
        // Evaluate the combination.
        // Move left or right based on sorted order.
    }
}
```

------------------------------------------------------------------------

## 12. Common mistakes to avoid

1.  **Using two pointers just because the problem has an array.** There
    must be a valid movement rule.
2.  **Ignoring the sorted-input requirement.** Pointer decisions based
    on smaller/larger values usually depend on sorted order.
3.  **Moving both pointers without justification.** This can skip valid
    candidates.
4.  **Using the wrong loop condition.** `left < right`, `left <= right`,
    and bounds checks serve different purposes.
5.  **Forgetting duplicate handling.** Problems asking for unique
    combinations often need extra duplicate-skipping logic.
6.  **Losing original indices after sorting.** Sorting changes
    positions.
7.  **Returning the physical array length instead of the logical valid
    length.** Common in in-place compaction problems.
8.  **Not proving greedy movement.** Explain why discarded candidates
    cannot be answers.
9.  **Forgetting integer overflow.** A sum such as `arr[i] + arr[j]` may
    overflow `int`; use `long` when constraints require it.
10. **Writing code before defining pointer meaning.** This often leads
    to confusing updates and off-by-one errors.

------------------------------------------------------------------------

## 13. Complexity quick reference

  ------------------------------------------------------------------------
  Pattern                           Typical time       Typical extra space
  -------------------- ------------------------- -------------------------
  Opposite-direction                        O(n)                      O(1)
  scan                                           

  Slow-fast /                               O(n)                      O(1)
  read-write scan                                

  Two sorted arrays                     O(n + m)    O(1), excluding output

  Sorting + two           O(n log n) overall for    Depends on sorting and
  pointers                   one sort, plus scan                    output

  HashMap pair search               O(n) average                      O(n)
  ------------------------------------------------------------------------

Complexity depends on the exact problem, language implementation,
sorting method, and whether output storage is counted.

------------------------------------------------------------------------

## 14. How to explain a Two Pointers solution in an interview

Use this order:

1.  **Baseline:** "A straightforward solution would check all pairs,
    which takes O(n²) time."
2.  **Observation:** "The input is sorted, so the order tells us whether
    the current sum is too small or too large."
3.  **Pointer meaning:** "I keep one pointer at the beginning and one at
    the end."
4.  **Movement rule:** "If the sum is too small, I move the left pointer
    because I need a larger value."
5.  **Correctness:** "Because the array is sorted, this movement rules
    out the current left value with the current right value and all
    remaining smaller right-side choices."
6.  **Complexity:** "Each pointer moves only in one direction, at most n
    times, so time is O(n) and auxiliary space is O(1)."

Adapt the explanation to the actual problem; do not reuse the Two Sum
proof for a different problem.

------------------------------------------------------------------------

## 15. Practice roadmap

Study and solve one problem at a time. Keep a separate detailed file for
each problem.

1.  Valid Palindrome --- opposite-direction pointers.
2.  Two Sum II --- sorted array + opposite pointers.
3.  Container With Most Water --- greedy boundary movement.
4.  3Sum --- sorting + fixed pointer + two pointers.
5.  Move Zeroes --- slow-fast / read-write.
6.  Remove Duplicates from Sorted Array --- read-write.
7.  Valid Palindrome II --- mismatch and branching.
8.  Trapping Rain Water --- boundary pointers and running maxima.
9.  Merge Sorted Arrays --- two sorted sequences / in-place merge.
10. Squares of a Sorted Array --- compare absolute values at both ends.

For each problem, aim to explain the pointer meaning, movement rule,
invariant, and complexity without looking at the notes.
