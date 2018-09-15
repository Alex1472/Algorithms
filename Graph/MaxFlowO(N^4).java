import java.lang.reflect.Array;
import java.util.*;

public class NimGame {
    public static final int maxSize = 600;
    public static long[][] c = new long[maxSize][maxSize];
    public static long[][] f = new long[maxSize][maxSize];
    public static long[] e = new long[maxSize];
    public static long[] h = new long[maxSize];
    public static final long inf = (long)1e15;
    public static int n;

    //Нахождение макс. потока за О(|V|^4), точнее за O(|E| * |V|^2)  (проталкивание предпотока)
    //    !!!!! вершинны от 1 до n !!!!!
    //    !!!!! Возможно работает за O( |V|^3 * |E|)   !!!!!
    // исток - 1, сток n

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        int m = scanner.nextInt();
        for (int i = 0; i < m; ++i) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            c[x][y] = scanner.nextInt();
        }
        System.out.println(CalculateFlow(1, n));

    }

    public static long CalculateFlow(int sourse, int sink) {
        for (int i = 1; i <= n; ++i)
            if (c[sourse][i] > 0) {
                f[sourse][i] = c[sourse][i];
                f[i][sourse] = -c[sourse][i];
                e[i] = c[sourse][i];
            }
        h[sourse] = n;
        for(;;) {
            //Ищем вершину с избытком
            int i;
            for (i = 1; i <=n; ++i)
                if ((i != sourse) && (i != sink) && (e[i] > 0))
                    break;
            //Если не находим, то выходим
            if ( i == n + 1)
                break;

            //Ищем для вершины j, в которую из i пожно протолкнуть поток
            int j ;
            for (j = 1; j <= n; ++j)
                if ((c[i][j] - f[i][j] > 0) && (h[i] == h[j] + 1))
                    break;
            //Если нашли куда можно протолкнуть поток, то проталкиваем.
            //Иначе поднимаем i
            if (j != n + 1)
                push(i, j);
            else
                lift(i);
        }

        //Считаем получившийся поток
        long flow = 0;
        for (int i = 1; i <= n; ++i)
            if (c[sourse][i] > 0)
                flow += f[sourse][i];
        return flow;
    }
    //Проталкивание потока
    public static void push(int u, int v) {
        long delta = Math.min(e[u], c[u][v] - f[u][v]);
        f[u][v] += delta;
        f[v][u] -= delta;
        e[u] -= delta;
        e[v] += delta;
    }

    //Поднятие вершины
    public static void lift(int u) {
        long delta = inf;
        for (int i = 1; i <= n; ++i)
            if (c[u][i] - f[u][i] > 0)
                delta = Math.min(delta, h[i]);
        if (delta != inf)
            h[u] = delta + 1;
    }
}
