const INF = 1_000_000_000;

function countTwos(num: number): number {
    let count = 0;

    while (num !== 0 && num % 2 === 0) {
        num /= 2;
        count++;
    }

    return count;
}

function countFives(num: number): number {
    let count = 0;

    while (num !== 0 && num % 5 === 0) {
        num /= 5;
        count++;
    }

    return count;
}

// Result of DP
interface DPResult {
    cost: number;
    path: string;
}

// Solve DP for either factor 2 or factor 5
function solveDP(cost: number[][], n: number): DPResult {

    const dp: number[][] = Array.from(
        { length: n },
        () => Array(n).fill(INF)
    );

    const parent: string[][] = Array.from(
        { length: n },
        () => Array(n).fill("")
    );

    dp[0][0] = cost[0][0];

    // Fill the DP table
    for (let i = 0; i < n; i++) {

        for (let j = 0; j < n; j++) {

            if (i === 0 && j === 0) {
                continue;
            }

            // From top
            if (i > 0 && dp[i - 1][j] !== INF) {

                const newCost = dp[i - 1][j] + cost[i][j];

                if (newCost < dp[i][j]) {
                    dp[i][j] = newCost;
                    parent[i][j] = "D";
                }
            }

            // From left
            if (j > 0 && dp[i][j - 1] !== INF) {

                const newCost = dp[i][j - 1] + cost[i][j];

                if (newCost < dp[i][j]) {
                    dp[i][j] = newCost;
                    parent[i][j] = "R";
                }
            }
        }
    }

    // Reconstruct path
    let path = "";

    let row = n - 1;
    let col = n - 1;

    while (row !== 0 || col !== 0) {

        const move = parent[row][col];

        path += move;

        if (move === "D") {
            // We came from the cell above
            row--;
        } else if (move === "R") {
            // We came from the cell on the left
            col--;
        }
    }

    // Reverse path
    path = path.split("").reverse().join("");

    return {
        cost: dp[n - 1][n - 1],
        path: path
    };
}

const input = require("prompt-sync")();

let index = 0;

const n = Number(input[index++]);

const matrix: number[][] = Array.from(
    { length: n },
    () => Array(n).fill(0)
);

const twos: number[][] = Array.from(
    { length: n },
    () => Array(n).fill(0)
);

const fives: number[][] = Array.from(
    { length: n },
    () => Array(n).fill(0)
);

let zeroRow = -1;
let zeroCol = -1;


// Input matrix and calculate factors
for (let i = 0; i < n; i++) {

    for (let j = 0; j < n; j++) {

        matrix[i][j] = Number(input[index++]);

        if (matrix[i][j] === 0) {

            zeroRow = i;
            zeroCol = j;

            // Don't allow normal DP to use zero
            twos[i][j] = INF;
            fives[i][j] = INF;

        } else {

            twos[i][j] = countTwos(matrix[i][j]);
            fives[i][j] = countFives(matrix[i][j]);
        }
    }
}


// Find the best path for factors 2 and 5
const answer2 = solveDP(twos, n);
const answer5 = solveDP(fives, n);

let bestZeros: number;
let bestPath: string;


// Choose the better of the two DP solutions
if (answer2.cost < answer5.cost) {

    bestZeros = answer2.cost;
    bestPath = answer2.path;

} else {

    bestZeros = answer5.cost;
    bestPath = answer5.path;
}


// If zero exists and normal DP gives more than 1 trailing zero
if (zeroRow !== -1 && bestZeros > 1) {

    bestZeros = 1;
    bestPath = "";

    // Move down to zero's row
    for (let i = 0; i < zeroRow; i++) {
        bestPath += "D";
    }

    // Move right to zero's column
    for (let i = 0; i < zeroCol; i++) {
        bestPath += "R";
    }

    // Move down to the bottom
    for (let i = zeroRow; i < n - 1; i++) {
        bestPath += "D";
    }

    // Move right to the rightmost column
    for (let i = zeroCol; i < n - 1; i++) {
        bestPath += "R";
    }
}


// Output
console.log(bestZeros);
console.log(bestPath);