#include<iostream>
#include<vector>

using namespace std;

int main(){
    int size_array;
    cin >> size_array;

    vector<int> num_arr(size_array);

    for(int i = 0; i < num_arr.size(); i++){
        cin >> num_arr[i];
    }

    long long totalMoves = 0;
    for(int i = 1; i < num_arr.size(); i++){
        int current = num_arr[i];
        int previous = num_arr[i - 1];

        if(current < previous){
            long long gap = previous - current;
            totalMoves += gap;
            num_arr[i] = previous;
        }
    }

    cout << totalMoves << endl;
    return 0;
}