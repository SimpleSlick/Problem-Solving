#include<iostream>

using namespace std;

int main(){
    long n, m, a;

    cin >> n >> m >> a;

    long long side1 = (n + a - 1) / a;
    long long side2 = (m + a - 1) / a;

    long long result = side1 * side2;

    cout << result;

    return 0;
}