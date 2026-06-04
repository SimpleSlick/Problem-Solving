# Problem Statement
You are given an infinite, two-dimensional coordinate system where integers are populated sequentially starting from $1$ in a expanding, square-layered pattern. Your task is to calculate the exact numerical value residing at a specific target location defined by row $y$ and column $x$, without constructing the grid in memory.

| 1 | 2 | 9 | 10 | 25 |
| ----- | ----- | ----- | ----- | ----- |
| 4 | 3 | 8 | 11 | 24 |
| 5 | 6 | 7 | 12 | 23 |
| 16 | 15 | 14 | 13 | 22 |
| 17 | 18 | 19 | 20 | 21 |

### The Geometric Pattern rules:
* **Layer Definition:** The grid grows in concentric, L-shaped shells (or rings). The layer number for any cell is determined by the maximum value between its row and column coordinates: $z = \max(y, x)$.
* **Directional Flow:** The values snake along these L-shaped layers, but the direction changes depending on whether the layer number is even or odd. One parity populates numbers from the top row downward and then leftward, while the opposite parity populates numbers from the bottom-left upward and then rightward.
### Input Requirements:
* An integer $t$ specifying the total number of independent lookup operations to perform.
* This is followed by $t$ lines, each containing two massive positional integers: $y$ (the row index) and $x$ (the column index).
### Output Goal:
For each given coordinate pair, output a single integer representing the exact sequence value assigned to that cell.

# The Trap
The most instinctive way to solve this is to treat it as a simulation or a matrix construction problem. You would dynamically allocate a massive 2D array (matrix) in memory. Then, using loops and directional state variables (up, down, left, right), you would literally mimic the snaking, spiral growth pattern—populating the matrix cell-by-cell with an incrementing counter until you reach the requested size. Once the matrix is fully constructed, you would perform a simple direct lookup: `return grid[y][x]`.

### Why this Approach is Bad:
* **The Catastrophic Memory Allocation Crash ($O(N^2)$ Space)**: Look closely at the system constraints. The coordinates $y$ and $x$ can be as large as $10^9$ ($1,000,000,000$).
    * Trying to allocate a 2D matrix of size $10^9 \times 10^9$ to store standard integers would require approximately $10^{18}$ cells.
    * This would require 4 Exabytes (4 billion Gigabytes) of RAM to store. Your program will instantly suffer a catastrophic Out of Memory (OOM) crash before it can even place the number `2`.
* **The Time Complexity Limit ($O(N^2)$ Time):** Even if you had infinite memory, looping cell-by-cell to populate a grid up to a index of $10^9$ means your processor would have to execute roughly $10^{18}$ loop iterations. Under the strict Time Limit of 1.00 s, a modern CPU can only process around $10^8$ operations per second. Your code will instantly freeze and trigger a Time Limit Exceeded (TLE) failure.
* **The Scalar Multiplication Overflow:** A naive math approach might try to jump directly to the corners of the grid by calculating the square of the layer size. However, if you multiply $10^9 \times 10^9$ using standard 32-bit integer variables, the value will overflow its bit capacity, wrapping around into corrupted negative numbers.

# The Math Shortcut
### Key properties of Number Spiral
* **Layer Determination:** The Layer $z$ for any coordinate (y, x) is defined by the maximum coordinate value: $z = max(y, x)$. This represents a nested square shell of size $z$ x $z$.
* **Perfect Squares:** Each Layers z contains exactly $2z - 1$ elements and concludes at a maximum value of $z^2$
* **Altering direction: (Parity):** The spiral wraps in altering directions depending on whether $z$ is even or odd.
    * **Even Layers $(2, 4, 6, \dots):$** Clockwise. Numbers increase along column 1 downwards to ($z, 1$), then rightward along row $z$ to ($z, z$) to ending at $z^2$
    * **Odd Layers $(1, 3, 5, \dots):$** Counter clockwise. Numbers increase along row 1 rightwards to ($1, z$), then downwards along column $z$ to ($z, z$) ending at $z^2$.

### Mathematical Derivation

#### Case - 1: $z$ is Even (max(y, x) = z)
The layer flows from ($1, z$) down to ($z, 1$) and then right to ($z, z$) = $z^2$
* ***Sub-case $y \leq x$ (Upper/Right Region):***  
The coordinate is on the vertical segment of the shell. The count starts from the inner square boundary $(x - 1)^ 2$ and increases downward along column $x$ by the row index $y$.

$$\text{Value} = (x - 1)^ 2 + y$$

* ***Sub-case $y > x$ (Upper/Right Region):***
The coordinate is on the horizontal segment on the shell. It is closer to the end of tha layer at ($y, y$) = $y^2$. Since the numbers decrease as you move leftward from the corner $(y, y)$, subtract the distance from the edge and add 1 for alignment.

$$\text{Value} = y^2 - (x - 1) = y^2 - x + 1$$

#### Case - 2: $z$ is Even (max(y, x) = z)
The layer flows from $(z, 1)$ right to $(1, z)$ and then down to $(z, z) = z^2$

* ***Sub-case $x \leq y$ (Lower/Left region):***
The coordinate is on the horizontal segment. The count starts from the inner square boundary $(y - 1)^2$ and increase rightward along row $y$ by the column index $x$.

$$\text{Value} = (y - 1)^2 + x$$

* ***Sub-case $x > y$ (Upper/Right region):***
The coordinate is on the vertical segment. It is closer to the end of the layer at $(x, x) = x^2$. Since numbers decrease as you move upward from the corner $(x, x)$, subtract the distance from the edge and add 1 for alignment.

