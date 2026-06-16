#include<iostream>
#include<string>
#include<regex>

using namespace std;

// RXCY -> Alphanumeric
void rowCoordinate(string input){
    int cIndex = input.find('C');

    string row = input.substr(1, cIndex -1);
    string col = input.substr(cIndex + 1);

    long long num = stoll(col);
    string colString = "";

    while(num > 0){
        num--;
        long long remainder = num % 26;
        char ch = 'A' + remainder;
        colString = ch + colString;
        num /= 26;
    }
    
    cout << colString << row << "\n";
}

// Alphanumeric -> RXCY
void rowColSystem(string input){
    int firstDigitIndex = 0;

    while(firstDigitIndex < input.size() && !isdigit(input[firstDigitIndex])){
        firstDigitIndex++;
    }

    string row = input.substr(firstDigitIndex);
    string col = input.substr(0, firstDigitIndex);

    long long total = 0;

    for(char letter : col){
        int value = letter - 'A' + 1;
        total = total * 26 + value;
    }

    cout << "R" << row << "C" << total << "\n";
}

int main(){
    int n;
    cin >> n;

    regex pattern("^R[0-9] + C[0-9]+$");

    while(n--){
        string str;
        cin >> str;

        if(regex_match(str, pattern)){
            rowCoordinate(str);
        } else{
            rowColSystem(str);
        }
    }

    return 0;
}