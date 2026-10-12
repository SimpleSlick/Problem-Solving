fun main(){
    val t = readln().toInt()

    repeat(t){
        val (y, x) = readln().trim().split(" ").map { it.toLong() }

        val z = Math.max(y, x)

        if(z % 2L == 0L){
            if(y == z){
                println(z * z - x + 1)
            }else{
                println((z - 1) * (z - 1)+ y)
            }
        }else{
            if(x == z){
                println(z * z - y + 1)
            }else{
                println((z - 1) * (z - 1)+ x)
            }
        }
    }
}