### Summary table for Calculation
| Case | Condition | Formula |
| ----- | ----- | ----- |
| $z$ is even |$y > x$| $y^2 - x + 1$|
| | $y \le x$ | $(x-1)^2 + y$ |
| $z$ is odd | $x > y$ | $x^2 - y + 1$ |
| | $x \le y$ | $(y-1)^2 + x$ |

# The Algorithm
```
Step - 1: Start
Step - 2: Input t
Step - 3: Set
            out: String/Array
Step - 4: Loop 
            t-- > 0
Step - 5: Input x, y
Step - 6: Set
            z = max(x, y)
            result = ""
Step - 7: if z % 2 == 0
            [Goto Step - 8]
          else
            [Goto Step - 9]
Step - 8: if y == z
                result = (z - 1)(z - 1) + y
          else
                result = z.z - y + 1
            [Goto Step - 10]
Step - 9: if x == z
                result = z.z - y + 1
        else
                result = (z - 1)(z - 1) + x
            [Goto Step - 10]
Step - 10: Add result to out
          [Repeat Step - 4]
Step - 11: Display out
Step - 12: End
```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The test-case loop contributes `t` operations, the input operation contributes `t` operations, the conditional blocks contribute `t` operations, the nest conditional blocks contribute `t` operations, the arithmetic calculations contribute `t` operations, and adding the result to the output container contributes `t` operations, while the remaining statements are constant operations. Therefore, the time equation becomes:

$$T(t) = 6t + 2$$

### Time Math
To calculate complexity we need to apply few principles:

* **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the 
BigO notation. After removing constants, the equations becomes:

$$T(n) = n + 1$$

* **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:

$$T(n) = n$$

Which can finally be represented as: 

$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution. This algorithm mainly uses:

`out`  
`x`  
`y`  
`z`  
`result`  
`loop control variables`  
The array/string `out` stores one result for each test case. If there are `t` test cases, then `out` stores `t` values. The remaining variables require only constant memory.
Therefore, the space equation becomes:

$$S(n) = t + 5$$

After applying principles for calculating complexity, the equation becomes:

$$S(n) = t$$

Which can be finally represented as:

$$O(t)$$

### Auxiliary Space Math
To calculate the auxiliary space complexity, we consider only the extra memory created by the algorithm and exclude the input values. Since the algorithm creates the `out` array/string to store all results before displaying them, the auxiliary space equation becomes:

$$AS(n) = t + 5$$

After applying principles for calculating complexity, the equation becomes:

$$AS(n) = t$$

Which can be finally represented as:

$$O(t)$$

# Edge Cases
### 1. Maximum-Cap Boundary Squared Multiplication
When the lookup coordinates sit at the absolute ceiling of the system constraints (e.g., $y = 10^9$ or $x = 10^9$):
* **Inside the algorithm:** The mathematical formula relies on calculating the perfect square of the layer boundary (e.g., $\text{layer}^2 = (10^9)^2 = 10^{18}$) to locate the base value of the ring.
* **Result**: Arithmetic Integer Overflow. If these algebraic squaring operations are executed using standard signed 32-bit integer variables, the maximum capacity ($2 \times 10^9$) is violently exceeded. The binary bits will wrap around, producing a corrupted negative value or zero, completely breaking the offset calculations. The formula must be evaluated using 64-bit primitive types (`long long` in C++ or `long` in Java).
### 2. Perfect Diagonal Identity Splitting 
If a test case requests a coordinate that sits perfectly on the diagonal axis line where the row index exactly equals the column index (e.g., $y = 4, x = 4$):
* **Inside the algorithm:** The logic splits behaviors based on whether the row or the column is larger ($\max(y, x)$) to determine which leg of the L-shaped layer the cell resides on. When they are perfectly equal, a naive strict inequality check (`like if (x > y) ... else if (y > x) ...`) will fail to capture the coordinate.
* **Result**: Null/Default Fall-Through Error. If the equality state is not intentionally handled by a catch-all assignment, the program can bypass the addition/subtraction offsets entirely, returning a default 0 or an unadjusted layer corner square value.
### 3. Asymmetric Axis-Pinned Minimums 
If the lookup target is pinned to the absolute outer perimeter edge of the infinite coordinate system (e.g., $y = 1, x = 5$ or $y = 5, x = 1$):
* **Inside the algorithm:** The code evaluates offsets by calculating distances to corners or midpoints. For example, calculating the step difference between the coordinate and the layer limit involves evaluating structural boundaries like $(\text{layer} - y)$ or $(\text{layer} - x)$.
* **Result**: Off-By-One Indexing Inversions. When a coordinate is exactly $1$, edge-distance properties hit their mathematical extremes (e.g., $\text{distance} = \text{layer} - 1$). If your formula’s tracking logic handles inclusive/exclusive index adjustments incorrectly, these outer-fringe rows and columns will consistently trigger off-by-one errors, printing adjacent cell values instead of the target.
### 4. Non-Positive Grid Violations 
While the theoretical constraints dictate $1 \le y, x \le 10^9$, if a corrupted pipeline or fuzz-testing script injects a zero or negative coordinate (e.g., $y = 0, x = 5$):
* **Inside the algorithm:** The maximum layer boundaries calculation will evaluate $\max(0, 5) = 5$, assuming a valid layer 5 grid. However, downstream offset subtraction logic like $(\text{layer} - y)$ will evaluate to $(5 - 0) = 5$.
* **Result**: Out-of-Bounds Logical Underflow. Because a zero-indexed row doesn't physically exist in this 1-indexed coordinate space, the math will overshoot the real limits of layer 5 and output an impossible number belonging to an entirely different layer, breaking the integrity of the math engine.