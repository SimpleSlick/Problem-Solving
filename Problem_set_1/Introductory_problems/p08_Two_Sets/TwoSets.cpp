#include <iostream>
#include <string>

using namespace std;

int main() {
    long long num;
    cin >> num;

    string set1 = "";
    string set2 = "";

    long long sum = num * (num + 1) / 2;

    if (sum % 2 == 0) {
        cout << "YES" << endl;

        if (num % 4 == 3) {
            set1 += "1 2 ";
            int count1 = 2;

            set2 += "3 ";
            int count2 = 1;

            for (long long i = 4; i <= num; i += 4) {
                set1 += to_string(i) + " " + to_string(i + 3) + " ";
                count1 += 2;

                set2 += to_string(i + 1) + " " + to_string(i + 2) + " ";
                count2 += 2;
            }

            cout << count2 << endl;
            cout << set2 << endl;
            cout << count1 << endl;
            cout << set1 << endl;

        } else {
            int count = 0;

            for (long long i = 1; i <= num; i += 4) {
                set1 += to_string(i) + " " + to_string(i + 3) + " ";
                set2 += to_string(i + 1) + " " + to_string(i + 2) + " ";
                count += 4;
            }

            cout << count / 2 << endl;
            cout << set1 << endl;
            cout << count / 2 << endl;
            cout << set2 << endl;
        }

    } else {
        cout << "NO" << endl;
    }

    return 0;
}