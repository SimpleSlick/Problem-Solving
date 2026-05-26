package p02_Missing_Numbers;

import java.util.Scanner;

public class missingNumbers{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

            long n = scan.nextLong();
    
            long[] numbers = new long[(int)n - 1];
            long actualSum = 0;
    
            for(int i = 0; i < n - 1; i++){
                numbers[i] = scan.nextLong();
                actualSum += numbers[i];
            }
            long expected_sum = n * (n + 1) / 2;
    
            System.out.println(expected_sum - actualSum);

            scan.close();
        }
    }