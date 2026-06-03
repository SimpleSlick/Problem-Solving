str_input = str(input())

max_val = 1
current_val = 1

for ch in range(1, len(str_input)):
    if(str_input[ch] == str_input[ch - 1]):
        current_val += 1
    else:
        max_val = max(max_val, current_val)
        current_val = 1

if(current_val > max_val):
    max_val = current_val

print(max_val)