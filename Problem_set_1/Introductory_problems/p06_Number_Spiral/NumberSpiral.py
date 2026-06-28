t = int(input())

out = []

while(t > 0):
    y, x = list(map(int, input().split()))
    z = max(x, y)

    result = 0
    if z % 2 == 0:
        if z == y:
            result = (z - 1) * (z - 1) + y
            print(result)
        else:
            result = z * z - y + 1
            print(result)
    else:
        if z == x:
            result = z * z - y + 1
            print(result)
        else:
            result = (z - 1) * (z - 1) + x
            print(result)
    t -= 1