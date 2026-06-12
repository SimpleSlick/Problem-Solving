const input = require("prompt-sync")();

const size_array: number = Number(input())

const num_arr: number[] = input().split(" ").map(Number);

let totalMoves: number = 0;

for (let i: number = 1; i < size_array; i++) {
  const previous = num_arr[i - 1]!;
  const current = num_arr[i]!;

  if (current < previous) {
    const gap = previous - current;
    totalMoves += gap;
    num_arr[i] = previous;
  }
}

console.log(totalMoves);

export {};