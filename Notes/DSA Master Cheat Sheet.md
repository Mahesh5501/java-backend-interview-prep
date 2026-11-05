
# JAVA DSA MASTER CHEAT SHEET
## Understand the Problem → Recognize the Pattern → Choose the Algorithm → Write Java Code

**Goal:** Learn how to solve unfamiliar DSA interview problems, not just memorize solutions.

---

# PART 1 — HOW TO SOLVE ANY DSA PROBLEM

Before coding, follow these 7 steps.

## Step 1: Understand the input and output

Ask:
- What input am I receiving?
- What output is expected?
- Do I need one answer or all possible answers?
- Is the input sorted?
- Are duplicates allowed?
- Can the input be empty?
- What are the input size and constraints?

## Step 2: Identify the main goal

| Problem asks for... | Think about... |
|---|---|
| Find a pair or target | HashMap, Two Pointers |
| Longest or shortest continuous section | Sliding Window |
| Find a middle node or cycle | Fast & Slow Pointers |
| Matching brackets or next greater element | Stack |
| Search in sorted data | Binary Search |
| Reverse or rearrange nodes | Linked List |
| Hierarchical data | Trees |
| Top K, smallest K, largest K | Heap / PriorityQueue |
| All possible combinations | Backtracking |
| Connectivity or shortest path | Graphs |
| Maximum/minimum with repeated subproblems | Dynamic Programming |
| Overlapping time ranges | Intervals |
| Fast cache lookup and eviction | HashMap + Doubly Linked List |

## Step 3: Choose the simplest suitable approach

Start with the brute-force idea.

Ask:
- Could I use two loops?
- Could I check every possible answer?
- What makes that approach slow?

Then look for a better data structure or algorithm.

## Step 4: Define the important state

Examples:
- `left`, `right` = current window boundaries
- `map` = values already seen
- `slow`, `fast` = pointers moving at different speeds
- `dp[i]` = answer for a smaller problem
- `current` = partial answer being built
- `visited` = nodes already explored

## Step 5: Write the algorithm in plain English

Example: Find two numbers that add up to a target.

1. Visit each number.
2. Calculate the number needed to reach the target.
3. Check whether that number has already appeared.
4. If yes, return both indices.
5. Otherwise, store the current number and its index.

## Step 6: Dry-run a small example

Use an example with 3–5 elements.

Track:
- Current index or pointers
- Data structure contents
- Condition being checked
- Why a pointer moves or a loop continues

## Step 7: Check complexity and edge cases

Ask:
- How many times can each element be processed?
- Is there nested iteration?
- What extra memory is used?
- What happens for an empty input or one element?

---

# PART 2 — PATTERN SELECTION GUIDE

Use this when you don't know which algorithm to choose.

| If the question mentions... | First pattern to consider |
|---|---|
| Continuous subarray or substring | Sliding Window |
| Sorted array and pair search | Two Pointers |
| Middle, cycle, repeated movement | Fast & Slow |
| Find duplicates or complements | HashMap / HashSet |
| Brackets, undo, next greater element | Stack |
| Sorted search or minimum feasible answer | Binary Search |
| Node reversal, middle, cycle | Linked List |
| Height, path, subtree | Trees |
| Kth largest, top K, repeated min/max | Heap |
| Every subset, permutation, combination | Recursion / Backtracking |
| Connected nodes, dependencies, routes | Graphs |
| Best result across smaller overlapping problems | Dynamic Programming |
| Overlapping ranges or meeting times | Intervals |
| O(1) lookup with least-recently-used eviction | LRU Cache |

IMPORTANT:
These are clues, not strict rules. Some problems combine multiple patterns.

---

# PATTERN 1 — SLIDING WINDOW

## When to recognize it

Look for:
- A continuous subarray or substring
- Maximum or minimum sum of consecutive elements
- Longest valid substring
- Smallest subarray satisfying a condition
- A fixed number of consecutive elements

A window is a continuous section between `left` and `right`.

## A. Fixed-size window

Example: Maximum sum of K consecutive elements.

### Algorithm

1. Calculate the sum of the first K elements.
2. Move the window one position at a time.
3. Subtract the outgoing element.
4. Add the incoming element.
5. Update the maximum.

