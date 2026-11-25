class Solution {
public:
    int search(vector<int>& nums, int target) {
        int n = nums.size();
        int low = 0, end = n - 1;

        while(low <= end){
            int mid = low + (end - low) / 2;

            if(nums[mid] == target){
                return mid;
            }

            // left sorted part
            if(nums[low] <= nums[mid]){
                if(nums[low] <= target && target < nums[mid]){
                    end = mid - 1;
                }else{
                    low = mid + 1;
                }
            }else{
                // right part
                if(nums[mid] < target && target <= nums[end]){
                    low = mid + 1;
                }else{
                    end = mid - 1;
                }
            }
        }

        return -1; // value not found
    }
};