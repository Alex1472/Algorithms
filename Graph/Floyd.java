import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class NimGame {
    public static int n;
    public static long[][][] dist;
    public static long inf = (long)1e15;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(new File("input.txt")));
        n = scanner.nextInt();
      
        dist = new long[n + 1][n + 1][n + 1];

        for (int j = 0; j <= n; ++j)
            for (int k = 0; k <= n; ++k)
                dist[0][j][k] = inf;

        for (int i = 1; i <= n; ++i)
            for (int j = 1; j <= n; ++j) {
                int tmp = scanner.nextInt();
                if ((tmp != -1))
                    dist[0][i][j] = tmp;
                else
                    dist[0][i][j] = inf;
            }
        Floyd();
        long max = -inf;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if ((dist[n][i][j] != inf) && (i != j))
                    max = Math.max(max, dist[n][i][j]);
            }
        }
        if (max == -inf)
            System.out.println(0);
        else
            System.out.println(max);
    }

    public static void Floyd() {
        for (int i = 1; i <= n; ++i)
            for (int j = 1; j <=n; ++j)
                for (int k = 1; k <= n; ++k) {
                    dist[i][j][k] = dist[i-1][j][k];
                    if ((dist[i - 1][j][i] != inf) && (dist[i - 1][i][k] != inf))
                        dist[i][j][k] = Math.min(dist[i - 1][j][k], dist[i - 1][j][i] + dist[i - 1][i][k]);
                }
    }
}

