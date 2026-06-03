#include<iostream>
#include<string>
#include<algorithm>

using namespace std;

int main(){
    string input;

    getline(cin, input);

    int max_streak = 1, current_streak = 1;

    for(int i = 1; i < input.length(); i++){
        if(input[i] == input[i - 1]){
            current_streak++;
        } else{
            max_streak = max(max_streak, current_streak);
            current_streak = 1;
        }
    }

    if(current_streak > max_streak){
        max_streak = current_streak;
    }

    cout << max_streak;

    return 0;
}