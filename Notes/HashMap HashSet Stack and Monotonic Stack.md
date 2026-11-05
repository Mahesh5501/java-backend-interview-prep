
# DSA PATTERN 4 & 5 — HASHMAP / HASHSET + STACK / MONOTONIC STACK
## Recognition → Algorithm → Variations → Interview Problems

# PATTERN 4 — HASHMAP / HASHSET

## 1. What Is the HashMap / HashSet Pattern?

Use HashMap or HashSet when you need to remember information about elements you have already seen.

Imagine this array:

[4, 2, 7, 2, 4]

Questions you might be asked:
- Have I seen this number before?
- How many times does each number appear?
- Where did I first see this number?
- Does another number exist that completes a target sum?
- How many subarrays have a particular sum?
- Which elements appear in both arrays?

HashMap and HashSet let you answer these questions efficiently without repeatedly scanning the entire collection.

### HashSet

Stores unique values.

Example:

Set = {4, 2, 7}

The repeated 4 and 2 are stored only once.

Use it when you mainly need to know whether a value exists.

### HashMap

Stores key-value pairs.

Example:

Map = {4=2, 2=2, 7=1}

Here:
- 4 appears twice.
- 2 appears twice.
- 7 appears once.

Use it when you need to associate information with a key.

---

## 2. How to Recognize HashMap / HashSet

| Problem clue | Likely approach |
|---|---|
| Find duplicates | HashSet |
| Check whether a value exists | HashSet |
| Count frequency | HashMap |
| Find the first repeated value | HashSet or HashMap |
| Find the first non-repeating character | Frequency HashMap |
| Two Sum in an unsorted array | HashMap |
| Group anagrams | HashMap with a canonical key |
| Find common elements | HashSet or HashMap |
| Longest consecutive sequence | HashSet |
| Count subarrays with sum K | Prefix Sum + HashMap |
| Find a value's previous index | HashMap |
| Find elements occurring more than a threshold | HashMap |
| Next greater element | Stack, usually monotonic |
| Balanced parentheses | Stack |

Important:

HashMap and HashSet are not the same pattern as Sliding Window. They are data structures that can be used inside Sliding Window or other algorithms.

For example:
- Longest substring without repeated characters → Sliding Window + HashSet or HashMap.
- Two Sum → HashMap without a window.
- Count subarrays with sum K → Prefix Sum + HashMap.

---

## 3. HashMap / HashSet Decision Tree

Ask these questions.

### Question 1: Do I only need to know whether a value exists?

Use HashSet.

Examples:
- Detect duplicates.
- Find whether a number is present.
- Find common values.

### Question 2: Do I need a count or additional information for each value?

Use HashMap.

Examples:
- Frequency counting.
- Last-seen index.
- Grouping elements.
- Mapping a value to its complement.

### Question 3: Am I searching for a pair that satisfies a condition?

Consider storing previously seen values in a HashMap or HashSet.

Examples:
- Two Sum.
- Pair with a target difference.
- Find a complement.

### Question 4: Is the question about a continuous subarray and its sum?

Consider Prefix Sum + HashMap, especially when negative numbers are allowed.

### Question 5: Is the question about the nearest greater or smaller element?

Consider a monotonic stack instead of HashMap or HashSet.

---

# FAMILY A — FREQUENCY COUNTING

## 4. How to Recognize It

Look for:
- How many times does each value appear?
- Find duplicates.
- Find the most frequent value.
- Find the first non-repeating character.
- Check whether two strings have the same character frequencies.

The main idea is simple:

Use each element as a key and its frequency as the value.

## 5. Algorithm

1. Create an empty HashMap.
2. Visit every element.
3. Increase its count.
4. Use the finished map to answer the question.

## 6. Java Template

    Map<Integer, Integer> frequency = new HashMap<>();

    for (int value : arr) {
        frequency.put(
            value,
            frequency.getOrDefault(value, 0) + 1
        );
    }

