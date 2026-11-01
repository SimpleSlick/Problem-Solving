class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        if (n > 2) {
            // Finding maximum and minimum element indexes
            int miniIdx = 0;
            int maxIdx = 0;

            for (int i = 1; i < n; i++) {
                if (nums[i] < nums[miniIdx]) {
                    miniIdx = i;
                }

                if (nums[i] > nums[maxIdx]) {
                    maxIdx = i;
                }
            }

            // Finding the leftmost and rightmost indexes
            int l = Math.min(miniIdx, maxIdx);
            int r = Math.max(miniIdx, maxIdx);

            // Choosing the minimum deletion alternative
            return Math.min(
                r + 1,          // Delete from left
                Math.min(
                    n - l,          // Delete from right
                    l + n + 1 - r   // Delete from both sides
                )
            );
        } else {
            return n;
        }
    }
}