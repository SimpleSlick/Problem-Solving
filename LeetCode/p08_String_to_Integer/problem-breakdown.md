# Problem Statement
Convert a messy text string into a clean, signed 32-bit integer. You must manually read through the string from left to right, skip useless spaces, pick up the sign if there is one, read the numbers, and stop the moment an invalid character appears. Finally, clamp the number so it fits safely inside standard 32-bit limits without using built-in conversion functions like `parseInt()` or `stoi()`.

### Rules and Constraints
* **State 0 (Leading Whitespace):** Ignore and eat up any spaces at the very beginning.
* **State 1 (Sign Detection):** Check for an optional single `+` or `-`. If neither is there, assume the number is positive.
* **State 2 (Digit Extraction):** Accumulate contiguous numeric characters (`'0'` through `'9'`). The moment you hit a letter, dot, extra sign, space, or end of the string, stop reading immediately.
* **State 3 (Clamping to Hardware Limits):** If the resulting number is smaller than $-2^{31}$, clamp it to $-2^{31}$ ($-2{,}147{,}483{,}648$). If it is larger than $2^{31} - 1$, clamp it to $2^{31} - 1$ ($2{,}147{,}483{,}647$). If no digits were read at all, the answer is simply $0$.

### Input Requirements
* A single string `s` with a length between $0$ and $200$ characters.
* The string can contain uppercase/lowercase English letters, digits (`0-9`), spaces `' '`, signs `'+'`, `'-'`, and periods `'.'`.

### Output Goal
Return the final converted integer, clamped within $[-2^{31}, 2^{31} - 1]$.

# The Trap
The most common naive way to solve this is to first clean the string using regular expressions or built-in string methods (like trim() or string splits) to isolate the number part. After grabbing the digits as a substring, people often try to convert it directly into a standard integer using the formula result = result * 10 + digit, or by using wider data types like 64-bit integers (long / long long) or arbitrary-precision libraries (like Python's auto-expanding integers or Java's BigInteger), and only check whether it exceeds the 32-bit limits at the very end.
### Why this Approach Fails:
- **The Silent Overflow Crash:** If you multiply `result * 10` inside a standard signed 32-bit integer, a long string of numbers (for example, `"99999999999999999999"`) will burst past the $2{,}147{,}483{,}647$ limit almost immediately. In C++, signed integer overflow triggers undefined behavior; in Java, it quietly wraps around into meaningless negative numbers. Checking for overflow after doing `result * 10 + digit` is already too late.
- **The "Clean First, Parse Later" Pitfall:** Using regex or multiple string split passes to peel off spaces and signs is fragile and wasteful. In cases like `"+-12"`, `"   +0 123"`, or `"-1337c0d3"`, a messy string easily breaks rigid split rules because the valid number abruptly stops the exact moment an unexpected character appears (even if that character is another sign or space). Creating temporary sliced strings also wastes heap memory.
- **Cheating with Wider Types:** Storing intermediate values in a 64-bit integer (`long`) seems like an easy fix to avoid overflow before clamping. However, in real interview settings and standard low-level hardware constraints, relying on 64-bit storage is often explicitly forbidden or fails when the input contains hundreds of consecutive digits (which overflows even a 64-bit integer).

# The Algorithm
```
BEGIN ALGORITHM
    FUNCTION myAtoi(s -> String) -> Integer
        i <- 0

        WHILE (i < LENGTH(s) AND s[i] == '-')
            i++
        END WHILE

        sign <- 1
        IF (i < LENGTH(s) AND s[i] == '-')
            sign <- -1
            i++
        ELSE IF(i < LENGTH(s) AND s[i] == '+')
            sign <- 1
            i++
        END IF

        total <- 0
        (Long) -> limit

        IF sign == -1
            limit <- 2147483648
        ELSE
            limit <- 2147483647
        END IF

        WHILE(i < LENGTH(s) AND Character.IS_DIGIT(s[i]))
            digit <- s[i] - '0'
            
            IF (total > limit / 10 OR (total == limit / 10 AND digit > limit % 10))
                IF sign == -1
                    RETURN Integer.MIN
                ELSE
                    RETURN Integer.MAX
                END IF
            
            total <- total * 10 + (s[i] - '0')
            i++
        END WHILE
    
        RETURN CONVERT.Integer(total * sign)
    END FUNCTION
END ALGORITHM
```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The algorithm contains two while loops that process the input string, with each loop advancing through the string at most once. The first loop skips leading spaces, while the second loop processes the digits and performs arithmetic operations. Since these loops execute sequentially rather than being nested, each contributes n operations, while the remaining statements are constant operations. Therefore, the time equation becomes:

