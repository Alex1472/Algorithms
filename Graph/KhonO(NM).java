import java.lang.reflect.Array;
import java.util.*;

public class NimGame {
    public static int n;
    public static int s;
    public static ArrayList<ArrayList<Integer>> matrix =
            new ArrayList<ArrayList<Integer>>();
    public static boolean[] mark;
    public static int[] match;

    //Максимальное паросочетание за O(N*M) - алгоритм Куна(венгерский алгоритм)
    //matrix для каждой вершины из первой доли хранит её соседей из второй
    //match хранит текущее паросочетание, т. е. для вершины из второй доли её
    //соседа из первой, иначе нуль
    //mark  массив пометок для dfs
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        s = scanner.nextInt();
        mark = new boolean[s + 1];
        match = new int[n + 1];
        Arrays.fill(match, -1);

        for (int i = 0; i <= s; ++i)
            matrix.add(new ArrayList<Integer>());

        for (int i = 1; i <= s; ++i)
            for (int j = 1; j <= n; ++j) {
                int tmp = scanner.nextInt();
                if (tmp == 1)
                    matrix.get(i).add(j);
            }
        findMatch();
        int res = 0;
        for (int i = 1; i <= n; ++i)
            if (match[i] != -1)
                ++res;
        System.out.println(res);

    }
    //поочередно пытаемся добавить каждую из вершин из первой доли
    public static void findMatch() {
        for (int i = 1; i <= s; ++i) {
            Arrays.fill(mark, false);
            khon(i);
        }
    }
    //увеличивающая цепь - простой путь ребра которого поочередно то принадлежат,
    //то не принадлежат текущему паросочетанию
    //паросочетание максимально <=> нет увеличивающих цепей
    //khon пытается построить увеличивающую цепь с началом в u,
    //при этом обновляя паросочетание
    //возвращает true, если удалось, иначе false

    public static boolean khon(int u) {
        //если уже были в вершине, то цепь построить нельзя
        if (mark[u])
            return false;
        mark[u] = true;
        //перебираем всех соседей из второй доли
        //если соседа нет в паросочетании или
        //удалось построить увеличивающую цепь для его соседа, то
        //добавляем ребро (u, i)  в паросочетание
        for (int i : matrix.get(u)) {
            if ((match[i] == -1) || (khon(match[i]))) {
                match[i] = u;
                return true;
            }
        }
        return false;
    }
}