### Java template

```java
int maxSum(int[] nums, int k) {
    if (k <= 0 || k > nums.length) {
        throw new IllegalArgumentException();
    }

    int sum = 0;

    for (int i = 0; i < k; i++) {
        sum += nums[i];
    }

    int max = sum;

    for (int right = k; right < nums.length; right++) {
        sum += nums[right];
        sum -= nums[right - k];

        max = Math.max(max, sum);
    }

    return max;
}
```

Time: O(n). Extra space: O(1).

## B. Variable-size window

Example: Longest substring without repeating characters.

### Algorithm

1. Expand the window by moving `right`.
2. If the window becomes invalid, move `left` until it is valid.
3. Update the best answer.
4. Continue.

```java
int longestUniqueSubstring(String s) {
    Set<Character> set = new HashSet<>();

    int left = 0;
    int max = 0;

    for (int right = 0; right < s.length(); right++) {
        char ch = s.charAt(right);

        while (set.contains(ch)) {
            set.remove(s.charAt(left));
            left++;
        }

        set.add(ch);

        max = Math.max(max, right - left + 1);
    }

    return max;
}
```

Time: O(n). Space: O(min(n, alphabet size)).

## How to choose `if` versus `while`

- Use `if` when one adjustment is sufficient.
- Use `while` when you must keep adjusting until the condition becomes valid.

For example, removing duplicates from a window may require removing multiple characters.

## How to choose longest versus shortest

- Longest valid window: generally shrink until valid, then update the maximum.
- Shortest valid window: generally shrink while valid, updating the minimum before shrinking.

WARNING: Variable sliding windows do not automatically work for every sum problem. Negative numbers can break the usual expand/shrink logic. Consider prefix sums or other techniques.

---

# PATTERN 2 — TWO POINTERS

## When to recognize it

Look for:
- Sorted arrays
- Pair sums
- Palindrome checking
- Removing duplicates in-place
- Reversing an array
- Comparing values from opposite ends

## A. Opposite-direction pointers

Example: Two Sum in a sorted array.

```java
int[] twoSumSorted(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
        int sum = nums[left] + nums[right];

        if (sum == target) {
            return new int[]{left, right};
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }

    return new int[]{-1, -1};
}
```

Why?
- If the sum is too small, increase the left value.
- If the sum is too large, decrease the right value.

This reasoning depends on the array being sorted.

Time: O(n). Space: O(1).

## B. Read/write pointers

Example: Remove duplicates from a sorted array.

```java
int removeDuplicates(int[] nums) {
    if (nums.length == 0) {
        return 0;
    }

    int write = 1;

    for (int read = 1; read < nums.length; read++) {
        if (nums[read] != nums[write - 1]) {
            nums[write] = nums[read];
            write++;
        }
    }

    return write;
}
```

- `read` examines elements.
- `write` marks where the next valid element belongs.

Time: O(n). Space: O(1).

## How to choose the pointer movement

Ask: Which movement can safely eliminate impossible answers?

Do not move pointers randomly. Explain why the movement is safe.

---

# PATTERN 3 — FAST & SLOW POINTERS

## When to recognize it

Look for:
- Find the middle of a linked list
- Detect a cycle
- Find the cycle entrance
- Repeatedly transform a number until it reaches a state

## A. Find the middle of a linked list

```java
ListNode middleNode(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    return slow;
}
```

Slow moves one step; fast moves two.

For an even-length list, this returns the second middle node.

## B. Detect a cycle

```java
boolean hasCycle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {
            return true;
        }
    }

    return false;
}
```

If a cycle exists, fast eventually catches slow.

Time: O(n). Extra space: O(1).

## C. Find the cycle entrance

1. Find the meeting point using slow and fast.
2. Reset one pointer to the head.
3. Move both pointers one step at a time.
4. Their next meeting point is the cycle entrance.

## Important

Use fast & slow when pointer movement itself reveals structure. Use a HashSet if remembering all previously visited nodes is simpler.

---

# PATTERN 4 — HASHMAP / HASHSET

## When to recognize it

Look for:
- Find duplicates
- Count frequencies
- Find a complement
- Group related values
- Check whether something has appeared before

