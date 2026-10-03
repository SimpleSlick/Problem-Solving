import java.util.Arrays;
import java.util.Scanner;

public class First{
    static final int INF = 1_000_000_000;

    // count the factors of 2
    static int countTwos(int num){
        int count = 0;

        while(num != 0 && num % 2 == 0){
            num /= 2;
            count++;
        }

        return count;
    }

    // count the factors of 2
    static int countFives(int num){
        int count = 0;

        while(num != 0 && num % 5 == 0){
            num /= 5;
            count++;
        }

        return count;
    }

    static class Result{
        int cost;
        String path;

        Result(int cost, String path){
            this.cost = cost;
            this.path = path;
        }
    }

    static Result solveDP(int[][] cost, int n){
        int[][] dp = new int[n][n];
        char[][] parent = new char[n][n];

         // Fill dp with INF
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = cost[0][0];

        // Fill the DP table
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                // From top
                if (i > 0 && dp[i - 1][j] != INF) {
                    int newCost = dp[i - 1][j] + cost[i][j];

                    if (newCost < dp[i][j]) {
                        dp[i][j] = newCost;
                        parent[i][j] = 'D';
                    }
                }

                // From left
                if (j > 0 && dp[i][j - 1] != INF) {
                    int newCost = dp[i][j - 1] + cost[i][j];

                    if (newCost < dp[i][j]) {
                        dp[i][j] = newCost;
                        parent[i][j] = 'R';
                    }
                }
            }
        }

        // Reconstruct path
        StringBuilder path = new StringBuilder();

        int row = n - 1;
        int col = n - 1;

        while (row != 0 || col != 0) {
            char move = parent[row][col];
            path.append(move);

            if (move == 'D') {
                // We came from the cell above
                row--;
            } 
            else if (move == 'R') {
                // We came from the cell on the left
                col--;
            }
        }

        // Reverse path
        path.reverse();
        return new Result(dp[n - 1][n - 1], path.toString());
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        int[][] matrix = new int[n][n];
        int[][] twos = new int[n][n];
        int[][] fives = new int[n][n];

        int zeroRow = -1, zeroCol = -1;

        //input matrix to calculate factors
        for(int i = 0; i < n; i++){
             for (int j = 0; j < n; j++) {

                matrix[i][j] = scan.nextInt();

                if (matrix[i][j] == 0) {

                    zeroRow = i;
                    zeroCol = j;

                    // Don't allow normal DP to use zero
                    twos[i][j] = INF;
                    fives[i][j] = INF;

                } 
                else {

                    twos[i][j] = countTwos(matrix[i][j]);
                    fives[i][j] = countFives(matrix[i][j]);
                }
            }
        }

        // Find the best path for factors of 2 and 5
        Result answer2 = solveDP(twos, n);
        Result answer5 = solveDP(fives, n);

        int bestZeros;
        String bestPath;

        // Choose the better of the two DP solutions
        if (answer2.cost < answer5.cost) {
            bestZeros = answer2.cost;
            bestPath = answer2.path;
        } 
        else {
            bestZeros = answer5.cost;
            bestPath = answer5.path;
        }

        // If there is a zero and using it gives only 1 trailing zero
        if (zeroRow != -1 && bestZeros > 1) {
            bestZeros = 1;
            StringBuilder path = new StringBuilder();

            // Move down to zero's row
            for (int i = 0; i < zeroRow; i++) {
                path.append('D');
            }

            // Move right to zero's column
            for (int i = 0; i < zeroCol; i++) {
                path.append('R');
            }

            // Move down from zero's row to bottom
            for (int i = zeroRow; i < n - 1; i++) {
                path.append('D');
            }

            // Move right from zero's column to right
            for (int i = zeroCol; i < n - 1; i++) {
                path.append('R');
            }

            bestPath = path.toString();
        }

        System.out.println(bestZeros);
        System.out.println(bestPath);

        scan.close();
    }
}