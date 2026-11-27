# Stack --- Common Patterns Reference

A reusable revision guide for Java interviews (Java 8/17).

This file explains patterns shared by classic stack problems. Each runnable
class in `../` includes brute-force where it helps, an optimal stack solution,
dry-run comments, and a `main()` demo.

------------------------------------------------------------------------

## 1. What is a Stack?

A **stack** is Last-In-First-Out (LIFO): the most recently pushed item is the
first one popped.

In Java, use `Deque` (double-ended queue) as a stack --- never the legacy
`Stack` class:

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(x);       // add on top
int top = stack.peek();  // view top without removing
int removed = stack.pop(); // remove top
boolean empty = stack.isEmpty();
```

**Interview rule:** Say *"I use `ArrayDeque` as a stack"* --- it is faster and
clearer than `java.util.Stack`.

------------------------------------------------------------------------

## 2. How to recognise a Stack problem

Look for:

- Matching or nesting: brackets, tags, nested strings (`3[abc]`).
- "Previous / next greater / smaller element" in an array.
- Expression evaluation: postfix (RPN), infix with precedence.
- Undo / cancel: remove last opened bracket or last duplicate pair.
- Processing left-to-right where you may need to **go back** to an earlier index.
- Monotonic behaviour: keep elements in increasing or decreasing order on the stack.

These clues suggest a stack; confirm with the problem contract.

------------------------------------------------------------------------

## 3. Pattern A --- Bracket / nesting validation

### Shape

Scan left to right. On an **opener**, push. On a **closer**, the top must be the
matching opener; pop if correct, else invalid.

### When to use it

- Valid Parentheses
- HTML/XML tag matching (variants)
- Any "open before close, LIFO order" rule

### Two common implementations

1. **Push openers; on closer, match against peek.**
2. **Push expected closer** when you see an opener (e.g. `(` pushes `)`).

### Invariant

> Everything still on the stack are openers (or expected closers) waiting for a
> partner later in the string.

At the end, stack must be empty.

------------------------------------------------------------------------

## 4. Pattern B --- Monotonic stack (indices or values)

Maintain a stack such that values (or values at indices) are **strictly
increasing** or **strictly decreasing** from bottom to top.

### Typical workflow (next greater to the right)

Often scan **right to left** (or left to right with different pop rules):

1. Pop from stack while top is "not useful" (e.g. `<=` current).
2. Stack top (if any) is the answer for current index.
3. Push current index or value.

### When to use it

- Daily Temperatures (days until warmer)
- Next Greater Element I / II
- Largest Rectangle in Histogram (increasing stack of indices)
- Stock span problems

### Why O(n)?

Each index is pushed once and popped at most once.

------------------------------------------------------------------------

## 5. Pattern C --- Stack + simulation / build result

Use the stack as scratch space while building output:

- **Remove adjacent duplicates:** push chars; if new equals peek, pop peek.
- **Decode String:** push current string + repeat count when you see `[` or digit context.
- **Evaluate RPN:** push numbers; on operator, pop two operands, push result.

### When to use it

The answer is a **string or number built incrementally**, and nested or
pairwise structure maps to LIFO.

------------------------------------------------------------------------

## 6. Pattern D --- Min Stack (auxiliary stack)

Track the minimum in O(1) by storing **pairs** `(value, minSoFar)` or a second
stack of running minimums.

### When to use it

Design problems requiring `getMin()` in O(1) alongside push/pop.

------------------------------------------------------------------------

## 7. Pattern E --- Histogram / width expansion

For **Largest Rectangle in Histogram:**

1. Append sentinel `0` height to force pops at the end.
2. Stack holds **indices** with **increasing heights**.
3. When current height is lower than height at stack top, the popped index's
   height is the rectangle height; width extends from new top to current index.

### Key idea

When you pop index `h`, the first smaller bar on the left is the new stack top;
on the right is the current index `i`.

------------------------------------------------------------------------

## 8. Repeatable method for any stack problem

1. **Contract:** input, output, constraints, online vs full scan.
2. **Brute force:** nested loops, re-scan from each index --- O(n²) baseline.
3. **Observation:** what future information does the stack remember?
4. **Stack stores:** values, indices, chars, or composite state?
5. **Pop rule:** what condition makes the top obsolete?
6. **Dry-run:** 5--8 steps on a small example.
7. **Edges:** empty input, single element, all increasing / all decreasing.
8. **Complexity:** usually O(n) time, O(n) stack space.

------------------------------------------------------------------------

## 9. Java templates

### Bracket validation (expected closer)

```java
Deque<Character> stack = new ArrayDeque<>();
for (char c : s.toCharArray()) {
    if (c == '(') stack.push(')');
    else if (c == '{') stack.push('}');
    else if (c == '[') stack.push(']');
    else {
        if (stack.isEmpty() || stack.pop() != c) return false;
    }
}
return stack.isEmpty();
```

### Monotonic stack --- next greater (right to left)

```java
Deque<Integer> stack = new ArrayDeque<>();
int[] ans = new int[n];
for (int i = n - 1; i >= 0; i--) {
    while (!stack.isEmpty() && stack.peek() <= nums[i]) stack.pop();
    ans[i] = stack.isEmpty() ? -1 : stack.peek();
    stack.push(nums[i]);
}
```

### RPN evaluation

```java
Deque<Integer> stack = new ArrayDeque<>();
for (String token : tokens) {
    if (isOperator(token)) {
        int b = stack.pop(), a = stack.pop();
        stack.push(apply(a, b, token));
    } else {
        stack.push(Integer.parseInt(token));
    }
}
return stack.pop();
```

------------------------------------------------------------------------

## 10. Common mistakes

1. Using `Stack` instead of `ArrayDeque`.
2. Forgetting to check `isEmpty()` before `pop()` / `peek()`.
3. Pushing **indices** but comparing **values** (or the reverse).
4. Wrong monotonic direction (increasing vs decreasing) for the question.
5. Off-by-one on width in histogram (use indices, not lengths only).
6. In RPN: pop order --- second pop is **left** operand for `-` and `/`.
7. Decode String: not saving/restoring prefix string when entering `[`.
8. Assuming stack empty at end without checking (brackets).

------------------------------------------------------------------------

## 11. Complexity quick reference

| Pattern              | Typical time | Typical extra space |
|----------------------|-------------:|--------------------:|
| Validation scan      | O(n)         | O(n)                |
| Monotonic stack      | O(n)         | O(n)                |
| RPN / simulation     | O(n)         | O(n)                |
| Histogram rectangle  | O(n)         | O(n)                |
| Brute force pairs    | O(n²)        | O(1)                |

------------------------------------------------------------------------

## 12. Interview explanation order

1. **Baseline:** nested loops or repeated scans --- O(n²).
2. **Idea:** stack remembers the nearest useful previous element.
3. **Invariant:** what ordering holds on the stack?
4. **Walkthrough:** one short dry run.
5. **Complexity:** each element pushed/popped once --- O(n).

------------------------------------------------------------------------

## 13. Practice roadmap (this folder)

1. Valid Parentheses --- validation.
2. Min Stack --- design + auxiliary min.
3. Daily Temperatures --- monotonic indices.
4. Next Greater Element I --- monotonic values.
5. Largest Rectangle in Histogram --- monotonic indices + width.
6. Decode String --- stack of states.
7. Evaluate RPN --- operand stack.
8. Remove Adjacent Duplicates --- cancel on push.

For each file, practice saying the brute approach, the stack invariant, and
time/space without opening the code.