`getOrDefault(value, 0)` means:
- If the value already exists, return its current count.
- Otherwise, use 0 as its initial count.

## 7. Example

Input:

[4, 2, 7, 2, 4, 4]

Process the elements:

- Read 4 → {4=1}
- Read 2 → {4=1, 2=1}
- Read 7 → {4=1, 2=1, 7=1}
- Read 2 → {4=1, 2=2, 7=1}
- Read 4 → {4=2, 2=2, 7=1}
- Read 4 → {4=3, 2=2, 7=1}

Final result:

4 appears 3 times.
2 appears 2 times.
7 appears 1 time.

Complexity:
- Time: O(n) expected.
- Extra space: O(u), where u is the number of distinct values.

## 8. Variation — First Non-Repeating Character

Input:

"swiss"

Frequency counts:
- s → 3
- w → 1
- i → 1

Scan the original string from left to right:
- s → repeated, skip.
- w → appears once, return it.

Answer = 'w'

Why do we scan the original string again?

A HashMap does not automatically tell us which qualifying character appeared first in the original input. The second scan preserves the original order.

Java solution:

    static char firstNonRepeating(String s) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : s.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        for (char c : s.toCharArray()) {
            if (frequency.get(c) == 1) {
                return c;
            }
        }

        return '\0'; // No non-repeating character
    }

Complexity:
- Time: O(n).
- Extra space: O(u).

---

# FAMILY B — LOOKUP AND COMPLEMENTS

## 9. How to Recognize It

Look for:
- Find two numbers that sum to a target.
- Find a pair with a target difference.
- Find two values that satisfy a relationship.
- Return indices of matching values.

The important question is:

**Can I calculate the value I need from the current value?**

For Two Sum:

current + needed = target

Therefore:

needed = target - current

Instead of checking every possible pair, remember values you have already visited.

## 10. Example — Two Sum in an Unsorted Array

Input:

arr = [2, 7, 11, 15]
target = 9

Visit 2:
- Needed = 9 - 2 = 7.
- Have we seen 7? No.
- Store 2.

Visit 7:
- Needed = 9 - 7 = 2.
- Have we seen 2? Yes.
- Pair found.

Answer = indices [0, 1].

## 11. Java Solution

    static int[] twoSum(int[] arr, int target) {
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int needed = target - arr[i];

            if (seen.containsKey(needed)) {
                return new int[]{seen.get(needed), i};
            }

            seen.put(arr[i], i);
        }

        return new int[]{-1, -1};
    }

Why do we check before inserting the current element?

The problem usually requires two different indices. Checking first prevents the current element from matching itself.

Complexity:
- Time: O(n) expected.
- Extra space: O(n).

### Variations

| Problem | What changes? |
|---|---|
| Return indices | Store value → index |
| Return values | Store values or return the pair |
| Count pairs | Track frequencies and carefully handle duplicates |
| Find a pair with difference K | Check related values such as x + K or x - K |
| Input is sorted | Opposite-end Two Pointers may use O(1) extra space |
| Find triplets | Sorting + Two Pointers is a common approach |

Do not automatically use HashMap for every pair problem. Sorted input may make Two Pointers simpler.

---

# FAMILY C — GROUPING BY A COMMON KEY

## 12. How to Recognize It

Look for:
- Group words that are anagrams.
- Group objects by a property.
- Find values sharing the same signature.
- Categorize records using a derived key.

The key idea:

Different inputs belong together when they produce the same key.

## 13. Example — Group Anagrams

Input:

["eat", "tea", "tan", "ate", "nat", "bat"]

Anagrams contain the same characters with the same frequencies.

One simple key is the sorted string.

- "eat" → "aet"
- "tea" → "aet"
- "ate" → "aet"
- "tan" → "ant"
- "nat" → "ant"
- "bat" → "abt"

Words with the same key go into the same group.

## 14. Java Solution

    static List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);

            String key = new String(chars);

            groups.computeIfAbsent(
                key,
                k -> new ArrayList<>()
            ).add(word);
        }

        return new ArrayList<>(groups.values());
    }

