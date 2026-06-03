# Problem Statement
You are given two linear, non-empty pointer chains (singly linked node data structures). Each node in these chains holds a single-digit integer value ranging from $0$ to $9$. Together, the sequential nodes in each chain represent a massive non-negative integer, but with a structural twist: the positional digits are stored in a completely reversed chronological sequence.

Your goal is to mathematically add these two large numeric streams together and construct a brand-new, independent pointer chain that houses the resulting summation, also preserved in reverse order.

### The Rules & Constraints:
* **Positional Alignment:** The head of each chain represents the lowest positional magnitude (the ones place), followed by the tens place, hundreds place, and so on.
* **No Leading Spaces:** The datasets are guaranteed to have no unnecessary leading zeros (or trailing nodes containing $0$), unless the numerical value itself is explicitly just the number $0$.
* **Scale Bounds:** Each input chain can span a depth of anywhere from $1$ up to $100$ individual nodes.

### Example Behavior:
Suppose you receive two sequence paths representing the numbers $342$ and $465$:
* Path 1 Layout: `2 -> 4 -> 3` (reversing to represent $342$)
* Path 2 Layout: `5 -> 6 -> 4` (reversing to represent $465$)
* The system pairs the columns sequentially: $2+5=7$, $4+6=10$ (write $0$, carry over $1$), and $3+4+1=8$.
* The final compiled output chain must read: `7 -> 0 -> 8` (representing the final sum, $807$).

### Output Goal:
Return the starting structural pointer (the head node) of the newly generated summation chain.

# The Trap
The most instinctive, non-technical way to solve this is to extract the numbers from the data structures first. You would traverse both linked lists from head to tail, parse their node values into a string or an array, reverse those sequences to read them in the correct forward order, and convert them into standard numerical variables (like a primitive `int` or `long`). Once you have the two native numbers, you simply add them together using basic addition math (`sum = num1 + num2`), convert that final sum back into a reversed stream of digits, and build a brand-new linked list out of it.

Why this Approach is Bad (The Bottlenecks):
* The Memory Storage Crash (The Big-Int Bottleneck): Look closely at the system constraints. Each linked list can contain up to 100 nodes. A 100-node linked list represents a number that is 100 digits long.
    * A standard 32-bit integer (`int`) can only hold numbers up to roughly 10 digits ($\approx 2 \times 10^9$).
    * A standard 64-bit long integer (`long` or `long long`) can only hold numbers up to roughly 19 digits ($\approx 9 \times 10^{18}$).
    * **Result:** Attempting to extract and squeeze a 100-digit number into a native hardware variable will instantly cause a catastrophic Arithmetic Overflow. The upper digits will be completely truncated, the numbers will warp into garbage or negative data, and your final summation list will be totally corrupted.
* The Performance / Object Allocation Waste: Even if you bypass the hardware limit by using a custom string-based large number library (like `BigInteger` in Java), you are still forced to loop through the data multiple times: once to extract, once to reverse, once to execute string-addition math, and once more to allocate new nodes. This creates highly inefficient overhead.

# The Algorithm
```
Step - 1: addTwoNumbers(ListNode l1, ListNode l2)
Step - 2: Set
            head = ListNode(0)
            curr = head
            carry = 0
Step - 3: Loop
            l1 != null or l2 != null or carry != 0
Step - 4: Set
            x = is l1 != null, if true(l1.val) : false(0)
            y = is l2 != null, if true(l2.val) : false(0)
Step - 5: Process
                sum = carry + x + y
                carry = sum / 10
Step - 6: Process
                curr.next = ListNode(sum % 10)
                curr = curr.next
Step - 7: if l1 != null
                l1 = l1.next
Step - 8: if l2 != null
                l2 = l2.next
          [Repeat Step - 3]
Step - 9: return head.next
```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The loop traversal, value assignment, arithmetic calculation, node creation, and linked-list pointer updates each contribute n operations, while the initialization statements are constant operations. Therefore, the time equation becomes:

$$T(n) = 6n + 3$$

### Time Math
1. **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big O$ notation. After removing constants, the equations becomes:  
$$T(n) = n + 1$$

2. **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:  
$$T (n) = n$$


Which can finally represented as: 
$$O(n)$$

