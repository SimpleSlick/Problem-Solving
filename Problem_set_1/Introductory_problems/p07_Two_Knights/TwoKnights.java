import java.util.Scanner;

public class TwoKnights {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
    
        long n = scan.nextLong();

        for(long j = 1; j <= n; j++){
            long totalWays = j * j * (j * j - 1) / 2;
            long attackingWays = 4 * (j - 1) * (j - 2);

            long result = totalWays - attackingWays;
            System.out.println(result);
        }
        scan.close();
    }
}