`computeIfAbsent` means:
- If the key exists, return its list.
- Otherwise, create a new list, store it, and return it.

Complexity:
- If there are n words and each word has length k, sorting each word takes O(k log k).
- Overall time: O(n × k log k).
- Extra space: proportional to the keys and grouped output.

Variation:
For a restricted lowercase-English alphabet, a 26-character frequency signature can replace sorting. That can reduce key-generation time to O(k), at the cost of a larger signature representation.

---

# FAMILY D — HASHSET AND SEQUENCE RECOGNITION

## 15. How to Recognize It

Look for:
- Longest consecutive sequence.
- Find missing values.
- Detect duplicates.
- Find values that do not depend on their original order.

A HashSet is useful when you need fast membership checks.

## 16. Example — Longest Consecutive Sequence

Input:

[100, 4, 200, 1, 3, 2]

Consecutive sequences include:
- 1, 2, 3, 4
- 100
- 200

Longest length = 4.

A slow solution would start counting from every value. That can repeat work.

A better approach:

1. Put all values into a HashSet.
2. For each value x, check whether x - 1 exists.
3. If x - 1 exists, x is not the start of a sequence.
4. If x - 1 does not exist, count forward from x.
5. Keep the longest length.

## 17. Java Solution

    static int longestConsecutive(int[] arr) {
        Set<Integer> values = new HashSet<>();

        for (int value : arr) {
            values.add(value);
        }

        int best = 0;

        for (int value : values) {
            if (!values.contains(value - 1)) {
                int current = value;
                int length = 1;

                while (values.contains(current + 1)) {
                    current++;
                    length++;
                }

                best = Math.max(best, length);
            }
        }

        return best;
    }

Why do we check `value - 1`?

We only start counting when we have found the beginning of a consecutive sequence. This avoids recounting the same sequence from every element.

Complexity:
- Time: O(n) expected.
- Extra space: O(n).

Important Java edge case:
If inputs can contain `Integer.MIN_VALUE` or `Integer.MAX_VALUE`, protect arithmetic against integer overflow when calculating adjacent values.

---

# FAMILY E — PREFIX SUM + HASHMAP

## 18. How to Recognize It

Look for:
- Count subarrays with sum K.
- Find whether a subarray has a target sum.
- Find a subarray whose sum is zero.
- Count ranges with a particular sum.
- Negative numbers are allowed.

This family is especially important because ordinary Sliding Window logic may fail when negative numbers are present.

## 19. What Is Prefix Sum?

Prefix sum means the sum of all elements from the beginning through a particular position.

Example:

Array = [2, -1, 3]

Prefix sums:
- After 2 → 2
- After -1 → 1
- After 3 → 4

Suppose:
- current prefix sum = 7
- target = 3

We need an earlier prefix sum of:

7 - 3 = 4

Why?

If currentPrefix - previousPrefix = target, the elements between those prefix positions sum to target.

Therefore:

previousPrefix = currentPrefix - target

We can use a HashMap to remember how many times each prefix sum has appeared.

## 20. Count Subarrays With Sum K

Example:

arr = [1, 1, 1]
k = 2

Valid subarrays:
- [1, 1] at indices 0–1
- [1, 1] at indices 1–2

Answer = 2.

## 21. Java Solution

    static int subarraySum(int[] arr, int k) {
        Map<Integer, Integer> prefixFrequency = new HashMap<>();

        prefixFrequency.put(0, 1);

        int prefixSum = 0;
        int count = 0;

        for (int value : arr) {
            prefixSum += value;

            int needed = prefixSum - k;

            count += prefixFrequency.getOrDefault(needed, 0);

            prefixFrequency.put(
                prefixSum,
                prefixFrequency.getOrDefault(prefixSum, 0) + 1
            );
        }

        return count;
    }

Why initialize the map with `{0: 1}`?

It represents an empty prefix with sum zero. It allows us to count a subarray that starts at index zero.

Why add the current prefix sum after checking?

