import java.util.Scanner;

public class palindromeReorder {
    public static String palindrome(String s){
        int n = s.length();
        char[] ans = new char[(int)n];

        int[] freq = new int[26];
        for(int i = 0; i < n; i++){
            freq[s.charAt(i) - 'A'] += 1;
        }

        int count = 0;
        for(int i = 0; i < freq.length; i++){
            if(freq[i] % 2 != 0){
                count += 1;
            }
        }

        if (count > 1){
            return "NO SOLUTION";
        }

        int left = 0, right = n - 1;
        for(int i = 0; i < 26; i++){
            while(freq[i] > 1){
                ans[left++] = ans[right--] = (char)('A' + i);
                freq[i] -= 2;
            }
        }

        for(int i = 0; i < 26; i++){
            if(freq[i] == 1){
                ans[n / 2] = (char)('A' + i);
                break;
            }
        }

        return new String(ans);
    }
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();

        System.out.println(palindrome(line));
        scan.close();
    }
}