const input = require("prompt-sync")();

let n: number = Number(input());

let result = 0;
let temp_num = 0;

for(let i = 5; i <= n; i *= 5){
    temp_num = n / i;
    result += temp_num;
}

console.log(result)