$$T(n) = n + n + 2 \rightarrow T(n) = 2n + 2$$

### Time Math
To calculate complexity we need to apply few principles:
1. **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big O$ notation. After removing constants, the equations becomes:

$$T(n) = n + 1$$

2. **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to: 

$$T(n) = n$$

Which can finally be represented as: 

$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much memory the algorithm uses during execution. This algorithm mainly uses:

- `s` → input string of length `n`
- `i` → loop control variable
- `sign` → stores the sign of the number
- `total` → stores the converted number
- `limit` → stores the maximum or minimum allowed integer value
- `digit` → stores the extracted digit

The input string requires `n` memory locations, while the remaining variables require constant memory. No additional arrays, dynamically growing data structures, or recursion stacks are created. Therefore, the space equation becomes:

$$ S(n) = n + 5 $$

After applying the principles of space complexity, the equation becomes:

$$ S(n) = n $$

Which can be finally represented as:

$$O(n)$$

### Auxiliary Space Math
To calculate the auxiliary space complexity, we exclude the input string `s` and consider only the additional memory used during execution. The algorithm uses five variables: `i`, `sign`, `total`, `limit`, and `digit`. These variables require a fixed amount of memory regardless of the input length.

Therefore, the auxiliary space equation becomes:

$$ AS(n) = 5 $$

After removing constants, the equation becomes:

$$ AS(n) = 1 $$

Which can be finally represented as:

$$O(1)$$

# Edge Cases
### 1. Multiple Signs or Displaced Signs 
The string contains more than one sign symbol, or a space exists between the sign and the first digit:
- **Inside the algorithm:** After picking up the first valid sign, the next character must be a numeric digit (`'0'`–`'9'`). If the character following the sign is another sign symbol or a space, digit parsing must never begin.
- **Result:** If sign detection runs in an unconstrained loop instead of reading at most a single character, the polarity flips multiple times or treats subsequent symbols as valid prefixes. Encountering any character other than a digit immediately after the sign must cause the reading phase to abort and safely return `0`.
### 2. Massive Leading Zeros
The input provides dozens or hundreds of consecutive zeros before reaching the significant digits or before the string terminates:
- **Inside the algorithm:** The parser iterates past repeated `'0'` characters without increasing the magnitude of `result`.
- **Result:** If bounds checking or length checking assumes an arbitrary limit on the total digit count (such as assuming a number fits in at most 10 characters), long sequences of zeros can trigger false overflow detections. The extraction loop must consume zero values cleanly without multiplying past zero, ensuring overflow logic only activates once positive magnitude accumulates.
### 3. Asymmetric Positive vs. Negative Overflow 
The accumulated number crosses 32-bit hardware boundaries, where the upper limit is $2{,}147{,}483{,}647$ and the lower limit is $-2{,}147{,}483{,}648$:
- **Inside the algorithm:** The running positive tally reaches $214{,}748{,}364$ (`INT_MAX / 10`), and the next incoming digit is evaluated.   
- **Result:** If the number is positive and the trailing digit is $> 7$, multiplying by $10$ exceeds $2{,}147{,}483{,}647$. If the number is negative and the trailing digit is $> 8$, the magnitude exceeds $-2{,}147{,}483{,}648$. The bounds check must happen before multiplying:

$$\text{total} > \frac{\text{INT\_MAX}}{10} \quad \text{or} \quad \left(\text{total} == \frac{\text{INT\_MAX}}{10} \text{ and } \text{digit} > 7\right) \implies \text{clamp to } \text{INT\_MAX}$$

$$\text{total} > \frac{\text{INT\_MAX}}{10} \quad \text{or} \quad \left(\text{total} == \frac{\text{INT\_MAX}}{10} \text{ and } \text{digit} \ge 8\right) \implies \text{clamp to } \text{INT\_MIN (if negative)}$$

### 4. Non-Digit Characters Before Numbers 
The sequence contains letters, punctuation, or symbols prior to encountering any digits:
- **Inside the algorithm:** The pointer skips initial whitespace, but encounters an alphabetic character or period instead of a sign or digit.
- **Result:** The specification states that conversion stops as soon as a non-digit character appears. The parser must immediately exit without searching further down the string, returning the baseline total of `0`.
### 5. Pure Whitespace or Empty Input 
The string contains no printable non-whitespace characters:
- **Inside the algorithm:** The whitespace loop shifts the index pointer until either a non-space character is found or the string boundary is reached.
- **Result:**If the loop condition checks `s[i] == ' '` without continuously validating `i < s.length()`, the index advances past the end of the string buffer and triggers a memory access fault. The pointer loop must enforce bounds verification on every advance and terminate returning 0 if the end of the string is reached.