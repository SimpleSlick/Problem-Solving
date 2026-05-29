# Problem Statement
You are given a primary rectangular structural surface with dimensions measuring $n \times m$ units. Your goal is to completely cover this entire surface area using smaller, uniform square tiles, where each tile has a fixed edge length of $a$ units. You need to find the absolute minimum number of square tiles required to guarantee total coverage.

### The Rules
* **No Modifying Tiles:** You are strictly forbidden from breaking, cutting, or splitting the square tiles. Every tile must be placed as a whole unit.

* **Alignment:** The edges of the square tiles must be placed perfectly parallel to the borders of the primary rectangular surface.
* **Overhang Allowed:** The total combined tile surface area is allowed to extend beyond the boundary edges of the primary rectangle, but the rectangle itself must be completely hidden beneath the tiles.

### Input requirements:
* A single line containing three positive integers: $n$ (length), $m$ (width), and $a$ (the size of the square tiles).

### Output Goal
Calculate and print a single integer representing the lowest quantity of full square tiles needed to fully overlay the area.

# The Trap
The most instinctive, brute-force way to solve this is to treat it as a simulation. You would write nested loops that manually place one tile at a time along the length $n$, and then along the width $m$, incrementing a counter variable until the entire coordinate plane of the rectangular surface is covered. Alternatively, a naive math approach would be to calculate the total area of the rectangle ($n \times m$) and divide it by the area of a single tile ($a \times a$).

### Why this approach is Bad?
* **The Infinite Simulation Bottleneck:**  The system constraints specify that the dimensions $n, m,$ and $a$ can be as massive as $10^9$ ($1,000,000,000$). If you attempt to use loops to place tiles one by one, your program will execute billions of loop cycles. This forces the processor to time out, leading to a fatal Time Limit Exceeded (TLE) failure.

* **The Area Division Trap:**  Dividing the total area ($\frac{n \times m}{a \times a}$) is mathematically incorrect. Because you cannot cut or break tiles, any leftover space along an edge requires an entire extra tile to cover the overhang. Pure area division completely ignores this fractional edge geometry.

* **Arithmetic Overflow:** Even if you try to compute the area mathematically using standard 32-bit integer variables, multiplying $n \times m$ (where both can be $10^9$) results in $10^{18}$. This value massively overflows the capacity of a standard integer data type, corrupting the numbers into negative values and breaking the output logic completely.

# The Algorithm
<pre>
Step-1: Start
Step-2: Input
          n, m, a
Step-3: Process
          side1 = (n + a - 1) / a
          side2 = (m + a - 1) / a
          result = side1 * side2
Step-4: Display
          result
Step-5: End
</pre>

# Time Complexity Analysis
To calculate how fast this algorithm runs, we analyze the operations performed throughout the algorithm and represent them mathematically. The algorithm performs one input operation, three processing operations, and one display operation, while no loops or recursive calls are present. Therefore, all operations contribute constant-time operations. Hence, the time equation becomes:
$$T(n) = 5$$

### Time Math
1. **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big O$ notation. After removing constants, the equations becomes:  
$$T(n) = 1$$

2. **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to: 
$$T (n) = 1$$

Which is finally represented as: 
$$O(1)$$

### Space Math
To calculate how much memory this algorithm uses, we analyze the variables and storage used during execution. The algorithm stores the input values `n`, `m`, and `a`, along with the processing variables `side1`, `side2`, and `result`. Since no arrays, recursion, or dynamically growing data structures are used, all memory usage remains constant throughout execution. Therefore, the space equation becomes:
$$S(n) = 6$$

After simplifying and removing constants, the total space complexity becomes: $O(1)$

This is also the auxiliary space complexity the difference would be in the variable count for space complexity. The variable which are counted are `side1`, `side2` and `result`. The equation finally becomes:
$$S(n) = 3$$
After simplifying and removing the constant, the auxiliary space complexity would the same as total space complexity.

# Edge Cases
### 1. Marco-Scale Division
When dealing with maximum constraint boundaries where the rectangle and tile sizes approach the absolute upper limit ($n, m, a = 10^9$):
* ***Inside the algorithm:*** If the code attempts to compute the total area of the plane using standard 32-bit integer variables (e.g., executing `n * m`), the resulting product reaches up to $10^{18}$.

* ***Result:*** Arithmetic Overflow. The data type completely exhausts its bit capacity and wraps around into a completely corrupted negative number or incorrect small integer. The system will output a garbled or impossible tile count. To prevent this, calculations must be cast into 64-bit integer types (like `long long` in C++ or `long` in Java).

### 2. Large Tile Over-Scale Boundary
If the size of a single square tile is actually larger than one or both dimensions of the rectangular surface itself (e.g., $n = 3, m = 4, a = 5$):
* ***Inside the algorithm:*** The division mechanics must evaluate how many times a $5 \times 5$ tile fits into a length of 3 and a width of 4. A naive integer division calculation (`n / a`) drops the fraction and incorrectly calculates `0`.
* ***Result:*** Logical Underflow / Zero-Count Bug. If your ceiling division logic isn't robust, the formula might conclude that `0` tiles are needed because the rectangle is smaller than a single tile. The correct mathematical behavior must force both independent axis counters up to exactly `1`, yielding a final required output of $1 \times 1 = 1$ tile.

### 3. Perfect Fit vs. Fractional Remainder Transitions
If the dimensions are fractional or if you use floating-point types to calculate ceiling division (e.g., `ceil(n / a)`):
* ***Inside the algorithm:*** Computers struggle with absolute precision when casting massive integers into floating-point numbers (`double` or `float`). For example, a large number that should divide perfectly might be stored internally as `99.999999999` instead of `100`.
* ***Result:*** Off-By-One Logic Error. Passing that floating-point value into a ceiling function like `ceil()` will mistakenly round it up to `101` instead of `100`, forcing the program to output an incorrect tile count. The calculation must be done using pure integer arithmetic to remain safe.

### 4. Non-Positive Constraints
While the system constraints state $1 \le n, m, a \le 10^9$, if an unvalidated script passes a zero or negative value:
* ***Inside the algorithm:*** If the tile size $a = 0$, any division step in the formula will attempt to evaluate a value divided by zero (e.g., `n / 0`). If $n$ or $m$ are negative, the tile count will evaluate to a negative quantity.
* ***Result:*** Runtime Exception / Crash. Attempting to divide by zero causes the execution engine to crash instantly with a critical system error (such as `ArithmeticException` in Java). If negative numbers are processed without crashing, the program outputs a meaningless negative tile count.