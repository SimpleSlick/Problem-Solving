import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int n = scanner.nextInt();

        Set<Integer> s = new HashSet<>();

        for(int i = 0; i < n; i++){
            int x = scanner.nextInt();
            s.add(x);
        }

        System.out.println(s.size());

        scanner.close();
    }
}