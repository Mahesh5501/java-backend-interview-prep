
# DSA CHEAT SHEET — GRAPHS, DYNAMIC PROGRAMMING, INTERVALS & LRU CACHE

## Recognition → Algorithm → Variations → Java Templates → Interview Problems

---

# PART 1 — GRAPHS

## 1. What is a graph?

A graph consists of:
- Vertices (nodes)
- Edges (connections between nodes)

Example:

    0 ---- 1
    |      |
    2 ---- 3

Graphs can represent:
- Cities connected by roads
- Services communicating with other services
- Social network connections
- Tasks with dependencies
- Grid cells connected to neighboring cells

## 2. How to recognize a graph problem

| Problem wording | Likely technique |
|---|---|
| Is there a path between two nodes? | DFS / BFS |
| Count connected groups | DFS / BFS |
| Count islands in a grid | Grid DFS / BFS |
| Find shortest path in an unweighted graph | BFS |
| Find shortest path with nonnegative weights | Dijkstra |
| Detect a cycle in an undirected graph | DFS / BFS with parent, or DSU |
| Detect a cycle in a directed graph | DFS states or topological sort |
| Can all tasks be completed? | Topological sort / cycle detection |
| Find a valid task execution order | Topological sort |
| Connect all nodes with minimum total edge cost | Minimum Spanning Tree |
| Repeatedly merge connected groups | Union-Find / DSU |

## 3. Graph decision tree

START: What is the question asking?

1. Visit reachable nodes or count groups?
   -> DFS or BFS.

2. Shortest path?
   - All edges have equal cost -> BFS.
   - Weighted edges, all weights nonnegative -> Dijkstra.
   - Negative weights -> Consider Bellman-Ford, depending on constraints.
   - Directed acyclic graph (DAG) -> Consider topological-order shortest paths.

3. Is there a cycle?
   - Undirected graph -> DFS with parent or Union-Find.
   - Directed graph -> DFS states or topological sort.

4. Are tasks dependent on other tasks?
   -> Topological sort.

5. Do you repeatedly need to combine groups or check connectivity?
   -> Union-Find / DSU.

IMPORTANT:
BFS does not automatically solve every shortest-path problem. It finds shortest paths by edge count when all edges have equal cost.

---

## 4. Representing a Graph in Java

An adjacency list stores the neighbors of each node.

For this graph:

    0 -- 1
    |
    2

The adjacency list is:

    0 -> [1, 2]
    1 -> [0]
    2 -> [0]

Java:

```java
List<List<Integer>> graph = new ArrayList<>();

int n = 3;

for (int i = 0; i < n; i++) {
    graph.add(new ArrayList<>());
}

// Undirected edge between 0 and 1
graph.get(0).add(1);
graph.get(1).add(0);

// Undirected edge between 0 and 2
graph.get(0).add(2);
graph.get(2).add(0);
```

For a directed edge `u -> v`, add `v` to `graph.get(u)` only.

For a weighted graph, each neighbor entry must also store its weight.

---

## 5. DFS — Explore a Graph

### Recognition

Use DFS when you need to:
- Visit all nodes reachable from a starting node
- Count connected components
- Explore a maze
- Detect certain cycles
- Traverse a graph without needing shortest distance

### Java template

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

### Why use visited?

Graphs can contain cycles.

Without `visited`, this graph could recurse indefinitely:

    0 -- 1
    |    |
    3 -- 2

### Complexity

With an adjacency list:
- Time: O(V + E)
- Space: O(V) for visited and, in recursive DFS, up to O(V) call-stack space.

V = number of vertices; E = number of edges.

### Common mistake

For disconnected graphs, starting DFS from node 0 does not visit every component.

To visit the entire graph:

```java
for (int i = 0; i < n; i++) {
    if (!visited[i]) {
        dfs(i, graph, visited);
    }
}
```

---

## 6. BFS — Shortest Path in an Unweighted Graph

### Recognition

Use BFS when you need the minimum number of edges between nodes in an unweighted graph.

### Java template

