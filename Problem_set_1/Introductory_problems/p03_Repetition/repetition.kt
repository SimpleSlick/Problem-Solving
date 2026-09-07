fun main(){
    var inputLine: String = readln()

    var maxStreak: Int = 1
    var currentStreak: Int = 1

    for(i in 1..<inputLine.length){
        if(inputLine[i] == inputLine[i - 1]){
            currentStreak++
        }else{
            maxStreak = Math.max(maxStreak, currentStreak)
            currentStreak = 1
        }
    }

    if(currentStreak > maxStreak){
        maxStreak = currentStreak
    }

    print(maxStreak)
}