import java.util.*;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int result = 0;
        Map<Character, Integer> hash_map = new HashMap<>();
        int i = 0; //left pointer
        int j = 0; //right pointer
        while(j < s.length()){
            char ch = s.charAt(j);
            if(hash_map.containsKey(ch)){
                i = Math.max(hash_map.get(ch), i);
            }
            result = Math.max(result, j - i + 1);
            hash_map.put(ch, j + 1);
            j++;
        }
        return result;
    }
}