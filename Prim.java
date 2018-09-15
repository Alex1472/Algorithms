
import java.beans.VetoableChangeListener;
import java.io.*;
import java.util.*;

public class NimGame {
    public static int n;
    public static long inf = (long)1e15;
    public static List<List<Edge>> edges;
    public static boolean mark[]; //пометки, определяющие добавлена ли вершина в остовное дерево
    public static long[] prior; //приоритет вершины. Определяет какую след. вершину добавлять в остовное дерево
    public static int[] from; //хранит вершину, из которой пришли в данную. В итоге нам хранится или -1 или
    // второй конец ребра в остовном дереве

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        n = sc.nextInt();
        int m = sc.nextInt();
        edges = new ArrayList<>();
        for (int i = 0; i <= n; ++i)
            edges.add(new ArrayList<Edge>());
        mark = new boolean[n + 1];
        prior = new long[n + 1];
        for (int i = 0; i < prior.length; ++i)
            prior[i] = inf;
        from = new int[n + 1];
        Arrays.fill(from, -1);
        for (int i = 0; i < m; ++i) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int w = sc.nextInt();
            edges.get(x).add(new Edge(y, w));
            edges.get(y).add(new Edge(x, w));
        }
        Prim();
        int minWeight = 0;
        for (int i = 1; i <= n; ++i)
            if (from[i] != -1)
                minWeight += prior[i];
        System.out.println(minWeight);
        sc.close();
    }
     //вершины 1..n
     //O(|E|log|V|)
    public static void Prim() {
        //1 - начальная вершина
        //для обновления приоритетов вершин в очереди делаем трюк:
        //просто добавляем вершину с обновленным значением, а эру же вершину со старым
        //значением не удаляем. Когда достаем очередную вершину сверяем совпадает ли её приоритет в очереди
        //с её приоритетов в prior. Если нет, то пропускаем, т. к. это устаревшее значение. Иначе рассматриваем.
        prior[1] = 0;
        PriorityQueue<Vertex> q = new PriorityQueue<>();
        q.offer(new Vertex(0, 1));
        prior[1] = 0;
        while (!q.isEmpty()) {
            Vertex x = q.poll();
            if (x.key != prior[x.value])
                continue;
            mark[x.value] = true;
            for (Edge i : edges.get(x.value))
                if (( i.weight < prior[i.dest]) && (!mark[i.dest])) {
                    prior[i.dest] =  i.weight;
                    q.offer(new Vertex(prior[i.dest], i.dest));
                    from[i.dest] = x.value;
                }
        }
    }


}

class Edge {
    public int dest;
    public long weight;

    public Edge(int dest, long weight) {
        this.dest = dest;
        this.weight = weight;
    }
}

class  Vertex implements Comparable {
    public long key;
    public int value;

    public Vertex(long key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Object o) {
         Vertex that = (Vertex)o;
         if (that.key == this.key)
             return 0;
         if (that.key > this.key)
             return -1;
         return 1;
    }
}





