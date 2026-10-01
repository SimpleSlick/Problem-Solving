class Solution:
    def myAtoi(self, s: str) -> int:
        i = 0

        # Point at the character after whitespaces
        while i < len(s) and s[i] == ' ':
            i += 1

        # check for sign
        sign = 1
        if i < len(s) and s[i] == '-':
            sign = -1
            i += 1
        elif i < len(s) and s[i] == '+':
            sign = 1
            i += 1

        # Build Number
        total = 0

        # Set Limit
        if sign == -1:
            limit = 2147483648
        else:
            limit = 2147483647

        while i < len(s) and s[i].isdigit():
            digit = ord(s[i] - ord('0'))

            # Check overflow
            if total > limit // 10 or (total == limit // 10 and digit > limit % 10):
                if sign == -1:
                    return -2147483648
                else:
                    return 2147483647

            total = total * 10 + digit
            i += 1

        return total * sign