We want earlier prefixes to identify subarrays ending at the current element. Inserting afterward also avoids counting the current prefix as its own earlier prefix.

Complexity:
- Time: O(n) expected.
- Extra space: O(n).

Important:
The running sum and answer can overflow `int` for large inputs. If constraints require it, use `long` for prefix sums and counts.

---

## 22. HashMap vs HashSet vs Sliding Window

| Requirement | Good starting point |
|---|---|
| Does this value exist? | HashSet |
| How often does this value appear? | HashMap |
| Where did I last see this value? | HashMap |
| Find a complement for the current value | HashMap or HashSet |
| Longest continuous substring with a rule | Sliding Window + Set/Map |
| Count subarrays with sum K | Prefix Sum + HashMap |
| Longest consecutive values regardless of order | HashSet |
| Find the nearest greater element | Monotonic Stack |

---

## 23. HashMap / HashSet Common Mistakes

1. Using a HashSet when you need frequencies.
2. Storing a value after checking the wrong condition.
3. Forgetting that a HashMap can store only one value per key; use a list or count when necessary.
4. Forgetting that `put` replaces the existing value for a key.
5. Assuming HashMap iteration preserves insertion order.
6. Assuming every HashMap operation is guaranteed O(1); average performance is generally constant time, but worst-case behavior can differ.
7. Forgetting to initialize prefix frequency with `{0: 1}` in the standard subarray-sum counting solution.
8. Using ordinary Sliding Window for negative-valued sum problems without validating the approach.
9. Confusing the first occurrence with the last occurrence.
10. Ignoring integer overflow in sums or counts.

---

# PATTERN 5 — STACK / MONOTONIC STACK

## 24. What Is a Stack?

A stack follows LIFO:

Last In, First Out.

Imagine a stack of plates:
- Put a plate on top.
- Remove the top plate first.

Java operations:

    Deque<Integer> stack = new ArrayDeque<>();

    stack.push(10);       // Add to top
    stack.push(20);
    stack.peek();         // Read top: 20
    stack.pop();          // Remove top: 20
    stack.isEmpty();      // Check whether empty

Use `Deque` with `ArrayDeque` for most ordinary stack problems in modern Java.

---

## 25. How to Recognize an Ordinary Stack

Look for:
- Balanced parentheses.
- Matching opening and closing brackets.
- Undo or backtracking through recent actions.
- Evaluate an expression.
- Reverse characters or elements.
- Nested structures.
- Process the most recent unmatched item first.

Why a stack?

Because the most recently opened or added item often needs to be handled first.

Examples:
- `({[]})` is balanced.
- `([)]` is not balanced.

When a closing bracket appears, it must match the most recently opened bracket.

---

# FAMILY A — BALANCED PARENTHESES

## 26. How to Recognize It

Look for:
- Balanced parentheses.
- Matching brackets.
- Valid nesting.
- Opening and closing symbols.

## 27. Algorithm

1. If an opening bracket appears, push it.
2. If a closing bracket appears:
   - The stack must not be empty.
   - The top must be the matching opening bracket.
   - Pop the top.
3. At the end, the stack must be empty.

## 28. Java Solution

    static boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                char open = stack.pop();

                if (c == ')' && open != '(') {
                    return false;
                }

                if (c == ']' && open != '[') {
                    return false;
                }

                if (c == '}' && open != '{') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

Complexity:
- Time: O(n).
- Extra space: O(n).

Note:
This version assumes the input contains only the three bracket types. If other characters are allowed, handle them explicitly.

---

# FAMILY B — NEXT GREATER / NEXT SMALLER ELEMENT

## 29. What Is a Monotonic Stack?

A monotonic stack keeps its elements in a particular increasing or decreasing order.

It is useful when a problem asks about the nearest greater or smaller element.

Examples:
- Next Greater Element.
- Next Smaller Element.
- Previous Greater Element.
- Previous Smaller Element.
- Daily Temperatures.
- Stock Span.
- Largest Rectangle in Histogram.

