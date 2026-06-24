#include<iostream>

using namespace std;

int main(){
    long long number;
    cin >> number;

    if(number > 3){
        for(int i = 2; i <= number; i += 2){
            cout << i << " ";
        }

        for(int i = 1; i <= number; i += 2){
            cout << i << " ";
        }
    } else if(number == 1){
        cout << number;
    } else{
        cout << "NO SOLUTION";
    }
}