# Problem Statement
Given two disjoint, monotonically non-decreasing sequences of real-valued scalar measurements of lengths $m$ and $n$ respectively, determine the central statistical balance point (the median value) of their combined union without explicitly concatenating or physically merging the collections.

### The Rules & Invariant Conditions:
- **Virtual Union Ordering:** The target value corresponds strictly to the central element(s) of a hypothetical unified sequence of length $m + n$ sorted in non-decreasing order.
- **Parity Division:**
  - If the combined size $m + n$ is odd, the median is the single element located exactly at the unified central index $\lfloor (m + n) / 2 \rfloor$.
  - If the combined size $m + n$ is even, the median is the arithmetic mean of the two middle elements spanning the split boundary.   
- **Sub-Linear Runtime Barrier:** Physical synthesis or linear scanning of the merged stream is impermissible; the balance point must be resolved within strict sub-linear $O(\log(\min(m, n)))$ execution limits.

### Input Requirements:
- Two sorted integer arrays nums1 and nums2 with respective lengths $m$ and $n$, where $0 \le m, n \le 1000$ and $1 \le m + n \le 2000$.   
- Scalar entries fall within $-10^6 \le \text{nums}[i] \le 10^6$.

### Output Goal:
Return a floating-point scalar representing the exact median value of the combined dataset.

# The Trap
### The Obvious Approach:
The most natural naive approach is to physically merge the two arrays into a single combined array of size $m + n$ using the standard two-pointer merge technique from merge sort (or concatenating both arrays and running a standard sorting algorithm). Once the unified sorted collection is created, the median is extracted directly via index lookups: accessing index $\lfloor (m + n) / 2 \rfloor$ if the total size is odd, or averaging the elements at $(m + n) / 2 - 1$ and $(m + n) / 2$ if even.
### Why this Approach is Bad:
- Violation of the Sub-Linear Time Limit ($O(m + n)$ vs. $O(\log(m + n))$):
  - Linear merging processes every element in both collections sequentially, requiring $O(m + n)$ time.
  - If arrays are simply concatenated and sorted from scratch via quicksort or Timsort, the runtime further degrades to $O((m + n)\log(m + n))$.
  - The problem explicitly enforces an $O(\log(m + n))$ boundary. While $m, n \le 1000$ in this specific test suite may pass an $O(m + n)$ simulation, in algorithmic interviews and competitive programming environments this approach is rejected as fundamentally sub-optimal because it fails the strict sub-linear constraint.
- Auxiliary Space Allocation Overhead:
  - Allocating a new merged array requires $O(m + n)$ extra memory space.
  - Even if optimized to $O(1)$ space by using two pointers to simulate the merge without storing elements until the middle index is reached, the time complexity remains trapped at $O(m + n)$ operations.
- Redundant Exhaustive Search:
  - The median depends solely on the 1 or 2 boundary elements at the exact center of the merged distribution.
  - Inspecting, comparing, and advancing through every element in the lower half of both arrays does unnecessary work when both input arrays are already sorted.

# The Algorithm
```
BEGIN ALGORITHM
    FUNCTION findMedianSortedArrays(nums1 -> {REF -> {VECTOR}}, nums1 -> {REF -> {VECTOR}}) -> Double
        IF(LENGTH(nums1) > LENGTH(nums2))
            INTERCHANGE(nums1, nums2)
        END IF

        m <- LENGTH(nums1)
        n <- LENGTH(nums2)

        low = 0, high = m

        WHILE low <= high
            (Integer) -> partition1 <- low + (high - low) / 2
            (Integer) -> partition2 <- (m + n + 1) / 2 - partition1
            
            (Integer) -> left1 <- CHOOSE(partition1 == 0, Integer.Min, nums1[partition1 - 1])
            (Integer) -> right1 <- CHOOSE(partition1 == m, Integer.Max, nums1[partition1])
            (Integer) -> left2 <- CHOOSE(partition2 == 0, Integer.Min, nums1[partition2 - 1])
            (Integer) -> right1 <- CHOOSE(partition2 == m, Integer.Min, nums1[partition2])

            IF(left <= right2 && left2 <= right1)
                IF((m + n) % 2 == 1)
                    RETURN MAX(left1, left2)
                END IF

                RETURN(MAX(left1, left2) + MIN(right1, right2)) / 2.0;
            ELSE IF(left1 > right2)
                high <- partition - 1

            ELSE
                low <- partition + 1
            
            END IF
            
        END WHILE
      
        RETURN 0.0
    END FUNCTION
END ALGORITHM
```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The algorithm uses a binary-search approach where the search range is divided approximately in half during every iteration. Therefore, the WHILE loop runs approximately log n times, while the partition calculations, comparisons, and other operations inside each iteration are constant operations. Therefore, the time equation becomes:

