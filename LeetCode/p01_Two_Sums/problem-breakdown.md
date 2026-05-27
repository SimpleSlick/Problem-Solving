# Problem Statement
You are given a linear sequence of numerical data points and a specific baseline integer called the target combination value. Your goal is to pinpoint the location identifiers (indices) of two distinct data elements from the sequence that, when mathematically combined via addition, match the exact value of the target baseline.

### The Rules
* **Exclusively:** You are strictly forbidden from reusing the exact same element position twice to form your sum. Each structural index can only be leveraged once.
* **Order Independent:** The location indices can be submitted in any arrangement.

### Example Behavior
Consider a system configuration where the dataset contains `[2, 7, 11, 15]` and the required target baseline is `9`.
* The system evaluates the elements at location `0` (value 2) and location `1` (value 7).
* Since $2 + 7 = 9$, the system successfully identifies and outputs the locations `[0, 1]`.

### Output Goal
Isolate and display the two index markers representing the position of the matching pair.

# The Trap
### The Obvious Approach
The most straightforward way to solve this is to look at every single possible pair of numbers in the array until you find the two that add up to the target. This requires a nested loop (a loop inside a loop). The outer loop fixes a number at index $i$, and the inner loop scans every other index $j$ ahead of it to check if $\text{nums}[i] + \text{nums}[j] == \text{target}$.

### Why this approach is bad
* **The Quadratic Time Bottleneck:** Because you are checking every element against every other element, the total number of comparisons scales quadratically with the size of the array. For an array of size $n$, the algorithm must perform roughly $\frac{n^2}{2}$ checks in the worst case.

Mathematically, this results in a time complexity of:
$$T(n) = O(n^2)$$

* **The Performance Failure:** If the array grows to a standard large dataset size of $n = 10^5$ ($100,000$ elements), a quadratic algorithm forces the computer to execute up to $10,000,000,000$ (ten billion) operations.
    * This volume of work will easily exceed standard execution timelines, causing your program to lag heavily or terminate with a Time Limit Exceeded (TLE) error.

# The Algorithm
### Two Sum Function:
#### Function `twoSum(nums[], target)`
<pre>
Step-1: Start
Step-2: Loop
          i < nums.length
Step-3: Loop
          j < nums.length
Step-4: if nums[j] == target - nums[i]
              Process
                  return [] {i, j}
        [Repeat Step-3 and Step-2]
Step-5: return an empty array
</pre>

#### Main Program
<pre>
Step-1: Start
Step-2: Input
          n, target
Step-3: Process
          numbers = size(n)
Step-4: Loop
          i < numbers.length
Step-5: Input
          numbers[i]
          [Repeat Step-4]
Step-6: result = twoSum(numbers, target)
Step-7: if result = null
              Display "No Element found"
          else
              Display result[0] + " " + result[1]
</pre>

# Time Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The nested loop traversal contributes `n²` operations, and the comparison operation inside the nested loop contributes another `n²` operations, while the input loop and remaining statements are constant or linear operations. Therefore, the time equation becomes:
$$T(n) = 2n^2 + n + 2$$

### Time Math
To calculate the time complexity we need apply few principles:

Ignore Constants: Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big \ O$ notation. After removing constants, the equation becomes:
$$T(n) = n^2 + n + 1$$

Power dominance: In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to: 
$$T(n) = n^2$$
Which can be finally represented as: 
$$O(n^2)$$

### Space Complexity
To calculate how much memory this algorithm uses, we analyze the extra storage and data structures used during execution. The algorithm stores the input array `numbers[]`, which requires `n` memory locations, and the `twoSum()` function returns another array containing the result indices. Apart from this, only a few constant variables are used. Therefore, the space equation becomes:
$$S(n) = n + 2$$

After removing the constants, the equation becomes:
$$S(n) = n$$

The final space complexity becomes: 
$$O(n)$$

And if the input array is ignored and the space complexity of the auxiliary space becomes:
$$O(1)$$
# Edge Cases
### 1. Same Index Collision
If the user provides an input array where an individual element happens to equal exactly half of the target value (e.g., nums = [3, 2, 4], target = 6):
* ***Inside the algorithm:*** When the outer loop (Step-2) is at index $i = 0$ (value `3`), the inner loop (`Step-3`) boots up and scans indices. When the inner loop pointer $j$ hits `0`, it checks the condition in `Step-4`: `if nums[0] == 6 - nums[0]`, which simplifies to `3 == 3`.
* ***Result:*** Logic Contamination. The condition evaluates to true, and the function immediately fires `return [] {0, 0}`. The algorithm breaks the strict exclusivity rule of the puzzle by utilizing the exact same element position twice instead of pairing index 1 and index `2` (`2 + 4 = 6`).

### 2. Redundant Solutions
If the input dataset contains multiple unique pairs that can successfully combine to satisfy the target value (e.g., `nums = [1, 2, 3, 4], target = 5`):
* ***Inside the algorithm:*** The nested loops scan sequentially from left to right. As soon as the outer loop index $i = 0$ (`1`) meets the inner loop index $j = 3$ (`4`), the conditional matching block is triggered.
* ***Result:*** The function hits an immediate exit state, returning `[] {0, 3}`. Any other valid alternative combinations present downstream (such as `2 + 3` at positions `1` and `2`) are entirely ignored due to the sequential termination design of the scanning engine.

### 3. Sub-Minimum Dataset Boundaries
If the main program receives an input scale configuration indicating an array size of less than two elements (e.g., $n = 0$ or $n = 1$):
* ***Inside the algorithm:*** In the `Main Program`, `Step-3` initializes an empty or single-element structure. When passed down to the `TwoSum Function`, the control boundaries `i < nums.length` and `j < nums.length` will either fail to initiate or fail to sustain proper nested evaluation checks.
* ***Result:*** The conditional match block in `Step-4` is entirely bypassed. The function returns a `null` reference up to the main program, triggering `Step-7` to output `"No Element found"`. While gracefully handled, it represents a data-void run where zero processing mechanics occur.

### 4. Absence of a Valid Complementary Pair
If the input collection passes completely through structural processing and contains no two numbers that aggregate to the target baseline:
* ***Inside the algorithm:*** The outer and inner loops exhaustively loop through every combination step possible. The match statement in `Step-4` fails to evaluate as true for every single pass.
* ***Result:*** The nested framework finishes execution without hitting the return statement. Depending on code implementation defaults, it falls out of the loop block and passes back a null value, routing the program directly to display `"No Element found"`.