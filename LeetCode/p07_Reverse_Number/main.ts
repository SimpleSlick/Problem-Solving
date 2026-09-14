class Solution{
    reverse(n: number){
        let rev = 0;

        const INT_MAX = 2147483647;
        const INT_MIN = -2147483648;

        while(n != 0){
            let digit = n % 10;
            n = Math.trunc(n / 10);

            if(rev > Math.trunc(INT_MAX / 10) || (rev == Math.trunc(INT_MAX / 10) && digit > 7)){
                return 0;
            }

            if(rev > Math.trunc(INT_MIN / 10) || (rev == Math.trunc(INT_MIN / 10) && digit < -8)){
                return 0;
            }

            rev = (rev * 10) + digit;
        }

        return rev;
    }
}