import java.util.Scanner;

public class IncreasingArray {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int size_array = scan.nextInt();

        int[] num_arr = new int[(int)size_array];
        
        for(int i = 0; i < num_arr.length; i++){
            num_arr[i] = scan.nextInt();
        }
        
        long totalMoves = 0;
        for(int i = 1; i < num_arr.length; i++){
            int current = num_arr[i];
            int previous = num_arr[i - 1];
            
            if(current < previous){
                long gap = previous - current;
                totalMoves += gap;
                num_arr[i] = previous;
            }
        }
        System.out.println(totalMoves);
        scan.close();
    }
}