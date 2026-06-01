# Problem Statement
You are given a collection of all integers starting from $1$ up to a given boundary limit $n$. Your goal is to arrange these numbers into a single row such that no two adjacent elements have an absolute difference of exactly 1. If a valid layout exists that avoids placing consecutive numbers side-by-side, you must construct it. If it is mathematically impossible to meet this constraint, you must identify the dead end.

### The Rule and Constraint:
* **Adjacency Protection:** For any two neighboring values in your final sequence, $A$ and $B$, the condition $|A - B| \neq 1$ must hold true.
* **Failure State:** If the given limit $n$ is too restrictive to avoid consecutive neighbors, the system must clearly signal that no arrangement is possible.

### Example Behavior
Consider an input upper boundary limit of $n = 5$. The pool of available numbers is `[1, 2, 3, 4, 5]`.

* A naive chronological arrangement like `[1, 2, 3, 4, 5]` fails immediately because $|2 - 1| = 1$.
* A valid re-ordered sequence is `[4, 2, 5, 3, 1]`. Looking at the neighbors: $|2 - 4| = 2$, $|5 - 2| = 3$, $|3 - 5| = 2$, and $|1 - 3| = 2$. No adjacent elements have a difference of 1, making this a successful layout.

### Output Goal
Print the newly arranged sequence separating the values clearly. If no such configuration can physically exist, print a specific terminal string indicating the lack of a solution.

# The Trap
The most instinctive, brute-force way to solve this is to look at it as a search problem. You would generate every possible permutation of the numbers from $1$ to $n$ using recursion (backtracking). For each arrangement generated, you would run a quick check to see if any neighboring elements have a difference of 1. The moment you find a permutation that passes the rule, you print it and exit.

### Why this approach is bad
* **The Factorial Explosion (Time Complexity):** Generating permutations is incredibly slow. The total number of ways to arrange $n$ unique items is calculated as $n!$ (factorial).
    * If $n = 3$, there are only $3 \times 2 \times 1 = 6$ arrangements to check. Easy.
    * If $n = 10$, that number spikes to $3,628,800$ arrangements.
* **The Reality Check:** The system constraints allow $n$ to be as large as $10^6$ ($1,000,000$). Attempting to run a factorial-time algorithm $O(n!)$ on a million elements is computationally impossible. It would take your computer longer than the age of the universe to finish. Under the strict Time Limit of 1.00 s, your code will immediately fail with a Time Limit Exceeded (TLE) error.
* **The Stack Overflow Risk:** Standard recursive backtracking requires creating a deep call stack. Trying to go $1,000,000$ layers deep into recursion will completely exhaust the computer's memory, causing a fatal StackOverflowError crash before the time limit even runs out.

# The Logic Shortcut
When you separate numbers by their parity (even or odd), you are grouping them like this:
* Even Group ($E$): $[2, 4, 6, 8, \dots]$
* Odd Group ($O$): $[1, 3, 5, 7, \dots]$
If you print all numbers in the Even group first, and then all numbers in the Odd group, your final sequence looks like this:

$$\text{Sequence} = [2, 4, 6, \dots, \mathbf{E_{\text{last}}}, \mathbf{O_{\text{first}}}, 3, 5, \dots]$$

### The Math proof
To ensure no adjacent elements have an absolute difference of $1$, we must evaluate three zones in your sequence:
1. **Inside the Even Group:** Every consecutive even number is defined as $2k$ and $2k+2$. Their absolute difference is:

$$|(2k + 2) - 2k| = 2 \quad (\neq 1)$$

2. **Inside the Odd Group:** Every consecutive odd number is defined as $2k+1$ and $2k+3$. Their absolute difference is:

$$|(2k + 3) - (2k + 1)| = 2 \quad (\neq 1)$$

3. **The Bridge:** This is the only danger zone. It is where the last even number ($E_{\text{last}}$) sits right next to the first odd number ($O_{\text{first}}$). We must ensure that:

$$|E_{\text{last}} - O_{\text{first}}| \neq 1$$

### Why Small Numbers break the bridge?

#### Case 1: When $n = 3$
* Even Group: $[2]$ (So, $E_{\text{last}} = 2$)
* Odd Group: $[1, 3]$ (So, $O_{\text{first}} = 1$)
* Combined Sequence: $[2, 1, 3]$

The Bridge Evaluation:
The bridge occurs between index 0 and index 1, which holds the numbers $2$ and $1$.

$$|E_{\text{last}} - O_{\text{first}}| = |2 - 1| = 1$$

