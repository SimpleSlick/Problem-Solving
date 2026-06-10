const input = require("prompt-sync")();

const line = String(input());

let max_streak: number = 1;
let current_streak: number = 1;

for(let i: number = 1; i < line.length; i++){
    if(line[i] === line[i - 1]){
        current_streak += 1;
    } else{
        max_streak = Math.max(max_streak, current_streak);
        current_streak = 1;
    }
}

if(current_streak > max_streak){
    max_streak = current_streak;
}

console.log(max_streak);