const input = require("prompt-sync")();

let n: number = Number(input())

for(let j = 1; j <= n; j++){
    let totalWays = Math.pow(j, 2) * (Math.pow(j, 2) - 1) / 2;
    let attackingWays = 4 * (j - 1) * (j - 2);

    let result = totalWays - attackingWays;
    console.log(result);
}