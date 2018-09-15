import java.io.*;
import java.util.*;

public class NimGame {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(new File("input.txt")));
        char[] s = scanner.nextLine().toCharArray();
        scanner.close();
        int[] result = computePFL(s);
        PrintWriter pr = new PrintWriter(new FileOutputStream(new File("output.txt")));
        for (int i = 0; i < result.length; ++i)
            pr.print(result[i] + " ");
        pr.close();
    }
    
    //Поиск префикс-функции
    public static int[] computePFL(char[] s) {
        int[] result = new int[s.length];
        result[0] = 0;
        int t = 0;
        for (int i = 1; i < s.length; ++i) {
            while ((t != 0) && (s[t] != s[i]))
                t = result[t - 1];
            if (s[t] == s[i])
                ++t;
            result[i] = t;
        }
        return result;
    }

}

