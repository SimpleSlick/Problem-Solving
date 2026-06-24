number = int(input())

even_num = []
odd_num = []

if(number > 3):
    for i in range(1, number + 1):
        if(i % 2 == 0):
            even_num.append(str(i))
        else:
            odd_num.append(str(i))
        
    print(" ".join(even_num + odd_num))
elif number == 1:
    print(number)
else:
    print("NO SOLUTION")