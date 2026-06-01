n = int(input())

numbers = []
actual_sum = 0

for i in input().split():
    numbers.append(int(i))
    actual_sum = sum(numbers)

excepted_sum = n + (n + 1) / 2
print(int(excepted_sum - actual_sum))