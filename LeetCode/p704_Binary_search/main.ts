class Solution{
    search(nums: number[], target: number): number{
        let n: number = nums.length;

        let low: number = 0;
        let high: number = n - 1;

        while(low <= high){
            let mid: number = low + Math.floor((high - low) / 2);

            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return -1;
    }
}