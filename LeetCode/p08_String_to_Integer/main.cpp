class Solution {
public:
    int myAtoi(string s) {
        long long i = 0;

        // point at the character after the whitespaces
        while(i < s.length() && s[i] == ' '){
            i++;
        }

        // check for sign
        long long sign = 1;
        if(i < s.length() && s[i] == '-'){
            sign = -1;
            i++;
        }else if(i < s.length() && s[i] == '+'){
            sign = 1;
            i++;
        }

        // build number
        long long total = 0;

        // limit set
        long long limit;

        if(sign == -1){
            limit = 2147483648LL;
        }else{
            limit = 2147483647LL;
        }

        while(i < s.length() && isdigit(s[i])){
            long long digit = s[i] - '0';

            // check overflow
            if(total > limit / 10 || (total == limit / 10 && digit > limit % 10)){
                if(sign == -1){
                    return INT_MIN;
                }else{
                    return INT_MAX;
                }
            }

            total = total * 10 + (s[i] - '0');
            i++;
        }

        return (int)(total * sign);
    }
};