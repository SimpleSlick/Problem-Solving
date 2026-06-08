const prompt = require("prompt-sync")();

const n: number = Number(prompt());

const numbers: number[] = prompt()
    .split(" ")
    .map(Number);

let actualSum = 0;

for (const num of numbers) {
    actualSum += num;
}

const expectedSum = n * (n + 1) / 2;

console.log(expectedSum - actualSum);

export {};