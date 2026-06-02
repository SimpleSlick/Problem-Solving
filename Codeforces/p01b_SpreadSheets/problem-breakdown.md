# Problem Statement
You are given a set of grid cell coordinates represented in one of two different structural notation systems. Your task is to dynamically detect which notation system a given coordinate is using, convert it accurately into the alternative system, and output the result.

### The Two Systems
* **Alpha-Numeric Notation (System A):** The column identifier is represented as a alphabetic token string (resembling an alphanumeric base-26 numbering scheme: $A=1$, $B=2$, $\dots$, $Z=26$, $AA=27$, $AB=28$, etc.), immediately concatenated with an integer representing the row number. (e.g., `BC23`).
* **Grid-Coordinate Notation (System B):** The coordinate explicitly separates row and column integers using prefix markers, mapping directly to a format where row and column positions are absolute integers. (e.g., `R23C55`).

### Input Requirements
* An integer $n$ indicating the total number of coordinate translations to process.
* This is followed by $n$ lines, each containing a single valid coordinate string adhering to either System A or System B rules.

### Output Goal
For each input coordinate string, print its identical spatial location converted into the opposite notation system format.

# The Trap
The most instinctive way to solve this is to handle parsing with simple string splits or regular expressions, and convert alphabetic columns to numbers using a standard Base-26 formula (similar to hexadecimal or binary conversion), where 'A' $= 1$, 'B' $= 2$, up to 'Z' $= 26$. For example, converting a two-letter column like `BC` naively looks like:

$$\text{Value} = (2 \times 26^1) + (3 \times 26^0) = 52 + 3 = 55$$

To reverse the process (converting `55` back to letters), the obvious way is to repeatedly use the remainder modulo 26 (`55 % 26`) and divide by 26 to extract the characters from right to left.

### Why this approach is Bad?
* **The Bijective Base-26 "Zero" Trap (The Math Bottleneck):** Standard positional number systems (like decimal or binary) have a structural placeholder for zero (`0`). Excel column names do not use a zero. The sequence jumps directly from `Z` (26) to `AA` (27).
    * If your algorithm encounters a column index that is a perfect multiple of 26 (such as `52`, which should be `AZ`), a naive modulo calculation (`52 % 26`) yields a remainder of `0`.
    * Result: Because there is no character mapped to `0`, your code will either crash with an out-of-bounds array index, or output an incorrect character like `@Z` or `BZ`, corrupting the data.

* The Faulty Detection Trap (The Parsing Bottleneck): A naive detection strategy might look at the first character of the string to decide the notation style: "If it starts with 'R', it must be RXCY notation."
    * This assumption completely falls apart because valid Alpha-Numeric string coordinates can easily start with the letter 'R' as a column header (e.g., cell `R23` means column 18, row 23).
    * Result: A simple prefix check will misclassify `R23` as an incomplete `RXCY` string instead of an Alpha-Numeric string, breaking the logic engine and outputting invalid translations.

# The Math Shortcut
### Column Encoding
Spreadsheet columns are represented using a bijective base-26 numbering system where:

| Symbol | Value | 
| ----- | ----- | 
| A | 1 |
| B | 2 |
| -- | -- |
| Z | 26 |

Unlike conventional positional numeral systems, bijective base-26 contains no zero digit.

### Conversion: A1 Format → RC Format  
The corresponding column number is calculated as:
$$N = Σ(value(Lᵢ) × 26ⁿ⁻ⁱ)$$

where:  
value(A) = 1  
value(B) = 2  
...  
value(Z) = 26  

Example

Column: AB  
N = (1 × 26¹) + (2 × 26⁰)  
N = 28

Result:  
AB15 → R15C28

### Conversion: RC Format → A1 Format
Given a column number N:

Compute:
digit = (N − 1) mod 26  
Map digit to:  
0 → A  
1 → B  
...  
25 → Z

Update:  
N = floor((N − 1) / 26)  
Repeat until N = 0

The generated symbols are concatenated in reverse order to produce the final column identifier.

Example  
Column Number: 28

Iteration 1:  
digit = 1 → B  
N = 1

Iteration 2:  
digit = 0 → A  
N = 0

Result:  
28 → AB

Output:  
R15C28 → AB15

# The Algorithm
Function rowCoordinate:
```
Step - 1: rowCoordinate(input)
Step - 2: Process
            set cIndex = input['C']
            set row = input[1, cIndex]
            set col = input[cIndex + 1]
            set num = Number(col)
            set colString = ""
Step - 3: Loop
            num > 0
Step - 4: num--
Step - 5: remainder = num % 26
Step - 6: ch = 'A' + remainder
Step - 7: colString = ch + colString
Step - 8: num = num / 26
         [Repeat Step - 3]
Step - 9: Display colString + row
Step - 10: End
```

Function rowColSystem:
```
Step - 1: rowColSystem(input)
Step - 2: firstDigitIndex = 0
Step - 3: Loop 
            firstDigitIndex < input.length && input[firstDigitIndex] is not a digit then
Step - 4: firstDigitIndex++
          [Repeat Step - 3]
Step - 5: Set
            row = input[firstDigitIndex]
            col = input[0, firstDigitIndex]
            total = 0
Step - 6: Loop
            i < col.length
Step - 7: letter = col[i]
Step - 8: value = letter - 'A' + 1
Step - 9: total = total * 26
Step - 10: total = total + value
           [Repeat Step - 6]
Step - 11: Display "R" + row + "C" + total
Step - 12: End
```

