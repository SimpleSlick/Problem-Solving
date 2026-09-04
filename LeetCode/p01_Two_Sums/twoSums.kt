package problemSolving.LeetCode

class Solution{
    fun TwoSums(nums: Array<Int>, target: Int): IntArray{
        for(i in 0 until nums.size){
            var j = i + 1
            for(j in 0 until nums.size){
                if(nums[i] + nums[j] == target){
                    return intArrayOf(i, j)
                }
            }
        }
        return intArrayOf(0, 0)   // values not found
    }
}