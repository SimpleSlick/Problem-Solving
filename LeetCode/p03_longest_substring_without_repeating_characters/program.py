class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        result = 0
        hash_map = {}

        i = 0    # left pointer
        j = 0    # right pointer

        while j < len(s):
            ch = s[j]

            if ch in hash_map:
                i = max(hash_map[ch], i)

            result = max(result, j - i + 1)
            hash_map[ch] = j + 1

            j += 1
        return result