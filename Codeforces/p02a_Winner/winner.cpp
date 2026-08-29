#include<bits/stdc++.h>

using namespace std;

int main(){
    int num;
    cin >> num;

    unordered_map<string, int> map_info;
    vector<string> playerName;
    vector<int> playerPoint;

    // Store all entries and calculate the final score
    for(int i = 0; i < num; i++){
        string name;
        int points;

        cin >> name >> points;

        playerName.push_back(name);
        playerPoint.push_back(points);

        // to check if the player's point record already exist or not
        if(map_info.find(name) != map_info.end()){
            map_info[name] += points;
        }else{
            map_info[name] = points;
        }

        // find the maximum score
        int maxFinalScore = INT_MIN;

        for(auto &entry : map_info){
            if(entry.second > maxFinalScore){
                maxFinalScore = entry.second;
            }
        }

        unordered_map<string, int> live_score;

        for(int i = 0; i < num; i++){
            string name = playerName[i];
            int point = playerPoint[i];

            if(live_score.find(name) != live_score.end()){
                live_score[name] += point;
            }else{
                live_score[name] = point;
            }

            int finalTotal = map_info[name];
            int currentTotal = live_score[name];

            if(finalTotal == maxFinalScore && currentTotal >= maxFinalScore){
                cout << name << endl;
                break;
            }
        }
    }

    return 0;
}