## A. Two Sum in an unsorted array

```java
int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
        int needed = target - nums[i];

        if (map.containsKey(needed)) {
            return new int[]{map.get(needed), i};
        }

        map.put(nums[i], i);
    }

    return new int[]{-1, -1};
}
```

The map stores numbers already seen and their indices.

Time: O(n) average. Space: O(n).

## B. Count frequencies

```java
Map<Integer, Integer> frequency = new HashMap<>();

for (int num : nums) {
    frequency.merge(num, 1, Integer::sum);
}
```

## HashMap or HashSet?

- HashMap: you need a value associated with a key.
- HashSet: you only need to know whether an element exists.

Common mistake: inserting the current element before checking its complement can accidentally match an element with itself in some implementations.

---

# PATTERN 5 — STACK / MONOTONIC STACK

## When to recognize it

Look for:
- Balanced brackets
- Next greater or smaller element
- Previous greater or smaller element
- Nested structures
- Undo operations
- Remove adjacent duplicates

## A. Valid parentheses

```java
boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (char ch : s.toCharArray()) {
        if (ch == '(' || ch == '[' || ch == '{') {
            stack.push(ch);
        } else {
            if (stack.isEmpty()) {
                return false;
            }

            char top = stack.pop();

            if ((ch == ')' && top != '(')
                    || (ch == ']' && top != '[')
                    || (ch == '}' && top != '{')) {
                return false;
            }
        }
    }

    return stack.isEmpty();
}
```

Time: O(n). Space: O(n).

## B. Next greater element

Use a monotonic stack when you need the next greater or smaller value.

```java
int[] nextGreater(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);

    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty()
                && nums[i] > nums[stack.peek()]) {
            result[stack.pop()] = nums[i];
        }

        stack.push(i);
    }

    return result;
}
```

The stack stores indices of elements whose next greater value has not yet been found.

Time: O(n), because each index is pushed and popped at most once.

---

# PATTERN 6 — BINARY SEARCH

## When to recognize it

Look for:
- Sorted input
- Search for a target
- First or last occurrence
- Lower or upper bound
- Find minimum feasible capacity or maximum feasible value

## A. Standard binary search

```java
int binarySearch(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    return -1;
}
```

Time: O(log n). Space: O(1).

## B. First occurrence

Use binary search but continue searching left after finding the target.

## C. Binary search on the answer

Recognize this when:
- The question asks for the minimum or maximum possible answer.
- You can test whether a candidate answer is feasible.
- Feasibility changes monotonically as the answer increases or decreases.

Example: Minimum capacity needed to ship packages within a deadline.

Search the capacity range. For each capacity, simulate whether the packages can be shipped in time.

IMPORTANT: Binary search is not limited to sorted arrays. It can work on a monotonic feasibility condition.

---

# PATTERN 7 — LINKED LIST

## When to recognize it

Look for:
- Reverse nodes
- Find the middle
- Detect a cycle
- Remove the nth node from the end
- Merge sorted lists
- Find an intersection

## A. Reverse a linked list

```java
ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;

    while (current != null) {
        ListNode next = current.next;

        current.next = prev;
        prev = current;
        current = next;
    }

    return prev;
}
```

Always save `next` before changing `current.next`, or the remaining list may be lost.

Time: O(n). Space: O(1).

## B. Remove nth node from the end

Use a dummy node and two pointers.

1. Move fast n steps ahead.
2. Move slow and fast together until fast reaches the last node.
3. Remove `slow.next`.

A dummy node simplifies removing the head.

## C. Merge two sorted lists

Compare the two current nodes. Attach the smaller one to the result and advance that list.

Use a dummy node to simplify the first insertion.

---

# PATTERN 8 — TREES

## When to recognize it

Look for:
- Height or depth
- Traversal order
- Root-to-leaf paths
- Subtree calculations
- BST search
- Lowest common ancestor
- Maximum path sum

## A. Tree traversal

```java
void preorder(TreeNode root) {
    if (root == null) {
        return;
    }

    process(root);
    preorder(root.left);
    preorder(root.right);
}
```

- Preorder: node, left, right.
- Inorder: left, node, right.
- Postorder: left, right, node.
- BFS: level by level using a Queue.

