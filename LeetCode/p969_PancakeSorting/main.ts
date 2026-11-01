class Solution {
  flip(array: number[], k: number): void {
    let left = 0;
    let right = k;

    while (left < right) {
      [array[left], array[right]] = [array[right], array[left]];

      left++;
      right--;
    }
  }

  isSorted(array: number[]): boolean {
    return array.every(
      (value, index) => index === 0 || array[index - 1] <= value,
    );
  }

  pancakeSort(arr: number[]): number[] {
    if (this.isSorted(arr)) {
      return [];
    }

    const result: number[] = [];
    const n = arr.length;

    for (let currentSize = n; currentSize > 1; currentSize--) {
      let maxElement = 0;

      // Finding the maximum element
      for (let i = 0; i < currentSize; i++) {
        if (arr[i] > arr[maxElement]) {
          maxElement = i;
        }
      }

      // If element is already placed
      if (maxElement === currentSize - 1) {
        continue;
      }

      if (maxElement !== 0) {
        this.flip(arr, maxElement);
        result.push(maxElement + 1);
      }

      this.flip(arr, currentSize - 1);
      result.push(currentSize);
    }

    return result;
  }
}