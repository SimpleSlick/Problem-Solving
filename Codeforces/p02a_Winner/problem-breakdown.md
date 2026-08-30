# Problem Statement
You are given a chronological transaction log tracking positive and negative score adjustments assigned to named participants. Your objective is to identify the singular winning entity based on a dynamic two-tier criterion: finding who achieves the highest cumulative balance by the end of all events, resolved by which qualifying candidate was the earliest in history to reach or exceed that terminal maximum threshold.

### The Rules & Win Condition
- **Base Balance:** Every participant starts with a cumulative score of $0$.
- **Final Standing Evaluation:** When all $n$ event updates have completed, each participant has a final terminal balance. Let $m$ be the global maximum score among all participants ($m = \max(\text{final\_scores})$).
- **The Tie-Breaking Mechanism:**
  - If exactly one participant finishes with score $m$, they are declared the winner.
  - If two or more participants finish with the exact same maximum final score $m$, the winner is the candidate who first reached a running score $\ge m$ during chronological playback.
- **The Candidate Constraint:** An entity can only win if their final score at the end of the log equals $m$. Reaching $m$ early in the log does not qualify an entity if subsequent negative transactions pull their final score below $m$.

### Input Requirements
- An integer $n$ ($1 \le n \le 1000$) denoting the total number of sequential log operations.
- Followed by $n$ lines, each containing an alphanumeric identifier name (length $1$ to $32$) and an integer delta score ($-1000 \le \text{score} \le 1000$).
- At least one participant is guaranteed to conclude the sequence with a strictly positive final score ($m > 0$).

### Output Goal
Output the identifier string of the unique winning participant.

# The Trap
## The Obvious Approach:
The most intuitive approach is to simulate the contest with a single linear pass over the transaction log, keeping track of each participant's running score in a hash map alongside a global maximum variable max_score and a candidate winner tracker current_winner. Whenever an incoming score adjustment pushes a participant's tally strictly above or equal to max_score, the algorithm immediately updates max_score and overwrites current_winner with that participant's name.
### Why this Approach is Bad:
- **The Transient Peak Fallacy:** A player can experience an early scoring surge that briefly reaches or exceeds the current maximum, only to lose points in subsequent rounds.
  - *Example:* Player A gains 10 points in round 1 (standing at 10), but loses 6 points in round 3 (finishing at 4). Meanwhile, Player B steadily reaches 5 points in round 2 and keeps that score until the end.
  - A single-pass approach would flag Player A when they hit 10. However, the final standings determine that the true maximal score at game completion is 5, held by Player B. Player A does not finish with the maximum score and cannot win. 
- **The Premature Tie-Break Bug:** The condition "first reached at least $m$ points" requires knowing the final maximum score $m$ before evaluating historical timestamps.
  - You cannot know what target score $m$ defines a qualifying candidate until the entire log has finished processing.
  - Any attempt to crown a winner on the fly without a global baseline causes incorrect classifications whenever point deductions occur later in the timeline.

# The Algorithm
```
BEGIN ALGORITHM
  num <- Input()

  ({Map}) -> map_info <- Map(String, Integer)
  playerName <- Vector(String)
  playerPoint <- Vector(Integer)

  FOR i <- 0, i < num, i++
    (String) -> name <- Input()
    (Integer) -> point <- Input()

    playerName.APPEND(name)
    playerPoint.APPEND(point)

    IF map_info.LOCATE(name) != map_info.END()
      map_info[name] += point
    ELSE
      map_info[name] = point
    END IF

    (Integer) -> maxFinalScore <- Integer.Min

    FOR (Any) -> entry: map_info
      IF entry > maxFinalScore
        maxFinalScore <- entry
      END IF
    END FOR

    ({Map}) -> liveScore <- Map(String, Integer) 

    FOR i <- 0, i < n, i++
      (String) -> name <- playerPoint[i]
      (Integer) -> point <- playerPoint[i]

      IF liveScore.LOCATE(name) != liveScore.END()
        liveScore[name] += point
      ELSE
        liveScore[name] = point
      END IF

      (Integer) -> finalTotal <- map_info[name]
      (Integer) -> currentTotal <- liveScore[name]

      IF finalTotal == maxFinalScore AND currentTotal >= maxFinalScore
        DISPLAY name
        BREAK;
      END IF
    END FOR
  END FOR
END ALGORITHM
```

