class Solution {
public:
    int minimumDeletions(vector<int>& nums) {
        int n = nums.size();

        if(n > 2){
        // finding maximum or minimum element in the array
        int miniIdx = min_element(nums.begin(), nums.end()) - nums.begin();
        int maxIdx = max_element(nums.begin(), nums.end()) - nums.begin();

        // find the maximum and minimum pointer indexes
        int l = min(miniIdx, maxIdx);
        int r = max(miniIdx, maxIdx);

        // chose from the correct alternatives
        return min({
            r + 1,          // delete from left
            n - l,          // delete from right
            l + n + 1 - r   // delete from both the sides
        });
        }else{
            return n;
        }
        return 0; // if found nothing
    }
};