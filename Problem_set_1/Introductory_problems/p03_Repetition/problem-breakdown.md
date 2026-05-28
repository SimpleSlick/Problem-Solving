# Problem Statement
You are given a text stream of length $n$ containing a sequence of categorized characters. Your task is to scan the stream from beginning to end to find the maximum length of a contiguous block (substring) where every single character is identical.

### The Scenario:
* **The Stream:** A single continuous string consisting of a fixed set of tokens.
* **The Target:** The longest uninterrupted run of the exact same token side-by-side.

### Example behavior:
If the input stream is `ATTCGGGA`, the individual characters repeat in groups: `A` (length 1), `TT` (length 2), `C` (length 1), `GGG` (length 3), and `A` (length 1). The longest continuous repetition of a single token type is 3.

### Constraint:  
&emsp;&emsp;&emsp;&emsp; $1 \le n \le 10^6$

### Output:
Print a single integer representing the maximum length found during the scan.

# The Trap
The most instinctive way to find the longest repetition is to check every single possible substring within the text block. You would use a nested loop (a loop inside a loop). The outer loop selects a starting character, and the inner loop checks all subsequent characters to see how long that specific character repeats. You then keep track of the highest count seen so far.

### Why this approach is Bad (The BottleNeck)
* **The Exponential Time BottleNeck:** Because you are using a nested loop to re-examine characters you have already looked at, a nested loop approach scales quadratically. Mathematically, checking every pair takes:

$$T(n) = O(n^2)\ operations$$

* **The Reality Check:** The system constraint states that the string size $n$ can be up to $10^6$ ($1,000,000$ characters).
    * An $O(n^2)$ algorithm would require $(1,000,000)^2 = 1,000,000,000,000$ (one trillion) operations.
    * Given standard CPU execution speeds, this naive approach would take several minutes to finish. Since the system enforces a strict Time Limit of 1.00 s, your program will completely fail and terminate with a Time Limit Exceeded (TLE) error.

# The Algorithm
<pre>
Step-1: Start
Step-2: Input
          input
Step-3: Process
          set max = 1, current = 1
Step-4: Loop
          i < input.length
Step-5: if input[i] == input[i - 1]
              Process
                  current++
          else
              max = max(max, current)
              current = 1
          [Repeat Step-4]
Step-6: if current > max
              Process
                  max = current
Step-7: Display max
Step-8: End
</pre>

# Time Complexity Analysis
To calculate how fast the algorithm runs, we analyze operations performed throughout the algorithm and represent them mathematically. The loop runs `n` times, and during each iteration the program performs comparison and update operations inside the `if-else` block, contributing approximately `2` operations per iteration, while the remaining statements are constant operations. Therefore, the time equation becomes:

$$T(n) = 2n + 3$$

### Time Math
To calculate complexity we need to apply few principles:
1. **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big O$ notation. After removing constants, the equations becomes:  
$$T(n) = n + 1$$
2. **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to: 
$$T (n) = n$$ <br>
Which can finally represented as: 
$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution. This algorithm mainly uses:
* `max`
* `current`
* Loop control variables  

There are no additional arrays, recursion, or dynamically growing data structures are created. Since the memory usage remains constant throughout execution, the space equation becomes:
$$S(n) = 3$$
After applying principles for calculating complexity, the equation becomes:
$$S(n) = 1$$
Which can be finally represented as:
$$O(1)$$

# Edge Cases
### 1. The Minimum Boundary
If the user inputs a string containing only a single character (e.g., `input = "A"`, so `input.length = 1`):  
* **Inside the algorithm:** In Step-3, both variables are initialized to `1`. Assuming your loop index starts at `i = 1` to check back safely, the condition `i < 1` instantly evaluates to false.

* **Result:** The entire loop is skipped completely. The program jumps straight to Step-6, finds that `current > max` ($1 > 1$) is false, and displays `max` as `1`. While the result is technically correct, the execution bypasses the comparison engine entirely.

### 2. Uniform Sequence
If the user inputs a string where every single character is identical (e.g., `input = "AAAA"`):
* **Inside the algorithm:** Step-5 will continuously evaluate as true for every iteration because `input[i] == input[i-1]`. The program only triggers `current++` inside the loop and completely skips the `else` block where `max` is normally updated.
* **Result:** The loop finishes executing with `current = 4` and `max = 1`. If Step-6 (`if current > max`) was missing, your program would output an incorrect value of `1`. Thankfully, your Step-6 acts as a vital safety net that catches this exact scenario at the last second, updating `max = current` to print the correct answer.

### 3. Alternating or Random Streams
If the user inputs a highly volatile stream where characters change at almost every index (e.g., `input = "ATCG"`):
* **Inside the algorithm:** Step-5 will fail to match at every single character step, forcing the execution flow into the `else` block repeatedly.
* **Result:** Every time a new character appears, the algorithm runs `max = max(max, current)` and resets `current = 1`. The counters constantly overwrite themselves, keeping the state memory usage stable but generating constant conditional branches for the processor.

### 4. Empty Input Stream
If the system encounters an empty string configuration where `input.length = 0`:
* **Inside the algorithm:** In Step-3, both tracking variables are forcefully initialized to `1` (`max = 1, current = 1`). The loop in Step-4 is naturally skipped because `i < 0` is false.
* **Result:** Logic Corruption. The code jumps straight to Step-6 and Step-7, displaying a final maximum repetition length of `1`. This is mathematically invalid because an empty string contains absolutely zero characters, so the output should realistically be `0`.