$$T(n) = \text{log(n)} + 1$$

### Time Math
To calculate complexity we need to apply few principles:
1. **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the $Big O$ notation. After removing constants, the equations becomes:

$$T(n) = \text{log(n)} + 1$$

2. **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to: 

$$T(n) = \text{log(n)}$$

Which can finally be represented as: 

$$O(log \ n)$$

### Space Complexity
To calculate the space complexity, we analyze how much memory the algorithm uses during execution. This algorithm mainly uses:

- `nums1` → input array of size m
- `nums2` → input array of size n
- Variables such as `m`, `n`, `low`, `high`, `partition1`, `partition2`, `left1`, `right1`, `left2`, and `right2` → constant memory

The algorithm does not create any new array, recursion stack, or dynamically growing data structure. Therefore, the input arrays contribute `m + n` memory, while the remaining variables require only constant memory.

Therefore, the total space equation becomes:

$$S(m,n) = m + n + 10$$

After applying the space-complexity principles:

$$S(m,n) = m + n$$

Which can be finally represented as:

$$O(m + n)$$

### Auxiliary Space Math
For auxiliary space, the input arrays `nums1` and `nums2` are excluded because they are already provided as input. The algorithm only uses a fixed number of variables and does not allocate additional storage proportional to the input size.

Therefore:

$$AS(m,n) = 10$$

After removing the constant:

$$AS(m,n) = 1$$

Which can be finally represented as:

$$O(1)$$

# Edge Cases
### 1. One Empty Array 
One of the two collections contains zero elements, while the other contains all active elements (e.g., `nums1 = []`, `nums2 = [1, 2, 3, 4]`):
- **Inside the algorithm:** The partition cut on the empty array must evaluate valid boundary values without attempting to dereference index `0` or index `-1`.
- **Result:** **Index Out of Bounds / Null Pointer Dereference.** If partition logic blindly accesses `nums1[partition1 - 1]` or `nums1[partition1]`, it crashes immediately. The implementation must use virtual infinite sentinel guards:
$$\text{maxLeft}_1 = (p_1 == 0) \ ? \ -\infty : \text{nums1}[p_1 - 1]$$
$$\text{minRight}_1 = (p_1 == m) \ ? \ +\infty : \text{nums1}[p_1]$$

### 2. Searching the Larger Array
The binary search is initiated on `nums1` when `nums1` has significantly more elements than `nums2` ($m > n$):
- **Inside the algorithm:** The midpoint calculation sets $p_1 = (low + high) / 2$, and the required balance partition sets:
$$p_2 = \frac{m + n + 1}{2} - p_1$$
- **Result:** **Negative Partition Index Bounds.** When $m > n$, selecting an extreme partition cut in the larger array forces $p_2$ to compute as a negative number or exceed $n$. To enforce $0 \le p_2 \le n$ and maintain strict $O(\log(\min(m, n)))$ logarithmic bounds, the function must guarantee that binary search executes solely on the shorter array (swapping parameters upfront if $m > n$).

### 3. Non-Overlapping Disjoint Intervals
Every value in `nums1` is strictly smaller than every value in `nums2` (e.g., `nums1 = [1, 2]`, `nums2 = [3, 4]`):
- **Inside the algorithm:** The binary search hits an extreme boundary condition where the optimal cut places all elements of `nums1` on the left ($p_1 = m$) and elements of `nums2` on the right ($p_2 = 0$).
- **Result:** **Boundary Sentinel Miss.** When $p_1 = m$, `minRight1` has no physical array element and must fall back to $+\infty$. When $p_2 = 0$, `maxLeft2` has no physical element and must fall back to $-\infty$. Without numeric sentinels (`INT_MIN`, `INT_MAX`), standard comparison checks fail, causing the search interval to exhaust without finding a valid partition.

### 4. Single-Element Total Parity 
The smallest valid non-empty edge case where one array has a single scalar and the other is empty (e.g., `nums1 = []`, `nums2 = [1]`):   
- **Inside the algorithm:** $m = 0, n = 1$, combined size is odd ($m + n = 1$). The total left elements needed is $\lfloor (0 + 1 + 1) / 2 \rfloor = 1$.
- **Result:** If parity handling treats total size modulo 2 incorrectly or assumes at least two elements exist to perform a midpoint average $(a + b) / 2.0$, it returns corrupted values. The odd-parity return must directly yield $\max(\text{maxLeft}_1, \text{maxLeft}_2)$ without calculating an average.