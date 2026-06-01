#include <iostream>
#include <vector>

using namespace std;

int main(){
    long long n;
    cin >> n;

    vector <long long> number(n - 1);

    long long actual_sum = 0;

    for(int i = 0; i < n - 1; i++){
        cin >> number[i];
        actual_sum += number[i];
    }

    long long expected_sum = n * (n + 1) / 2;

    cout << expected_sum - actual_sum;
    return 0;
}