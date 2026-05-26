# Problem Statement
You are given an upper boundary limit, $n$, and an incomplete collection containing $n - 1$ unique integers. Every integer in this collection is strictly supposed to fall within the expected contiguous range from $1$ to $n$. Your goal is to pinpoint the exact integer that is absent from the collection.

### The Scenario
* **Expected Elements:** All distinct values from $1$ up to $n$.
* **Actual Elements:** A scrambled stream of numbers containing all expected values except for one missing target.

### Output:
Isolate and display the single missing value.

# The Trap
### The obvious approach:
The most instinctive way to solve this is to look at the numbers one by one. You could sort the incoming stream of the numbers first and then loop through them to check where the consecutive chain breaks. Alternatively, you could create a secondary "checklist" (like an array or hash set) of size $n$, mark off each number as you see it, and then scan the checklist to find the unmarked slot.

### Why this approach is Bad (The Bottlenecks):
* **The time Bottleneck (Sorting):** If you sort the $n-1$ numbers first, a standard sorting algorithm will take $O(n \log n)$ steps. When $n$ becomes very large (e.g., $1,000,000$), sorting forces the computer to do millions of unnecessary operations just to find a single missing value.

* **The Space Bottleneck (The Checklist):** If you use a checklist array or hash set to keep track of the numbers, you are duplicating the data. For a large limit $n$, this approach steals a massive chunk of extra memory space ($O(n)$ extra space), which is highly inefficient for a problem that can be tracked in-place.

# The Math Shortcut
The problem asks us to find the missing number from a sequence of numbers ranging from $1$ to $n$.  

Instead of checking every number one by one, we can use a mathematical shortcut. We know that the sum of the first n natural numbers always follows a fixed formula:

### $$\sum_{i = 1}^{n} i = \frac{n(n + 1)}{2}$$

Using this formula, we can quickly calculate what the total sum should be if no number were missing.

After that:  
1. Calculate the expected sum using the formula.
2. Calculate the actual sum of the given input numbers.
3. Subtract the actual sum from the expected sum.

The remaining value will be missing number.

# The Algorithm
<pre>
Step-1: Start
Step-2: Input n
Step-3: Process 
                set actual_sum = 0 
                set expected_sum = n * (n + 1) / 2 
                set numbers[] = size(n - 1)
Step-4: Loop 
            i < n - i
Step-5: Read number[i]
Step-6: Update 
                actual_sum += numbers[i] 
            [Repeat Step-4]
Step-7: Display
            expected_sum - actual_sum
Step-8: End
</pre>

# Time complexity analysis
To calculate how fast the algorithm runs, we analyze operations performed inside the loop and represent them mathematically. The loop runs $n-1$ times, and during each iteration the program reads a number and updates the sum, contributing approximately $2$ operation per iteration, while the remaining statements are constant operations. Therefore, the time equation becomes: $$T(n) = 2n + 2$$

### Time Math
To calculate the time complexity we need to apply few principles:

1. **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big O$ notation. After removing constants, the equations becomes:  
$$T(n) = n + 1$$

2. **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to: 
$$T (n) = n$$

Which is finally represented as: 
$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution.
This algorithm mainly uses:  
* one variable `n`
* second variable `actual_sum`
* third variable `expected_sum` 

These are the constant-size variables: $3$

Then it also have dynamically growing memory structure; 
`numbers[]` and has size $n - 1$ because one number is missing.

So total memory becomes:
$$S(n) = n + 2$$

Which can be finally represented as Total Space Complexity:
$$O(n)$$

But in Auxiliary Space Complexity, the answer becomes: $O(1)$. Where the input variable and input array is ignored.

In Total Space Complexity, it uses growing memory space, and in auxiliary space complexity, it uses constant space.

# Edge Cases
While the mathematical formula works perfectly in theory, implementing this specific flow opens up a few practical vulnerabilities:

### 1. Large Input Boundary
Even if you use standard primitive types, the mathematical formula for calculating the expected sum can cause numbers to spike drastically.  
* If a user inputs a number $n$ close to or exceeding the maximum capacity of a 32-bit integer ($n \ge 65,536$), the operation `n * (n + 1) / 2` will exceed the maximum storage limits.
* Result: Arithmetic Overflow. In languages like Java, the number wraps around into an unpredictable or negative value. Ultimately, the subtraction will display an entirely incorrect or negative missing number.

### 2. Invalid Negative or Zero Inputs
If the user inputs a non-positive integer (e.g., $n = 0$ or $n = -5$):  
* The initialization block attempts to dynamically allocate an array of size $n - 1$ (which evaluates to a size of `-1` or `-6`).
* Result: System Crash. Programming languages cannot allocate memory blocks with a negative dimension. The runtime environment will immediately throw an exception (such as `NegativeArraySizeException` in Java) and terminate.

### 3. The Minimum Valid Boundary
If the user inputs the absolute minimum valid system boundary ($n = 1$):  
* **Inside the algorithm:** Step-3 sets the array size to $0$ ($n - 1 = 0$). When Step-4 runs, the loop condition instantly evaluates to false because $i < 0$ is untrue at the start.
* **Result:** The entire reading loop is completely bypassed. The program jumps straight to Step-7 and outputs `expected_sum` - `actual_sum` ($1 - 0 = 1$). While the final answer `1` is technically correct, no input stream is ever processed.

### 4. Premature Stream Termination (Hanging or Exception Crash)  
If the user specifies an input scale of $n$, but provides fewer numbers than the expected $n - 1$ elements in the stream:  
* **Inside the algorithm:** The loop condition in Step-4 will keep expecting elements until $i$ reaches $n - 1$.  
* **Result:** When Step-5 tries to execute `Read number[i]` on an empty stream, the algorithm will either hang indefinitely waiting for input that does not exist, or it will crash immediately with a stream termination error (like `NoSuchElementException`).