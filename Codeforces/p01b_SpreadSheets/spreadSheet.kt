package problemSolving.CodeForce

// rxcy to alpha-numeric
fun rowCordinate(input: String){
    val cIndex = input.indexOf('C')

    val row: String = input.substring(1, cIndex)
    val col: String = input.substring(cIndex + 1)

    var num: Long = col.toLong()
    var colString = ""

    while(num > 0){
        num--
        val remainder: Int = num.toInt() % 26
        val ch: Char = ('A' + remainder)
        colString = ch + colString
        num /= 26
    }

    println(colString + row)
}

// alphanumeric to rxcy
fun rowColSystem(input: String){
    var firstDigitIndex = 0
    while(firstDigitIndex < input.length && !input[firstDigitIndex].isDigit()){
        firstDigitIndex++
    }

    val row: String = input.substring(firstDigitIndex)
    val col: String = input.substring(0, firstDigitIndex)

    var total = 0

    for(i in 0..<col.length){
        val letter: Char = col[i]
        val value: Int = letter - 'A' + 1

        total *= 26
        total += value
    }

    println("R" + row + "C" + col)
}

fun main(){
    val n = readln().toLong()

    for(i in 0..<n){
        val str: String = readln()

        if(str.matches(Regex("^R\\d+C\\d+$"))){
            rowCordinate(str)
        }else{
            rowColSystem(str)
        }
    }
}