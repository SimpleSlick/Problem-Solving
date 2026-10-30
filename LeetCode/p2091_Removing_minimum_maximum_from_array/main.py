class Solution:
    def minimumDeletions(self, nums: list[int]) -> int:
        n = len(nums)

        if n > 2:
            # finding maximum and minimum element indexes
            miniIdx = nums.index(min(nums))
            maxIdx = nums.index(max(nums))

            # finding the leftmost and rightmost indexes
            l = min(miniIdx, maxIdx)
            r = max(miniIdx, maxIdx)

            # choosing the min deletion alternative
            return(min, r + 1, n - 1, l + n + 1 - r)
        
        else:
            return n