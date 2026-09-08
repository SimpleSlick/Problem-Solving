fun main(){
    val n: Long = readln().toLong()
    val m: Long = readln().toLong()
    val a: Long = readln().toLong()

    var side1: Long = (n + a - 1) / a
    var side2: Long = (m + a - 1) / a

    val result = side1 * side2
    println(result)
}