Instead of checking every element to the right or left, the stack helps remove candidates that can no longer be answers.

### Increasing vs Decreasing

Terminology can vary depending on whether people describe the stack from bottom to top or top to bottom. Always state the actual invariant.

For example:

"Values in my stack are decreasing from bottom to top" is more precise than simply saying "I use a decreasing stack."

---

## 30. How to Recognize Monotonic Stack

Look for:
- Next greater element.
- Next smaller element.
- Previous greater element.
- Previous smaller element.
- Nearest warmer day.
- First taller building to the right.
- Maximum rectangular area in a histogram.
- A value's next qualifying neighbour.

The key question:

**For each element, do I need the nearest element on one side that is greater or smaller?**

If yes, consider a monotonic stack.

---

# FAMILY C — NEXT GREATER ELEMENT

## 31. Example

Input:

[2, 1, 2, 4, 3]

Find the next greater element to the right for every position.

Output:

[4, 2, 4, -1, -1]

Explanation:
- For 2 at index 0, the next greater value is 4.
- For 1, the next greater value is 2.
- For 2 at index 2, the next greater value is 4.
- For 4, there is no greater value to its right.
- For 3, there is no greater value to its right.

## 32. Recognition and Algorithm

Process elements from left to right.

The stack stores indices whose next greater element has not yet been found.

When a new value arrives:
1. Compare it with the value at the stack top.
2. While the new value is greater, the new value answers the unresolved index at the top.
3. Pop that index and record the answer.
4. Continue resolving smaller values.
5. Push the current index.
6. At the end, unresolved elements have no greater value to the right.

Why store indices?

Because we need to know which position receives the answer. Indices also let us retrieve the corresponding values.

## 33. Java Solution

    static int[] nextGreater(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];

        Arrays.fill(result, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty()
                    && arr[i] > arr[stack.peek()]) {

                int index = stack.pop();
                result[index] = arr[i];
            }

            stack.push(i);
        }

        return result;
    }

Complexity:
- Time: O(n).
- Extra space: O(n).

Why is it O(n) despite the `while` loop?

Each index is pushed onto the stack once and popped at most once. The total number of stack operations is therefore linear.

### Important variation: Strictly greater vs greater than or equal

For strictly greater, use:

    arr[i] > arr[stack.peek()]

If equal values are also allowed to answer the condition, the comparison changes.

The correct comparison depends on the exact wording of the problem.

---

# FAMILY D — DAILY TEMPERATURES

## 34. How to Recognize It

Question:

For each day, find how many days must pass until a warmer temperature.

Input:

[73, 74, 75, 71, 69, 72, 76, 73]

Output:

[1, 1, 4, 2, 1, 1, 0, 0]

Why is this a monotonic stack problem?

For each day, we need the next greater temperature to the right, but the answer is the number of positions between the two days.

The stack stores indices of unresolved days.

## 35. Java Solution

    static int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty()
                    && temperatures[i] > temperatures[stack.peek()]) {

                int previousDay = stack.pop();

                result[previousDay] = i - previousDay;
            }

            stack.push(i);
        }

        return result;
    }

Complexity:
- Time: O(n).
- Extra space: O(n).

Variation:
If the problem asks for the next greater temperature itself, store that value rather than the distance between indices.

---

# FAMILY E — STOCK SPAN

## 36. How to Recognize It

For each day's stock price, calculate how many consecutive days up to and including today had prices less than or equal to today's price.

Example:

Prices = [100, 80, 60, 70, 60, 75, 85]

Spans = [1, 1, 1, 2, 1, 4, 6]

The problem asks how far backward we can extend before finding a greater price.

That is a previous-greater-element problem.

## 37. Algorithm

1. Maintain a stack of indices with prices in decreasing order.
2. For each price, pop previous prices that are less than or equal to it.
3. If the stack becomes empty, all previous days are included.
4. Otherwise, the top is the nearest previous day with a greater price.
5. Calculate the span from that index.

