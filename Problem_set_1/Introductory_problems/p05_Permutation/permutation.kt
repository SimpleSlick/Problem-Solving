fun main(){
    var number = readln().toLong();

    if(number > 3){
        var i = 2
        while(i <= number){
            print("$i ")
            i += 2
        }

        i = 1
        while(i <= number){
            print("$i ")
            i += 2
        }
    }else if(number == 1L){
        print(number)
    }else{
        print("NO SOLUTION")
    }
}