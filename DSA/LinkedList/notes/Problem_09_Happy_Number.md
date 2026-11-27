# Problem 09 — Happy Number

> **Java file:** `../HappyNumber.java`

> **Pattern:** Fast & Slow pointers (Floyd cycle detection) — **same family as Linked List Cycle (Problem 03)**  
> **Goal:** Return `true` if `n` is a happy number.  
> **LeetCode:** 202

---

## 1. Problem statement

Write an algorithm to determine if a number `n` is **happy**.

A happy number is defined by the following process:

1. Starting with any positive integer, replace the number by the **sum of the squares of its digits**.
2. Repeat until the number equals `1` (happy), or it **loops endlessly in a cycle** that does not include `1` (not happy).

Return `true` if `n` is a happy number, otherwise `false`.

### Example 1

```text
Input:  n = 19
Output: true

19 → 1² + 9² = 82
82 → 8² + 2² = 68
68 → 6² + 8² = 100
100 → 1² + 0² + 0² = 1   ✓
```

### Example 2

```text
Input:  n = 2
Output: false

2 → 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4 → …  (cycle, never 1)
```

### Constraints (typical)

- `1 <= n <= 2^31 - 1`

**Follow-up:** Can you solve it without extra space? (Floyd — same as cycle detection on a linked list.)

---

## 2. Mental picture — draw the number chain

There is no `ListNode` in the problem statement, but you can **draw the same arrows** as a linked list.

```text
Happy path (n = 19):

  19 ──► 82 ──► 68 ──► 100 ──► 1 ──► 1 ──► 1 …
                                    ↑_____|
                              (1 points to itself)
```

```text
Unhappy path (n = 2) — eventually closes a loop without 1:

  2 ──► 4 ──► 16 ──► 37 ──► 58 ──► 89 ──► 145 ──► 42 ──► 20 ──┐
  ▲                                                            │
  └──────────────────────────── 4 ◄────────────────────────────┘
```

**One sentence to keep in mind:**

> Each number is a **node**; `next` is **sum of squares of digits**. Happy means you eventually walk to node `1`.

That is why this problem sits in the **Linked List** folder: Floyd fast/slow from Problem 03 applies unchanged — only what `next` means changes.

**Pattern = Fast & Slow pointers (same family as Linked List Cycle).**

---

## 2b. Why this is a cycle problem (not “just math”)

| Linked List Cycle (141)      | Happy Number (202)                    |
|------------------------------|----------------------------------------|
| Node = `ListNode` with `val` | Node = integer `n`                     |
| `next` = `node.next`         | `next` = `sumSquares(n)`               |
| Cycle ⇒ infinite walk        | Unhappy ⇒ infinite walk in same cycle  |
| No `null` in cycle           | No exit except reaching `1`            |
| Floyd: slow + fast meet      | Floyd: slow + fast meet                |

**Interview pivot:** “I recognise implicit linked structure — I’ll reuse Floyd with a digit-square successor.”

---

## 3. How to start thinking (simple questions)

1. **What stops success?** We see `1`.
2. **What stops failure?** We see the **same number twice** (a cycle).
3. **Brute way to detect repeat?** `HashSet` of every value on the chain.
4. **Can we detect repeat without memory?** Yes — Floyd (fast catches slow in any cycle).
5. **After Floyd says “they met,” how do we know happy vs unhappy?** Meeting point is `1` iff happy.

If you can explain the chain drawing above, you already have the optimal approach.

---

## 4. Approach 1 — Brute force (HashSet)

### Idea

Walk the chain. After each step, if current value is `1`, return `true`. If we try to insert current value into a set and it was already there, return `false`.

### Why it works

Every step is deterministic. The only possibilities are:

- Hit `1` (finite, success), or
- Enter a cycle (finite steps until first repeat).

The set records the path; first duplicate means cycle without having reached `1`.

### Key method

`isHappyHashSet()` in `../HappyNumber.java`

### Java (core loop)

```java
public static int nextHappy(int n) {
    int sum = 0;
    while (n > 0) {
        int digit = n % 10;
        sum += digit * digit;
        n /= 10;
    }
    return sum;
}

public static boolean isHappyHashSet(int n) {
    Set<Integer> seen = new HashSet<>();
    while (n != 1 && seen.add(n)) {
        n = nextHappy(n);
    }
    return n == 1;
}
```

### Dry run (n = 19)

| Step | `n` | In set? |
|---:|---:|---|
| 0 | 19 | add |
| 1 | 82 | add |
| 2 | 68 | add |
| 3 | 100 | add |
| 4 | 1 | loop ends (`n == 1`) → **true** |

### Complexity

| | Brute (HashSet) |
|---|---|
| **Time** | `O(k)` — `k` = steps until `1` or cycle |
| **Space** | `O(k)` — set stores chain prefix |

`k` is small in practice (unhappy cycle length is bounded), but interview answers cite **number of digits** per step → often written `O(log n)` per step, `O(k)` overall.

### When brute still matters

Use HashSet on the whiteboard to **verify** examples 19 and 2 before you write Floyd. Shows you understand **what** is cycling.

---