## B. Maximum depth

```java
int maxDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }

    return 1 + Math.max(
        maxDepth(root.left),
        maxDepth(root.right)
    );
}
```

The parent combines the answers returned by its children.

## C. Choose the traversal

- Need sorted values from a BST: inorder.
- Need child results before processing the parent: postorder.
- Need parent processed before children: preorder.
- Need level-by-level processing: BFS.

## D. Advanced tree questions

- Balanced tree: return height or failure.
- Validate BST: pass lower and upper bounds.
- Kth smallest BST value: inorder with a counter.
- Lowest common ancestor: recursive DFS.
- Maximum path sum: DFS returns one branch while a global answer can use both branches.

---

# PATTERN 9 — HEAP / PRIORITYQUEUE

## When to recognize it

Look for:
- Kth largest or smallest
- Top K frequent elements
- Repeatedly get the minimum or maximum
- Merge K sorted lists
- Running median

## A. Kth largest element

Maintain a min-heap of size K.

```java
int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();

    for (int num : nums) {
        heap.offer(num);

        if (heap.size() > k) {
            heap.poll();
        }
    }

    return heap.peek();
}
```

Why a min-heap?

The smallest of the K largest candidates is at the root. That value is the kth largest.

Time: O(n log k). Space: O(k).

## Heap memory trick

- Min-heap: smallest element first.
- Max-heap: largest element first.

Java max-heap:

```java
PriorityQueue<Integer> maxHeap =
    new PriorityQueue<>(Comparator.reverseOrder());
```

Use a heap when you repeatedly need the next best candidate without sorting everything.

---

# PATTERN 10 — RECURSION / BACKTRACKING

## When to recognize it

Look for:
- Generate all subsets or subsequences
- Generate permutations
- Combination Sum
- Generate valid parentheses
- Solve N-Queens
- Explore all valid paths

## The basic recursion questions

1. What does one method call represent?
2. What is the base case?
3. How does the problem get smaller?

For backtracking, also ask:
4. What choices can I make?
5. How do I undo a choice?

## A. Generate subsequences

Each character has two choices: include or exclude.

```java
void subsequences(String s, int index, String current) {
    if (index == s.length()) {
        System.out.println(current);
        return;
    }

    // Exclude
    subsequences(s, index + 1, current);

    // Include
    subsequences(
        s,
        index + 1,
        current + s.charAt(index)
    );
}
```

For n characters, there are 2^n subsequences.

## B. Backtracking template

```java
void backtrack(State state) {
    if (isComplete(state)) {
        saveSolution(state);
        return;
    }

    for (Choice choice : availableChoices(state)) {
        if (!isValid(choice, state)) {
            continue;
        }

        makeChoice(choice, state);

        backtrack(state);

        undoChoice(choice, state);
    }
}
```

- Include/exclude each element: subsets.
- Choose unused elements: permutations.
- Choose combinations without reordering: use a `start` index.
- Reject invalid branches early: pruning.

When saving a mutable list, save a copy:

```java
result.add(new ArrayList<>(current));
```

---

# PATTERN 11 — GRAPHS

## When to recognize it

Look for:
- Connections between nodes
- Connected components
- Islands
- Shortest paths
- Cycles
- Task dependencies

## A. DFS

```java
void dfs(
        int node,
        List<List<Integer>> graph,
        boolean[] visited) {

    visited[node] = true;

    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, graph, visited);
        }
    }
}
```

Use DFS to explore reachable nodes and connected regions.

## B. BFS

Use a Queue for level-order exploration or shortest paths in an unweighted graph.

Mark nodes visited when adding them to the Queue.

## C. Choose the algorithm

- Connected components: DFS or BFS.
- Unweighted shortest path: BFS.
- Weighted shortest path with nonnegative weights: Dijkstra.
- Task dependencies: Topological sort.
- Repeated connectivity checks: Union-Find / DSU.
- Directed cycle: DFS states or topological sort.

Time for DFS/BFS using an adjacency list: O(V + E).

---

# PATTERN 12 — DYNAMIC PROGRAMMING

## When to recognize it

