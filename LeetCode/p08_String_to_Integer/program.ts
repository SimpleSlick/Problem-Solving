class Solution{
    myAtoi(s: String): number{
        let i: number = 0;

        // point the character after white spaces
        while(i < s.length && s[i] === ' '){
            i++;
        }

        // check for sign
        let sign: number = 1;
        if(i < s.length && s[i] === '-'){
            sign = -1;
            i++;
        }else if(i < s.length && s[i] === '+'){
            sign = 1;
            i++;
        }

        // build number
        let total: number = 0;

        // limit set
        let limit: number;

        if(sign === -1){
            limit = 2147483648;
        }else{
            limit = 2147483647;
        }

        while(i < s.length && s[i]! >= '0' && s[i]! <= '9'){
            const digit: number = Number(s[i]);

            // check overflow
            if(total > Math.floor(limit / 10) || (total === Math.floor(limit / 10)) && digit > limit % 10){
                if(sign == -1){
                    return -2147483648; // int min
                }else{
                    return 2147483647; // int max
                }
            }

            total = total * 10 + digit;
            i++;
        }

        return total * sign;
    }
}