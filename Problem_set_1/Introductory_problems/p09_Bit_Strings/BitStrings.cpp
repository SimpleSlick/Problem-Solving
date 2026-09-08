#include<bits/stdc++.h>

using namespace std;

int main(){
    long long length, result = 1, mod = 1000000007;
    cin >> length;

    for(int i = 0; i < length; i++){
        result = (result * 2) % mod;
    }

    cout << result << endl;

    return 0;
}