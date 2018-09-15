import java.io.*;
import java.util.*;

public class NimGame {
    public static Pair[][] table; //сама таблица
    public static int[] numbers;
    public static int n;
    public static int[] powers = new int[30]; //массив степеней двоек(без переполнения)

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        n = sc.nextInt();
        numbers = new int[n + 1];
        for (int i = 1; i <= n; i++)
            numbers[i] = sc.nextInt();
        createTable(numbers);
        int m = sc.nextInt();

        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("output.txt")));
        for (int i = 0; i < m; i++) {
            Pair res = findMax(sc.nextInt(), sc.nextInt());
            pw.println(res.max + " " + res.index);
        }
        sc.close();
        pw.close();
    }
    //максимум на отрезке [l; r] !!!предполагается, что числа нумеруются с единицы!!!
    public static Pair findMax(int l, int r) {
        int pw = (int)Math.floor(Math.log(r - l + 1) / Math.log(2));
        return (table[pw][l].max > table[pw][r - powers[pw] + 1].max) ?
                table[pw][l] : table[pw][r - powers[pw] + 1];
    }
    //создание таблицы и заполнение powers
    public static void createTable(int[] numbers) {
        table = new Pair[(int)Math.ceil(Math.log(numbers.length) / Math.log(2)) + 1][numbers.length];
        for (int i = 0; i < numbers.length; i++)
            table[0][i] = new Pair(numbers[i], i);

        for (int l = 2, pw = 1; l<= numbers.length; l *= 2, ++pw)
            for (int i = 0; i < numbers.length; ++i) {
                if (i + l > numbers.length)
                    break;
                table[pw][i] = (table[pw - 1][i].max > table[pw - 1][i + l / 2].max) ?
                        table[pw - 1][i] : table[pw - 1][i + l / 2];
            }
        powers[0] = 1;
        for (int i = 1; i < powers.length; i++)
            powers[i] = 2 * powers[i - 1];
    }
}

class Pair {
    public int max;
    public int index;

    public Pair(int max, int index) {
        this.max = max;
        this.index = index;
    }
}