```java
int[] shortestDistances(
        List<List<Integer>> graph,
        int source) {

    int n = graph.size();
    int[] distance = new int[n];
    Arrays.fill(distance, -1);

    Queue<Integer> queue = new LinkedList<>();

    queue.offer(source);
    distance[source] = 0;

    while (!queue.isEmpty()) {
        int node = queue.poll();

        for (int neighbor : graph.get(node)) {
            if (distance[neighbor] == -1) {
                distance[neighbor] = distance[node] + 1;
                queue.offer(neighbor);
            }
        }
    }

    return distance;
}
```

### Why mark a node when enqueuing it?

A node should be discovered and assigned its distance once.

Marking it when adding it to the queue prevents multiple nodes from repeatedly enqueueing the same neighbor.

### Complexity

- Time: O(V + E)
- Space: O(V)

Unreachable nodes retain distance `-1`.

---

## 7. Number of Islands — Grid DFS / BFS

### Recognition

The input is a grid, and you need to count connected groups of cells.

Example:

    1 1 0
    1 0 0
    0 0 1

If only horizontal and vertical neighbors count, there are two islands.

### Algorithm

1. Visit every cell.
2. When an unvisited land cell is found, increment the island count.
3. Run DFS or BFS to mark every cell in that island.
4. Continue scanning.

### Java template

```java
int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) {
        return 0;
    }

    int rows = grid.length;
    int cols = grid[0].length;
    int count = 0;

    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == '1') {
                count++;
                sink(grid, r, c);
            }
        }
    }

    return count;
}

void sink(char[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length
            || c < 0 || c >= grid[0].length
            || grid[r][c] != '1') {
        return;
    }

    grid[r][c] = '0';

    sink(grid, r + 1, c);
    sink(grid, r - 1, c);
    sink(grid, r, c + 1);
    sink(grid, r, c - 1);
}
```

This version modifies the input grid to mark visited land.

If the input must remain unchanged, use a separate `visited` array.

### Complexity

For R rows and C columns:
- Time: O(R × C)
- Space: O(R × C) in the worst case for recursion/visited state.

---

## 8. Detect a Cycle in a Directed Graph

### Recognition

Examples:
- Can tasks depend on one another in a circular way?
- Is there a dependency cycle?
- Can a directed graph be topologically sorted?

Use DFS states:
- 0 = unvisited
- 1 = currently in the recursion path
- 2 = fully processed

Finding an edge to a node with state 1 indicates a cycle.

```java
boolean hasCycle(
        int node,
        List<List<Integer>> graph,
        int[] state) {

    state[node] = 1;

    for (int neighbor : graph.get(node)) {
        if (state[neighbor] == 1) {
            return true;
        }

        if (state[neighbor] == 0
                && hasCycle(neighbor, graph, state)) {
            return true;
        }
    }

    state[node] = 2;
    return false;
}
```

Check every node to cover disconnected components.

Complexity: O(V + E).

---

## 9. Topological Sort — Task Scheduling

### Recognition

Use topological sort when:
- Tasks have prerequisites.
- A course must be completed before another.
- You need an order that respects directed dependencies.

A topological ordering exists only if the directed graph has no cycle.

### Kahn's algorithm

1. Calculate each node's indegree (number of incoming edges).
2. Put all nodes with indegree zero into a queue.
3. Remove a node and add it to the result.
4. Decrease the indegree of its neighbors.
5. Add neighbors whose indegree becomes zero.
6. If fewer than V nodes are processed, a cycle exists.

```java
List<Integer> topologicalSort(
        List<List<Integer>> graph) {

    int n = graph.size();
    int[] indegree = new int[n];

    for (int node = 0; node < n; node++) {
        for (int neighbor : graph.get(node)) {
            indegree[neighbor]++;
        }
    }

    Queue<Integer> queue = new LinkedList<>();

    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);
        }
    }

    List<Integer> order = new ArrayList<>();

    while (!queue.isEmpty()) {
        int node = queue.poll();
        order.add(node);

        for (int neighbor : graph.get(node)) {
            indegree[neighbor]--;

            if (indegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }

    if (order.size() != n) {
        return new ArrayList<>(); // Cycle exists
    }

    return order;
}
```

Complexity: O(V + E).

---