### Space Math

To calculate the space complexity, we analyze how much extra memory the algorithm uses during execution. This algorithm mainly uses:

* `head`
* `curr`
* `carry`
* `x`
* `y`
* `sum`
* loop control and temporary references

In addition, a new linked list node is created during each iteration of the loop using:


curr.next = ListNode(sum % 10)


If the input lists contain `n` digits, the algorithm creates approximately `n` new nodes for the result list. Therefore, the new linked list contributes `n` memory locations, while the remaining variables require only constant memory.

The space equation becomes:

$$S(n) = n + 6$$

After applying principles for calculating complexity, the equation becomes:

$$S(n) = n$$

Which can be finally represented as:

$$O(n)$$

### Auxiliary Space Math
To calculate the auxiliary space complexity, we consider only the extra memory created by the algorithm and exclude the input linked lists. Since a new result linked list containing `n` nodes is created, the auxiliary space equation becomes:

$$AS(n) = n + 6$$

After applying principles for calculating complexity, the equation becomes:

$$AS(n) = n$$

Which can be finally represented as:

$$O(n)$$

# Edge Cases
### 1. Asymmetric Sequence Lengths (The Early-Termination Failure)
When the two input sequences contain vastly different numbers of nodes (e.g., adding a 1-digit number to a 5-digit number: $l_1 = [9]$ and $l_2 = [1, 2, 3, 4, 5]$):

* **Inside the algorithm:** A naive linear loop that terminates the moment either list pointer hits a null boundary (`while l1 != null && l2 != null`) will halt prematurely after processing the very first node.
* **Result:** Truncated Calculation / Dropped Digits. The algorithm will process the ones column, link it to the output, and completely abandon the remaining 4 digits of $l_2$. The correct design must utilize an inclusive logical gate (`while l1 != null || l2 != null`) and pad the exhausted node streams with a virtual value of 0 to ensure the addition sweeps fully across the longer chain.

### 2. Trailing Terminal Carry-Over (The Missing Node Bug)
Consider a boundary calculation that forces a final numeric remainder at the very last column position (e.g., $99 + 1$, represented as $l_1 = [9, 9]$ and $l_2 = [1]$):

* **Inside the algorithm:** The traversal loop steps through index positions smoothly. At the final slot, it processes $9 + \text{carry}(1) = 10$. It writes $0$ to the node, shifts the carry state register to 1, and moves both stream pointers into a `null` boundary state, which naturally breaks the main loop loop.
* **Result:** Off-By-One Magnitude Error. If the program logic lacks a terminal sanity check for the carry register outside the loop body, it will drop that trailing `1` completely. Instead of outputting the correct answer $100$ (`[0, 0, 1]`), the list will terminate early and output $0$ (`[0, 0]`). The system must explicitly build a final extra node at the end if `carry > 0` upon loop exit.
    
### 3. Symmetric Universal Cascading Carries (The Processing Load Peak)
When processing two equally long, maximum-capacity chains containing exclusively maximum-digit elements (e.g., $l_1 = [9, 9, 9, 9]$ and $l_2 = [9, 9, 9, 9]$):

* **Inside the algorithm:** Every individual step execution inside the traversal loop triggers an active carry state transformation ($9 + 9 + 1 = 19$). The carry register is forced to stay at `1` across every single node transition in the entire architecture.

* **Result:** While this won't crash standard pointer-link operations, it represents the absolute worst-case mathematical execution path. It creates a continuous, uninterrupted chain of logical branch triggers where every single node allocation block is forced to run arithmetic reduction routines (`sum % 10`) and value-forwarding steps.

### 4. Direct Zero Identity Processing (The Minimum Unit Boundary)

If one or both of the stream collections represent the base identity value of zero (e.g., $l_1 = [0]$ and $l_2 = [0]$):

* **Inside the algorithm:** The iteration pointers initialize at the head nodes. The mathematical processing block evaluates $0 + 0 + \text{carry}(0) = 0$.

* **Result:** The system creates a single independent output node containing `0`. While simple, your loop must cleanly handle this single-step execution path without trying to search for non-existent next pointers (`node.next`), which would throw a fatal `NullPointerException` crash right at the initialization gate.