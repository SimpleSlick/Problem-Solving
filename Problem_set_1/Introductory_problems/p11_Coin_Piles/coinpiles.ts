const input = require("prompt-sync")();

let t: number = Number(input());

while(t > 0){
    const [x, y]: string[] = input().trim().split(/\s+/);
    const a: bigint = BigInt(x);
    const b: bigint = BigInt(y);

    if((a + b) % 3n === 0n && a <= 2n * b && b <= 2n * a){
        console.log("YES");
    }else{
        console.log("NO");
    }

    t--;
}