## 10. Dijkstra — Weighted Shortest Path

### Recognition

The graph has weighted edges, and all edge weights are nonnegative.

Use Dijkstra to find shortest distances from a source.

Core idea:
- Maintain the best known distance to each node.
- Always process the currently closest candidate.
- Relax outgoing edges if a shorter distance is found.

Use a min-priority queue ordered by distance.

Important: In Java, use a `long` distance if path sums may exceed `int`.

Complexity with an adjacency list and binary heap:
- Common bound: O((V + E) log V)
- Space: O(V + E)

Do not use ordinary BFS for arbitrary weighted shortest paths.

---

## 11. Union-Find / Disjoint Set Union (DSU)

### Recognition

Use DSU when the problem repeatedly asks:
- Are two nodes in the same connected group?
- How many connected components remain?
- Does adding an edge connect nodes already in the same component?

```java
class DSU {
    int[] parent;
    int[] size;

    DSU(int n) {
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }

    boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return false;
        }

        if (size[rootA] < size[rootB]) {
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }

        parent[rootB] = rootA;
        size[rootA] += size[rootB];

        return true;
    }
}
```

Path compression and union by size make operations nearly constant time in practice.

---

# PART 2 — DYNAMIC PROGRAMMING (DP)

## 12. What is Dynamic Programming?

Dynamic Programming solves problems by reusing answers to smaller subproblems.

It is useful when:
1. The problem can be broken into smaller subproblems.
2. The same subproblems are encountered repeatedly, or an optimization/counting recurrence can be efficiently reused.

Two common approaches:

- Memoization: recursion + cache.
- Tabulation: fill a table iteratively.

## 13. How to recognize DP

| Problem clue | Possible DP pattern |
|---|---|
| Number of ways to reach a position | Counting DP |
| Maximum/minimum score or cost | Optimization DP |
| Can a target sum be formed? | Boolean / subset DP |
| Best result using first i elements | One-dimensional DP |
| Choices include take or skip | Knapsack-style DP |
| Compare two strings | Two-dimensional DP |
| Longest common subsequence | String DP |
| Longest increasing subsequence | Sequence DP |
| Grid paths with obstacles | Grid DP |
| Choices depend on the previous few states | State-transition DP |

DP is not the right answer for every problem that contains recursion. If subproblems do not repeat and the search space is small, ordinary recursion or backtracking may be enough.

---

## 14. The Five Steps to Build a DP Solution

Always identify:

1. State: What does `dp[i]` or `dp[i][j]` mean?
2. Transition: How is the state calculated from smaller states?
3. Base cases: What are the smallest known answers?
4. Evaluation order: Which states must be computed first?
5. Final answer: Which state contains the requested result?

If you cannot explain what `dp[i]` means in one sentence, do not start coding yet.

---

## 15. Fibonacci — Recursion to DP

Recurrence:

F(n) = F(n - 1) + F(n - 2)

Base cases:

F(0) = 0
F(1) = 1

### Recursive version

```java
int fib(int n) {
    if (n <= 1) {
        return n;
    }

    return fib(n - 1) + fib(n - 2);
}
```

This repeats work and takes exponential time.

### Memoization

```java
int fib(int n, int[] memo) {
    if (n <= 1) {
        return n;
    }

    if (memo[n] != -1) {
        return memo[n];
    }

    memo[n] = fib(n - 1, memo)
            + fib(n - 2, memo);

    return memo[n];
}
```

Initialize `memo` to -1 before calling.

Time: O(n).
Space: O(n), including the cache and recursion stack.

### Tabulation

```java
int fib(int n) {
    if (n <= 1) {
        return n;
    }

    int[] dp = new int[n + 1];

    dp[0] = 0;
    dp[1] = 1;

    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }

    return dp[n];
}
```

### Space optimization

Only the previous two values are needed:

```java
int fib(int n) {
    if (n <= 1) {
        return n;
    }

    int prev2 = 0;
    int prev1 = 1;

    for (int i = 2; i <= n; i++) {
        int current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }

    return prev1;
}
```

Time: O(n); auxiliary space: O(1).

---

## 16. Climbing Stairs