Java solution:

    static int[] stockSpan(int[] prices) {
        int n = prices.length;
        int[] span = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty()
                    && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }

            span[i] = stack.isEmpty()
                    ? i + 1
                    : i - stack.peek();

            stack.push(i);
        }

        return span;
    }

Complexity:
- Time: O(n).
- Extra space: O(n).

Important:
The `<=` comparison matters because the problem includes previous prices equal to today's price in the span.

---

# FAMILY F — LARGEST RECTANGLE IN HISTOGRAM

## 38. How to Recognize It

Given bar heights, find the largest rectangular area that can fit inside the histogram.

Example:

Heights = [2, 1, 5, 6, 2, 3]

Answer = 10.

The rectangle formed by heights 5 and 6 has:
- Width = 2.
- Height = 5.
- Area = 10.

Why a monotonic stack?

For each bar, we need to determine how far a rectangle of that height can extend to the left and right before encountering a shorter bar.

The stack helps find the boundaries efficiently.

### High-level algorithm

1. Maintain a stack of indices with increasing bar heights.
2. When a shorter bar arrives, pop taller bars.
3. Each popped bar can now have its right boundary determined.
4. Use the new stack top to determine its left boundary.
5. Calculate its maximum area.
6. Continue until all bars are processed, using an appropriate final flush or sentinel.

This is a more advanced monotonic-stack problem. Learn Next Greater Element and Stock Span first.

---

# 39. Ordinary Stack vs Monotonic Stack

| Question | Ordinary Stack | Monotonic Stack |
|---|---|---|
| Balanced parentheses | Yes | No |
| Evaluate postfix expression | Yes | No |
| Undo last operation | Yes | No |
| Next greater element | Usually not sufficient by itself | Yes |
| Next smaller element | Usually not sufficient by itself | Yes |
| Daily temperatures | No typical ordinary-stack solution | Yes |
| Stock span | No typical ordinary-stack solution | Yes |
| Largest histogram rectangle | Specialized monotonic stack | Yes |

A monotonic stack is still a stack. The difference is that we maintain an ordering invariant while pushing and popping elements.

---

# 40. How to Choose the Stack's Direction

Do not memorize one direction for every problem.

Instead, ask:

1. Am I looking for the next greater or next smaller element?
2. Am I processing from left to right or right to left?
3. Which previously seen elements become resolved when the current element arrives?
4. Which elements can never be answers anymore?

For next greater element while scanning left to right:
- Keep unresolved values on the stack.
- When the current value is greater than the top value, resolve and pop the top.

For stock span:
- Remove previous values that are less than or equal to the current price.
- The remaining top, if any, is the previous greater value.

The exact comparison determines how equal values are treated.

---

# 41. Common Stack / Monotonic Stack Mistakes

1. Calling `peek()` or `pop()` without checking whether the stack is empty.
2. Storing values when indices are required for distances or result positions.
3. Using the wrong comparison operator for equal values.
4. Forgetting to process unresolved stack elements at the end when the algorithm requires a final flush.
5. Assuming every problem asking for a greater element needs a monotonic stack without checking direction or requirements.
6. Using an ordinary stack when the problem needs a maintained ordering invariant.
7. Thinking nested `while` loops automatically mean O(n²).
8. Confusing the nearest greater element with the maximum element to the right.
9. Forgetting that the top of the stack represents the most recently unresolved candidate, not necessarily the largest element overall.
10. Using a queue when the problem requires LIFO behavior.

---

# 42. Interview Follow-Up Questions

## HashMap / HashSet

Q: Why is HashMap useful for Two Sum?

A: It remembers previously seen values so that we can check for the required complement in expected constant time.

Q: Why do we scan twice for the first non-repeating character?

A: The first pass builds frequencies. The second pass finds the earliest character whose frequency is one.

Q: Why does Prefix Sum + HashMap work with negative values?

A: It uses the equality between prefix sums rather than assuming that the running sum changes monotonically.

Q: What is the difference between `containsKey` and `getOrDefault`?

