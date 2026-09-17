# Problem Statement
You are given a fixed sequence length $n$. Each slot in this sequence can independently take on one of two mutually exclusive state values (a binary decision space). Your objective is to determine the total number of distinct global state configurations that can be formed across the entire length of the sequence, reduced under a standard large prime modulo field.
### The Rules & Modular Constraints:
- **Independent Binary Choice:** Every position from index $1$ through $n$ possesses exactly $2$ possible valid states, unconstrained by neighboring choices ($2 \times 2 \times \dots \times 2 = 2^n$).
- **Scale Bounds:** The sequence length $n$ can reach magnitudes up to $10^6$.
- **Modular Normalization:** Because the raw cardinality grows exponentially and exceeds primitive hardware representations, the final tally must be computed modulo $10^9 + 7$.

### Input Requirements:
A single integer $n$ ($1 \le n \le 10^6$), representing the exact sequence depth.

### Output Goal:
Return a single integer representing the total configuration count: 
$$2^n \pmod{10^9 + 7}$$

# The Trap
### The Obvious Approach:
The most intuitive approach is to calculate the final power directly using standard arithmetic primitives or built-in power functions (such as pow(2, n) in C++, Math.pow(2, n) in Java, or 2 ** n in Python), and then apply the modulo operator to the finished product: result = pow(2, n) % (10^9 + 7). Alternatively, a developer might attempt a naive recursion or loop that multiplies by 2 repeatedly $n$ times without intermediate reduction, intending to take the modulo only at the very end.
### Why this Approach is Bad:
- **Catastrophic Arithmetic Overflow ($2^{10^6}$ Scale):** Standard 64-bit integer registers (long long or uint64_t) max out at $2^{64} - 1 \approx 1.84 \times 10^{19}$.
  - The input constraint allows $n$ up to $10^6$. The true mathematical value of $2^{10^6}$ is a colossal number containing over $300{,}000$ decimal digits.
  - In compiled languages like C, C++, or Java, computing this unconstrained value causes immediate bit overflow, wrapping around to zero or garbage values within the first 64 steps. Floating-point functions (double pow()) will promptly overflow to Infinity, losing all low-order precision and yielding 0 after modulo.
- **Arbitrary-Precision Memory and CPU Stall ($O(n^2)$ BigInt Drag):** In languages with dynamic arbitrary-precision integers (like Python), attempting to compute the entire $2^{10^6}$ number before applying % 1000000007 forces the runtime to allocate huge blocks of memory to hold the gigantic integer. Multiplying increasingly large numbers scales poorly in time, causing heavy latency and high memory allocation penalties.
- **The Bit-Shift Truncation Trap:** Writing 1 << n works cleanly for small exponents, but bit-shifting beyond the width of the hardware register (e.g., shifting a 32-bit or 64-bit integer by $n \ge 64$) triggers hardware truncation or undefined behavior in C/C++, silently yielding 0.

# Algorithm
```
BEGIN ALGORITHM
    (Long) -> length <- INPUT()
    (Long) -> result = 1, mod = 1000000007
    
    FOR i <- 0, i < length, i++
        result <- (result * 2) % mod
    END FOR

    DISPLAY result
END ALGORITHM

```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The FOR loop runs n times, and during each iteration the algorithm performs the calculation result = (result * 2) % mod, contributing another n operations, while the input, initialization, and display statements are constant operations. Therefore, the time equation becomes:
$$T(n) = 2n + 3$$

To calculate complexity we need to apply few principles:

- **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the BigO notation. After removing constants, the equations becomes:

$$T(n) = n + 1$$

- **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:

$$T(n) = n$$

Which can finally be represented as:

$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution. This algorithm mainly uses:
- length
- result
- mod
- Loop control variable i

No arrays, strings, recursion, or dynamically growing data structures are created. All variables require a fixed amount of memory regardless of the value of length. Therefore, the space equation becomes:
$$S(n) = 4$$

After applying principles for calculating complexity, the equation becomes:
$$S(n) = 1$$

Which can be finally represented as:
$$O(1)$$

### Auxiliary Space Math
The algorithm does not create any additional data structures or dynamically allocate memory during execution. Therefore, the auxiliary space equation becomes:
$$AS(n) = 4$$

After removing constants:
$$AS(n) = 1$$

Which can be finally represented as:
$$O(1)$$

# Edge cases
### 1. The Full Bit-Width Ceiling
When computing the answer at the absolute maximum constraint limit ($n = 10^6$):
- **Inside the algorithm:** The power calculation executes $10^6$ iterations in a linear loop, or $\approx 20$ squarings in binary exponentiation.
- **Result:** Delayed Modular Application / Register Overflow. If modular reduction is applied lazily (e.g., waiting every few iterations or computing `(ans * 2)` inside a 32-bit container without intermediate modulo reduction), the running variable will silently overflow $2^{31} - 1$ within 31 iterations. The reduction $\pmod{10^9 + 7}$ must occur strictly at every single multiplication step, and intermediate products must be held in 64-bit primitives (`long long` in C++ or `long` in Java) to safeguard against overflow during multiplication.
### 2. Primitive Bit-Shift Truncation
Using binary left-shift operators as a performance shortcut (e.g., `1 << n` or `1LL << n`):
- **Inside the algorithm:** In standard machine architectures, shifting a 64-bit value by an offset $n \ge 64$ invokes undefined behavior in C/C++, or uses only the lowest 6 bits of the shift count in Java (n & 63).
- **Result:** Silent Cyclic Zeroing. A shift of `1LL << 64` evaluates to `1`, and `1LL << 65` evaluates to `2`, producing wildly incorrect cyclic results rather than the true powers of two. Shift operators cannot replace loops or modular exponentiation engines for exponents larger than the physical register bit-width.
### 3. The Minimal Base Boundary 
Evaluating the smallest allowed constraint value:
- **Inside the algorithm:** A binary exponentiation algorithm handles the loop condition `while (n > 0)` or checks the lowest bit `n & 1`.
- **Result:** Off-By-One Base Initialization. If the loop or recursion logic seeds the answer with 2 instead of the multiplicative identity 1, or if decrement logic halts early without processing the single bit, the function can return `4`, `0`, or skip the modular reduction entirely. For $n = 1$, the function must strictly return $(1 \times 2) \pmod{10^9 + 7} = 2$.
### 4. Zero-Exponent Null Boundary 
While the stated problem bounds enforce $1 \le n \le 10^6$, test harness edge-fuzzing or sub-routine calls may supply $n = 0$:
- **Inside the algorithm:** Mathematically, a set of length $0$ contains exactly $1$ configuration (the empty sequence $\epsilon$), and $2^0 \equiv 1 \pmod{10^9 + 7}$.
- **Result:** Zero Output Inversion. Implementations that enforce hard-coded loop passes (e.g., `do ... while`) or initialize default return counters to `0` will incorrectly output `0` instead of the mathematically sound identity 1. An exponentiation routine must cleanly yield 1 when the exponent is zero.