num = int(input())

set1 = ""
set2 = ""

total_sum = num * (num + 1) // 2

if total_sum % 2 == 0:
    print("YES")

    if num % 4 == 3:
        set1 += "1 2 "
        count1 = 2

        set2 += "3 "
        count2 = 1

        i = 4
        while i <= num:
            set1 += str(i) + " " + str(i + 3) + " "
            count1 += 2

            set2 += str(i + 1) + " " + str(i + 2) + " "
            count2 += 2

            i += 4

        print(count2)
        print(set2)
        print(count1)
        print(set1)

    else:
        count = 0

        i = 1
        while i <= num:
            set1 += str(i) + " " + str(i + 3) + " "
            set2 += str(i + 1) + " " + str(i + 2) + " "
            count += 4
            i += 4

        print(count // 2)
        print(set1)
        print(count // 2)
        print(set2)

else:
    print("NO")