Main Program:
```
Step - 1: Start
Step - 2: Input n
Step - 3: Loop 
            i < n
Step - 4: Input str
Step - 5: if str matches pattern "^R\\d+C\\d+$"
                rowCoordinate(str)
          else
                rowColSystem(str)
          [Repeat Step - 3]
Step - 6: End
```

# Complexity Analysis
The entire program is divided into three sections, each computed independently before determining the overall time complexity of the algorithm.

1. **Function rowCoordinate:**
The loop operation and the five processing operations performed during each iteration contribute linear operations, while the initialization and display statements are constant operations. Therefore, the time equation becomes:

$$T(n) = 6n + 6$$

2. **Function rowColSystem:**
The first loop and its update operation contribute `2n` operations, while the second loop and its processing operations contribute `5n` operations. The remaining initialization and display statements are constant operations. Therefore, the time equation becomes:

$$T(n) = 7n + 4$$

3. **The Main Program:**
The loop operation, string input operation, conditional check, and function call each contribute n operations, while the remaining statements are constant operations. Therefore, the time equation becomes:

$$T(n) = 4n + 1$$

To calculate the $Big \ O$ which defines to calculate the worst-case scenario of the entire program, so the worst case total becomes:

$$T(n) = (4n + 1) + (7n + 4) $$
$$T(n) = 11n + 5 $$

### Time Math
To calculate complexity we need to apply few principles:
1. **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big O$ notation. After removing constants, the equations becomes:  
$$T(n) = n + 1 $$

2. **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:
$$T(n) = n $$

Which can finally be represented as:
$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution. This algorithm mainly uses:

- `str`
- `cIndex`
- `row`
- `col`
- `num`
- `colString`
- `remainder`
- `ch`
- `firstDigitIndex`
- `total`
- `loop control variables`

No arrays, recursion stacks, or dynamically growing data structures are created. Although colString grows during execution, its size is bounded by the length of the input string and does not require additional storage proportional to the number of test cases n. Therefore, the memory usage remains constant throughout execution.

The space equation becomes:

$$S(n) = 11$$

After applying principles for calculating complexity, the equation becomes:

$$S(n) = 1$$

Which can be finally represented as:

$$O(1)$$

### Auxiliary Space Math
To calculate the auxiliary space complexity, we consider only the extra memory created by the algorithm and exclude the input. The algorithm uses only a fixed number of variables and does not allocate any additional arrays or recursive stack frames.

The auxiliary space equation becomes:

$$AS(n) = 11$$

After applying principles for calculating complexity, the equation becomes:

$$AS(n) = 1$$

Which can finally be represented as:

$$O(1)$$

# Edge Cases
### 1. Faulty Regex Matching Rule (The Classification Trap)
In the `Main Program`, Step - 5 uses the regular expression pattern `"^R\\d+C\\d+$"` to route the string to `rowCoordinate(str)`.

**Inside the algorithm:** This regex forces the string to start with R, followed by digits, followed by `C`, and ending in digits (e.g., `R23C55`). However, if a valid Alpha-Numeric string like `RC23` is entered (Column `RC`, Row `23`), it will not match this regex because there are no digits between `R` and `C`.

**Result:** Incorrect Function Routing. The program will misclassify `RC23` and send it down the `else` path to `rowColSystem(str)`. Inside `rowColSystem`, the code will try to parse `RC23` as if it were an alpha-numeric column string, producing completely broken garbage coordinates instead of converting it to `R23C419`.

### 2. Missing Loop Variable Control (The Main Thread Lockup)
Look closely at the `Main Program` loop structure across Step - 3, Step - 4, and Step - 5.

**Inside the algorithm:** Step - 3 sets up a loop with the condition `i < n`. However, nowhere inside the main loop block is the iterator index `i` initialized to a baseline value (e.g., `i = 0`), nor is it incremented anywhere before hitting `[Repeat Step - 3]`.

**Result:** Infinite Loop Hang. Because `i` never moves forward, the expression `i < n` will remain true indefinitely. The program will continuously demand user inputs or process the exact same string endlessly, locking up the CPU until a Time Limit Exceeded (TLE) error kills the process.

### 3. Substring Slicing Parameter Inversion (String Crash)
Look at the processing mechanics inside `Function rowCoordinate` at Step - 2:

**Inside the algorithm:** The code runs `set row = input[1, cIndex]`. In standard programming, string substring slicing methods typically require the arguments to be `(starting_index, length)` or `(starting_index, ending_index)`. `If input = "R2C5"`, then `cIndex` (the position of 'C') is `2`. The slice becomes `input[1, 2]`.

**Result:** Logical Off-By-One or Crash. If your environment interprets the second parameter as an exclusive end index, `input[1, 2]` only grabs the character at index 1 (`2`), which works. However, if the environment interprets the second parameter as a length, it will grab 2 characters starting from index 1 (`2C`), capturing the 'C' inside your numeric row variable. This will cause subsequent operations or conversions to throw a fatal formatting exception and crash the application.

### 4. Floating-Point Division Remainder Corruption
In `Function rowCoordinate`, look at Step - 8: `num = num / 26`.

**Inside the algorithm:** In Step - 2, `num` is cast as a generic `Number(col)`. In languages like JavaScript, numbers are floating-point values by default. If `num = 55`, executing `55 / 26` results in `2.11538`... instead of a clean truncated integer `2`.

**Result:** Infinite Loop / Corruption. On the next iteration of Step - 3, the loop condition `num > 0` remains true. When it executes Step - 5 (`remainder = num % 26`), it will perform a modulo operation on a decimal fraction (`2.11538... % 26`). This yields bizarre, fractional remainders, which causes Step - 6 (`'A' + remainder`) to calculate non-alphabetic character bytes. The function will generate string garbage and loop indefinitely.