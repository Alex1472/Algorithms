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

    //Нахождение макс. потока за О(|V|^3) (проталкивание предпотока)
    //    !!!!! вершинны от 1 до n !!!!!
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
        System.out.println(calculateFlow(1, n));

    }

    public static long calculateFlow(int sourse, int sink) {
        //Проталикаем иаксимальный поток из истока
        for (int i = 1; i <= n; ++i)
            if (c[sourse][i] > 0) {
                f[sourse][i] = c[sourse][i];
                f[i][sourse] = -c[sourse][i];
                e[i] = c[sourse][i];
            }
        h[sourse] = n;
        //maxH - хранит переполненные вершины с максимальной высотой
        int[] maxh = new int[2*n];
        int size = 0;
        for (;;) {
            //находим переполненные вершины с максимальной высотой
            for (int i = 1; i <= n; ++i) {
                if ((e[i] > 0) && (i != sourse) && (i != sink)) {
                    if ((size != 0) && (h[maxh[0]] < h[i]))
                        size = 0;
                    if ((size == 0) || (h[maxh[0]] == h[i]))
                        maxh[size++] = i;
                }
            }
            //если переполненных вершин нет, то выходим
            if (size == 0)
                break;

            //обрабатываем найдеммые на пред. шаге вершины
            while (size != 0) {
                //рассматриваем очередную вершину
                int i = maxh[size - 1];
                boolean pushable = false;

                //пытаемся протолкнуть избыток в вершине i
                for (int j = 1; (j <= n) && (e[i] > 0); ++j) {
                    if ((c[i][j] - f[i][j] > 0) && (h[i] == h[j] + 1)) {
                        push(i, j);
                        pushable = true;
                    }
                }
                //Если при проталкивании вершина перестала быть переполненной, то удаляем её
                if (e[i] == 0)
                    --size;
                //Если ничего из вершины i, проталкнуть не удалось, то поднимаем вершину
                if (!pushable) {
                    lift(i);
                    //Если при поднятии вершина стала выше остальных, то выходим и пересчитываем
                    //переполненные вершины максимальнгой высоты
                    if (h[i] > h[maxh[0]]) {
                        size = 0;
                        break;
                    }
                }
            }
        }
        //Считаем получившийся поток
        long flow = 0;
        for (int i = 1; i <= n; ++i)
            if (c[sourse][i] > 0)
                flow += f[sourse][i];
        return flow;
    }
    //проталкивание по ребру (u, v)
    public static void push(int u, int v) {
        long delta = Math.min(e[u], c[u][v] - f[u][v]);
        f[u][v] += delta;
        f[v][u] -= delta;
        e[u] -= delta;
        e[v] += delta;
    }
    //поднятие вершины u
    public static void lift(int u) {
        long delta = inf;
        for (int i = 1; i <= n; ++i)
            if (c[u][i] - f[u][i] > 0)
                delta = Math.min(delta, h[i]);

        if (delta != inf)
            h[u] = delta + 1;
    }
}