### Recognition

You can climb 1 or 2 steps at a time. How many distinct ways can you reach step n?

### State

`dp[i]` = number of ways to reach step i.

### Transition

To reach step i, you came from:
- Step i - 1
- Step i - 2

Therefore:

`dp[i] = dp[i - 1] + dp[i - 2]`

### Java

```java
int climbStairs(int n) {
    if (n <= 2) {
        return n;
    }

    int oneStepBefore = 2;
    int twoStepsBefore = 1;

    for (int i = 3; i <= n; i++) {
        int current = oneStepBefore + twoStepsBefore;

        twoStepsBefore = oneStepBefore;
        oneStepBefore = current;
    }

    return oneStepBefore;
}
```

Time: O(n).
Space: O(1).

Always check the problem's constraints: the number of ways can overflow `int` for sufficiently large n.

---

## 17. House Robber — Take or Skip

### Recognition

You cannot choose adjacent elements, and you want the maximum total.

### State

`dp[i]` = maximum money obtainable from the first i houses.

For each house, choose:

- Skip it: `dp[i - 1]`
- Take it: `dp[i - 2] + nums[i - 1]`

Transition:

`dp[i] = max(dp[i - 1], dp[i - 2] + nums[i - 1])`

### Java

```java
int rob(int[] nums) {
    int prev2 = 0;
    int prev1 = 0;

    for (int money : nums) {
        int current = Math.max(
            prev1,
            prev2 + money
        );

        prev2 = prev1;
        prev1 = current;
    }

    return prev1;
}
```

Time: O(n).
Space: O(1).

### Recognition trick

If each item has a take/skip decision and taking an item affects which future items are allowed, consider a DP state that records the best answer up to the current position.

---

## 18. Coin Change — Minimum Number of Coins

### Recognition

Given coin denominations and a target amount, find the minimum number of coins needed.

### State

`dp[a]` = minimum coins needed to make amount a.

### Transition

For each coin c that can be used:

`dp[a] = min(dp[a], dp[a - c] + 1)`

### Java

```java
int coinChange(int[] coins, int amount) {
    int impossible = amount + 1;

    int[] dp = new int[amount + 1];
    Arrays.fill(dp, impossible);

    dp[0] = 0;

    for (int a = 1; a <= amount; a++) {
        for (int coin : coins) {
            if (coin <= a) {
                dp[a] = Math.min(
                    dp[a],
                    dp[a - coin] + 1
                );
            }
        }
    }

    return dp[amount] == impossible
        ? -1
        : dp[amount];
}
```

Time: O(amount × number of coin denominations).
Space: O(amount).

Assume all denominations are positive.

### Important distinction

- Minimum number of coins: optimization DP.
- Number of ways to make the amount: counting DP.
- Can the amount be formed? Boolean DP.

These are related but have different state meanings and transitions.

---

## 19. 0/1 Knapsack — Each Item Used At Most Once

### Recognition

Each item has a weight and value. Find the maximum value without exceeding capacity.

### State

`dp[c]` = maximum value achievable with capacity c using the items processed so far.

### One-dimensional Java implementation

```java
int knapsack(
        int[] weights,
        int[] values,
        int capacity) {

    int[] dp = new int[capacity + 1];

    for (int i = 0; i < weights.length; i++) {
        for (int c = capacity; c >= weights[i]; c--) {
            dp[c] = Math.max(
                dp[c],
                dp[c - weights[i]] + values[i]
            );
        }
    }

    return dp[capacity];
}
```

### Why loop backward?

Each item can be used at most once.

Backward iteration prevents the current item from being reused multiple times during the same item iteration.

For unbounded knapsack, where items can be reused, forward capacity iteration is commonly used instead.

Time: O(n × capacity).
Space: O(capacity).

---

## 20. Grid DP — Unique Paths

### Recognition

Count paths from the top-left to the bottom-right, moving only right or down.

### State

`dp[r][c]` = number of ways to reach cell (r, c).

### Transition

`dp[r][c] = dp[r - 1][c] + dp[r][c - 1]`

Each path comes from above or from the left.

### Java

