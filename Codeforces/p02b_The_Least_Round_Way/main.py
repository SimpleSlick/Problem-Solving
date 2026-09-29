INF = 10**9

def count_twos(num):
    count = 0

    while num != 0 and num % 2 == 0:
        num //= 2
        count += 1

    return count

def count_fives(num):
    count = 0

    while num != 0 and num % 5 == 0:
        num //= 5
        count += 1

    return count

# solve dp for either factor of 2 or 5
def solve_dp(cost, n):
    dp =[[INF for _ in range(n)]for _ in range(n)]
    parent = [['' for _ in range(n)] for _ in range(n)]

    dp[0][0] = cost[0][0]

    # fill the dp table
    for i in range(n):
        for j in range(n):

            if i == 0 and j == 0:
                continue

            # from top
            if i > 0 and dp[i - 1][j] != INF:
                new_cost = dp[i - 1][j] + cost[i][j]

                if new_cost < dp[i][j]:
                    dp[i][j] = new_cost
                    parent[i][j] = 'D'

            # from left
            if j > 0 and dp[i][j - 1] != INF:
                new_cost = dp[i][j - 1]  + cost[i][j]

                if new_cost < dp[i][j]:
                    dp[i][j] = new_cost
                    parent[i][j] = 'R'

    # reconstruct path
    path = []

    row = n - 1
    col = n - 1

    while row != 0 or col != 0:
        move = parent[row][col]
        path.append(move)

        if move == 'D':
            # we came from the cell above
            row -= 1

        elif move == 'R':
            # we came from cell on the left
            col -= 1

    path.reverse()

    return dp[n - 1][n - 1],''.join(path)

n = int(input())

matrix = [[0 for _ in range(n)] for _ in range(n)]

twos = [[0 for _ in range(n)] for _ in range(n)]
fives = [[0 for _ in range(n)] for _ in range(n)]

zero_row = -1
zero_col = -1

# input matrix and calculate factors
for i in range(n):
    values = list(map(int, input().split()))

    for j in range(n):
        matrix[i][j] = values[j]

        if matrix[i][j] == 0:
            zero_row = i
            zero_col = j

            twos[i][j] = INF
            fives[i][j] = INF

        else:
            twos[i][j] = count_twos(matrix[i][j])
            fives[i][j] = count_fives(matrix[i][j])

# find the best path for factors of 2 and 5
answer2 = solve_dp(twos, n)
answer5 = solve_dp(fives, n)

# choose the better of the two DP solutions
if answer2[0] < answer5[0]:
    best_zeros = answer2[0]
    best_path = answer2[1]
else:
    best_zeros = answer5[0]
    best_path = answer5[1]

    # Handle path through zero
if zero_row != -1 and best_zeros > 1:
    best_zeros = 1
    best_path = ""

    # Move down to zero's row
    for _ in range(zero_row):
        best_path += 'D'

    # Move right to zero's column
    for _ in range(zero_col):
        best_path += 'R'

    # Move down to the bottom
    for _ in range(zero_row, n - 1):
        best_path += 'D'

    # Move right to the end
    for _ in range(zero_col, n - 1):
        best_path += 'R'

print(best_zeros)
print(best_path)