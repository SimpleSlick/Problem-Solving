#include<bits/stdc++.h>

using namespace std;

class Solution{
public:
    int lengthOfSubString(string s){
        int result = 0;
        unordered_map<char, int> hash_map;

        int i = 0;    // left pointer
        int j = 0;   // right pointer

        while(j < s.length()){
            char ch = s[j];

            if(hash_map.find(ch) != hash_map.end()){
                i = max(hash_map[ch], i);
            }

            result = max(result, j - i + 1);
            hash_map[ch] = j + 1;

            j++;
        }

        return result;
    }
};