#include <iostream>
using namespace std;

int main(){
    long long n;
    cin >> n;

    long long result = 0, temp_num = 0;

    for (long long i = 5; i <= n; i *= 5){
        temp_num = n / i;
        result += temp_num;
    }

    cout << result << endl;

    return 0;
}