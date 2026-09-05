# Problem Statement
You are given a linear sequential stream of discrete token symbols. Your task is to find the maximum possible span length of a contiguous, uninterrupted sub-segment within this sequence such that every token within that segment is completely distinct, containing no duplicate occurrences of any symbol.

### The Rules & Structural Distinction
- **Contiguity Constraint:** The selected segment must be a continuous block (a substring). Disconnected token selections that skip intermediate indices (subsequences) are strictly invalid.
- **Uniqueness Invariant:** A candidate segment $[L, R]$ is valid if and only if for every pair of indices $i, j$ within the segment ($L \le i < j \le R$), the tokens at those positions are distinct ($\text{stream}[i] \neq \text{stream}[j]$).
- **Empty Boundary:** If the sequence contains no tokens at all, the length of the longest non-repeating segment is naturally $0$.

### Input Requirements
- A single input string $s$ composed of printable characters(which can include uppercase, and lowercase letters, digits, punctuation symbols and spaces).

### Output Goals
Return a single integer denoting the size (character count) of the longest contiguous sub-segment containing entirely unique elements.

# The trap
The most straightforward brute-force solution is to inspect every conceivable contiguous substring within the given sequence. You write an outer loop fixing the start boundary index $i$ from $0$ to $n-1$, an inner loop fixing the end boundary index $j$ from $i$ to $n-1$, and for every identified slice $[i, j]$, you iterate through all characters inside a third nested loop (or insert them into a hash set) to verify whether all characters in that substring are mutually distinct. If no duplicates exist, you update a global tracker `max_length = max(max_length, j - i + 1)`.

### Why This approach is Bad
- **The Cubic Time Collapse ($O(n^3)$ Time Complexity):** In a sequence of length $n$, the number of possible contiguous substrings is:
$$\frac{n(n + 1)}{2} \approx O(n^2)$$
Verifying the uniqueness of characters in a slice of length $k$ requires up to $O(k)$ inspections. Across all substrings, the cumulative operations scale to:
$$\sum_{i=0}^{n-1} \sum_{j=i}^{n-1} (j - i + 1) = O(n^3)$$
If $n = 5 \times 10^4$, executing roughly $(5 \times 10^4)^3 = 1.25 \times 10^{14}$ operations will overwhelm any modern CPU ($10^8$ operations per second limit), resulting in an immediate Time Limit Exceeded (TLE) failure.
- The Redundant Reset Penalty (The Naive Sliding Trap): Even if the algorithm optimizes substring checking to $O(n^2)$ by expanding a hash set character-by-character from each starting point $i$, it still suffers from wasteful restarts. When the right pointer encounters a repeated character, throwing away all progress and incrementing $i$ by just $1$ re-scans identical valid ranges repeatedly without utilizing the spatial position of the offending duplicate character.

# The Algorithm
```
BEGIN ALGORITHM
    FUNCTION lengthOfLongestSubstring(s -> String) -> Integer
        result <- 0
        ({Map}) -> hash_map <- Map(Character, Integer)
        i <- 0, j <- 0

        WHILE(j < LENGTH(s))
            (char) -> ch <- s[j]

            IF(hash_map.LOCATE(ch) != hash_map.END())
                i <- max(hash_map.GET(ch), i)
            END IF

            result = max(result, j - i + 1)
            hash_map[ch] = j + 1
            j++
        END WHILE

        RETURN result
    END FUNCTION
END ALGORITHM

```

# Complexity Analysis
For this algorithm, the WHILE loop runs once for each character in the string, so it contributes n. Inside each iteration, the algorithm performs several constant-time operations: character access, hash-map lookup/update, the max comparison, result calculation, hash-map insertion, and increment of j.

Using the same operation-counting approach we've been using:
- `WHILE` loop → `n`
- `IF` block → `n`
- `result = max(...)` → `n`
- `hash_map[ch] = j + 1` → `n`
- `j++` → `n`
- initialization/return → constant

Therefore, the equation can be represented as:

$$T(n) = 5n + 2$$

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

- Input string `s` → `n` characters
- `hash_map` → can store up to `n` characters and their positions
- Variables such as `result`, `i`, `j`, and `ch` → constant memory

The input string requires `n` memory locations, while `hash_map` can grow up to `n` entries. The remaining variables require only constant memory. Therefore, the space equation becomes:

