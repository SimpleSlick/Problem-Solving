function twoSums(nums: number[], target: number): number[]{
    for(let i:number = 0; i < nums.length; i++){
        for(let j: number = i + 1; j < nums.length; j++){
            if(nums[j] == target - nums[i]!){
                return [i, j];
            }
        }
    }
    return[];
}