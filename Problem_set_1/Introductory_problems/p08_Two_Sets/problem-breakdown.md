# Problem Statement
You are given a sequence of consecutive positive integers starting from $1$ up to an upper limit $n$. Your goal is to determine whether this entire collection can be partitioned into exactly two disjoint subsets such that the arithmetic sum of the elements in the first subset is strictly equal to the arithmetic sum of the elements in the second subset. If a balanced split is mathematically possible, you must explicitly construct and output both subsets.

### Rules & Constraint
- **Total Utilization:** Every single integer in the range $[1, n]$ must belong to exactly one of the two subsets (no missing numbers, no duplicate usage).
- **Equal Weight Requirement:** If the subsets are labeled $S_1$ and $S_2$, then $\sum(S_1) = \sum(S_2) = \frac{1}{2} \sum_{i=1}^{n} i$.
- **Failure State:** If the cumulative sum of all integers from $1$ to $n$ cannot be divided evenly into two whole halves, the partition is impossible.

### Input Requirements
- A single line containing an integer $n$ ($1 \le n \le 10^6$), representing the size of the initial integer range.

### Output Goal
- Print a success indicator string (e.g., `"YES"`) if an equal partition exists, followed by the size and elements of both subsets across separate lines.
- If no equal partition possible, print a terminal negative indicator string(eg., `"NO"`)

# The Trap
### The Obvious Approach
The most instinctive way to solve this is to treat it as a classic 0/1 Knapsack or Subset Sum decision problem. You first compute the target sum $\text{Target} = \frac{n(n + 1)}{4}$. Then, you write a recursive backtracking function or build a 2D dynamic programming (DP) boolean table `dp[i][s]`, where each state tracks whether a subset sum $s$ can be formed using a combination of the first $i$ integers. Once a valid subset matching the target sum is found, you collect those numbers for Set 1 and place the remaining unused numbers into Set 2.

### Why this approach is Bad
- **The Exponential Recursion Explosion ($O(2^n)$ Time):** Using brute-force recursion to explore every possible subset branch tests $2^n$ combinations. With $n = 10^6$, evaluating $2^{1000000}$ branches is computationally impossible and will freeze instantly, resulting in a ***Time Limit Exceeded (TLE)*** error.
- **The Pseudo-Polynomial Memory Collapse ($O(n \times \text{Target})$ Space):** Standard DP table construction requires dimensions of $n \times \text{Target}$. For $n = 10^6$, the target sum is approximately $\frac{10^{12}}{4} = 2.5 \times 10^{11}$. Allocating a table of size $10^6 \times 2.5 \cdot 10^{11}$ would require petabytes of RAM, causing an immediate fatal ***Out of Memory (OOM)*** crash.
- **The Arithmetic Overflow Trap:** Calculating the total sum $\frac{n(n + 1)}{2}$ with $n = 10^6$ produces $\approx 5 \times 10^{11}$. Storing this intermediate sum in a standard 32-bit signed integer ($\max \approx 2 \times 10^9$) causes an integer overflow, corrupting the target sum into a negative value or zero before any partitioning even begins.

# Maths Shortcut
## The Parity Criterion
The total sum of all integers from $1$ to $n$ is given by: 
$$S_n = \frac{n(n + 1)}{2}$$

To partition the collection into two subsets with identical sums, $S_n$ must be an even integer. Evaluating $S_n \pmod 2$ across all possible forms of $n$:

- **Case** $n \equiv 0 \pmod 4$: Let $n = 4k$.
$$S_{4k} = \frac{4k(4k + 1)}{2} = 2k(4k + 1) \quad (\text{Even} \implies \textbf{Valid})$$

- **Case** $n \equiv 3 \pmod 4$: Let $n = 4k + 3$.
  $$S_{4k+3} = \frac{(4k + 3)(4k + 4)}{2} = (4k + 3)(2k + 2) = 2(4k + 3)(k + 1) \quad (\text{Even} \implies \textbf{Valid})$$

