class Solution:
    def reverse(self, n: int) -> int:
        rev = 0

        while n != 0:
            digit = n % 10

            n /= 10

            if rev > (2**31 - 1) // 10 or (rev == (2**31 - 1) // 10 and digit > 7):
                return 0

            if rev < (-2**31) // 10 or (rev == (-2**31) // 10 and digit < -8):
                return 0

            rev = (rev * 10) + digit

        return rev