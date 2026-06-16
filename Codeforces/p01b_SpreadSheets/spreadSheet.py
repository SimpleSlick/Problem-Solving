import re

# RXCY -> Alphanumeric
def row_coordinate(input_str):
    c_Index = input_str.index("C")

    row = input_str[1 : c_Index]
    col = input_str[c_Index + 1:]

    num = int(col)
    col_string = ""

    while num > 0:
        num -= 1
        remainder = num % 26
        col_string = chr(ord('A') + remainder) + col_string
        num //= 26
    
    print(col_string + row)

# Alphanumeric -> RXCY
def row_col_system(str):
    first_digit = 0
    while(first_digit < len(str) and not str[first_digit].isdigit()):
        first_digit += 1

    row = str[first_digit:]
    col = str[:first_digit]

    total = 0

    for letter in col:
        value = ord(letter) - ord('A') + 1
        total = total * 26 + value

    print(f"R{row}C{total}")

n = int(input())

for _ in range(n):
    s = input()

    if re.fullmatch(r"R/d+C/d+", s):
        row_coordinate(s)
    else:
        row_col_system(s)