# Complexity Analysis
To calculate how fast this algorithm runs, we analyze the repeating operations performed throughout the algorithm and represent them mathematically. The algorithm contains three sequential loops. The first loop processes all `n` players, the second loop checks up to `n` entries in the map to find the maximum score, and the third loop processes the `n` players again to determine the winner. Since these loops are sequential rather than nested, their operations are added instead of multiplied. Therefore, the time equation becomes:

$$T(n)=16n+2$$

To calculate complexity we need to apply few principles:

- **Ignore Constants:** Constant values do not significantly affect the growth of an algorithm because they remain fixed even when the input size increases. Therefore, constants are ignored while finding the BigO notation. After removing constants, the equations becomes:
- 
$$T(n) = n + 1$$

- **Power dominance:** In time complexity analysis, the term with the highest growth rate dominates the entire equation as the input size becomes very large. Lower-order terms and smaller growth terms become negligible. Here, the term with the highest power is n, so the equation simplifies to:

$$T(n) = n$$

Which can finally be represented as:

$$O(n)$$

### Space Math
To calculate the space complexity, we analyze how much memory the algorithm uses during execution. This algorithm mainly uses:

- `map_info` → stores up to `n` player-score entries
- `playerName` → stores `n` player names
- `playerPoint` → stores `n` player scores
- `liveScore` → stores up to `n` player-score entries
- other variables such as `num`, `name`, `point`, `maxFinalScore`, `entry`, `finalTotal`, and `currentTotal` → constant memory

The three initial data structures map_info, playerName, and playerPoint each grow with the number of players, contributing n memory locations each. The liveScore map also grows up to n entries. Therefore, the space equation becomes:

$$S(n) = n + n + n + n + 7$$

So, 
$$S(n) = 4n + 7$$

Which can be finally represented as:

$$O(n)$$

### Auxiliary Space Complexity
For auxiliary space, we exclude the input data itself. Here, `playerName` and `playerPoint` represent the stored input data, while `map_info` and `liveScore` are additional data structures created and maintained by the algorithm.

Therefore:

$$AS(n) = n + n + 7$$

So:

$$AS(n) = 2n + 7$$

After removing the constant:

$$AS(n) = 2n$$

Which can be finally represented as:

$$O(n)$$

# Edge Cases
### 1. Temporary Peak with Late Degradation
A player surges early to a high score that meets or exceeds the eventual final winning total $m$, but then loses points in later rounds:
- **Inside the algorithm:** Suppose Candidate A reaches a running score of $12$ early, but incurs negative penalties bringing their final balance down to $8$. Candidate B reaches $10$ and finishes at $10$, making the global maximum $m = 10$.
- **Result:** False Winner Selection. If the second pass checks whether any player hits $\ge m$ during replay without strictly restricting the search to the set of candidates whose final score equals $m$, Candidate A would trigger the condition at score $10$ or $12$ and win erroneously. The evaluation set in Pass 2 must strictly contain only candidates where $\text{final\_score}[\text{player}] == m$
### 2. Overshooting the Target Threshold
A candidate jumps directly past the winning score $m$ in a single high-value transaction without ever landing exactly on $m$:
- **Inside the algorithm:** Let the maximum final score be $m = 8$. A qualifying candidate sits at score $6$ and receives an update of $+5$, leaping directly to $11$
- **Result:** Infinite Search / Missed Win Condition. If the secondary pass checks for an exact equality match (`running_score == m`), this candidate will skip the trigger entirely. The condition must explicitly check for boundary attainment using an inequality: `running_score >= m`.
### 3. All Final Contenders Finish with Negative Intermediate Dips
Multiple candidates finish tied at a modest score (e.g., $m = 2$), but both experience negative fluctuations throughout the match:
- **Inside the algorithm:** Both players drop below $0$ during early rounds (e.g., $-500$) before recovering to $2$.
- **Result:** Premature Default Assignment. If the tracking algorithm initializes score variables or candidate thresholds to a baseline of `0` instead of tracking actual negative cumulative balances, or if it resets negative balances to zero, running tallies will become distorted. Score containers must support signed values across the full negative range without floor clamping.
### 4. Single Participant or Uncontested Dominance 
All transactions belong to a single player, or one player finishes strictly higher than all other competitors:
- **Inside the algorithm:** The candidate pool with final score $m$ contains exactly one entity.
- **Result:** Redundant Replay Logic / Off-by-One Delays. While the two-pass logic inherently resolves this case, edge implementations that require at least two candidates to initiate tie-breaking logic may fail to return a name or crash if tie-breaker branches assume $\vert{}\text{candidates}\vert{} > 1$. The algorithm must seamlessly select the sole maximum holder on their very first occurrence of hitting $m$.