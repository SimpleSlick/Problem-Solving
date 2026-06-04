import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class NumberSpiral {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = br.readLine();
        if (firstLine == null) return;
        
        int t = Integer.parseInt(firstLine.trim());
        
        StringBuilder out = new StringBuilder();

        while (t-- > 0) {
            String line = br.readLine();
            if (line == null) break;
            
            StringTokenizer st = new StringTokenizer(line);
            long y = Long.parseLong(st.nextToken());
            long x = Long.parseLong(st.nextToken());
            
            long z = Math.max(x, y);
            long result;

            if (z % 2 == 0) {
                if (y == z) {
                    result = z * z - x + 1;
                } else {
                    result = (z - 1) * (z - 1) + y;
                }
            } else {
                if (x == z) {
                    result = z * z - y + 1;
                } else {
                    result = (z - 1) * (z - 1) + x;
                }
            }
            out.append(result).append("\n");
        }
        System.out.print(out);
    }
}