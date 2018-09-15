import java.lang.reflect.Array;
import java.util.*;

public class NimGame {
    public static int n;
    public static int[][] matrix;
    public static int[] table;

    //проверка на двудольность и разбиение на доли, если возможно
    //table хранит номер доли 0 или 1
    // !!!!! вершины от 1 до n !!!!!

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        int m = scanner.nextInt();
        matrix = new int[n + 2][n + 2];
        table = new int[n + 2];
        Arrays.fill(table, -1);
        for (int i = 0; i < m; ++i) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            matrix[x][y] = 1;
            matrix[y][x] = 1;
        }

        if (!split()) {
            System.out.println("NO");
            return;
        }
        System.out.println("YES");
        for (int i = 1; i <= n; ++i)
            if (table[i] == 0)
                System.out.print(i + " ");

    }
    //пытается разбить граф на 2 доли
    //возвращает true если получилось, иначе false
    public static boolean split() {
        for (int i = 1; i <= n; ++i)
            if (table[i] == -1)
                if (!dfs(i, 0))
                    return false;
        return true;
    }
    
    //вспомогательная функция пытающаяся разбить компоненту связности на доли
    public static boolean dfs(int x, int color) {
        table[x] = color;
        for (int i = 1; i <= n; ++i) {
            if (matrix[x][i] == 1) {
                if (table[i] == table[x])
                    return false;
                if (table[i] == -1)
                    if (!dfs(i, Math.abs(1 - table[x])))
                        return false;
            }
        }
        return true;
    }
}