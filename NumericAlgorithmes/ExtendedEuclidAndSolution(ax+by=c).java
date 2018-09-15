import java.math.BigInteger;
import java.util.Scanner;

public class helloworld {
    public static void main(String[] args) {
        //находим какое либо решение диофантового уравнения ax + by = c (a, b > 0)
        //найдем d = НОД(а, b). Если с не делится на d, то решений нет(ведь ax + by делится на d)
        //найдем расширенным алгоритмом Евклида решение ax + by = d, это (x', y')
        //тогда x = x'c/d, y = y'c/d подходят  
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int[] result = ExtendedEuclid(Math.max(a, b), Math.min(a, b));
        if (c % result[0] != 0) {
            System.out.println("Impossible");
            return;
        }
        if (a>= b)
            System.out.println(result[0] + " " + result[1] * c / result[0] + " " + result[2] * c / result[0]);
        if (a < b)
            System.out.println(result[0] + " " + result[2] * c / result[0] + " " + result[1] * c / result[0]);
    }

    //Вычисляем НОД х и у т. ч. x >= y. Заодно находим числа а и b т. ч. ax + by = NOD(x, y)
      
    public static int[] ExtendedEuclid(int x, int y) {
        if (y == 0)
            return new int[] { x, 1, 0 };
        int[] buffer = ExtendedEuclid(y, x % y);
        return new int[] { buffer[0], buffer[2], buffer[1] - buffer[2]*(x / y)} ;
    }
}
