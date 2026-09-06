fun main(){
    var n: Long = readln().toLong()

    var number: LongArray = LongArray((n - 1).toInt())
    var actualSum: Long = 0

    for(i in 0..<n - 1){
        number[i.toInt()] = readln().toLong()
        actualSum += number[i.toInt()]
    }

    var expectedSum: Long = n * (n + 1) / 2
    print(expectedSum - actualSum)
}