```java
int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];

    for (int r = 0; r < m; r++) {
        dp[r][0] = 1;
    }

    for (int c = 0; c < n; c++) {
        dp[0][c] = 1;
    }

    for (int r = 1; r < m; r++) {
        for (int c = 1; c < n; c++) {
            dp[r][c] =
                dp[r - 1][c] + dp[r][c - 1];
        }
    }

    return dp[m - 1][n - 1];
}
```

Time: O(m × n).
Space: O(m × n).

If the grid contains obstacles, blocked cells must have zero ways.

---

## 21. Longest Common Subsequence (LCS)

### Recognition

Given two strings, find the longest sequence of characters appearing in both strings in the same relative order. Characters do not need to be adjacent.

### State

`dp[i][j]` = LCS length between the first i characters of string A and the first j characters of string B.

### Transition

If the last characters match:

`dp[i][j] = 1 + dp[i - 1][j - 1]`

Otherwise:

`dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])`

### Java

```java
int longestCommonSubsequence(String a, String b) {
    int m = a.length();
    int n = b.length();

    int[][] dp = new int[m + 1][n + 1];

    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                dp[i][j] = 1 + dp[i - 1][j - 1];
            } else {
                dp[i][j] = Math.max(
                    dp[i - 1][j],
                    dp[i][j - 1]
                );
            }
        }
    }

    return dp[m][n];
}
```

Time: O(m × n).
Space: O(m × n).

---

## 22. DP Mistakes

1. Defining `dp[i]` without saying what it means.
2. Writing a transition before defining the state.
3. Missing base cases.
4. Filling the table in the wrong order.
5. Confusing minimum cost, maximum score, number of ways, and possibility.
6. Using the wrong loop direction for 0/1 versus unbounded knapsack.
7. Forgetting integer overflow.
8. Using DP where a simpler greedy or graph algorithm solves the problem.
9. Ignoring repeated subproblems in an exponential recursive solution.
10. Forgetting to account for the space used by the table or recursion stack.

## 23. DP Recognition Summary

| Problem | First technique to consider |
|---|---|
| Fibonacci | 1D DP |
| Climbing stairs | 1D counting DP |
| House Robber | Take/skip DP |
| Coin Change | Minimum/optimization DP |
| 0/1 Knapsack | Capacity DP |
| Unique Paths | Grid DP |
| LCS | 2D string DP |
| Can a subset reach a target? | Subset-sum DP |
| Longest increasing subsequence | Sequence DP |
| Same states recalculated recursively | Memoization |

---

# PART 3 — INTERVALS

## 24. What is an Interval?

An interval is a range represented by a start and an end.

Examples:

[1, 3]
[2, 6]
[8, 10]

Interval questions often involve:
- Overlapping ranges
- Combining ranges
- Scheduling
- Removing conflicting ranges
- Finding the minimum number of resources needed

## 25. How to recognize an interval problem

| Problem wording | Likely technique |
|---|---|
| Merge overlapping intervals | Sort by start + merge |
| Insert a new interval | Scan and merge |
| Remove the minimum number of intervals | Greedy by end time |
| Minimum meeting rooms | Min-heap by end time or sweep line |
| Check if a person can attend all meetings | Sort and check overlap |
| Maximum number of non-overlapping intervals | Greedy by end time |
| Count simultaneous events | Sweep line / events |
| Query many points against ranges | Depends on constraints; sorting, binary search, or interval data structure |

## 26. The most important overlap rule

For closed intervals [a, b] and [c, d], assuming a <= c:

They overlap or touch when:

`c <= b`

They are strictly separated when:

`c > b`

If the problem treats intervals as half-open, such as [a, b), touching endpoints may not overlap. Read the problem's definition carefully.

---

## 27. Merge Intervals

### Recognition

"Merge all overlapping intervals."

Example:

Input:
[[1,3], [2,6], [8,10], [9,12]]

Output:
[[1,6], [8,12]]

### Algorithm

1. Sort intervals by start.
2. Keep the current merged interval.
3. If the next interval overlaps, extend the current end.
4. Otherwise, save the current interval and start a new one.

