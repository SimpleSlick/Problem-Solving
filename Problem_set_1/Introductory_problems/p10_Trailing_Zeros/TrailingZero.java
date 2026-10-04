import java.util.Scanner;

public class TrailingZero{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        long n = scan.nextLong();

        long result = 0, temp_num = 0;
        for(long i = 5; i <= n; i *= 5){
            temp_num = n / i;
            result += temp_num;
        }
        System.out.println(result);
        scan.close();
    }
}