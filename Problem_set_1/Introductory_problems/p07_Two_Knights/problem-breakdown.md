# Problem Statement
You are given an integer limit $n$. For every individual grid size scale $k$ from $1$ up to $n$, you must evaluate a hypothetical square grid of dimensions $k \times k$. Your goal is to calculate the total number of unique ways to place exactly two identical, indistinguishable data tokens onto separate cells of the grid such that they do not conflict with each other.

### The Conflict Rule
Two tokens are considered to be in conflict if the absolute spatial distance between their coordinates $(y_1, x_1)$ and $(y_2, x_2)$ forms an L-shaped offset. Specifically, a conflict occurs if either of the following geometric displacement conditions is met:
* $\Delta y = 1$ and $\Delta x = 2$
* $\Delta y = 2$ and $\Delta x = 1$
  
### Input Requirements
* A single input line containing a positive integer $n$ ($1 \le n \le 10,000$), representing the maximum grid size dimension to evaluate.

### Output Goal
Print exactly $n$ lines. The $k$-th line must contain a single integer representing the total number of valid non-conflicting arrangements possible on a $k \times k$ grid structure.

# The Trap
### The Obvious Approach:
The most instinctive, brute-force way to solve this is to treat it as a nested configuration search. For each grid size $k$, you would write nested loops that iterate through every possible coordinate position $(y_1, x_1)$ to place the first token. Inside that, you would use another set of nested loops to scan every possible coordinate position $(y_2, x_2)$ to place the second token. For each pair of locations, you would execute an explicit condition check to calculate the delta differences ($|y_1 - y_2|$ and $|x_1 - x_2|$) to see if they meet the forbidden L-shaped conflict criteria. If they are safe, you increment a counter.

### Why this Approach is Bad:
* **The Quartic Time Explosion:** On a grid of size $k \times k$, there are total cells $N = k^2$.
  * When selecting combinations of two cells, a brute-force approach evaluates roughly $N^2 = k^4$ total pairs.
  * Look closely at the system constraints: $n$ can be up to $10,000$. For the final step where $k = 10,000$, your loops would need to evaluate approximately $(10,000)^4 = 10^{16}$ operations.
  * Because you must repeat this process for every intermediate grid scale from $1$ to $n$, the cumulative steps explode completely. Under a strict Time Limit of 1.00 s, a modern processor can handle roughly $10^8$ operations per second. This naive search would take days to finish, resulting in an immediate Time Limit Exceeded (TLE) failure.
* **The Arithmetic Overflow Trap:** Even if you optimize the iteration to look up combinations mathematically rather than using loops, calculating the total raw combinations of choosing two cells out of $k^2$ involves evaluating:

$$\text{Total Combinations} = \frac{k^2(k^2 - 1)}{2}$$

# The Math Shortcut
Use Complementary Counting

$$Valid \ Configurations = TotalWays - Attacking ways$$

**Efficiency Result:** Reduces an $O(k^4)$ simulation loop to an $O(1)$ constant-time mathematical calculation which is required to pass the large constraint like $k = 10,000$.

## Derivation 
### 1. Total Ways Formula Derivation
* A $k \times k$ board contains $k^2$ total squares.
* The first knight can occupy any of the $k^2$ squares.
* The second knight can occupy of the remaining $(k^2 - 1)$ squares.
* Because the two knight are identical, the order of placement does not matter. Divide by 2 to eliminate duplicate pairs:
  
$$Total \ ways = \frac{k^2(k^2 - 1)}{2}$$

### 2. Attacking Ways Formula Derivation
* **The Box Property:** Two Knights can attack each other if and only if they occupy the opposite corners of a $2 \times 3$ or a $3 \times 2$ bounding boxes.
* **Counting $2 \times 3$ Boxes:**
  * Vertically, a 2-row box can slide into $(k - 1)$ position.
  * Horizontally, a 3-column box can slide into $(k - 2)$ positions.
  * Total $3 \times 2$ boxes = $(k - 1)(k - 2)$
 
* **Counting $3 \times 2$ Boxes:**
  * Vertically, a 3-row box can slide into $(k - 2)$ position.
  * Horizontally, a 2-column box can slide into $(k - 1)$ positions.
  * Total $3 \times 2$ boxes = $(k - 2)(k - 1)$
  
* **Counting Boxes:** The total number of attacking sub-grids is:

$$Total \ Boxes = (k - 1)(k - 2) + (k - 2)(k - 1) = 2(k - 1)(k - 2)$$

* **Attacking Configurations:** Each individual box contains exactly **2** distinct ways for the knight to mutually attack via opposite diagonals. Multiple the total box count by 2:

$$Attacking Ways = 2 \times 2(k - 1)(k - 2) = 4(k - 1)(k - 2)$$

## Final Operational Formula

$$Valid \ Ways = \frac{k^2(k^2 - 1)}{2} - 4(k - 1)(k - 2)$$