Look for:
- Maximum or minimum result from smaller decisions
- Number of ways to reach a state
- Take/skip decisions
- Repeated recursive subproblems
- String comparisons or grid paths

## Five steps for every DP problem

1. State: What does `dp[i]` mean?
2. Transition: How do smaller states produce this state?
3. Base cases: What answers are already known?
4. Order: Which states must be calculated first?
5. Result: Where is the requested answer stored?

## A. House Robber

Cannot rob adjacent houses. Maximize the total.

At each house:
- Skip it: keep the previous best.
- Take it: add its value to the best from two positions earlier.

```java
int rob(int[] nums) {
    int prev2 = 0;
    int prev1 = 0;

    for (int value : nums) {
        int current = Math.max(
            prev1,
            prev2 + value
        );

        prev2 = prev1;
        prev1 = current;
    }

    return prev1;
}
```

Time: O(n). Space: O(1).

## B. Other common DP families

| Problem | State idea |
|---|---|
| Climbing Stairs | Ways to reach step i |
| Coin Change | Minimum coins for amount i |
| 0/1 Knapsack | Best value for capacity c |
| Unique Paths | Ways to reach grid cell |
| LCS | Best common subsequence for two prefixes |
| Subset Sum | Whether a target is reachable |
| Longest Increasing Subsequence | Best subsequence ending at a position |

Do not start coding before defining what the DP state means.

---

# PATTERN 13 — INTERVALS

## When to recognize it

Look for:
- Start and end times
- Overlapping ranges
- Meeting rooms
- Scheduling
- Minimum removals
- Combining ranges

## A. Merge intervals

```java
int[][] merge(int[][] intervals) {
    if (intervals.length == 0) {
        return new int[0][];
    }

    Arrays.sort(
        intervals,
        Comparator.comparingInt(a -> a[0])
    );

    List<int[]> result = new ArrayList<>();

    int start = intervals[0][0];
    int end = intervals[0][1];

    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] <= end) {
            end = Math.max(end, intervals[i][1]);
        } else {
            result.add(new int[]{start, end});

            start = intervals[i][0];
            end = intervals[i][1];
        }
    }

    result.add(new int[]{start, end});

    return result.toArray(new int[result.size()][]);
}
```

Time: O(n log n), dominated by sorting.

## B. Choose the interval technique

- Merge overlapping intervals: sort by start.
- Keep maximum non-overlapping intervals: greedy by end.
- Minimum meeting rooms: min-heap or sweep line.
- Count concurrent events: sweep line.

Important: Check whether intervals are closed or half-open. Equal endpoints can change the overlap condition.

---

# PATTERN 14 — LRU CACHE

## When to recognize it

The question requires:
- `get(key)`
- `put(key, value)`
- Fixed capacity
- Least-recently-used eviction
- O(1) average lookup and update

## The design

Use two data structures:

1. HashMap: key → node.
2. Doubly linked list: most recent at the front, least recent at the back.

## Get

1. Find the key in the map.
2. If absent, return the required missing-key value.
3. Move the node to the front.
4. Return its value.

## Put

1. If the key exists, update it and move it to the front.
2. Otherwise, insert a new node at the front.
3. If capacity is exceeded, remove the node at the back.
4. Remove the evicted key from the map.

Why a doubly linked list?

It allows an arbitrary node to be removed in O(1) when you already have its reference.

## Complexity

- Get: O(1) average.
- Put: O(1) average.
- Space: O(capacity).

Important: A normal Queue alone is not enough because an arbitrary accessed element must move to the front efficiently.

---

# PART 3 — HOW TO CHANGE AN ALGORITHM FOR VARIATIONS

When the interviewer changes the question, identify exactly what changed.

| Change in requirement | What to reconsider |
|---|---|
| Sorted array becomes unsorted | Two Pointers may become HashMap |
| Need longest instead of shortest | Change when the best answer is updated |
| Need all answers instead of one | Backtracking or collect multiple results |
| Need minimum number of steps | BFS for unweighted graphs; DP for many optimization problems |
| Need weighted shortest path | Dijkstra or another weighted-path algorithm |
| Need the kth largest value | Heap |
| Need frequent min/max access | Heap |
| Need to return paths, not just a count | Store parent/path information |
| Need to avoid duplicate combinations | Sort, use `start`, or skip duplicate choices |
| Items may be reused | Change recursive index or DP loop direction |
| Need O(1) cache operations | Combine a HashMap with a doubly linked list |
| Input becomes very large | Reassess time and space complexity |

