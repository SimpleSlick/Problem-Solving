const input = require("prompt-sync")();

let n: number = Number(input())

let s: Set<number> = new Set();

for(let i = 0; i < n; i++){
    let x: number = Number(input());
    s.add(x);
}

console.log(s.size);