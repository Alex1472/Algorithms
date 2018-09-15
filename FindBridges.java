import java.io.*;
import java.util.*;

public class NimGame {

    static int n, size = 0;
    static int[][] graph;
    static int[] mark;
    static int[] topSort;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        n = sc.nextInt();
        graph = new int[n + 1][n + 1];
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            graph[x][y] = 1;
            graph[y][x] = 1;
        }
        System.out.println(m - culcBridges());
    }
    //подсчет коичества мостов в графе (не обязательно связнов)
    //разделения на компоненты реберной двусвязности хранится в mark
    public static int culcBridges() {
        mark = new int[n + 1];
        topSort = new int[n + 1];
        for (int i = 1; i <= n; ++i)
            if (mark[i] == 0)
                dfs1(i);

        Arrays.fill(mark, 0);
        int color = 0;
        for (int i = size - 1; i >= 0; --i)
            if (mark[topSort[i]] == 0)
                dfs2(topSort[i], ++color);

        int res = 0;
        for (int i = 1; i <= n; ++i)
            for (int j = i + 1; j <= n; ++j)
                if (((graph[i][j] == 1) || (graph[j][i] == 1)) && (mark[i] != mark[j]))
                    ++res;
        return res;
    }
    //ориентирует ребра, по которым идет, к корню и выполняет топологическую сортировку
    //т. е. просто добавление вершин на выходе
    public static void dfs1(int x) {
        mark[x] = 1;
        for (int i = 1; i <= n; i++)
            if ((graph[x][i] == 1) && (mark[i] == 0)) {
                graph[x][i] = 0;
                dfs1(i);
            }

        topSort[size++] = x;
    }
    //покраска очередной компоненты реберной двусвязности
    public static void dfs2(int x, int color) {
        mark[x] = color;
        for (int i = 1; i <= n; ++i)
            if ((mark[i] == 0) && (graph[x][i] == 1))
                dfs2(i, color);
    }
}


