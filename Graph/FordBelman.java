import com.sun.org.apache.xpath.internal.SourceTree;

import java.lang.reflect.Array;
import java.security.KeyPair;
import java.util.*;

//Нахождение кратчайшего растоя ния от заданной вершины до всех остальных - Форд-Белман за О(|E|*|V|)

public class NimGame {
    public static int n;
    public static ArrayList<Edge> edges =
            new ArrayList<Edge>();
    public static long[] dist;
    public static long inf = (long)1e15;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        dist = new long[n + 1];

        for (int i = 1; i <= n; ++i)
            for (int j = i + 1; j <= n; ++j)
                edges.add(new Edge(i, j,(179*i+719*j) % 1000 - 500));

        FordBelman(1);

        System.out.println(dist[n]);
    }

    public static void FordBelman(int u) {
        Arrays.fill(dist, inf);
        dist[u] = 0;
        //updated показывает было ли на очередной итерации обновлено хотя бы одно растояние
        boolean updated;
        for (;;) {
            updated = false;
            for (Edge edge : edges) {
                if (dist[edge.u] < inf) {
                    if (dist[edge.v] > Math.min(dist[edge.u] + edge.w, dist[edge.v])) {
                        dist[edge.v] = Math.min(dist[edge.u] + edge.w, dist[edge.v]);
                        updated = true;
                    }
                }
            }
            //Если обновлений не было, то выходим
            if (!updated)
                return;
        }
    }
}

class Edge {
    public int w;
    public int u;
    public int v;

    public Edge(int u, int v, int w) {
        this.w = w;
        this.u = u;
        this.v = v;
    }
}