A: `containsKey` checks whether a key exists. `getOrDefault` retrieves the associated value or a supplied fallback.

## Stack / Monotonic Stack

Q: Why does Next Greater Element run in O(n)?

A: Every index enters the stack once and leaves it at most once.

Q: Why store indices rather than values?

A: Indices let us calculate distances and write results into the correct positions.

Q: How do you choose `>` versus `>=`?

A: Follow the exact definition. Strictly greater excludes equal values; greater than or equal includes them.

Q: When should you use a monotonic stack?

A: When the problem needs a nearest greater/smaller candidate and elements can be discarded once they can no longer contribute to the answer.

---

# 43. How to Solve an Unfamiliar Problem

## Step 1 — Identify what must be remembered

- Existence only → HashSet.
- Count or associated information → HashMap.
- Most recent unmatched item → Stack.
- Unresolved greater/smaller candidates → Monotonic Stack.

## Step 2 — Identify the relationship

- Current value needs a complement → HashMap lookup.
- Repeated values need counting → Frequency map.
- Continuous range sum with negative values → Prefix Sum + HashMap may fit.
- Nearest greater/smaller element → Monotonic Stack.
- Nested brackets → Ordinary Stack.

## Step 3 — Define the stored information

Examples:
- Value → frequency.
- Value → previous index.
- Prefix sum → number of occurrences.
- Stack → indices of unresolved elements.

## Step 4 — Explain why each operation happens

For every `put`, `add`, `push`, or `pop`, explain what information is being saved or discarded and why.

## Step 5 — Dry-run a small example

Track:
- The map or set after each element.
- The stack from bottom to top.
- The current answer.

## Step 6 — Check edge cases

- Empty input.
- One element.
- Duplicate values.
- No match.
- All values equal.
- Negative values.
- Equal neighbouring values.
- No greater element exists.
- Integer overflow in sums.

## Step 7 — Explain complexity

State the expected time complexity and extra space. For monotonic stacks, explain why each element is pushed and popped at most once.

---

# 44. Five-Second Revision Table

| If the question says... | Think... |
|---|---|
| Does this value exist? | HashSet |
| Count occurrences | HashMap |
| Find a complement | HashMap |
| Group anagrams | HashMap + canonical key |
| Longest consecutive sequence | HashSet |
| Count subarrays with sum K | Prefix Sum + HashMap |
| Balanced brackets | Stack |
| Last opened item must close first | Stack |
| Next greater element | Monotonic Stack |
| Next smaller element | Monotonic Stack |
| Days until a warmer temperature | Monotonic Stack |
| Stock span | Monotonic Stack |
| Largest rectangle in histogram | Monotonic Stack |

---

# 45. Practice Order

## HashMap / HashSet

1. Contains Duplicate.
2. Valid Anagram.
3. First Non-Repeating Character.
4. Two Sum.
5. Group Anagrams.
6. Longest Consecutive Sequence.
7. Subarray Sum Equals K.
8. Longest Substring Without Repeating Characters — combine HashMap/HashSet with Sliding Window.

## Stack / Monotonic Stack

1. Valid Parentheses.
2. Implement a Stack using Queues.
3. Min Stack.
4. Next Greater Element.
5. Daily Temperatures.
6. Stock Span.
7. Next Smaller Element.
8. Largest Rectangle in Histogram.
9. Trapping Rain Water — compare monotonic-stack and two-pointer approaches.

---

# FINAL MEMORY RULE

HashMap / HashSet:

**Remember what you have seen so you can answer lookup, frequency, and relationship questions efficiently.**

Stack:

**Process the most recent unresolved item first.**

Monotonic Stack:

**Keep candidates in a useful order and remove candidates that can no longer be answers.**

Before coding, ask:

1. What information do I need to remember?
2. Do I need existence, frequency, or a previous index?
3. Is the problem asking for the nearest greater or smaller value?
4. What does each map entry or stack element represent?
5. Why is it safe to remove or overwrite this information?
