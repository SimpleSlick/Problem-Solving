class Solution:
    def isPalindrome(self, x: int) -> bool:
        # negative number
        if(x < 0):
            return False

        temp, rev = x, 0
        while x != 0:
            digit = x % 10
            rev = (rev * 10) + digit
            x /= 10

        if(rev == temp):
            return True
        else:
            return False 