$$S(n) = n + n + 4$$

So:

$$S(n) = 2n + 4$$

After applying principles for calculating complexity, the equation becomes:

$$S(n) = 2n$$

Which can be finally represented as:

$$O(n)$$

### Auxiliary Space Math
For auxiliary space, we exclude the input string `s` and consider only the additional memory created by the algorithm. The `hash_map` can contain up to `n` entries, while the remaining variables require constant space.

Therefore:

$$AS(n) = n + 4$$

After removing the constant:

$$AS(n) = n$$

Which can be finally represented as:

$$O(n)$$

# Edge Cases
### 1. The Zero-Length Null Stream ($s = \text{""}$)
The sequence input contains zero characters (an entirely empty string):
- **Inside the algorithm:** If pointer structures, lookup arrays, or length counters assume at least one character exists to seed the initial window (e.g., executing `lookup[s[0]] = 0` prior to loop entry), the engine will attempt to access index 0 of an empty container.
- **Result:** <u>***Out-of-Bounds Exception / Segmentation Fault***</u>. An unhandled empty string will crash immediately before the loop starts. The sliding window must initialize gracefully with $L = 0, R = 0, \text{max\_len} = 0$, allowing the main loop condition ($R <u \text{length}$) to evaluate to false right away and safely return 0. 

### 2. Homogeneous Monotonous Sequences ($s = \text{"aaaaaa"}$)
Every token in the stream is an identical repeating symbol:
- **Inside the algorithm:** The right pointer advances to index 1 and immediately detects a collision. The left pointer $L$ must shift forward by one step on every single increment of $R$.
- **Result:** <u>***Off-By-One Span Calculation***</u>. The valid sliding window span never grows larger than $1$. If the logic computes length updates before resolving the collision or handles the left boundary adjustment inclusively instead of advancing past the prior instance ($L = \text{prev\_index} + 1$), the counter can mistakenly evaluate to `2` or fail to shrink back down to `1`.

### 3. All-Unique Dispersed Streams ($s = \text{"abcdef"}$)
The input consists entirely of distinct tokens with zero duplicates from start to end:
- **Inside the algorithm:** The right pointer $R$ sweeps across the entire sequence from index $0$ to $n-1$ without triggering the left pointer $L$ to move forward even once.
- **Result:** <u>***Missed Terminal Update Bug***</u>. Algorithms that only recalculate or store `max_len` when a duplicate collision occurs will exit the loop having recorded nothing (returning 0 or an incomplete count). The global maximum calculation $\text{max\_len} = \max(\text{max\_len}, R - L + 1)$ must execute on every step iteration, guaranteeing that a strictly monotonic growth path updates the result to the full length $n$.

### 4. Obsolete Historical Collisions Outside the Active Window
A character encountered by the right pointer $R$ previously appeared in the sequence, but its last occurrence sits behind the current left pointer $L$ (e.g., in $s = \text{"abba"}$, when $R$ reaches the final `a` at index $3$, the first `a` was at index $0$, while $L$ is currently at index $2$):
- **Inside the algorithm:** The hash map/lookup table returns the index of the first `a` (index $0$).
- **Result:** <u>**Window Regressive Inversion.**</u> If the code blindly assigns $L = \text{last\_seen}[c] + 1$, the left boundary will leap backward from $2$ to $1$, re-incorporating duplicate `b` tokens that were already pruned. The left pointer transition must be strictly monotonic:

$$L = \max(L, \text{last\_seen}[c] + 1)$$

### 5. Extended ASCII, Whitespace, and Multi-Byte Symbols
The sequence contains spaces, control characters, numeric digits, or punctuation (e.g., $s = \text{"a b c a ! 1 2"}$):
- **Inside the algorithm:** If the implementation replaces a general hash map with a fixed-size frequency/index array of size $26$ (assuming only lowercase English letters `'a'` – `'z'`), calculating an array offset such as `s[R] - 'a'` will produce negative offsets for spaces (`' ' - 'a' < 0`) or punctuation.
- **Result:** <u>**Memory Access Violation.**</u> The program will access memory out of bounds and crash. The tracking structure must support a full 128/256-element direct-address table (covering the complete standard ASCII set) or an associative hash map to cleanly handle arbitrary character encodings.