- Case $n \equiv 1 \pmod 4$ or $n \equiv 2 \pmod 4$: $S_n$ evaluates to an odd integer. Dividing an odd integer into two equal integral halves is impossible ($\implies \textbf{NO SOLUTION}$).

## The 4-Block Balancing
When a solution exists, numbers are grouped into symmetric 4-element balancing blocks of consecutive values $\{x, x+1, x+2, x+3\}$.

Every 4-block balances equally by pairing outer and inner elements:
- **Set-1:** Receives the 1st and 4th elements: $x + (x + 3) = 2x + 3$
- **Set 2:** receives the 2nd and 3rd elements: $(x + 1) + (x + 2) = 2x + 3$

Because each block yields an equal sum of $2x + 3$ for both sets, the algorithm advances the pointer by increments of 4 until all numbers are assigned.

## Structural Partition Strategies
### Branch A: When $n \equiv 0 \pmod 4$
The entire sequence breaks down into complete 4-blocks starting at index $1$:
- **Initialization:** `Set 1 = []`, `Set 2 = []`
- **Block Iteration ($i = 1, 5, 9, \dots, n-3$):**
  - $\text{Set 1} \leftarrow \{i, i + 3\}$
  - $\text{Set 2} \leftarrow \{i + 1, i + 2\}$

### Branch B: When $n \equiv 3 \pmod 4$
The first 3 numbers cannot form a 4-block and are manually assigned to seed the initial balance: $1 + 2 = 3$.
- **Initialization:**
  - $\text{Set 1} \leftarrow \{1, 2\}$ $(\text{Sum} = 3)$
  - $\text{Set 2} \leftarrow \{3\}$ $(\text{Sum} = 3)$
- Block Iteration ($i = 4, 8, 12, \dots, n-3$):
  - $\text{Set 1} \leftarrow \{i, i + 3\}$
  - $\text{Set 2} \leftarrow \{i + 1, i + 2\}$

# The Algorithm
```
BEGIN ALGORITHM
    (Long) -> num <- INPUT()
    (String) -> set1
    (String) -> set2

    (Long) -> sum <- num * (num + 1) / 2

    IF sum % 2 == 0
        DISPLAY "YES"

        IF num % 4 == 3
            set1.append("1 2 ")
            count1 <- 2

            set2.append("3 ")
            count2 <- 1

            FOR (Long) -> i <- 4, i <= num, i += 4
                set1.append(CONVERT.String(i) + " " + CONVERT.String(i + 3) + " ")
                count1 <- count1 + 2
                
                set2.append(CONVERT.String(i + 1) + " " + CONVERT.String(i + 2) + " ")
                count2 <- count2 + 2
            END FOR

            DISPLAY count2
            DISPLAY set2
            DISPLAY count1
            DISPLAY set1

        ELSE
            count <- 0

            FOR (Long) -> i <- 4, i <= num, i += 4
                set1.append(CONVERT.String(i) + " " + CONVERT.String(i + 3) + " ")
                set2.append(CONVERT.String(i + 1) + " " + CONVERT.String(i + 2) + " ")
                count <- count + 4
            END FOR

            DISPLAY count / 2
            DISPLAY set2
            DISPLAY count / 2
            DISPLAY set1
        END IF

    ELSE
        DISPLAY "NO"
    END IF
END ALGORITHM
```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The loop contributes n operations, while the two append operations and the count update inside the loop each contribute n operations. The remaining calculations, conditions, and display operations are treated as constant operations. Therefore, the time equation becomes:

$$T(n)=4n+2$$

To calculate complexity we need to apply few principles:

- **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the BigO notation. After removing constants, the equations becomes:

$$T(n) = n + 1$$

- **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:

$$T(n) = n$$

Which can finally be represented as:

$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much memory the algorithm uses during execution. This algorithm mainly uses:

