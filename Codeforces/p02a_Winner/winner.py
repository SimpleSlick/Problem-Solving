num = int(input())

map_info = {}
playerName = []
playerPoint = []

for _ in range(num):
    name, points = input().split()
    points = int(points)

    playerName.append(name)
    playerPoint.append(points)

    # Check if the player's point record already exists
    if name in map_info:
        map_info[name] += points
    else:
        map_info[name] = points

    # Find the maximum Score
    maxFinalScore = float('-inf')

    for name2, score in map_info.items():
        if score > maxFinalScore:
            maxFinalScore = score

    liveScore = {}

    for i in range(num):
        name2 = playerName[i]
        point = playerPoint[i]

        if name2 in liveScore:
            liveScore[name] += points
        else:
            liveScore[name] = points

        finalTotal = map_info[name2]
        currentTotal = liveScore[name2]

        if finalTotal == maxFinalScore and currentTotal >= maxFinalScore:
            print(name2)
            break