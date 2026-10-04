class StringToInteger{
    fun myAtoi(s: String): Int {
        var i = 0

        // point at the character after thw whitespaces
        while(i < s.length && s[i] == " "){
            i++
        }

        // check for sign
        var sign = 1
        if(i < s.length && s[i] == '-'){
            sign = -1
            i++
        }else if(i < s.length && s[i] == '+'){
            sign = 1
            i++
        }

        // build number
        var total = 0L

        // set limit
        val limit = if(sign == -1){
            2147483648L
        }else{
            2147483647L
        }

        while(i < s.length && s[i].isDigit()){
            val digit = s[i] - '0'

            // overflow
            if(total > limit / 10 || (total == limit / 10 && digit > limit % 10)){
                return if(sign == -1){
                    Int.MIN_VALUE
                }else{
                    Int.MAX_VALUE
                }
            }

            total = total * 10 + digit
            i++
        }

        return (total * sign).toInt()
    }
}