Because the absolute difference is exactly $1$, the structural rule is violated. There is no other way to shuffle $[1, 2, 3]$ to make it work either. Therefore, $n=3$ is mathematically a "NO SOLUTION" state.

#### Case 2: When $n = 2$
* Even Group: $[2]$
* Odd Group: $[1]$
* Combined Sequence: $[2, 1]$

The Bridge Evaluation:

$$|2 - 1| = 1$$
This also fails. Thus, $n=2$ is also a "NO SOLUTION" state.

# The Algorithm
```
Step-1: Start
Step-2: Input 
            number
Step - 3: Process
            Set even_num[]
            Set odd_num[]
Step - 4: if number > 3
Step - 5: Loop i <= number
Step - 6: if i % 2 == 0
            Process
                add i to even_num[]
          else
                add i to odd_num[]
Step - 7: Display even_num + odd_num
          [Repeat Step - 5]
          else if number == 1
Step - 8: Display number
                go to Step - 10
          else
Step - 9: Display "NO SOLUTION"
Step - 10: End
```

# The Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The loop traversal contributes n operations, the conditional check contributes another n operations, and the insertion operation contributes another n operations, while the remaining statements are constant operations. Therefore, the time equation becomes:
$$T(n) = 3n + 2$$

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
* `even_num[]`
* `odd_num[]`
* Constant variables such as `numbers` and loop control variables

The arrays `even_num[]` and `odd_num[]` together store all numbers from 1 to n, contributing n memory locations. The remaining variables require only constant memory. Therefore, the space equation becomes:

$$S(n) = n + 2$$

After applying principles for calculating complexity, the equation becomes:

$$S(n) = n$$

Which can finally be represented:

$$O(n)$$

To calculate the auxiliary space complexity, we consider only the extra memory created by the algorithm and exclude the input. The arrays `even_num[]` and `odd_num[]` are created during execution and together store `n` elements. Therefore, the auxiliary space equation becomes:

$$AS(n) = n$$

Which can be finally represented as:

$$O(n)$$

# Edge Cases
### 1. Loop Control Failure

When the program successfully validates `Step - 4: if number > 3` and moves directly into `Step - 5: Loop i <= number`:
* **Inside the algorithm:** The loop's execution condition depends entirely on comparing the variable `i` to the `number`. However, nowhere in Step-3 or Step-5 is `i` initialized to a starting value (like `1`), nor is it incremented anywhere inside the block.
* **Result:** Infinite Loop State. Because `i` stays uninitialized or static, the loop condition never becomes false. The program hangs indefinitely at Step-5, locking up the thread until the testing system terminates it with a *Time Limit Exceeded (TLE)* error.

### 2. Intermediate Stream Flooding
Consider a valid, standard input size like `number = 4`.
* **Inside the algorithm:** Your array print command, `Step - 7: Display even_num + odd_num`, is placed above the closure statement `[Repeat Step - 5]`. This structurally leaves it locked inside the loop body.
* **Result:** Corrupted Output Format. Instead of waiting for the collections to populate entirely and outputting `2 4 1 3` once, the system is forced to broadcast partial states onto the console at every iteration cycle. The grader will see an illegible stream of values (e.g., `[2][1]`, then `[2][1,3]`, then `[2,4][1,3]`), resulting in a Wrong Answer (WA) verdict.

### 3. Out-of-Bounds Sub-Minimum Inversion
If an automated stress-testing environment feeds a non-positive integer like 0 or a negative value into your program:

* Inside the algorithm: The system checks Step-4 (`if number > 3`, which is false). It completely skips the block and looks below. It then hits the dangling `else if number == 1` statement attached at the bottom of Step-7 (which is also false). Finally, it falls through into the default catch-all `else` container in Step-9.

* Result: The system prints `"NO SOLUTION"`. While technically true that an array of size 0 has no valid arrangement, bypassing standard variable sanity checks by dropping straight through broken block indentations can cause severe compiling or logical parsing errors depending on the language used.

### 4. Non-Numeric Character Injection
If the console input framework encounters an empty line, space corruption, or textual data strings (like `"text"`) instead of a proper integer token at runtime:

* Inside the algorithm: In Step-2, the variable assignment expects a clean primitive integer format to bind directly to the registry name `number`.

* Result: Immediate Execution Crash. The parsing system cannot cast characters into numerical types. The execution environment will instantly throw a critical system crash exception (such as `InputMismatchException` in Java) and terminate the program before it can execute any of your logical rules.