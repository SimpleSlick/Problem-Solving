const input = require("prompt-sync")();

function rowCoordinate(input: string){
    let cIndex: number = input.indexOf('C');

    let row: string = input.substring(1, cIndex);
    let col: string = input.substring(cIndex + 1);

    let num: number = Number(col);
    let colString: string = "";

    while(num > 0){
        num--;
        let remainder: number = num % 26;
        let ch: string = String.fromCharCode('a'.charCodeAt(0) + remainder);
        colString = ch + colString;
        num = Math.floor(num / 26);
    }

    console.log(colString + row);
}

function rowColSystem(input: string){
    let firstDigitIndex = 0;
    while(firstDigitIndex < input.length && !/^\d$/.test(input.charAt(firstDigitIndex))){
        firstDigitIndex++;
    }

    let row: string = input.substring(firstDigitIndex);
    let col: string = input.substring(0, firstDigitIndex);

    let total: number = 0;
    for(let i = 0; i < col.length; i++){
        let letter: string = col.charAt(i);
        let value: number = letter.charCodeAt(0) - 'A'.charCodeAt(0) + 1;

        total *= 26;
        total += value;
    }

    console.log(`R${row}C${total}`);
}

let n: number = Number(input());

for(let i = 0; i < n; i++){
    let str: string = String(input());

    if(/^R\d+C\d+$/.test(str)){
        rowCoordinate(str);
    }else{
        rowColSystem(str);
    }
}