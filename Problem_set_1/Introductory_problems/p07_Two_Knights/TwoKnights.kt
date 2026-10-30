fun main(){
    val n = readln().toLong()

    for (i in 1..n){
        val totalWays = i * i * (i * i - 1) / 2
        val attackingWays = 4 * (i - 1) * (i - 2)

        val result = totalWays - attackingWays
        println(result)
    }
}