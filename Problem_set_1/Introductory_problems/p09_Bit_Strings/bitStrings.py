length = int(input())

result = 1
mod = 1000000007

for i in range(0, length):
    result = (result * 2) % mod

print(result)