import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class winner {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        if(!scan.hasNextInt()) return;
        int num = scan.nextInt();

        HashMap<String, Integer> final_info = new HashMap<>();
        ArrayList<String> playerName = new ArrayList<String>();
        ArrayList<Integer> playerPoint = new ArrayList<Integer>();

        for(int i = 0; i < num; i++){
            String name = scan.next();
            int point = scan.nextInt();
            scan.nextLine();

            playerName.add(name);
            playerPoint.add(point);

            if(final_info.containsKey(name)){
                final_info.put(name, final_info.get(name) + point);
            }else{
                final_info.put(name, point);
            }
        }

        int maxFinalScore = Integer.MIN_VALUE;

        for(int score :  final_info.values()){
            if(score > maxFinalScore){
                maxFinalScore = score;
            }
        }

        HashMap<String, Integer> liveScore = new HashMap<>();

        for(int i = 0; i < num; i++){
            String name = playerName.get(i);
            int point = playerPoint.get(i);

            if(liveScore.containsKey(name)){
                liveScore.put(name, liveScore.get(name) + point);
            }else{
                liveScore.put(name, point);
            }

            int finalTotal = final_info.getOrDefault(name, 0);
            int currentTotal = liveScore.getOrDefault(name, 0);

            if(finalTotal == maxFinalScore && currentTotal >= maxFinalScore){
                System.out.println(name);
                break;
            }
        }
        scan.close();
    }
}
