package LeetCode.p04_Median_of_two_sorted_arrays;

public class Solution {
    public double findMedianSortedArray(int[] nums1, int[] nums2){
        if(nums1.length > nums2.length){
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int m = nums1.length;
        int n = nums1.length;

        int low = 0, high = m;

        while(low <= high){
            // divider in nums1 and nums2
            int partition1 = low + (high - low) / 2;
            int partition2 = (m + n - 1) / 2 - partition1;

            // value around the divider
            int left1 = (partition1 == 0) ? Integer.MIN_VALUE: nums1[partition1 - 1];
            int right1 = (partition1 == m) ? Integer.MAX_VALUE: nums1[partition1];
            int left2 = (partition2 == 0) ? Integer.MIN_VALUE: nums1[partition2 - 1];
            int right2 = (partition1 == n) ? Integer.MAX_VALUE: nums1[partition2];

            // correct partition
            if(left1 <= right2 && left2 <= right1){
                // odd number of elements
                if((m + n) % 2 == 1){
                    return Math.max(left1, left2);
                }

                // even number of elements
                return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
            }
            // Partition1 is too far away from the right
            else if(left1 > right2){
                high = partition1 - 1;
            }

            // Partition is too far away from left
            else{
                low = partition1 + 1;
            }
        }
        
        return 0.0;
    }
}