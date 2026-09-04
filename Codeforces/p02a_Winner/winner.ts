const input = require("prompt-sync")();

const num = Number(input());

const mapInfo = new Map<string, number>();
const playerName: String[] = [];
const playerPoint: number[] = [];

// Read Input
for(let i = 0; i < num; i++){
    const [name, point] = input().split(" ");
    const points = Number(point);

    playerName.push(name);
    playerPoint.push(points);

    mapInfo.set(name, (mapInfo.get(name) ?? 0) + points);
}

// Find the maximum score
let maxFinalScore = -Infinity;
for(const score of mapInfo.values()){
    if(score > maxFinalScore){
        maxFinalScore = score;
    }
}

// Track Live Scores
const liveScore = new Map<string, number>();

for(let i = 0; i < num; i++){
    const name: any = playerName[i];
    const point: any = playerPoint[i];

    liveScore.set(name, (liveScore.get(name) ?? 0) + point);

    const finalTotal = mapInfo.get(name)!;
    const currentTotal = liveScore.get(name)!;

    if(finalTotal === maxFinalScore && currentTotal >= maxFinalScore){
        console.log(name);
        break;
    }
}