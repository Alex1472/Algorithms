import java.util.Scanner;

//Двое играют в такую игру: перед ними лежит шоколадка размера NxM. 
//За ход можно разломить имеющийся кусок шоколадки вдоль одной из сторон на 2 "непустых".

//Однако, нельзя разламывать куски размером не больше, чем 1*k (куски можно поворачивать; мы считаем, 
//что один кусок "не больше" другого, если он равен ему или его части). 
//Таким образом, нельзя разламывать куски размером 1*1, 1*2, , 1*k, а остальные куски разламывать можно.

//Проигрывает тот, кто не может сделать ход. Определите, кто же станет победителем в игре, если известны начальные размеры шоколадки.


public class NimGame {
    public static int[][] results = new int[110][110];
    //считаем результаты игр, чтобы сократить рекурсию 

    public static void main(String[] args) {
        for (int i = 0; i < 110; ++i)
            for (int j = 0; j < 110; ++j)
                results[i][j] = -1;

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();

        int result = SpragGrandi(Math.max(n , m), Math.min(n, m), k);
        if (result == 0)
            System.out.println("2");
        else
            System.out.println("1");
    }

    public static int SpragGrandi(int n, int m, int k) {
        if ((m == 1) && (n <= k))
            return 0;
        if (results[n][m] != -1)
            return results[n][m];
        int size = (n + 1)*(m + 1);
        boolean[] values = new boolean[size];
   
        //считам функция Шпрага-Гранди для всех восможных переходов
        //если при переходе игра разбивается на 2 нехависимые игры,
        //то значение ф-ий Ф-Г - xor значений ф-ий Ф-Г для этих игр
 
        for (int i = 1; i < n; ++i) {
            int value = SpragGrandi(Math.max(i, m), Math.min(i, m), k) ^
                    SpragGrandi(Math.max(n - i, m), Math.min(n - i, m), k);
            if (value < size)
                values[value] = true;
        }
        for (int i = 1; i < m; ++i) {
            int value = SpragGrandi(Math.max(i, n), Math.min(i, n), k) ^
                    SpragGrandi(Math.max(m - i, n), Math.min(m - i, n), k);
            if (value < size)
                values[value] = true;
        }
        //Значение ф-ий Ф-Г - миним. неотриц. значение не встреч среди
        //значений ф-ий Ф-Г для подыгр. 
        int result = 0;
        for (; values[result]; ++result) {}
        results[n][m] = result;
        return result;
    }
}