## 5. Approach 2 — Optimal (Floyd fast / slow)

### Idea

Two integers `slow` and `fast`, both start at `n`.

- Each iteration: `slow = nextHappy(slow)`; `fast = nextHappy(nextHappy(fast))`.
- Stop when `slow == fast`.
- Return `slow == 1`.

Same loop shape as `hasCycle()` in `LinkedListCycle.java`, so you can reuse muscle memory.

### Steps

1. Implement `nextHappy(n)` once (digit-square sum).
2. `slow = fast = n`.
3. `do { advance slow once, fast twice; } while (slow != fast);`
4. Answer is whether meeting point is `1`.

### Why `do-while`?

At start, `slow == fast == n`. A `while (slow != fast)` would never run. Problem 03 uses `while (fast != null && fast.next != null)` because list nodes differ; here we need at least one hop.

### Key method

`isHappyFloyd()` in `../HappyNumber.java`

```java
public static boolean isHappyFloyd(int n) {
    int slow = n;
    int fast = n;
    do {
        slow = nextHappy(slow);
        fast = nextHappy(nextHappy(fast));
    } while (slow != fast);
    return slow == 1;
}
```

### Visual walkthrough (n = 2, abbreviated)

```text
slow/fast start at 2.

Eventually slow and fast both land on 4 inside the unhappy cycle.
slow == fast, slow != 1  →  false
```

### Happy case (n = 7)

Chain hits `1`; both pointers meet at `1` → `true`.

---

## 6. Hardest part — trusting Floyd when there is no `ListNode`

### Worry 1: “Is meeting always safe to interpret?”

On an unhappy number, **every** starting point eventually enters the **same** well-known cycle (does not include `1`). Floyd’s theorem: fast and slow meet **inside** the cycle. If the cycle is not at `1`, meeting value ≠ `1`.

### Worry 2: “What about happy numbers — isn’t `1 → 1` a cycle?”

Yes. For happy `n`, both pointers enter the `1 → 1 → …` tail and meet at `1`. Returning `slow == 1` is correct.

### Worry 3: “Implementing digit sum”

```text
n = 130
digits: 0² + 3² + 1² = 10   (process with n % 10, n / 10)
```

Negative `n` is outside constraints; guard anyway in practice code.

### Side-by-side with Problem 03

```text
Linked List Cycle          Happy Number
─────────────────          ────────────
slow = slow.next           slow = nextHappy(slow)
fast = fast.next.next      fast = nextHappy(nextHappy(fast))
meet ⇒ has cycle           meet at 1 ⇒ happy
```

---

## 7. Detailed dry run (Floyd, n = 19)

Chain: `19 → 82 → 68 → 100 → 1 → 1 → …`

We only show slow/fast **values** after each full iteration (both advanced):

| Iter | slow after | fast after | slow == fast? |
|---:|---:|---:|---|
| 1 | 82 | 100 | no |
| 2 | 68 | 1 | no |
| 3 | 100 | 100 | **yes** |

Start: `slow = fast = 19`.

| Iter | slow after | fast after | meet? |
|---:|---:|---:|---|
| 1 | 82 | 68 | no |
| 2 | 68 | 1 | no |
| 3 | 100 | 1 | no |
| 4 | 1 | 1 | **yes → happy** |

(`fast` after iter 1: `next(next(19))` = `next(82)` = 68. After iter 4 both are at `1`.)

---

## 7b. Paper-trace template (unhappy, n = 2)

Fill while coding:

| Iter | slow | fast | Notes |
|---:|---:|---:|---|
| 0 | 2 | 2 | |
| 1 | 4 | 37 | |
| … | … | … | until equal |
| k | 4 | 4 | meet, not 1 → **false** |

---

## 8. Why the algorithm is correct

**HashSet:** Deterministic sequence; first repeat ⇒ cycle; if `1` never appeared, unhappy.

**Floyd:** Any sequence that eventually cycles has the same tortoise-hare meeting property as linked lists. Happy sequences cycle at `1` only; unhappy sequences cycle in `{4, 16, 37, 58, 89, 145, 42, 20, …}`. Meeting value `1` iff happy.

---

## 9. Complexity comparison

| Method | Time | Extra space |
|---|---:|---:|
| HashSet | `O(k)` steps | `O(k)` |
| Floyd | `O(k)` steps | `O(1)` |

`k` is bounded (small constant max chain before cycle for 32-bit `n`). Per-step cost is `O(d)` digits ≈ `O(log n)` for current `n`.

Neither approach can need more than a few hundred iterations for LeetCode constraints.

---

## 10. Edge cases

| Case | Expected | Note |
|---|---|---|
| `n = 1` | `true` | Already happy |
| `n = 19` | `true` | Classic interview example |
| `n = 2` | `false` | Classic unhappy |
| `n = 7` | `true` | Short path to `1` |
| Large `n` near `2^31-1` | still fast | Chain length bounded |

---

## 11. Common mistakes

