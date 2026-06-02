package Codeforces.p01b_SpreadSheets;

import java.util.Scanner;

public class spreadSheet {
    // rxcy to alpha-numeric
    public static void rowCoordinate(String input){
        int cIndex = input.indexOf('C');

        String row = input.substring(1, cIndex);
        String col = input.substring(cIndex + 1);

        long num = Long.parseLong(col);
        String colString = "";
        while(num > 0){
            num--;
            long remainder = num % 26;
            char ch = (char)('A' + remainder);
            colString = ch + colString;
            num = num / 26;
        }

        System.out.println(colString + row);
    }
    // alpha-numeric to rxcy
    public static void rowColSystem(String input){
        int firstDigitIndex = 0;
        while(firstDigitIndex < input.length() && !Character.isDigit(input.charAt(firstDigitIndex))){
            firstDigitIndex++;
        }

        String row = input.substring(firstDigitIndex);
        String col = input.substring(0, firstDigitIndex);

        int total = 0;
        for(int i = 0; i < col.length(); i++){
            char letter = col.charAt(i);
            int value = letter - 'A' + 1;

            total *= 26;
            total += value;
        }
        System.out.println("R" + row + "C" + total);
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        long n = scan.nextLong();
        scan.nextLine();

        for(int i = 0; i < n; i++){
            String str = scan.nextLine();

            if(str.matches("^R\\d+C\\d+$")){
                rowCoordinate(str);
            } else{
                rowColSystem(str);
            }
        }
        scan.close();
    }
}