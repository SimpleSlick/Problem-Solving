class Solution {
public:
    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {
        if(nums1.size() > nums2.size()){
            swap(nums1, nums2);
        }

        int m = nums1.size();
        int n = nums2.size();

        int low = 0;
        int high = m;

        while(low <= high){
            // divider in nums1 and nums2
            int partition1 = low + (high - low) / 2;
            int partition2 = (m + n + 1) / 2 - partition1;

            // value around the dividor
            int left1 = (partition1 == 0) ? INT_MIN : nums1[partition1 - 1];
            int right1 = (partition1 == m) ? INT_MAX : nums1[partition1];

            int left2 = (partition2 == 0) ? INT_MIN : nums2[partition2 - 1];
            int right2 = (partition2 == n) ? INT_MAX : nums2[partition2];

            // correct divisor
            if(left1 <= right2 && left2 <= right1){
                // Odd number of elements
                if((m + n) % 2 == 1){
                    return max(left1, left2);
                }

                // Even number of elements
                return(max(left1, left2) + min(right1, right2)) / 2.0;
            }
            // Dividor was too far from right
            else if(left1 > right2){
                high = partition1 - 1;
            }
            
            // Dividor was too far from left
            else{
                low = partition1 + 1;
            }
        }

        return 0.0;
    }
};