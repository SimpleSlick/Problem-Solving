class Solution{
    isPalindrome(x: number): boolean{
        // negative number
        if(x < 0){
            return false;
        }

        let temp = x, rev = 0;
        while(x != 0){
            let digit = x % 10;
            rev = (rev * 10) + digit;
            x /= 10;
        }

        return rev == temp;
    }
}