| Mistake | Effect |
|---|---|
| Using `while (slow != fast)` without advancing first | Dead loop or wrong on start |
| Forgetting `return slow == 1` after meet | Always `true` on any cycle |
| Digit sum with wrong base or sign | Wrong chain |
| Treating “happy” as “no cycle” | `1` is a one-node cycle |
| Copying list cycle code but not implementing `nextHappy` | Does not compile / wrong logic |

---

## 11b. Debug checklist

1. Print `chainPreview(n, 15)` from `HappyNumber.java`.
2. Compare HashSet vs Floyd on same `n`.
3. Hand-trace `nextHappy(130)` → `10`.
4. Re-read Problem 03 — same Floyd skeleton.

---

## 12. Interview explanation — say this aloud

### Thirty-second version

> “I treat each number as a node and the next pointer as sum of squares of digits. If I reach 1 it’s happy; otherwise the sequence must cycle. I can use a HashSet or Floyd slow and fast — same as linked list cycle — and return whether the meeting point is 1.”

### Two-minute version

> “Start from n, repeatedly replace n with the sum of squares of its digits. That’s a deterministic linked list without actual nodes. Happy iff we hit 1. Unhappy numbers always fall into a fixed cycle that never includes 1. Brute: HashSet until duplicate or 1. Optimal: Floyd — slow one step, fast two steps — identical structure to LeetCode 141. When they meet, check if the value is 1. O(k) time, O(1) space.”

---

## 13. Follow-ups

**Q: What is the unhappy cycle?**  
A: All unhappy chains eventually enter `4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4`.

**Q: Can we hard-code that cycle for O(1) space?**  
A: Yes — detect when you hit `4` (or any member) without Floyd; less elegant but valid.

**Q: Find the starting node of the cycle (LeetCode 142 style)?**  
A: After meet, reset one pointer to `n`, move both one step until equal — entry into cycle (for unhappy numbers, entry is `4`).

**Q: Happy number for other bases?**  
A: Same pattern; change digit extraction for base `b`.

**Q: Why is this in Linked List study?**  
A: **Pattern = Fast & Slow pointers (same family as Linked List Cycle).** It trains you to see implicit `next` pointers.

---

## 13b. Complexity Q&A

**Why time O(k)?** Each step reduces or transforms `n`; repeat or `1` within bounded steps.

**Why Floyd O(1) space?** Only two int pointers — no set.

**Can we beat Floyd?** Same asymptotic; HashSet is easier to explain first.

---

## Link to Java — which method is which

| Approach | Method in `HappyNumber` |
|---|---|
| HashSet | `isHappyHashSet` |
| Floyd (optimal) | `isHappyFloyd` |

Run `main` to print all approaches on sample inputs.

---

## 14. Complete runnable Java

Full source: `../HappyNumber.java` (`package DSA.LinkedList`).

```bash
cd LinkedList
javac HappyNumber.java
java -cp . DSA.LinkedList.HappyNumber
```

---

## 15. Complete Java class (copy-friendly)

```java
package DSA.LinkedList;

import java.util.HashSet;
import java.util.Set;

public class HappyNumber {

    public static int nextHappy(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    public static boolean isHappyHashSet(int n) {
        if (n <= 0) {
            return false;
        }
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && seen.add(n)) {
            n = nextHappy(n);
        }
        return n == 1;
    }

    public static boolean isHappyFloyd(int n) {
        if (n <= 0) {
            return false;
        }
        int slow = n;
        int fast = n;
        do {
            slow = nextHappy(slow);
            fast = nextHappy(nextHappy(fast));
        } while (slow != fast);
        return slow == 1;
    }

    public static void main(String[] args) {
        int[] tests = {19, 2, 1, 7};
        for (int n : tests) {
            System.out.println(
                n + " → HashSet: " + isHappyHashSet(n)
                    + ", Floyd: " + isHappyFloyd(n));
        }
    }
}
```

---

## 16. Practice checklist

- [ ] Implement `nextHappy` without hints.
- [ ] Implement `isHappyHashSet` and trace `n = 19` on paper.
- [ ] Implement `isHappyFloyd` — compare loop to `hasCycle()` in Problem 03.
- [ ] Trace Floyd for `n = 2` until `slow == fast`.
- [ ] State time and space for both approaches aloud.
- [ ] Say explicitly: **Pattern = Fast & Slow (Linked List Cycle family).**
- [ ] Run `HappyNumber.main` and match output.

### Small challenge

Without code: write the full chain from `n = 7` until you hit `1`.

---

## 17. Quick revision

Pattern: **Pattern B — Fast & Slow (Floyd).**

Brute: `isHappyHashSet()` — `O(k)` time, `O(k)` space.  
Optimal: `isHappyFloyd()` — `O(k)` time, `O(1)` space.

One line:

> **Digit-square chain = implicit linked list; Floyd meet at 1 = happy.**

---

## 18. Pattern used

**Pattern B — Fast & slow pointers** (`Linked_List_Common_Patterns.md`)

Same code family as **Problem 03 — Linked List Cycle**.

---

## 19. Next problem

**Linked List track (Problems 01–09)** — next: revisit cycle + happy pairs in mock interviews, or continue your global DSA roadmap from `DSA_Interview_Pattern_Roadmap.md`.