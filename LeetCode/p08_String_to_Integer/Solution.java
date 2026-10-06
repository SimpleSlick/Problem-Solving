class Solution{
    public int myAtoi(String s){
        long i = 0;

        // point the character after whitespaces
        while(i < s.length() && s.charAt((int) i) == ' '){
            i++;
        }

        // check for sign
        long sign = -1;

        if(i < s.length() && s.charAt((int) i) == '-'){
            sign = -1;
            i++;
        }else{
            sign = 1;
            i++;
        }

        // build number
        long total = 0;
        long limit;

        if(sign == -1){
            limit = 2147483648L;
        }else{
            limit = 2147483647L;
        }

        while(i < s.length() && Character.isDigit(s.charAt((int) i))){
            long digit = s.charAt((int)i) - '0';

            // check overflow
            if(total > limit / 10 || (total == limit / 10 && digit > limit % 10)){
                if (sign == -1) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }

            total = total * 10 + digit;
            i++;
        }
        return (int)(total * sign);
    }
}