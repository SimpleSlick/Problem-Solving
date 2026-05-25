# Problem Statement
Given a starting positive integer $n$, track its transformation over a series of steps until it reduces to a specific terminal value. At each step, the value of $n$ changes based on whether it is even or odd.

### Rules of Transformation:  

- **Condition A** (Even): If the current number is divisible by 2, update the number by halving it.
- **Condition B** (Odd): If the current number is not divisible by 2, update the number by scaling it by a factor of 3 and adding a unit value.
- **Termination**: The process repeats sequentially until $n$ reaches the value of 1.

### Constraints:
&emsp; $1 \le n \le 10^6$

### Example Behavior:
If the initial input starts at $n = 3$, the value undergoes the following chain of transitions before halting:  

&emsp; $3 \rightarrow 10 \rightarrow 5 \rightarrow 16 \rightarrow 8 \rightarrow 4 \rightarrow 2 \rightarrow 1$

Your goal is to simulate this progression and determine the sequence generated for any given input $n$.

# The trap

#### The obvious approach:  
The most straightforward way to solve this is to use a simple `while` loop that runs as long as $n$ is greater than 1. Inside the loop, you can use an `if-else` statement to check if the number is even or odd, apply the rule, and print or store the result.

#### Why this approach is dangerous (The BottleNecks):

1. **The Infinite Loop Risk:** This problem relies on un proven maths concept. We assume every number eventually reduces to 1, but it is not mathematically guaranteed. A naive loop has no safety net; if an input enters an infinite cycle or grows infinitely, the program would hang forever or crash due to timeout.

2. **The Memory Overflow Trap:** The sequence does not decrease smoothly. Even a relatively small starting input can skyrocket to massive intermediate values before finally crashing down to 1.

    * *Example:* Starting with $n = 27$ takes 111 steps and shoots all the way up to $9,232$ before recovering  
    * If the intermediate value exceeds the maximum limit of a standard integer data type (integer overflow), the number will become corrupted (often turning negative), causing the logic to break completely.

# The Algorithm (Program Flow)
<pre>
Step-1: Start
Step-2: Input n
Step-3: Display n
Step-4: Loop
            i != 1
Step-5: if n is even
            Update n = n / 2
        else
            Update n = 3 * n + 1
Step-6: Display n
        [Repeat Step-4]
Step-7: End
</pre>

# Time complexity analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed in the loop and represent them mathematically. The loop operation, conditional block, and display statement each contribute n operations, while the remaining statements are constant operations. Therefore, the time equation becomes:
$$T(n) = 3n + 2$$

### Time Math:
To calculate the time complexity we need apply few principles:  
1. ***Ignore Constants:*** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big\ O$ notation. After removing constants, the equation becomes:
$$T(n) = n + 1$$

2. ***Power Dominance:*** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:
$$T(n)=n$$
&emsp;&emsp;Which is finally represented as:
$$O(n)$$

### Space Math:
To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution.  
This algorithm mainly uses:  
* one variable $n$

No arrays, lists, recursion stack, or dynamically growing memory structures are used. The memory usage remains constant regardless of the input size.

So, the space equation becomes:
$$S(n) = 1$$

Which can finally represented as:
$$O(1)$$
This means the algorithm uses constant space

# Edge Cases
1. The Input is `0` (Infinite Loop)  
    If the user inputs `0`, the code enters the `while(n != 1)` loop.   
    * Inside the loop, it checks `if(n % 2 == 0)`. Since `0 % 2` is `0`, it executes `n = 0 / 2;`, which sets `n` back to 0.
    * The loop condition 0 != 1 remains true forever.
    * Result: Infinite Loop. Your program will hang indefinitely and never terminate.

2. Negative Numbers (Infinite Loop or Negative Spirals)  
If the user inputs a negative number (e.g., -5), the code checks if `-5 != 1` (which is true) and enters the loop.
    * Negative Even Numbers: `-6 / 2` becomes -3.
    * Negative Odd Numbers: In Java, `-5 % 2` results in -1 (not 1), so it triggers the else block. `(-5 * 3) + 1` becomes -14. 
    * Result: The numbers will bounce around entirely in the negative spectrum. Because they can never become 1, the program enters an Infinite Loop. Interestingly, negative Collatz sequences are known to trap themselves into one of three specific negative repeating loops (like `-1 → -2 → -1`).
3. Empty Input / Non-Numeric Input (Crash)  
If the user presses Enter without typing anything (empty input) or types text like `"apple"`:
    * `scan.nextLong()` expects a valid 64-bit integer.
    * Result: The program instantly crashes with an `InputMismatchException`.

4. Extremely Large Numbers (Arithmetic Overflow)  
Even though you smartly used `long n` (which holds numbers up to $\approx 9.22 \times 10^{18}$), the Collatz sequence makes values spike drastically before they shrink.
    * If a user inputs a number close to the maximum limit of a `long`, the `n * 3 + 1` step can exceed the maximum storage capacity.
    * Result: In Java, overflowing a signed `long` causes the number to wrap around into a negative number. Once it becomes negative, it falls into Danger Zone #2 and enters an Infinite Loop.
