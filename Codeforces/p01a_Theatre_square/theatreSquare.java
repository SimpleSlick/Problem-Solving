import java.util.Scanner;

public class theatreSquare {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long n = scan.nextLong();
        long m = scan.nextLong();
        long a = scan.nextLong();

        long side1 = (n + a - 1) / a;
        long side2 = (m + a - 1) / a;

        long result = side1 * side2;
        System.out.println(result);

        scan.close();
    }
}