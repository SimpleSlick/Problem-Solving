package p03_Repetition;
import java.util.Scanner;

public class Repetitions{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String input = scan.nextLine();

        int max = 1, current = 1;

        for(int i = 1; i < input.length(); i++){
            if(input.charAt(i) == input.charAt(i - 1)){
                current++;
            } else{
                max = Math.max(max, current);
                current = 1;
            }
        }

        if(current > max){
            max = current;
        }
        System.out.println(max);
        scan.close();
    }
}