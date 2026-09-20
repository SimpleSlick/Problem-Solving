#include<bits/stdc++.h>

using namespace std;

const int INF = 1e9;

int countTwos(int num){
    int count = 0;

    while(num != 0 && num % 2 == 0){
        num /= 2;
        count ++;
    }

return count;
}

int countFives(int num){
    int count = 0;

    while(num != 0 && num % 5 == 0){
        num /= 5;
        count++;
    }

return count;
}

// Solve DP for either factor 2 or factor 5
pair<int, string> solveDP(vector<vector<int>>& cost, int n){
    vector<vector<int>> dp(n, vector<int>(n, INF));
    vector<vector<char>> parent(n, vector<char>(n));

    dp[0][0] = cost[0][0];

    // fill the DP table
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            if(i == 0 && j == 0){
                continue;
            }

            // from top
            if(i > 0 && dp[i - 1][j] != INF){
                int newCost = dp[i - 1][j] + cost[i][j];

                if(newCost < dp[i][j]){
                    dp[i][j] = newCost;
                    parent[i][j] = 'D';
                }
            }
            
            // from left
            if(j > 0 && dp[i][j - 1] != INF){
                int newCost = dp[i][j - 1] + cost[i][j];
                
                if(newCost < dp[i][j]){
                    dp[i][j] = newCost;
                    parent[i][j] = 'R';
                }
            }
        }
    }

    // Reconstruct Path
    string path;

    int row = n - 1;
    int col = n - 1;

    while(row != 0 || col != 0){
        char move = parent[row][col];

        path += move;

        if(move == 'D'){
            // we came from the above cell
            row--;
        }else if(move == 'R'){
            // we came form the cell on the left
            col--;
        }
    }

    reverse(path.begin(), path.end());

    return{dp[n - 1][n - 1], path};
}

int main(){
    int n;
    cin >> n;

    vector<vector<int>> matrix(n, vector<int>(n));

    vector<vector<int>> twos(n, vector<int>(n));
    vector<vector<int>> fives(n, vector<int>(n));

    int zeroRow = -1;
    int zeroCol = -1;

    // input matrix and calculate factors
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            cin >> matrix[i][j];

            if(matrix[i][j] == 0){
                zeroRow = i;
                zeroCol = j;

                // dont allow normal DP to use Zero
                twos[i][j] = INF;
                fives[i][j] = INF;
            }else{
                twos[i][j] = countTwos(matrix[i][j]);
                fives[i][j] = countFives(matrix[i][j]);
            }
        }
    }

    // finding the best path for fives and twos
    pair<int, string> answer2 = solveDP(twos, n);
    pair<int, string> answer5 = solveDP(fives, n);

    int bestZeros;
    string bestPath;

    // choose the better of two dp soln
    if(answer2.first < answer5.first){
        bestZeros = answer2.first;
        bestPath = answer2.second;
    }else{
        bestZeros = answer5.first;
        bestPath = answer5.second;
    }

    if(zeroRow != -1 && bestZeros > 1){
        bestZeros = 1;
        bestPath = "";

        // move down to zero's row
        for(int i = 0; i < zeroRow; i++){
            bestPath += 'D';
        }

        // move right to zero's column
        for(int i = 0; i < zeroCol; i++){
            bestPath += 'R';
        }

        for(int i = zeroRow; i < n - 1; i++){
            bestPath += 'D';
        }

        for(int i = zeroCol; i < n - 1; i++){
            bestPath += 'R';
        }
    }

    cout << bestZeros << endl;
    cout << bestPath << endl;

    return 0;
}