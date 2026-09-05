package p09_Bit_Strings;

import java.util.Scanner;
// import java.math.BigInteger;

public class BitStrings {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        long length = scan.nextLong();
        long result = 1;
        long mod = 1000000007;

        for(int i = 0; i < length; i++){
            result = (result * 2) % mod;
        }

        System.out.println(result);
        scan.close();
    }
}
