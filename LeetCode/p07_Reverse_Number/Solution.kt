class Solution{
    fun reverse(n: Int): Int {
        var num = n
        var rev = 0

        while(num != 0){
            val digit = num % 10
            num /= 10

            if(rev > Int.MAX_VALUE / 10 || (rev == Int.MAX_VALUE / 10 && digit > 7)){
                return 0
            }

            if(rev < Int.MIN_VALUE / 10 || (rev == Int.MIN_VALUE / 10 && digit < -8)){
                return 0
            }

            rev = (rev * 10) + digit
        }

        return rev
    }
}