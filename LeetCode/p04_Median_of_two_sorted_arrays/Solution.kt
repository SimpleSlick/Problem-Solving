class MedianofArrays {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        var array1 = nums1
        var array2 = nums2

        if(nums1.size > nums2.size){
            array1 =  array2.also { array2 = array1 }
        }

        var m = array1.size
        var n = array2.size

        var low = 0
        var high = m

        while(low <= high){
            var partition1 = low + (high - low) / 2
            var partition2 = (m + n - 1) / 2 - partition1

            // value around the dividor
            val left1 = if (partition1 == 0) Int.MIN_VALUE else nums1[partition1 - 1]
            val right1 = if(partition1 == m) Int.MAX_VALUE else nums1[partition1]
            val left2 = if (partition2 == 0) Int.MIN_VALUE else nums2[partition2 - 1]
            val right2 = if(partition2 == n) Int.MAX_VALUE else nums2[partition2]

            // correct parition
            if(left1 <= right2 && left2 <= right1){
                // odd number of elements
                if((m + n) % 2 == 1){
                    return Math.max(left1, left2).toDouble();
                }

                // even number of elements
                return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0
            }else if(left1 > right2){
                high = partition1 - 1;
            }else{
                low = partition1 + 1;
            }
        }
        return 0.0
    }
}