# The Algorithm
```
Step-1: Start
Step-2: Input n
Step-3: for j = 1 to j = n
Step-4: Process
            totalWays = j * j * (j * j - 1) / 2
Step-5: Process
            attackingWays = 4 * (j - 1) * (j - 2)
Step-6: Process
            result = totalWays - attackingWays
Step-7: Display result
       [End for loop]
Step-8: End
```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The loop contributes `n` operations, while each calculation (`totalWays`, `attackingWays`, `result`) and the display operation also contribute `n` operations. The remaining statements are constant operations. Therefore, the time equation becomes:

$$T(n) = 5n + 2$$

To calculate complexity we need to apply few principles:

Ignore Constants: Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the BigO notation. After removing constants, the equations becomes:

$$T(n) = n + 1$$

Power dominance: In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:

$$T(n) = n$$

Which can finally be represented as:

$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution. This algorithm mainly uses:

* `j`
* `totalWays`
* `attackingWays`
* `result`
* loop control variables

No additional arrays, recursion, linked lists, or dynamically growing data structures are created. The variables occupy a fixed amount of memory regardless of the value of `n`. Therefore, the space equation becomes:

$$S(n) = 4$$

After applying principles for calculating complexity, the equation becomes:

$$S(n) = 1$$

Which can be finally represented as:

$$O(1)$$

### Auxiliary Space Math
To calculate the auxiliary space complexity, we consider only the extra memory created by the algorithm and exclude the input. The algorithm uses only a fixed number of variables and does not allocate any additional memory that grows with `n`.

The auxiliary space equation becomes:

$$AS(n) = 4$$

After applying principles for calculating complexity, the equation becomes:

$$AS(n) = 1$$

Which can be finally represented as:

$$O(1)$$

# Edge Cases
### 1. The Sub-Minimum Grid Baseline ($k = 1$)
When the program initializes its evaluation cycle at the absolute starting boundary where the grid dimension is $1 \times 1$:
* **Inside the algorithm:** The mathematical calculation evaluates total raw combinations of placing two data tokens on the board using the formula $\frac{k^2(k^2 - 1)}{2}$. For $k=1$, this evaluates to $\frac{1(0)}{2} = 0$
* **Result:** The system correctly outputs 0 because it is physically impossible to place two distinct tokens onto a single cell. However, if your offset logic subtracts a fixed constant representing conflict configurations without first validating that $k \ge 3$, the formula can calculate a negative number of ways, which is a structural impossibility.

### 2. The Conflict-Void Scale Boundary ($k = 2$)
Consider the intermediate structural size of a $2 \times 2$ grid:
* **Inside the algorithm:** A $2 \times 2$ matrix contains a total of 4 squares. The total ways to choose two squares is $\frac{4 \times 3}{2} = 6$. However, because an L-shaped conflict requires a bounding space of at least $2 \times 3$ or $3 \times 2$ cells to exist, the physical board is too small to host any conflicts.
* **Result:** Mathematical Underflow Risk. If your generalized shortcut formula blindly calculates conflict patterns using an expression like $4(k-1)(k-2)$ without a safety gate, substituting $k=2$ evaluates to $4(1)(0) = 0$. The subtraction yields $6 - 0 = 6$, which matches the correct sample output. However, any slight off-by-one error in your boundary tracking logic for sizes $k < 3$ will corrupt this transitional step.

### 3. Cumulative Mid-Range Combinatorics (The 32-Bit Register Crash)
When the processing loop climbs into the upper dimensions of the loop boundary (e.g., $k \ge 2,000$ up to $k = 10,000$):
* **Inside the algorithm:** The mathematical code computes the total unconstrained placement pairs by multiplying $k^2 \times (k^2 - 1)$. When $k = 10,000$, $k^2 = 100,000,000$. Squaring this value pushes the intermediate numerator product up to $10^{16}$.
* **Result:** Arithmetic Integer Overflow. A standard signed 32-bit integer data register maxes out at $2,147,483,647$ ($\approx 2 \times 10^9$). The calculation violently bursts past this hardware barrier, causing the bits to truncate and wrap around into a nonsense negative number. The console will print corrupted or impossible negative placement counts unless all variables are explicitly instantiated as 64-bit integer types (long long in C++ or long in Java).
### 4. Non-Positive Boundary Violations ($n \le 0$)
If a system testing pipeline or unvalidated upstream configuration script injects a zero or negative integer value into the setup gate:
* **Inside the algorithm:** The loop engine attempts to instantiate a counting loop sequence structured as `for (int k = 1; k <= n; k++)`.
* **Result:** Silent Execution Bypassing. Because $1 \le 0$ immediately evaluates to false, the loop block terminates instantly without generating any lines of output. While this avoids a runtime crash, it represents a dead execution path that fails to print the tracking data strings expected by automated evaluation grading software.