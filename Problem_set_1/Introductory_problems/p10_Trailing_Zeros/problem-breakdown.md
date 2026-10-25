# Problem Statement
Find how many zeros are sitting at the very end of the huge number $n!$ ($n$ factorial, which is $1 \times 2 \times 3 \times \dots \times n$). You need to count only the zeros at the end without calculating the full factorial multiplication, because the number gets too massive to store.

### Input Requirements
* A single integer $n(1 \leq \ n \ 10^9)$

### Output Goal
Return a single integer showing the total count of trailing zeros in $n!$

# The Trap
### The Obvious Approach:
The most straightforward idea is to calculate the full factorial value $n! = 1 \times 2 \times 3 \times \dots \times n$ using a loop or recursion. Once the full multiplication is finished, you count the trailing zeros by repeatedly checking the last digit with modulo (`total % 10 == 0`) and dividing by 10 (`total = total / 10`), or by converting the huge number to a string and counting the zeros from right to left.
### Why this Approach Fails:
* *Catastrophic Number Explosion:*
  * Factorials grow at an astronomical rate.
  * A standard 64-bit integer (`unsigned long long` in C++ or `long` in Java) overflows at $21!$.
  * Even a modest input like $n = 100$ produces a number with 158 digits. At the upper limit of $n = 10^9$, computing and storing the exact value of $10^9!$ is physically impossible on any real computer—the resulting number would contain billions of digits and consume gigabytes of memory just to hold the digits of a single number.
* **Guaranteed Time Limit Exceeded (TLE):**
  * Even if you use arbitrary-precision libraries (like Java's `BigInteger` or Python's auto-expanding integers), multiplying large numbers $10^9$ times takes far too long.
  * A modern processor can run roughly $10^8$ basic operations per second. A loop that iterates $10^9$ times doing huge multiplication operations will blow way past the standard 1.00-second time limit.
* **Redundant Calculation:**
  * You do not care about any of the actual non-zero digits in $n!$.
  * Calculating and storing the entire front portion of the number just to see a few zeros at the tail end is completely unnecessary work.

# The Logic Shortcut
A trailing zeros is formed by the factor ($10 = 2 \times 5$). Since the factors of 2 occur more frequently than factors of 5 in a factorial, the number of trailing zeros depends on the number of 5.

### Formula:
[
$Z(n!) = \left\lfloor\frac{n}{5}\right\rfloor +
\left\lfloor\frac{n}{25}\right\rfloor +
\left\lfloor\frac{n}{125}\right\rfloor + \cdots$
]

### Example:
Input: (n = 100)

| Power of 5 | Calculation | Result |
| :--- | :--- | ---: | 
| (5) |  (100 / 5) | 20 |
| (25) |  (100 / 25) | 4 |
| (125) |  (100 / 125) | 0 |

Therefore, $Z(100!) = 20 + 4 = \boxed{24}$

# The Algorithm
```
BEGIN ALGORITHM
    (Long) -> n <- Input()

    result <- 0
    temp_num <- 0

    FOR i <- 5, i <= n, i *= 5
        temp_num <- n / i
        result += temp_num
    END FOR

    DISPLAY result
END ALGORITHM
```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The loop starts at 5 and multiplies i by 5 after each iteration. Therefore, the loop executes approximately \(\frac{n}{5}\) times. During each iteration, the algorithm performs a division and an addition, contributing approximately 2 operations per iteration, while the remaining statements are constant operations. Therefore, the time equation becomes:

$$T(n) = 2\frac{n}{5} + 3$$

To calculate complexity we need to apply few principles:

- **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the BigO notation. After removing constants, the equations becomes:

$$T(n) = n + 1$$

- **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:

$$T(n) = n$$

Which can finally be represented as:

$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much memory the algorithm uses during execution. This algorithm mainly uses:
* `n` → stores the input number
* `result` → stores the accumulated result
* `temp_num` → stores the temporary quotient
* `i` → loop control variable

No arrays, recursion, or dynamically growing data structures are created. All variables require a fixed amount of memory regardless of the input value. Therefore, the space equation becomes:

$$S(n) = 4$$

