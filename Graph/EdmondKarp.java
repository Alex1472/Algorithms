import java.lang.reflect.Array;
import java.util.*;

public class NimGame {
    public static int[][] c = new int[100][100];
    public static int[][] f = new int[100][100];
    public static int n;
    public static final int inf = 100000000;
    public static final int maxSize = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        int m = scanner.nextInt();
        for (int i = 0; i < m; ++i) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            c[x][y] = scanner.nextInt();
        }
        System.out.println(EdmondKarp(c, f, 1, n));

    }
    //Алгоритм Эдмонда-Карпа за О(E^2*V), где V - кол-во вершин, Е = кол-во ребер
    //т. е. в худшем случае за О(V^5)
    public static int EdmondKarp(int[][] c, int[][] f, int source, int sink) {
        for (;;) {
            //Сначала находим путь в остаточной сети
            //Саму сеть явно не строим, ведь пропускная способность в ней
            // с[i][j] - f[i][j], где с - пропускная способность ребра, f - текущий поток
            Queue<Integer> q = new ArrayDeque<>();
            q.offer(source);
            int[] from = new int[maxSize];
            Arrays.fill(from, -1);
            for (; !q.isEmpty(); ) {
                int x = q.poll();
                for (int i = 1; i <= n; ++i) {
                    if ((from[i] == -1) && (c[x][i] - f[x][i] > 0)) {
                        from[i] = x;
                        q.offer(i);
                    }
                }
            }
            //Если в остаточной сети пути до стока нет, то выходим
            if (from[sink] == -1)
                break;

            //Находим на сколько нужно увеличить поток по данной сети
            //Это минимум из пропускных способностей ребер пути в остаточной сети
            int bottleNeck = inf;
            int current = sink;
            for (; current != source; ) {
                int prev = from[current];
                bottleNeck = Math.min(bottleNeck, c[prev][current] - f[prev][current]);
                current = prev;
            }
            //Увеличиваем поток по данному пути
            //и увеличиваем отрицательный поток по обратным ребрам
            current = sink;
            for (; current != source; ) {
                int prev = from[current];
                f[prev][current] += bottleNeck;
                f[current][prev] -= bottleNeck;
                current = prev;
            }
        }

        //Считаем получившийся поток
        //просто сумма потоков по ребрам из истока
        int flow = 0;
        for (int i = 1; i <= n; ++i) {
            if (c[source][i] > 0)
                flow += f[source][i];
        }
        return flow;
    }
}