Never change code blindly. First identify which assumption or requirement has changed.

---

# PART 4 — COMMON COMPLEXITY GUIDE

| Complexity | Typical meaning |
|---|---|
| O(1) | Constant-time operation |
| O(log n) | Binary search or heap operation |
| O(n) | One pass through input |
| O(n log n) | Sorting or processing all elements with a heap |
| O(n²) | Two nested loops over input |
| O(2^n) | Enumerating subsets |
| O(n!) | Enumerating permutations |

These are common patterns, not universal guarantees. Always analyze the actual algorithm.

---

# PART 5 — COMMON MISTAKES TO AVOID

1. Choosing a pattern from a keyword without understanding the requirement.
2. Coding before explaining the algorithm.
3. Forgetting empty input and boundary conditions.
4. Moving pointers without explaining why the movement is safe.
5. Using BFS for arbitrary weighted shortest paths.
6. Using binary search without a sorted structure or monotonic condition.
7. Forgetting the base case in recursion.
8. Forgetting to undo changes in backtracking.
9. Confusing subsets, combinations, and permutations.
10. Defining DP transitions without defining the state.
11. Forgetting that a HashMap is O(1) on average.
12. Ignoring integer overflow.
13. Forgetting that recursive algorithms use stack space.
14. Returning a result in the wrong format.
15. Memorizing code without understanding its invariant.

---

# PART 6 — THE INTERVIEW PROBLEM-SOLVING TEMPLATE

Use this checklist for every new question.

## 1. Clarify

- What exactly must be returned?
- Can there be duplicates?
- Is the input sorted?
- What are the constraints?
- Are there negative values?
- Are empty inputs possible?

## 2. Recognize

- Is it a continuous range?
- Is it sorted?
- Do I need to remember previously seen values?
- Is it a tree or graph?
- Must I enumerate all choices?
- Are there repeated subproblems?
- Are intervals involved?
- Must operations be O(1)?

## 3. Plan

Explain the approach in 3–5 sentences.

## 4. Code

Use meaningful variable names and handle the base cases.

## 5. Dry-run

Trace the smallest useful example.

## 6. Test

Check:
- Empty input
- One element
- Duplicate values
- No solution
- Minimum and maximum constraints

## 7. Analyze

Explain:
- Time complexity
- Auxiliary space
- Why the chosen algorithm is correct
- What trade-off an alternative approach would have

---

# PART 7 — PRACTICE ROADMAP

Study in this order. Complete a few problems from one pattern before moving to the next.

## Level 1: Build confidence

1. Two Sum
2. Maximum Sum Subarray of Size K
3. Longest Substring Without Repeating Characters
4. Reverse Linked List
5. Binary Search
6. Valid Parentheses
7. Maximum Depth of Binary Tree

## Level 2: Learn variations

8. Remove Duplicates from Sorted Array
9. Linked List Cycle
10. Kth Largest Element
11. Merge Intervals
12. Number of Islands
13. Climbing Stairs
14. Generate Subsets
15. Level Order Traversal

## Level 3: Interview-level patterns

16. Minimum Window Substring
17. Search in Rotated Sorted Array
18. Lowest Common Ancestor
19. Course Schedule
20. Coin Change
21. Combination Sum
22. Meeting Rooms II
23. LRU Cache

## Level 4: Advanced

24. Sliding Window Maximum
25. Trapping Rain Water
26. Word Search
27. Longest Common Subsequence
28. Binary Tree Maximum Path Sum
29. Network Delay Time
30. 0/1 Knapsack

---

# FINAL MEMORY RULE

For every problem, remember:

**Recognize → Define the state → Choose the data structure → Explain the algorithm → Code → Dry-run → Analyze.**

You do not need to memorize every solution. You need to understand:
- Why the pattern fits.
- What information the algorithm maintains.
- Why each pointer, loop, or recursive call moves.
- What changes when the problem requirements change.
