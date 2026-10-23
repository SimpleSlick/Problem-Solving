#include<bits/stdc++.h>

using namespace std;

class Solution {
public:
    void flip(vector<int> &array, int k){
        reverse(array.begin(), array.begin() + k + 1);
    }

    bool isSorted(vector<int> &array){
        return is_sorted(array.begin(), array.end());
    }

    vector<int> pancakeSort(vector<int>& arr) {
        if(isSorted(arr)){
            return {};
        }

        vector<int> result;

        int n = arr.size();

        for(int currentSize = n; currentSize > 1; currentSize--){
            int maxElement = 0;

            // finding the maximum
            for(int i = 0; i < currentSize; i++){
                if(arr[i] > arr[maxElement]){
                    maxElement = i;
                }
            }

            // if element is placed at its position
            if(maxElement == currentSize - 1){
                continue;
            }
                if(maxElement != 0){
                    flip(arr, maxElement);
                    result.push_back(maxElement + 1);
                }

                flip(arr, currentSize - 1);
                result.push_back(currentSize);
        }

        return result;
    }
};