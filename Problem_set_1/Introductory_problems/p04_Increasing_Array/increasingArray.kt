fun main(){
    val sizeArray: Int = readln().toInt()
    var numArray: IntArray = IntArray(sizeArray)

    for(i in 0 until numArray.size){
        numArray[i] = readln().toInt()
    }

    var totalMoves: Long = 0;
    for(i in 1 until numArray.size){
        val current = numArray[i]
        val previous = numArray[i - 1]

        if(current < previous){
            val gap = previous - current
            totalMoves += gap
            numArray[i] = previous
        }
    }

    println(totalMoves)
}