After applying the principles of space complexity, the equation becomes:

$$S(n) = 1$$

Which can be finally represented as:

$$O(1)$$

### Auxiliary Space Math
To calculate the auxiliary space complexity, we exclude the input variable `n` and consider only the additional memory used during execution. The algorithm uses three auxiliary variables: `result`, `temp\_num`, and `i`. Since their memory usage remains constant, the auxiliary space equation becomes:

$$AS(n) = 3$$

After removing constants, the equation becomes:

$$AS(n) = 1$$

Which can be finally represented as:

$$O(1)$$

## Edge Cases
### 1. Integer Overflow in Power Generation
When implementing Legendre's formula by computing powers of $5$ in the denominator (`n / d` where `d = 5, 25, 125, ...`):
* **Inside the algorithm:** If `d` is stored as a standard signed 32-bit integer (`int` in C++ or Java), multiplying by $5$ in the step after $5^{13} = 1{,}220{,}703{,}125$ attempts to evaluate $5^{14} = 6{,}103{,}515{,}625$.
* **Result:** **32-Bit Signed Integer Overflow / Infinite Loop.** Because $6{,}103{,}515{,}625 > 2^{31} - 1$ ($2{,}147{,}483{,}647$), the value overflows into negative numbers. In a loop condition like `while (n / d > 0)` or `while (d <= n)`, `d` becomes negative, triggering an infinite loop or arithmetic exception. To avoid overflow, either declare the denominator `d` as a 64-bit integer (`long long` / `long`) or divide the numerator iteratively:

$$n = \lfloor n / 5 \rfloor, \quad \text{ans} += n$$

### 2. The Upper Constraint Ceiling ($n = 10^9$)
Evaluating the problem's maximum defined bound:
* **Inside the algorithm:** $n = 10^9$ requires evaluating all powers of $5$ up to $5^{12} = 244{,}140{,}625$.
* **Result:** **Off-By-One Iteration Stoppage.** A loop written as `while (d < n)` instead of `while (d <= n)` will skip valid contributions if a power equals $n$. Although no exact power of $5$ equals $10^9$ ($5^{12} < 10^9 < 5^{13}$), off-by-one boundary comparisons can cause missed additions for other clean power values. Dividing $n$ repeatedly until $n = 0$ ensures every valid power tier is counted without boundary checks.

### 3. Numbers Below the First Multiple of 5 ($1 \le n < 5$)
Evaluating small inputs such as $n = 1, 2, 3,$ or $4$:
* **Inside the algorithm:** The product contains no multiples of $5$, meaning no trailing zeros can be formed.
* **Result:** **Premature Underflow or Division Errors.** If an algorithm assumes at least one factor of $5$ exists and initializes a counter with `1`, or if division routines do not handle $n < 5$ gracefully, it will return incorrect non-zero values. Evaluating $\lfloor n / 5 \rfloor$ directly yields $0$, which must be returned cleanly.

### 4. Zero-Valued Factorial Boundary ($n = 0$)
While the problem statement enforces $1 \le n \le 10^9$, edge testing or helper function calls may pass $n = 0$:
* **Inside the algorithm:** Mathematically, $0! = 1$, which contains exactly $0$ trailing zeros.
* **Result:** **Zero Identity Failure.** A post-test loop (such as `do ... while`) executes at least once and may produce an off-by-one count. A pre-test loop (`while (n > 0)`) immediately skips execution and returns `0`, matching the mathematical definition.

### 5. Multi-Factor Power Multiples ($n = 25, 125, 625, \dots$)
Inputs that hit exact square or cubic powers of $5$:
* **Inside the algorithm:** At $n = 25$, the number $25$ contributes two factors of $5$ ($5 \times 5$).
* **Result:** **Under-counting Zeros.** Naively counting how many numbers are multiples of $5$ via $\lfloor n / 5 \rfloor$ yields $5$ zeros for $n = 25$. However, $25!$ actually has $6$ trailing zeros because $25$ provides an extra factor of $5$. The algorithm must account for higher powers via cumulative divisions ($\lfloor 25/5 \rfloor + \lfloor 25/25 \rfloor = 5 + 1 = 6$).