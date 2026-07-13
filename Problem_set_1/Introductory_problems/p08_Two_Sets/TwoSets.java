import java.util.Scanner;

public class TwoSets {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        long num = scan.nextLong();
        StringBuilder set1 = new StringBuilder();
        StringBuilder set2 = new StringBuilder();

        long sum = num * (num + 1) / 2;

        if (sum % 2 == 0) {
            System.out.println("YES");

            if (num % 4 == 3) {
                set1.append(1).append(" ").append(2).append(" ");
                int count1 = 2;
                set2.append(3).append(" ");
                int count2 = 1;
                for (int i = 4; i <= num; i += 4) {
                    set1.append(i).append(" ").append(i + 3).append(" ");
                    count1 += 2; 
                    set2.append(i + 1).append(" ").append(i + 2).append(" ");
                    count2 += 2;
                }
                System.out.println(count2);
                System.out.println(set2);
                System.out.println(count1);
                System.out.println(set1);
            } else {
                int count = 0;
                for (int i = 1; i <= num; i += 4) {
                    set1.append(i).append(" ").append(i + 3).append(" ");
                    set2.append(i + 1).append(" ").append(i + 2).append(" ");
                    count += 4;
                }
                System.out.println(count / 2);
                System.out.println(set1);
                System.out.println(count / 2);
                System.out.println(set2);
            }

        } else {
            System.out.println("NO");
        }
        scan.close();
    }
}