### Java

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
        int nextStart = intervals[i][0];
        int nextEnd = intervals[i][1];

        if (nextStart <= end) {
            end = Math.max(end, nextEnd);
        } else {
            result.add(new int[]{start, end});

            start = nextStart;
            end = nextEnd;
        }
    }

    result.add(new int[]{start, end});

    return result.toArray(new int[result.size()][]);
}
```

### Complexity

- Time: O(n log n), dominated by sorting.
- Auxiliary space: O(n) for the result, excluding sorting implementation details.

### Common mistake

Do not set `end = nextEnd` blindly when merging. A shorter interval nested inside the current interval must not shrink the merged range.

Use:

`end = Math.max(end, nextEnd)`

---

## 28. Insert Interval

### Recognition

The intervals are already sorted and non-overlapping. Insert a new interval and merge if necessary.

### Algorithm

1. Add intervals entirely before the new interval.
2. Merge every interval that overlaps the new interval.
3. Add the merged interval.
4. Add the remaining intervals.

### Java

```java
int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0;
    int n = intervals.length;

    while (i < n
            && intervals[i][1] < newInterval[0]) {
        result.add(intervals[i]);
        i++;
    }

    while (i < n
            && intervals[i][0] <= newInterval[1]) {

        newInterval[0] =
            Math.min(newInterval[0], intervals[i][0]);

        newInterval[1] =
            Math.max(newInterval[1], intervals[i][1]);

        i++;
    }

    result.add(newInterval);

    while (i < n) {
        result.add(intervals[i]);
        i++;
    }

    return result.toArray(new int[result.size()][]);
}
```

This uses the closed-interval overlap convention and assumes the original intervals are sorted and non-overlapping.

Time: O(n).
Space: O(n) for the output.

---

## 29. Non-overlapping Intervals — Minimum Removals

### Recognition

"Remove the minimum number of intervals so that the remaining intervals do not overlap."

Equivalent goal: Keep the maximum possible number of non-overlapping intervals.

### Greedy idea

Sort by end time and keep the interval that finishes earliest.

Why?

Finishing earlier leaves more room for future intervals.

### Java

```java
int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(
        intervals,
        Comparator.comparingInt(a -> a[1])
    );

    int removed = 0;
    int end = Integer.MIN_VALUE;

    for (int[] interval : intervals) {
        if (interval[0] < end) {
            removed++;
        } else {
            end = interval[1];
        }
    }

    return removed;
}
```

This version treats intervals that touch at an endpoint as non-overlapping.

Time: O(n log n).
Space: depends on the sorting implementation.

---

## 30. Meeting Rooms II — Minimum Number of Rooms

### Recognition

"What is the minimum number of rooms required for all meetings?"

The answer is the maximum number of meetings active at the same time.

### Min-heap strategy

1. Sort meetings by start time.
2. Maintain a min-heap of meeting end times.
3. If the earliest-ending meeting finishes before or at the next meeting's start, reuse that room.
4. Add the current meeting's end time.
5. The heap size represents the rooms currently in use.

```java
int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) {
        return 0;
    }

    Arrays.sort(
        intervals,
        Comparator.comparingInt(a -> a[0])
    );

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    for (int[] meeting : intervals) {
        if (!minHeap.isEmpty()
                && minHeap.peek() <= meeting[0]) {
            minHeap.poll();
        }

        minHeap.offer(meeting[1]);
    }

    return minHeap.size();
}
```

This assumes a meeting ending at time t frees the room for another meeting starting at time t.

Time: O(n log n).
Space: O(n).

---

## 31. Sweep Line — Count Overlapping Events

### Recognition

Questions like:
- How many events are active at once?
- What is the maximum concurrent load?
- How many bookings overlap at any time?

Create events for interval starts and ends.

For closed intervals, tie handling matters. For half-open intervals [start, end), process end events before start events at the same timestamp when counting simultaneous occupancy.

Alternative: Sort starts and ends separately and move through them in time order.

### Important lesson

The interval definition determines how equal endpoints are treated. A single `<` versus `<=` can change the result.

---

## 32. Interval Recognition Summary

| Problem | Main technique |
|---|---|
| Merge ranges | Sort by start |
| Insert a range | Scan before / overlap / after |
| Minimum removals | Greedy by end |
| Maximum non-overlapping intervals | Greedy by end |
| Minimum meeting rooms | Min-heap or sweep line |
| Maximum concurrent events | Sweep line |
| Repeated interval queries | Binary search or specialized structure, depending on the query |

---

# PART 4 — LRU CACHE

## 33. What Is an LRU Cache?

LRU means Least Recently Used.

A cache stores recently accessed values so they can be retrieved quickly.

When the cache is full and a new key must be inserted, remove the key that has not been used for the longest time.

Example with capacity 2:

1. put(1, 10) -> {1}
2. put(2, 20) -> {1, 2}
3. get(1) -> 10; key 1 becomes most recently used
4. put(3, 30) -> evict key 2

Why key 2?

Key 1 was accessed more recently. Key 2 is now the least recently used.

## 34. Recognition clues

The problem typically asks for:
- `get(key)`
- `put(key, value)`
- Fixed capacity
- Evict least recently used
- O(1) average time for get and put

A standard implementation combines:

1. HashMap: locate a node by key in O(1) average time.
2. Doubly linked list: move nodes and remove the least recently used node in O(1).

A normal queue is insufficient for this design because moving an arbitrary item to the front would require searching or additional work.

---

## 35. LRU Design

Maintain a doubly linked list:

    Most recent                       Least recent
        HEAD <-> A <-> B <-> C <-> TAIL

Use dummy head and tail nodes:
- The node immediately after head is most recently used.
- The node immediately before tail is least recently used.

The HashMap maps:

`key -> linked-list node`

### Get operation

1. If the key is absent, return -1.
2. If present, move the node to the front.
3. Return its value.

### Put operation

1. If the key already exists, update its value and move it to the front.
2. Otherwise, create a new node and add it to the front.
3. If capacity is exceeded, remove the node before tail.
4. Remove the evicted key from the HashMap.

Both operations take O(1) average time.

---

## 36. Complete Java LRU Cache

```java
import java.util.HashMap;
import java.util.Map;

