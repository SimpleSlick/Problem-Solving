class Solution{
    findMedianSortedArrays(nums1: number[], nums2: number[]): number{
        if(nums1.length > nums2.length){
            [nums1, nums2] = [nums2, nums1];
        }

        const m = nums1.length;
        const n = nums2.length;

        let low = 0;
        let high = m;

        while(low <= high){
            // divider in nums1 and nums2
            const partition1 = low + Math.floor((high - low) / 2);
            const partition2 = Math.floor((m + n + 1) / 2) - partition1;

            // values around the divider
            const left1 = partition1 === 0 ? -Infinity : nums1[partition1 - 1]!;
            const right1 = partition1 === m ? Infinity: nums1[partition1]!;
            const left2 = partition2 === 0 ? -Infinity : nums2[partition2 - 1]!;
            const right2 = partition2 === n ? Infinity: nums2[partition1]!;

            // correct partition
            if(left1 <= right2 && left2 <= right1){
                // odd number of elements
                if((m + n) % 2 === 1){
                    return Math.max(left1, left2);
                }

                return(
                    Math.max(left1, left2) + Math.min(right1, right2)
                ) / 2;
            }
            else if(left1 > right2){
                high = partition1 - 1;
            }else{
                low = partition1 + 1;
            }
        }
        return 0.0;
    }
}