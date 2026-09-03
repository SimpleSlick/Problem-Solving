fun main(){
    var n = readln().toLong()

    while(n != 1L){
        if(n % 2L == 0L){
            n = n / 2L
        }else{
            n = n * 3L + 1L
        }
        print("$n -> ")
    }
}