const input = require("prompt-sync")();

const num: bigint = BigInt(input());

let set1 = "";
let set2 = "";const sum = (num * (num + 1n)) / 2n;

if (sum % 2n === 0n) {
    console.log("YES");

    if (num % 4n === 3n) {
        set1 += "1 2 ";
        let count1 = 2;

        set2 += "3 ";
        let count2 = 1;

        for (let i = 4n; i <= num; i += 4n) {
            set1 += `${i} ${i + 3n} `;
            count1 += 2;

            set2 += `${i + 1n} ${i + 2n} `;
            count2 += 2;
        }

        console.log(count2);
        console.log(set2);
        console.log(count1);
        console.log(set1);
    } else {
        let count = 0;

        for (let i = 1n; i <= num; i += 4n) {
            set1 += `${i} ${i + 3n} `;
            set2 += `${i + 1n} ${i + 2n} `;
            count += 4;
        }

        console.log(count / 2);
        console.log(set1);
        console.log(count / 2);
        console.log(set2);
    }
} else {
    console.log("NO");
}