import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NimGame {
    //Случайное дерево поиска
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(new File("input.txt")));
        PrintWriter pr = new PrintWriter(new FileOutputStream(new File("output.txt")));
        int n = scanner.nextInt();
        if (n == 0)
            return;
        scanner.nextInt();
        Vertex root = new Vertex(scanner.nextInt(), null, null, 1);
        pr.println(0);
        int elements = 1;
        for (int i = 2; i <= n; ++i) {
            int command = scanner.nextInt();
            int x = scanner.nextInt();
            if (command == 1) {
                root = RandomSearchTree.add(root, x);
                ++elements;
                pr.println(elements - RandomSearchTree.findPosition(root, x));
            }
            if (command == 2) {
                int tmp = RandomSearchTree.findElementOnPosition(root,elements - x);
                root = RandomSearchTree.delete(root, tmp);
                --elements;
            }
        }
        pr.close();
        scanner.close();
    }

    public static void generate(int n, int max) throws FileNotFoundException {
        PrintWriter pr = new PrintWriter(new FileOutputStream(new File("input.txt")));
        Random random = new Random();
        for (int i = 0; i < n; ++i) {
            int tmp = random.nextInt(max) - max / 2;
            if (tmp == 0)
                --tmp;
            pr.print(tmp + " ");
        }
        pr.print(0);
        pr.close();
    }
}
//вершина 
// left, right -левый и правый сыновья
// value - значение, count кол-во элементов в дереве с корнем в данной вершине
class Vertex {
    public int value;
    public Vertex left;
    public Vertex right;
    public int count;

    public Vertex(int value, Vertex left, Vertex right, int count) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.count = count;
    }
}

class RandomSearchTree {
    static int[] res = new int[10];
    static int size = 0;
    //вохвращает true, нсли элемент со значением x,
    //есть в дереве с корнем в v, иначе false 
    public static boolean find(Vertex v, int x) {
        if (v == null)
            return false;
        if ( v.value == x)
            return true;
        if (v.value < x)
            return find(v.right, x);
        else
            return find(v.left, x);
    }
    //соединяет два дерева a и b в одно и возвращает новое дерево
    
    public static Vertex merge(Vertex a, Vertex b) {
        //если одной из вершин - null, то возвращаем вторую
        if (a == null)
            return b;
        if (b == null)
            return a;
        //смотрим какую из вершин корней поддеревьев сделать корнем
        Random random = new Random();
        int r = random.nextInt(a.count + b. count);
        Vertex res;
        if (r < a.count) {
            a.right = merge(a.right, b);
            res = a;
        } else {
            b.left = merge(a, b.left);
            res = b;
        }
        reculc(res);
        return res;
    }
    //добавляем в дерево новое значение x
    //и возвращаем новое дерево с добавленной вершиной
    //т. е. если корень хранится в root
    //то надо вызвать root = add(root, x)
    public static Vertex add(Vertex v, int x) {
        if (v == null)
            return new Vertex(x, null, null, 1);
        Random random = new Random();
        int r = random.nextInt(v.count + 1);

        if (r == 0) {
            SplitTrees st = split(v, x);
            Vertex res = new Vertex(x, st.less, st.more, 0);
            reculc(res);
            return res;
        } else {
            if (v.value > x) {
                v.left = add(v.left, x);
                reculc(v);
            } else {
                v.right = add(v.right, x);
                reculc(v);
            }
        }
        return v;
    }
    //разделение дерева v по ключу x
    //т. е. позвращаетдва дерева в одном все ключи <= x, 
    //а в другом >= x
    public static SplitTrees split(Vertex v, int x) {
        if (v == null)
            return new SplitTrees(null, null);
        SplitTrees res;
        if (v.value < x) {
            SplitTrees tmp = split(v.right, x);
            v.right = tmp.less;
            res = new SplitTrees(v, tmp.more);
        } else {
            SplitTrees tmp = split(v.left, x);
            v.left = tmp.more;
            res = new SplitTrees(tmp.less, v);
        }
        reculc(res.less);
        reculc(res.more);
        return res;
    }
    //удаление вершины с ключом x из дерева v
    //вызов root = delete(root, x)
    public static Vertex delete(Vertex v, int x) {
        if (v == null)
            return null;
        if (v.value == x) {
            Vertex res = merge(v.left, v.right);
            reculc(res);
            return res;
        }
        if (x < v.value)
            v.left = delete(v.left, x);
        else
            v.right = delete(v.right, x);
        reculc(v);
        return v;
    }

    public static void printTree(Vertex v, PrintWriter pw) {
        if (v == null)
            return ;
        printTree(v.left, pw);
        pw.println(v.value);

        printTree(v.right, pw);
    }

    static void reculc(Vertex u) {
        if (u == null)
            return;
        int res = 1;
        if (u.left != null)
            res += u.left.count;
        if (u.right != null)
            res += u.right.count;
        u.count = res;
    }

    public static int findPosition(Vertex v, int x) {
        int leftCount = (v.left == null) ? 0 : v.left.count;
        if (v.value == x)
            return leftCount + 1;
        if (v.value < x)
            return leftCount + 1 + findPosition(v.right, x);
        else
            return findPosition(v.left, x);
    }

    public static int findElementOnPosition(Vertex v, int position) {
        int leftCount = (v.left == null) ? 0 : v.left.count;
        if (leftCount + 1 == position)
            return v.value;
        if (leftCount + 1 < position)
            return findElementOnPosition(v.right, position - leftCount - 1);
        else
            return findElementOnPosition(v.left, position);
    }


}

class SplitTrees {
    Vertex less;
    Vertex more;

    public SplitTrees(Vertex less, Vertex more) {
        this.less = less;
        this.more = more;
    }
}




