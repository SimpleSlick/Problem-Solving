t = int(input())
for _ in range(t):
    a, b = map(int, input().split())
    if (a + b) % 3 == 0 and a <= 2 * b and b <= 2 * a:
        print("YES")
    else:
        print("NO")