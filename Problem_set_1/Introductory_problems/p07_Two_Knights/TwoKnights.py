n = int(input())

for i in range(1, n + 1):
    totalWays = i ** 2 * (i * i - 1) / 2
    attackingWays = 4 * (i - 1) * (i - 2)

    result = totalWays - attackingWays
    print(result)