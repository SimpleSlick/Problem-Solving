class Solution {
    minimumDeletions(nums: number[]): number {
        const n = nums.length;

        if (n > 2) {
            // Finding maximum and minimum element indexes
            const miniIdx = nums.indexOf(Math.min(...nums));
            const maxIdx = nums.indexOf(Math.max(...nums));

            // Finding the leftmost and rightmost indexes
            const l = Math.min(miniIdx, maxIdx);
            const r = Math.max(miniIdx, maxIdx);

            // Choosing the minimum deletion alternative
            return Math.min(
                r + 1,          // Delete from left
                n - l,          // Delete from right
                l + n + 1 - r   // Delete from both sides
            );
        } else {
            return n;
        }
    }
}