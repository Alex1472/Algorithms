
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class NimGame {
    public static int n;
    public static Vertex[] vertexs; //вершины в системе непересекающихся множеств
    public static Edge[] edges; //ребра графа

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        n = sc.nextInt();
        int m = sc.nextInt();
        vertexs = new Vertex[n + 1];
        for (int i = 0; i <= n; ++i)
            vertexs[i] = new Vertex();
        edges = new Edge[m];
        for (int i = 0; i < m; ++i)
            edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
        Edge[] ostov = Kraskal(edges);
        int sumWeight = 0;
        for (int i = 0; i < ostov.length; ++i )
            sumWeight += ostov[i].weight;
        System.out.println(sumWeight);
        sc.close();

    }
    
    //Алгоритм Краскала
    public static Edge[] Kraskal(Edge[] edges) {
        Arrays.sort(edges); //сортируем ребра по весу
        Edge[] result = new Edge[n - 1];
        int size = 0;
        for (int i = 0; i < edges.length; ++i) {
            //Если ребро соединяет 2 разные компоненты связности
            //на данный момент, то добавлем ребро в остовное дерево
            //и соединяем компоненты
            Vertex a = find(vertexs[edges[i].u]);
            Vertex b = find(vertexs[edges[i].v]);
            if (a != b) {
                result[size++] = edges[i];
                union(vertexs[edges[i].u], vertexs[edges[i].v]);
            }
        }
        return result;
    }

    public static void union(Vertex a, Vertex b) {
        a = find(a);
        b = find(b);
        if (a == b)
            return;
        if (a.rank > b.rank)
            b.parent = a;
        else {
            a.parent = b;
            if (a.parent == b.parent)
                ++a.rank;
        }
    }

    public static Vertex find(Vertex x) {
        if (x.parent == null)
            return x;
        x.parent = find(x.parent);
        return x.parent;
    }
}

class Vertex {
    public int rank;
    public Vertex parent;

    public Vertex() {
        this.rank = 0;
    }
}

class Edge implements Comparable  {
    public int weight;
    public int u;
    public int v;

    public Edge(int u, int v, int weight) {
        this.weight = weight;
        this.u = u;
        this.v = v;
    }

    @Override
    public int compareTo(Object o) {
        Edge that = (Edge)o;
        if (this.weight == that.weight)
            return 0;
        if (this.weight < that.weight)
            return -1;
        return 1;
    }
}









