import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NimGame {
    public static int n;
    public static long[] elements;
    //Дерево Фенвика
    //в массиве elements хранятся суммы элементов
    //для i-ого индекса это элементы с элементы индекс которого получается
    //отбрасыванием единиц в конце двоичной записи i до i-ого
    //в данном примере изначально все элементы нули
    //элементы нумеруются с нулевого

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(new File("input.txt")));
        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("output.txt")));
        n = scanner.nextInt();
        elements = new long[n];
        while (true) {
            int command = scanner.nextInt();
            if (command == 0)
                break;
            if (command == 1) {
                int i = scanner.nextInt();
                int d = scanner.nextInt();
                update(i - 1, d);
            }
            if (command == 2) {
                int l = scanner.nextInt();
                int r = scanner.nextInt();
                pw.println(sumOnSegment(l - 1, r - 1));
            }
        }
        pw.close();
        scanner.close();
    }

    //обновление массива сумм elements
    //если элемент с индексом x изменился на d
    //тогда нудно обновить суммы индекс которух получается заменой
    //в двоичной записи индекса x нескольких последних нулей единицами
    public static void update(int x, long d) {
        for (int i = x; i < n; i = i | (i + 1) )
            elements[i] += d;
    }

    //считает сумму элементов на отрезке [l, r]
    //это разность суммы на [0, r] и суммы [0, l - 1]
    public static long sumOnSegment(int l, int r) {
        return sum(r) - sum(l - 1);
    }

    //считает сумму элементов на отрезке [0, x]
    //для этого берем сумму в elements[x] - сумма от элемента с индексом получающимся
    //заменой в двоичной записи последних единиц на нули до индекс x
    //и переходим в элементу к поиску суммы от нуля до индекса начального элемента суммы
    //elements[x] минус 1
    static long sum(int x) {
        if (x == -1)
                return 0;
        long result = 0;
        for (int i = x; i >= 0; i = (i & (i + 1)) - 1)
            result += elements[i];
        return result;
    }


}








