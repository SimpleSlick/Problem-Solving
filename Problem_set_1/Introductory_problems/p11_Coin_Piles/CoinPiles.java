// package Problem_set_1.Introductory_problems.p11_Coin_Piles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class CoinPiles {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < t; i++) {
            String line = br.readLine();
            if (line != null) {
                StringTokenizer st = new StringTokenizer(line);
                long a = Long.parseLong(st.nextToken());
                long b = Long.parseLong(st.nextToken());

                if ((a + b) % 3 == 0 && a <= 2 * b && b <= 2 * a) {
                    result.append("YES\n");
                } else {
                    result.append("NO\n");
                }
            }
        }
        System.out.println(result);
    }
}