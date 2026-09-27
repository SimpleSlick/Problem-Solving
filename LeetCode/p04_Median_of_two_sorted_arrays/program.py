class Solution:
    def findMedianSortedArrays(self, nums1: list[int], nums2: list[int]) -> float:

        # Always perform binary search on the smaller array
        if len(nums1) > len(nums2):
            nums1, nums2 = nums2, nums1

        m = len(nums1)
        n = len(nums2)

        low = 0
        high = m

        while low <= high:

            # Partition in nums1 and nums2
            partition1 = low + (high - low) // 2
            partition2 = (m + n + 1) // 2 - partition1

            # Values around the partition
            left1 = float("-inf") if partition1 == 0 else nums1[partition1 - 1]
            right1 = float("inf") if partition1 == m else nums1[partition1]

            left2 = float("-inf") if partition2 == 0 else nums2[partition2 - 1]
            right2 = float("inf") if partition2 == n else nums2[partition2]

            # Correct partition
            if left1 <= right2 and left2 <= right1:

                # Odd number of elements
                if (m + n) % 2 == 1:
                    return float(max(left1, left2))

                # Even number of elements
                return (max(left1, left2) + min(right1, right2)) / 2

            # Partition 1 is too far to the right
            elif left1 > right2:
                high = partition1 - 1

            # Partition 1 is too far to the left
            else:
                low = partition1 + 1

        return 0.0