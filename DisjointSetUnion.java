import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NimGame {
    public static int n;
    public static  Vertex[] vertexs;

    //Система непересекающихся множеств
    //В неориентированный взвешанный граф добавляют ребра.
    //Напишите программу, которая в некоторые моменты находит сумму весов ребер в компоненте связности
    //Изначально все вершины хранятся как одноэлементные множества в массиве vertors
    //Множества храним как деревья. Для каждой вершины храним родителя, вес всех ребер между вершинами в поддереве а также
    //ранг(для написания можно считать, что это высота дерева. На самом деле нет, т. к. при переподвешивании
    //высоты "портятся"

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(new File("input.txt")));
        PrintWriter pr = new PrintWriter(new FileOutputStream(new File("output.txt")));
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        vertexs = new Vertex[n + 1];
        for (int i = 0; i < n + 1; ++i)
            vertexs[i] = new Vertex();

        for (int i = 0; i < m; ++i) {
            int command = scanner.nextInt();
            if (command == 2) {
                Vertex root = find(vertexs[scanner.nextInt()]);
                pr.println(root.weight);
            }

            if (command == 1)
                union(vertexs[scanner.nextInt()], vertexs[scanner.nextInt()], scanner.nextInt());
        }

        scanner.close();
        pr.close();
    }
    //возвращает вершину дерева, которому принадлежит вершина х

    public static Vertex find(Vertex x) {
        if (x.parent == null)
            return x;
        //переподвешиваем вершину к корню
        x.parent = find(x.parent);
        return x.parent;
    }

    public static void union(Vertex a, Vertex b, int weight) {
        a = find(a);
        b = find(b);
        if (a == b) {
            //Если вершины лежат в одной компоненте связности, то просто увеличиваем вес
            //ребер компоненты на вес добавляемого ребра
            a.weight += weight;
            return;
        }
        //подвешиваем дерево с меньшим рангом к дереву с большим рангом
        if (a.rank > b.rank) {
            b.parent = a;
            //обновление веса нового дерева
            a.weight += b.weight + weight;
        } else {
            a.parent = b;
            b.weight += a.weight + weight;
            //если веса одинаковые, то ранг дерева восрастает на 1
            if (a.rank == b.rank)
                b.rank += 1;
        }
    }



}

class Vertex {
    public Vertex parent;
    public int rank;
    public int weight;

    public Vertex() {
        this.parent = null;
        this.rank = 0;
        this.weight = 0;
    }
}








