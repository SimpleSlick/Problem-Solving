n, m, a = map(int, input().split())

side1 = (n + a - 1) // a
side2 = (m + a - 1) // a

result = side1 * side2

print(result)