class LRUCache {

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;

    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        head = new Node(-1, -1);
        tail = new Node(-1, -1);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);

        if (node == null) {
            return -1;
        }

        moveToFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        Node existing = map.get(key);

        if (existing != null) {
            existing.value = value;
            moveToFront(existing);
            return;
        }

        Node node = new Node(key, value);

        map.put(key, node);
        addAfterHead(node);

        if (map.size() > capacity) {
            Node lru = removeBeforeTail();
            map.remove(lru.key);
        }
    }

    private void moveToFront(Node node) {
        remove(node);
        addAfterHead(node);
    }

    private void addAfterHead(Node node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node removeBeforeTail() {
        Node lru = tail.prev;
        remove(lru);
        return lru;
    }
}
```

## Example usage

```java
LRUCache cache = new LRUCache(2);

cache.put(1, 10);
cache.put(2, 20);

System.out.println(cache.get(1)); // 10

cache.put(3, 30); // Evicts key 2

System.out.println(cache.get(2)); // -1
System.out.println(cache.get(3)); // 30
```

## 37. Why a Doubly Linked List?

Consider a singly linked list.

If you know the node you want to remove, you still need its previous node to reconnect the list.

With a doubly linked list, the node has:
- `prev`
- `next`

Therefore, you can remove it directly in O(1).

The HashMap gives the node reference; the doubly linked list handles ordering.

## 38. LRU Complexity

| Operation | Complexity |
|---|---|
| Get by key | O(1) average |
| Insert or update | O(1) average |
| Move node to front | O(1) |
| Remove least recently used node | O(1) |
| Extra storage | O(capacity) |

HashMap operations are O(1) on average, not an absolute worst-case guarantee.

## 39. LRU Common Mistakes

1. Updating a key without marking it recently used.
2. Forgetting to remove an evicted key from the HashMap.
3. Removing the most recently used node instead of the least recently used node.
4. Incorrectly updating `prev` and `next` pointers.
5. Failing when capacity is zero.
6. Creating duplicate nodes for an existing key.
7. Assuming the implementation is thread-safe. This implementation is not thread-safe.
8. Returning a different missing-key value without following the problem's requirements.

## 40. LRU vs Related Cache Policies

| Policy | Eviction rule |
|---|---|
| LRU | Least recently accessed |
| LFU | Least frequently accessed |
| FIFO | Oldest inserted |
| TTL-based | Entry expires after a time limit |

These are different policies. A TTL cache does not automatically behave like an LRU cache.

---

# PART 5 — HOW TO APPROACH AN UNFAMILIAR PROBLEM

## Graphs

1. Identify vertices and edges.
2. Determine directed or undirected.
3. Determine weighted or unweighted.
4. Ask whether the goal is reachability, components, shortest path, cycle detection, or ordering.
5. Choose DFS, BFS, Dijkstra, topological sort, or DSU.
6. Handle disconnected components.
7. Define how visited nodes or distances are tracked.
8. Check cycles and edge cases.
9. State time and space complexity.

## Dynamic Programming

1. Identify repeated subproblems or a useful recurrence.
2. Define the state in one sentence.
3. Derive the transition.
4. Write base cases.
5. Choose memoization or tabulation.
6. Confirm evaluation order.
7. Determine the final answer location.
8. Check whether space can be optimized.
9. Test small inputs and boundary cases.
10. Check overflow.

## Intervals

1. Identify the interval endpoint convention.
2. Check whether intervals are sorted.
3. Determine whether the task is merging, selecting, counting, or allocating resources.
4. Decide whether sorting by start or end is useful.
5. Choose merge, greedy, heap, or sweep line.
6. Check equal endpoints and nested intervals.
7. Analyze sorting and extra space.

## LRU Cache

1. Identify the required operations and their complexity.
2. Use a HashMap for key lookup.
3. Use a doubly linked list for usage order.
4. Define which end is most recent and which is least recent.
5. Update recency on every successful access and appropriate updates.
6. Evict the least recently used node when full.
7. Keep the map and list synchronized.
8. Check capacity zero, repeated keys, and missing keys.

---

# PART 6 — PRACTICE ORDER

## Graphs
1. Find if a path exists
2. Number of Islands
3. Number of Connected Components
4. Shortest Path in an Unweighted Graph
5. Detect Cycle in a Directed Graph
6. Course Schedule
7. Rotting Oranges
8. Network Delay Time
9. Redundant Connection
10. Minimum Spanning Tree

## Dynamic Programming
1. Fibonacci
2. Climbing Stairs
3. House Robber
4. Min Cost Climbing Stairs
5. Coin Change
6. Unique Paths
7. Partition Equal Subset Sum
8. 0/1 Knapsack
9. Longest Common Subsequence
10. Longest Increasing Subsequence

## Intervals
1. Merge Intervals
2. Insert Interval
3. Meeting Rooms
4. Non-overlapping Intervals
5. Meeting Rooms II
6. Minimum Number of Arrows to Burst Balloons
7. Employee Free Time
8. Sweep Line / Maximum Concurrent Events

## LRU Cache
1. Implement a doubly linked list
2. Insert a node at the front
3. Remove an arbitrary node
4. Implement HashMap + doubly linked list
5. Implement `get`
6. Implement `put`
7. Handle eviction and capacity zero
8. Explain why each operation is O(1) average

---

# FIVE-SECOND REVISION

GRAPHS:
- Reachability / components -> DFS or BFS
- Unweighted shortest path -> BFS
- Weighted nonnegative shortest path -> Dijkstra
- Dependencies -> Topological sort
- Repeated connectivity queries -> DSU

DP:
- Define the state before writing code.
- Derive the transition.
- Establish base cases.
- Calculate states in the correct order.
- Consider memoization when recursion repeats work.

INTERVALS:
- Merge -> Sort by start.
- Select maximum compatible intervals -> Greedy by end.
- Minimum meeting rooms -> Min-heap or sweep line.
- Endpoint equality depends on the interval convention.

LRU:
- HashMap -> Find node.
- Doubly linked list -> Maintain recency.
- Successful get -> Move to front.
- Put existing key -> Update and move to front.
- Full cache -> Remove node before tail.
