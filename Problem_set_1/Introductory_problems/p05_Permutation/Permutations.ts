const input = require("prompt-sync")();

let num: number = Number(input())

let even_num: string[] = [];
let odd_num: string[]= [];

if(num > 3){
    for(let i: number = 1; i <= num; i++){
        if(i % 2 == 0){
            even_num.push(String(i));
        } else{
            odd_num.push(String(i));
        }
    }
    console.log([...even_num, ...odd_num]. join(" "));
}else if(num == 1){
    console.log(num);
}else{
    console.log("NO SOLUTION");
}