const prompt = require("prompt-sync")();

let n: number = Number(prompt());

let result: string = `${n}`;

while(n != 1){
    if(n % 2 === 0){
        n = n / 2;
    } else{
        n = n * 3 + 1;
    }
    result += ` ${n}`;
}

console.log(result);

export{};