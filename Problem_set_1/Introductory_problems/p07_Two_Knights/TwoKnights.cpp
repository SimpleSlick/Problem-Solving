#include<iostream>

using namespace std;

int main(){
    long long n;
    cin >> n;

    for(int i = 1; i <= n; i++){
        long long totalWays = i * i * (i * i - 1) / 2;
        long long attackingWays = 4 * (i - 1) * (i - 2);

        long long result = totalWays - attackingWays;
        cout << result;
    }

    return 0;
}