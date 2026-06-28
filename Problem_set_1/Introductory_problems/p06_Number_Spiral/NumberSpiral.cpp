#include<iostream>

using namespace std;

int main(){
    int t;
    cin >> t;

    while(t-- > 0){
        long long y, x;
        cin >> y >> x;

        long long z = max(y, x);
        
        if(z % 2 == 0){
            if(y == z){
                cout << z * z - x + 1;
            } else{
                cout << (z - 1) * (z - 1) + y;
            }
        } else{
            if(x == z){
                cout << z * z - y + 1;
            } else{
                cout << (z - 1) * (z - 1) + x;
            }
        }
    }
    return 0;
}