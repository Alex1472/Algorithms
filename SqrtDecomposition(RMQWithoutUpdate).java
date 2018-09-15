import java.io.*;
import java.util.*;

public class NimGame {
    public static int n, step;
    public static Pair[] numbers;
    public static Pair[] max;


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        n = sc.nextInt();
        numbers = new Pair[n + 1];
        for (int i = 0; i < n; i++)
            numbers[i] = new Pair(sc.nextLong(), i);
        prepare(numbers);

        int m = sc.nextInt();
        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("output.txt")));
        for (int i = 0; i < m; ++i) {
            Pair res = findMax(sc.nextInt(), sc.nextInt());
            pw.println(res.max + " " + (res.index));
        }
        pw.close();



        sc.close();
    }
    //подготовка подсчет максимумов на отрезках длиной sqrt(numbers.length)
    public static void prepare(Pair[] numbers) {
        step = (int)Math.floor(Math.sqrt(numbers.length));
        max = new Pair[numbers.length / step + 1];
        for (int i = 0; i < numbers.length; i++)
            max[i / step] = Pair.max(numbers[i], max[i / step]);
    }

    //нахождение максимума на отрезке от l до r
    //!!!запросы принимаются из условия, что элементы нумеруюся с 1!!!
    public static Pair findMax(int l, int r) {
        --l; --r;
        if (l / step == r / step) {
            Pair res = numbers[l];
            for (int i = l + 1; i <= r; i++)
                res = Pair.max(res, numbers[i]);
            return new Pair(res.max, res.index + 1);
        }
        Pair res = null;
        for (int i = l; i % step != 0; ++i)
            res = Pair.max(res, numbers[i]);
        for (int i = r; i % step != (step - 1); --i)
            res = Pair.max(res, numbers[i]);
        for (int i = (l % step == 0) ? (l / step) : (l / step + 1) ; i < ((r % step == step - 1) ?
                (r / step + 1) : (r / step)); ++i)
            res = Pair.max(res, max[i]);
        return new Pair(res.max, res.index + 1);
    }


}

class Pair {
    public long max;
    public long index;

    public Pair(long max, long index) {
        this.max = max;
        this.index = index;
    }

    public static Pair max(Pair a, Pair b) {
        if (a == null) return b;
        if (b == null) return a;
        return (a.max > b.max) ? a : b;
    }
}


