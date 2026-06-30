const input = require("prompt-sync")();

let t: number = Number(input())

while(t-- > 0){
    let [y, x] = input().split(" ").map(Number)

    let z: number = Math.max(y, x)
    // let result: number;

    if(z % 2 == 0){
        if(y == z){
            console.log(z * z - x + 1);
        } else{
            console.log((z - 1) * (z - 1) + y);
        }
    } else{
        if(x == z){
            console.log(z * z - y + 1);
        } else{
            console.log((z - 1) * (z - 1) + x);
        }
    }
}