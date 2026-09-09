const input = require("prompt-sync")();

let length: number = Number(input());

let result = 1, mod = 1000000007;

for(let i = 0; i < length; i++){
    result = (result * 2) % mod;
}

console.log(result);