- `set1`
- `set2`
- `num`
- `sum`
- `count`, `count1`, `count2`
- loop control variable `i`

The two strings `set1` and `set2` together store the numbers from `1` to `num`. Therefore, their combined storage grows proportionally with `n`, while the remaining variables require only constant memory. Therefore, the space equation becomes:

$$S(n) = n + 6$$

After applying principles for calculating complexity, the equation becomes:

$$S(n) = n$$

Which can be finally represented as:

$$O(n)$$

### Auxiliary Space Math

Since `set1` and `set2` are created and grow during the execution, they are auxiliary memory. Their combined storage is proportional to n.

Therefore:

$$AS(n) = n + 6$$

After removing the constant:

$$AS(n) = n$$

Which can be finally represented as:

$$O(n)$$

So the final result is:

Total Space: `O(n)`
Auxiliary Space: `O(n)`

# Edge Cases
### 1. Cumulative Sum Bit Overflow ($n = 10^6$)
   When evaluating the input at the upper constraint limit ($n = 10^6$):
   - **Inside the algorithm:** The initial validity check computes the cumulative sum $S_n = \frac{n(n + 1)}{2}$. For $n = 10^6$, $S_n \approx 5 \times 10^{11}$.
   - **Result:** Arithmetic Integer Overflow. If this computation or the intermediate product $n(n + 1)$ is evaluated using standard 32-bit signed integers, the register wraps around into a corrupted negative value. A check like `sum % 2 == 0` or evaluating target halves can fail or yield incorrect parity checks. Variables holding intermediate sums must be strictly typed as 64-bit integers (`long long` in C++, `long` in Java).
  
### 2. The Minimal Valid Base Cases ($n = 3$ and $n = 4$)
   When the input is the smallest possible valid value for either structural parity branch:
   - **Inside the algorithm:** 
     - For $n = 3$ ($n \equiv 3 \pmod 4$), the base elements $\{1, 2\}$ and $\{3\}$ are assigned directly. The remaining block loop starting at $i = 4$ must evaluate $i \le n - 3$ ($4 \le 0$), which is false.
     - For $n = 4$ ($n \equiv 0 \pmod 4$), the single block $\{1, 4\}$ and $\{2, 3\}$ is processed. The loop starts at $i = 1$ and terminates before $i = 5$.
   - **Result:** Loop Bound Underflow / Index Errors. If the loop condition is written with strict upper bounds (e.g., assuming $n \ge 7$ or $n \ge 8$ for multi-block iterations) without allowing early loop termination, it can access elements outside the range or execute zero iterations incorrectly.
  
### 3. Immediate Parity Failure Boundaries ($n = 1$ and $n = 2$)
   When testing the lowest possible positive bounds where division is impossible:
   - **Inside the algorithm:** 
     - For $n = 1$, $S_1 = 1$, and $1 \pmod 4 = 1$.
     - For $n = 2$, $S_2 = 3$, and $2 \pmod 4 = 2$.
   - **Result: Incorrect Execution Bypass.** The algorithm must cleanly evaluate the non-divisibility condition and output `"NO"` immediately. If the logic assumes all inputs can be split and directly attempts array initialization or indexing for Set 1 / Set 2, it will construct unbalanced sets or throw index-out-of-range exceptions.

### 4. Zero and Negative Scale Boundary ($n \le 0$)
If an unvalidated test feed or fuzzing framework inputs $n = 0$ or a negative integer:
- **Inside the algorithm:** Computing $0 \pmod 4$ evaluates to $0$, which matches the `Branch A` condition ($n \equiv 0 \pmod 4$) if guarded only by modulo math without a positive boundary check ($n \ge 1$).
- **Result:** False Positive Output. The program would print `"YES"` followed by sets of size `0` (or enter a negative indexing loop for $n < 0$), violating problem constraints. An explicit boundary guard ($n < 1 \implies \text{invalid}$) prevents entering structural partitioning logic.