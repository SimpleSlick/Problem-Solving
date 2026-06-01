import java.util.Scanner;

public class Permutations {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        long number = scan.nextLong();
        StringBuilder even_num = new StringBuilder();
        StringBuilder odd_num = new StringBuilder();

        if (number > 3) {
            for (int i = 1; i <= number; i++) {
                if (i % 2 == 0) {
                    even_num.append(i).append(" ");
                } else {
                    odd_num.append(i).append(" ");
                }
            }
            System.out.print(even_num.toString() + odd_num.toString());
        } else if(number == 1){
            System.out.println(number);
        } else {
            System.out.println("NO SOLUTION");
        }
        scan.close();
    }
}