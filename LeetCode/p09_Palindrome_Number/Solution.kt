class PalindromeNumber{
    fun isPalindrome(x: Int): Boolean {
        // negative number
        if(x < 0){
            return false
        }

        val temp = x
        var rev = 0
        var originalNum = x

        while(originalNum != 0){
            val digit = x % 10
            rev = rev * 10 + digit
            originalNum /= 10
        }

        return rev == temp
    }
}