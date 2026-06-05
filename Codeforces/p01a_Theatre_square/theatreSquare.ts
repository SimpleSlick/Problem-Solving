const input = require("prompt-sync")();

let [n, m, a] = input().split(" ").map(Number);

let side1: number = (n + a - 1) / a;
let side2: number = (m + a - 1) / a;

let result: number = side1 * side2;
console.log(result.toFixed(0));