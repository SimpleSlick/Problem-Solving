class Solution:
    def flip(self, array, k):
        array[:k + 1] = reversed(array[:k + 1])

    def isSorted(self, array):
        return array == sorted(array)

    def pancakeSort(self, arr):
        if self.isSorted(arr):
            return []

        result = []
        n = len(arr)

        for currentSize in range(n, 1, -1):
            maxElement = 0

            # Finding the maximum element
            for i in range(currentSize):
                if arr[i] > arr[maxElement]:
                    maxElement = i

            # If any element is already placed
            if maxElement == currentSize - 1:
                continue

            if maxElement != 0:
                self.flip(arr, maxElement)
                result.append(maxElement + 1)

            self.flip(arr, currentSize - 1)
            result.append(currentSize)

        return result