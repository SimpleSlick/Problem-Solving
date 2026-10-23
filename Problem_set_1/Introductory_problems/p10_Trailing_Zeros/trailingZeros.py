n = int(input())

result = 0
temp_num = 0

i = 5
while i <= n:
    temp_num = n / i
    result += temp_num

    i *= 5

print(result)