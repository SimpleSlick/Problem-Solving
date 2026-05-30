# Problem Statement
You are given a linear sequence of $n$ integers. Your objective is to enforce a strict structural rule across the entire dataset: every data point in the sequence must be at least as large as the data point immediately preceding it. To achieve this alignment, you can apply an incremental operation to any individual element, increasing its value by exactly 1 unit per operation. Your goal is to calculate the absolute minimum number of incremental operations needed to fix the sequence.

### The Rules and Constraints
* ***Directional Validation:*** For any position $i$ in the sequence (where $i > 0$), the value must satisfy the condition: $\text{Element}[i] \ge \text{Element}[i-1]$.

* ***Single-Direction Modification:*** You can only add value to elements to bring them up to standard; you are completely forbidden from decrementing (subtracting from) any values.

### Example Behavior:
Consider an input configuration sequence consisting of `[3, 2, 5, 1, 7]` with a size limit of $n = 5$:

* The system evaluates the transition from `3` to `2`. To satisfy the sorting rule, the `2` must be raised to match the previous maximum of `3` (requires 1 operation, changing the element to `3`).

* The system moves forward. `5` is already greater than `3`, so no operations are needed.

* The system evaluates the transition from `5` to `1`. The 1 must be raised to match the running maximum of `5` (requires 4 operations, changing the element to `5`).

he sequence is now `[3, 3, 5, 5, 7]`. The total operations combined equal $1 + 4 = 5$.

# The Trap
The most instinctive way to simulate this is to literally mimic the problem's description. You would write a loop that steps through the array. Whenever you find an element that is smaller than its predecessor ($\text{Element}[i] < \text{Element}[i-1]$), you would use a nested while loop to add 1 to it over and over again, incrementing a global moves counter each time, until it finally becomes equal to the previous number.

### Why this approach is bad

* **The Simulation BottleNeck:** The system constraints indicate that individual numbers inside the array can be as large as $10^9$, and the array size $n$ can be up to $2 \cdot 10^5$.
    * Imagine a worst-case sequence like `[1000000000, 1]`.
    * If you use an inner loop to increment the `1` by one unit at a time, your code will have to execute one billion operations for just a single pair of numbers!
    * This repetitive incrementing will cause your program to lag massively, instantly triggering a Time Limit Exceeded (TLE) failure.

* **The Arithmetic overflow:** Because the values inside the array are so large ($10^9$) and the array is quite long, the total number of operations required across the whole sequence can easily add up to a massive number that exceeds $2^{31}-1$ (approx. $2.14 \times 10^9$).
    * If you store your total `moves` counter inside a standard 32-bit integer variable, the value will overflow.
    * Result: The counter will silently warp into a negative number or a completely incorrect value, corrupting your final result.

# The Algorithm
<pre>
Step-1: Start
Step-2: Input
          size_array
Step-3: Set
          num_arr[] = size(size_array)
Step-4: Loop
          i < num_arr.length
Step-5: Input
          num_arr[i]
          [Repeat Step-4]
Step-6: Set
          total_moves = 0
Step-7: Loop
          i < num_arr.length
Step-8: Set
          current = num_arr[i]
          previous = num_arr[i - 1]
Step-9: if current < previous
              gap = previous - current
              total_moves += gap
              num_arr[i] = previous
              [Repeat Step-7]
Step-10: Display
           total_moves
Step-11: End
</pre>

# The Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The first loop used for taking array input contributes `n` operations, while the second loop used for comparison and updating operations contributes another `n` operations. Inside the second loop, the comparison and update operations also contribute approximately `n` operations, while the remaining statements are constant operations. Therefore, the time equation becomes:
$$T(n) = 3n + 3$$

### Time Math
To calculate complexity we need to apply few principles:
1. **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big O$ notation. After removing constants, the equations becomes:  
$$T(n) = n + 1$$

2. **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:  
$$T (n) = n$$


Which can finally represented as: 
$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution. This algorithm mainly uses:

* an array `num_arr[]` of `size_array`
* a variable `total_moves`
* a variable `current`
* a variable `previous`
* a variable `gap`

So, the equation for space complexity becomes:
$$S(n) = 2n + 4$$

After simplifying and removing constants, the total space complexity becomes:
$$O(n)$$

For auxiliary space complexity:
* the input array `num_arr[]` is excluded because it is part of the input
* only extra variables are counted.

So, the auxiliary space complexity equation becomes
$$AS(n) = 4$$
After removing constants, the auxiliary space complexity becomes:
$$O(1)$$

# Edge Cases
### 1. Pointer Out-Of-Bounds on Initial Evaluation
When the loop in Step-7 begins executing at its very first index configuration (assuming a standard array start where $i = 0$):
* Inside the algorithm: In Step-8, the program attempts to retrieve the state variable `previous = num_arr[i - 1]`. For the first element, this calculation tries to look up `num_arr[-1]`.
* Result: Critical System Crash. Programming environments do not allow accessing negative indices within standard arrays. The runtime engine will immediately halt execution and throw an `ArrayIndexOutOfBoundsException` or a segmentation fault, crashing the entire application before any math takes place. To be safe, the evaluation loop must explicitly begin at index $i = 1$.

### 2. The Minimum Valid Sub-Array Boundary
If the user inputs an array size containing exactly one individual element (size_array = 1):
* Inside the algorithm: Step-3 allocates an array of size 1, and Step-4/5 populates it. If your Step-7 loop index starts at $i = 1$ (to avoid the crash mentioned in Edge Case #1), the condition `i < num_arr.length` ($1 < 1$) evaluates to false immediately.
* Result: The logic bypasses the processing framework completely. It jumps down to Step-10 and displays `total_moves`, which remains initialized at `0`. In this scenario, the output `0` is completely correct because a single-element sequence is mathematically already sorted, but it represents a dead execution path.

### 3. Cumulative Value Overflow
If the array contains a large set of numbers that are heavily disorganized in descending order (e.g., $n = 2 \cdot 10^5$, with elements cascading from $10^9$ down to $1$):
* Inside the algorithm: Step-9 calculates the `gap` using direct subtraction and accumulates it via `total_moves += gap`. Across thousands of large drops, the combined sum of these gaps will rapidly grow and cross the threshold limit of a signed 32-bit integer ($2^{31}-1$).
* Result: Arithmetic Integer Overflow. If `total_moves` is assigned to a standard 32-bit variable type, the memory bits will overflow, causing the number to wrap around into a nonsense negative value. Step-10 will consequently output an invalid or negative total operational count.

### 4. Void Collection Inversion Errors
If the user types in an invalid non-positive scale limit like `0` or `-5`:
* Inside the algorithm: In Step-3, the execution block attempts to initialize a data structure using a negative dimension: `num_arr[] = size(-5)`.
* Result: Memory Allocation Failure / Crash. Modern hardware environments cannot build physical memory frameworks with negative allocations. The algorithm will abort instantly at Step-3 with a termination warning (such as `NegativeArraySizeException` in Java), completely failing to reach the processing loops.